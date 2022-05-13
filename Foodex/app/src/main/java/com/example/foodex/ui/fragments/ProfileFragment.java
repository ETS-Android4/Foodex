package com.example.foodex.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodex.R;
import com.example.foodex.data.User;
import com.example.foodex.ui.LoginActivity;
import com.example.foodex.viewmodel.ProfileViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    User user;
    TextView email, fullName;
    ProgressBar progressBar;
    Button logout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        logout = view.findViewById(R.id.signOut);
        email = view.findViewById(R.id.email);
        fullName = view.findViewById(R.id.fullName);
        progressBar = view.findViewById(R.id.progressBar);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        profileViewModel.getFullName().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                fullName.setText(s);
            }
        });

        profileViewModel.getEmail().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                email.setText(s);
            }
        });

        profileViewModel.getProfile();
        return view;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
//        user = null;
//
//        if (logout != null) {
//            logout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    profileViewModel.signOut();
//                }
//            });
//        }

//        profileViewModel.getProgressBar().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean aBoolean) {
//                if(!aBoolean)
//                {
//                    int visibility = View.INVISIBLE;
//                    progressBar.setVisibility(View.GONE);
//                }
//            }
//        });
//
//        profileViewModel.getCompleted().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean aBoolean) {
//                if(aBoolean)
//                {
//                    Intent intent = new Intent(getActivity(), LoginActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });



//        profileViewModel.getCurrentUser().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
//            @Override
//            public void onChanged(FirebaseUser firebaseUser) {
//                if(firebaseUser != null)
//                {
//                    profileViewModel.setUser(firebaseUser.getUid());
//                }
//            }
//        });
//
//        profileViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onChanged(User user) {
//                if(user != null)
//                {
//                    email.setText(user.getEmail());
//                    name.setText(user.getFullName());
//                    username.setText(user.getUserName());
//                }
//            }
//        });
    }
