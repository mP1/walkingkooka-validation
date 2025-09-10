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

package walkingkooka.validation.convert;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.Either;
import walkingkooka.ToStringTesting;
import walkingkooka.collect.list.Lists;
import walkingkooka.convert.FakeConverterContext;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.ValidationError;
import walkingkooka.validation.ValidationErrorList;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertSame;

public final class ValidationConverterValidationErrorListTest extends ValidationConverterTestCase<ValidationConverterValidationErrorList<FakeConverterContext>, FakeConverterContext>
    implements ToStringTesting<ValidationConverterValidationErrorList<FakeConverterContext>> {

    private final static TestValidationReference TEST_VALIDATION_REFERENCE = new TestValidationReference("Hello");

    private final static String MESSAGE = "Message111";

    private final static String MESSAGE2 = "Message222";

    @Test
    public void testConvertValidationErrorListToStringFails() {
        this.convertFails(
            ValidationErrorList.empty(),
            String.class
        );
    }

    @Test
    public void testConvertNull() {
        this.convertAndCheck(
            null,
            ValidationErrorList.empty()
        );
    }

    @Test
    public void testConvertString() {
        this.convertAndCheck(
            MESSAGE,
            ValidationErrorList.<TestValidationReference>empty()
                .concat(
                    validationError(MESSAGE)
                )
        );
    }

    @Test
    public void testConvertValidationError() {
        final ValidationError<TestValidationReference> validationError = validationError(MESSAGE);

        this.convertAndCheck(
            validationError,
            ValidationErrorList.<TestValidationReference>empty()
                .concat(validationError)
        );
    }

    @Test
    public void testConvertEmptyList() {
        this.convertAndCheck(
            Lists.empty(),
            ValidationErrorList.empty()
        );
    }

    @Test
    public void testConvertListOfNull() {
        this.convertAndCheck(
            Arrays.asList((Object)null),
            ValidationErrorList.empty()
        );
    }

    @Test
    public void testConvertListString() {
        this.convertAndCheck(
            Lists.of(MESSAGE),
            ValidationErrorList.<TestValidationReference>empty()
                .concat(validationError(MESSAGE))
        );
    }

    @Test
    public void testConvertListStringToValidationErrorList2() {
        this.convertAndCheck(
            Lists.of(
                MESSAGE,
                MESSAGE2
            ),
            ValidationErrorList.<TestValidationReference>empty()
                .concat(validationError(MESSAGE))
                .concat(validationError(MESSAGE2))
        );
    }

    @Test
    public void testConvertListValidationError() {
        final ValidationError<TestValidationReference> validationError = validationError(MESSAGE);

        this.convertAndCheck(
            Lists.of(validationError),
            ValidationErrorList.<TestValidationReference>empty()
                .concat(validationError)
        );
    }

    @Test
    public void testConvertListValidationError2() {
        final ValidationError<TestValidationReference> validationError = validationError(MESSAGE);
        final ValidationError<TestValidationReference> validationError2 = validationError(MESSAGE2);

        this.convertAndCheck(
            Lists.of(
                validationError,
                validationError2
            ),
            ValidationErrorList.<TestValidationReference>empty()
                .concat(validationError)
                .concat(validationError2)
        );
    }

    @Test
    public void testConvertListNullAndStringAndValidationError() {
        final ValidationError<TestValidationReference> validationError = validationError(MESSAGE);

        this.convertAndCheck(
            Lists.of(
                null,
                validationError,
                MESSAGE2
            ),
            ValidationErrorList.<TestValidationReference>empty()
                .concat(validationError)
                .concat(
                    validationError(MESSAGE2)
                )
        );
    }

    @Test
    public void testConvertValidationErrorListToValidationErrorList() {
        final ValidationErrorList<TestValidationReference> validationErrorList = ValidationErrorList.with(
            Lists.of(
                validationError(MESSAGE)
            )
        );

        assertSame(
            validationErrorList,
            this.convertAndCheck(
                validationErrorList,
                validationErrorList
            )
        );
    }

    @Override
    public ValidationConverterValidationErrorList<FakeConverterContext> createConverter() {
        return ValidationConverterValidationErrorList.instance();
    }

    @Override
    public FakeConverterContext createContext() {
        return new FakeConverterContext() {
            @Override
            public boolean canConvert(final Object value,
                                      final Class<?> type) {
                return (value instanceof String || value instanceof ValidationError) &&
                    ValidationError.class == type;
            }

            @Override
            public <T> Either<T, String> convert(final Object value,
                                                 final Class<T> target) {
                return value instanceof String && target == ValidationError.class ?
                    this.successfulConversion(
                        validationError(
                            (String) value
                        ),
                        target
                    ) :
                    value instanceof ValidationError && target == ValidationError.class ?
                        this.successfulConversion(
                            value,
                            target
                        ) :
                        this.failConversion(
                            value,
                            target
                        );
            }
        };
    }

    private static ValidationError<TestValidationReference> validationError(final String message) {
        return ValidationError.with(
            TEST_VALIDATION_REFERENCE,
            message
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            ValidationConverterValidationErrorList.instance(),
            "* to ValidationErrorList"
        );
    }

    // class............................................................................................................

    @Override
    public Class<ValidationConverterValidationErrorList<FakeConverterContext>> type() {
        return Cast.to(ValidationConverterValidationErrorList.class);
    }
}
