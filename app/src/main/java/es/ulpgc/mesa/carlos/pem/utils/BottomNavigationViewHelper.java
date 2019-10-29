package es.ulpgc.mesa.carlos.pem.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import es.ulpgc.mesa.carlos.pem.Home.HomeActivity;
import es.ulpgc.mesa.carlos.pem.InterestedBooks.InterestedBooksActivity;
import es.ulpgc.mesa.carlos.pem.InterestedPeople.InterestedPeopleActivity;
import es.ulpgc.mesa.carlos.pem.MyBooks.MyBooksActivity;
import es.ulpgc.mesa.carlos.pem.R;

public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationView";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx) {
        bottomNavigationViewEx.enableAnimation(true);
        bottomNavigationViewEx.enableItemShiftingMode(true);
        bottomNavigationViewEx.enableShiftingMode(true);
        bottomNavigationViewEx.setTextVisibility(true);
    }


    public static void enableNavigation(final Context context, final Activity callingActivity, BottomNavigationViewEx bottomNavigationViewEx) {
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.ic_house:
                        if (context.getClass() != HomeActivity.class) {
                            Intent intent1 = new Intent(context, HomeActivity.class);
                            context.startActivity(intent1);
                            callingActivity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                        }

                        break;
                    case R.id.ic_stand:
                        if (context.getClass() != MyBooksActivity.class) {
                            Intent intent2 = new Intent(context, MyBooksActivity.class);
                            context.startActivity(intent2);
                            callingActivity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

                        }

                        break;
                    case R.id.ic_like:
                        if (context.getClass() != InterestedBooksActivity.class) {
                            Intent intent3 = new Intent(context, InterestedBooksActivity.class);
                            context.startActivity(intent3);
                            callingActivity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

                        }

                        break;
                    case R.id.ic_person:
                        if (context.getClass() != InterestedPeopleActivity.class) {
                            Intent intent4 = new Intent(context, InterestedPeopleActivity.class);
                            context.startActivity(intent4);
                            callingActivity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

                        }
                        break;
                }
                return false;
            }
        });
    }
}
