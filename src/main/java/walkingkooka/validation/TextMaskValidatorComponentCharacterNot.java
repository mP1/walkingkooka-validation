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

/**
 * Matches any character not matching the {@link TextMaskValidatorComponent}.
 */
final class TextMaskValidatorComponentCharacterNot<T extends ValidationReference> extends TextMaskValidatorComponentCharacter<T> {

    static <T extends ValidationReference> TextMaskValidatorComponent<T> with(final TextMaskValidatorComponent<T> component) {
        return new TextMaskValidatorComponentCharacterNot<>(component);
    }

    private TextMaskValidatorComponentCharacterNot(final TextMaskValidatorComponent<T> component) {
        super();
        this.component = (TextMaskValidatorComponentCharacter<T>) component;
    }

    @Override //
    boolean isMatch(final char c) {
        return false == this.component.isMatch(c);
    }

    @Override //
    CharSequence expected() {
        return "not " + this.component.expected();
    }

    private final TextMaskValidatorComponentCharacter<T> component;

// Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.toString()
            .hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof TextMaskValidatorComponentCharacterNot &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final TextMaskValidatorComponentCharacterNot<?> other) {
        return this.component.equals(other.component);
    }

    @Override
    public String toString() {
        return NOT + this.component.toString();
    }
}
