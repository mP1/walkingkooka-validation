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
import walkingkooka.collect.list.Lists;
import walkingkooka.convert.Converter;
import walkingkooka.convert.Converters;
import walkingkooka.net.AbsoluteUrl;

public final class AbsoluteUrlValidatorTest implements ValidatorTesting2<AbsoluteUrlValidator<TestValidationReference, TestValidatorContext>, TestValidationReference, TestValidatorContext>,
    ToStringTesting<AbsoluteUrlValidator<TestValidationReference, TestValidatorContext>> {

    private final static TestValidationReference REFERENCE = new TestValidationReference("HelloField");

    @Test
    public void testValidateWithNull() {
        this.validateAndCheck(
            null,
            this.createContext(),
            ValidationError.with(REFERENCE)
                .setMessage("Missing url")
        );
    }

    @Test
    public void testValidateWithEmptyString() {
        this.validateAndCheck(
            "",
            this.createContext(),
            ValidationError.with(REFERENCE)
                .setMessage("no protocol: ")
        );
    }

    @Test
    public void testValidateWithStringRelativeUrl() {
        this.validateAndCheck(
            "/relative-url/123",
            this.createContext(),
            ValidationError.with(REFERENCE)
                .setMessage("no protocol: /relative-url/123")
        );
    }

    @Test
    public void testValidateWithStringMissingHost() {
        this.validateAndCheck(
            "https://",
            this.createContext(),
            ValidationError.with(REFERENCE)
                .setMessage("Missing host name")
        );
    }

    @Test
    public void testValidateWithAbsoluteUrl() {
        this.validateAndCheck(
            AbsoluteUrl.parse("https://example.com/123"),
            this.createContext()
        );
    }

    @Test
    public void testValidateWithAbsoluteUrlString() {
        this.validateAndCheck(
            "https://example.com/456",
            this.createContext()
        );
    }

    @Override
    public AbsoluteUrlValidator<TestValidationReference, TestValidatorContext> createValidator() {
        return AbsoluteUrlValidator.instance();
    }

    @Override
    public TestValidatorContext createContext() {
        return new TestValidatorContext() {

            @Override
            public <T> Either<T, String> convert(final Object value,
                                                 final Class<T> target) {
                return this.converter.convert(
                    value,
                    target,
                    this
                );
            }

            private final Converter<TestValidatorContext> converter = Converters.collection(
                Lists.of(
                    Converters.characterOrCharSequenceOrHasTextOrStringToCharacterOrCharSequenceOrString(),
                    Converters.hasText()
                )
            );

            @Override
            public ValidationError<TestValidationReference> validationError(final String message) {
                return ValidationError.with(REFERENCE)
                    .setMessage(message);
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
            AbsoluteUrlValidator.class.getSimpleName()
        );
    }

    // class............................................................................................................

    @Override
    public Class<AbsoluteUrlValidator<TestValidationReference, TestValidatorContext>> type() {
        return Cast.to(AbsoluteUrlValidator.class);
    }
}
