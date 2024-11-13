package example.hello_world;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
// GPS
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Context;
import android.Manifest;
import androidx.core.app.ActivityCompat;
// Telegram Messages
import android.os.Handler;
import java.util.Calendar;



// UTILS
import android.util.Log;


public class SandboxFragment extends Fragment {

    private LocationManager locationManager;
    private TextView locationTextView;

    public SandboxFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sandbox, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        handleButtonPress();
        handleButtonPressTelegramSend();
        handleTelegramReceive();
        handleGPSUpdates();
        handleSendDummyNotification();
    }

    private void handleButtonPress() {
        Button clickMeButton = getView().findViewById(R.id.clickMeButton);
        TextView helloBazelTextView = getView().findViewById(R.id.helloBazelTextView);

        Greeter greeter = new Greeter();

        // Bazel supports Java 8 language features like lambdas!
        clickMeButton.setOnClickListener(v -> helloBazelTextView.setText(greeter.sayHello()));
    }

    private void handleButtonPressTelegramSend() {
        Button sendTelegramButton = getView().findViewById(R.id.sendTelegramTest);

        // Bazel supports Java 8 language features like lambdas!
        sendTelegramButton.setOnClickListener(v -> {
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                mainActivity.sendWebsocketMessage(Test2Interface.get_message_string(true, 243, Test2Interface.TestEnum.kTestEnumValue1));
            }
        });
    }

    private void handleSendDummyNotification() {
        Button sendDummyNotificationButton = getView().findViewById(R.id.sendDummyNotification);

        // Bazel supports Java 8 language features like lambdas!
        sendDummyNotificationButton.setOnClickListener(v -> {
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                mainActivity.sendNotification("Orm", "Test notification.", "StatusFragment");
            }
        });
    }

    private void handleTelegramReceive() {
        TextView receiveTelegramTextView = getView().findViewById(R.id.receiveTelegramTestText);

        Handler handler =new Handler();
        final Runnable r = new Runnable() {
                public void run() {
                    handler.postDelayed(this, 1000);
//                     String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
//                     new TelegramReceiveClient(res -> {receiveTelegramTextView.setText(res);}).execute();
//                     new Test3Interface().receive_message();
                    receiveTelegramTextView.setText(String.valueOf(Test3Interface.test_uint8));
                }
            };
            handler.postDelayed(r, 0000);
    }

    private void handleGPSUpdates() {
        Log.v("Fraikin Home Automation", "Handle GPS Updates");
        locationTextView = getView().findViewById(R.id.locationTextView);
        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);

        // Check permissions and start location updates if granted
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null && mainActivity.isLocationPermissionGranted()) {
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.v("Fraikin Home Automation", "startLocationUpdates");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, locationListener);
        }
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            locationTextView.setText("Latitude: " + latitude + "\nLongitude: " + longitude);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
        @Override
        public void onProviderEnabled(String provider) {}
        @Override
        public void onProviderDisabled(String provider) {}
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        }
    }
}
