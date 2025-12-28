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
import walkingkooka.Cast;
import walkingkooka.ToStringTesting;
import walkingkooka.convert.CanConvert;
import walkingkooka.convert.ConverterContexts;
import walkingkooka.convert.Converters;
import walkingkooka.datetime.DateTimeContexts;
import walkingkooka.datetime.DateTimeSymbols;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentContexts;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.text.LineEnding;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.validation.provider.ValidatorSelector;

import java.math.MathContext;
import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicValidatorContextTest implements ValidatorContextTesting<BasicValidatorContext<TestValidationReference>, TestValidationReference>,
    ToStringTesting<BasicValidatorContext<TestValidationReference>> {

    private final static LineEnding LINE_ENDING = LineEnding.NL;

    private final static Locale LOCALE = Locale.ENGLISH;

    private final static TestValidationReference VALIDATION_REFERENCE = new TestValidationReference("A1");

    private final static Function<ValidatorSelector, Validator<TestValidationReference, ? super ValidatorContext<TestValidationReference>>> VALIDATOR_SELECTOR_TO_VALIDATOR = (final ValidatorSelector selector) -> {
        throw new UnsupportedOperationException();
    };

    private final static BiFunction<Object, TestValidationReference, ExpressionEvaluationContext> REFERENCE_EXPRESSION_EVALUATION_CONTEXT_FUNCTION = (final Object value,
                                                                                                                                                      final TestValidationReference validationReference) -> {
        throw new UnsupportedOperationException();
    };

    private final static DecimalNumberContext DECIMAL_NUMBER_CONTEXT = DecimalNumberContexts.american(MathContext.DECIMAL32);

    private final static CanConvert CAN_CONVERT = ConverterContexts.basic(
        false, // canNumbersHaveGroupSeparator
        Converters.EXCEL_1900_DATE_SYSTEM_OFFSET, // offset
        ',', // valueSeparator
        Converters.simple(),
        DateTimeContexts.basic(
            DateTimeSymbols.fromDateFormatSymbols(
                new DateFormatSymbols(LOCALE)
            ),
            LOCALE, // locale
            1950, // defaultYear
            50, // twoDigitYear
            LocalDateTime::now
        ),
        DECIMAL_NUMBER_CONTEXT
    );

    private final static EnvironmentContext ENVIRONMENT_CONTEXT = EnvironmentContexts.readOnly(
        EnvironmentContexts.empty(
            LINE_ENDING,
            LOCALE,
            () -> LocalDateTime.of(
                1999,
                12,
                31,
                12,
                58,
                59
            ), // now
            Optional.of(
                EmailAddress.parse("user@example.com")
            )
        )
    );

    @Test
    public void testWithNullValidationReferenceFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicValidatorContext.with(
                null,
                VALIDATOR_SELECTOR_TO_VALIDATOR,
                REFERENCE_EXPRESSION_EVALUATION_CONTEXT_FUNCTION,
                CAN_CONVERT,
                ENVIRONMENT_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullValidationSelectorToValidatorFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicValidatorContext.with(
                VALIDATION_REFERENCE,
                null,
                REFERENCE_EXPRESSION_EVALUATION_CONTEXT_FUNCTION,
                CAN_CONVERT,
                ENVIRONMENT_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullValidationReferenceToExpressionEvaluationContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicValidatorContext.with(
                VALIDATION_REFERENCE,
                VALIDATOR_SELECTOR_TO_VALIDATOR,
                null,
                CAN_CONVERT,
                ENVIRONMENT_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullCanConvertFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicValidatorContext.with(
                VALIDATION_REFERENCE,
                VALIDATOR_SELECTOR_TO_VALIDATOR,
                REFERENCE_EXPRESSION_EVALUATION_CONTEXT_FUNCTION,
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
                VALIDATOR_SELECTOR_TO_VALIDATOR,
                REFERENCE_EXPRESSION_EVALUATION_CONTEXT_FUNCTION,
                CAN_CONVERT,
                null
            )
        );
    }

    // setValidationReference...........................................................................................

    @Test
    public void testSetValidationReferenceWithDifferent() {
        final TestValidationReference differentReference = new TestValidationReference("Different");

        final BasicValidatorContext<TestValidationReference> context = this.createContext();
        final BasicValidatorContext<TestValidationReference> different = Cast.to(
            context.setValidationReference(differentReference)
        );

        assertSame(
            differentReference,
            different.validationReference(),
            "validationReference"
        );

        assertSame(
            CAN_CONVERT,
            different.canConvert,
            "canConvert"
        );

        checkEquals(
            ENVIRONMENT_CONTEXT.cloneEnvironment(),
            different.environmentContext,
            "environmentContext"
        );
    }

    // cloneEnvironment..................................................................................................

    @Test
    public void testCloneEnvironment() {
        final BasicValidatorContext<TestValidationReference> before = this.createContext();
        final ValidatorContext<TestValidationReference> after = before.cloneEnvironment();
        assertNotSame(
            before,
            after
        );

        this.checkEquals(
            before,
            after
        );
    }

    // setEnvironmentContext............................................................................................

    @Test
    public void testSetEnvironmentContext() {
        final BasicValidatorContext<TestValidationReference> context = this.createContext();

        final EnvironmentContext different = EnvironmentContexts.fake();

        this.checkNotEquals(
            ENVIRONMENT_CONTEXT,
            different
        );

        final ValidatorContext<TestValidationReference> set = context.setEnvironmentContext(different);

        assertNotSame(
            context,
            set
        );

        this.checkEquals(
            this.createContext(different),
            set
        );
    }

    @Override
    public void testRemoveEnvironmentValueWithNowFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetEnvironmentValueWithNowFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public BasicValidatorContext<TestValidationReference> createContext() {
        return this.createContext(
            ENVIRONMENT_CONTEXT.cloneEnvironment()
        );
    }

    private BasicValidatorContext<TestValidationReference> createContext(final EnvironmentContext environmentContext) {
        return BasicValidatorContext.with(
            VALIDATION_REFERENCE,
            VALIDATOR_SELECTOR_TO_VALIDATOR,
            REFERENCE_EXPRESSION_EVALUATION_CONTEXT_FUNCTION,
            CAN_CONVERT,
            environmentContext
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            BasicValidatorContext.with(
                VALIDATION_REFERENCE,
                VALIDATOR_SELECTOR_TO_VALIDATOR,
                REFERENCE_EXPRESSION_EVALUATION_CONTEXT_FUNCTION,
                CAN_CONVERT,
                ENVIRONMENT_CONTEXT
            ).toString(),
            VALIDATION_REFERENCE + " " + VALIDATOR_SELECTOR_TO_VALIDATOR + " " + REFERENCE_EXPRESSION_EVALUATION_CONTEXT_FUNCTION + " " + CAN_CONVERT + " " + ENVIRONMENT_CONTEXT
        );
    }

    // class............................................................................................................

    @Override
    public Class<BasicValidatorContext<TestValidationReference>> type() {
        return Cast.to(BasicValidatorContext.class);
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
