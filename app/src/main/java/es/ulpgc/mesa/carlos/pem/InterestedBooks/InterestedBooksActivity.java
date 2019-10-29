package es.ulpgc.mesa.carlos.pem.InterestedBooks;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import es.ulpgc.mesa.carlos.pem.Home.HomeActivity;
import es.ulpgc.mesa.carlos.pem.R;
import es.ulpgc.mesa.carlos.pem.utils.BottomNavigationViewHelper;

public class InterestedBooksActivity
        extends AppCompatActivity implements InterestedBooksContract.View {

    public static String TAG = InterestedBooksActivity.class.getSimpleName();

    private InterestedBooksContract.Presenter presenter;
    private static final int ACTIVITY_NUM= 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interested_books);
        setupBottomNavigationView();

        // do the setup
        InterestedBooksScreen.configure(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // load the data
    }

    @Override
    public void displayData(InterestedBooksViewModel viewModel) {
        //Log.e(TAG, "displayData()");

        // deal with the data
        ((TextView) findViewById(R.id.data)).setText(viewModel.data);
    }

    @Override
    public void injectPresenter(InterestedBooksContract.Presenter presenter) {
        this.presenter = presenter;
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
