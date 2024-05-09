package com.majumundur.Services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ValidationService {
    private Validator validator;

    public ValidationService(Validator validator){
        this.validator = validator;
    }

    public List<String> validate(Object request) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(request);
        List<String> violationList = new ArrayList<>();
        if (constraintViolations.size() != 0) {
            for(ConstraintViolation c : constraintViolations){
                violationList.add(c.getMessage());
            }
            return  violationList;
        }
        return  null;
    }
}