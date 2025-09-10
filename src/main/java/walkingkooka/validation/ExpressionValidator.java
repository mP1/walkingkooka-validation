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

import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.expression.Expression;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A {@link Validator} which executes the given {@link Expression} passing the validation value as a reference called VALUE.
 * The expression must return null or an empty list to indicate no validation errors.
 */
final class ExpressionValidator<R extends ValidationReference, C extends ValidatorContext<R>> implements Validator<R, C> {

    static <R extends ValidationReference, C extends ValidatorContext<R>> ExpressionValidator<R, C> with(final Expression expression) {
        return new ExpressionValidator<>(
            Objects.requireNonNull(expression, "expression")
        );
    }

    private ExpressionValidator(final Expression expression) {
        super();
        this.expression = expression;
    }

    // Validator........................................................................................................

    @Override
    public List<ValidationError<R>> validate(final Object value,
                                             final C context) {
        Objects.requireNonNull(context, "context");

        final Object errors = Cast.to(
            context.expressionEvaluationContext(value)
            .evaluateExpression(this.expression)
        );

        final ValidationErrorList<R> validationErrors;
        if(errors instanceof ValidationError) {
            validationErrors = ValidationErrorList.with(
                Lists.of(
                (ValidationError<R>) errors
            )
            );
        } else {
            validationErrors = ValidationErrorList.with(
                Cast.to(errors)
            );
        }

        return validationErrors;
    }

    private final Expression expression;

    // choices..........................................................................................................

    /**
     * Note the expression is ignored, and this always returns no choices.
     */
    @Override
    public Optional<List<ValidationChoice>> choices(final ValidatorContext<R> context) {
        Objects.requireNonNull(context, "context");
        return NO_CHOICES;
    }

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.expression.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof ExpressionValidator && this.equals0((ExpressionValidator<?, ?>) other);
    }

    private boolean equals0(final ExpressionValidator<?, ?> other) {
        return this.expression.equals(other.expression);
    }

    @Override
    public String toString() {
        return this.expression.toString();
    }
}
