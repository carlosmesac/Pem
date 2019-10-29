package es.ulpgc.mesa.carlos.pem.AddBook;

import android.content.Context;
import android.content.Intent;

import es.ulpgc.mesa.carlos.pem.App.AppMediator;
import es.ulpgc.mesa.carlos.pem.Home.HomeActivity;

public class AddBookRouter implements AddBookContract.Router {

    public static String TAG = AddBookRouter.class.getSimpleName();

    private AppMediator mediator;

    public AddBookRouter(AppMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void navigateToNextScreen() {
        Context context = mediator.getApplicationContext();
        Intent intent = new Intent(context, AddBookActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void passDataToNextScreen(AddBookState state) {
        mediator.setAddBookState(state);
    }

    @Override
    public AddBookState getDataFromPreviousScreen() {
        AddBookState state = mediator.getAddBookState();
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
