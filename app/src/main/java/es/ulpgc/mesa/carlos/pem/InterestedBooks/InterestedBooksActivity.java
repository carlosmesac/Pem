package es.ulpgc.mesa.carlos.pem.InterestedBooks;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

import es.ulpgc.mesa.carlos.pem.App.BookItem;
import es.ulpgc.mesa.carlos.pem.Home.HomeActivity;
import es.ulpgc.mesa.carlos.pem.MyBooks.MyBooksAdapter;
import es.ulpgc.mesa.carlos.pem.MyBooks.MyBooksViewModel;
import es.ulpgc.mesa.carlos.pem.R;
import es.ulpgc.mesa.carlos.pem.utils.BottomNavigationViewHelper;

public class InterestedBooksActivity
        extends AppCompatActivity implements InterestedBooksContract.View {

    public static String TAG = InterestedBooksActivity.class.getSimpleName();

    private InterestedBooksContract.Presenter presenter;
    private static final int ACTIVITY_NUM= 2;
    private ArrayList<BookItem> bookItemArrayList;
    private InterestedBooksAdapter interestedBooksAdapter;
    private RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interested_books);


        TextView textView= findViewById(R.id.title_cabecera);
        textView.setText(R.string.title_interestedBooks);

        setupBottomNavigationView();
        //Iniciacion del recyclerView
        recyclerView = findViewById(R.id.recyclerIdInterested);
        bookItemArrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        setupBottomNavigationView();

        // do the setup
        InterestedBooksScreen.configure(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // load the data
        presenter.fillInterestedBooksArray();
    }

    @Override
    public void displayData(InterestedBooksViewModel viewModel) {
        //Log.e(TAG, "displayData()");

        // deal with the data
    }

    @Override
    public void injectPresenter(InterestedBooksContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void displayInterestedBooks(final InterestedBooksViewModel viewModel) {
        bookItemArrayList = viewModel.bookItemArrayList;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                interestedBooksAdapter = new InterestedBooksAdapter(getApplicationContext(),bookItemArrayList);
                recyclerView.setAdapter(interestedBooksAdapter);


            }
        });
    }
    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bottomNavView);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(this,this,bottomNavigationView);
        Menu menu= bottomNavigationView.getMenu();
        MenuItem menuItem= menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }
}
