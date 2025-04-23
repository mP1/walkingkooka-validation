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
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentContexts;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.validation.ValidatorContextDelegatorTest.TestValidatorContext;
import walkingkooka.validation.provider.ValidatorSelector;

import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class ValidatorContextDelegatorTest implements ValidatorContextTesting<TestValidatorContext, TestValidationReference> {

    private final static TestValidationReference VALIDATION_REFERENCE = new TestValidationReference("A1");

    private final static Function<ValidatorSelector, Validator<TestValidationReference, ? extends ValidatorContext<TestValidationReference>>> VALIDATOR_SELECTOR_TO_VALIDATOR = (final ValidatorSelector selector) -> {
        throw new UnsupportedOperationException();
    };

    private final static BiFunction<Object, TestValidationReference, ExpressionEvaluationContext> REFERENCE_EXPRESSION_EVALUATION_CONTEXT_FUNCTION = (final Object value,
                                                                                                                                                      final TestValidationReference validationReference) -> {
        throw new UnsupportedOperationException();
    };

    private final static DecimalNumberContext DECIMAL_NUMBER_CONTEXT = DecimalNumberContexts.american(MathContext.DECIMAL32);

    private final static ConverterContext CONVERTER_CONTEXT = ConverterContexts.basic(
        Converters.EXCEL_1900_DATE_SYSTEM_OFFSET, // offset
        Converters.simple(),
        DateTimeContexts.locale(
            Locale.ENGLISH, // locale
            1950, // defaultYear
            50, // twoDigitYear
            LocalDateTime::now
        ),
        DECIMAL_NUMBER_CONTEXT
    );

    private final static EnvironmentContext ENVIRONMENT_CONTEXT = EnvironmentContexts.empty(
        LocalDateTime::now, // now
        Optional.of(
            EmailAddress.parse("user@example.com")
        )
    );

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
