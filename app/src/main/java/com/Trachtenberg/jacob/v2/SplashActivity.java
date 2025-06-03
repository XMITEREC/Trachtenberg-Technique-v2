package com.Trachtenberg.jacob.v2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Kodular Clock1 → 3-second delay
        findViewById(android.R.id.content).postDelayed(
                () -> startActivity(new Intent(this, MainActivity.class)),
                3000
        );
    }
}
