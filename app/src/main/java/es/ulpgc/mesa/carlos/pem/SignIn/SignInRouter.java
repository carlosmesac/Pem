package es.ulpgc.mesa.carlos.pem.SignIn;

import android.content.Context;
import android.content.Intent;

import es.ulpgc.mesa.carlos.pem.App.AppMediator;
import es.ulpgc.mesa.carlos.pem.Login.LoginActivity;

public class SignInRouter implements SignInContract.Router {

    public static String TAG = SignInRouter.class.getSimpleName();

    private AppMediator mediator;

    public SignInRouter(AppMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void navigateToNextScreen() {
        Context context = mediator.getApplicationContext();
        Intent intent = new Intent(context, SignInActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void passDataToNextScreen(SignInState state) {
        mediator.setSignInState(state);
    }

    @Override
    public SignInState getDataFromPreviousScreen() {
        SignInState state = mediator.getSignInState();
        return state;
    }

    @Override
    public void goLogin() {
        Context context = mediator.getApplicationContext();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
