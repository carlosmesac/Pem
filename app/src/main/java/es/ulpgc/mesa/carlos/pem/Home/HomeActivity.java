package es.ulpgc.mesa.carlos.pem.Home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

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

import es.ulpgc.mesa.carlos.pem.R;
import es.ulpgc.mesa.carlos.pem.utils.BottomNavigationViewHelper;

public class HomeActivity
        extends AppCompatActivity implements HomeContract.View {

    public static String TAG = HomeActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private HomeContract.Presenter presenter;
    private FloatingActionButton addBook;
    private static final int ACTIVITY_NUM = 0;
    FirebaseUser user;
    public static String LoggedIn_User_Email;
    private DatabaseReference databaseReference;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        TextView textView=(TextView) findViewById(R.id.title_cabecera);
        textView.setText(R.string.title_books);


        databaseReference= FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        OneSignal.setSubscription(true);
        //Set the tag for current User
        if (mAuth.getCurrentUser()!=null) {

            databaseReference.child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String userName= dataSnapshot.child("username").getValue().toString();
                    OneSignal.sendTag("User_ID",userName);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


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

    /**
     * Setup the custom toolbar
     */
    private void setupToolbar() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
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