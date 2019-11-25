package es.ulpgc.mesa.carlos.pem.Usuario;

import java.lang.ref.WeakReference;

import es.ulpgc.mesa.carlos.pem.App.Contract;

public interface UserContract {

    interface View {
        void injectPresenter(Presenter presenter);

        void displayData(UserViewModel viewModel);

        void displayUserArray(final UserViewModel viewModel);
    }

    interface Presenter {
        void injectView(WeakReference<View> view);

        void injectModel(Model model);

        void injectRouter(Router router);

        void fetchData();

        void goHome();

        void fillUserArray();

    }

    interface Model {
        String fetchData();

        void fillUserArray(Contract.FillUserArray fillUserArray);
    }

    interface Router {
        void navigateToNextScreen();

        void passDataToNextScreen(UserState state);

        UserState getDataFromPreviousScreen();

        void goHome();
    }
}
