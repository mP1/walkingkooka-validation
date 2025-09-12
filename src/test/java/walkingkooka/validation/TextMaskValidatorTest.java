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
import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.ToStringTesting;
import walkingkooka.collect.list.Lists;
import walkingkooka.convert.Converter;
import walkingkooka.convert.Converters;
import walkingkooka.test.ParseStringTesting;

public final class TextMaskValidatorTest implements ValidatorTesting2<TextMaskValidator<TestValidationReference, TestValidatorContext>, TestValidationReference, TestValidatorContext>,
    ToStringTesting<TextMaskValidator<TestValidationReference, TestValidatorContext>>,
    ParseStringTesting<TextMaskValidator<TestValidationReference, TestValidatorContext>>,
    HashCodeEqualsDefinedTesting2<TextMaskValidator<TestValidationReference, TestValidatorContext>> {

    private final static TestValidationReference REFERENCE = new TestValidationReference("HelloField");

    // parse............................................................................................................

    @Test
    public void testParseInvalidCharacter() {
        this.parseStringInvalidCharacterFails(
            "B",
            'B'
        );
    }

    @Test
    public void testParseInvalidCharacter2() {
        this.parseStringInvalidCharacterFails(
            "?A9B",
            'B'
        );
    }

    @Test
    public void testParseNotWithoutNext() {
        this.parseStringFails(
            "?~",
            new IllegalArgumentException("Not missing following character")
        );
    }

    @Test
    public void testParseUnclosedQuotes() {
        this.parseStringFails(
            "\"Hello",
            new IllegalArgumentException("Unclosed double quotes")
        );
    }

    @Test
    public void testParseAny() {
        this.parseMaskAndCheck(
            "?",
            TextMaskValidatorComponent.any()
        );
    }

    @Test
    public void testParseAnyAny() {
        this.parseMaskAndCheck(
            "??",
            TextMaskValidatorComponent.any(),
            TextMaskValidatorComponent.any()
        );
    }

    @Test
    public void testParseAnyAnyAny() {
        this.parseMaskAndCheck(
            "???",
            TextMaskValidatorComponent.any(),
            TextMaskValidatorComponent.any(),
            TextMaskValidatorComponent.any()
        );
    }

    @Test
    public void testParseBackslashLetter() {
        this.parseMaskAndCheck(
            "\\1",
            TextMaskValidatorComponent.escaped('1')
        );
    }

    @Test
    public void testParseBackslashLetterLetter() {
        this.parseMaskAndCheck(
            "\\1A",
            TextMaskValidatorComponent.escaped('1'),
            TextMaskValidatorComponent.letter()
        );
    }

    @Test
    public void testParseDigitDigit() {
        this.parseMaskAndCheck(
            "99",
            TextMaskValidatorComponent.digit(),
            TextMaskValidatorComponent.digit()
        );
    }

    @Test
    public void testParseLetterLetter() {
        this.parseMaskAndCheck(
            "AA",
            TextMaskValidatorComponent.letter(),
            TextMaskValidatorComponent.letter()
        );
    }

    @Test
    public void testParseLowerCaseLetterLowerCaseLetter() {
        this.parseMaskAndCheck(
            "LL",
            TextMaskValidatorComponent.lowerCaseLetter(),
            TextMaskValidatorComponent.lowerCaseLetter()
        );
    }

    @Test
    public void testParseNotLetter() {
        this.parseMaskAndCheck(
            "~A",
            TextMaskValidatorComponent.not(
                TextMaskValidatorComponent.letter()
            )
        );
    }

    @Test
    public void testParseNotLetterDigit() {
        this.parseMaskAndCheck(
            "~A9",
            TextMaskValidatorComponent.not(
                TextMaskValidatorComponent.letter()
            ),
            TextMaskValidatorComponent.digit()
        );
    }

    @Test
    public void testParseNotLetterNotDigit() {
        this.parseMaskAndCheck(
            "~A~9",
            TextMaskValidatorComponent.not(
                TextMaskValidatorComponent.letter()
            ),
            TextMaskValidatorComponent.not(
                TextMaskValidatorComponent.digit()
            )
        );
    }

    @Test
    public void testParseQuoted() {
        this.parseMaskAndCheck(
            "\"A\"",
            TextMaskValidatorComponent.character('A')
        );
    }

    @Test
    public void testParseQuoted2() {
        this.parseMaskAndCheck(
            "\"Hello\"",
            TextMaskValidatorComponent.character('H'),
            TextMaskValidatorComponent.character('e'),
            TextMaskValidatorComponent.character('l'),
            TextMaskValidatorComponent.character('l'),
            TextMaskValidatorComponent.character('o')
        );
    }

    @Test
    public void testParseQuotedLetter() {
        this.parseMaskAndCheck(
            "\"A\"9",
            TextMaskValidatorComponent.character('A'),
            TextMaskValidatorComponent.digit()
        );
    }

    @Test
    public void testParseUpperCaseLetterUpperCaseLetter() {
        this.parseMaskAndCheck(
            "UU",
            TextMaskValidatorComponent.upperCaseLetter(),
            TextMaskValidatorComponent.upperCaseLetter()
        );
    }

    private void parseMaskAndCheck(final String mask,
                                   final TextMaskValidatorComponent<TestValidationReference>...components) {
        final TextMaskValidator<TestValidationReference, TestValidatorContext> validator = new TextMaskValidator<>(
            Lists.of(components),
            mask
        );
        this.parseStringAndCheck(
            mask,
            validator
        );

        this.toStringAndCheck(
            validator,
            mask
        );
    }

    @Override
    public TextMaskValidator<TestValidationReference, TestValidatorContext> parseString(final String mask) {
        return TextMaskValidator.parse(mask);
    }

    @Override
    public Class<? extends RuntimeException> parseStringFailedExpected(final Class<? extends RuntimeException> thrown) {
        return thrown;
    }

    @Override
    public RuntimeException parseStringFailedExpected(final RuntimeException thrown) {
        return thrown;
    }
    // validate.........................................................................................................

    @Test
    public void testValidateWithNull() {
        this.maskValidateAndCheck(
            "?",
            null,
            ValidationError.with(
                REFERENCE,
                "End of text expected character"
            )
        );
    }

    @Test
    public void testValidateWithEmptyString() {
        this.maskValidateAndCheck(
            "?",
            "",
            ValidationError.with(
                REFERENCE,
                "End of text expected character"
            )
        );
    }

    @Test
    public void testValidateWithUnmatchedCharacter() {
        this.maskValidateAndCheck(
            "AAA",
            "abcd",
            ValidationError.with(
                REFERENCE,
                "Invalid character 'd' at 3"
            )
        );
    }

    @Test
    public void testValidateWithLetterMaskAndDigit() {
        this.maskValidateAndCheck(
            "A",
            "9",
            ValidationError.with(
                REFERENCE,
                "Invalid character '9' at 0 expected letter"
            )
        );
    }

    @Test
    public void testValidateWithLetterMaskAndDigit2() {
        this.maskValidateAndCheck(
            "AAA",
            "B9",
            ValidationError.with(
                REFERENCE,
                "Invalid character '9' at 1 expected letter"
            )
        );
    }

    @Test
    public void testValidateWithDigitMaskAndLetter() {
        this.maskValidateAndCheck(
            "99",
            "8A",
            ValidationError.with(
                REFERENCE,
                "Invalid character 'A' at 1 expected digit"
            )
        );
    }

    @Test
    public void testValidateWithLowerCaseMaskAndUpperCaseLetter() {
        this.maskValidateAndCheck(
            "LL",
            "lX",
            ValidationError.with(
                REFERENCE,
                "Invalid character 'X' at 1 expected lower-case letter"
            )
        );
    }

    @Test
    public void testValidateWithUpperCaseMaskAndLowerCaseLetter() {
        this.maskValidateAndCheck(
            "UU",
            "Ua",
            ValidationError.with(
                REFERENCE,
                "Invalid character 'a' at 1 expected upper-case letter"
            )
        );
    }

    @Test
    public void testValidateWithTextLiteral() {
        this.maskValidateAndCheck(
            "\"Hello\"",
            "Hello"
        );
    }

    @Test
    public void testValidateWithTextLiteral2() {
        this.maskValidateAndCheck(
            "\"He\\llo\"",
            "Hello"
        );
    }

    @Test
    public void testValidateWithTextLiteralAndInvalidCharacter() {
        this.maskValidateAndCheck(
            "\"Hello\"",
            "Hel!o",
            ValidationError.with(
                REFERENCE,
                "Invalid character '!' at 3 expected 'l'"
            )
        );
    }

    @Test
    public void testValidateWithMask() {
        this.maskValidateAndCheck(
            "\"Hello\"?AAA999",
            "Hello*abc123"
        );
    }

    @Test
    public void testValidateWithMaskIncludesNot() {
        this.maskValidateAndCheck(
            "~9",
            "X"
        );
    }

    @Test
    public void testValidateWithMaskIncludesNot2() {
        this.maskValidateAndCheck(
            "~999",
            "X23"
        );
    }

    @Test
    public void testValidateWithMaskIncludesNot3() {
        this.maskValidateAndCheck(
            "\"Hello\"?AAA~999",
            "Hello*abcX23"
        );
    }

    @Test
    public void testValidateWithMaskIncludesNot4() {
        this.maskValidateAndCheck(
            "~AA~AA",
            "9A9A"
        );
    }

    private void maskValidateAndCheck(final String mask,
                                      final String text,
                                      final ValidationError<TestValidationReference>...expected) {
        this.validateAndCheck(
            TextMaskValidator.parse(mask),
            text,
            this.createContext(),
            expected
        );
    }

    @Override
    public TextMaskValidator<TestValidationReference, TestValidatorContext> createValidator() {
        return new TextMaskValidator<>(
            Lists.of(
                TextMaskValidatorComponent.any()
            ),
            "?"
        );
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
                    Converters.hasTextToString()
                )
            );

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

    // hashCode/equals..................................................................................................

    @Override
    public TextMaskValidator<TestValidationReference, TestValidatorContext> createObject() {
        return new TextMaskValidator<>(
            Lists.of(
                TextMaskValidatorComponent.any(),
                TextMaskValidatorComponent.not(
                    TextMaskValidatorComponent.letter()
                )
            ),
            "?~A"
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createValidator(),
            "?"
        );
    }

    // class............................................................................................................

    @Override
    public Class<TextMaskValidator<TestValidationReference, TestValidatorContext>> type() {
        return Cast.to(TextMaskValidator.class);
    }
}
