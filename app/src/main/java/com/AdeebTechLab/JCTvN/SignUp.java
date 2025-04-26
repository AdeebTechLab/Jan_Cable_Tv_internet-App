package com.AdeebTechLab.JCTvN;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    TextInputEditText email, password, repassword, name, phone, town, address, idCard; // Added idCard
    Button signup;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    TextView loginNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Bind Views
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        town = findViewById(R.id.town);
        address = findViewById(R.id.address);
        idCard = findViewById(R.id.id_card); // Bind new field
        signup = findViewById(R.id.signup);
        loginNow = findViewById(R.id.loginNow);

        // Redirect to Login Screen
        loginNow.setOnClickListener(view -> {
            startActivity(new Intent(SignUp.this, Login.class));
            finish();
        });

        // Handle Signup Logic
        signup.setOnClickListener(view -> {
            String userEmail = email.getText().toString().trim().toLowerCase();
            String userPassword = password.getText().toString();
            String userRePassword = repassword.getText().toString();
            String userName = name.getText().toString().trim();
            String userPhone = phone.getText().toString().trim();
            String userTown = town.getText().toString().trim();
            String userAddress = address.getText().toString().trim();
            String userIdCard = idCard.getText().toString().trim(); // Get ID Card input

            // Input validation
            if (TextUtils.isEmpty(userEmail)) {
                email.setError("Email is required");
                return;
            }
            if (TextUtils.isEmpty(userPassword)) {
                password.setError("Password is required");
                return;
            }
            if (TextUtils.isEmpty(userRePassword)) {
                repassword.setError("Re-enter password is required");
                return;
            }
            if (!userPassword.equals(userRePassword)) {
                repassword.setError("Passwords do not match");
                return;
            }
            if (TextUtils.isEmpty(userName)) {
                name.setError("Name is required");
                return;
            }
            if (TextUtils.isEmpty(userPhone)) {
                phone.setError("Phone is required");
                return;
            }
            if (TextUtils.isEmpty(userTown)) {
                town.setError("Town is required");
                return;
            }
            if (TextUtils.isEmpty(userAddress)) {
                address.setError("Address is required");
                return;
            }
            if (TextUtils.isEmpty(userIdCard)) { // Validate ID Card
                idCard.setError("ID Card is required");
                return;
            }

            // Check if email already exists in Firebase Authentication
            mAuth.fetchSignInMethodsForEmail(userEmail)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            if (task.getResult().getSignInMethods() != null && !task.getResult().getSignInMethods().isEmpty()) {
                                Toast.makeText(SignUp.this, "Email already exists!", Toast.LENGTH_SHORT).show();
                            } else {
                                // Create user account in Firebase Authentication
                                mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                                        .addOnCompleteListener(signupTask -> {
                                            if (signupTask.isSuccessful()) {
                                                // Save user details to Firestore
                                                saveUserToFirestore(userEmail, userName, userPhone, userTown, userAddress, userIdCard);
                                            } else {
                                                Toast.makeText(SignUp.this, "Authentication failed: " + signupTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(SignUp.this, "Failed to validate email: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    private void saveUserToFirestore(String userEmail, String userName, String userPhone, String userTown, String userAddress, String userIdCard) {
        // Create a user document in Firestore
        Map<String, Object> user = new HashMap<>();
        user.put("Email", userEmail);
        user.put("Name", userName);
        user.put("Phone", userPhone);
        user.put("Town", userTown);
        user.put("Address", userAddress);
        user.put("ID Card", userIdCard); // Add ID Card field
        user.put("isAdmin", false); // Default role for new users

        // Package Details
        user.put("Id", "Updating...");
        user.put("Net Speed", "Updating...");
        user.put("Account Status", "Updating...");
        user.put("Start Date", "Updating...");
        user.put("Server", "Updating...");

        // Payment Details
        user.put("Due Amount", 0);
        user.put("Due Date", "Updating...");
        user.put("Payment Status", "Updating...");

        // Save data in "users" collection
        db.collection("users").document(userEmail)
                .set(user)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(SignUp.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUp.this, Login.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(SignUp.this, "Failed to save user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
