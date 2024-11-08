package example.hello_world;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.app.Activity;
import android.widget.ImageView;

import android.content.Intent;
import android.content.Context;
import android.app.ActivityManager;

import android.util.Log;



public class SplashActivity extends Activity {

    private static final int SPLASH_DISPLAY_LENGTH = 2000; // Duration in milliseconds
    Intent backgroundService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        TextView welcomeText = findViewById(R.id.welcome_text);
        ImageView splashImage = findViewById(R.id.splash_image);

        Animation fadeInScale = AnimationUtils.loadAnimation(this, R.anim.fade_in_scale);
        splashImage.startAnimation(fadeInScale);
        welcomeText.startAnimation(fadeInScale);

        backgroundService = new Intent(this, BackgroundService.class);
        if (!isMyServiceRunning(backgroundService.getClass())) {
            startService(backgroundService);
            Log.v("Fraikin Home Automation", "Start service background");
        }

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


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        Log.v("Fraikin Home Automation", "onDestroy Splash Act!");
        stopService(backgroundService);
        super.onDestroy();

    }
}
