package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    Button login;
    Button signup;
    EditText em;
    String email;
    EditText pass;
    String password;
    Intent intent;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        signup = findViewById(R.id.signup);
        login = findViewById(R.id.login);
        em = findViewById(R.id.email);
        pass = findViewById(R.id.password);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempeEmail = em.getText().toString();
                String tempepPassword = pass.getText().toString();
                if (TextUtils.isEmpty(tempeEmail) || TextUtils.isEmpty(tempepPassword)) {
                    Toast.makeText(MainActivity.this, "Please fill password/email input", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    email = tempeEmail;
                    password = tempepPassword;
                    signUp();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempeEmail = em.getText().toString();
                String tempepPassword = pass.getText().toString();
                if (TextUtils.isEmpty(tempeEmail) || TextUtils.isEmpty(tempepPassword)) {
                    Toast.makeText(MainActivity.this, "Please fill passord/email input", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    email = tempeEmail;
                    password = tempepPassword;
                    logIn();

                }
            }
        });

    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    void logIn() {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("good", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Bad", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getBaseContext(), task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            intent = new Intent(getBaseContext(), Main2Activity.class);
            startActivity(intent);
            finish();
        }
    }

    void signUp() {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("good", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getBaseContext(), "add to FireBase", Toast.LENGTH_LONG);
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Bad", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getBaseContext(), task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }


}
