package es.ulpgc.mesa.carlos.pem.Usuario;

import es.ulpgc.mesa.carlos.pem.App.Contract;

public class UserModel implements UserContract.Model {

    public static String TAG = UserModel.class.getSimpleName();
    private Contract repository;

    public UserModel(Contract contract) {
        this.repository= contract;
    }

    @Override
    public String fetchData() {
        // Log.e(TAG, "fetchData()");
        return "Hello";
    }

    @Override
    public void fillUserArray(Contract.FillUserArray fillUserArray) {
        repository.fillUserArray(fillUserArray);
    }
}
