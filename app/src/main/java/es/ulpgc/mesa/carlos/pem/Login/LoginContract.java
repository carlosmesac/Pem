package es.ulpgc.mesa.carlos.pem.Login;

import java.lang.ref.WeakReference;

import es.ulpgc.mesa.carlos.pem.App.Contract;

public interface LoginContract {


    interface View {
        void injectPresenter(Presenter presenter);

        void displayData(LoginViewModel viewModel);
    }

    interface Presenter {
        void injectView(WeakReference<View> view);

        void injectModel(Model model);

        void injectRouter(Router router);

        void fetchData();

        void startSignInScreen();

        void signIn(String email, String password);

        void onSuccess();

        void startHome();

        void onError();
    }

    interface Model {
        String fetchData();

        void signIn(String email, String password, Contract.OnSignInCallback callback);
    }

    interface Router {
        void navigateToNextScreen();

        void passDataToNextScreen(LoginState state);

        LoginState getDataFromPreviousScreen();

        void signIn();

        void goHome();
    }
}
