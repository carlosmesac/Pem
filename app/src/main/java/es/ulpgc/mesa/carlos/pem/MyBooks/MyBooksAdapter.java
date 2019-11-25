package es.ulpgc.mesa.carlos.pem.MyBooks;

import android.app.Dialog;
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

import es.ulpgc.mesa.carlos.pem.App.BookItem;
import es.ulpgc.mesa.carlos.pem.R;

public class MyBooksAdapter extends RecyclerView.Adapter<MyBooksAdapter.ViewHolder> {

    private ArrayList<BookItem> bookList;
    public static String TAG = MyBooksAdapter.class.getSimpleName();

    Dialog myDialog;
    private DatabaseReference allBooksRef = FirebaseDatabase.getInstance().getReference().child("allBooks");
    private DatabaseReference booksRef = FirebaseDatabase.getInstance().getReference().child("booksUser");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private int currentItem=0;

    public MyBooksAdapter(ArrayList<BookItem> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public MyBooksAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_books_item, null, false);

        myDialog = new Dialog(parent.getContext());
        myDialog.setContentView(R.layout.mybook_dialog);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyBooksAdapter.ViewHolder holder, int position) {
        holder.title.setText(bookList.get(position).getTitle());
        holder.author.setText(bookList.get(position).getAutor());
        loadImageFromURL(holder.image, bookList.get(position).getImage());


        //Dialog ini
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentItem=holder.getLayoutPosition();
                Button delete = (Button) myDialog.findViewById(R.id.dialogDelete);
                TextView dialog_author = (TextView) myDialog.findViewById(R.id.dialogAuthor);
                TextView dialog_title = (TextView) myDialog.findViewById(R.id.dialogTitle);
                ImageView dialog_image = (ImageView) myDialog.findViewById(R.id.dialogImage);
                dialog_author.setText(bookList.get(currentItem).getAutor());
                dialog_title.setText(bookList.get(currentItem).getTitle());
                loadImageFromURL(dialog_image, bookList.get(currentItem).getImage());
                final BookItem bookItem = bookList.get(currentItem);

                myDialog.show();
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        allBooksRef.child(mAuth.getCurrentUser().getUid() + "_" + bookItem.getTitle() + "_" + bookItem.getIsbn()).removeValue();
                        booksRef.child(mAuth.getCurrentUser().getUid()).child(bookItem.getTitle() + "_" + bookItem.getIsbn()).removeValue();
                        final StorageReference ref = storageRef.child("images/" + mAuth.getCurrentUser().getUid() + "/" + bookItem.getTitle() + ".jpg");
                        ref.delete();
                        myDialog.dismiss();
                        bookList.clear();

                    }
                });


                Log.d(TAG, "Click on" + currentItem);


            }
        });


    }


    @Override
    public int getItemCount() {
        return bookList.size();
    }
    public void setItems(ArrayList<BookItem> items) {
        bookList = items;
        Log.d("Adapter", bookList.toString());
        notifyDataSetChanged();
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