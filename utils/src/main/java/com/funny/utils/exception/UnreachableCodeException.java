package com.funny.utils.exception;


public class UnreachableCodeException extends RuntimeException {
    private static final long serialVersionUID = 4315142788286816787L;


    public UnreachableCodeException() {
        super();
    }


    public UnreachableCodeException(String message) {
        super(message);
    }


    public UnreachableCodeException(Throwable cause) {
        super(cause);
    }


    public UnreachableCodeException(String message,Throwable cause) {
        super(message,cause);
    }
}
