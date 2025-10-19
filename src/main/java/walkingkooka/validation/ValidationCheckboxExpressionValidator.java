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

import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.ExpressionEvaluationContext;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A {@link Validator} which executes the given {@link Expression}, which returns a {@link ValidationCheckbox}.
 * Note the validation value must match one of the values in {@link ValidationCheckbox}.
 */
final class ValidationCheckboxExpressionValidator<R extends ValidationReference, C extends ValidatorContext<R>> implements Validator<R, C> {

    static <R extends ValidationReference, C extends ValidatorContext<R>> ValidationCheckboxExpressionValidator<R, C> with(final Expression expression) {
        return new ValidationCheckboxExpressionValidator<>(
            Objects.requireNonNull(expression, "expression")
        );
    }

    private ValidationCheckboxExpressionValidator(final Expression expression) {
        super();
        this.expression = expression;
    }

    // Validator........................................................................................................

    @Override
    public List<ValidationError<R>> validate(final Object value,
                                             final C context) {
        Objects.requireNonNull(context, "context");

        final ValidationCheckbox checkbox = this.evaluateExpressionToValidationCheckbox(context);
        ValidationError<R> error = context.validationError();

        final Optional<Object> optionalValue = Optional.ofNullable(value);
        if (false == optionalValue.equals(checkbox.trueValue()) && optionalValue.equals(checkbox.falseValue())) {
            error = error.setMessage("Invalid checkbox value");
        }

        return context.validationErrorList()
            .concat(
                error.setValue(
                    Optional.ofNullable(checkbox)
                )
            );
    }

    private final Expression expression;

    // choices..........................................................................................................

    /**
     * Evaluates the {@link Expression} returning the {@link ValidationCheckbox}.
     */
    @Override
    public Optional<ValidationPromptValue> promptValue(final ValidatorContext<R> context) {
        Objects.requireNonNull(context, "context");

        return Optional.ofNullable(
            this.evaluateExpressionToValidationCheckbox(context)
        );
    }

    private ValidationCheckbox evaluateExpressionToValidationCheckbox(final ValidatorContext<R> context) {
        final ExpressionEvaluationContext expressionEvaluationContext = context.expressionEvaluationContext(null);

        return expressionEvaluationContext.convertOrFail(
            expressionEvaluationContext.evaluateExpression(this.expression),
            ValidationCheckbox.class
        );
    }

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.expression.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof ValidationCheckboxExpressionValidator && this.equals0((ValidationCheckboxExpressionValidator<?, ?>) other);
    }

    private boolean equals0(final ValidationCheckboxExpressionValidator<?, ?> other) {
        return this.expression.equals(other.expression);
    }

    @Override
    public String toString() {
        return this.expression.toString();
    }
}
