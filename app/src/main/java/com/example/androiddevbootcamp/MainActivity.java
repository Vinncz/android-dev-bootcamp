package com.example.androiddevbootcamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;

import java.util.ResourceBundle;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        Button login = findViewById(R.id.loginButton);
        EditText emailField = findViewById(R.id.txtEmail);
        EditText passwordField = findViewById(R.id.txtPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {

                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();

                Boolean fails = false;

                if ( email.isEmpty() || email.isBlank() ) {
                    Toast.makeText(MainActivity.this, "Email field cannot be empty", Toast.LENGTH_LONG).show();
                    fails = true;

                } else if ( !email.contains("@") ) {
                    Toast.makeText(MainActivity.this, "Email field must contains '@' symbol", Toast.LENGTH_LONG).show();
                    fails = true;

                } else if ( !email.endsWith(".com") ) {
                    Toast.makeText(MainActivity.this, "Email field must ends with '.com'", Toast.LENGTH_LONG).show();
                    fails = true;

                } else if ( password.isEmpty() || password.isBlank() ) {
                    Toast.makeText(MainActivity.this, "Password field cannot be empty", Toast.LENGTH_LONG).show();
                    fails = true;

                } else if ( password.length() < 8 ) {
                    Toast.makeText(MainActivity.this, "Password must at least be 8 characters long", Toast.LENGTH_LONG).show();
                    fails = true;

                }

                if ( fails ) {
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "You are logged in as " + user.getEmail(), Toast.LENGTH_SHORT).show();

                            Intent toHome = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(toHome);
                            finish();

                        } else {
                            Toast.makeText(MainActivity.this, "Wrong credentials", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
            }
        });

        Button register = findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent toRegister = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(toRegister);
                finish();
            }
        });
    }
}

//import android.animation.ObjectAnimator;
//import android.os.Bundle;
//import android.view.View;
//import android.view.ViewTreeObserver;
//
//import androidx.appcompat.app.AppCompatActivity;

//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        View rootView = findViewById(android.R.id.content);
//        final float initialY = rootView.getY();
//
//        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                View rootView = findViewById(android.R.id.content);
//                float currentY = rootView.getY();
//                float diff = initialY - currentY;
//
//                if (diff > 100) {
//                    rootView.animate()
//                            .translationY(0)
//                            .setDuration(300)
//                            .start();
//                }
//
//                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            }
//        });
//    }
//}
