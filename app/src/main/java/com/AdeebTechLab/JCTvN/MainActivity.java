package com.AdeebTechLab.JCTvN;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;

public class MainActivity extends AppCompatActivity {

    // OneSignal App ID
    private static final String ONESIGNAL_APP_ID = "82597716-05fe-4bdb-83da-7bc5b81c315b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize OneSignal Notifications
        try {
            OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);
            OneSignal.initWithContext(this, ONESIGNAL_APP_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Navigate to Login after a delay
        findViewById(R.id.main).postDelayed(() -> {
            startActivity(new Intent(MainActivity.this, Login.class));
            finish(); // Close splash activity to prevent back navigation
        }, 3000); // 3-second delay
    }
}