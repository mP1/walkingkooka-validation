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
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.text.LineEnding;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.validation.provider.ValidatorSelector;

import java.util.Locale;
import java.util.Optional;

public interface ValidatorContextDelegator<T extends ValidationReference> extends ValidatorContext<T>,
    ConverterLikeDelegator,
    EnvironmentContextDelegator {

    // EnvironmentContextDelegator......................................................................................

    @Override
    default <TT> ValidatorContext<T> setEnvironmentValue(final EnvironmentValueName<TT> name,
                                                         final TT value) {
        this.environmentContext()
            .setEnvironmentValue(
                name,
                value
            );
        return this;
    }

    @Override
    default ValidatorContext<T> removeEnvironmentValue(final EnvironmentValueName<?> name) {
        throw new UnsupportedOperationException();
    }

    // ValidatorContextDelegator.......................................................................................

    ValidatorContext<T> validatorContext();

    @Override
    default T validationReference() {
        return this.validatorContext()
            .validationReference();
    }

    @Override
    default Validator<T, ? super ValidatorContext<T>> validator(final ValidatorSelector selector) {
        return this.validatorContext()
            .validator(selector);
    }

    @Override
    default ExpressionEvaluationContext expressionEvaluationContext(final Object value) {
        return this.validatorContext()
            .expressionEvaluationContext(value);
    }

    // ConverterLikeDelegator..............................................................................................

    @Override
    default ConverterLike converterLike() {
        return this.validatorContext();
    }

    // EnvironmentContextDelegator......................................................................................

    @Override
    default ValidatorContext<T> setLineEnding(final LineEnding lineEnding) {
        this.environmentContext()
            .setLineEnding(lineEnding);
        return this;
    }

    @Override
    default ValidatorContext<T> setLocale(final Locale locale) {
        this.environmentContext()
            .setLocale(locale);
        return this;
    }

    @Override
    default ValidatorContext<T> setUser(final Optional<EmailAddress> user) {
        this.environmentContext()
            .setUser(user);
        return this;
    }
    
    @Override
    default EnvironmentContext environmentContext() {
        return this.validatorContext();
    }
}
