package com.example.demo.exception;


import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;
    private final String VALIDATION_MESSAGE="Validation failed";

    @ExceptionHandler(CurrencyException.class)
    private ResponseEntity<?> handleCurrencyExceptions(CurrencyException exception) {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setMessage(exception.getMessage());
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<?> handleMethodArgument(MethodArgumentNotValidException exception){
        ApiValidationError apiError = new ApiValidationError();
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setMessage(VALIDATION_MESSAGE);
        List<FieldError> violations = exception.getBindingResult().getFieldErrors();
        violations.forEach(v->apiError.addSubError(getValidationError(v)));
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }


    private ApiSubError getValidationError(FieldError error) {
        return new ApiValidationSubError(error.getObjectName(),
                error.getField(),
                error.getRejectedValue(),
                messageSource.getMessage(error,LocaleContextHolder.getLocale()));

    }
}
