// MainActivity.java
package example.hello_world;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.os.Bundle;
import androidx.core.view.GravityCompat;
import android.util.Log;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.google.android.material.navigation.NavigationView;
// GPS
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Context;
import android.Manifest;
import androidx.core.app.ActivityCompat;
// Notifications
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.app.Notification;
import android.content.Context;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.content.Intent;
import android.app.PendingIntent;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean isLocationPermissionGranted = false;
    private static final String CHANNEL_ID = "default_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("Fraikin Home Automation", "Welcome to Fraikin Home Automation");

        createNotificationChannel();
        checkLocationPermission();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        // Set up ActionBar toggle for the drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Load HomeFragment as the default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        if (getIntent() != null) {
            openTargetFragment(getIntent().getStringExtra("targetFragment"));
        }

        // Set up item selection for the drawer
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment selectedFragment = null;

                int item_id = item.getItemId();
                if (item_id==R.id.nav_home) {
                    selectedFragment = new HomeFragment();
                } else if (item_id==R.id.nav_status) {
                    selectedFragment = new StatusFragment();
                } else if (item_id==R.id.nav_debug) {
                    selectedFragment = new DebugFragment();
                } else if (item_id==R.id.nav_sandbox) {
                    selectedFragment = new SandboxFragment();
                } else if (item_id==R.id.nav_settings) {
                    selectedFragment = new SettingsFragment();
                } else {
                    selectedFragment = new HomeFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });

        // Setup websocket connection
        MyWebSocketClient webSocketClient = new MyWebSocketClient();
        webSocketClient.connect();
        webSocketClient.sendMessage("Hello from Android!");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            isLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            isLocationPermissionGranted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }

    public boolean isLocationPermissionGranted() {
        return isLocationPermissionGranted;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Default Channel";
            String description = "Channel for default notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    // Method to send a notification
    public void sendNotification(String title, String message, String targetFragment) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("targetFragment", targetFragment); // Pass the target fragment's name
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.splash_screen)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        openTargetFragment(intent.getStringExtra("targetFragment"));
    }

    // Method to load the specified fragment
    private void openTargetFragment(String targetFragment) {
        if (targetFragment != null) {
            Fragment selectedFragment;
            if (targetFragment.equals("HomeFragment")) {
                selectedFragment = new HomeFragment();
            } else if (targetFragment.equals("StatusFragment")) {
                selectedFragment = new StatusFragment();
            } else if (targetFragment.equals("DebugFragment")) {
                selectedFragment = new DebugFragment();
            } else if (targetFragment.equals("SandboxFragment")) {
                selectedFragment = new SandboxFragment();
            } else if (targetFragment.equals("SettingsFragment")) {
                selectedFragment = new SettingsFragment();
            } else {
                selectedFragment = new HomeFragment();
            }
            Log.v("Fraikin Home Automation", "Target fragment is " + targetFragment);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
        }
    }
}
