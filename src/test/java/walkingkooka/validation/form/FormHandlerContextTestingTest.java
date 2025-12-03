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
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.text.LineEnding;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.ValidatorContext;
import walkingkooka.validation.form.FormHandlerContextTestingTest.TestFormHandlerContext;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class FormHandlerContextTestingTest implements FormHandlerContextTesting<TestFormHandlerContext, TestValidationReference, Void> {

    @Override
    public void testEnvironmentValueLocaleEqualsLocale() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testEnvironmentValueUserEqualsUser() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetLocaleWithDifferent() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TestFormHandlerContext createContext() {
        return new TestFormHandlerContext();
    }

    @Override
    public Class<TestFormHandlerContext> type() {
        return TestFormHandlerContext.class;
    }

    static class TestFormHandlerContext implements FormHandlerContext<TestValidationReference, Void> {

        @Override
        public Form<TestValidationReference> form() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Comparator<TestValidationReference> formFieldReferenceComparator() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ValidatorContext<TestValidationReference> validatorContext(final TestValidationReference reference) {
            Objects.requireNonNull(reference, "reference");

            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<Object> loadFormFieldValue(final TestValidationReference reference) {
            Objects.requireNonNull(reference, "reference");
            throw new UnsupportedOperationException();
        }

        @Override
        public Void saveFormFieldValues(final List<FormField<TestValidationReference>> formFields) {
            Objects.requireNonNull(formFields, "formFields");

            throw new UnsupportedOperationException();
        }

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

        @Override
        public LineEnding lineEnding() {
            return LineEnding.NL;
        }

        @Override
        public FormHandlerContext<TestValidationReference, Void> setLineEnding(final LineEnding lineEnding) {
            Objects.requireNonNull(lineEnding, "lineEnding");
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Locale locale() {
            return Locale.ENGLISH;
        }

        @Override
        public FormHandlerContext<TestValidationReference, Void> setLocale(final Locale locale) {
            Objects.requireNonNull(locale, "locale");
            throw new UnsupportedOperationException();
        }

        @Override
        public FormHandlerContext<TestValidationReference, Void> setUser(final Optional<EmailAddress> user) {
            Objects.requireNonNull(user, "user");
            throw new UnsupportedOperationException();
        }

        @Override
        public FormHandlerContext<TestValidationReference, Void> cloneEnvironment() {
            throw new UnsupportedOperationException();
        }

        @Override
        public FormHandlerContext<TestValidationReference, Void> setEnvironmentContext(final EnvironmentContext environmentContext) {
            Objects.requireNonNull(environmentContext, "environmentContext");
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> Optional<T> environmentValue(final EnvironmentValueName<T> environmentValueName) {
            Objects.requireNonNull(environmentValueName, "environmentValueName");

            throw new UnsupportedOperationException();
        }

        @Override
        public <T> TestFormHandlerContext setEnvironmentValue(final EnvironmentValueName<T> name,
                                                              final T value) {
            Objects.requireNonNull(name, "name");
            Objects.requireNonNull(value, "value");
            throw new UnsupportedOperationException();
        }

        @Override
        public TestFormHandlerContext removeEnvironmentValue(final EnvironmentValueName<?> name) {
            Objects.requireNonNull(name, "name");
            throw new UnsupportedOperationException();
        }

        @Override
        public Set<EnvironmentValueName<?>> environmentValueNames() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<EmailAddress> user() {
            return Optional.empty();
        }

        @Override
        public LocalDateTime now() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }
}
