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

import walkingkooka.convert.FakeConverterContext;
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.text.LineEnding;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.validation.provider.ValidatorSelector;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class FakeValidatorContext<T extends ValidationReference> extends FakeConverterContext implements ValidatorContext<T> {

    public FakeValidatorContext() {
        super();
    }

    @Override
    public T validationReference() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ValidatorContext<T> setValidationReference(final T reference) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Validator<T, ? super ValidatorContext<T>> validator(final ValidatorSelector selector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ExpressionEvaluationContext expressionEvaluationContext(final Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ValidatorContext<T> cloneEnvironment() {
        throw new UnsupportedOperationException();
    }

    @Override
    public LineEnding lineEnding() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ValidatorContext<T> setLineEnding(final LineEnding lineEnding) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ValidatorContext<T> setLocale(final Locale locale) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ValidatorContext<T> setUser(final Optional<EmailAddress> user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> Optional<T> environmentValue(final EnvironmentValueName<T> environmentValueName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<EnvironmentValueName<?>> environmentValueNames() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TT> ValidatorContext<T> setEnvironmentValue(final EnvironmentValueName<TT> name,
                                                        final TT value) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(value, "value");
        throw new UnsupportedOperationException();
    }

    @Override
    public ValidatorContext<T> removeEnvironmentValue(final EnvironmentValueName<?> name) {
        Objects.requireNonNull(name, "name");
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<EmailAddress> user() {
        throw new UnsupportedOperationException();
    }
}
