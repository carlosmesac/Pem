package es.ulpgc.mesa.carlos.pem.SignIn;

import java.lang.ref.WeakReference;

import es.ulpgc.mesa.carlos.pem.App.Contract;

public class SignInPresenter implements SignInContract.Presenter {

    public static String TAG = SignInPresenter.class.getSimpleName();

    private WeakReference<SignInContract.View> view;
    private SignInViewModel viewModel;
    private SignInContract.Model model;
    private SignInContract.Router router;

    public SignInPresenter(SignInState state) {
        viewModel = state;
    }



    @Override
    public void goLoginScreen() {
        router.goLogin();
    }

    @Override
    public void createAccount(final String nombre, final String nombreUsuario, final String correo, final String direccion, final String contra) {
       model.createAcc(nombre,nombreUsuario,correo,direccion,contra, new Contract.CreateAccountCallback() {
           @Override
           public void onAccountCreated(boolean error) {
               if(!error){ //caso positivo
                   viewModel.message= "User created";
                   view.get().displayData(viewModel);
                   router.goLogin();
               }else{ //caso negativo
                   viewModel.message="User could not be created";
                   view.get().displayData(viewModel);
               }
           }
       });
    }


    @Override
    public void injectView(WeakReference<SignInContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(SignInContract.Model model) {
        this.model = model;
    }

    @Override
    public void injectRouter(SignInContract.Router router) {
        this.router = router;
    }

    @Override
    public void fetchData() {

    }
}
