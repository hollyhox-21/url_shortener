package org.example.url_shortener.requests;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.hibernate.validator.constraints.URL;

public class CreateLinkRequest {
    @URL(message = "invalid URL")
    @JsonAlias(value = "url")
    private String url;
    @JsonAlias(value = "count_redirect")
    private int countRedirect;

    public int getCountRedirect() {
        return countRedirect;
    }

    public String getUrl() {
            return url;
    }
}
