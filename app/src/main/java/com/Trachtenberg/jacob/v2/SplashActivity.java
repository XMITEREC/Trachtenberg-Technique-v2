package com.Trachtenberg.jacob.v2;

import android.content.Intent;
import android.os.*;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Android 12+ gets system splash automatically; pre-12 we inflate ours
        if (Build.VERSION.SDK_INT < 31) {
            setContentView(R.layout.activity_splash);
            View seal = findViewById(R.id.seal);
            seal.setAlpha(0f);
            seal.animate().alpha(1f).setDuration(1200).withEndAction(
                    () -> startActivity(new Intent(this, MainActivity.class))
            );
        } else {
            new Handler(Looper.getMainLooper()).postDelayed(
                    () -> startActivity(new Intent(this, MainActivity.class)), 3000);
        }
    }
}
