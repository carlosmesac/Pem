package es.ulpgc.mesa.carlos.pem.Usuario;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import es.ulpgc.mesa.carlos.pem.App.BookItem;
import es.ulpgc.mesa.carlos.pem.App.Contract;

public class UserPresenter implements UserContract.Presenter {

    public static String TAG = UserPresenter.class.getSimpleName();

    private WeakReference<UserContract.View> view;
    private UserViewModel viewModel;
    private UserContract.Model model;
    private UserContract.Router router;

    public UserPresenter(UserState state) {
        viewModel = state;
    }

    @Override
    public void fetchData() {
        // Log.e(TAG, "fetchData()");

        // use passed state if is necessary
        UserState state = router.getDataFromPreviousScreen();
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
    public void goHome() {
        router.goHome();
    }

    @Override
    public void fillUserArray() {
        model.fillUserArray(new Contract.FillUserArray() {
            @Override
            public void onFillUserArray(boolean error, ArrayList<BookItem> bookItemArrayList) {
                viewModel.bookItemArrayList=bookItemArrayList;
                view.get().displayUserArray(viewModel);
            }
        });
    }

    @Override
    public void injectView(WeakReference<UserContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(UserContract.Model model) {
        this.model = model;
    }

    @Override
    public void injectRouter(UserContract.Router router) {
        this.router = router;
    }
}
