package org.example.url_shortener.middlewares;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Component
public class CustomExceptionResolver extends AbstractHandlerExceptionResolver {

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
        if (ex instanceof LinkNotFoundException) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            modelAndView.addObject("message", ex.getMessage());
            return modelAndView;
        }
        if (ex instanceof LinkExpiredException) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            modelAndView.addObject("message", ex.getMessage());
            return modelAndView;
        }
        if (ex instanceof LinkCountExceededException) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            modelAndView.addObject("message", ex.getMessage());
            return modelAndView;
        }
        if (ex instanceof MethodArgumentNotValidException) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            modelAndView.addObject("message", ex.getMessage());
            return modelAndView;
        }

        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.addObject("message", "Another exception was handled");
        return modelAndView;
    }

}
