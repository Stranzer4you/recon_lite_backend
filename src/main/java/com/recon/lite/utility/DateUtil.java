package com.recon.lite.utility;

import com.recon.lite.exceptions.BadRequestException;
import com.recon.lite.utility.constants.ExceptionConstants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate parseDateOrThrow(String dateString) {
        try {
            return LocalDate.parse(dateString, FORMATTER);
        } catch (Exception e) {
            throw new BadRequestException(ExceptionConstants.INVALID_DATE_FORMAT);
        }
    }
}

