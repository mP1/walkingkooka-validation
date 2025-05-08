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
import walkingkooka.collect.set.Sets;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.ExpressionPurityContext;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterKind;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterName;
import walkingkooka.validation.ValidationError;
import walkingkooka.validation.ValidationReference;

import java.util.List;

/**
 * A function which returns a {@link ValidationError} if the {@link Expression} evaluates to true.
 */
final class ValidationExpressionFunctionValidationErrorIf<R extends ValidationReference, C extends ValidatorExpressionEvaluationContext<R>> extends ValidationExpressionFunction<ValidationError<R>, R, C> {

    /**
     * Type safe getter.
     */
    static <R extends ValidationReference, C extends ValidatorExpressionEvaluationContext<R>> ValidationExpressionFunctionValidationErrorIf<R, C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private static final ValidationExpressionFunctionValidationErrorIf<?, ?> INSTANCE = new ValidationExpressionFunctionValidationErrorIf<>();

    private ValidationExpressionFunctionValidationErrorIf() {
        super("validationErrorIf");
    }

    /**
     * The {@link Expression} which will be evaluated.
     */
    private final static ExpressionFunctionParameter<Expression> EXPRESSION = ExpressionFunctionParameterName.with("if")
        .required(Expression.class)
        .setKinds(Sets.of(ExpressionFunctionParameterKind.CONVERT));

    /**
     * The {@link ValidationError} which will be returned if the expression is true.
     */
    private final static ExpressionFunctionParameter<ValidationError> VALIDATION_ERROR = ExpressionFunctionParameterName.with("validationError")
        .required(ValidationError.class)
        .setKinds(Sets.of(ExpressionFunctionParameterKind.CONVERT));

    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = ExpressionFunctionParameter.list(
        EXPRESSION,
        VALIDATION_ERROR
    );

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    @Override
    public Class<ValidationError<R>> returnType() {
        return Cast.to(ValidationError.class);
    }

    @Override
    public boolean isPure(final ExpressionPurityContext context) {
        return false;
    }

    @Override
    ValidationError<R> applyNonNullParameters(final List<Object> parameters,
                                              final C context) {

        final Expression expression = EXPRESSION.getOrFail(parameters, 0);

        return context.convertOrFail(
            context.evaluateExpression(expression),
            Boolean.class
        ).booleanValue() ?
            VALIDATION_ERROR.getOrFail(parameters, 1) :
            null;
    }
}