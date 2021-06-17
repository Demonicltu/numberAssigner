package com.assigner.object;

public class NumberResponse {

    private final long value;

    private final String identifier;

    public NumberResponse(long value, String identifier) {
        this.value = value;
        this.identifier = identifier;
    }

    public long getValue() {
        return value;
    }

    public String getIdentifier() {
        return identifier;
    }

}
