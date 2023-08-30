package com.example.androiddevbootcamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://androiddevbootcamp-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("data").child("image");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        SharedPreferences sp = getSharedPreferences("logged-in-user-data", MODE_PRIVATE);
        Toast.makeText(HomeActivity.this, "You are logged in as " + sp.getString("name", "no name"), Toast.LENGTH_LONG).show();

        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {

                        LinearLayout ll = findViewById(R.id.verticalShelf);

                        for (DataSnapshot child : task.getResult().getChildren()) {
                            String image_path = child.getValue(String.class);

                            ImageView iv = new ImageView(HomeActivity.this);

                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT, // Width
                                    LinearLayout.LayoutParams.WRAP_CONTENT  // Height
                            );
                            layoutParams.setMargins(0, 0, 0, 16);

                            iv.setLayoutParams(layoutParams);
                            iv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent toDescription = new Intent(HomeActivity.this, RegisterActivity.class);
                                    startActivity(toDescription);
                                }
                            });

                            RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);

                            Glide.with(HomeActivity.this)
                                    .load(image_path)
                                    .apply(requestOptions)
                                    .into(iv);

                            ll.addView(iv);

//                            Toast.makeText(HomeActivity.this, "dapet: " + a, Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        Toast.makeText(HomeActivity.this, "No image data to show", Toast.LENGTH_LONG).show();

                    }

                } else {
                    Toast.makeText(HomeActivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();

                }
            }


        });

    }
}
