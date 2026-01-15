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

import walkingkooka.convert.ConverterLike;
import walkingkooka.convert.ConverterLikeDelegator;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentContextDelegator;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.validation.provider.ValidatorSelector;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

final class BasicValidatorContext<T extends ValidationReference> implements ValidatorContext<T>,
    ConverterLikeDelegator,
    EnvironmentContextDelegator {

    static <T extends ValidationReference> BasicValidatorContext<T> with(final T validationReference,
                                                                         final Function<ValidatorSelector, Validator<T, ? super ValidatorContext<T>>> validatorSelectorToValidator,
                                                                         final BiFunction<Object, T, ExpressionEvaluationContext> referenceToExpressionEvaluationContext,
                                                                         final ConverterLike converterLike,
                                                                         final EnvironmentContext environmentContext) {
        return new BasicValidatorContext<>(
            Objects.requireNonNull(validationReference, "validationReference"),
            Objects.requireNonNull(validatorSelectorToValidator, "validatorSelectorToValidator"),
            Objects.requireNonNull(referenceToExpressionEvaluationContext, "referenceToExpressionEvaluationContext"),
            Objects.requireNonNull(converterLike, "converterLike"),
            Objects.requireNonNull(environmentContext, "environmentContext")
        );
    }

    private BasicValidatorContext(final T validationReference,
                                  final Function<ValidatorSelector, Validator<T, ? super ValidatorContext<T>>> validatorSelectorToValidator,
                                  final BiFunction<Object, T, ExpressionEvaluationContext> referenceToExpressionEvaluationContext,
                                  final ConverterLike converterLike,
                                  final EnvironmentContext environmentContext) {
        this.validationReference = validationReference;
        this.validatorSelectorToValidator = validatorSelectorToValidator;
        this.referenceToExpressionEvaluationContext = referenceToExpressionEvaluationContext;
        this.converterLike = converterLike;
        this.environmentContext = environmentContext;
    }

    @Override
    public T validationReference() {
        return this.validationReference;
    }

    private final T validationReference;

    @Override
    public ValidatorContext<T> setValidationReference(final T validationReference) {
        return this.validationReference.equals(validationReference) ?
            this :
            new BasicValidatorContext<>(
                Objects.requireNonNull(validationReference, "validationReference"),
                this.validatorSelectorToValidator,
                this.referenceToExpressionEvaluationContext,
                this.converterLike,
                this.environmentContext
            );
    }

    @Override
    public Validator<T, ? super ValidatorContext<T>> validator(final ValidatorSelector selector) {
        Objects.requireNonNull(selector, "selector");

        return this.validatorSelectorToValidator.apply(selector);
    }

    private final Function<ValidatorSelector, Validator<T, ? super ValidatorContext<T>>> validatorSelectorToValidator;

    @Override
    public ExpressionEvaluationContext expressionEvaluationContext(final Object value) {
        return this.referenceToExpressionEvaluationContext.apply(
            value,
            this.validationReference
        );
    }

    private final BiFunction<Object, T, ExpressionEvaluationContext> referenceToExpressionEvaluationContext;

    // ConverterLikeDelegator...........................................................................................

    @Override
    public ConverterLike converterLike() {
        return this.converterLike;
    }

    // @VisibleForTesting
    final ConverterLike converterLike;

    // EnvironmentContext...............................................................................................

    @Override
    public ValidatorContext<T> cloneEnvironment() {
        return this.setEnvironmentContext(
            this.environmentContext.cloneEnvironment()
        );
    }

    @Override
    public ValidatorContext<T> setEnvironmentContext(final EnvironmentContext environmentContext) {
        return this.environmentContext == environmentContext ?
            this :
            new BasicValidatorContext<>(
                this.validationReference,
                this.validatorSelectorToValidator,
                this.referenceToExpressionEvaluationContext,
                this.converterLike,
                Objects.requireNonNull(environmentContext, "environmentContext")
            );
    }

    // EnvironmentContextDelegator......................................................................................

    @Override
    public EnvironmentContext environmentContext() {
        return this.environmentContext;
    }

    // @VisibleForTesting
    final EnvironmentContext environmentContext;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return Objects.hash(
            this.validationReference,
            this.validatorSelectorToValidator,
            this.referenceToExpressionEvaluationContext,
            this.converterLike,
            this.environmentContext
        );
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            (other instanceof BasicValidatorContext &&
                this.equals0((BasicValidatorContext<?>) other));
    }

    private boolean equals0(final BasicValidatorContext<?> other) {
        return this.validationReference.equals(other.validationReference) &&
            this.validatorSelectorToValidator.equals(other.validatorSelectorToValidator) &&
            this.referenceToExpressionEvaluationContext.equals(other.referenceToExpressionEvaluationContext) &&
            this.converterLike.equals(other.converterLike) &&
            this.environmentContext.equals(other.environmentContext);
    }
    @Override
    public String toString() {
        return this.validationReference +
            " " +
            this.validatorSelectorToValidator +
            " " +
            this.referenceToExpressionEvaluationContext +
            " " +
            this.converterLike +
            " " +
            this.environmentContext;
    }
}
