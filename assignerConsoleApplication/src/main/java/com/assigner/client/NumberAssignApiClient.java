package com.assigner.client;

import com.assigner.exception.ApiFailException;
import com.assigner.object.NumberResponse;

import java.io.IOException;

public interface NumberAssignApiClient {

    NumberResponse sendRequest(String parameter) throws IOException, ApiFailException;

}
