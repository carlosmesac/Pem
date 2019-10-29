package es.ulpgc.mesa.carlos.pem.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import es.ulpgc.mesa.carlos.pem.R;
import es.ulpgc.mesa.carlos.pem.App.BookItem;
import es.ulpgc.mesa.carlos.pem.Home.HomeAdapter;

public class UserActivity
        extends AppCompatActivity implements UserContract.View {
    ArrayList<BookItem> bookItemArrayList;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    public static String TAG = UserActivity.class.getSimpleName();
    private DatabaseReference databaseReference;
    private UserContract.Presenter presenter;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //Enable the back button
        //Set the username of the one that upload the book
        getUsername();
        //Set recyclerView
        Button boton= findViewById(R.id.buttonGoHome);
        recyclerView = findViewById(R.id.recyclerIdUserBooks);
        bookItemArrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        //Filling the array with the user books
        fillArray();
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.goHome();
            }
        });
        // do the setup
        UserScreen.configure(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // load the data
        presenter.fetchData();
    }

    @Override
    public void displayData(UserViewModel viewModel) {
        //Log.e(TAG, "displayData()");

        // deal with the data
    }

    @Override
    public void injectPresenter(UserContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void getUsername() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        Intent intent = getIntent();
        message = intent.getStringExtra(HomeAdapter.EXTRA_MESSAGE);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TextView textView = findViewById(R.id.userBooksUsername);
                textView.setText(dataSnapshot.child(message).child("username").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void fillArray() {
        TextView textView = findViewById(R.id.userBooksUsername);
        textView.getText();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("booksUser").child(message);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    String autor= dataSnapshot1.child("autor").getValue(String.class);
                    String image= dataSnapshot1.child("image").getValue(String.class);
                    String isbn= dataSnapshot1.child("isbn").getValue(String.class);
                    String title= dataSnapshot1.child("title").getValue(String.class);
                    String user= dataSnapshot1.child("user").getValue(String.class);
                    String email= dataSnapshot1.child("correo").getValue(String.class);
                    BookItem bookItem= new BookItem(autor,image,isbn,title,user,email);
                    bookItemArrayList.add(bookItem);

                }

                userAdapter= new UserAdapter(getApplicationContext(),bookItemArrayList);
                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}
