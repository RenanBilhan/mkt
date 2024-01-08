package com.example.mkt.exceptions;

import java.util.Map;

public class UniqueFieldExistsException extends Exception {

    private Map<String, String> violatedFields;

    public UniqueFieldExistsException(Map<String, String> violatedFields) {
        this.violatedFields = violatedFields;
    }

    public Map<String, String> getViolatedFields() {
        return violatedFields;
    }
}
