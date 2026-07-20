package com.evercodes.adventureworks.application.validator;

import br.com.fluentvalidator.AbstractValidator;
import com.evercodes.adventureworks.application.dto.PersonRequest;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.ObjectPredicate.nullValue;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

import org.springframework.stereotype.Component;

@Component
public class PersonValidator extends AbstractValidator<PersonRequest> 
{
    @Override
    public void rules() 
    {

        ruleFor(request -> request.getPersonType())
                .must(not(nullValue()))
                .withMessage("PersonType is required")
                .withFieldName("personType")
                .critical();

        ruleFor(request -> request.getFirstName())
                .must(not(stringEmptyOrNull()))
                .withMessage("FirstName is required")
                .withFieldName("firstName")
                .critical();

        ruleFor(request -> request.getLastName())
                .must(not(stringEmptyOrNull()))
                .withMessage("LastName is required")
                .withFieldName("lastName")
                .critical();
    }
}
