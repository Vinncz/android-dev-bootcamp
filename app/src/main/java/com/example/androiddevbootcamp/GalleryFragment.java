package com.example.androiddevbootcamp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GalleryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GalleryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GalleryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GalleryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GalleryFragment newInstance(String param1, String param2) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_gallery, container, false);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://androiddevbootcamp-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("data").child("images");

        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {

                        LinearLayout ll = v.findViewById(R.id.verticalShelf);

                        for (DataSnapshot child : task.getResult().getChildren()) {

                            /**
                             * "data": {
                             *     "images": [
                             *         {                 <-- variable foreach yg bernama "child" sekarang di level ini
                             *             "name": ...,
                             *             "desc": ...,
                             *             "path": ...
                             *         },
                             *         {
                             *             "name": ...,
                             *             "desc": ...,
                             *             "path": ...
                             *         }
                             *     ]
                             * }
                             */

                            String path = child.child("path").getValue(String.class);
                            String name = child.child("name").getValue(String.class);
                            String desc = child.child("desc").getValue(String.class);

                            /**
                             * "data": {
                             *     "images": [
                             *         {
                             *             "name": ...,
                             *             "desc": ...,
                             *             "path": ...      <-- String image_path ada di level ini
                             *         }
                             *     ]
                             * }
                             */

                            ImageView iv = new ImageView(v.getContext());

                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT, // Width
                                    LinearLayout.LayoutParams.WRAP_CONTENT  // Height
                            );
                            layoutParams.setMargins(0, 0, 0, 16);

                            iv.setLayoutParams(layoutParams);
                            iv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Bundle b = new Bundle();
                                    b.putString("name", name);
                                    b.putString("desc", desc);
                                    b.putString("path", path);

                                    DescriptionFragment df = new DescriptionFragment();
                                    df.setArguments(b);

                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.fragmentContainerView, df).addToBackStack(null).commit();
                                }
                            });

                            RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);

                            Glide.with(v.getContext())
                                    .load(path)
                                    .apply(requestOptions)
                                    .into(iv);

                            ll.addView(iv);

                        }

                    } else {
                        Toast.makeText(v.getContext(), "No image data to show", Toast.LENGTH_LONG).show();

                    }

                } else {
                    Toast.makeText(v.getContext(), task.getException().toString(), Toast.LENGTH_LONG).show();

                }
            }


        });

        return v;
    }
}
