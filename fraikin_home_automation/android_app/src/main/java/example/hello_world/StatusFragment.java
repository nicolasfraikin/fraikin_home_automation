package example.hello_world;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.os.Handler;


public class StatusFragment extends Fragment {

    public StatusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        handleNetworkStatus();
        handleDanboligHouseWatcherStatus();
        handleSmartHomeDeviceManagerStatus();
        handleVacuumRobotManagerStatus();
        handleChristmasLightManagerStatus();
        handleElectricityPriceWatcherStatus();
        handlePeopleAtHomeCheckerStatus();
        handleWhoIsHomeStatus();
    }

    private void handleNetworkStatus() {
        ImageView statusNetwork = getView().findViewById(R.id.status_network);
        Handler handler =new Handler();
        final Runnable r = new Runnable() {
                public void run() {
                    handler.postDelayed(this, 1000);
                    if (MyWebSocketClient.isConnected) {
                        statusNetwork.setImageResource(R.drawable.green_circle);
                    } else {
                        statusNetwork.setImageResource(R.drawable.red_circle);
                    }
                }
            };
            handler.postDelayed(r, 0000);
    }

    private void handleDanboligHouseWatcherStatus() {
        ImageView status = getView().findViewById(R.id.danbolig_house_watcher);
        Handler handler =new Handler();
        final Runnable r = new Runnable() {
                public void run() {
                    handler.postDelayed(this, 1000);
                    if (ModulesStatiInterface.danbolig_house_watcher_okay) {
                        status.setImageResource(R.drawable.green_circle);
                    } else {
                        status.setImageResource(R.drawable.red_circle);
                    }
                }
            };
            handler.postDelayed(r, 0000);
    }

    private void handleSmartHomeDeviceManagerStatus() {
        ImageView status = getView().findViewById(R.id.smart_home_device_manager);
        Handler handler =new Handler();
        final Runnable r = new Runnable() {
                public void run() {
                    handler.postDelayed(this, 1000);
                    if (ModulesStatiInterface.smart_home_device_manager_okay) {
                        status.setImageResource(R.drawable.green_circle);
                    } else {
                        status.setImageResource(R.drawable.red_circle);
                    }
                }
            };
            handler.postDelayed(r, 0000);
    }

    private void handleVacuumRobotManagerStatus() {
        ImageView status = getView().findViewById(R.id.vacuum_robot_manager);
        Handler handler =new Handler();
        final Runnable r = new Runnable() {
                public void run() {
                    handler.postDelayed(this, 1000);
                    if (ModulesStatiInterface.vacuum_robot_manager_okay) {
                        status.setImageResource(R.drawable.green_circle);
                    } else {
                        status.setImageResource(R.drawable.red_circle);
                    }
                }
            };
            handler.postDelayed(r, 0000);
    }

    private void handleChristmasLightManagerStatus() {
        ImageView status = getView().findViewById(R.id.christmas_light_manager);
        Handler handler =new Handler();
        final Runnable r = new Runnable() {
                public void run() {
                    handler.postDelayed(this, 1000);
                    if (ModulesStatiInterface.christmas_light_manager_okay) {
                        status.setImageResource(R.drawable.green_circle);
                    } else {
                        status.setImageResource(R.drawable.red_circle);
                    }
                }
            };
            handler.postDelayed(r, 0000);
    }

    private void handleElectricityPriceWatcherStatus() {
        ImageView status = getView().findViewById(R.id.electricity_price_watcher);
        Handler handler =new Handler();
        final Runnable r = new Runnable() {
                public void run() {
                    handler.postDelayed(this, 1000);
                    if (ModulesStatiInterface.electricity_price_watcher_okay) {
                        status.setImageResource(R.drawable.green_circle);
                    } else {
                        status.setImageResource(R.drawable.red_circle);
                    }
                }
            };
            handler.postDelayed(r, 0000);
    }

    private void handlePeopleAtHomeCheckerStatus() {
        ImageView status = getView().findViewById(R.id.people_at_home_checker);
        Handler handler =new Handler();
        final Runnable r = new Runnable() {
                public void run() {
                    handler.postDelayed(this, 1000);
                    if (ModulesStatiInterface.people_at_home_checker_okay) {
                        status.setImageResource(R.drawable.green_circle);
                    } else {
                        status.setImageResource(R.drawable.red_circle);
                    }
                }
            };
            handler.postDelayed(r, 0000);
    }

    private void handleWhoIsHomeStatus() {
        TextView whoIsHome = getView().findViewById(R.id.who_is_home);
        TextView whoIsHomeUpdated = getView().findViewById(R.id.who_is_home_updated);
        Handler handler =new Handler();
        final Runnable r = new Runnable() {
                public void run() {
                    handler.postDelayed(this, 1000);
                    String who_is_home = PeopleAtHomeInterface.people_at_home.replaceAll(";","\n");
                    whoIsHome.setText(who_is_home);
                    whoIsHomeUpdated.setText("Updated : " + PeopleAtHomeInterface.update_time);
                }
            };
            handler.postDelayed(r, 0000);
    }
}
