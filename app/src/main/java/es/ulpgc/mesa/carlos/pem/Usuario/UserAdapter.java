package es.ulpgc.mesa.carlos.pem.Usuario;

import android.app.Dialog;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import es.ulpgc.mesa.carlos.pem.R;
import es.ulpgc.mesa.carlos.pem.App.BookItem;
import es.ulpgc.mesa.carlos.pem.App.Like;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private ArrayList<BookItem> bookList;
    public static String TAG = UserAdapter.class.getSimpleName();

    Dialog myDialog;
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    private DatabaseReference allBooksRef= FirebaseDatabase.getInstance().getReference().child("allBooks");
    private DatabaseReference booksRef = FirebaseDatabase.getInstance().getReference().child("booksUser");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private Context context;


    public UserAdapter(Context context,ArrayList<BookItem> bookList) {
        this.bookList = bookList;
        this.context=context;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.books_item, null, false);

        myDialog = new Dialog(parent.getContext());
        myDialog.setContentView(R.layout.user_dialog);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserAdapter.ViewHolder holder, int position) {
        //Dialog ini
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button like = (Button) myDialog.findViewById(R.id.dialogUserLike);
                Button contact =(Button) myDialog.findViewById(R.id.dialogUserContact) ;
                TextView dialog_author = (TextView) myDialog.findViewById(R.id.dialogUserAuthor);
                TextView dialog_title = (TextView) myDialog.findViewById(R.id.dialogUserTitle);
                ImageView dialog_image = (ImageView) myDialog.findViewById(R.id.dialogUserImage);
                dialog_author.setText(bookList.get(holder.getLayoutPosition()).getAutor());
                dialog_title.setText(bookList.get(holder.getLayoutPosition()).getTitle());
                loadImageFromURL(dialog_image, bookList.get(holder.getLayoutPosition()).getImage());
                final BookItem bookItem= bookList.get(holder.getAdapterPosition());
                like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //
                        Like like= new Like(bookList.get(holder.getLayoutPosition()).getUser(),bookList.get(holder.getLayoutPosition()).getTitle(),mAuth.getCurrentUser().getUid());
                        databaseReference.child("Likes").child(like.getPublisher()).child(like.getCurrentUser()).child("title").setValue(like.getTitle());
                        databaseReference.child("Likes").child(like.getPublisher()).child(like.getCurrentUser()).child("user").setValue(like.getCurrentUser());

                        //Add to the database the book u liked
                        databaseReference.child("BooksILike").child(mAuth.getCurrentUser().getUid()).child(bookItem.getTitle()+"_"+bookItem.getIsbn()).setValue(bookItem);
                    }
                });
                contact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String CC = "";
                        String[] TO = {bookList.get(holder.getLayoutPosition()).getCorreo()};
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                        emailIntent.putExtra(Intent.EXTRA_CC, CC);
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Interesado en el libro: "+bookList.get(holder.getLayoutPosition()).getTitle());
                        context.startActivity(Intent.createChooser(emailIntent,"Enviar email."));

                    }
                });

                Log.d(TAG, "Click on" + holder.getLayoutPosition());
                myDialog.show();

            }
        });
        //Set the recyclerView items content
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
            title = (TextView) itemView.findViewById(R.id.bookTitle);
            author = (TextView) itemView.findViewById(R.id.bookAuthor);
            image = (ImageView) itemView.findViewById(R.id.imageBook);

        }

    }

    private void loadImageFromURL(ImageView imageView, String imageURL) {
        Glide.with(imageView.getContext())
                .load(imageURL)
                .into(imageView);
    }

}
