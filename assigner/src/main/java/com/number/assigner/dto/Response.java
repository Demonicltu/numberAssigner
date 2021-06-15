package com.number.assigner.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {

    @JsonProperty("s")
    private final String sIdentificator;

    private final long value;

    public Response(String sIdentificator, long value) {
        this.sIdentificator = sIdentificator;
        this.value = value;
    }

    public String getsIdentificator() {
        return sIdentificator;
    }

    public long getValue() {
        return value;
    }

}
