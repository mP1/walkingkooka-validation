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
import walkingkooka.ToStringTesting;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.ConverterContexts;
import walkingkooka.convert.Converters;
import walkingkooka.datetime.DateTimeContexts;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentContexts;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.net.email.EmailAddress;

import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicValidatorContextTest implements ValidatorContextTesting<BasicValidatorContext>,
    ToStringTesting<BasicValidatorContext> {

    private final static ValidationReference VALIDATION_REFERENCE = new ValidationReference() {
        @Override
        public String text() {
            return "A1";
        }
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

    @Test
    public void testWithNullValidationReferenceFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicValidatorContext.with(
                null,
                CONVERTER_CONTEXT,
                ENVIRONMENT_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullConverterContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicValidatorContext.with(
                VALIDATION_REFERENCE,
                null,
                ENVIRONMENT_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullEnvironmentContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicValidatorContext.with(
                VALIDATION_REFERENCE,
                CONVERTER_CONTEXT,
                null
            )
        );
    }

    @Override
    public BasicValidatorContext createContext() {
        return BasicValidatorContext.with(
            VALIDATION_REFERENCE,
            CONVERTER_CONTEXT,
            ENVIRONMENT_CONTEXT
        );
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
    public MathContext mathContext() {
        return DECIMAL_NUMBER_CONTEXT.mathContext();
    }

    @Override
    public char negativeSign() {
        return DECIMAL_NUMBER_CONTEXT.negativeSign();
    }

    @Override
    public char percentageSymbol() {
        return DECIMAL_NUMBER_CONTEXT.percentageSymbol();
    }

    @Override
    public char positiveSign() {
        return DECIMAL_NUMBER_CONTEXT.positiveSign();
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            BasicValidatorContext.with(
                VALIDATION_REFERENCE,
                CONVERTER_CONTEXT,
                ENVIRONMENT_CONTEXT
            ).toString(),
            VALIDATION_REFERENCE + " " + CONVERTER_CONTEXT + " " + ENVIRONMENT_CONTEXT
        );
    }

    // class............................................................................................................

    @Override
    public Class<BasicValidatorContext> type() {
        return BasicValidatorContext.class;
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
