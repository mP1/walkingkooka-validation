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

package walkingkooka.validation.form.function;

import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.locale.LocaleContexts;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.math.DecimalNumberContextDelegator;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.tree.expression.ExpressionFunctionName;
import walkingkooka.tree.expression.ExpressionReference;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.ValidatorContext;
import walkingkooka.validation.form.Form;
import walkingkooka.validation.form.FormField;
import walkingkooka.validation.form.function.FormHandlerExpressionEvaluationContextDelegatorTest.TestFormHandlerExpressionEvaluationContextDelegator;
import walkingkooka.validation.form.function.FormHandlerExpressionEvaluationContextTestingTest.TestFormHandlerExpressionEvaluationContext;

import java.math.MathContext;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public final class FormHandlerExpressionEvaluationContextDelegatorTest implements FormHandlerExpressionEvaluationContextTesting<TestValidationReference, Void, TestFormHandlerExpressionEvaluationContextDelegator>,
    DecimalNumberContextDelegator {

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
    public TestFormHandlerExpressionEvaluationContextDelegator createContext() {
        return new TestFormHandlerExpressionEvaluationContextDelegator();
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

    private final static DecimalNumberContext DECIMAL_NUMBER_CONTEXT = DecimalNumberContexts.american(MathContext.DECIMAL32);

    // class............................................................................................................

    @Override
    public void testEvaluateExpressionUnknownFunctionNameFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<TestFormHandlerExpressionEvaluationContextDelegator> type() {
        return TestFormHandlerExpressionEvaluationContextDelegator.class;
    }

    static final class TestFormHandlerExpressionEvaluationContextDelegator implements FormHandlerExpressionEvaluationContextDelegator<TestValidationReference, Void> {

        @Override
        public FormHandlerExpressionEvaluationContext<TestValidationReference, Void> expressionEvaluationContext() {
            return new FakeFormHandlerExpressionEvaluationContext<>() {

                @Override
                public TestFormHandlerExpressionEvaluationContext enterScope(final Function<ExpressionReference, Optional<Optional<Object>>> function) {
                    Objects.requireNonNull(function, "function");

                    throw new UnsupportedOperationException();
                }

                @Override
                public String currencySymbol() {
                    return DECIMAL_NUMBER_CONTEXT.currencySymbol();
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
                public <T> Optional<T> environmentValue(final EnvironmentValueName<T> name) {
                    Objects.requireNonNull(name, "name");

                    throw new UnsupportedOperationException();
                }

                @Override
                public <T> FakeFormHandlerExpressionEvaluationContext<TestValidationReference, Void> setEnvironmentValue(final EnvironmentValueName<T> name,
                                                                                                                         final T value) {
                    Objects.requireNonNull(name, "name");
                    Objects.requireNonNull(value, "value");
                    throw new UnsupportedOperationException();
                }

                @Override
                public FakeFormHandlerExpressionEvaluationContext<TestValidationReference, Void> removeEnvironmentValue(final EnvironmentValueName<?> name) {
                    Objects.requireNonNull(name, "name");
                    throw new UnsupportedOperationException();
                }

                @Override
                public Optional<EmailAddress> user() {
                    return Optional.empty();
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
                public Set<Locale> findByLocaleText(final String text,
                                                    final int offset,
                                                    final int count) {
                    return LocaleContexts.jre(Locale.ENGLISH)
                        .findByLocaleText(
                            text,
                            offset,
                            count
                        );
                }
            };
        }

        @Override
        public FormHandlerExpressionEvaluationContext<TestValidationReference, Void> enterScope(final Function<ExpressionReference, Optional<Optional<Object>>> function) {
            Objects.requireNonNull(function, "function");

            return new TestFormHandlerExpressionEvaluationContextDelegator();
        }

        @Override
        public Optional<Optional<Object>> reference(final ExpressionReference reference) {
            Objects.requireNonNull(reference, "reference");

            throw new UnsupportedOperationException();
        }

        @Override
        public <T> TestFormHandlerExpressionEvaluationContextDelegator setEnvironmentValue(final EnvironmentValueName<T> name,
                                                                                           final T value) {
            Objects.requireNonNull(name, "name");
            Objects.requireNonNull(value, "value");
            throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }
}
