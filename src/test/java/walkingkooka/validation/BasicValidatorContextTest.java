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

import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicValidatorContextTest implements ValidatorContextTesting<BasicValidatorContext<TestValidationReference>, TestValidationReference>,
    ToStringTesting<BasicValidatorContext<TestValidationReference>> {

    private final static TestValidationReference VALIDATION_REFERENCE = new TestValidationReference("A1");

    private final static Function<TestValidationReference, ExpressionEvaluationContext> REFERENCE_EXPRESSION_EVALUATION_CONTEXT_FUNCTION = (final TestValidationReference validationReference) -> {
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

    @Test
    public void testWithNullValidationReferenceFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicValidatorContext.with(
                null,
                REFERENCE_EXPRESSION_EVALUATION_CONTEXT_FUNCTION,
                CONVERTER_CONTEXT,
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
                REFERENCE_EXPRESSION_EVALUATION_CONTEXT_FUNCTION,
                CONVERTER_CONTEXT,
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
            CONVERTER_CONTEXT,
            different.converterContext,
            "converterContext"
        );

        assertSame(
            ENVIRONMENT_CONTEXT,
            different.environmentContext,
            "environmentContext"
        );
    }

    @Override
    public BasicValidatorContext<TestValidationReference> createContext() {
        return BasicValidatorContext.with(
            VALIDATION_REFERENCE,
            REFERENCE_EXPRESSION_EVALUATION_CONTEXT_FUNCTION,
            CONVERTER_CONTEXT,
            ENVIRONMENT_CONTEXT
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            BasicValidatorContext.with(
                VALIDATION_REFERENCE,
                REFERENCE_EXPRESSION_EVALUATION_CONTEXT_FUNCTION,
                CONVERTER_CONTEXT,
                ENVIRONMENT_CONTEXT
            ).toString(),
            VALIDATION_REFERENCE + " " + REFERENCE_EXPRESSION_EVALUATION_CONTEXT_FUNCTION + " " + CONVERTER_CONTEXT + " " + ENVIRONMENT_CONTEXT
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
