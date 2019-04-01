package com.vareger.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TxHashValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTxHash {
    String message() default "Invalid ethereum transaction hash";
    
    Class<?>[] groups() default {};
     
    Class<? extends Payload>[] payload() default {};
}
