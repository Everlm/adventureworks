package com.evercodes.adventureworks.application.validator;

import br.com.fluentvalidator.AbstractValidator;
import com.evercodes.adventureworks.presentation.dto.PersonRequest;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.ObjectPredicate.nullValue;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

public class PersonValidator extends AbstractValidator<PersonRequest> {

    @Override
    public void rules() {

        ruleFor(PersonRequest::getPersonType)
                .must(not(nullValue()))
                .when(not(nullValue()))
                .withMessage("PersonType is required")
                .withFieldName("personType")
                .critical();

        ruleFor(PersonRequest::getFirstName)
                .must(not(stringEmptyOrNull()))
                .when(not(nullValue()))
                .withMessage("FirstName is required")
                .withFieldName("firstName")
                .critical();

        ruleFor(PersonRequest::getLastName)
                .must(not(stringEmptyOrNull()))
                .when(not(nullValue()))
                .withMessage("LastName is required")
                .withFieldName("lastName")
                .critical();
    }
}
