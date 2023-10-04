package org.mojodojocasahouse.extra.exception;

public class MissingRequestParameterException extends RuntimeException{
    public MissingRequestParameterException(){
        super("Request cannot be empty");
    }
}
