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
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.text.LineEnding;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.validation.provider.ValidatorSelector;

import java.util.Locale;
import java.util.Optional;

/**
 * {@link walkingkooka.Context} that accompanies a {@link Validator} during validation.
 */
public interface ValidatorContext<T extends ValidationReference> extends ConverterLike,
    EnvironmentContext {

    /**
     * The current {@link ValidationReference}.
     * Useful when creating a {@link ValidationError} to report an error.
     */
    T validationReference();

    /**
     * May be used to return a {@link ValidatorContext} with a new or different {@link ValidationReference}.
     */
    ValidatorContext<T> setValidationReference(final T reference);

    /**
     * Returns an empty {@link ValidationErrorList}.
     */
    default ValidationErrorList<T> validationErrorList() {
        return ValidationErrorList.empty();
    }

    /**
     * Factory that creates a {@link ValidationError} using the current {@link ValidationReference}.
     */
    default ValidationError<T> validationError() {
        return ValidationError.with(
            this.validationReference()
        );
    }

    /**
     * Factory that returns a {@link Validator} for the given {@link ValidatorSelector}.
     */
    Validator<T, ? super ValidatorContext<T>> validator(final ValidatorSelector selector);

    /**
     * The preferred name for expressions to get the value being validated. This is the mechanism to pass a validation
     * value to an {@link Expression}.
     */
    String VALIDATION_EXPRESSION_VALUE_REFERENCE_STRING = "value";

    /**
     * Factory that returns a {@link ExpressionEvaluationContext} which may be used to evaluate an {@link Expression}.
     * The value parameter is the value (which may be null) being validated and should be made available at a reference called
     * {@link #VALIDATION_EXPRESSION_VALUE_REFERENCE_STRING}.
     * {@link Optional} isnt used because working with Optionals within an {@link Expression} is cumbersome.
     */
    ExpressionEvaluationContext expressionEvaluationContext(final Object value);

    @Override
    ValidatorContext<T> cloneEnvironment();

    @Override
    ValidatorContext<T> setEnvironmentContext(final EnvironmentContext environmentContext);

    @Override
    ValidatorContext<T> setLineEnding(final LineEnding lineEnding);

    @Override
    ValidatorContext<T> setLocale(final Locale locale);

    @Override
    ValidatorContext<T> setUser(final Optional<EmailAddress> user);

    @Override
    <TT> ValidatorContext<T> setEnvironmentValue(final EnvironmentValueName<TT> name,
                                                 final TT value);

    @Override
    ValidatorContext<T> removeEnvironmentValue(final EnvironmentValueName<?> name);
}
