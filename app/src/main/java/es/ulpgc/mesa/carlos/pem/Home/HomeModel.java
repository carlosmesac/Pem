package es.ulpgc.mesa.carlos.pem.Home;

import com.google.firebase.auth.FirebaseAuth;

import es.ulpgc.mesa.carlos.pem.App.Contract;

public class HomeModel implements HomeContract.Model {

    public static String TAG = HomeModel.class.getSimpleName();
    private FirebaseAuth mAuth;
    private HomeContract.Presenter presenter;
    private Contract repository;


    public HomeModel(Contract repository) {
        mAuth = FirebaseAuth.getInstance();
        this.repository = repository;
    }

    @Override
    public String fetchData() {
        // Log.e(TAG, "fetchData()");
        return "Hello";
    }


    @Override
    public void signOut(Contract.SignOutCallback callback) {
        repository.signOut(callback);

    }


    @Override
    public void isLogin(Contract.IsLoginCallBack isLoginCallBack) {

        repository.isLogin(isLoginCallBack);

    }

    @Override
    public void fillHomeBooksArrayList(Contract.FillHomeBooksArray fillHomeBooksArray) {
        repository.fillHomeBooksArray(fillHomeBooksArray);
    }
}