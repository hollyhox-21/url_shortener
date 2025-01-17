package org.example.url_shortener;

import jakarta.validation.Valid;
import org.example.url_shortener.middlewares.LinkCountExceededException;
import org.example.url_shortener.middlewares.LinkExpiredException;
import org.example.url_shortener.middlewares.LinkNotFoundException;
import org.example.url_shortener.model.User;
import org.example.url_shortener.repository.UserRepository;
import org.example.url_shortener.requests.CreateLinkRequest;
import org.example.url_shortener.requests.UpdateLinkRequest;
import org.example.url_shortener.utils.StringGenerator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@RestController
public class UrlShortenerController {

    private final UserRepository usersRepository;

    public UrlShortenerController(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @PostMapping(
            value = "/create",
            produces = "application/json",
            consumes = "application/json"
    )
    public User create(@RequestHeader(value = "X-User-Uuid", required = false) UUID userUuid,@Valid @RequestBody CreateLinkRequest body) {
        User user = new User();
        String shortUrl = StringGenerator.generate(6);

        user.setUserUuid(Objects.requireNonNullElseGet(userUuid, UUID::randomUUID));

        LocalDateTime expiredAt = LocalDateTime.now().plusDays(1);

        user.setUrl(body.getUrl());
        user.setShortUrl("clck.ru/" + shortUrl);
        user.setCountRedirect(body.getCountRedirect());
        user.setExpiredAt(expiredAt);
        usersRepository.save(user);
        return user;
    }

    @PostMapping(
            value = "/update",
            produces = "application/json",
            consumes = "application/json"
    )
    public void update(@RequestHeader(value = "X-User-Uuid", required = true) UUID userUuid,@Valid @RequestBody UpdateLinkRequest body) {

        try {
            usersRepository.updateFieldByUserIdAndShortUrl(userUuid, body.getShortUrl(), body.getCountRedirect());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @GetMapping(
            value = "/clck.ru/{alias}"
    )
    public RedirectView redirect(@RequestHeader("X-User-Uuid") UUID userUuid, @PathVariable String alias) throws LinkNotFoundException, LinkExpiredException, LinkCountExceededException {
        alias = "clck.ru/" + alias;

        User user = usersRepository.findByUserUuidAndShortUrl(userUuid, alias);
        if (user == null) {
            throw new LinkNotFoundException("Link not found: " + alias + " for user: " + userUuid);
        }
        if (user.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new LinkExpiredException("Link expired: " + alias + " for user: " + userUuid);
        }
        if (user.getCountRedirect() == 0) {
            throw new LinkCountExceededException("Link count exceeded: " + alias + " for user: " + userUuid);
        }

        if (user.getCountRedirect() > 0) {
            user.setCountRedirect(user.getCountRedirect() - 1);
            usersRepository.save(user);
        }

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(user.getUrl());
        return redirectView;
    }


}

