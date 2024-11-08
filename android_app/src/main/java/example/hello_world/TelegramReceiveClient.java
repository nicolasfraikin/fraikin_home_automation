package example.hello_world;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import android.os.AsyncTask;
import androidx.lifecycle.MutableLiveData;
// UTILS
import android.util.Log;

public class TelegramReceiveClient extends AsyncTask<Void, Void, String> {

    private static final String BOT_TOKEN = "1373590760:AAGilAC73vwIGZOnkbmVk_JV_2h4vdqewWM";
    private static final String CHAT_ID = "1244417647";
    private static final String TELEGRAM_API_URL_RECEIVE = "https://api.telegram.org/bot" + BOT_TOKEN;
    private static OkHttpClient client = new OkHttpClient();

    private OnMessageReceived onMessageReceived;

    public TelegramReceiveClient(OnMessageReceived onMessageReceived) {
        this.onMessageReceived = onMessageReceived;
    }

    @Override
    protected String doInBackground(Void... voids) {
        // Keeps track of the last processed update to avoid duplicate messages
        int offset = 0;

        Request request = new Request.Builder()
                .url(TELEGRAM_API_URL_RECEIVE + "/getUpdates?offset=" + offset)
                .build();
        String received_message = "";
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String jsonData = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray updates = jsonObject.getJSONArray("result");

            for (int i = 0; i < updates.length(); i++) {
                JSONObject update = updates.getJSONObject(i);
                offset = update.getInt("update_id") + 1;  // Increment offset

                JSONObject message = update.getJSONObject("message");
                String text = message.getString("text");
                received_message = text;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return received_message;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        onMessageReceived.onTaskCompleted(result);
    }
}