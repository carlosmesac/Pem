package es.ulpgc.mesa.carlos.pem.Home;

import android.content.Intent;
import android.content.Context;

import es.ulpgc.mesa.carlos.pem.AddBook.AddBookActivity;
import es.ulpgc.mesa.carlos.pem.App.AppMediator;
import es.ulpgc.mesa.carlos.pem.Login.LoginActivity;

public class HomeRouter implements HomeContract.Router {

    public static String TAG = HomeRouter.class.getSimpleName();

    private AppMediator mediator;

    public HomeRouter(AppMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void navigateToNextScreen() {
        Context context = mediator.getApplicationContext();
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void passDataToNextScreen(HomeState state) {
        mediator.setHomeState(state);
    }

    @Override
    public HomeState getDataFromPreviousScreen() {
        HomeState state = mediator.getHomeState();
        return state;
    }

    /**
     * Method that goes to the AddBook Activity
     */
    @Override
    public void goAddBookActivity() {
        Context context = mediator.getApplicationContext();
        Intent intent = new Intent(context, AddBookActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * Method that goes to the Login Activity
     */
    @Override
    public void goLogin() {
        Context context = mediator.getApplicationContext();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void goHome() {
        Context context = mediator.getApplicationContext();
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
