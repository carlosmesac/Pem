package es.ulpgc.mesa.carlos.pem.Login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import es.ulpgc.mesa.carlos.pem.App.Checker;
import es.ulpgc.mesa.carlos.pem.R;

public class LoginActivity<email, password>
        extends AppCompatActivity implements LoginContract.View {

    public static String TAG = LoginActivity.class.getSimpleName();

    private LoginContract.Presenter presenter;
    //Declare an instance of FireBaseAuth
    private FirebaseAuth mAuth;
    //Declare button and editText
    private TextView signIn;
    private Button login;
    private EditText username, pass;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // do the setup
        // hiding the action bar

        // getting the FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // initializing the components of the view
        login = findViewById(R.id.loginButton);
        signIn = findViewById(R.id.sign);
        username = findViewById(R.id.usernameLogin);
        pass = findViewById(R.id.passLogin);

        // listener when Login button is clicked
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Checker.validateEmail(username) && Checker.validatePassword(pass)) {
                    String email = username.getText().toString();
                    String password = pass.getText().toString();
                    presenter.signIn(email, password);
                }
            }
        });

        // listener when register button is clicked
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.startSignInScreen();
            }
        });

        // do the setup
        LoginScreen.configure(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // load the data
        presenter.fetchData();
    }

    @Override
    public void displayData(LoginViewModel viewModel) {
        Toast.makeText(getApplicationContext(), viewModel.message, Toast.LENGTH_LONG).show();

    }

    @Override
    public void injectPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
