package es.ulpgc.mesa.carlos.pem.Home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import es.ulpgc.mesa.carlos.pem.App.Repository;
import es.ulpgc.mesa.carlos.pem.R;
import es.ulpgc.mesa.carlos.pem.App.BookItem;
import es.ulpgc.mesa.carlos.pem.App.Like;
import es.ulpgc.mesa.carlos.pem.Usuario.UserActivity;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> implements Filterable {
    public static String TAG = HomeAdapter.class.getSimpleName();

    private ArrayList<BookItem> bookList;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Context context;
    Dialog myDialog;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private ArrayList<BookItem> bookListFull;

    public HomeAdapter(Context context, ArrayList<BookItem> bookList) {
        this.context = context;
        this.bookList = bookList;
        bookListFull= new ArrayList<>(bookList);//Create a new list that contains the full content of the array
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.books_item, null, false);
        myDialog = new Dialog(parent.getContext());
        myDialog.setContentView(R.layout.book_dialog);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeAdapter.ViewHolder holder, int position) {
        holder.title.setText(bookList.get(position).getTitle());
        holder.author.setText(bookList.get(position).getAutor());
        loadImageFromURL(holder.image, bookList.get(position).getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Button contact;
                Button user;
                Button like;
                TextView dialog_author;
                TextView dialog_title;
                ImageView dialog_image;
                contact = (Button) myDialog.findViewById((R.id.dialogBookContact));
                user = (Button) myDialog.findViewById(R.id.dialogBookUser);
                like = (Button) myDialog.findViewById(R.id.dialogBookLike);
                dialog_author = (TextView) myDialog.findViewById(R.id.dialogBookAuthor);
                dialog_title = (TextView) myDialog.findViewById(R.id.dialogBookTitle);
                dialog_image = (ImageView) myDialog.findViewById(R.id.dialogBookImage);
                dialog_author.setText(bookList.get(holder.getLayoutPosition()).getAutor());
                dialog_title.setText(bookList.get(holder.getLayoutPosition()).getTitle());
                loadImageFromURL(dialog_image, bookList.get(holder.getLayoutPosition()).getImage());
                final BookItem bookItem = bookList.get(holder.getAdapterPosition());
                user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, UserActivity.class);
                        String message = bookList.get(holder.getLayoutPosition()).getUser();
                        intent.putExtra(EXTRA_MESSAGE, message);
                        context.startActivity(intent);
                        Log.d(TAG, String.valueOf(holder.getLayoutPosition()));

                    }
                });
                like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Like like= new Like(bookList.get(holder.getLayoutPosition()).getUser(),bookList.get(holder.getLayoutPosition()).getTitle(),mAuth.getCurrentUser().getUid());
                        databaseReference.child("Likes").child(like.getPublisher()).child(like.getCurrentUser()+like.getTitle()).child("title").setValue(like.getTitle());
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String publisher=dataSnapshot.child("users").child(like.getPublisher()).child("username").getValue().toString();
                                databaseReference.child("Likes").child(like.getPublisher()).child(like.getCurrentUser()+like.getTitle()).child("publisher").setValue(publisher);

                                String currentUser= dataSnapshot.child("users").child(like.getCurrentUser()).child("username").getValue().toString();
                                databaseReference.child("Likes").child(like.getPublisher()).child(like.getCurrentUser()+like.getTitle()).child("user").setValue(currentUser);



                                Repository.sendNotification(publisher,currentUser);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        databaseReference.child("BooksILike").child(mAuth.getCurrentUser().getUid()).child(bookItem.getTitle() + "_" + bookItem.getIsbn()).setValue(bookItem);
                        myDialog.dismiss();
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

                Log.d(TAG, "Click on" + bookList.get(holder.getLayoutPosition()).getCorreo());
                myDialog.show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }



    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<BookItem> filterList= new ArrayList<>();
            if(constraint==null|| constraint.length()==0){
                filterList.addAll(bookListFull);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(BookItem bookItem: bookListFull){
                    if(bookItem.getTitle().toLowerCase().contains(filterPattern)){
                        filterList.add(bookItem);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values= filterList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            bookList.clear();
            bookList.addAll((ArrayList)results.values);

            notifyDataSetChanged();
        }
    };

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
