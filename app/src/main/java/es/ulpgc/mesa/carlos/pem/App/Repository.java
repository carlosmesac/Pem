package es.ulpgc.mesa.carlos.pem.App;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.onesignal.OneSignal;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import es.ulpgc.mesa.carlos.pem.Home.HomeActivity;
import es.ulpgc.mesa.carlos.pem.R;
import es.ulpgc.mesa.carlos.pem.Usuario.UserActivity;
import es.ulpgc.mesa.carlos.pem.Usuario.UserViewModel;

public class Repository implements Contract {
    private static Repository INSTANCE;
    private FirebaseAuth mAuth;
    private Context context;
    private DatabaseReference mDataBase;
    private StorageReference storageRef;
    private DatabaseReference booksRef;
    private DatabaseReference allBooksRef;
    String url;
    int i = 0;
    private DatabaseReference databaseReference;
    private ArrayList<BookItem> bookItemArrayList;
    private ArrayList<Like> likeArrayList;

    public static String TAG = Repository.class.getSimpleName();


    public static Contract getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(context);
        }
        return INSTANCE;
    }

    private Repository(Context context) {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        bookItemArrayList = new ArrayList<>();

        likeArrayList = new ArrayList<>();

        mDataBase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://proyectopem-54056.firebaseio.com/"); // DB reference

        mAuth = FirebaseAuth.getInstance();

        storageRef = FirebaseStorage.getInstance().getReference();

        booksRef = FirebaseDatabase.getInstance().getReference().child("booksUser");

        allBooksRef = FirebaseDatabase.getInstance().getReference().child("allBooks");

        this.context = context;

    }



    /**
     * Method that creates a new account with the values passed in the parameters
     *
     * @param nombre
     * @param nombreUsuario
     * @param correo
     * @param direccion
     * @param contra
     * @param callback
     */
    @Override
    public void createAcc(final String nombre, final String nombreUsuario, final String correo, final String direccion, final String contra, final CreateAccountCallback callback) {
        mDataBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.child("AllUsername").hasChild(nombreUsuario)) {
                    callback.onAccountCreated(true);
                } else {
                    final Users user = new Users(nombre, nombreUsuario, correo, direccion, contra);
                    createUser(user, callback);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    /**
     * Method that logs in if the user is on the Firebase Authentication dB
     *
     * @param email
     * @param password
     * @param callback
     */
    @Override
    public void signIn(String email, String password, final OnSignInCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            callback.onSignIn(false);
                        } else {
                            // If sign in fails, display a message to the user.
                            callback.onSignIn(true);
                        }
                    }
                });
    }

    /**
     * Method that disconnects the current user
     *
     * @param callback
     */
    @Override
    public void signOut(Contract.SignOutCallback callback) {
        OneSignal.setSubscription(false);
        mAuth.signOut();
        callback.signOut(false);

    }

    /**
     * Method that checks if is there is an user logged
     *
     * @param isLoginCallBack
     */
    @Override
    public void isLogin(IsLoginCallBack isLoginCallBack) {
        if (mAuth.getCurrentUser() != null) {
            isLoginCallBack.isLogin(true);
        } else {
            isLoginCallBack.isLogin(false);
        }
    }

    /**
     * Method that add the data values to the database and storage the image into firebase Storage
     *
     * @param isbn
     * @param author
     * @param title
     * @param imageView
     * @param callback
     */
    @Override
    public void addBook(final String isbn, final String author, final String title, final ImageView imageView, final CreateBookEntryCallBack callback) {
        mDataBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(isbn.equals("")||author.equals("")||title.equals("")){
                    callback.onAddNewBook(true);
                }else {
                    if (dataSnapshot.child("booksUser").child(mAuth.getCurrentUser().getUid()).hasChild(isbn)) {
                        callback.onAddNewBook(true);
                    } else {
                        final StorageReference ref = storageRef.child("images/" + mAuth.getCurrentUser().getUid() + "/" + title + ".jpg");
                        imageView.setDrawingCacheEnabled(true);
                        imageView.buildDrawingCache();
                        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                        byte[] data = baos.toByteArray();
                        UploadTask uploadTask = ref.putBytes(data);
                        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    throw task.getException();
                                }

                                // Continue with the task to get the download URL
                                return ref.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    Uri downloadUri = task.getResult();
                                    url = downloadUri.toString();//Se obtiene la direccion de la imagen que se acaba de subir
                                    BookItem bookItem = new BookItem(author, url, isbn, title, mAuth.getCurrentUser().getUid(),
                                            dataSnapshot.child("users").child(mAuth.getCurrentUser().getUid()).child("email").getValue().toString());
                                    booksRef.child(mAuth.getCurrentUser().getUid()).child(bookItem.getTitle() + "_" + bookItem.getIsbn()).setValue(bookItem);
                                    allBooksRef.child(mAuth.getCurrentUser().getUid() + "_" + bookItem.getTitle() + "_" + bookItem.getIsbn()).setValue(bookItem);
                                    callback.onAddNewBook(false);
                                    i++;
                                } else {
                                    // Handle failures
                                    // ...
                                }
                            }
                        });


                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

    }

    /**
     * Method that fill an arrayList with all the books in the database
     *
     * @param callback
     * @return ArrayList filled with all the books
     */
    @Override
    public ArrayList<BookItem> fillBooksArray(final Contract.FillBooksArray callback) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("booksUser").child(mAuth.getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookItemArrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String autor = dataSnapshot1.child("autor").getValue(String.class);
                    String image = dataSnapshot1.child("image").getValue(String.class);
                    String isbn = dataSnapshot1.child("isbn").getValue(String.class);
                    String title = dataSnapshot1.child("title").getValue(String.class);
                    String user = dataSnapshot1.child("user").getValue(String.class);
                    String email = dataSnapshot1.child("correo").getValue(String.class);
                    BookItem bookItem = new BookItem(autor, image, isbn, title, user, email);
                    bookItemArrayList.add(bookItem);


                }
                callback.onFillBooksArray(false, bookItemArrayList);


                //homeAdapter = new HomeAdapter(getContext(), bookItemArrayList);
                //recyclerView.setAdapter(homeAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return bookItemArrayList;
    }

    /**
     * Method that fill an arrayList with all the books that the current user liked
     *
     * @param callback
     * @return an arrayList with all the books that the current user liked
     */
    @Override
    public ArrayList<BookItem> fillInterestedBooksArray(final FillInterestedBooksArray callback) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("BooksILike").child(mAuth.getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookItemArrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String autor = dataSnapshot1.child("autor").getValue(String.class);
                    String image = dataSnapshot1.child("image").getValue(String.class);
                    String isbn = dataSnapshot1.child("isbn").getValue(String.class);
                    String title = dataSnapshot1.child("title").getValue(String.class);
                    String user = dataSnapshot1.child("user").getValue(String.class);
                    String email = dataSnapshot1.child("correo").getValue(String.class);

                    BookItem bookItem = new BookItem(autor, image, isbn, title, user, email);
                    bookItemArrayList.add(bookItem);

                }
                callback.onFillInterestedBooksArray(false, bookItemArrayList);
                //interestedBooksAdapter= new InterestedBooksAdapter(getContext(),bookItemArrayList);
                //recyclerView.setAdapter(interestedBooksAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return bookItemArrayList;

    }

    /**
     * Method that fills a list with all the user that liked a book that the current user uploaded
     *
     * @param callback
     * @return And arrayList with all the users
     */
    @Override
    public ArrayList<Like> fillInterestedPeopleArray(final FillInterestedPeopleArray callback) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Likes").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                likeArrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String title = dataSnapshot1.child("title").getValue(String.class);
                    String publisher = dataSnapshot1.child("publisher").getValue(String.class);
                    String currentUser = dataSnapshot1.child("user").getValue(String.class);
                    Like like = new Like(publisher, title, currentUser);
                    likeArrayList.add(like);
                }
                callback.onFillInterestedPeopleArray(false, likeArrayList);

                //interestedPeopleAdapter = new InterestedPeopleAdapter(getContext(), likeArrayList);
