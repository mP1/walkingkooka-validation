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

import walkingkooka.convert.CanConvert;
import walkingkooka.convert.CanConvertDelegator;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentContextDelegator;
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.validation.provider.ValidatorSelector;

import java.util.Locale;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

final class BasicValidatorContext<T extends ValidationReference> implements ValidatorContext<T>,
    CanConvertDelegator,
    EnvironmentContextDelegator {

    static <T extends ValidationReference> BasicValidatorContext<T> with(final T validationReference,
                                                                         final Function<ValidatorSelector, Validator<T, ? super ValidatorContext<T>>> validatorSelectorToValidator,
                                                                         final BiFunction<Object, T, ExpressionEvaluationContext> referenceToExpressionEvaluationContext,
                                                                         final CanConvert canConvert,
                                                                         final EnvironmentContext environmentContext) {
        return new BasicValidatorContext<>(
            Objects.requireNonNull(validationReference, "validationReference"),
            Objects.requireNonNull(validatorSelectorToValidator, "validatorSelectorToValidator"),
            Objects.requireNonNull(referenceToExpressionEvaluationContext, "referenceToExpressionEvaluationContext"),
            Objects.requireNonNull(canConvert, "canConvert"),
            Objects.requireNonNull(environmentContext, "environmentContext")
        );
    }

    private BasicValidatorContext(final T validationReference,
                                  final Function<ValidatorSelector, Validator<T, ? super ValidatorContext<T>>> validatorSelectorToValidator,
                                  final BiFunction<Object, T, ExpressionEvaluationContext> referenceToExpressionEvaluationContext,
                                  final CanConvert canConvert,
                                  final EnvironmentContext environmentContext) {
        this.validationReference = validationReference;
        this.validatorSelectorToValidator = validatorSelectorToValidator;
        this.referenceToExpressionEvaluationContext = referenceToExpressionEvaluationContext;
        this.canConvert = canConvert;
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
                this.canConvert,
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

    // CanConvertDelegator..............................................................................................

    @Override
    public CanConvert canConvert() {
        return this.canConvert;
    }

    // @VisibleForTesting
    final CanConvert canConvert;

    // EnvironmentContext...............................................................................................

    @Override
    public ValidatorContext<T> setLocale(final Locale locale) {
        this.environmentContext.setLocale(locale);
        return this;
    }

    @Override
    public <TT> BasicValidatorContext<T> setEnvironmentValue(final EnvironmentValueName<TT> name,
                                                             final TT value) {
        this.environmentContext.setEnvironmentValue(
            name,
            value
        );
        return this;
    }

    @Override
    public ValidatorContext<T> removeEnvironmentValue(final EnvironmentValueName<?> name) {
        this.environmentContext.removeEnvironmentValue(
            name
        );
        return this;
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
    public String toString() {
        return this.validationReference +
            " " +
            this.validatorSelectorToValidator +
            " " +
            this.referenceToExpressionEvaluationContext +
            " " +
            this.canConvert +
            " " +
            this.environmentContext;
    }
}
