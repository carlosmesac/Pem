package es.ulpgc.mesa.carlos.pem.InterestedBooks;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import es.ulpgc.mesa.carlos.pem.App.BookItem;
import es.ulpgc.mesa.carlos.pem.R;
import es.ulpgc.mesa.carlos.pem.Usuario.UserActivity;

public class InterestedBooksAdapter extends RecyclerView.Adapter<InterestedBooksAdapter.ViewHolder> {

    private ArrayList<BookItem> bookList;
    public static String TAG = InterestedBooksAdapter.class.getSimpleName();
    Dialog myDialog;
    private DatabaseReference booksILike = FirebaseDatabase.getInstance().getReference().child("BooksILike");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Context context;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private int currentItem=0;

    public InterestedBooksAdapter(Context context, ArrayList<BookItem> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public InterestedBooksAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_books_item, null, false);

        myDialog = new Dialog(parent.getContext());
        myDialog.setContentView(R.layout.interested_book_dialog);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final InterestedBooksAdapter.ViewHolder holder, int position) {


        //Dialog ini
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentItem=holder.getLayoutPosition();
                Button contact;
                Button user;
                Button delete;
                TextView dialog_author;
                TextView dialog_title;
                ImageView dialog_image;
                contact = (Button) myDialog.findViewById(R.id.dialogInterestedContact);
                user = (Button) myDialog.findViewById(R.id.dialogInterestedUser);
                delete = (Button) myDialog.findViewById(R.id.dialogInterestedDelete);
                dialog_author = (TextView) myDialog.findViewById(R.id.dialogInterestedAuthor);
                dialog_title = (TextView) myDialog.findViewById(R.id.dialogInterestedTitle);
                dialog_image = (ImageView) myDialog.findViewById(R.id.dialogInterestedImage);
                dialog_author.setText(bookList.get(currentItem).getAutor());
                dialog_title.setText(bookList.get(currentItem).getTitle());
                loadImageFromURL(dialog_image, bookList.get(currentItem).getImage());


                final BookItem bookItem = bookList.get(holder.getAdapterPosition());
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        booksILike.child(mAuth.getCurrentUser().getUid()).child(bookItem.getTitle() + "_" + bookItem.getIsbn()).removeValue();
                        bookList.clear();
                        myDialog.dismiss();
                        notifyDataSetChanged();
                    }
                });
                user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, UserActivity.class);
                        String message = bookList.get(currentItem).getUser();
                        intent.putExtra(EXTRA_MESSAGE, message);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(intent);

                    }
                });
                contact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mailto = "mailto:"+ bookList.get(currentItem).getCorreo() + "?subject=" + "Interesado en el libro: "+bookList.get(currentItem).getTitle();

                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                        emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        emailIntent.setData(Uri.parse(mailto));

                        try {
                            context.startActivity(emailIntent);
                        } catch (ActivityNotFoundException e) {
                            //TODO: Handle case where no email app is available
                        }





                    }
                });

                Log.d(TAG, "Click on" + currentItem);
                myDialog.show();

            }
        });
        holder.title.setText(bookList.get(position).getTitle());
        holder.author.setText(bookList.get(position).getAutor());
        loadImageFromURL(holder.image, bookList.get(position).getImage());


    }


    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView author;
        ImageView image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.myBookTitle);
            author = (TextView) itemView.findViewById(R.id.myBookAuthor);
            image = (ImageView) itemView.findViewById(R.id.imageMyBook);


        }

    }

    private void loadImageFromURL(ImageView imageView, String imageURL) {
        Glide.with(imageView.getContext())
                .load(imageURL)
                .into(imageView);
    }

}
