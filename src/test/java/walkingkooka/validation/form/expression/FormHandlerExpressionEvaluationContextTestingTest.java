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
import walkingkooka.datetime.DateTimeContext;
import walkingkooka.datetime.DateTimeContextDelegator;
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.locale.LocaleContext;
import walkingkooka.locale.LocaleContextDelegator;
import walkingkooka.locale.LocaleContexts;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.math.DecimalNumberContextDelegator;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.net.email.EmailAddress;
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
import walkingkooka.validation.form.expression.FormHandlerExpressionEvaluationContextTestingTest.TestFormHandlerExpressionEvaluationContext;

import java.math.MathContext;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public final class FormHandlerExpressionEvaluationContextTestingTest implements FormHandlerExpressionEvaluationContextTesting<TestValidationReference, Void, TestFormHandlerExpressionEvaluationContext>,
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
    public void testUserNotNull() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TestFormHandlerExpressionEvaluationContext createContext() {
        return new TestFormHandlerExpressionEvaluationContext();
    }

    // DecimalNumberContext..............................................................................................

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

    final static class TestFormHandlerExpressionEvaluationContext implements FormHandlerExpressionEvaluationContext<TestValidationReference, Void>,
        DateTimeContextDelegator,
        DecimalNumberContextDelegator,
        LocaleContextDelegator {

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
        public long dateOffset() {
            return 0;
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
        public FormHandlerExpressionEvaluationContext<TestValidationReference, Void> cloneEnvironment() {
            throw new UnsupportedOperationException();
        }

        @Override
        public DateTimeContext dateTimeContext() {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> Optional<T> environmentValue(final EnvironmentValueName<T> name) {
            Objects.requireNonNull(name, "name");

            throw new UnsupportedOperationException();
        }

        @Override
        public Set<EnvironmentValueName<?>> environmentValueNames() {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> TestFormHandlerExpressionEvaluationContext setEnvironmentValue(final EnvironmentValueName<T> name,
                                                                                  final T value) {
            Objects.requireNonNull(name, "name");
            Objects.requireNonNull(value, "value");
            throw new UnsupportedOperationException();
        }

        @Override
        public TestFormHandlerExpressionEvaluationContext removeEnvironmentValue(final EnvironmentValueName<?> name) {
            Objects.requireNonNull(name, "name");
            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<EmailAddress> user() {
            throw new UnsupportedOperationException();
        }

        @Override
        public DecimalNumberContext decimalNumberContext() {
            return DECIMAL_NUMBER_CONTEXT;
        }

        @Override
        public Locale locale() {
            throw new UnsupportedOperationException();
        }

        @Override
        public LocaleContext localeContext() {
            return LocaleContexts.jre(Locale.ENGLISH);
        }

        @Override
        public FormHandlerExpressionEvaluationContext<TestValidationReference, Void> setLocale(final Locale locale) {
            Objects.requireNonNull(locale, "locale");
            throw new UnsupportedOperationException();
        }

        @Override
        public MathContext mathContext() {
            return DECIMAL_NUMBER_CONTEXT.mathContext();
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
