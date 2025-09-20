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

import walkingkooka.text.CharSequences;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.ExpressionEvaluationContext;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A {@link Validator} that executes the given {@link Expression} expecting choices and verifies the validated value
 * is within the choices.
 */
final class ValidationChoiceListExpressionValidator<R extends ValidationReference, C extends ValidatorContext<R>> implements Validator<R, C> {

    static <R extends ValidationReference, C extends ValidatorContext<R>> ValidationChoiceListExpressionValidator<R, C> with(final Expression expression,
                                                                                                                             final String message) {
        return new ValidationChoiceListExpressionValidator<>(
            Objects.requireNonNull(expression, "expression"),
            CharSequences.failIfNullOrEmpty(message, "message")
        );
    }

    private ValidationChoiceListExpressionValidator(final Expression expression,
                                                    final String message) {
        super();
        this.expression = expression;
        this.message = message;
    }

    // Validator........................................................................................................

    @Override
    public List<ValidationError<R>> validate(final Object value,
                                             final C context) {
        Objects.requireNonNull(context, "context");

        final ValidationChoiceList choices = this.evaluateExpressionToValidationChoiceList(context);

        final Optional<Object> optionalValue = Optional.ofNullable(value);

        return choices.stream()
            .anyMatch((c) -> c.value().equals(optionalValue)) ?
            context.validationErrorList() :
            context.validationErrorList()
                .concat(
                    context.validationError()
                        .setMessage(this.message)
                );
    }

    private final Expression expression;

    private final String message;

    // choices..........................................................................................................

    /**
     * Evaluates the {@link Expression} returning the choices {@link ValidationChoiceList}.
     */
    @Override
    public Optional<List<ValidationChoice>> choices(final ValidatorContext<R> context) {
        Objects.requireNonNull(context, "context");

        return Optional.ofNullable(
            this.evaluateExpressionToValidationChoiceList(context)
        );
    }

    private ValidationChoiceList evaluateExpressionToValidationChoiceList(final ValidatorContext<R> context) {
        final ExpressionEvaluationContext expressionEvaluationContext = context.expressionEvaluationContext(null);

        return expressionEvaluationContext.convertOrFail(
            expressionEvaluationContext.evaluateExpression(this.expression),
            ValidationChoiceList.class
        );
    }

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return Objects.hash(
            this.expression,
            this.message
        );
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof ValidationChoiceListExpressionValidator && this.equals0((ValidationChoiceListExpressionValidator<?, ?>) other);
    }

    private boolean equals0(final ValidationChoiceListExpressionValidator<?, ?> other) {
        return this.expression.equals(other.expression) &&
            this.message.equals(other.message);
    }

    @Override
    public String toString() {
        return this.expression + " " + CharSequences.quoteAndEscape(this.message);
    }
}
