package com.AdeebTechLab.JCTvN;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Dashboard extends AppCompatActivity {

    TextView emailDisplay, statusDisplay, packageDisplay, paymentDisplay, dateDisplay,
            paymentStatusDisplay, nameDisplay, phoneDisplay, townDisplay, addressDisplay, idDisplay, idCardDisplay, startDateDisplay, serverDisplay;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    SwipeRefreshLayout swipeRefreshLayout;
    Button logoutButton, paymentHistoryButton, complaintButton, dailyupdateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize Firebase Auth, Firestore, and UI elements
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        logoutButton = findViewById(R.id.logoutButton);
        paymentHistoryButton = findViewById(R.id.paymentHistoryButton);
        complaintButton = findViewById(R.id.complaintButton);
        dailyupdateButton = findViewById(R.id.dailyupdateButton);


        emailDisplay = findViewById(R.id.emailDisplay);
        statusDisplay = findViewById(R.id.statusDisplay);
        packageDisplay = findViewById(R.id.packageDisplay);
        paymentDisplay = findViewById(R.id.paymentDisplay);
        dateDisplay = findViewById(R.id.dateDisplay);
        paymentStatusDisplay = findViewById(R.id.paymentStatusDisplay);
        nameDisplay = findViewById(R.id.nameDisplay);
        phoneDisplay = findViewById(R.id.phoneDisplay);
        townDisplay = findViewById(R.id.townDisplay);
        addressDisplay = findViewById(R.id.addressDisplay);
        idDisplay = findViewById(R.id.idDisplay);
        idCardDisplay = findViewById(R.id.idCardDisplay);
        startDateDisplay = findViewById(R.id.startDateDisplay);
        serverDisplay = findViewById(R.id.serverDisplay);

        // Load user data initially
        loadUserData();

        // SwipeRefreshLayout Listener
        swipeRefreshLayout.setOnRefreshListener(() -> {
            loadUserData();
            swipeRefreshLayout.setRefreshing(false);
        });

        // Logout Button Listener
        logoutButton.setOnClickListener(view -> {
            mAuth.signOut();
            Toast.makeText(Dashboard.this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Dashboard.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Payment History Button Listener
        paymentHistoryButton.setOnClickListener(view -> {
            Intent intent = new Intent(Dashboard.this, PaymentBackup.class);
            startActivity(intent);
        });

        // Complaint Button Listener (Open Google)
        complaintButton.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/923025403149?text=Hi%20Sir%20I%20use%20your%20app%20and%20I%20need%20Help"));
            startActivity(browserIntent);
        });

        // Daily Update Button Listener (Open YouTube)
        dailyupdateButton.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://whatsapp.com/channel/0029VbARyhFHbFVAN6vFsT0z"));
            startActivity(browserIntent);
        });
    }

    private void loadUserData() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail().toLowerCase();
            emailDisplay.setText("Email: " + userEmail);

            // Fetch user data from Firestore
            db.collection("users").document(userEmail)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("Name");
                            String phone = documentSnapshot.getString("Phone");
                            String town = documentSnapshot.getString("Town");
                            String address = documentSnapshot.getString("Address");
                            String id = documentSnapshot.getString("Id");
                            String idCard = documentSnapshot.getString("ID Card");
                            String accountStatus = documentSnapshot.getString("Account Status");
                            String userPackage = documentSnapshot.getString("Net Speed");
                            Long netPayment = documentSnapshot.getLong("Due Amount");
                            String paymentDate = documentSnapshot.getString("Due Date");
                            String paymentStatus = documentSnapshot.getString("Payment Status");
                            String server = documentSnapshot.getString("Server");
                            serverDisplay.setText("" + server);
                            startDateDisplay.setText("" + paymentDate);

                            // Display data
                            nameDisplay.setText("Name: " + name);
                            phoneDisplay.setText("Phone: " + phone);
                            townDisplay.setText("Town: " + town);
                            addressDisplay.setText("Address: " + address);
                            idDisplay.setText("ID: " + id);
                            idCardDisplay.setText("ID Card: " + idCard);
                            statusDisplay.setText("" + accountStatus);
                            packageDisplay.setText("" + userPackage);
                            paymentDisplay.setText("PKR " + netPayment);
                            dateDisplay.setText("" + paymentDate);
                            paymentStatusDisplay.setText("" + paymentStatus);
                        } else {
                            Toast.makeText(Dashboard.this, "No user data found", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(Dashboard.this, "Failed to fetch data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(Dashboard.this, "User is not logged in", Toast.LENGTH_SHORT).show();
        }
    }
}
