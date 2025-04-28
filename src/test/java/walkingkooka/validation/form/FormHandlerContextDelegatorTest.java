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

import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.ValidatorContext;
import walkingkooka.validation.form.FormHandlerContextDelegatorTest.TestFormHandlerContextDelegator;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class FormHandlerContextDelegatorTest implements FormHandlerContextTesting<TestFormHandlerContextDelegator, TestValidationReference, Void> {

    @Override
    public TestFormHandlerContextDelegator createContext() {
        return new TestFormHandlerContextDelegator();
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }

    // class............................................................................................................

    @Override
    public Class<TestFormHandlerContextDelegator> type() {
        return TestFormHandlerContextDelegator.class;
    }

    final static class TestFormHandlerContextDelegator implements FormHandlerContextDelegator<TestValidationReference, Void> {

        @Override
        public FormHandlerContext<TestValidationReference, Void> formHandlerContext() {
            return new FakeFormHandlerContext<>() {

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
                public <T> Optional<T> environmentValue(final EnvironmentValueName<T> name) {
                    Objects.requireNonNull(name, "name");

                    throw new UnsupportedOperationException();
                }

                @Override
                public Optional<EmailAddress> user() {
                    return Optional.of(
                        EmailAddress.parse("user@example.com")
                    );
                }
            };
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }
}
