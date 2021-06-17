package com.number.assigner.service;

import com.number.assigner.dto.GeneratedNumberResponse;
import com.number.assigner.exception.GeneratorFailException;

public interface NumberAssignService {

    GeneratedNumberResponse generateNumber(String identifier) throws GeneratorFailException;

}
