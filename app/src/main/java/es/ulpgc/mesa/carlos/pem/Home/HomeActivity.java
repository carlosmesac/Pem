package es.ulpgc.mesa.carlos.pem.Home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import es.ulpgc.mesa.carlos.pem.R;
import es.ulpgc.mesa.carlos.pem.utils.BottomNavigationViewHelper;

public class HomeActivity
        extends AppCompatActivity implements HomeContract.View {

    public static String TAG = HomeActivity.class.getSimpleName();

    private HomeContract.Presenter presenter;
    private FloatingActionButton addBook;
    private static final int ACTIVITY_NUM = 0;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        TextView textView=(TextView) findViewById(R.id.title_cabecera);
        textView.setText(R.string.title_books);

        //Setting navigationView
        setupBottomNavigationView();
        setupViewPager();
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
    }

    @Override
    public void displayData(HomeViewModel viewModel) {
        //Log.e(TAG, "displayData()");
        if (viewModel.message != "") {
            Toast.makeText(getApplicationContext(), viewModel.message, Toast.LENGTH_LONG).show();
        }
        // deal with the data
    }

    @Override
    public void injectPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }


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




    private void setupViewPager() {
        BooksAdapter adapter = new BooksAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment());
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(adapter);

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



}