package com.example.foodex.data;

import static android.content.ContentValues.TAG;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserDAO {
    private static UserDAO instance;
    private MutableLiveData<String> authenticationMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> progressBar = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> completed = new MutableLiveData<>(false);
    private MutableLiveData<String> email = new MutableLiveData<>();
    private MutableLiveData<String> fullName = new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> favorites = new MutableLiveData<>();
    private String userId;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    public UserDAO() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public MutableLiveData<ArrayList<String>> getFavoritesList()
    {
        return favorites;
    }

    public MutableLiveData<String> getAuthenticationMessage() {
        return authenticationMessage;
    }

    public MutableLiveData<Boolean> getProgressBar() {
        return progressBar;
    }

    public MutableLiveData<Boolean> getCompleted() {
        return completed;
    }


    public void register(String email, String password, String fullName) {
        progressBar.setValue(true);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User user = new User(fullName, email);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "RegisterUserWithEmail:success");
                                        completed.setValue(true);

                                        progressBar.setValue(false);
                                        authenticationMessage.postValue("User has been registered successfully!");
                                    } else {
                                        Log.w(TAG, "RegisterUserWithEmail:failure", task.getException());
                                        authenticationMessage.postValue("Failed to register! Try again!");
                                        progressBar.setValue(false);
                                    }
                                }
                            });
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    progressBar.setValue(false);
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                }
            }
        });

    }

    public void login(String email, String password) {

        progressBar.setValue(true);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Log.d(TAG, "signInUserWithEmail:success");
                    completed.setValue(true);

                    progressBar.setValue(false);
                    authenticationMessage.postValue("User has been logged in successfully!");
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    authenticationMessage.postValue("Failed to login! Please check your credentials!");
                    progressBar.setValue(false);
                }
            }
        });
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        completed.setValue(false);
    }

    public void resetPassword(String email) {
        progressBar.setValue(true);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    authenticationMessage.postValue("Check your email to reset your password!");
                    progressBar.setValue(false);
                } else {
                    authenticationMessage.postValue("Try again! Something went wrong!");
                    progressBar.setValue(false);
                }
            }
        });
    }

    public MutableLiveData<String> getFullName() {
        return fullName;
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public void getProfile() {
        user = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();

        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    fullName.postValue(userProfile.fullName);
                    email.postValue(userProfile.email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                authenticationMessage.postValue("Something went wrong!");
            }
        });
    }

    public void addFavorite(String id) {
        user = mAuth.getCurrentUser();
        userId = user.getUid();

        databaseReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("favorites").push().setValue(id).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressBar.setValue(false);
                    authenticationMessage.setValue("Recipe has been added to your favorites list!");
                    completed.setValue(true);
                } else {
                    progressBar.setValue(false);
                    authenticationMessage.setValue("Something went wrong while adding this recipe to your favorites!");
                }
            }
        });
    }

    public void addIngredient(String name, String image)
    {
        user = mAuth.getCurrentUser();
        userId = user.getUid();

        databaseReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pantry").child(name).setValue(image).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressBar.setValue(false);
                    authenticationMessage.setValue("Ingredient has been added to your Pantry!");
                    completed.setValue(true);
                } else {
                    progressBar.setValue(false);
                    authenticationMessage.setValue("Something went wrong while adding this ingredient to your Pantry!");
                }
            }
        });
    }

    public void getFavorites() {

        ArrayList<String> tempFavorites = new ArrayList<>();
        databaseReference.child("Users").child(FirebaseAuth.getInstance().getUid()).child("favorites").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    tempFavorites.add(dataSnapshot.getValue().toString());
                }
                favorites.setValue(tempFavorites);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                authenticationMessage.setValue("Something went wrong while getting your favorites!");
            }
        });

    }

}