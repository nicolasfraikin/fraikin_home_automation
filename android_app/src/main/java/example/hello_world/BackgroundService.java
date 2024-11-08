package example.hello_world;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

// UTILS
import android.util.Log;

public class BackgroundService extends Service {
    private final LocalBinder mBinder = new LocalBinder();
    protected Handler handler;

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
        handler.post(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                Log.v("Fraikin Home Automation", "Background task");
                // Handle all the topic receiving in here

            }
        });
        return android.app.Service.START_STICKY;
    }

}