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

final class TextMaskValidatorComponentCharacterLowerCaseLetter<T extends ValidationReference> extends TextMaskValidatorComponentCharacter<T> {

    static <T extends ValidationReference> TextMaskValidatorComponent<T> instance() {
        return INSTANCE;
    }

    private final static TextMaskValidatorComponentCharacterLowerCaseLetter INSTANCE = new TextMaskValidatorComponentCharacterLowerCaseLetter<>();

    private TextMaskValidatorComponentCharacterLowerCaseLetter() {
        super();
    }

    @Override //
    boolean isMatch(final char c) {
        return Character.isLowerCase(c);
    }

    @Override //
    CharSequence expected() {
        return "lower-case letter";
    }

    @Override
    public String toString() {
        return String.valueOf(LOWER_CASE_LETTER);
    }
}
