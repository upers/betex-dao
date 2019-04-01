package com.vareger.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TransactionStatus {
    @JsonProperty("PENDING")
    PENDING("PENDING"),

    @JsonProperty("SUCCESS")
    SUCCESS("SUCCESS"),

    @JsonProperty("FAIL")
    FAIL("FAIL");

    private final String name;

    TransactionStatus(String name) {
        this.name = name();
    }

    public String getName() {
        return name;
    }
}
