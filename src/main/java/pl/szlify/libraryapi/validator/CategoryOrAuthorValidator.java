package pl.szlify.libraryapi.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.szlify.libraryapi.model.dto.SubscriptionCreateDto;

public class CategoryOrAuthorValidator implements ConstraintValidator<CategoryOrAuthor, SubscriptionCreateDto> {
    @Override
    public boolean isValid(SubscriptionCreateDto subscriptionCreateDto, ConstraintValidatorContext context) {
        return subscriptionCreateDto.getCategory() != null || subscriptionCreateDto.getAuthor() != null;
    }
}
