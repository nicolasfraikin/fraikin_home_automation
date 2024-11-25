package example.hello_world;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
// import okio.ByteString;
import android.util.Log;


public class MyWebSocketClient extends WebSocketListener {

    private WebSocket webSocket;
    public static boolean isConnected = false;

    public void connect() {
        Log.v("Fraikin Home Automation","Connect to server");
        OkHttpClient client = new OkHttpClient();
//         Request request = new Request.Builder().url("ws://192.168.1.111:60141").build(); Host computer
        Request request = new Request.Builder().url("ws://192.168.1.106:60141").build(); //Raspi
        webSocket = client.newWebSocket(request, this);
        Log.v("Fraikin Home Automation","End of connect to server");
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        Log.v("Fraikin Home Automation","Open message");
        webSocket.send("Hello, Raspberry Pi!");
        Log.v("Fraikin Home Automation","Connected to server");
        isConnected=true;
    }

    public void sendMessage(String message) {
        if (webSocket != null) {
            webSocket.send(message);
            Log.v("Fraikin Home Automation", "Sent message: " + message);
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.v("Fraikin Home Automation","Received from server: " + text);
        Test3Interface.receive_message(text);
        ScheduledSmartHomeRunsInterface.receive_message(text);
        ElectricityPricesInterface.receive_message(text);
        VacuumRobotStatusInterface.receive_message(text);
        ChristmasLightStatusInterface.receive_message(text);
        ModulesStatiInterface.receive_message(text);
    }

//     @Override
//     public void onMessage(WebSocket webSocket, ByteString bytes) {
//         Log.v("Fraikin Home Automation","Received binary message");
//     }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(1000, null);
        Log.v("Fraikin Home Automation","Connection closing: " + reason);
        isConnected = false;
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        Log.v("Fraikin Home Automation","Connection failed: " + t.getMessage());
        isConnected = false;
    }

    public void close() {
        webSocket.close(1000, "Closing");
        isConnected = false;
    }
}
