package com.instahipsta.webappTest.impl;

import com.instahipsta.webappTest.services.UtilService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class UtilServiceImpl implements UtilService {

    @Override
    public LocalDate stringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        return LocalDate.parse(date, formatter);
    }
}
