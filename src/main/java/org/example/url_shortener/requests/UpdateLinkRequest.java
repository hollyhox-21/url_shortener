package org.example.url_shortener.requests;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Pattern;

public class UpdateLinkRequest {
    @Pattern(regexp = "^clck\\.ru/[a-zA-Z0-9]{6}$")
    @JsonAlias(value = "short_url")
    private String shortUrl;
    @JsonAlias(value = "count_redirect")
    private int countRedirect;

    public int getCountRedirect() {
        return countRedirect;
    }

    public String getShortUrl() {
        return shortUrl;
    }
}
