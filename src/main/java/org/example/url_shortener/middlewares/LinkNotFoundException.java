package org.example.url_shortener.middlewares;

final public class LinkNotFoundException extends Exception {
    public LinkNotFoundException(String message) {
        super(message);
    }
}

