package com.backdoored.api;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HasteBinApi
{
    public HasteBinApi() {
        super();
    }
    
    public static String uploadImpl(final String url, final String fileType, final String content) throws IOException {
        final byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        final URL uploadURL = new URL(url + "/documents");
        final HttpURLConnection connection = (HttpURLConnection)uploadURL.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "text/plain; charset=UTF-8");
        connection.setFixedLengthStreamingMode(bytes.length);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.connect();
        try {
            final OutputStream outputStream = connection.getOutputStream();
            outputStream.write(bytes);
            final InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
            final JsonObject json = (JsonObject)new Gson().fromJson((Reader)inputStream, (Class)JsonObject.class);
            String fileExtension = "";
            if (fileType != null && !fileType.isEmpty()) {
                fileExtension = "." + fileType;
            }
            connection.disconnect();
            return url + "/" + json.get("key").getAsString() + "." + fileType;
        }
        finally {
            connection.disconnect();
        }
    }
}
