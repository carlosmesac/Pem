package es.ulpgc.mesa.carlos.pem.Usuario;

public class UserModel implements UserContract.Model {

    public static String TAG = UserModel.class.getSimpleName();

    public UserModel() {

    }

    @Override
    public String fetchData() {
        // Log.e(TAG, "fetchData()");
        return "Hello";
    }
}
