package com.funny.utils.exception;

import java.io.Serializable;


public class ServiceNotFoundException extends ClassNotFoundException implements Serializable {
    private static final long serialVersionUID = 4639893859493988082L;

    public ServiceNotFoundException() {
        super();
    }

    public ServiceNotFoundException(String message) {
        super(message);
    }

    public ServiceNotFoundException(Throwable cause) {
        super(null, cause);

    }

    public ServiceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
