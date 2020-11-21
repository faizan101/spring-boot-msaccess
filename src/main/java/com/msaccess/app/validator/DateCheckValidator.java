package com.msaccess.app.validator;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateCheckValidator implements ConstraintValidator<DateCheck, Object> {

    private String fromDate;
    private String toDate;

    public void initialize(DateCheck constraintAnnotation) {
        this.fromDate = constraintAnnotation.fromDate();
        this.toDate = constraintAnnotation.toDate();
    }

    public boolean isValid(Object value,
                           ConstraintValidatorContext context) {


        Object param1 = new BeanWrapperImpl(value)
                .getPropertyValue(fromDate);
        Object param2 = new BeanWrapperImpl(value)
                .getPropertyValue(toDate);

        LocalDate fromDateValue = param1 == null ? null : (LocalDate.parse(new BeanWrapperImpl(value).getPropertyValue(fromDate).toString()));
        LocalDate toDateValue = param2 == null ? null : (LocalDate.parse(new BeanWrapperImpl(value).getPropertyValue(toDate).toString()));

        if (fromDateValue == null && toDateValue == null) {
            return true;
        } else {
            if (fromDateValue == null || toDateValue == null) {
                context.buildConstraintViolationWithTemplate("Error. Only one param passed. Please provide both FromDate and ToDate values in order to filter on date.").addConstraintViolation();
                context.disableDefaultConstraintViolation();
                return false;
            }
            if (fromDateValue.isAfter(toDateValue)) {
                context.buildConstraintViolationWithTemplate("Error. Invalid Values. FromDate cannot be after ToDate.").addConstraintViolation();
                context.disableDefaultConstraintViolation();
                return false;
            }
            return true;
        }
    }
}
