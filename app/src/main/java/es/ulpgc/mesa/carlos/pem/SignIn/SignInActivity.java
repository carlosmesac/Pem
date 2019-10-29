package es.ulpgc.mesa.carlos.pem.SignIn;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.ulpgc.mesa.carlos.pem.App.Checker;
import es.ulpgc.mesa.carlos.pem.R;

public class SignInActivity
        extends AppCompatActivity implements SignInContract.View {

    public static String TAG = SignInActivity.class.getSimpleName();

    private SignInContract.Model model;
    private SignInContract.Presenter presenter;
    private FirebaseAuth mAuth;
    private EditText name, username, email, pass, address;
    private Button login, newAcc;
    private DatabaseReference mDataBase;
    private int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        // do the setup
        mDataBase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://proyectopem-54056.firebaseio.com/");
        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.nameSignIn);
        email = findViewById(R.id.emailSignIn);
        username = findViewById(R.id.usernameSignIn);
        pass = findViewById(R.id.passSignIn);
        address = findViewById(R.id.addressSignIn);
        login = findViewById(R.id.buttonLoginSignIn);
        newAcc = findViewById(R.id.buttonSignIn);
        SignInScreen.configure(this);

        getSupportActionBar().hide();


        newAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Checker.validateName(name) && Checker.validateUsername(username) &&
                        Checker.validateEmail(email) && Checker.validateAddress(address) && Checker.validatePassword(pass)) {
                    String nombre = name.getText().toString();
                    String nombreUsuario = username.getText().toString();
                    String correo = email.getText().toString();
                    String direccion = address.getText().toString();
                    String contra = pass.getText().toString();
                    presenter.createAccount(nombre,nombreUsuario,correo,direccion,contra);


                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               presenter.goLoginScreen();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        // load the data
        presenter.fetchData();
    }

    @Override
    public void displayData(SignInViewModel viewModel) {
        //Display message user created
        Toast.makeText(getApplicationContext(), viewModel.message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void injectPresenter(SignInContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
