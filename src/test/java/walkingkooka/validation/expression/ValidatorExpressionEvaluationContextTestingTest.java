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

import walkingkooka.Either;
import walkingkooka.datetime.DateTimeSymbols;
import walkingkooka.locale.LocaleContext;
import walkingkooka.locale.LocaleContextDelegator;
import walkingkooka.locale.LocaleContexts;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.math.DecimalNumberContextDelegator;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.text.CaseSensitivity;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.ExpressionFunctionName;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.ExpressionReference;
import walkingkooka.tree.expression.function.ExpressionFunction;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.expression.ValidatorExpressionEvaluationContextTestingTest.TestValidatorExpressionEvaluationContext;
import walkingkooka.validation.form.Form;

import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public final class ValidatorExpressionEvaluationContextTestingTest implements ValidatorExpressionEvaluationContextTesting<TestValidationReference, TestValidatorExpressionEvaluationContext>,
    DecimalNumberContextDelegator {

    @Override
    public void testEnterScopeGivesDifferentInstance() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testEvaluateExpressionUnknownFunctionNameFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TestValidatorExpressionEvaluationContext createContext() {
        return new TestValidatorExpressionEvaluationContext();
    }

    // DecimalNumberContext.............................................................................................

    @Override
    public MathContext mathContext() {
        return DECIMAL_NUMBER_CONTEXT.mathContext();
    }

    // DecimalNumberContextDelegator....................................................................................

    @Override
    public DecimalNumberContext decimalNumberContext() {
        return DECIMAL_NUMBER_CONTEXT;
    }

    private final static DecimalNumberContext DECIMAL_NUMBER_CONTEXT = DecimalNumberContexts.american(MathContext.DECIMAL32);

    final static class TestValidatorExpressionEvaluationContext implements ValidatorExpressionEvaluationContext<TestValidationReference>,
        DecimalNumberContextDelegator,
        LocaleContextDelegator {

        @Override
        public TestValidatorExpressionEvaluationContext enterScope(final Function<ExpressionReference, Optional<Optional<Object>>> function) {
            Objects.requireNonNull(function, "function");

            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<Object> validationValue() {
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
        public List<String> ampms() {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<String> monthNames() {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<String> monthNameAbbreviations() {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<String> weekDayNames() {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<String> weekDayNameAbbreviations() {
            throw new UnsupportedOperationException();
        }

        @Override
        public LocalDateTime now() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int defaultYear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int twoDigitYear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public DateTimeSymbols dateTimeSymbols() {
            throw new UnsupportedOperationException();
        }

        // DecimalNumberContextDelegator....................................................................................

        @Override
        public DecimalNumberContext decimalNumberContext() {
            return DECIMAL_NUMBER_CONTEXT;
        }

        // DecimalNumberContext.............................................................................................

        @Override
        public MathContext mathContext() {
            return DECIMAL_NUMBER_CONTEXT.mathContext();
        }

        // LocaleContext................................................................................................

        @Override
        public LocaleContext localeContext() {
            return LocaleContexts.jre(Locale.ENGLISH);
        }

        @Override
        public Locale locale() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ValidatorExpressionEvaluationContext<TestValidationReference> setLocale(final Locale locale) {
            Objects.requireNonNull(locale, "locale");
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isPure(final ExpressionFunctionName name) {
            Objects.requireNonNull(name, "name");

            throw new UnsupportedOperationException();
        }

        @Override
        public ExpressionNumberKind expressionNumberKind() {
            throw new UnsupportedOperationException();
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
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }

    // class............................................................................................................

    @Override
    public Class<TestValidatorExpressionEvaluationContext> type() {
        return TestValidatorExpressionEvaluationContext.class;
    }
}
