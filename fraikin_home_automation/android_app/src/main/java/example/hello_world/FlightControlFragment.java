package example.hello_world;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Handler;
import android.widget.Switch;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.SeekBar;

import android.content.pm.ActivityInfo;
import android.view.WindowInsetsController;


import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class FlightControlFragment extends Fragment {

    private static final String TAG = "FlightControl";
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private boolean connectedToDevice = false;
    private boolean inFullscreenMode = false;
    private boolean wheelsIn = false;
    private boolean flyTurbineMove = false;
    private boolean flyLoopingMove = false;
    private boolean performCheckup = false;
    private boolean steeringByPropellers = false;
    private boolean lightsOn = false;
    private int previousOrientation;


    public FlightControlFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_flight_control, container, false);
        SeekBar seekBar = view.findViewById(R.id.speed_control);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null && mainActivity.isBluetoothPermissionGranted()) {
            previousOrientation = requireActivity().getRequestedOrientation();
            requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            handleFullScreenMode(mainActivity);
            handleBluetoothStatus();
            receiveBluetoothData();
            handleSpeedControl();
            handleAltitudeControl();
            handleSteeringControl();
            // Switches
            handleWheelsSwitch();
            handleTurbineSwitch();
            handleLoopingSwitch();
            handleCheckupSwitch();
            handleSteeringSwitch();
            handleLightsSwitch();
        }
    }

    private void handleSpeedControl() {
        SeekBar speedControl = getView().findViewById(R.id.speed_control);
        TextView speedValue = getView().findViewById(R.id.speed_value);

        speedControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress % 10 == 0) {
                    sendBluetoothData(FlightMainRotorSpeedInterface.get_message_string(progress) + '+');
                    speedValue.setText(String.valueOf(progress));
                    Log.e(TAG, "Send value " + progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Optional: Add logic when the slider is touched
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress(0);
                sendBluetoothData(FlightMainRotorSpeedInterface.get_message_string(0) + '+');
                speedValue.setText("0");
            }
        });
    }

    private void handleAltitudeControl() {
        SeekBar altitudeControl = getView().findViewById(R.id.altitude_control);
        TextView altitudeValue = getView().findViewById(R.id.altitude_value);

        altitudeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress % 10 == 0) {
                    sendBluetoothData(FlightAltitudeInterface.get_message_string(progress) + '+');
                    altitudeValue.setText(String.valueOf(progress));
                    Log.e(TAG, "Send value " + progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Optional: Add logic when the slider is touched
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress(0);
                sendBluetoothData(FlightAltitudeInterface.get_message_string(0) + '+');
                altitudeValue.setText("0");
            }
        });
    }
    
    private void handleSteeringControl() {
        SeekBar steeringControl = getView().findViewById(R.id.steering_control);
        TextView steeringValue = getView().findViewById(R.id.steering_value);

        steeringControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress % 10 == 0) {
                    sendBluetoothData(FlightDirectionInterface.get_message_string(progress) + '+');
                    steeringValue.setText(String.valueOf(progress));
                    Log.e(TAG, "Send value " + progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Optional: Add logic when the slider is touched
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress(0);
                sendBluetoothData(FlightDirectionInterface.get_message_string(0) + '+');
                steeringValue.setText("0");
            }
        });
    }

    private void handleBluetoothStatus() {
        Switch bluetoothSwitch = getView().findViewById(R.id.connect_bluetooth_switch);
        TextView statusBluetooth = getView().findViewById(R.id.status_bluetooth);
        Handler handler =new Handler();
            final Runnable r = new Runnable() {
                    public void run() {
                        handler.postDelayed(this, 500);
                        bluetoothSwitch.setChecked(connectedToDevice);
                        if (connectedToDevice) {
                            statusBluetooth.setTextColor(Color.GREEN);
                        } else {
                            statusBluetooth.setTextColor(Color.RED);
                        }
                    }
                };
                handler.postDelayed(r, 0000);
        bluetoothSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                connectBluetooth();
            } else {
                closeConnection();
            }
        });
    }

    private void handleWheelsSwitch() {
        Switch wheelsSwitch = getView().findViewById(R.id.control_landing_gear);
        TextView statusWheels = getView().findViewById(R.id.status_landing_gear);
        Handler handler =new Handler();
            final Runnable r = new Runnable() {
                    public void run() {
                        handler.postDelayed(this, 500);
                        wheelsSwitch.setChecked(wheelsIn);
                        if (wheelsIn) {
                            statusWheels.setTextColor(Color.BLUE);
                            statusWheels.setText("Wheels In");
                        } else {
                            statusWheels.setTextColor(Color.BLUE);
                            statusWheels.setText("Wheels Out");
                        }
                    }
                };
                handler.postDelayed(r, 0000);
        wheelsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                wheelsIn=true;
                sendBluetoothData(FlightSwitchesInterface.get_message_string(setSwitchesBit(FlightSwitchesInterface.Switch.kWheels, true)) + '+');
            } else {
                wheelsIn=false;
                sendBluetoothData(FlightSwitchesInterface.get_message_string(setSwitchesBit(FlightSwitchesInterface.Switch.kWheels, false)) + '+');
            }
        });
    }

    private void handleTurbineSwitch() {
        Switch turbineSwitch = getView().findViewById(R.id.fly_turbine_move);
        TextView statusTurbineMove = getView().findViewById(R.id.status_fly_turbine_move);
        Handler handler =new Handler();
            final Runnable r = new Runnable() {
                    public void run() {
                        handler.postDelayed(this, 500);
                        turbineSwitch.setChecked(flyTurbineMove);
                        if (flyTurbineMove) {
                            statusTurbineMove.setTextColor(Color.GREEN);
                        } else {
                            statusTurbineMove.setTextColor(Color.WHITE);
                        }
                    }
                };
                handler.postDelayed(r, 0000);
        turbineSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                flyTurbineMove=true;
                sendBluetoothData(FlightSwitchesInterface.get_message_string(setSwitchesBit(FlightSwitchesInterface.Switch.kTurbine, true)) + '+');
            } else {
                flyTurbineMove=false;
                sendBluetoothData(FlightSwitchesInterface.get_message_string(setSwitchesBit(FlightSwitchesInterface.Switch.kTurbine, false)) + '+');
            }
        });
    }

    private void handleLoopingSwitch() {
        Switch loopingSwitch = getView().findViewById(R.id.fly_looping_move);
        TextView statusLoopingMove = getView().findViewById(R.id.status_fly_looping_move);
        Handler handler =new Handler();
            final Runnable r = new Runnable() {
                    public void run() {
                        handler.postDelayed(this, 500);
                        loopingSwitch.setChecked(flyLoopingMove);
                        if (flyLoopingMove) {
                            statusLoopingMove.setTextColor(Color.GREEN);
                        } else {
                            statusLoopingMove.setTextColor(Color.WHITE);
                        }
                    }
                };
                handler.postDelayed(r, 0000);
        loopingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                flyLoopingMove=true;
                sendBluetoothData(FlightSwitchesInterface.get_message_string(setSwitchesBit(FlightSwitchesInterface.Switch.kLooping, true)) + '+');
            } else {
                flyLoopingMove=false;
                sendBluetoothData(FlightSwitchesInterface.get_message_string(setSwitchesBit(FlightSwitchesInterface.Switch.kLooping, false)) + '+');
            }
        });
    }

    private void handleCheckupSwitch() {
        Switch checkupSwitch = getView().findViewById(R.id.perform_system_check);
        TextView statusCheckup = getView().findViewById(R.id.status_system_check);
        Handler handler =new Handler();
            final Runnable r = new Runnable() {
                    public void run() {
                        handler.postDelayed(this, 500);
                        checkupSwitch.setChecked(performCheckup);
                        if (performCheckup) {
                            statusCheckup.setTextColor(Color.GREEN);
                        } else {
                            statusCheckup.setTextColor(Color.WHITE);
                        }
                    }
                };
                handler.postDelayed(r, 0000);
        checkupSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                performCheckup=true;
                sendBluetoothData(FlightSwitchesInterface.get_message_string(setSwitchesBit(FlightSwitchesInterface.Switch.kCheckup, true)) + '+');
            } else {
                performCheckup=false;
                sendBluetoothData(FlightSwitchesInterface.get_message_string(setSwitchesBit(FlightSwitchesInterface.Switch.kCheckup, false)) + '+');
            }
        });
    }

    private void handleSteeringSwitch() {
        Switch steeringSwitch = getView().findViewById(R.id.motor_steering);
        TextView statusSteering = getView().findViewById(R.id.status_motor_steering);
        Handler handler =new Handler();
            final Runnable r = new Runnable() {
                    public void run() {
                        handler.postDelayed(this, 500);
                        steeringSwitch.setChecked(steeringByPropellers);
                        if (steeringByPropellers) {
                            statusSteering.setTextColor(Color.BLUE);
                            statusSteering.setText("Motor Steering");
                        } else {
                            statusSteering.setTextColor(Color.WHITE);
                            statusSteering.setText("Propeller Steering");
                        }
                    }
                };
                handler.postDelayed(r, 0000);
        steeringSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                steeringByPropellers=true;
                sendBluetoothData(FlightSwitchesInterface.get_message_string(setSwitchesBit(FlightSwitchesInterface.Switch.kSteering, true)) + '+');
            } else {
                steeringByPropellers=false;
                sendBluetoothData(FlightSwitchesInterface.get_message_string(setSwitchesBit(FlightSwitchesInterface.Switch.kSteering, false)) + '+');
            }
        });
    }

    private void handleLightsSwitch() {
        Switch lightsSwitch = getView().findViewById(R.id.turn_lights_on);
        TextView statusLights = getView().findViewById(R.id.status_turn_lights_on);
        Handler handler =new Handler();
            final Runnable r = new Runnable() {
                    public void run() {
                        handler.postDelayed(this, 500);
                        lightsSwitch.setChecked(lightsOn);
                        if (lightsOn) {
                            statusLights.setTextColor(Color.BLUE);
                            statusLights.setText("Lights On");
                        } else {
                            statusLights.setTextColor(Color.WHITE);
                            statusLights.setText("Lights Off");
                        }
                    }
                };
                handler.postDelayed(r, 0000);
        lightsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                lightsOn=true;
                sendBluetoothData(FlightSwitchesInterface.get_message_string(setSwitchesBit(FlightSwitchesInterface.Switch.kLights, true)) + '+');
            } else {
                lightsOn=false;
                sendBluetoothData(FlightSwitchesInterface.get_message_string(setSwitchesBit(FlightSwitchesInterface.Switch.kLights, false)) + '+');
            }
        });
    }

    private byte setSwitchesBit(FlightSwitchesInterface.Switch switch_variant, boolean value) {
        byte out_value = (byte)FlightSwitchesInterface.v;
        if (switch_variant == FlightSwitchesInterface.Switch.kWheels) {
            if (value) {
                out_value |= 0b00000001;
            } else {
                out_value &= 0b11111110;
            }
        } else if (switch_variant == FlightSwitchesInterface.Switch.kTurbine) {
            if (value) {
                out_value |= 0b00000010;
            } else {
                out_value &= 0b11111101;
            }
        } else if (switch_variant == FlightSwitchesInterface.Switch.kLooping) {
            if (value) {
                out_value |= 0b00000100;
            } else {
                out_value &= 0b11111011;
            }
        } else if (switch_variant == FlightSwitchesInterface.Switch.kCheckup) {
            if (value) {
                out_value |= 0b00001000;
            } else {
                out_value &= 0b11110111;
            }
        } else if (switch_variant == FlightSwitchesInterface.Switch.kSteering) {
            if (value) {
                out_value |= 0b00010000;
            } else {
                out_value &= 0b11101111;
            }
        } else if (switch_variant == FlightSwitchesInterface.Switch.kLights) {
            if (value) {
                out_value |= 0b00100000;
            } else {
                out_value &= 0b11011111;
            }
        }
        return out_value;
    }

    private void handleFullScreenMode(MainActivity mainActivity) {
        Switch fullscreenSwitch = getView().findViewById(R.id.toggle_fullscreen_mode);
        TextView statusFullscreen = getView().findViewById(R.id.status_toggle_fullscreen_mode);
        Handler handler =new Handler();
            final Runnable r = new Runnable() {
                    public void run() {
                        handler.postDelayed(this, 500);
                        fullscreenSwitch.setChecked(inFullscreenMode);
                        if (inFullscreenMode) {
                            statusFullscreen.setTextColor(Color.GREEN);
                        } else {
                            statusFullscreen.setTextColor(Color.RED);
                        }
                    }
                };
                handler.postDelayed(r, 0000);
        fullscreenSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                hideSystemUI(mainActivity);
                if (mainActivity.getSupportActionBar() != null) {
                    mainActivity.getSupportActionBar().hide();
                    inFullscreenMode=true;
                }
            } else {
                restoreSystemUI();
                if (mainActivity.getSupportActionBar() != null) {
                    mainActivity.getSupportActionBar().show();
                    inFullscreenMode=true;
                }
                inFullscreenMode = false;
            }
        });
    }

    private void sendBluetoothData(String message) {
         if (outputStream != null) {
             try {
                 outputStream.write(message.getBytes());
             } catch (Exception e) {
                 Log.e(TAG, "Error sending message", e);
             }
         }
    }

    private void receiveBluetoothData() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            new Thread(() -> {
                byte[] buffer = new byte[1024];
                int bytes;
                while (true && connectedToDevice) {
                    try {
                        if (inputStream != null && (bytes = inputStream.read(buffer)) > 0) {
                            String receivedMessage = new String(buffer, 0, bytes);
                            Log.e(TAG, "Received " + receivedMessage);
//                             mainActivity.runOnUiThread(() -> statusText.setText("Received: " + receivedMessage));
                            mainActivity.runOnUiThread(() -> {});
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error reading message", e);
                        break;
                    }
                }
            }).start();
        }

    }

    private void connectBluetooth() {

        TextView statusBluetooth = getView().findViewById(R.id.status_bluetooth);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            statusBluetooth.setTextColor(Color.RED);
            return;
        }

        // Get paired devices
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
            if (device.getName().equals("HC-06")) {
                try {
                    socket = device.createRfcommSocketToServiceRecord(MY_UUID);
                    socket.connect();

                    outputStream = socket.getOutputStream();
                    inputStream = socket.getInputStream();

                    statusBluetooth.setTextColor(Color.GREEN);
                    connectedToDevice  =true;
                } catch (Exception e) {
                    Log.e(TAG, "Error connecting to device", e);
                    statusBluetooth.setTextColor(Color.RED);
                    connectedToDevice = false;
                }
                break;
            }
        }
    }

    private void closeConnection() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error closing socket", e);
        }
    }

    private void hideSystemUI(MainActivity mainActivity) {
        if (getActivity() == null) return;

        View decorView = requireActivity().getWindow().getDecorView();
        // Use WindowInsetsController for Android 11+
        requireActivity().getWindow().setDecorFitsSystemWindows(false);
        WindowInsetsController controller = requireActivity().getWindow().getInsetsController();
        if (controller != null) {
            controller.hide(android.view.WindowInsets.Type.systemBars());
            controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        }
        mainActivity.LockDrawer();

    }

    private void restoreSystemUI() {
        if (getActivity() == null) return;

        View decorView = requireActivity().getWindow().getDecorView();
        requireActivity().getWindow().setDecorFitsSystemWindows(true);
        WindowInsetsController controller = requireActivity().getWindow().getInsetsController();
        if (controller != null) {
            controller.show(android.view.WindowInsets.Type.systemBars());
            controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_DEFAULT);
        }
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.UnlockDrawer();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // Restore the previous orientation
        requireActivity().setRequestedOrientation(previousOrientation);
        closeConnection();
        restoreSystemUI();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Restore system UI when the fragment is destroyed
        restoreSystemUI();
    }
}
