package com.number.assigner.controller;

import com.number.assigner.dto.Response;
import com.number.assigner.error.ApplicationError;
import com.number.assigner.exception.GeneratorFailException;
import com.number.assigner.exception.RequestException;
import com.number.assigner.service.NumberAssignService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberAssignController {

    private final NumberAssignService numberAssignService;

    public NumberAssignController(NumberAssignService numberAssignService) {
        this.numberAssignService = numberAssignService;
    }

    @PostMapping("/")
    public Response assignNumber(@RequestParam(name = "s") String sIdentificator) {
        try {
            return numberAssignService.getOrGenerateNumber(sIdentificator);
        } catch (GeneratorFailException e) {
            throw new RequestException(ApplicationError.NUMBER_ASSIGN_ERROR);
        }
    }

}
