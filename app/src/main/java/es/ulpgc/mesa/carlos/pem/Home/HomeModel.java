package es.ulpgc.mesa.carlos.pem.Home;

import com.google.firebase.auth.FirebaseAuth;

import es.ulpgc.mesa.carlos.pem.App.Contract;

public class HomeModel implements HomeContract.Model {

    public static String TAG = HomeModel.class.getSimpleName();
    private FirebaseAuth mAuth;
    private HomeContract.Presenter presenter;
    private Contract repository;


    public HomeModel(Contract repository) {
        mAuth= FirebaseAuth.getInstance();
        this.presenter=presenter;
        this.repository=repository;
    }

    @Override
    public String fetchData() {
        // Log.e(TAG, "fetchData()");
        return "Hello";
    }

    /**
     * Method that log out the user and return to the log in screen
     */
    @Override
    public void signOut(Contract.SignOutCallback callback) {
        repository.signOut(callback);

    }

    /**
     * Method that cheks if there is an user logged
     * @param isLoginCallBack
     */
    @Override
    public void isLogin(Contract.IsLoginCallBack isLoginCallBack) {

        repository.isLogin(isLoginCallBack);

    }



}
