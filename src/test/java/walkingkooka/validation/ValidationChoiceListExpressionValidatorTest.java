/*
 * Copyright 2025 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.validation;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.Either;
import walkingkooka.ToStringTesting;
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.FakeExpressionEvaluationContext;

import java.util.List;
import java.util.Optional;

public final class ValidationChoiceListExpressionValidatorTest implements ValidatorTesting2<ValidationChoiceListExpressionValidator<TestValidationReference, TestValidatorContext>, TestValidationReference, TestValidatorContext>,
    ToStringTesting<ValidationChoiceListExpressionValidator<TestValidationReference, TestValidatorContext>> {

    private final static TestValidationReference REFERENCE = new TestValidationReference("HelloField");

    private final static String MESSAGE = "Invalid Choice 123";

    @Test
    public void testValidateWithNullFails() {
        final ValidationChoiceList choices = ValidationChoiceList.EMPTY.setElements(
            Lists.of(
                ValidationChoice.with(
                    "Label1",
                    Optional.of("Value1")
                )
            )
        );

        this.validateAndCheck(
            this.createValidator(choices),
            null,
            this.createContext(),
            ValidationError.with(REFERENCE)
                .setMessage(MESSAGE)
                .setValue(
                    Optional.of(choices)
                )
        );
    }

    @Test
    public void testValidateWithNull() {
        final ValidationChoiceList choices = ValidationChoiceList.EMPTY.setElements(
            Lists.of(
                ValidationChoice.with(
                    "Label1",
                    Optional.of("Value1")
                ),
                ValidationChoice.with(
                    "Label2",
                    Optional.empty()
                )
            )
        );

        this.validateAndCheck(
            this.createValidator(choices),
            null,
            this.createContext(),
            ValidationError.with(REFERENCE)
                .setValue(
                    Optional.of(choices)
                )
        );
    }

    @Test
    public void testValidateWithNonNullFails() {
        final ValidationChoiceList choices = ValidationChoiceList.EMPTY.concat(
            ValidationChoice.with(
                "Label1",
                Optional.of("Value1")
            )
        );

        this.validateAndCheck(
            this.createValidator(
                choices
            ),
            "UnknownValue123",
            this.createContext(),
            ValidationError.with(REFERENCE)
                .setMessage(MESSAGE)
                .setValue(
                    Optional.of(choices)
                )
        );
    }

    @Test
    public void testValidateWithNonNull() {
        final String value = "Value222";

        final ValidationChoiceList choices = ValidationChoiceList.EMPTY.setElements(
            Lists.of(
                ValidationChoice.with(
                    "Label1",
                    Optional.of("DifferentValue111")
                ),
                ValidationChoice.with(
                    "Label2",
                    Optional.of(value)
                ),
                ValidationChoice.with(
                    "Label3",
                    Optional.empty()
                )
            )
        );

        this.validateAndCheck(
            this.createValidator(choices),
            value,
            this.createContext(),
            ValidationError.with(REFERENCE)
                .setValue(
                    Optional.of(choices)
                )
        );
    }

    @Override
    public ValidationChoiceListExpressionValidator<TestValidationReference, TestValidatorContext> createValidator() {
        return this.createValidator(
            ValidationChoice.with(
                "Label1",
                Optional.of("Value1")
            )
        );
    }

    private ValidationChoiceListExpressionValidator<TestValidationReference, TestValidatorContext> createValidator(final ValidationChoice... choices) {
        return this.createValidator(
            Lists.of(choices)
        );
    }

    private ValidationChoiceListExpressionValidator<TestValidationReference, TestValidatorContext> createValidator(final List<ValidationChoice> choices) {
        return ValidationChoiceListExpressionValidator.with(
            Expression.value(choices),
            MESSAGE
        );
    }

    @Override
    public TestValidatorContext createContext() {
        return new TestValidatorContext() {

            @Override
            public ExpressionEvaluationContext expressionEvaluationContext(final Object value) {
                return new FakeExpressionEvaluationContext() {
                    @Override
                    public Object evaluateExpression(final Expression expression) {
                        return expression.toValue(this);
                    }

                    @Override
                    public <T> Either<T, String> convert(final Object value,
                                                         final Class<T> target) {
                        return this.successfulConversion(
                            ValidationChoiceList.EMPTY.setElements(
                                (List<ValidationChoice>) value
                            ),
                            target
                        );
                    }
                };
            }

            @Override
            public TestValidationReference validationReference() {
                return REFERENCE;
            }
        };
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        final Expression expression = Expression.add(
            Expression.value(1),
            Expression.value(23)
        );

        this.toStringAndCheck(
            ValidationChoiceListExpressionValidator.with(
                expression,
                MESSAGE
            ),
            "1+23 \"Invalid Choice 123\""
        );
    }

    // class............................................................................................................

    @Override
    public Class<ValidationChoiceListExpressionValidator<TestValidationReference, TestValidatorContext>> type() {
        return Cast.to(ValidationChoiceListExpressionValidator.class);
    }
}
