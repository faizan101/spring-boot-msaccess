package com.msaccess.app.validator;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AmountCheckValidator implements ConstraintValidator<AmountCheck, Object> {

    private String fromAmount;
    private String toAmount;

    public void initialize(AmountCheck constraintAnnotation) {
        this.fromAmount = constraintAnnotation.fromAmount();
        this.toAmount = constraintAnnotation.toAmount();
    }

    public boolean isValid(Object value,
                           ConstraintValidatorContext context) {

        Object param1 = new BeanWrapperImpl(value)
                .getPropertyValue(fromAmount);
        Object param2 = new BeanWrapperImpl(value)
                .getPropertyValue(toAmount);

        Double fromAmountValue = param1 == null ? null : Double.valueOf(new BeanWrapperImpl(value)
                .getPropertyValue(fromAmount).toString());
        Double toAmountValue = param2 == null ? null : Double.valueOf(new BeanWrapperImpl(value)
                .getPropertyValue(toAmount).toString());

        if (fromAmountValue == null && toAmountValue == null) {
            return true;
        } else {
            if (fromAmountValue == null || toAmountValue == null) {
                context.buildConstraintViolationWithTemplate("Error. Only one param passed. Please provide both fromAmount and toAmount values in order to filter on amount.").addConstraintViolation();
                context.disableDefaultConstraintViolation();
                return false;
            }
            if (fromAmountValue >= toAmountValue) {
                context.buildConstraintViolationWithTemplate("Error. Invalid Values. fromAmount cannot be greater or equal to toAmount.").addConstraintViolation();
                context.disableDefaultConstraintViolation();
                return false;
            }
            return true;
        }
    }
}
