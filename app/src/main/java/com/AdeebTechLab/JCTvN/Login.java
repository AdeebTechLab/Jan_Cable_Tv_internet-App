package com.AdeebTechLab.JCTvN;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.Executor;

public class Login extends AppCompatActivity {

    // UI Components
    TextInputEditText email, password;
    Button loginbt;
    Switch biometricSwitch;
    LottieAnimationView loadingAnimation;
    TextView signUpRedirect;

    // Firebase
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Bind Views
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginbt = findViewById(R.id.loginbt);
        biometricSwitch = findViewById(R.id.biometricSwitch);
        loadingAnimation = findViewById(R.id.loadingAnimation);
        signUpRedirect = findViewById(R.id.loginNow);

        // Biometric Setup
        boolean isBiometricEnabled = sharedPreferences.getBoolean("isBiometricEnabled", false);
        if (isBiometricEnabled) {
            showBiometricPrompt();
        }

        // Redirect to Sign-Up Screen
        signUpRedirect.setOnClickListener(view -> {
            startActivity(new Intent(Login.this, SignUp.class));
            finish();
        });

        // Handle Login
        loginbt.setOnClickListener(view -> {
            loadingAnimation.setVisibility(View.VISIBLE);

            String userEmail = email.getText().toString().trim();
            String userPassword = password.getText().toString();

            if (TextUtils.isEmpty(userEmail)) {
                email.setError("Email is required");
                loadingAnimation.setVisibility(View.GONE);
                return;
            }
            if (TextUtils.isEmpty(userPassword)) {
                password.setError("Password is required");
                loadingAnimation.setVisibility(View.GONE);
                return;
            }

            mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(task -> {
                        loadingAnimation.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            saveCredentials(userEmail, userPassword);
                            redirectToDashboard();
                        } else {
                            Toast.makeText(Login.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        biometricSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferences.edit().putBoolean("isBiometricEnabled", isChecked).apply();
            Toast.makeText(this, "Biometric login " + (isChecked ? "enabled" : "disabled") + ".", Toast.LENGTH_SHORT).show();
        });
    }

    private void showBiometricPrompt() {
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(Login.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                autoLogin();
            }

            @Override
            public void onAuthenticationFailed() {
                Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login with Fingerprint")
                .setSubtitle("Authenticate using your fingerprint")
                .setNegativeButtonText("Cancel")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }

    private void autoLogin() {
        String userEmail = sharedPreferences.getString("userEmail", null);
        String userPassword = sharedPreferences.getString("userPassword", null);

        if (userEmail != null && userPassword != null) {
            mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            redirectToDashboard();
                        } else {
                            Toast.makeText(Login.this, "Auto login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void saveCredentials(String email, String password) {
        sharedPreferences.edit()
                .putString("userEmail", email)
                .putString("userPassword", password)
                .apply();
    }

    private void redirectToDashboard() {
        Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Login.this, Dashboard.class));
        finish();
    }
}