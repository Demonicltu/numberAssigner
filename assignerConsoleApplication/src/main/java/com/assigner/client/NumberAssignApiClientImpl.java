package com.assigner.client;

import com.assigner.exception.ApiFailException;
import com.assigner.helper.RequestHelper;
import com.assigner.object.NumberResponse;
import com.assigner.singleton.ArgumentsHolder;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class NumberAssignApiClientImpl implements NumberAssignApiClient {

    private static final String API_IDENTIFIER_PARAM = "identifier";
    private static final String API_GENERATE_NUMBER_ENDPOINT = "/v1/number";

    public NumberResponse sendRequest(String parameter) throws IOException, ApiFailException {
        HttpURLConnection con = prepareConnection();

        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(RequestHelper.getParamString(API_IDENTIFIER_PARAM, parameter));
        out.flush();
        out.close();

        con.connect();

        int status = con.getResponseCode();

        if (status != HttpURLConnection.HTTP_OK) {
            con.disconnect();

            throw new ApiFailException("Failed to get number from API");
        }

        NumberResponse numberResponse = RequestHelper.getDeserializedResponse(con.getInputStream(), NumberResponse.class);

        con.disconnect();

        return numberResponse;
    }

    private HttpURLConnection prepareConnection() throws IOException {
        URL url = new URL(ArgumentsHolder.getInstance().getApiUrl() + API_GENERATE_NUMBER_ENDPOINT);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");

        con.setRequestProperty(
                "Authorization",
                String.format("Basic %s", RequestHelper.getBase64EncodedAuthorization(
                        ArgumentsHolder.getInstance().getApiUsername(), ArgumentsHolder.getInstance().getApiPassword()
                ))
        );

        con.setReadTimeout(10000);
        con.setConnectTimeout(15000);
        con.setDoOutput(true);

        return con;
    }

}
