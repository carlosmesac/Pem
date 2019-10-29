package es.ulpgc.mesa.carlos.pem.SignIn;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.ulpgc.mesa.carlos.pem.App.Contract;

public class SignInModel implements SignInContract.Model {

    public static String TAG = SignInModel.class.getSimpleName();

    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;
    private boolean value;
    private Contract repository;

    public SignInModel(Contract repository) {
        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://proyectopem-54056.firebaseio.com/"); // DB reference
        this.repository= repository;
    }

    @Override
    public String fetchData() {
        // Log.e(TAG, "fetchData()");
        return "Hello";
    }

    /**
     * This method checks if the DB has an user with the same username, if it exists dont create a new one but if it does not exist check if the email is used and if its not creates a new entry
     * @param nombre
     * @param nombreUsuario
     * @param correo
     * @param direccion
     * @param contra
     * @param callback
     */
    @Override
    public void createAcc(final String nombre, final String nombreUsuario, final String correo, final String direccion, final String contra, final Contract.CreateAccountCallback callback) {
        repository.createAcc(nombre,nombreUsuario,correo,direccion,contra,callback);
    }

    /**
     * Method that create a new user authentication
     * @param user
     * @param callback
     */


}

