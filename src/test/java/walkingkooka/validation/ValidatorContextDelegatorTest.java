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
import walkingkooka.convert.ConverterContexts;
import walkingkooka.convert.Converters;
import walkingkooka.datetime.DateTimeContexts;
import walkingkooka.datetime.DateTimeSymbols;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentContexts;
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.text.LineEnding;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.validation.ValidatorContextDelegatorTest.TestValidatorContext;
import walkingkooka.validation.provider.ValidatorSelector;

import java.math.MathContext;
import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class ValidatorContextDelegatorTest implements ValidatorContextTesting<TestValidatorContext, TestValidationReference> {

    private final static LineEnding LINE_ENDING = LineEnding.NL;

    private final static Locale LOCALE = Locale.ENGLISH;

    private final static TestValidationReference VALIDATION_REFERENCE = new TestValidationReference("A1");

    private final static Function<ValidatorSelector, Validator<TestValidationReference, ? super ValidatorContext<TestValidationReference>>> VALIDATOR_SELECTOR_TO_VALIDATOR = (final ValidatorSelector selector) -> {
        throw new UnsupportedOperationException();
    };

    private final static BiFunction<Object, TestValidationReference, ExpressionEvaluationContext> REFERENCE_EXPRESSION_EVALUATION_CONTEXT_FUNCTION = (final Object value,
                                                                                                                                                      final TestValidationReference validationReference) -> {
        throw new UnsupportedOperationException();
    };

    private final static DecimalNumberContext DECIMAL_NUMBER_CONTEXT = DecimalNumberContexts.american(MathContext.DECIMAL32);

    private final static ConverterContext CONVERTER_CONTEXT = ConverterContexts.basic(
        false, // canNumbersHaveGroupSeparator
        Converters.EXCEL_1900_DATE_SYSTEM_OFFSET, // offset
        ',', // valueSeparator
        Converters.simple(),
        DateTimeContexts.basic(
            DateTimeSymbols.fromDateFormatSymbols(
                new DateFormatSymbols(LOCALE)
            ),
            LOCALE, // locale
            1950, // defaultYear
            50, // twoDigitYear
            LocalDateTime::now
        ),
        DECIMAL_NUMBER_CONTEXT
    );

    private final static EnvironmentContext ENVIRONMENT_CONTEXT = EnvironmentContexts.readOnly(
        EnvironmentContexts.empty(
            LINE_ENDING,
            LOCALE,
            LocalDateTime::now, // now
            Optional.of(
                EmailAddress.parse("user@example.com")
            )
        )
    );

    @Override
    public void testRemoveEnvironmentValueWithNowFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetEnvironmentValueWithNowFails() {
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

    final static class TestValidatorContext implements ValidatorContextDelegator<TestValidationReference> {

        TestValidatorContext() {
            this(
                ValidatorContexts.basic(
                    VALIDATION_REFERENCE,
                    VALIDATOR_SELECTOR_TO_VALIDATOR,
                    REFERENCE_EXPRESSION_EVALUATION_CONTEXT_FUNCTION,
                    CONVERTER_CONTEXT,
                    ENVIRONMENT_CONTEXT
                )
            );
        }

        TestValidatorContext(final ValidatorContext<TestValidationReference> context) {
            this.context = context;
        }

        @Override
        public ValidatorContext<TestValidationReference> setValidationReference(final TestValidationReference reference) {
            if(this.validatorContext().validationReference().equals(reference)) {
                return this;
            }

            return new TestValidatorContext(
                this.validatorContext()
                    .setValidationReference(reference)
            );
        }

        @Override
        public ValidatorContext<TestValidationReference> validatorContext() {
            return this.context;
        }

        private final ValidatorContext<TestValidationReference> context;

        @Override
        public ValidatorContext<TestValidationReference> setLocale(final Locale locale) {
            Objects.requireNonNull(locale, "locale");
            throw new UnsupportedOperationException();
        }

        @Override
        public ValidatorContext<TestValidationReference> setUser(final Optional<EmailAddress> user) {
            Objects.requireNonNull(user, "user");
            throw new UnsupportedOperationException();
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
        public <T> TestValidatorContext setEnvironmentValue(final EnvironmentValueName<T> name,
                                                            final T value) {
            Objects.requireNonNull(name, "name");
            Objects.requireNonNull(value, "value");
            throw new UnsupportedOperationException();
        }

        @Override
        public TestValidatorContext removeEnvironmentValue(final EnvironmentValueName<?> name) {
            Objects.requireNonNull(name, "name");
            throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }

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
