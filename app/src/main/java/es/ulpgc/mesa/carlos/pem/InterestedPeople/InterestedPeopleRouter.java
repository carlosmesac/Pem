package es.ulpgc.mesa.carlos.pem.InterestedPeople;

import android.content.Intent;
import android.content.Context;

import es.ulpgc.mesa.carlos.pem.App.AppMediator;

public class InterestedPeopleRouter implements InterestedPeopleContract.Router {

    public static String TAG = InterestedPeopleRouter.class.getSimpleName();

    private AppMediator mediator;

    public InterestedPeopleRouter(AppMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void navigateToNextScreen() {
        Context context = mediator.getApplicationContext();
        Intent intent = new Intent(context, InterestedPeopleActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void passDataToNextScreen(InterestedPeopleState state) {
        mediator.setInterestedPeopleState(state);
    }

    @Override
    public InterestedPeopleState getDataFromPreviousScreen() {
        InterestedPeopleState state = mediator.getInterestedPeopleState();
        return state;
    }
}
