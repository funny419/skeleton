package com.funny.utils.convert;

import java.io.Serializable;


public class ConvertException extends Exception implements Serializable {
    private static final long serialVersionUID = -8551515950407197124L;




    public ConvertException(Throwable t) {
        super(t);
    }


    public ConvertException() {
        super();
    }


    public ConvertException(String message) {
        super(message);
    }


    public ConvertException(String message,Throwable t) {
        super(message,t);
    }


    public ConvertException(Object value) {
        this("Unable to convert value: " + value);
    }


    public ConvertException(Object value,Throwable t) {
        this("Unable to convert value: " + value,t);
    }
}
