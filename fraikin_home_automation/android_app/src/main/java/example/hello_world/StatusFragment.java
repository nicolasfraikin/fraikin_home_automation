package example.hello_world;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
}
