package example.hello_world;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Handler;
import android.graphics.Color;
import java.util.Random;
// Open dialog
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import android.widget.Button;
import android.content.DialogInterface;
import android.text.Html;

import android.util.Log;




public class SmartHomeFragment extends Fragment {

    enum SmartHomeMachine {
        DISHWASHER,
        WASHING_MACHINE,
        DRYER
    }

    private Random rand = new Random();

    public SmartHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_smart_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        handleScheduledDishwasherRuns();
        handleButtonPressScheduleDishwasherRun();
        handleScheduledWashingMachineRuns();
        handleButtonPressScheduleWashingMachineRun();
        handleScheduledDryerRuns();
        handleButtonPressScheduleDryerRun();
        handleButtonSeeElectricityPrices();
        handleVacuumRobotStatus();
        handleVacuumRobotStartStop();
        handleChristmasLights();
        handleOutsideChristmasLights();
    }

    private void handleScheduledDishwasherRuns() {
        TextView statusDishwasherRunsScheduled = getView().findViewById(R.id.status_dishwasher_runs_scheduled);
        Button scheduleDishwasherRunButton = getView().findViewById(R.id.scheduleDishwasherRun);

        Handler handler =new Handler();
            final Runnable r = new Runnable() {
                    public void run() {
                        handler.postDelayed(this, 1000);
                        if (ScheduledSmartHomeRunsInterface.dishwasher_run_scheduled) {
                            statusDishwasherRunsScheduled.setText("Dishwasher run scheduled at " + ScheduledSmartHomeRunsInterface.scheduled_dishwasher_run_time);
                            statusDishwasherRunsScheduled.setTextColor(Color.RED);
                            scheduleDishwasherRunButton.setVisibility(View.GONE);
                        } else {
                            statusDishwasherRunsScheduled.setText("No dishwasher runs scheduled");
                            statusDishwasherRunsScheduled.setTextColor(Color.GREEN);
                            scheduleDishwasherRunButton.setVisibility(View.VISIBLE);
                        }
                    }
                };
                handler.postDelayed(r, 0000);

    }

    private void handleScheduledWashingMachineRuns() {
        TextView statusWashingMachineRunsScheduled = getView().findViewById(R.id.status_washing_machine_runs_scheduled);
        Button scheduleWashingMachineRunButton = getView().findViewById(R.id.scheduleWashingMachineRun);

        Handler handler =new Handler();
            final Runnable r = new Runnable() {
                    public void run() {
                        handler.postDelayed(this, 1000);
                        if (ScheduledSmartHomeRunsInterface.washing_machine_run_scheduled) {
                            statusWashingMachineRunsScheduled.setText("Washing machine run scheduled at " + ScheduledSmartHomeRunsInterface.scheduled_washing_machine_run_time);
                            statusWashingMachineRunsScheduled.setTextColor(Color.RED);
                            scheduleWashingMachineRunButton.setVisibility(View.GONE);
                        } else {
                            statusWashingMachineRunsScheduled.setText("No washing machine runs scheduled");
                            statusWashingMachineRunsScheduled.setTextColor(Color.GREEN);
                            scheduleWashingMachineRunButton.setVisibility(View.VISIBLE);
                        }
                    }
                };
                handler.postDelayed(r, 0000);

    }

    private void handleScheduledDryerRuns() {
        TextView statusDryerRunsScheduled = getView().findViewById(R.id.status_dryer_runs_scheduled);
        Button scheduleDryerRunButton = getView().findViewById(R.id.scheduleDryerRun);

        Handler handler =new Handler();
            final Runnable r = new Runnable() {
                    public void run() {
                        handler.postDelayed(this, 1000);
                        if (ScheduledSmartHomeRunsInterface.dryer_run_scheduled) {
                            statusDryerRunsScheduled.setText("Dryer run scheduled at " + ScheduledSmartHomeRunsInterface.scheduled_dryer_run_time);
                            statusDryerRunsScheduled.setTextColor(Color.RED);
                            scheduleDryerRunButton.setVisibility(View.GONE);
                        } else {
                            statusDryerRunsScheduled.setText("No dryer runs scheduled");
                            statusDryerRunsScheduled.setTextColor(Color.GREEN);
                            scheduleDryerRunButton.setVisibility(View.VISIBLE);
                        }
                    }
                };
                handler.postDelayed(r, 0000);

    }

    private void handleVacuumRobotStatus() {
        TextView statusRobot = getView().findViewById(R.id.status_robot);
        Button startRobotButton = getView().findViewById(R.id.startRobot);
        Button stopRobotButton = getView().findViewById(R.id.stopCleaning);
        Button startCleaningWhenOutOfHouseButton = getView().findViewById(R.id.startCleaningWhenOutOfHouse);

        Handler handler =new Handler();
            final Runnable r = new Runnable() {
                    public void run() {
                        handler.postDelayed(this, 1000);
                        statusRobot.setText(VacuumRobotStatusInterface.status);
                        if (VacuumRobotStatusInterface.status.contains("Charging")) {
                            statusRobot.setTextColor(Color.BLUE);
                            startRobotButton.setVisibility(View.VISIBLE);
                            startCleaningWhenOutOfHouseButton.setVisibility(View.VISIBLE);
                            stopRobotButton.setVisibility(View.GONE);
                        } else {
                            statusRobot.setTextColor(Color.GREEN);
                            startRobotButton.setVisibility(View.GONE);
                            startCleaningWhenOutOfHouseButton.setVisibility(View.GONE);
                            stopRobotButton.setVisibility(View.VISIBLE);
                        }
                    }
                };
                handler.postDelayed(r, 0000);

    }

    private void handleVacuumRobotStartStop() {
        Button startRobotButton = getView().findViewById(R.id.startRobot);
        Button stopRobotButton = getView().findViewById(R.id.stopCleaning);
        Button startCleaningWhenOutOfHouseButton = getView().findViewById(R.id.startCleaningWhenOutOfHouse);

        startRobotButton.setOnClickListener(v -> startRobot());
        stopRobotButton.setOnClickListener(v -> stopRobot());
        startCleaningWhenOutOfHouseButton.setOnClickListener(v -> startCleaningWhenOutOfHouse());
    }

    private void handleChristmasLights() {
        Button turnOnLightButton = getView().findViewById(R.id.turnChristmasLightsOn);
        Button turnOffLightButton = getView().findViewById(R.id.turnChristmasLightsOff);

        turnOnLightButton.setOnClickListener(v -> turnOnLight());
        turnOffLightButton.setOnClickListener(v -> turnOffLight());
        Handler handler =new Handler();
            final Runnable r = new Runnable() {
                    public void run() {
                        handler.postDelayed(this, 1000);
                        if (ChristmasLightStatusInterface.status == ChristmasLightStatusInterface.Status.kTurnedOn) {
                            turnOnLightButton.setVisibility(View.GONE);
                            turnOffLightButton.setVisibility(View.VISIBLE);
                        } else if (ChristmasLightStatusInterface.status == ChristmasLightStatusInterface.Status.kTurnedOff) {
                            turnOnLightButton.setVisibility(View.VISIBLE);
                            turnOffLightButton.setVisibility(View.GONE);
                        }
                    }
                };
                handler.postDelayed(r, 0000);
    }

    private void handleOutsideChristmasLights() {
        Button lightsButton = getView().findViewById(R.id.outsideLightChainPress);
        lightsButton.setOnClickListener(v -> pressOutsideLight());
    }

    private void pressOutsideLight() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.sendWebsocketMessage(LightChainRequestInterface.get_message_string(true, rand.nextInt(100)));
        }
        Toast.makeText(requireContext(), "Press outside light chain button", Toast.LENGTH_SHORT).show();
    }

    private void turnOnLight() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.sendWebsocketMessage(ChristmasLightRequestInterface.get_message_string(ChristmasLightRequestInterface.Request.kTurnOn, rand.nextInt(100)));
        }
        Toast.makeText(requireContext(), "Christmas Lights will be turned on", Toast.LENGTH_SHORT).show();
    }

    private void turnOffLight() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.sendWebsocketMessage(ChristmasLightRequestInterface.get_message_string(ChristmasLightRequestInterface.Request.kTurnOff, rand.nextInt(100)));
        }
        Toast.makeText(requireContext(), "Christmas Lights will be turned off", Toast.LENGTH_SHORT).show();
    }

    private void startRobot() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.sendWebsocketMessage(VacuumRobotRequestsInterface.get_message_string(VacuumRobotRequestsInterface.Request.kStartRobot,  rand.nextInt(100)));
        }
        Toast.makeText(requireContext(), "Vacuum robot will be started", Toast.LENGTH_SHORT).show();
    }

    private void stopRobot() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.sendWebsocketMessage(VacuumRobotRequestsInterface.get_message_string(VacuumRobotRequestsInterface.Request.kStopRobot,  rand.nextInt(100)));
        }
        Toast.makeText(requireContext(), "Vacuum robot will be stopped",Toast.LENGTH_SHORT).show();
    }

    private void startCleaningWhenOutOfHouse() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.sendWebsocketMessage(VacuumRobotRequestsInterface.get_message_string(VacuumRobotRequestsInterface.Request.kStartAfterPeopleInHouseLeave,  rand.nextInt(100)));
        }
        Toast.makeText(requireContext(), "Vacuum robot will start after " + PeopleAtHomeInterface.people_at_home.replaceAll(";",",") + " left.", Toast.LENGTH_SHORT).show();
    }

    private void handleButtonSeeElectricityPrices() {

        Button seeElectricityPricesButton = getView().findViewById(R.id.seeElectricityPrices);

        // Bazel supports Java 8 language features like lambdas!
        seeElectricityPricesButton.setOnClickListener(v -> seeElectricityPrices());
        TextView electricityPricesHeadline = getView().findViewById(R.id.ElectricityPrices);
        Handler handler =new Handler();
            final Runnable r = new Runnable() {
                    public void run() {
                        handler.postDelayed(this, 1000);
                        electricityPricesHeadline.setText("Updated : " + ElectricityPricesInterface.update_time);
                    }
                };
                handler.postDelayed(r, 0000);
    }

    private void seeElectricityPrices() {
        String[] items = ElectricityPricesInterface.cheapest_prices.split(";");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Electricity prices")
                  .setItems(items, (dialog, which) -> {})
                  .setNegativeButton("Close", null)
                  .show();
    }

    private void handleButtonPressScheduleDishwasherRun() {

        Button scheduleDishwasherRunButton = getView().findViewById(R.id.scheduleDishwasherRun);

        // Bazel supports Java 8 language features like lambdas!
        scheduleDishwasherRunButton.setOnClickListener(v -> showFirstDialog(SmartHomeMachine.DISHWASHER));
    }

    private void handleButtonPressScheduleWashingMachineRun() {

        Button scheduleWashingMachineRunButton = getView().findViewById(R.id.scheduleWashingMachineRun);

        // Bazel supports Java 8 language features like lambdas!
        scheduleWashingMachineRunButton.setOnClickListener(v -> showFirstDialog(SmartHomeMachine.WASHING_MACHINE));
    }

    private void handleButtonPressScheduleDryerRun() {

        Button scheduleDryerRunButton = getView().findViewById(R.id.scheduleDryerRun);

        // Bazel supports Java 8 language features like lambdas!
        scheduleDryerRunButton.setOnClickListener(v -> showFirstDialog(SmartHomeMachine.DRYER));
    }

    private String getMessageDialog(SmartHomeMachine variant) {
        if (variant==SmartHomeMachine.DISHWASHER) {
            return "In order to schedule a dishwasher run, please follow these steps : <br> <br> 1. fill in the tablet <br> 2. Press the Power Button and the Start button. <br> DO NOT CLOSE THE DOOR YET ";
        }
        else if (variant==SmartHomeMachine.WASHING_MACHINE) {
            return "In order to schedule a washing machine run, please follow these steps : <br> <br> 1. fill in the detergent <br> 2. Set up your program and press start. <br> DO NOT CLOSE THE DOOR YET ";
        }
        else if (variant==SmartHomeMachine.DRYER) {
            return "In order to schedule a dryer run, please follow these steps : <br> <br> 1. Set up your program and press start. <br> DO NOT CLOSE THE DOOR YET ";
        }
        return "Invalid";
    }

    private void showFirstDialog(SmartHomeMachine variant) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("1. Preparation")
                .setMessage(Html.fromHtml(getMessageDialog(variant)))
                .setPositiveButton("Continue", (dialog, which) -> showChoiceDialog(variant))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showChoiceDialog(SmartHomeMachine variant) {
        String[] items = ElectricityPricesInterface.cheapest_prices.split(";");
        for (String item : items) {
            Log.v("PRICES ITEM = ", item);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("2. Schedule run time (Windows ordered by cheapest price)")
                  .setSingleChoiceItems(items, -1, (dialog, which) -> {
                                      dialog.dismiss(); // Close the dialog after a selection
                                      showFinalDialog(items[which], variant);
                                  })
                  .setNegativeButton("Cancel", null)
                  .show();
    }

    private String getFinalDialogMessage(SmartHomeMachine variant) {
        if (variant==SmartHomeMachine.DISHWASHER) {
            return "Now close the door to the dishwasher. Shortly after the dishwasher will be turned off. It will be restarted at the scheduled time.";
        }
        else if (variant==SmartHomeMachine.WASHING_MACHINE) {
            return "Now close the door to the washing machine. Shortly after the washing machine will be turned off. It will be restarted at the scheduled time.";
        }
        else if (variant==SmartHomeMachine.DRYER) {
            return "Now close the door to the dryer. Shortly after the dryer will be turned off. It will be restarted at the scheduled time.";
        }
        return "Invalid";
    }

    private void showFinalDialog(String selectedTime, SmartHomeMachine variant) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("3. Close the door")
                .setMessage(getFinalDialogMessage(variant))
                .setPositiveButton("Finish", (dialog, which) -> {
                      MainActivity mainActivity = (MainActivity) getActivity();
                      if (mainActivity != null) {
                          if (variant==SmartHomeMachine.DISHWASHER) {
                              mainActivity.sendWebsocketMessage(
                                RequestedSmartHomeRunsInterface.get_message_string(
                                                                                    true, selectedTime,
                                                                                    false, "Invalid",
                                                                                    false, "Invalid",
                                                                                    rand.nextInt(100)));
                          } else if (variant==SmartHomeMachine.WASHING_MACHINE) {
                                mainActivity.sendWebsocketMessage(
                                RequestedSmartHomeRunsInterface.get_message_string(
                                                                                    false, "Invalid",
                                                                                    true, selectedTime,
                                                                                    false, "Invalid",
                                                                                    rand.nextInt(100)));
                          } else if (variant==SmartHomeMachine.DRYER) {
                                mainActivity.sendWebsocketMessage(
                                RequestedSmartHomeRunsInterface.get_message_string(
                                                                                    false, "Invalid",
                                                                                    false, "Invalid",
                                                                                    true, selectedTime,
                                                                                    rand.nextInt(100)));
                          }
                      }
                      Toast.makeText(requireContext(), "Next run will be scheduled for : " + selectedTime, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

}
