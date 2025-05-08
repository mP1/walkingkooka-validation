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

package walkingkooka.validation.function;

import walkingkooka.Cast;
import walkingkooka.tree.expression.ExpressionPurityContext;
import walkingkooka.tree.expression.function.ExpressionFunction;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.validation.ValidationReference;

import java.util.List;

/**
 * A function which returns the value being validator from the {@link ValidatorExpressionEvaluationContext}.
 */
final class ValidationExpressionFunctionValidationValue<R extends ValidationReference, C extends ValidatorExpressionEvaluationContext<R>> extends ValidationExpressionFunction<Object, R, C> {

    /**
     * Type safe getter.
     */
    static <R extends ValidationReference, C extends ValidatorExpressionEvaluationContext<R>> ValidationExpressionFunctionValidationValue<R, C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private static final ValidationExpressionFunctionValidationValue<?, ?> INSTANCE = new ValidationExpressionFunctionValidationValue<>();

    private ValidationExpressionFunctionValidationValue() {
        super("validationValue");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return ExpressionFunction.NO_PARAMETERS;
    }

    @Override
    public Class<Object> returnType() {
        return Object.class;
    }

    @Override
    public boolean isPure(final ExpressionPurityContext context) {
        return false;
    }

    @Override
    Object applyNonNullParameters(final List<Object> parameters,
                                  final C context) {
        return context.validationValue();
    }
}