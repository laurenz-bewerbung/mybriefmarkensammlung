package de.lm.mybriefmarkensammlung.dto.request.CustomValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)

@Constraint(validatedBy = ImageValidator.class)
@Documented
public @interface RequiredUpload {

    String message() default "Es muss mindestens eine Datei hochgeladen werden.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}