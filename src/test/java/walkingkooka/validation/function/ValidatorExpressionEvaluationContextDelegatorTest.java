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

package walkingkooka.validation.function;

import org.junit.jupiter.api.Test;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.math.DecimalNumberContextDelegator;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.tree.expression.ExpressionFunctionName;
import walkingkooka.tree.expression.ExpressionReference;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.form.Form;
import walkingkooka.validation.function.ValidatorExpressionEvaluationContextDelegatorTest.TestValidatorExpressionEvaluationContextDelegator;
import walkingkooka.validation.function.ValidatorExpressionEvaluationContextTestingTest.TestValidatorExpressionEvaluationContext;

import java.math.MathContext;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public final class ValidatorExpressionEvaluationContextDelegatorTest implements ValidatorExpressionEvaluationContextTesting<TestValidationReference, TestValidatorExpressionEvaluationContextDelegator>,
    DecimalNumberContextDelegator {

    private final static Optional<Object> VALIDATION_VALUE = Optional.of("ValidationValue123");

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
    public Class<TestValidatorExpressionEvaluationContextDelegator> type() {
        return TestValidatorExpressionEvaluationContextDelegator.class;
    }

    static final class TestValidatorExpressionEvaluationContextDelegator implements ValidatorExpressionEvaluationContextDelegator<TestValidationReference> {

        @Override
        public ValidatorExpressionEvaluationContext<TestValidationReference> expressionEvaluationContext() {
            return new FakeValidatorExpressionEvaluationContext<>() {

                @Override
                public Optional<Object> validationValue() {
                    return VALIDATION_VALUE;
                }

                @Override
                public TestValidatorExpressionEvaluationContext enterScope(final Function<ExpressionReference, Optional<Optional<Object>>> function) {
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
                public Form<TestValidationReference> form() {
                    throw new UnsupportedOperationException();
                }
            };
        }

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
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }
}
