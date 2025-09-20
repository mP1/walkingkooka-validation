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
import walkingkooka.ToStringTesting;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.FakeExpressionEvaluationContext;

public final class ExpressionValidatorTest implements ValidatorTesting2<ExpressionValidator<TestValidationReference, TestValidatorContext>, TestValidationReference, TestValidatorContext>,
    ToStringTesting<ExpressionValidator<TestValidationReference, TestValidatorContext>> {

    private final static TestValidationReference REFERENCE = new TestValidationReference("HelloField");

    private final static String MESSAGE = "Missing Hello";

    @Test
    public void testValidate() {
        this.validateAndCheck(
            null,
            this.createContext(),
            ValidationError.with(REFERENCE)
                .setMessage(MESSAGE)
        );
    }

    @Override
    public ExpressionValidator<TestValidationReference, TestValidatorContext> createValidator() {
        return ExpressionValidator.with(
            Expression.value(
                ValidationError.with(REFERENCE)
                    .setMessage(MESSAGE)
            )
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
            ExpressionValidator.with(expression),
            expression.toString()
        );
    }

    // class............................................................................................................

    @Override
    public Class<ExpressionValidator<TestValidationReference, TestValidatorContext>> type() {
        return Cast.to(ExpressionValidator.class);
    }
}
