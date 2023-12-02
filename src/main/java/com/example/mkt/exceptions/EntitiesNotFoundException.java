package com.example.mkt.exceptions;

import org.webjars.NotFoundException;

public class EntitiesNotFoundException extends NotFoundException {
    public EntitiesNotFoundException(String msg){
        super(msg);
    }
}
