package com.funny.utils.exception;


public class UnexpectedFailureException extends RuntimeException {
    private static final long serialVersionUID = 8675509309307474568L;


    public UnexpectedFailureException() {
        super();
    }


    public UnexpectedFailureException(String message) {
        super(message);
    }


    public UnexpectedFailureException(Throwable cause) {
        super(cause);
    }


    public UnexpectedFailureException(String message,Throwable cause) {
        super(message,cause);
    }
}
