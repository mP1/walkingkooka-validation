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

import walkingkooka.Cast;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.environment.EnvironmentValueWatcher;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.text.Indentation;
import walkingkooka.text.LineEnding;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.ValidatorContext;
import walkingkooka.validation.form.FormHandlerContextDelegatorTest.TestFormHandlerContextDelegator;

import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public final class FormHandlerContextDelegatorTest implements FormHandlerContextTesting<TestFormHandlerContextDelegator, TestValidationReference, Void> {

    @Override
    public void testEnvironmentValueLineEndingEqualsLineEnding() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testEnvironmentValueLocaleEqualsLocale() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testEnvironmentValueNowEqualsNow() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testEnvironmentValueUserEqualsUser() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testRemoveEnvironmentValueWithNowFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetEnvironmentValueWithNowFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetCurrencyWithDifferentAndWatcher() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetIndentationWithDifferentAndWatcher() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetLineEndingWithDifferentAndWatcher() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetLocaleWithDifferent() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetLocaleWithDifferentAndWatcher() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetTimeOffsetWithDifferentAndWatcher() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetUserWithDifferentAndWatcher() {
        throw new UnsupportedOperationException();
    }

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
                public FormHandlerContext<TestValidationReference, Void> cloneEnvironment() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public FormHandlerContext<TestValidationReference, Void> setEnvironmentContext(final EnvironmentContext environmentContext) {
                    Objects.requireNonNull(environmentContext, "environmentContext");
                    throw new UnsupportedOperationException();
                }

                @Override
                public <T> Optional<T> environmentValue(final EnvironmentValueName<T> name) {
                    Objects.requireNonNull(name, "name");

                    return Cast.to(
                        CURRENCY.equals(name) ?
                            Optional.of(
                                this.currency()
                            ) :
                            INDENTATION.equals(name) ?
                                Optional.of(
                                    this.indentation()
                                ) :
                                LOCALE.equals(name) ?
                                    Optional.of(
                                        this.locale()
                                    ) :
                                    LINE_ENDING.equals(name) ?
                                        Optional.of(
                                            this.lineEnding()
                                        ) :
                                        USER.equals(name) ?
                                            this.user() :
                                            Optional.empty()
                    );
                }

                @Override
                public <T> void setEnvironmentValue(final EnvironmentValueName<T> name,
                                                    final T value) {
                    Objects.requireNonNull(name, "name");
                    Objects.requireNonNull(value, "value");
                    throw new UnsupportedOperationException();
                }

                @Override
                public void removeEnvironmentValue(final EnvironmentValueName<?> name) {
                    Objects.requireNonNull(name, "name");
                    throw new UnsupportedOperationException();
                }

                @Override
                public Currency currency() {
                    return Currency.getInstance("AUD");
                }

                @Override
                public Indentation indentation() {
                    return Indentation.SPACES2;
                }

                @Override
                public LineEnding lineEnding() {
                    return LineEnding.NL;
                }

                @Override
                public Locale locale() {
                    return Locale.ENGLISH;
                }

                @Override
                public void setLocale(final Locale locale) {
                    Objects.requireNonNull(locale, "locale");
                    throw new UnsupportedOperationException();
                }

                @Override
                public Optional<EmailAddress> user() {
                    return Optional.of(
                        EmailAddress.parse("user@example.com")
                    );
                }

                @Override
                public void setUser(final Optional<EmailAddress> user) {
                    Objects.requireNonNull(user, "user");
                    throw new UnsupportedOperationException();
                }
            };
        }

        @Override
        public FormHandlerContext<TestValidationReference, Void> cloneEnvironment() {
            throw new UnsupportedOperationException();
        }

        @Override
        public FormHandlerContext<TestValidationReference, Void> setEnvironmentContext(final EnvironmentContext environmentContext) {
            Objects.requireNonNull(environmentContext, "environmentContext");

            return new TestFormHandlerContextDelegator();
        }

        @Override
        public <T> void setEnvironmentValue(final EnvironmentValueName<T> name,
                                            final T value) {
            Objects.requireNonNull(name, "name");
            Objects.requireNonNull(value, "value");
            throw new UnsupportedOperationException();
        }

        @Override
        public void setLineEnding(final LineEnding lineEnding) {
            Objects.requireNonNull(lineEnding, "lineEnding");
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setLocale(final Locale locale) {
            Objects.requireNonNull(locale, "locale");
            throw new UnsupportedOperationException();
        }

        @Override
        public void setUser(final Optional<EmailAddress> user) {
            Objects.requireNonNull(user, "user");
            throw new UnsupportedOperationException();
        }

        @Override
        public Runnable addEventValueWatcher(final EnvironmentValueWatcher watcher) {
            Objects.requireNonNull(watcher, "watcher");
            throw new UnsupportedOperationException();
        }

        @Override
        public Runnable addEventValueWatcherOnce(final EnvironmentValueWatcher watcher) {
            Objects.requireNonNull(watcher, "watcher");
            throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }
}
