package es.ulpgc.mesa.carlos.pem.Usuario;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import es.ulpgc.mesa.carlos.pem.App.Repository;
import es.ulpgc.mesa.carlos.pem.R;
import es.ulpgc.mesa.carlos.pem.App.BookItem;
import es.ulpgc.mesa.carlos.pem.App.Like;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private ArrayList<BookItem> bookList;
    public static String TAG = UserAdapter.class.getSimpleName();

    Dialog myDialog;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference allBooksRef = FirebaseDatabase.getInstance().getReference().child("allBooks");
    private DatabaseReference booksRef = FirebaseDatabase.getInstance().getReference().child("booksUser");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private Context context;
    private String publisher = "";
    private String currentUser = "";
    private int currentItem=0;

    public UserAdapter(Context context, ArrayList<BookItem> bookList) {
        this.bookList = bookList;
        this.context = context;
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
                currentItem=holder.getLayoutPosition();
                Button like = (Button) myDialog.findViewById(R.id.dialogUserLike);
                Button contact = (Button) myDialog.findViewById(R.id.dialogUserContact);
                TextView dialog_author = (TextView) myDialog.findViewById(R.id.dialogUserAuthor);
                TextView dialog_title = (TextView) myDialog.findViewById(R.id.dialogUserTitle);
                ImageView dialog_image = (ImageView) myDialog.findViewById(R.id.dialogUserImage);
                dialog_author.setText(bookList.get(currentItem).getAutor());
                dialog_title.setText(bookList.get(currentItem).getTitle());
                loadImageFromURL(dialog_image, bookList.get(currentItem).getImage());
                final BookItem bookItem = bookList.get(holder.getAdapterPosition());
                like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Like like = new Like(bookList.get(currentItem).getUser(), bookList.get(currentItem).getTitle(), mAuth.getCurrentUser().getUid());
                        databaseReference.child("Likes").child(like.getPublisher()).child(like.getCurrentUser() + like.getTitle()).child("title").setValue(like.getTitle());
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                publisher = dataSnapshot.child("users").child(like.getPublisher()).child("username").getValue().toString();
                                databaseReference.child("Likes").child(like.getPublisher()).child(like.getCurrentUser() + like.getTitle()).child("publisher").setValue(publisher);

                                currentUser = dataSnapshot.child("users").child(like.getCurrentUser()).child("username").getValue().toString();
                                databaseReference.child("Likes").child(like.getPublisher()).child(like.getCurrentUser() + like.getTitle()).child("user").setValue(currentUser);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        sendNotification(publisher, currentUser);

                        databaseReference.child("BooksILike").child(mAuth.getCurrentUser().getUid()).child(bookItem.getTitle() + "_" + bookItem.getIsbn()).setValue(bookItem);
                        myDialog.dismiss();
                    }
                });
                contact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String CC = "";
                        String[] TO = {bookList.get(currentItem).getCorreo()};
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                        emailIntent.putExtra(Intent.EXTRA_CC, CC);
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Interesado en el libro: " + bookList.get(currentItem).getTitle());
                        context.startActivity(Intent.createChooser(emailIntent, "Enviar email."));

                    }
                });

                Log.d(TAG, "Click on" + currentItem);
                myDialog.show();

            }
        });
        //Set the recyclerView items content
        holder.title.setText(bookList.get(position).getTitle());
        holder.author.setText(bookList.get(position).getAutor());
        loadImageFromURL(holder.image, bookList.get(position).getImage());


    }

    /**
     * Method that send a push notification to the book owner
     *
     * @param currentUser
     * @param publisher
     */
    private static void sendNotification(final String publisher, final String currentUser) {

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            try {
                String jsonResponse;

                URL url = new URL("https://onesignal.com/api/v1/notifications");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setUseCaches(false);
                con.setDoOutput(true);
                con.setDoInput(true);

                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setRequestProperty("Authorization", "Basic NTc3M2RmMTUtOTVlMy00NGMxLTg0MmUtNTA0ODA4NDZjNWFi");
                con.setRequestMethod("POST");

                String strJsonBody = "{"
                        + "\"app_id\": \"1d17caa3-26c9-4915-949f-081b2b15ab6f\","

                        + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + publisher + "\"}],"

                        + "\"data\": {\"foo\": \"bar\"},"
                        + "\"contents\": {\"en\": \"A " + currentUser + " le Interesa un libro tuyo\"}"
                        + "}";


                System.out.println("strJsonBody:\n" + strJsonBody);

                byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                con.setFixedLengthStreamingMode(sendBytes.length);

                OutputStream outputStream = con.getOutputStream();
                outputStream.write(sendBytes);

                int httpResponse = con.getResponseCode();
                System.out.println("httpResponse: " + httpResponse);

                if (httpResponse >= HttpURLConnection.HTTP_OK
                        && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                    Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                    jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    scanner.close();
                } else {
                    Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                    jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    scanner.close();
                }
                System.out.println("jsonResponse:\n" + jsonResponse);

            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
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
