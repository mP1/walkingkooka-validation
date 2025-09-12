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
import walkingkooka.text.printer.TreePrintableTesting;

public final class TextMaskValidatorTest implements ValidatorTesting2<TextMaskValidator<TestValidationReference, TestValidatorContext>, TestValidationReference, TestValidatorContext>,
    ToStringTesting<TextMaskValidator<TestValidationReference, TestValidatorContext>>,
    ParseStringTesting<TextMaskValidator<TestValidationReference, TestValidatorContext>>,
    HashCodeEqualsDefinedTesting2<TextMaskValidator<TestValidationReference, TestValidatorContext>>,
    TreePrintableTesting {

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
    public void testParseOptionalWithoutPrecedingComponent() {
        this.parseStringFails(
            "+",
            new IllegalArgumentException("Optional '+': Missing component before")
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
            TextMaskValidatorComponent.textLiteral("A")
        );
    }

    @Test
    public void testParseQuoted2() {
        this.parseMaskAndCheck(
            "\"Hello\"",
            TextMaskValidatorComponent.textLiteral("Hello")
        );
    }

    @Test
    public void testParseQuotedLetter() {
        this.parseMaskAndCheck(
            "\"A\"9",
            TextMaskValidatorComponent.textLiteral("A"),
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

    @Test
    public void testParseOptionalLetter() {
        this.parseMaskAndCheck(
            "A+",
            TextMaskValidatorComponent.optional(
                TextMaskValidatorComponent.letter()
            )
        );
    }

    @Test
    public void testParseOptionalLetterDigit() {
        this.parseMaskAndCheck(
            "A+9",
            TextMaskValidatorComponent.optional(
                TextMaskValidatorComponent.letter()
            ),
            TextMaskValidatorComponent.digit()
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
    public void testValidateWithTextLiteralIncludesEscapedCharacter() {
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

    @Test
    public void testValidateWithMaskNotTextLiteral() {
        this.maskValidateAndCheck(
            "~\"Hello\"",
            "ABCDE"
        );
    }

    @Test
    public void testValidateWithMaskNotTextLiteral2() {
        this.maskValidateAndCheck(
            "~\"Hello\"9",
            "ABCDE1"
        );
    }

    @Test
    public void testValidateWithOptionalDigitPresent() {
        this.maskValidateAndCheck(
            "9+",
            "9"
        );
    }

    @Test
    public void testValidateWithOptionalDigitPresentLetter() {
        this.maskValidateAndCheck(
            "9+A",
            "9A"
        );
    }

    @Test
    public void testValidateWithOptionalDigitMissing() {
        this.maskValidateAndCheck(
            "9+",
            ""
        );
    }

    @Test
    public void testValidateWithOptionalDigitMissingLetter() {
        this.maskValidateAndCheck(
            "9+A",
            "A"
        );
    }

    @Test
    public void testValidateWithOptionalDigitPresentOptionalLetterPresentTextLiteral() {
        this.maskValidateAndCheck(
            "9+A+\"Hello\"",
            "9AHello"
        );
    }

    @Test
    public void testValidateWithOptionalDigitMissingOptionalLetterPresentTextLiteral() {
        this.maskValidateAndCheck(
            "9+A+\"Hello\"",
            "AHello"
        );
    }

    @Test
    public void testValidateWithOptionalTextLiteralPresent() {
        this.maskValidateAndCheck(
            "\"Hello\"+",
            "Hello"
        );
    }

    @Test
    public void testValidateWithOptionalTextLiteralPresentDigit() {
        this.maskValidateAndCheck(
            "\"Hello\"+9",
            "Hello8"
        );
    }

    @Test
    public void testValidateWithOptionalTextLiteralMissing() {
        this.maskValidateAndCheck(
            "\"Hello\"+",
            ""
        );
    }

    @Test
    public void testValidateWithOptionalTextLiteralMissingDigit() {
        this.maskValidateAndCheck(
            "\"Hello\"+9",
            "8"
        );
    }

    @Test
    public void testValidateWithRepeatingLetterPresent() {
        this.maskValidateAndCheck(
            "A*",
            "B"
        );
    }

    @Test
    public void testValidateWithRepeatingLetterPresentDigit() {
        this.maskValidateAndCheck(
            "A*9",
            "B8"
        );
    }

    @Test
    public void testValidateWithRepeatingLetterPresentTwiceDigit() {
        this.maskValidateAndCheck(
            "A*9",
            "BC8"
        );
    }

    @Test
    public void testValidateWithRepeatingLetterPresentThriceDigit() {
        this.maskValidateAndCheck(
            "A*9",
            "BCD8"
        );
    }

    @Test
    public void testValidateWithRepeatingLetterMissing() {
        this.maskValidateAndCheck(
            "A*",
            ""
        );
    }

    @Test
    public void testValidateWithRepeatingLetterMissingDigit() {
        this.maskValidateAndCheck(
            "A*9",
            "8"
        );
    }

    @Test
    public void testValidateWithRepeatingTextLiteralPresent() {
        this.maskValidateAndCheck(
            "\"Hello\"*",
            "Hello"
        );
    }

    @Test
    public void testValidateWithRepeatingTextLiteralPresentDigit() {
        this.maskValidateAndCheck(
            "\"Hello\"*9",
            "Hello8"
        );
    }

    @Test
    public void testValidateWithRepeatingTextLiteralPresentTwiceDigit() {
        this.maskValidateAndCheck(
            "\"Hello\"*9",
            "HelloHello8"
        );
    }

    @Test
    public void testValidateWithRepeatingTextLiteralMissing() {
        this.maskValidateAndCheck(
            "\"Hello\"*",
            ""
        );
    }

    @Test
    public void testValidateWithRepeatingTextLiteralMissingDigit() {
        this.maskValidateAndCheck(
            "\"Hello\"*9",
            "8"
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

    // TreePrintable....................................................................................................

    @Test
    public void testTreePrint() {
        this.treePrintAndCheck(
            TextMaskValidator.parse("?A9LU\"Hello\"~AA*"),
            "TextMaskValidator\n" +
                "  \"?A9LU\\\"Hello\\\"~AA*\"\n" +
                "    TextMaskValidatorComponentCharacterAny ?\n" +
                "    TextMaskValidatorComponentCharacterLetter A\n" +
                "    TextMaskValidatorComponentCharacterDigit 9\n" +
                "    TextMaskValidatorComponentCharacterLowerCaseLetter L\n" +
                "    TextMaskValidatorComponentCharacterUpperCaseLetter U\n" +
                "    TextMaskValidatorComponentTextLiteral \"Hello\"\n" +
                "    TextMaskValidatorComponentNot ~\n" +
                "      TextMaskValidatorComponentCharacterLetter A\n" +
                "    TextMaskValidatorComponentRepeating *\n" +
                "      TextMaskValidatorComponentCharacterLetter A\n" +
                "  \n"
        );
    }

    // class............................................................................................................

    @Override
    public Class<TextMaskValidator<TestValidationReference, TestValidatorContext>> type() {
        return Cast.to(TextMaskValidator.class);
    }
}
