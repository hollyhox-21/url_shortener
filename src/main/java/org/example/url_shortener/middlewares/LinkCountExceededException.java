package org.example.url_shortener.middlewares;

final public class LinkCountExceededException extends Exception {
    public LinkCountExceededException(String message) {
        super(message);
    }
}
