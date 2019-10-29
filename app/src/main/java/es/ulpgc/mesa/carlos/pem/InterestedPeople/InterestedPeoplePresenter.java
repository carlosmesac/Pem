package es.ulpgc.mesa.carlos.pem.InterestedPeople;

import android.util.Log;

import java.lang.ref.WeakReference;

public class InterestedPeoplePresenter implements InterestedPeopleContract.Presenter {

    public static String TAG = InterestedPeoplePresenter.class.getSimpleName();

    private WeakReference<InterestedPeopleContract.View> view;
    private InterestedPeopleViewModel viewModel;
    private InterestedPeopleContract.Model model;
    private InterestedPeopleContract.Router router;

    public InterestedPeoplePresenter(InterestedPeopleState state) {
        viewModel = state;
    }

    @Override
    public void fetchData() {
        // Log.e(TAG, "fetchData()");

        // use passed state if is necessary
        InterestedPeopleState state = router.getDataFromPreviousScreen();
        if (state != null) {

            // update view and model state
            viewModel.data = state.data;

            // update the view
            view.get().displayData(viewModel);

            return;
        }

        // call the model  
        String data = model.fetchData();

        // set view state
        viewModel.data = data;

        // update the view
        view.get().displayData(viewModel);

    }

    @Override
    public void injectView(WeakReference<InterestedPeopleContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(InterestedPeopleContract.Model model) {
        this.model = model;
    }

    @Override
    public void injectRouter(InterestedPeopleContract.Router router) {
        this.router = router;
    }
}
