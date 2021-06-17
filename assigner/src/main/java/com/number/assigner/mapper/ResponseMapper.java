package com.number.assigner.mapper;

import com.number.assigner.dto.GeneratedNumberResponse;

public class ResponseMapper {

    public static GeneratedNumberResponse toResponse(String identifier, long number) {
        return new GeneratedNumberResponse(
                identifier,
                number
        );
    }

}
