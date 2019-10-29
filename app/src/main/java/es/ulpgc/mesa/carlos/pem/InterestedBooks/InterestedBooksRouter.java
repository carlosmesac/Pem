package es.ulpgc.mesa.carlos.pem.InterestedBooks;

import android.content.Intent;
import android.content.Context;

import es.ulpgc.mesa.carlos.pem.App.AppMediator;

public class InterestedBooksRouter implements InterestedBooksContract.Router {

    public static String TAG = InterestedBooksRouter.class.getSimpleName();

    private AppMediator mediator;

    public InterestedBooksRouter(AppMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void navigateToNextScreen() {
        Context context = mediator.getApplicationContext();
        Intent intent = new Intent(context, InterestedBooksActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void passDataToNextScreen(InterestedBooksState state) {
        mediator.setInterestedBooksState(state);
    }

    @Override
    public InterestedBooksState getDataFromPreviousScreen() {
        InterestedBooksState state = mediator.getInterestedBooksState();
        return state;
    }
}
