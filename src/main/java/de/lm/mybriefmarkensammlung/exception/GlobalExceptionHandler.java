package de.lm.mybriefmarkensammlung.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchCategoryException.class)
    public String handleCategoryNotFound(NoSuchCategoryException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(NoSuchCollectionException.class)
    public String handleCollectionNotFound(NoSuchCollectionException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(NoSuchImageException.class)
    public String handleImageNotFound(NoSuchImageException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(NoSuchRoleException.class)
    public String handleRoleNotFound(NoSuchRoleException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(NoSuchUserException.class)
    public String handleUserNotFound(NoSuchUserException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404";
    }
}