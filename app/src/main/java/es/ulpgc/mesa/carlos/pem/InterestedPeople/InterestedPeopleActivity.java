package es.ulpgc.mesa.carlos.pem.InterestedPeople;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import es.ulpgc.mesa.carlos.pem.R;
import es.ulpgc.mesa.carlos.pem.utils.BottomNavigationViewHelper;

public class InterestedPeopleActivity
        extends AppCompatActivity implements InterestedPeopleContract.View {

    public static String TAG = InterestedPeopleActivity.class.getSimpleName();

    private InterestedPeopleContract.Presenter presenter;
    private static final int ACTIVITY_NUM= 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interested_people);
        setupBottomNavigationView();

        // do the setup
        InterestedPeopleScreen.configure(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // load the data
    }

    @Override
    public void displayData(InterestedPeopleViewModel viewModel) {
        //Log.e(TAG, "displayData()");

        // deal with the data
        ((TextView) findViewById(R.id.data)).setText(viewModel.data);
    }

    @Override
    public void injectPresenter(InterestedPeopleContract.Presenter presenter) {
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
