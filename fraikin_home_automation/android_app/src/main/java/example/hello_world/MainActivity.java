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
// Allow to run in background
import android.net.Uri;
import android.provider.Settings;
import android.content.Intent;
import android.content.Context;
import android.app.ActivityManager;
// Bluetooth permissions
import android.content.pm.PackageManager;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean isLocationPermissionGranted = false;
    private boolean isBluetoothPermissionGranted = false;
    private static final String CHANNEL_ID = "default_channel";
    static MyWebSocketClient webSocketClient = new MyWebSocketClient();
    Intent backgroundService;
    private static final int REQUEST_BLUETOOTH_PERMISSIONS = 1;


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
                } else if (item_id==R.id.nav_smart_home) {
                    selectedFragment = new SmartHomeFragment();
                } else if (item_id==R.id.nav_sandbox) {
                    selectedFragment = new SandboxFragment();
                } else if (item_id==R.id.nav_settings) {
                    selectedFragment = new SettingsFragment();
                } else if (item_id==R.id.nav_flight_control) {
                    selectedFragment = new FlightControlFragment();
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
        connectWebsocket();
        allowToRunInBackground();

        // Start backgroundService
        BackgroundService.setActivityContext(this);
        backgroundService = new Intent(this, BackgroundService.class);
        if (!isMyServiceRunning(backgroundService.getClass())) {
            startService(backgroundService);
            Log.v("Fraikin Home Automation", "Start service background");
        }

        // Check bluetooth permission
        checkBluetoothPermissions();

    }

    private void checkBluetoothPermissions() {
        String[] permissions = {
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN
        };

        isBluetoothPermissionGranted = true;
        for (String permission : permissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                isBluetoothPermissionGranted = false;
                break;
            }
        }

        if (!isBluetoothPermissionGranted) {
            requestPermissions(permissions, REQUEST_BLUETOOTH_PERMISSIONS);
        }
    }

    public static void connectWebsocket() {
        webSocketClient.connect();
        webSocketClient.sendMessage("Hello from Android!");
    }

    private void allowToRunInBackground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        }
    }

    public static void sendWebsocketMessage(String text) {
        webSocketClient.sendMessage(text);
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
        if (requestCode == REQUEST_BLUETOOTH_PERMISSIONS) {
            isBluetoothPermissionGranted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }

    public boolean isLocationPermissionGranted() {
        return isLocationPermissionGranted;
    }

    public boolean isBluetoothPermissionGranted() {
        return isBluetoothPermissionGranted;
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
    public static void sendNotification(Context context, String title, String message, String targetFragment) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("targetFragment", targetFragment); // Pass the target fragment's name
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.splash_screen)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
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
            } else if (targetFragment.equals("SmartHomeFragment")) {
                selectedFragment = new SmartHomeFragment();
            } else if (targetFragment.equals("SandboxFragment")) {
                selectedFragment = new SandboxFragment();
            } else if (targetFragment.equals("SettingsFragment")) {
                selectedFragment = new SettingsFragment();
            } else if (targetFragment.equals("FlightControlFragment")) {
                selectedFragment = new FlightControlFragment();
            } else {
                selectedFragment = new HomeFragment();
            }
            Log.v("Fraikin Home Automation", "Target fragment is " + targetFragment);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
        }
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
    public void LockDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void UnlockDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    protected void onDestroy() {
        Log.v("Fraikin Home Automation", "onDestroy MainActivity!");
        stopService(backgroundService);
        super.onDestroy();

    }
}
