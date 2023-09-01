package com.example.androiddevbootcamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        Button register = findViewById(R.id.registerButton);

        EditText nameField = findViewById(R.id.txtName);
        EditText emailField = findViewById(R.id.txtEmail);
        EditText passwordField = findViewById(R.id.txtPassword);
        EditText confirmPasswordField = findViewById(R.id.txtConfirmPassword);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {

                String name = nameField.getText().toString();
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();
                String confirmPassword = confirmPasswordField.getText().toString();

                performRegistration(name, email, password, confirmPassword);
            }
        });


        Button login = findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent toLogin = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(toLogin);
                finish();
            }
        });
    }

    private boolean isNameValid(String name) {
        return !name.isEmpty() && !name.isBlank() && name.length() >= 5;
    }

    private boolean isEmailValid(String email) {
        return !email.isEmpty() && !email.isBlank() && email.contains("@") && email.endsWith(".com");
    }

    private boolean isPasswordValid(String password) {
        return !password.isEmpty() && !password.isBlank() && password.length() >= 8;
    }

    private boolean arePasswordsMatching(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    private void showErrorMessage(String message) {
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void performRegistration (String _name, String _email, String _password, String _cpass) {
        String name = _name;
        String email = _email;
        String password = _password;
        String confirmPassword = _cpass;

        if (!isNameValid(name)) {
            showErrorMessage("Name field cannot be empty and must be at least 5 characters long");

        } else if (!isEmailValid(email)) {
            showErrorMessage("Email field must not be empty, contain '@' symbol, and end with '.com'");

        } else if (!isPasswordValid(password)) {
            showErrorMessage("Password field must not be empty and must be at least 8 characters long");

        } else if (!arePasswordsMatching(password, confirmPassword)) {
            showErrorMessage("Password confirmation must match the original password");

        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(RegisterActivity.this, "You have registered as " + user.getEmail(), Toast.LENGTH_SHORT).show();

                                String uid = user.getUid();

                                SharedPreferences sp = getSharedPreferences("logged-in-user-data", MODE_PRIVATE);
                                SharedPreferences.Editor spe = sp.edit();

                                spe.putString(uid + ".name", name);
                                spe.putString(uid + ".email", email);
//                                spe.putString(uid + ".password", _password);

                                spe.apply();


                                Intent toHome = new Intent(RegisterActivity.this, HomeActivity.class);
                                startActivity(toHome);
                                finish();

                            } else {
                                Exception exception = task.getException();

                                if (exception instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(RegisterActivity.this, "Email address is already in use", Toast.LENGTH_SHORT).show();

                                } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                    Toast.makeText(RegisterActivity.this, "Invalid email or password format", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(RegisterActivity.this, "An error occurred: " + exception.getMessage(), Toast.LENGTH_SHORT).show();

                                }

                                return;
                            }
                        }
                    });

        }

        return;
    }

}
