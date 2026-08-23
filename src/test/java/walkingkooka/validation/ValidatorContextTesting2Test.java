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

import org.junit.jupiter.api.Test;
import walkingkooka.convert.BinaryNumberConverterFunctions;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.ConverterContextDelegator;
import walkingkooka.convert.ConverterContexts;
import walkingkooka.convert.Converters;
import walkingkooka.currency.CurrencyCode;
import walkingkooka.currency.CurrencyLocaleContexts;
import walkingkooka.datetime.DateTimeContextTesting;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentContextDelegator;
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.math.DecimalNumberContextTesting;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.text.Indentation;
import walkingkooka.text.LineEnding;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.ExpressionEvaluationContexts;
import walkingkooka.validation.ValidatorContextTesting2Test.TestValidatorContext;
import walkingkooka.validation.provider.ValidatorSelector;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public final class ValidatorContextTesting2Test implements ValidatorContextTesting2<TestValidatorContext, TestValidationReference>,
    DateTimeContextTesting,
    DecimalNumberContextTesting {

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
    public void testSetTimeOffsetWithDifferentAndWatcher() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TestValidatorContext createContext() {
        return new TestValidatorContext();
    }

    // HasEnvironmentContext............................................................................................

    @Test
    @Override
    public void testEnvironmentContext() {
        final TestValidatorContext context = new TestValidatorContext();

        this.environmentContextAndCheck(
            context,
            context.environmentContext
        );
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
        public Charset charset() {
            return this.environmentContext.charset();
        }

        @Override
        public void setCharset(final Charset charset) {
            this.environmentContext.setCharset(charset);
        }
        
        @Override
        public Indentation indentation() {
            return this.environmentContext.indentation();
        }

        @Override
        public void setIndentation(final Indentation indentation) {
            this.environmentContext.setIndentation(indentation);
        }
        
        @Override
        public LineEnding lineEnding() {
            return this.environmentContext.lineEnding();
        }

        @Override
        public void setLineEnding(final LineEnding lineEnding) {
            this.environmentContext.setLineEnding(lineEnding);
        }
        
        @Override
        public Locale locale() {
            return this.environmentContext.locale();
        }

        @Override
        public void setLocale(final Locale locale) {
            this.environmentContext.setLocale(locale);
        }

        @Override
        public void setUser(final Optional<EmailAddress> user) {
            this.environmentContext.setUser(user);
        }

        @Override
        public ExpressionEvaluationContext expressionEvaluationContext(final Object value) {
            return ExpressionEvaluationContexts.fake();
        }

        @Override
        public CurrencyCode currencyCode() {
            return CONVERTER_CONTEXT.currencyCode();
        }

        @Override
        public final ConverterContext converterContext() {
            return CONVERTER_CONTEXT;
        }

        @Override
        public LocalDateTime now() {
            return this.environmentContext.now();
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
            return this.environmentContext;
        }

        private final EnvironmentContext environmentContext = ENVIRONMENT_CONTEXT.cloneEnvironment();

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }

    private final static ConverterContext CONVERTER_CONTEXT = ConverterContexts.basic(
        false, // canNumbersHaveGroupSeparator
        Converters.EXCEL_1900_DATE_SYSTEM_OFFSET, // dateOffset
        ',', // valueSeparator
        Converters.objectToString(),
        BinaryNumberConverterFunctions.fake(), // multiplier
        BINARY_TEXT_CONTEXT,
        CurrencyLocaleContexts.fake(),
        DATE_TIME_CONTEXT,
        DECIMAL_NUMBER_CONTEXT
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
