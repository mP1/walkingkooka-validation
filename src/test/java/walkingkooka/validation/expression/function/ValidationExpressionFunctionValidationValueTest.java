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
import walkingkooka.tree.expression.function.ExpressionFunction;
import walkingkooka.tree.expression.function.ExpressionFunctionTesting;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.expression.FakeValidatorExpressionEvaluationContext;

import java.util.Optional;

public final class ValidationExpressionFunctionValidationValueTest implements ExpressionFunctionTesting<ValidationExpressionFunctionValidationValue<TestValidationReference, FakeValidatorExpressionEvaluationContext<TestValidationReference>>, Object, FakeValidatorExpressionEvaluationContext<TestValidationReference>> {

    // apply............................................................................................................

    @Test
    public void testApplyMissingValidationValue() {
        final Optional<Object> validationValue = Optional.empty();

        this.applyAndCheck(
            this.createBiFunction(),
            ExpressionFunction.NO_PARAMETER_VALUES,
            this.createContext(validationValue),
            validationValue
        );
    }

    @Test
    public void testApplyMissingFieldAndMissingValues() {
        final Optional<Object> validationValue = Optional.ofNullable("ValidationValue123");

        this.applyAndCheck(
            this.createBiFunction(),
            ExpressionFunction.NO_PARAMETER_VALUES,
            this.createContext(validationValue),
            validationValue
        );
    }

    @Override
    public ValidationExpressionFunctionValidationValue<TestValidationReference, FakeValidatorExpressionEvaluationContext<TestValidationReference>> createBiFunction() {
        return ValidationExpressionFunctionValidationValue.instance();
    }

    @Override
    public FakeValidatorExpressionEvaluationContext<TestValidationReference> createContext() {
        return this.createContext(Optional.empty());
    }

    private FakeValidatorExpressionEvaluationContext<TestValidationReference> createContext(final Optional<Object> validationValue) {
        return new FakeValidatorExpressionEvaluationContext<>() {
            @Override
            public Optional<Object> validationValue() {
                return validationValue;
            }
        };
    }

    @Override
    public int minimumParameterCount() {
        return 0;
    }

    // class............................................................................................................

    @Override
    public Class<ValidationExpressionFunctionValidationValue<TestValidationReference, FakeValidatorExpressionEvaluationContext<TestValidationReference>>> type() {
        return Cast.to(ValidationExpressionFunctionValidationValue.class);
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
