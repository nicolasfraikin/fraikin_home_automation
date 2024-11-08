package example.hello_world;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.widget.Switch;
import android.content.SharedPreferences;
import android.content.Context;


// UTILS
import android.util.Log;


public class SettingsFragment extends Fragment {

    private Switch notificationsSwitch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        handleNewHousesNotification();
    }

    public void handleNewHousesNotification() {
        notificationsSwitch = getView().findViewById(R.id.houses_notifications_switch);

        // Load saved settings
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        boolean notificationsEnabled = sharedPreferences.getBoolean("houses_notifications_switch", false);

        notificationsSwitch.setChecked(notificationsEnabled);

        notificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save the boolean setting for notifications
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("houses_notifications_switch", isChecked);
            editor.apply();
        });
    }

}
