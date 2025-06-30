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
import walkingkooka.Either;
import walkingkooka.ToStringTesting;

public final class TextLengthValidatorTest implements ValidatorTesting2<TextLengthValidator<TestValidationReference, TestValidatorContext>, TestValidationReference, TestValidatorContext>,
    ToStringTesting<TextLengthValidator<TestValidationReference, TestValidatorContext>> {

    private final static TestValidationReference REFERENCE = new TestValidationReference("HelloField");

    @Test
    public void testValidateNullReturnsError() {
        this.validateAndCheck(
            null,
            this.createContext(),
            ValidationError.with(
                REFERENCE,
                "Missing required text"
            )
        );
    }

    @Test
    public void testValidateWithEmptyString() {
        this.validateAndCheck(
            "",
            this.createContext(),
            ValidationError.with(
                REFERENCE,
                "Missing required text"
            )
        );
    }

    @Test
    public void testValidateWithShorterString() {
        this.validateAndCheck(
            "A",
            this.createContext(),
            ValidationError.with(
                REFERENCE,
                "Text length 1 < 2"
            )
        );
    }

    @Test
    public void testValidateWithLongerString() {
        this.validateAndCheck(
            "ABCDEFG",
            this.createContext(),
            ValidationError.with(
                REFERENCE,
                "Text length 7 > 2"
            )
        );
    }

    @Override
    public TextLengthValidator<TestValidationReference, TestValidatorContext> createValidator() {
        return TextLengthValidator.with(
            2,
            4
        );
    }

    @Override
    public TestValidatorContext createContext() {
        return new TestValidatorContext() {

            @Override
            public <T> Either<T, String> convert(final Object value,
                                                 final Class<T> target) {
                return this.successfulConversion(
                    target.cast(value),
                    target
                );
            }

            @Override
            public ValidationError<TestValidationReference> validationError(final String message) {
                return ValidationError.with(
                    REFERENCE,
                    message
                );
            }

            @Override
            public TestValidationReference validationReference() {
                return REFERENCE;
            }
        };
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createValidator(),
            "TextLength minLength=2 maxLength=4"
        );
    }

    // class............................................................................................................

    @Override
    public Class<TextLengthValidator<TestValidationReference, TestValidatorContext>> type() {
        return Cast.to(TextLengthValidator.class);
    }
}
