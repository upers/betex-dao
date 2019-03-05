package com.vareger.validators;

import com.vareger.models.Broker;
import com.vareger.servicies.BrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class BrokerIdValidator implements ConstraintValidator<ValidBrokerId, Integer> {

    @Autowired
    private BrokerService brokerService;

    @Override
    public void initialize(ValidBrokerId constraintAnnotation) {

    }

    @Override
    public boolean isValid(Integer brokerId, ConstraintValidatorContext context) {
        if (brokerId == null)
            return false;

        Broker broker = brokerService.findById(brokerId);
        if (broker == null)
            return false;

        return true;

    }

}
