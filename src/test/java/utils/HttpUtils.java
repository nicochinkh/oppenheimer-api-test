package utils;

import com.google.gson.Gson;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class HttpUtils {

    public static CloseableHttpResponse sendPOST(String url, Object request) throws IOException {

        HttpPost httpPost = new HttpPost(url);

        Gson gson = new Gson();
        String requestJsonString = gson.toJson(request);

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(requestJsonString));
            System.out.println(requestJsonString);
            httpClient = HttpClients.createDefault();
            response = httpClient.execute(httpPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }


}
