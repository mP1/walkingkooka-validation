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

import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.ConverterContextDelegator;
import walkingkooka.convert.ConverterContexts;
import walkingkooka.convert.Converters;
import walkingkooka.datetime.DateTimeContexts;
import walkingkooka.datetime.DateTimeSymbols;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentContextDelegator;
import walkingkooka.environment.EnvironmentContexts;
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.text.Indentation;
import walkingkooka.text.LineEnding;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.ExpressionEvaluationContexts;
import walkingkooka.validation.ValidatorContextTestingTest.TestValidatorContext;
import walkingkooka.validation.provider.ValidatorSelector;

import java.math.MathContext;
import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public final class ValidatorContextTestingTest implements ValidatorContextTesting<TestValidatorContext, TestValidationReference> {

    private final static LineEnding LINE_ENDING = LineEnding.NL;

    private final static Locale LOCALE = Locale.ENGLISH;

    @Override
    public void testRemoveEnvironmentValueWithNowFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetEnvironmentValueWithNowFails() {
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
    public void testSetUserWithDifferentAndWatcher() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TestValidatorContext createContext() {
        return new TestValidatorContext();
    }

    static class TestValidatorContext implements ValidatorContext<TestValidationReference>,
        ConverterContextDelegator,
        EnvironmentContextDelegator {

        TestValidatorContext() {
            this(new TestValidationReference("A1"));
        }

        TestValidatorContext(final TestValidationReference reference) {
            this.reference = reference;
        }

        @Override
        public TestValidationReference validationReference() {
            return this.reference;
        }

        private final TestValidationReference reference;

        @Override
        public ValidatorContext<TestValidationReference> setValidationReference(final TestValidationReference reference) {
            return this.reference.equals(reference) ?
                this :
                new TestValidatorContext(
                    Objects.requireNonNull(reference, "reference")
                );
        }

        @Override
        public Validator<TestValidationReference, ? super ValidatorContext<TestValidationReference>> validator(final ValidatorSelector selector) {
            Objects.requireNonNull(selector, "selector");

            throw new UnsupportedOperationException();
        }

        @Override
        public Indentation indentation() {
            return ValidatorContextTestingTest.INDENTATION;
        }

        @Override
        public void setIndentation(final Indentation indentation) {
            Objects.requireNonNull(indentation, "indentation");
            throw new UnsupportedOperationException();
        }
        
        @Override
        public LineEnding lineEnding() {
            return ValidatorContextTestingTest.LINE_ENDING;
        }

        @Override
        public void setLineEnding(final LineEnding lineEnding) {
            Objects.requireNonNull(lineEnding, "lineEnding");
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Locale locale() {
            return ValidatorContextTestingTest.LOCALE;
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
        public ExpressionEvaluationContext expressionEvaluationContext(final Object value) {
            return ExpressionEvaluationContexts.fake();
        }

        @Override
        public final ConverterContext converterContext() {
            return CONVERTER_CONTEXT;
        }

        @Override
        public LocalDateTime now() {
            return CONVERTER_CONTEXT.now();
        }

        @Override
        public TestValidatorContext cloneEnvironment() {
            throw new UnsupportedOperationException();
        }

        @Override
        public TestValidatorContext setEnvironmentContext(final EnvironmentContext environmentContext) {
            Objects.requireNonNull(environmentContext, "environmentContext");
            return new TestValidatorContext();
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
        public EnvironmentContext environmentContext() {
            return EnvironmentContexts.empty(
                Indentation.SPACES2,
                LineEnding.NL,
                ValidatorContextTestingTest.LOCALE,
                this,
                Optional.empty()
            );
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }

    private final static ConverterContext CONVERTER_CONTEXT = ConverterContexts.basic(
        false, // canNumbersHaveGroupSeparator
        Converters.EXCEL_1900_DATE_SYSTEM_OFFSET, // dateOffset
        Indentation.SPACES2,
        LineEnding.NL,
        ',', // valueSeparator
        Converters.objectToString(),
        DateTimeContexts.basic(
            DateTimeSymbols.fromDateFormatSymbols(
                new DateFormatSymbols(LOCALE)
            ),
            LOCALE,
            1950,
            50,
            () -> LocalDateTime.of(
                1999,
                12,
                31,
                12,
                58,
                59
            )
        ),
        DecimalNumberContexts.american(
            MathContext.DECIMAL32
        )
    );

    // class............................................................................................................

    @Override
    public Class<TestValidatorContext> type() {
        return TestValidatorContext.class;
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
