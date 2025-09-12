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

import walkingkooka.Cast;
import walkingkooka.text.CharSequences;

/**
 * Matches the given character verbatim. This is also used to match a backslash escaped character.
 */
final class TextMaskValidatorComponentCharacterChar<T extends ValidationReference> extends TextMaskValidatorComponentCharacter<T> {

    static <T extends ValidationReference> TextMaskValidatorComponent<T> with(final char c,
                                                                              final String toString) {
        TextMaskValidatorComponentCharacterChar<T> component;

        switch (c) {
            case DASH:
                component = DASH_COMPONENT;
                break;
            case SLASH:
                component = SLASH_COMPONENT;
                break;
            case SPACE:
                component = SPACE_COMPONENT;
                break;
            default:
                component = new TextMaskValidatorComponentCharacterChar<>(
                    c,
                    toString
                );
        }

        return component;
    }

    private final static TextMaskValidatorComponentCharacterChar DASH_COMPONENT = new TextMaskValidatorComponentCharacterChar<>(
        DASH
    );

    private final static TextMaskValidatorComponentCharacterChar SLASH_COMPONENT = new TextMaskValidatorComponentCharacterChar<>(
        SLASH
    );

    private final static TextMaskValidatorComponentCharacterChar SPACE_COMPONENT = new TextMaskValidatorComponentCharacterChar<>(
        SPACE
    );

    private TextMaskValidatorComponentCharacterChar(final char c) {
        this(
            c,
            CharSequences.quoteIfChars(c)
                .toString()
        );
    }

    private TextMaskValidatorComponentCharacterChar(final char c,
                                                    final String toString) {
        super();
        this.c = c;
        this.toString = toString;
    }

    @Override //
    boolean isMatch(final char c) {
        return this.c == c;
    }

    private final char c;

    @Override //
    CharSequence expected() {
        return CharSequences.quoteIfChars(this.c);
    }

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.toString()
            .hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof TextMaskValidatorComponentCharacterChar &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final TextMaskValidatorComponentCharacterChar<?> other) {
        return this.c == other.c;
    }

    @Override
    public String toString() {
        return this.toString;
    }

    private final String toString;
}
