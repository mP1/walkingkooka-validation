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

package walkingkooka.validation.expression.function;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.Either;
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.function.ExpressionFunctionTesting;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.ValidationError;
import walkingkooka.validation.expression.FakeValidatorExpressionEvaluationContext;

public final class ValidationExpressionFunctionValidationErrorIfTest implements ExpressionFunctionTesting<ValidationExpressionFunctionValidationErrorIf<TestValidationReference, FakeValidatorExpressionEvaluationContext<TestValidationReference>>, ValidationError<TestValidationReference>, FakeValidatorExpressionEvaluationContext<TestValidationReference>> {

    private final static TestValidationReference REFERENCE = new TestValidationReference("Field123");

    // apply............................................................................................................

    @Test
    public void testApplyExpressionTrue() {
        final ValidationError<TestValidationReference> error = ValidationError.with(
            REFERENCE,
            "Error message 123"
        );

        this.applyAndCheck(
            Lists.of(
                Expression.value("true"),
                error
            ),
            error
        );
    }

    @Test
    public void testApplyExpressionFalse() {
        final ValidationError<TestValidationReference> error = ValidationError.with(
            REFERENCE,
            "Error message 123"
        );

        this.applyAndCheck(
            Lists.of(
                Expression.value("false"),
                error
            ),
            null
        );
    }

    @Override
    public ValidationExpressionFunctionValidationErrorIf<TestValidationReference, FakeValidatorExpressionEvaluationContext<TestValidationReference>> createBiFunction() {
        return ValidationExpressionFunctionValidationErrorIf.instance();
    }

    @Override
    public FakeValidatorExpressionEvaluationContext<TestValidationReference> createContext() {
        return new FakeValidatorExpressionEvaluationContext<>() {

            @Override
            public <T> Either<T, String> convert(final Object value,
                                                 final Class<T> target) {
                checkEquals(value.getClass(), String.class, "value");
                checkEquals(target, Boolean.class, "target");
                return this.successfulConversion(
                    Boolean.parseBoolean(String.class.cast(value)),
                    target
                );
            }
        };
    }

    @Override
    public int minimumParameterCount() {
        return 2;
    }

    // class............................................................................................................

    @Override
    public Class<ValidationExpressionFunctionValidationErrorIf<TestValidationReference, FakeValidatorExpressionEvaluationContext<TestValidationReference>>> type() {
        return Cast.to(ValidationExpressionFunctionValidationErrorIf.class);
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
