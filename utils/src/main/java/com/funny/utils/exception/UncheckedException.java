package com.funny.utils.exception;

import java.io.Serializable;


public class UncheckedException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 991523423655751900L;


    public UncheckedException() {
        super();
    }

    public UncheckedException(String message) {
        super(message);
    }

    public UncheckedException(Throwable cause) {
        super(cause);
    }

    public UncheckedException(String message, Throwable cause) {
        super(message, cause);
    }
}
