package com.number.assigner.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {

    @JsonProperty("s")
    private final String sIdentifier;

    private final long value;

    public Response(String sIdentifier, long value) {
        this.sIdentifier = sIdentifier;
        this.value = value;
    }

    public String getsIdentifier() {
        return sIdentifier;
    }

    public long getValue() {
        return value;
    }

}
