package com.number.assigner.service;

import com.number.assigner.dto.Response;
import com.number.assigner.exception.GeneratorFailException;

public interface NumberAssignService {

    Response getOrGenerateNumber(String sIdentifier) throws GeneratorFailException;

}
