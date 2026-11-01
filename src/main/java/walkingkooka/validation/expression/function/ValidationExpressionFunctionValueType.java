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
import walkingkooka.validation.ValueType;
import walkingkooka.validation.expression.ValidatorExpressionEvaluationContext;

import java.util.List;

/**
 * A function that returns the {@link ValueType} from a value such as a {@link String} or {@link walkingkooka.validation.HasValueType}.
 */
final class ValidationExpressionFunctionValueType<R extends ValidationReference, C extends ValidatorExpressionEvaluationContext<R>> extends ValidationExpressionFunction<ValueType, R, C> {

    /**
     * Type safe getter.
     */
    static <R extends ValidationReference, C extends ValidatorExpressionEvaluationContext<R>> ValidationExpressionFunctionValueType<R, C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private static final ValidationExpressionFunctionValueType<?, ?> INSTANCE = new ValidationExpressionFunctionValueType<>();

    private ValidationExpressionFunctionValueType() {
        super("valueType");
    }

    final static ExpressionFunctionParameter<ValueType> VALUE_TYPE = ExpressionFunctionParameterName.with("valueType")
        .required(ValueType.class)
        .setKinds(ExpressionFunctionParameterKind.CONVERT_EVALUATE_RESOLVE_REFERENCES);


    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = ExpressionFunctionParameter.list(
        VALUE_TYPE
    );

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    @Override
    public Class<ValueType> returnType() {
        return ValueType.class;
    }

    @Override
    public boolean isPure(final ExpressionPurityContext context) {
        return true;
    }

    @Override
    ValueType applyNonNullParameters(final List<Object> parameters,
                                     final C context) {
        return VALUE_TYPE.getOrFail(parameters, 0);
    }
}