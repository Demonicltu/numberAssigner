package com.assigner.object;

public class Response {

    private final long value;

    private final String s;

    public Response(long value, String s) {
        this.value = value;
        this.s = s;
    }

    public long getValue() {
        return value;
    }

    public String getS() {
        return s;
    }

}
