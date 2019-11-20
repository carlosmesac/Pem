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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.onesignal.OneSignal;

import java.util.ArrayList;

import es.ulpgc.mesa.carlos.pem.App.BookItem;
import es.ulpgc.mesa.carlos.pem.R;
import es.ulpgc.mesa.carlos.pem.utils.BottomNavigationViewHelper;

public class HomeActivity
        extends AppCompatActivity implements HomeContract.View {

    private ArrayList<BookItem> bookItemArrayList;

    public static String TAG = HomeActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private HomeContract.Presenter presenter;
    private FloatingActionButton addBook;
    private static final int ACTIVITY_NUM = 0;
    FirebaseUser user;
    private HomeAdapter homeAdapter;

    public static String LoggedIn_User_Email;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ActionBar ab= getSupportActionBar();
        ab.setTitle(R.string.title_home);



        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        OneSignal.setSubscription(true);
        //Set the tag for current User
        if (mAuth.getCurrentUser() != null) {

            databaseReference.child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String userName = dataSnapshot.child("username").getValue().toString();
                    OneSignal.sendTag("User_ID", userName);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


        //setup recyclerView
        recyclerView = findViewById(R.id.recyclerId);
        bookItemArrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        //Setting navigationView
        setupBottomNavigationView();
//        setupToolbar();

        addBook = findViewById(R.id.addBook);

        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        if (viewModel.message != "") {
            Toast.makeText(getApplicationContext(), viewModel.message, Toast.LENGTH_LONG).show();
        }
        // deal with the data
    }

    @Override
    public void displayHomeBooks(HomeViewModel viewModel) {
        bookItemArrayList = viewModel.bookItemArrayList;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                homeAdapter = new HomeAdapter(getApplicationContext(), bookItemArrayList);
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


//    /**
//     * Set the top navigation bar
//     */
//    private void setupToolbar() {
//        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if (item.getItemId() == R.id.mybutton) {
//                    presenter.signOut();
//
//                    return false;
//                }
//                return false;
//            }
//        });
//    }

    /**
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView() {
        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bottomNavView);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(this, this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.mymenu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        MenuItem settings= menu.findItem(R.id.mybutton);
        settings.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                presenter.signOut();
                return false;
            }
        });
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