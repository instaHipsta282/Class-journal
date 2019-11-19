package com.instahipsta.webappTest.services;

import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.Map;

public interface UtilService {
    LocalDate stringToLocalDate(String date);

    Map<String, String> getErrors(BindingResult bindingResult);
}
