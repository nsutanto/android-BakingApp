package nsutanto.bakingapp.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtil {

    public static String getHttpResponse(String url) {
        String respStr = "";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            respStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return respStr;
    }
}
