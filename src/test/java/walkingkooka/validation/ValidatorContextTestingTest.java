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
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentContextDelegator;
import walkingkooka.environment.EnvironmentContexts;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.validation.ValidatorContextTestingTest.TestValidatorContext;

import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public final class ValidatorContextTestingTest implements ValidatorContextTesting<TestValidatorContext, TestValidationReference> {
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
        public final ConverterContext converterContext() {
            return CONVERTER_CONTEXT;
        }

        @Override
        public LocalDateTime now() {
            return CONVERTER_CONTEXT.now();
        }

        @Override
        public EnvironmentContext environmentContext() {
            return EnvironmentContexts.empty(
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
        Converters.EXCEL_1900_DATE_SYSTEM_OFFSET, // dateOffset
        Converters.objectToString(),
        DateTimeContexts.locale(
            Locale.ENGLISH,
            1950,
            50,
            LocalDateTime::now
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
