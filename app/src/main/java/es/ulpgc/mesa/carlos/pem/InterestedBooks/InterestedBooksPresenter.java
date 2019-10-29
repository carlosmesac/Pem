package es.ulpgc.mesa.carlos.pem.InterestedBooks;

import android.util.Log;

import java.lang.ref.WeakReference;

public class InterestedBooksPresenter implements InterestedBooksContract.Presenter {

    public static String TAG = InterestedBooksPresenter.class.getSimpleName();

    private WeakReference<InterestedBooksContract.View> view;
    private InterestedBooksViewModel viewModel;
    private InterestedBooksContract.Model model;
    private InterestedBooksContract.Router router;

    public InterestedBooksPresenter(InterestedBooksState state) {
        viewModel = state;
    }

    @Override
    public void fetchData() {
        // Log.e(TAG, "fetchData()");

        // use passed state if is necessary
        InterestedBooksState state = router.getDataFromPreviousScreen();
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
    public void injectView(WeakReference<InterestedBooksContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(InterestedBooksContract.Model model) {
        this.model = model;
    }

    @Override
    public void injectRouter(InterestedBooksContract.Router router) {
        this.router = router;
    }
}
