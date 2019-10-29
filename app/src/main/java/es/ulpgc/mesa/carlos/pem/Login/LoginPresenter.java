package es.ulpgc.mesa.carlos.pem.Login;

import java.lang.ref.WeakReference;

import es.ulpgc.mesa.carlos.pem.App.Contract;

public class LoginPresenter implements LoginContract.Presenter {

    public static String TAG = LoginPresenter.class.getSimpleName();

    private WeakReference<LoginContract.View> view;
    private LoginViewModel viewModel;
    private LoginContract.Model model;
    private LoginContract.Router router;

    public LoginPresenter(LoginState state) {
        viewModel = state;
    }



    @Override
    public void startSignInScreen() {
        router.signIn();

    }

    @Override
    public void signIn(String email, String password) {
        model.signIn(email, password, new Contract.OnSignInCallback() {
            @Override
            public void onSignIn(boolean error) {
                if (!error){
                    startHome();
                }else{
                    viewModel.message="Password or username does not exist";
                    view.get().displayData(viewModel);
                }
            }
        });
    }

    @Override
    public void onSuccess() {
        startHome();
    }

    @Override
    public void startHome() {
        router.goHome();
    }

    @Override
    public void onError() {
        viewModel.message="Password or username does not exist";
        view.get().displayData(viewModel);

    }

    @Override
    public void injectView(WeakReference<LoginContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(LoginContract.Model model) {
        this.model = model;
    }

    @Override
    public void injectRouter(LoginContract.Router router) {
        this.router = router;
    }

    @Override
    public void fetchData() {

    }
}
