package es.ulpgc.mesa.carlos.pem.AddBook;

import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import es.ulpgc.mesa.carlos.pem.App.Contract;

public class AddBookModel implements AddBookContract.Model {

    public static String TAG = AddBookModel.class.getSimpleName();
    private StorageReference storageRef;
    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;
    private Contract repository;

    public AddBookModel(Contract repository) {
        storageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://proyectopem-54056.firebaseio.com/"); // DB reference
        this.repository= repository;
    }

    @Override
    public String fetchData() {
        // Log.e(TAG, "fetchData()");
        return "Hello";
    }

    /**
     * Method that checks if there is a book with the same ISBN upload by the user if it does not exist upload it
     * @param isbn
     * @param author
     * @param title
     * @param imageView
     * @param callback
     */

    @Override
    public void addBook(final String isbn, final String author, final String title, final ImageView imageView, final Contract.CreateBookEntryCallBack callback) {
        repository.addBook(isbn,author,title,imageView,callback);
    }


}


