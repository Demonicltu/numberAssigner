package com.assigner.helper;

import com.google.gson.Gson;
import com.assigner.exception.ApiFailException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RequestHelper {

    public static String getBase64EncodedAuthorization(String username, String password) {
        String authentication = String.format("%s:%s", username, password);

        return Base64.getEncoder().encodeToString(authentication.getBytes(StandardCharsets.UTF_8));
    }

    public static String getParamString(String key, String value) {
        return URLEncoder.encode(key, StandardCharsets.UTF_8) +
                "=" +
                URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    public static <T> T getDeserializedResponse(InputStream responseStream, Class<T> objectClass)
            throws IOException, ApiFailException {
        Gson gson = new Gson();

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(responseStream)
        )) {
            String jsonString = in.readLine();

            if (jsonString != null) {
                return gson.fromJson(jsonString, objectClass);
            }

            throw new ApiFailException("Empty response");
        }
    }

}
