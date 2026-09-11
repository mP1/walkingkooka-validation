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

package walkingkooka.validation.form.expression;

import walkingkooka.Either;
import walkingkooka.currency.CurrencyLocaleContext;
import walkingkooka.currency.CurrencyLocaleContextDelegator;
import walkingkooka.currency.CurrencyLocaleContextTesting;
import walkingkooka.datetime.DateTimeContext;
import walkingkooka.datetime.DateTimeContextDelegator;
import walkingkooka.datetime.DateTimeContextTesting;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentContextDelegator;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.math.DecimalNumberContextDelegator;
import walkingkooka.text.CaseSensitivity;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.ExpressionFunctionName;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.ExpressionReference;
import walkingkooka.tree.expression.function.ExpressionFunction;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.ValidatorContext;
import walkingkooka.validation.form.Form;
import walkingkooka.validation.form.FormField;
import walkingkooka.validation.form.expression.FormHandlerExpressionEvaluationContextTesting2Test.TestFormHandlerExpressionEvaluationContext;

import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public final class FormHandlerExpressionEvaluationContextTesting2Test implements FormHandlerExpressionEvaluationContextTesting2<TestValidationReference, Void, TestFormHandlerExpressionEvaluationContext>,
    CurrencyLocaleContextTesting,
    DateTimeContextTesting,
    DecimalNumberContextDelegator {

    @Override
    public void testEnterScopeGivesDifferentInstance() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testEnvironmentContext() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testEvaluateExpressionUnknownFunctionNameFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TestFormHandlerExpressionEvaluationContext createContext() {
        return new TestFormHandlerExpressionEvaluationContext();
    }

    @Override
    public void testTestNaming() {
        throw new UnsupportedOperationException();
    }

    // DecimalNumberContext..............................................................................................

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

    final static class TestFormHandlerExpressionEvaluationContext implements FormHandlerExpressionEvaluationContext<TestValidationReference, Void>,
        CurrencyLocaleContextDelegator,
        DateTimeContextDelegator,
        DecimalNumberContextDelegator,
        EnvironmentContextDelegator {

        @Override
        public TestFormHandlerExpressionEvaluationContext enterScope(final Function<ExpressionReference, Optional<Optional<Object>>> function) {
            Objects.requireNonNull(function, "function");

            throw new UnsupportedOperationException();
        }

        @Override
        public Object handleException(final RuntimeException exception) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<Optional<Object>> reference(final ExpressionReference reference) {
            Objects.requireNonNull(reference, "reference");

            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isText(final Object value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public CaseSensitivity stringEqualsCaseSensitivity() {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> T prepareParameter(final ExpressionFunctionParameter<T> parameter,
                                      final Object value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean canNumbersHaveGroupSeparator() {
            throw new UnsupportedOperationException();
        }

        @Override
        public long dateOffset() {
            return 0;
        }

        @Override
        public <N extends Number> N multiply(final Number left,
                                             final Number right,
                                             final Class<N> type) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char valueSeparator() {
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
        public Object evaluate(final String expression) {
            Objects.requireNonNull(expression, "expression");
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isPure(final ExpressionFunctionName name) {
            Objects.requireNonNull(name, "name");

            throw new UnsupportedOperationException();
        }

        @Override
        public ExpressionNumberKind expressionNumberKind() {
            return EXPRESSION_NUMBER_KIND;
        }

        @Override
        public ExpressionFunction<?, ExpressionEvaluationContext> expressionFunction(final ExpressionFunctionName name) {
            Objects.requireNonNull(name, "name");

            throw new UnsupportedOperationException();
        }

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

        // CurrencyLocaleContext........................................................................................

        @Override
        public CurrencyLocaleContext currencyLocaleContext() {
            return CURRENCY_LOCALE_CONTEXT;
        }

        // DateTimeContext..............................................................................................

        @Override
        public DateTimeContext dateTimeContext() {
            return DATE_TIME_CONTEXT;
        }

        // DecimalNumberContextDelegator................................................................................

        @Override
        public MathContext mathContext() {
            return DECIMAL_NUMBER_CONTEXT.mathContext();
        }

        @Override
        public DecimalNumberContext decimalNumberContext() {
            return DECIMAL_NUMBER_CONTEXT;
        }

        // EnvironmentContextDelegator..................................................................................

        @Override
        public FormHandlerExpressionEvaluationContext<TestValidationReference, Void> cloneEnvironment() {
            throw new UnsupportedOperationException();
        }

        @Override
        public FormHandlerExpressionEvaluationContext<TestValidationReference, Void> setEnvironmentContext(final EnvironmentContext environmentContext) {
            Objects.requireNonNull(environmentContext, "environmentContext");

            return new TestFormHandlerExpressionEvaluationContext();
        }

        @Override
        public Currency currency() {
            return this.environmentContext.currency();
        }

        @Override
        public void setCurrency(final Currency currency) {
            this.environmentContext.setCurrency(currency);
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
        public EnvironmentContext environmentContext() {
            return this.environmentContext;
        }

        private final EnvironmentContext environmentContext = ENVIRONMENT_CONTEXT.cloneEnvironment();

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }

    // class............................................................................................................

    @Override
    public Class<TestFormHandlerExpressionEvaluationContext> type() {
        return TestFormHandlerExpressionEvaluationContext.class;
    }
}
