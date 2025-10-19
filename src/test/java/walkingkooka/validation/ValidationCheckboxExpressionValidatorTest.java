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
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.FakeExpressionEvaluationContext;

import java.util.Optional;

public final class ValidationCheckboxExpressionValidatorTest implements ValidatorTesting2<ValidationCheckboxExpressionValidator<TestValidationReference, TestValidatorContext>, TestValidationReference, TestValidatorContext>,
    ToStringTesting<ValidationCheckboxExpressionValidator<TestValidationReference, TestValidatorContext>> {

    private final static TestValidationReference REFERENCE = new TestValidationReference("HelloField");

    private final static Object TRUE = 111;

    private final static Object FALSE = 222;

    private final static ValidationCheckbox CHECKBOX = ValidationCheckbox.with(
        Optional.of(TRUE),
        Optional.of(FALSE)
    );

    @Test
    public void testValidateWithNull() {
        this.validateAndCheck(
            this.createValidator(),
            null,
            this.createContext(),
            ValidationError.with(REFERENCE)
                .setValue(
                    Optional.of(CHECKBOX)
                )
        );
    }

    @Test
    public void testValidateWithFalse() {
        this.validateAndCheck(
            this.createValidator(),
            Optional.of(FALSE),
            this.createContext(),
            ValidationError.with(REFERENCE)
                .setValue(
                    Optional.of(CHECKBOX)
                )
        );
    }

    @Test
    public void testValidateWithTrue() {
        this.validateAndCheck(
            this.createValidator(),
            Optional.of(TRUE),
            this.createContext(),
            ValidationError.with(REFERENCE)
                .setValue(
                    Optional.of(CHECKBOX)
                )
        );
    }

    @Test
    public void testValidateWithNeither() {
        this.validateAndCheck(
            this.createValidator(),
            Optional.of("Neither"),
            this.createContext(),
            ValidationError.with(REFERENCE)
                .setValue(
                    Optional.of(CHECKBOX)
                )
        );
    }

    @Override
    public ValidationCheckboxExpressionValidator<TestValidationReference, TestValidatorContext> createValidator() {
        return ValidationCheckboxExpressionValidator.with(
            Expression.value(CHECKBOX)
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
                            CHECKBOX,
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
            ValidationCheckboxExpressionValidator.with(
                expression
            ),
            "1+23"
        );
    }

    // class............................................................................................................

    @Override
    public Class<ValidationCheckboxExpressionValidator<TestValidationReference, TestValidatorContext>> type() {
        return Cast.to(ValidationCheckboxExpressionValidator.class);
    }
}
