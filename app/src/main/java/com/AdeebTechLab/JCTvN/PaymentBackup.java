package com.AdeebTechLab.JCTvN;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class PaymentBackup extends AppCompatActivity {

    // Firebase Auth and Firestore instances
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    // TextViews to display "P1", "P2", and "P3"
    private TextView p1Display, p2Display, p3Display, p4Display, p5Display, p6Display, p7Display, p8Display, p9Display, p10Display, p11Display, p12Display;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_backup);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize TextViews
        p1Display = findViewById(R.id.p1Display); // TextView for P1
        p2Display = findViewById(R.id.p2Display); // TextView for P2
        p3Display = findViewById(R.id.p3Display); // TextView for P3
        p4Display = findViewById(R.id.p4Display);
        p5Display = findViewById(R.id.p5Display);
        p6Display = findViewById(R.id.p6Display);
        p7Display = findViewById(R.id.p7Display);
        p8Display = findViewById(R.id.p8Display);
        p9Display = findViewById(R.id.p9Display);
        p10Display = findViewById(R.id.p10Display);
        p11Display = findViewById(R.id.p11Display);
        p12Display = findViewById(R.id.p12Display);


        if (p1Display == null || p2Display == null || p3Display == null) {
            Log.e("InitializationError", "One or more TextViews with IDs 'p1Display', 'p2Display', 'p3Display' not found in XML.");
            return; // Exit if TextViews are not properly initialized
        }

        // Load user data
        loadUserData();
    }

    private void loadUserData() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            if (userEmail != null) {
                userEmail = userEmail.toLowerCase();

                // Fetch "P1", "P2", and "P3" fields from Firestore
                db.collection("users").document(userEmail)
                        .get()
                        .addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                String p1 = documentSnapshot.getString("P1");
                                String p2 = documentSnapshot.getString("P2");
                                String p3 = documentSnapshot.getString("P3");
                                String p4 = documentSnapshot.getString("P4");
                                String p5 = documentSnapshot.getString("P5");
                                String p6 = documentSnapshot.getString("P6");
                                String p7 = documentSnapshot.getString("P7");
                                String p8 = documentSnapshot.getString("P8");
                                String p9 = documentSnapshot.getString("P9");
                                String p10 = documentSnapshot.getString("P10");
                                String p11 = documentSnapshot.getString("P11");
                                String p12 = documentSnapshot.getString("P12");

                                if (p1 != null) {
                                    p1Display.setText("P1: " + p1); // Display P1 value
                                } else {
                                    p1Display.setText(""); // Handle missing P1 field
                                    Log.w("PaymentBackup", "P1 field is missing in Firestore");
                                }

                                if (p2 != null) {
                                    p2Display.setText("P2: " + p2); // Display P2 value
                                } else {
                                    p2Display.setText(""); // Handle missing P2 field
                                    Log.w("PaymentBackup", "P2 field is missing in Firestore");
                                }

                                if (p3 != null) {
                                    p3Display.setText("P3: " + p3); // Display P3 value
                                } else {
                                    p3Display.setText(""); // Handle missing P3 field
                                    Log.w("PaymentBackup", "P3 field is missing in Firestore");
                                }
                                if (p4 != null) {
                                    p4Display.setText("P4: " + p4); // Display P3 value
                                } else {
                                    p4Display.setText(""); // Handle missing P3 field
                                    Log.w("PaymentBackup", "P3 field is missing in Firestore");

                                }
                                if (p5 != null) {
                                    p5Display.setText("P5: " + p5); // Display P3 value
                                } else {
                                    p5Display.setText(""); // Handle missing P3 field
                                    Log.w("PaymentBackup", "P3 field is missing in Firestore");
                                }
                                if (p6 != null) {
                                    p6Display.setText("P6: " + p6); // Display P3 value
                                } else {
                                    p6Display.setText(""); // Handle missing P3 field
                                    Log.w("PaymentBackup", "P3 field is missing in Firestore");

                                }
                                if (p7 != null) {
                                    p7Display.setText("P7: " + p7); // Display P3 value
                                } else {
                                    p7Display.setText(""); // Handle missing P3 field
                                    Log.w("PaymentBackup", "P3 field is missing in Firestore");

                                }
                                if (p8 != null) {
                                    p8Display.setText("P8: " + p8); // Display P3 value
                                } else {
                                    p8Display.setText(""); // Handle missing P3 field
                                    Log.w("PaymentBackup", "P3 field is missing in Firestore");
                                }
                                if (p9 != null) {
                                    p9Display.setText("P9: " + p9); // Display P3 value
                                } else {
                                    p9Display.setText(""); // Handle missing P3 field
                                    Log.w("PaymentBackup", "P3 field is missing in Firestore");

                                }
                                if (p10 != null) {
                                    p10Display.setText("P10: " + p10); // Display P3 value
                                } else {
                                    p10Display.setText(""); // Handle missing P3 field
                                    Log.w("PaymentBackup", "P10 field is missing in Firestore");

                                }
                                if (p11 != null) {
                                    p11Display.setText("P11: " + p11); // Display P3 value
                                } else {
                                    p11Display.setText(""); // Handle missing P3 field
                                    Log.w("PaymentBackup", "P11 field is missing in Firestore");

                                }
                                if (p12 != null) {
                                    p12Display.setText("P12: " + p12); // Display P3 value
                                } else {
                                    p12Display.setText(""); // Handle missing P3 field
                                    Log.w("PaymentBackup", "P12 field is missing in Firestore");

                                }
                            }

                            else {
                                Toast.makeText(PaymentBackup.this, "No user data found", Toast.LENGTH_SHORT).show();
                                p1Display.setText("Document not found");
                                p2Display.setText("Document not found");
                                p3Display.setText("Document not found");
                                p4Display.setText("Document not found");
                                p5Display.setText("Document not found");
                                p6Display.setText("Document not found");
                                p7Display.setText("Document not found");
                                p8Display.setText("Document not found");
                                p9Display.setText("Document not found");
                                p10Display.setText("Document not found");
                                p11Display.setText("Document not found");
                                p12Display.setText("Document not found");

                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(PaymentBackup.this, "Failed to fetch data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("PaymentBackup", "Error fetching Firestore document", e);
                        });
            } else {
                Toast.makeText(this, "User email is null. Cannot fetch data.", Toast.LENGTH_SHORT).show();
                Log.e("UserError", "Current user's email is null.");
            }
        } else {
            Toast.makeText(this, "User is not logged in", Toast.LENGTH_SHORT).show();
            Log.e("UserError", "No user logged in.");
        }
    }
}
