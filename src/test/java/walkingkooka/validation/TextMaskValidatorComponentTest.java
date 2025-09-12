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
import walkingkooka.HashCodeEqualsDefinedTesting;
import walkingkooka.ToStringTesting;
import walkingkooka.reflect.ClassTesting;
import walkingkooka.reflect.JavaVisibility;

public final class TextMaskValidatorComponentTest implements ClassTesting<TextMaskValidatorComponent<?>>,
    HashCodeEqualsDefinedTesting,
    ToStringTesting<TextMaskValidatorComponent<?>> {

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsAnyAny() {
        this.checkEquals(
            TextMaskValidatorComponent.any(),
            TextMaskValidatorComponent.any()
        );
    }

    @Test
    public void testEqualsLetterAndLetter() {
        this.checkEquals(
            TextMaskValidatorComponent.letter(),
            TextMaskValidatorComponent.letter()
        );
    }

    @Test
    public void testEqualsNotLetterAndNotLetter() {
        this.checkEquals(
            TextMaskValidatorComponent.not(
                TextMaskValidatorComponent.letter()
            ),
            TextMaskValidatorComponent.not(
                TextMaskValidatorComponent.letter()
            )
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToStringWithAny() {
        this.toStringAndCheck(
            TextMaskValidatorComponent.any(),
            "?"
        );
    }

    @Test
    public void testToStringWithDigit() {
        this.toStringAndCheck(
            TextMaskValidatorComponent.digit(),
            "9"
        );
    }

    @Test
    public void testToStringWithLetter() {
        this.toStringAndCheck(
            TextMaskValidatorComponent.letter(),
            "A"
        );
    }

    @Test
    public void testToStringWithLowerCaseLetter() {
        this.toStringAndCheck(
            TextMaskValidatorComponent.lowerCaseLetter(),
            "L"
        );
    }

    @Test
    public void testToStringWithNot() {
        this.toStringAndCheck(
            TextMaskValidatorComponent.not(
                TextMaskValidatorComponent.letter()
            ),
            "~A"
        );
    }

    @Test
    public void testToStringWithOptionalLetter() {
        this.toStringAndCheck(
            TextMaskValidatorComponent.optional(
                TextMaskValidatorComponent.upperCaseLetter()
            ),
            "U+"
        );
    }

    @Test
    public void testToStringWithRepeatingLetter() {
        this.toStringAndCheck(
            TextMaskValidatorComponent.repeating(
                TextMaskValidatorComponent.upperCaseLetter()
            ),
            "U*"
        );
    }

    @Test
    public void testToStringWithUpperCaseLetter() {
        this.toStringAndCheck(
            TextMaskValidatorComponent.upperCaseLetter(),
            "U"
        );
    }

    // class............................................................................................................

    @Override
    public Class<TextMaskValidatorComponent<?>> type() {
        return Cast.to(TextMaskValidatorComponent.class);
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
