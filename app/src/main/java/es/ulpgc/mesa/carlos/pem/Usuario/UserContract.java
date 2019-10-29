package es.ulpgc.mesa.carlos.pem.Usuario;

import java.lang.ref.WeakReference;

public interface UserContract {

    interface View {
        void injectPresenter(Presenter presenter);

        void displayData(UserViewModel viewModel);
    }

    interface Presenter {
        void injectView(WeakReference<View> view);

        void injectModel(Model model);

        void injectRouter(Router router);

        void fetchData();

        void goHome();
    }

    interface Model {
        String fetchData();
    }

    interface Router {
        void navigateToNextScreen();

        void passDataToNextScreen(UserState state);

        UserState getDataFromPreviousScreen();

        void goHome();
    }
}
