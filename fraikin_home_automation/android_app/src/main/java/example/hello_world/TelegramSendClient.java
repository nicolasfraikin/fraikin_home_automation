package example.hello_world;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import android.os.AsyncTask;
// UTILS
import android.util.Log;

public class TelegramSendClient extends AsyncTask<String, Void, String> {

    private static final String BOT_TOKEN = "1373590760:AAGilAC73vwIGZOnkbmVk_JV_2h4vdqewWM";
    private static final String CHAT_ID = "-1002033268659";
    private static final String TELEGRAM_API_URL_SEND = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage";
    private static OkHttpClient client = new OkHttpClient();

    @Override
    protected String doInBackground(String... params) {
        String message = params[0];
        RequestBody body = new FormBody.Builder()
                .add("chat_id", CHAT_ID)
                .add("text", message)
                .build();

        Request request = new Request.Builder()
                .url(TELEGRAM_API_URL_SEND)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                Log.v("Fraikin Home Automation", "Response not successfull sending" + response);
                throw new IOException("Unexpected code " + response);
            }
            Log.v("Fraikin Home Automation", "Message sent successfully: " + response.body().string());
            return "Message sent successfully: " + response.body().string();
        } catch (Exception e) {
            Log.v("Fraikin Home Automation", "Message not sent successfully: " + message);
            e.printStackTrace();
            return "Message not sent successfully: " + message;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d("TelegramSendTask", result);
    }
}