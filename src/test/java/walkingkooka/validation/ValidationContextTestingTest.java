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
import walkingkooka.validation.ValidationContextTestingTest.TestValidationContext;

import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

public final class ValidationContextTestingTest implements ValidationContextTesting<TestValidationContext>{
    @Override
    public TestValidationContext createContext() {
        return new TestValidationContext();
    }

    static class TestReference implements ValidationReference {
        @Override
        public String text() {
            return "A1";
        }
    }

    static class TestValidationContext implements ValidationContext<TestReference>,
        ConverterContextDelegator,
        EnvironmentContextDelegator {

        @Override
        public TestReference validationReference() {
            return new TestReference();
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

    @Override
    public String currencySymbol() {
        return CONVERTER_CONTEXT.currencySymbol();
    }

    @Override
    public char decimalSeparator() {
        return CONVERTER_CONTEXT.decimalSeparator();
    }

    @Override
    public String exponentSymbol() {
        return CONVERTER_CONTEXT.exponentSymbol();
    }

    @Override
    public char groupSeparator() {
        return CONVERTER_CONTEXT.groupSeparator();
    }

    @Override
    public MathContext mathContext() {
        return CONVERTER_CONTEXT.mathContext();
    }

    @Override
    public char negativeSign() {
        return CONVERTER_CONTEXT.negativeSign();
    }

    @Override
    public char percentageSymbol() {
        return CONVERTER_CONTEXT.percentageSymbol();
    }

    @Override
    public char positiveSign() {
        return CONVERTER_CONTEXT.positiveSign();
    }

    // class............................................................................................................

    @Override
    public Class<TestValidationContext> type() {
        return TestValidationContext.class;
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
