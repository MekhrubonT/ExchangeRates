package turaev.exchangerates.loader;


import java.io.IOException;

public class BadResponseException extends IOException {

    public BadResponseException(String message) {
        super(message);
    }

    public BadResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadResponseException(Throwable cause) {
        super(cause);
    }
}