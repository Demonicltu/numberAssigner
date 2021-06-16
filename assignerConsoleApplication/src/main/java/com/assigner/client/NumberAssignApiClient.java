package com.assigner.client;

import com.assigner.exception.ApiFailException;
import com.assigner.object.Response;

import java.io.IOException;

public interface NumberAssignApiClient {

    Response sendRequest(String parameter) throws IOException, ApiFailException;

}
