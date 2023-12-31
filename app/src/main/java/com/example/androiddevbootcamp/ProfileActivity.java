package com.example.androiddevbootcamp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) return;

        String uid = user.getUid();

        SharedPreferences sp = getSharedPreferences("logged-in-user-data", MODE_PRIVATE);
        String name = sp.getString(uid + ".name", "no name");
        String email = sp.getString(uid + ".email", "unknown email");

        TextView tvName = findViewById(R.id.name);
        tvName.setText(name);

        TextView tvEmail = findViewById(R.id.email);
        tvEmail.setText(email);

        Button lo = findViewById(R.id.logoutButton);
        lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent i = new Intent(ProfileActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(i);
            }
        });
    }
}
