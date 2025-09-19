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
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.expression.function.ExpressionFunctionTesting;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.expression.ValidatorExpressionEvaluationContext;
import walkingkooka.validation.expression.ValidatorExpressionEvaluationContexts;
import walkingkooka.validation.provider.ValidatorSelector;

import java.util.Arrays;

public final class ValidationExpressionFunctionGetValidatorTest implements ExpressionFunctionTesting<ValidationExpressionFunctionGetValidator<TestValidationReference, ValidatorExpressionEvaluationContext<TestValidationReference>>, ValidatorSelector, ValidatorExpressionEvaluationContext<TestValidationReference>> {

    // apply............................................................................................................

    @Test
    public void testApplyNullValidatorSelector() {
        this.applyAndCheck(
            Arrays.asList(
                (Object)null
            ),
            null
        );
    }

    @Test
    public void testApplyValidatorSelector() {
        final ValidatorSelector validator = ValidatorSelector.parse("hello-validator");

        this.applyAndCheck(
            Lists.of(validator),
            validator
        );
    }

    @Override
    public ValidationExpressionFunctionGetValidator<TestValidationReference, ValidatorExpressionEvaluationContext<TestValidationReference>> createBiFunction() {
        return ValidationExpressionFunctionGetValidator.instance();
    }

    @Override
    public ValidatorExpressionEvaluationContext<TestValidationReference> createContext() {
        return ValidatorExpressionEvaluationContexts.fake();
    }

    @Override
    public int minimumParameterCount() {
        return 1;
    }

    // class............................................................................................................

    @Override
    public Class<ValidationExpressionFunctionGetValidator<TestValidationReference, ValidatorExpressionEvaluationContext<TestValidationReference>>> type() {
        return Cast.to(ValidationExpressionFunctionGetValidator.class);
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
