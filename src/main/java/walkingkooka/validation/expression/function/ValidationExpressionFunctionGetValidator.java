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

import walkingkooka.Cast;
import walkingkooka.tree.expression.ExpressionPurityContext;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterKind;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterName;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.expression.ValidatorExpressionEvaluationContext;
import walkingkooka.validation.provider.ValidatorSelector;

import java.util.List;

/**
 * A function that returns the @link ValidatorSelector} from a source.
 */
final class ValidationExpressionFunctionGetValidator<R extends ValidationReference, C extends ValidatorExpressionEvaluationContext<R>> extends ValidationExpressionFunction<ValidatorSelector, R, C> {

    /**
     * Type safe getter.
     */
    static <R extends ValidationReference, C extends ValidatorExpressionEvaluationContext<R>> ValidationExpressionFunctionGetValidator<R, C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private static final ValidationExpressionFunctionGetValidator<?, ?> INSTANCE = new ValidationExpressionFunctionGetValidator<>();

    private ValidationExpressionFunctionGetValidator() {
        super("getValidator");
    }

    final static ExpressionFunctionParameter<ValidatorSelector> VALIDATOR_SELECTOR = ExpressionFunctionParameterName.with("validator")
        .required(ValidatorSelector.class)
        .setKinds(ExpressionFunctionParameterKind.CONVERT_EVALUATE);


    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = ExpressionFunctionParameter.list(
        VALIDATOR_SELECTOR
    );

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    @Override
    public Class<ValidatorSelector> returnType() {
        return ValidatorSelector.class;
    }

    @Override
    public boolean isPure(final ExpressionPurityContext context) {
        return false; // ValidatorSelector parameter could change at anytime.
    }

    @Override
    ValidatorSelector applyNonNullParameters(final List<Object> parameters,
                                             final C context) {
        return VALIDATOR_SELECTOR.getOrFail(parameters, 0);
    }
}