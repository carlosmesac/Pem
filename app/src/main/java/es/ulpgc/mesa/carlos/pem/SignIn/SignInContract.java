package es.ulpgc.mesa.carlos.pem.SignIn;

import java.lang.ref.WeakReference;

import es.ulpgc.mesa.carlos.pem.App.Contract;

public interface SignInContract {

    interface View {
        void injectPresenter(Presenter presenter);

        void displayData(SignInViewModel viewModel);
    }

    interface Presenter {
        void injectView(WeakReference<View> view);

        void injectModel(Model model);

        void injectRouter(Router router);

        void fetchData();

        void goLoginScreen();

        void createAccount(String nombre, String nombreUsuario, String correo, String direccion, String contra);

    }

    interface Model {
        String fetchData();

        void createAcc(String nombre, String nombreUsuario, String correo, String direccion, String contra, Contract.CreateAccountCallback callback);

    }

    interface Router {
        void navigateToNextScreen();

        void passDataToNextScreen(SignInState state);

        SignInState getDataFromPreviousScreen();

        void goLogin();
    }
}
