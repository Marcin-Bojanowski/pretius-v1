package com.example.demo.exception;


import lombok.Data;

@Data
public class ApiValidationSubError extends ApiSubError {
    private String object;
    private String field;
    private Object rejectValue;
    private String message;

    public ApiValidationSubError(String object, String field, Object rejectValue, String message) {
        this.object = object;
        this.field = field;
        this.rejectValue = rejectValue;
        this.message = message;
    }
}
