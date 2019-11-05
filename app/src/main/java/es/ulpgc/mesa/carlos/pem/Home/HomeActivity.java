package es.ulpgc.mesa.carlos.pem.Home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

import es.ulpgc.mesa.carlos.pem.App.BookItem;
import es.ulpgc.mesa.carlos.pem.R;
import es.ulpgc.mesa.carlos.pem.utils.BottomNavigationViewHelper;

public class HomeActivity
        extends AppCompatActivity implements HomeContract.View {

    public static String TAG = HomeActivity.class.getSimpleName();

    private HomeContract.Presenter presenter;
    private FloatingActionButton addBook;
    private static final int ACTIVITY_NUM = 0;
    private RecyclerView recyclerView;
    private ArrayList<BookItem> bookItemArrayList;
    private HomeAdapter homeAdapter;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //Setup the recyclerView
        recyclerView = findViewById(R.id.recyclerId);
        bookItemArrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        //Set the title of the activity
        TextView textView=(TextView) findViewById(R.id.title_cabecera);
        textView.setText(R.string.title_books);

        //Setting navigationView
        setupBottomNavigationView();
        setupToolbar();

         addBook = findViewById(R.id.addBook);

         addBook.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
        presenter.goAddBook();
        }
        });

        HomeScreen.configure(this);
    }


    @Override
    protected void onResume() {
        super.onResume();

        // load the data
        presenter.fetchData();
        presenter.isLogin();
        presenter.homeBooksArrayList();

    }

    @Override
    public void displayData(HomeViewModel viewModel) {
        //Log.e(TAG, "displayData()");
        // deal with the data
    }

    @Override
    public void displayHomeBooks(HomeViewModel viewModel) {
        bookItemArrayList = viewModel.bookItemArrayList;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                homeAdapter = new HomeAdapter(getApplicationContext(),bookItemArrayList);
                recyclerView.setAdapter(homeAdapter);


            }
        });
    }


    @Override
    public void injectPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    /**
     * Set the top navigation bar
     */
    private void setupToolbar() {
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.mybutton) {
                    presenter.signOut();

                    return false;
                }
                return false;
            }
        });
    }

    /**
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView() {
        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bottomNavView);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(this,this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.mymenu, menu);

        MenuItem searchItem= menu.findItem(R.id.search);
        SearchView searchView= (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (homeAdapter != null) {
                    homeAdapter.getFilter().filter(newText);
                }
                return false;

            }
        });
        return super.onCreateOptionsMenu(menu);

    }



}