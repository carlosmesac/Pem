package es.ulpgc.mesa.carlos.pem.Login;

import com.google.firebase.auth.FirebaseAuth;

import es.ulpgc.mesa.carlos.pem.App.Contract;

public class LoginModel implements LoginContract.Model {

    public static String TAG = LoginModel.class.getSimpleName();
    private FirebaseAuth mAuth;
    private LoginContract.Presenter presenter;
    private Contract repository;


    public LoginModel(Contract repository) {
        this.mAuth = FirebaseAuth.getInstance();
        this.presenter = presenter;
        this.repository= repository;
    }

    @Override
    public String fetchData() {
        // Log.e(TAG, "fetchData()");
        return "Hello";
    }


    @Override
    public void signIn(String email, String password,Contract.OnSignInCallback callback) {
        repository.signIn(email,password,callback);
    }
}
