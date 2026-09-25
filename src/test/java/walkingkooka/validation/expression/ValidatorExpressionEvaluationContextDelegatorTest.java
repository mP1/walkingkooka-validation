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

package walkingkooka.validation.expression;

import org.junit.jupiter.api.Test;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.environment.EnvironmentWatcher;
import walkingkooka.locale.LocaleLanguageTag;
import walkingkooka.logging.LoggerPath;
import walkingkooka.logging.LoggingLevel;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.math.DecimalNumberContextDelegator;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.text.Indentation;
import walkingkooka.text.LineEnding;
import walkingkooka.tree.expression.ExpressionFunctionName;
import walkingkooka.tree.expression.ExpressionReference;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.expression.ValidatorExpressionEvaluationContextDelegatorTest.TestValidatorExpressionEvaluationContextDelegator;
import walkingkooka.validation.form.Form;

import java.math.MathContext;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public final class ValidatorExpressionEvaluationContextDelegatorTest implements ValidatorExpressionEvaluationContextTesting2<TestValidationReference, TestValidatorExpressionEvaluationContextDelegator>,
    DecimalNumberContextDelegator {

    private final static Optional<Object> VALIDATION_VALUE = Optional.of("ValidationValue123");

    @Override
    public void testDateTimeSymbolsForLocaleWithNullFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testDecimalNumberSymbolsForLocaleWithNullFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testLocaleTextWithNullFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetLineEndingWithDifferentAndWatcher() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void testValidationValue() {
        this.validationValueAndCheck(
            this.createContext(),
            VALIDATION_VALUE
        );
    }

    @Override
    public TestValidatorExpressionEvaluationContextDelegator createContext() {
        return new TestValidatorExpressionEvaluationContextDelegator();
    }

    @Override
    public int decimalNumberDigitCount() {
        return DECIMAL_NUMBER_CONTEXT.decimalNumberDigitCount();
    }

    @Override
    public MathContext mathContext() {
        return DECIMAL_NUMBER_CONTEXT.mathContext();
    }

    // DecimalNumberContextDelegator....................................................................................

    @Override
    public DecimalNumberContext decimalNumberContext() {
        return DECIMAL_NUMBER_CONTEXT;
    }

    // HasEnvironmentContext............................................................................................

    @Test
    @Override
    public void testEnvironmentContext() {
        final TestValidatorExpressionEvaluationContextDelegator context = new TestValidatorExpressionEvaluationContextDelegator();

        this.environmentContextAndCheck(
            context,
            context.expressionEvaluationContext
        );
    }

    // class............................................................................................................

    @Override
    public void testEvaluateExpressionUnknownFunctionNameFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testTestNaming() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<TestValidatorExpressionEvaluationContextDelegator> type() {
        return TestValidatorExpressionEvaluationContextDelegator.class;
    }

    static final class TestValidatorExpressionEvaluationContextDelegator implements ValidatorExpressionEvaluationContextDelegator<TestValidationReference> {

        @Override
        public ValidatorExpressionEvaluationContext<TestValidationReference> expressionEvaluationContext() {
            return this.expressionEvaluationContext;
        }

        private final FakeValidatorExpressionEvaluationContext<TestValidationReference> expressionEvaluationContext = new FakeValidatorExpressionEvaluationContext<>() {

            @Override
            public Optional<Object> validationValue() {
                return VALIDATION_VALUE;
            }

            @Override
            public FakeValidatorExpressionEvaluationContext<TestValidationReference> enterScope(final Function<ExpressionReference, Optional<Optional<Object>>> function) {
                Objects.requireNonNull(function, "function");

                throw new UnsupportedOperationException();
            }

            @Override
            public Object evaluate(final String expression) {
                Objects.requireNonNull(expression, "expression");
                throw new UnsupportedOperationException();
            }

            @Override
            public String currencySymbol() {
                return DECIMAL_NUMBER_CONTEXT.currencySymbol();
            }

            @Override
            public int decimalNumberDigitCount() {
                return DECIMAL_NUMBER_CONTEXT.decimalNumberDigitCount();
            }

            @Override
            public char decimalSeparator() {
                return DECIMAL_NUMBER_CONTEXT.decimalSeparator();
            }

            @Override
            public String exponentSymbol() {
                return DECIMAL_NUMBER_CONTEXT.exponentSymbol();
            }

            @Override
            public char groupSeparator() {
                return DECIMAL_NUMBER_CONTEXT.groupSeparator();
            }

            @Override
            public String infinitySymbol() {
                return DECIMAL_NUMBER_CONTEXT.infinitySymbol();
            }

            @Override
            public MathContext mathContext() {
                return DECIMAL_NUMBER_CONTEXT.mathContext();
            }

            @Override
            public char monetaryDecimalSeparator() {
                return DECIMAL_NUMBER_CONTEXT.monetaryDecimalSeparator();
            }

            @Override
            public String nanSymbol() {
                return DECIMAL_NUMBER_CONTEXT.nanSymbol();
            }

            @Override
            public char negativeSign() {
                return DECIMAL_NUMBER_CONTEXT.negativeSign();
            }

            @Override
            public char percentSymbol() {
                return DECIMAL_NUMBER_CONTEXT.percentSymbol();
            }

            @Override
            public char permillSymbol() {
                return DECIMAL_NUMBER_CONTEXT.permillSymbol();
            }

            @Override
            public char positiveSign() {
                return DECIMAL_NUMBER_CONTEXT.positiveSign();
            }

            @Override
            public char zeroDigit() {
                return DECIMAL_NUMBER_CONTEXT.zeroDigit();
            }

            @Override
            public boolean isPure(final ExpressionFunctionName name) {
                Objects.requireNonNull(name, "name");

                throw new UnsupportedOperationException();
            }

            @Override
            public Optional<Optional<Object>> reference(final ExpressionReference reference) {
                Objects.requireNonNull(reference, "reference");

                throw new UnsupportedOperationException();
            }

            @Override
            public Form<TestValidationReference> form() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Set<Locale> findLocaleByText(final String text,
                                                final int offset,
                                                final int count) {
                return LOCALE_CONTEXT.findLocaleByText(
                    text,
                    offset,
                    count
                );
            }

            @Override
            public Optional<Locale> localeForLanguageTag(final LocaleLanguageTag languageTag) {
                Objects.requireNonNull(languageTag, "languageTag");
                throw new UnsupportedOperationException();
            }

            @Override
            public <T> Optional<T> environmentValue(final EnvironmentValueName<T> name) {
                return this.environmentContext.environmentValue(name);
            }

            @Override
            public <T> void setEnvironmentValue(final EnvironmentValueName<T> name,
                                                final T value) {
                this.environmentContext.setEnvironmentValue(
                    name,
                    value
                );
            }

            @Override
            public void removeEnvironmentValue(final EnvironmentValueName<?> name) {
                this.environmentContext.removeEnvironmentValue(name);
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
            public LocalDateTime now() {
                return this.environmentContext.now();
            }

            @Override
            public void setUser(final Optional<EmailAddress> user) {
                this.environmentContext.setUser(user);
            }

            @Override
            public Runnable addEnvironmentWatcher(final EnvironmentWatcher watcher) {
                return this.environmentContext.addEnvironmentWatcher(watcher);
            }

            @Override
            public Runnable addEnvironmentWatcherOnce(final EnvironmentWatcher watcher) {
                return this.environmentContext.addEnvironmentWatcherOnce(watcher);
            }

            @Override
            public EnvironmentValueName<?> parseEnvironmentValueName(final String name) {
                return this.environmentContext.parseEnvironmentValueName(name);
            }

            @Override
            public boolean isLoggingEnabled(final LoggingLevel level) {
                return this.environmentContext.isLoggingEnabled(level);
            }

            @Override
            public void logEnter(final LoggerPath logger) {
                this.environmentContext.logEnter(logger);
            }

            @Override
            public void logExit() {
                this.environmentContext.logExit();
            }

            @Override
            public void log(final LoggingLevel loggingLevel,
                            final String message,
                            final Throwable throwable) {
                this.environmentContext.log(
                    loggingLevel,
                    message,
                    throwable
                );
            }

            private final EnvironmentContext environmentContext = ENVIRONMENT_CONTEXT.cloneEnvironment();
        };

        @Override
        public ValidatorExpressionEvaluationContext<TestValidationReference> enterScope(final Function<ExpressionReference, Optional<Optional<Object>>> function) {
            Objects.requireNonNull(function, "function");

            return new TestValidatorExpressionEvaluationContextDelegator();
        }

        @Override
        public Optional<Optional<Object>> reference(final ExpressionReference reference) {
            Objects.requireNonNull(reference, "reference");

            throw new UnsupportedOperationException();
        }

        @Override
        public TestValidatorExpressionEvaluationContextDelegator cloneEnvironment() {
            return new TestValidatorExpressionEvaluationContextDelegator();
        }

        @Override
        public TestValidatorExpressionEvaluationContextDelegator setEnvironmentContext(final EnvironmentContext context) {
            Objects.requireNonNull(context, "context");

            return new TestValidatorExpressionEvaluationContextDelegator();
        }

        @Override
        public EnvironmentContext environmentContext() {
            return this.expressionEvaluationContext;
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }
}
