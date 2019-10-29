package es.ulpgc.mesa.carlos.pem.App;

import android.util.Patterns;
import android.widget.EditText;

import androidx.annotation.NonNull;

public class Checker {
    public static boolean validateEmail(@NonNull EditText emailInput) {
        String email = emailInput.getText().toString().trim();
        if (email.isEmpty()) {
            emailInput.setError("Field cannot be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Please enter a valid email address");
            return false;
        } else {
            emailInput.setError(null);
            return true;
        }
    }

    public static boolean validatePassword(@NonNull EditText passwordInput) {
        String password = passwordInput.getText().toString().trim();

        if (password.isEmpty()) {
            passwordInput.setError("Field cannot be empty");
            return false;
        } else {
            passwordInput.setError(null);
            return true;
        }
    }

    public static boolean validateName(@NonNull EditText nameInput) {
        String name = nameInput.getText().toString().trim();

        if (name.isEmpty()) {
            nameInput.setError("Field cannot be empty");
            return false;
        } else {
            nameInput.setError(null);
            return true;
        }
    }
    public static boolean validateAddress(@NonNull EditText addressInput) {
        String address = addressInput.getText().toString().trim();

        if (address.isEmpty()) {
            addressInput.setError("Field cannot be empty");
            return false;
        } else {
            addressInput.setError(null);
            return true;
        }
    }
    public static boolean validateUsername(@NonNull EditText usernameInput) {
        String username = usernameInput.getText().toString().trim();

        if (username.isEmpty()) {
            usernameInput.setError("Field cannot be empty");
            return false;
        } else {
            usernameInput.setError(null);
            return true;
        }
    }

}

