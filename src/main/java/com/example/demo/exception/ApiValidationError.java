package com.example.demo.exception;

import io.swagger.annotations.Api;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class ApiValidationError extends ApiError {
    private List<ApiSubError> subErrors;


    public void addSubError(ApiSubError subError) {
        if (subErrors == null) {
            subErrors = new ArrayList<>();
        }
        subErrors.add(subError);
    }

}
