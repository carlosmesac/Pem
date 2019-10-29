package es.ulpgc.mesa.carlos.pem.Usuario;

import android.content.Context;
import android.content.Intent;

import es.ulpgc.mesa.carlos.pem.App.AppMediator;
import es.ulpgc.mesa.carlos.pem.Home.HomeActivity;

public class UserRouter implements UserContract.Router {

    public static String TAG = UserRouter.class.getSimpleName();

    private AppMediator mediator;

    public UserRouter(AppMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void navigateToNextScreen() {
        Context context = mediator.getApplicationContext();
        Intent intent = new Intent(context, UserActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void passDataToNextScreen(UserState state) {
        mediator.setUserState(state);
    }

    @Override
    public UserState getDataFromPreviousScreen() {
        UserState state = mediator.getUserState();
        return state;
    }

    @Override
    public void goHome() {
        Context context = mediator.getApplicationContext();
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
