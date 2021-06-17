package com.number.assigner.dto;

public class GeneratedNumberResponse {

    private final String identifier;

    private final long value;

    public GeneratedNumberResponse(String identifier, long value) {
        this.identifier = identifier;
        this.value = value;
    }

    public String getIdentifier() {
        return identifier;
    }

    public long getValue() {
        return value;
    }

}
