package pl.szlify.libraryapi.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CategoryOrAuthorValidator.class)
public @interface CategoryOrAuthor {
    String message() default "At least one of the category or author fields must be filled";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
