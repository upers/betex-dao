package com.vareger.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BrokerIdValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBrokerId {
    String message() default "Invalid broker id";
    
    Class<?>[] groups() default {};
     
    Class<? extends Payload>[] payload() default {};
}
