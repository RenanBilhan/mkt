package com.example.mkt.exceptions;

import java.util.Map;

public class UniqueFieldExistsException extends Exception {

    private Map<String, String> camposViolados;

    public UniqueFieldExistsException(Map<String, String> camposViolados) {
        this.camposViolados = camposViolados;
    }

    public Map<String, String> getCamposViolados() {
        return camposViolados;
    }
}
