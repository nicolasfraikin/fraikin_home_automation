package example.hello_world;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.content.Context;

// UTILS
import android.util.Log;

public class BackgroundService extends Service {
    private final LocalBinder mBinder = new LocalBinder();
    protected Handler handler;
    private boolean dishwasherRunPreviouslyRequested;
    private boolean firstExecution=true;
    private static Context activityContext;
    private String peoplePreviouslyAtHome;

    public static void setActivityContext(Context context) {
        activityContext = context;
    }

    public class LocalBinder extends Binder {
        public BackgroundService getService() {
            return BackgroundService .this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("Fraikin Home Automation", "On destroy command");
        Intent broadcastIntent = new Intent(this, Restarter.class);
        sendBroadcast(broadcastIntent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler = new Handler();
        Log.v("Fraikin Home Automation", "On start command");
        updateDishwasherPreviousRun();

        handler.post(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                if (!MyWebSocketClient.isConnected) {
                    Log.v("Fraikin Home Automation", "Connect websocket");
                    MainActivity.connectWebsocket();
                }
                MainActivity.sendWebsocketMessage("Ping");
                Log.v("Fraikin Home Automation", "Background task");

                if (!firstExecution && !dishwasherRunPreviouslyRequested && ScheduledSmartHomeRunsInterface.dishwasher_run_scheduled) {
                    MainActivity.sendNotification(activityContext, "Smart Home", "New dishwasher run scheduled at " +  ScheduledSmartHomeRunsInterface.scheduled_dishwasher_run_time, "SmartHomeFragment");
                }
                if (!firstExecution && dishwasherRunPreviouslyRequested && !ScheduledSmartHomeRunsInterface.dishwasher_run_scheduled) {
                    MainActivity.sendNotification(activityContext, "Smart Home", "Just started the dishwasher for you :)", "SmartHomeFragment");
                }
                if (!firstExecution && PeopleAtHomeInterface.people_at_home != peoplePreviouslyAtHome && peoplePreviouslyAtHome != null) {
                    String[] currentPeople = PeopleAtHomeInterface.people_at_home.split(";");
                    for (String currentPerson : currentPeople) {
                        if (!peoplePreviouslyAtHome.contains(currentPerson)) {
                            MainActivity.sendNotification(activityContext, "Welcome", currentPerson + " is home!", "StatusFragment");
                        }
                    }
                }
                firstExecution=false;
                updateDishwasherPreviousRun();
                updatePeoplePreviouslyAtHome();

            }
        });
        return android.app.Service.START_STICKY;
    }

    private void updateDishwasherPreviousRun() {
        if (MyWebSocketClient.isConnected) {
            dishwasherRunPreviouslyRequested = ScheduledSmartHomeRunsInterface.dishwasher_run_scheduled;
        }
    }
    private void updatePeoplePreviouslyAtHome() {
        if (MyWebSocketClient.isConnected) {
            peoplePreviouslyAtHome = PeopleAtHomeInterface.people_at_home;
        }
    }


}