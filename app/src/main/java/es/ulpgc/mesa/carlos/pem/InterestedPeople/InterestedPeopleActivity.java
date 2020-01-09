package es.ulpgc.mesa.carlos.pem.InterestedPeople;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

import es.ulpgc.mesa.carlos.pem.App.Like;
import es.ulpgc.mesa.carlos.pem.MyBooks.MyBooksAdapter;
import es.ulpgc.mesa.carlos.pem.MyBooks.MyBooksViewModel;
import es.ulpgc.mesa.carlos.pem.R;
import es.ulpgc.mesa.carlos.pem.utils.BottomNavigationViewHelper;

public class InterestedPeopleActivity
        extends AppCompatActivity implements InterestedPeopleContract.View {

    public static String TAG = InterestedPeopleActivity.class.getSimpleName();

    private InterestedPeopleContract.Presenter presenter;
    private static final int ACTIVITY_NUM = 3;
    private ArrayList<Like> likeArrayList;
    private InterestedPeopleAdapter interestedPeopleAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interested_people);
        getSupportActionBar().setTitle(R.string.title_interestedPeople);


        //Init recyclerView
        recyclerView = findViewById(R.id.recyclerIdInterestedPeople);
        likeArrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        setupBottomNavigationView();


        // do the setup
        InterestedPeopleScreen.configure(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.fillLikesArray();

        // load the data
    }

    @Override
    public void displayData(InterestedPeopleViewModel viewModel) {
        //Log.e(TAG, "displayData()");

        // deal with the data
    }

    @Override
    public void displayInterestedPeople(final InterestedPeopleViewModel viewModel) {
        likeArrayList = viewModel.likeArrayList;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                interestedPeopleAdapter = new InterestedPeopleAdapter(getApplicationContext(), likeArrayList);
                recyclerView.setAdapter(interestedPeopleAdapter);


            }
        });
    }

    @Override
    public void injectPresenter(InterestedPeopleContract.Presenter presenter) {
        this.presenter = presenter;
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
