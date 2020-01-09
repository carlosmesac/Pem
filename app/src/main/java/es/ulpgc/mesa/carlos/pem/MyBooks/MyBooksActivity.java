package es.ulpgc.mesa.carlos.pem.MyBooks;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

import es.ulpgc.mesa.carlos.pem.App.BookItem;
import es.ulpgc.mesa.carlos.pem.R;
import es.ulpgc.mesa.carlos.pem.utils.BottomNavigationViewHelper;

public class MyBooksActivity
        extends AppCompatActivity implements MyBooksContract.View {

    public static String TAG = MyBooksActivity.class.getSimpleName();

    private MyBooksContract.Presenter presenter;
    private static final int ACTIVITY_NUM = 1;
    ArrayList<BookItem> bookItemArrayList;
    DatabaseReference databaseReference;
    private MyBooksAdapter myBooksAdapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);
        getSupportActionBar().setTitle(R.string.title_myBooks);


        setupBottomNavigationView();
        //Iniciacion del recyclerView
        recyclerView = findViewById(R.id.recyclerIdMyBooks);
        bookItemArrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        // do the setup

        FloatingActionButton addButton= findViewById(R.id.addBook);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addBook();
            }
        });
        MyBooksScreen.configure(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // load the data
        presenter.fillBooksArray();
    }

    @Override
    public void displayData(MyBooksViewModel viewModel) {
        //Log.e(TAG, "displayData()");

        // deal with the data
    }

    @Override
    public void injectPresenter(MyBooksContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void displayBooks(final MyBooksViewModel viewModel) {
        bookItemArrayList = viewModel.bookItemArrayList;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myBooksAdapter = new MyBooksAdapter(bookItemArrayList);
                recyclerView.setAdapter(myBooksAdapter);


            }
        });
    }


    private void setupBottomNavigationView() {
        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bottomNavView);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(this, this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
