package example.hello_world;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.app.Activity;
import android.widget.ImageView;

import android.util.Log;



public class SplashActivity extends Activity {

    private static final int SPLASH_DISPLAY_LENGTH = 2000; // Duration in milliseconds


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        TextView welcomeText = findViewById(R.id.welcome_text);
        ImageView splashImage = findViewById(R.id.splash_image);

        Animation fadeInScale = AnimationUtils.loadAnimation(this, R.anim.fade_in_scale);
        splashImage.startAnimation(fadeInScale);
        welcomeText.startAnimation(fadeInScale);

        // Delay and navigate to Main Activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Navigate to MainActivity
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish(); // Finish SplashActivity to remove it from the back stack
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}
