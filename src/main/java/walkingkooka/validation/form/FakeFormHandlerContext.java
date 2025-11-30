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

package walkingkooka.validation.form;

import walkingkooka.Either;
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.environment.FakeEnvironmentContext;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.text.LineEnding;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.ValidatorContext;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class FakeFormHandlerContext<R extends ValidationReference, S> extends FakeEnvironmentContext implements FormHandlerContext<R, S> {

    public FakeFormHandlerContext() {
        super();
    }

    @Override
    public Form<R> form() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Comparator<R> formFieldReferenceComparator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ValidatorContext<R> validatorContext(final R reference) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Object> loadFormFieldValue(final R reference) {
        throw new UnsupportedOperationException();
    }

    @Override
    public S saveFormFieldValues(final List<FormField<R>> formFields) {
        throw new UnsupportedOperationException();
    }

    // EnvironmentConvert...............................................................................................

    @Override
    public FormHandlerContext<R, S> cloneEnvironment() {
        throw new UnsupportedOperationException();
    }

    @Override
    public FormHandlerContext<R, S> removeEnvironmentValue(final EnvironmentValueName<?> name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public FormHandlerContext<R, S> setLineEnding(final LineEnding lineEnding) {
        throw new UnsupportedOperationException();
    }

    @Override
    public FormHandlerContext<R, S> setLocale(final Locale locale) {
        throw new UnsupportedOperationException();
    }

    @Override
    public FormHandlerContext<R, S> setUser(final Optional<EmailAddress> user) {
        throw new UnsupportedOperationException();
    }

    // CanConvert.......................................................................................................

    @Override
    public boolean canConvert(final Object value,
                              final Class<?> type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> Either<T, String> convert(final Object value,
                                         final Class<T> type) {
        throw new UnsupportedOperationException();
    }
}