//                recyclerView.setAdapter(interestedPeopleAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return likeArrayList;
    }

    /**
     * MEthod that fills an array with the books of a certain user
     *
     * @param callback
     * @return array filled with all the books
     */
    @Override
    public ArrayList<BookItem> fillUserArray(final FillUserArray callback) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("booksUser").child(UserActivity.message);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookItemArrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String autor = dataSnapshot1.child("autor").getValue(String.class);
                    String image = dataSnapshot1.child("image").getValue(String.class);
                    String isbn = dataSnapshot1.child("isbn").getValue(String.class);
                    String title = dataSnapshot1.child("title").getValue(String.class);
                    String user = dataSnapshot1.child("user").getValue(String.class);
                    String email = dataSnapshot1.child("correo").getValue(String.class);
                    BookItem bookItem = new BookItem(autor, image, isbn, title, user, email);
                    bookItemArrayList.add(bookItem);
                }
                callback.onFillUserArray(false,bookItemArrayList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return bookItemArrayList;
    }

    @Override
    public ArrayList<BookItem> fillHomeBooksArray(final FillHomeBooksArray callback) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("allBooks");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookItemArrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String autor = dataSnapshot1.child("autor").getValue(String.class);
                    String image = dataSnapshot1.child("image").getValue(String.class);
                    String isbn = dataSnapshot1.child("isbn").getValue(String.class);
                    String title = dataSnapshot1.child("title").getValue(String.class);
                    String user = dataSnapshot1.child("user").getValue(String.class);
                    String email = dataSnapshot1.child("correo").getValue(String.class);
                    BookItem bookItem = new BookItem(autor, image, isbn, title, user, email);
                    bookItemArrayList.add(bookItem);


                }
                callback.onFillHomeBooksArray(false,bookItemArrayList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return bookItemArrayList;
    }


    /**
     * Method that add the user to the firebase database and creates another child to add only the username
     *
     * @param user
     * @param callback
     */
    private void createUser(final Users user, final Contract.CreateAccountCallback callback) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    callback.onAccountCreated(false);// no hay error
                    //set the Display name to the username
                    mDataBase.child("users").child(mAuth.getCurrentUser().getUid()).setValue(user);
                    mDataBase.child("AllUsername").child(user.getUsername()).setValue(user.getUsername());
                } else {
                    callback.onAccountCreated(true);//hay error
                }
            }
        });

    }


}