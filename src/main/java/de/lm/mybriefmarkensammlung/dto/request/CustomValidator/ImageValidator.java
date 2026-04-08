package de.lm.mybriefmarkensammlung.dto.request.CustomValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class ImageValidator implements ConstraintValidator<RequiredUpload, MultipartFile[]> {
    @Override
    public void initialize(RequiredUpload constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile[] value, ConstraintValidatorContext context) {
        return  value != null &&
                value.length > 0 &&
                !value[0].isEmpty();
    }
}
