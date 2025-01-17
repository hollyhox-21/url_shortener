package org.example.url_shortener.middlewares;

final public class LinkExpiredException extends Exception {
    public LinkExpiredException(String message) {
        super(message);
    }
}
