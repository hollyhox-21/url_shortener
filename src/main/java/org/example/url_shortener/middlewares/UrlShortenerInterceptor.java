package org.example.url_shortener.middlewares;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.url_shortener.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.example.url_shortener.model.User;

import java.util.Optional;
import java.util.UUID;

@Component
public class UrlShortenerInterceptor implements HandlerInterceptor {
    private final UserRepository usersRepository;

    public UrlShortenerInterceptor(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String userID = request.getHeader("X-User-Uuid");

            Optional<User> user = usersRepository.findOneByUserUuid(UUID.fromString(userID));
            if (user.isEmpty()) {
                throw new IllegalArgumentException("user not found: " + userID);
            }

            return true;
        } catch (IllegalArgumentException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error: " + ex.getMessage());
            return false;
        }
    }

//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, org.springframework.web.servlet.ModelAndView modelAndView) throws Exception {
//        System.out.println("Post Handle logic: " + request.getRequestURI());
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        System.out.println("After Completion logic: " + request.getRequestURI());
//    }
}
