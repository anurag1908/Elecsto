package com.anurag.electronic_store.Elecsto.exceptions;

import lombok.Builder;

@Builder
public class BadApiRequestException extends RuntimeException {

    public BadApiRequestException(){
        super("Bad Api request please check the inputs");
    }
    public BadApiRequestException(String message){
        super(message);
    }

}
