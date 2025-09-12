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

import walkingkooka.text.cursor.TextCursor;

import java.util.Iterator;

/**
 * Base class for a {@link TextMaskValidatorComponent} that matches a single character.
 */
abstract class TextMaskValidatorComponentCharacter<T extends ValidationReference> extends TextMaskValidatorComponent<T> {

    TextMaskValidatorComponentCharacter() {
        super();
    }

    @Override//
    final ValidationErrorList<T> tryMatch(final TextCursor text,
                                          final boolean invertNext,
                                          final Iterator<TextMaskValidatorComponent<T>> nextComponent,
                                          final ValidatorContext<T> context) {
        ValidationErrorList<T> errors;

        if (text.isEmpty()) {
            errors = this.endOfText(context);
        } else {
            final char c = text.at();

            if (invertNext ^ this.isMatch(c)) {
                text.next();

                if (nextComponent.hasNext()) {
                    errors = nextComponent.next()
                        .tryMatch(
                            text,
                            false, // invert no ?
                            nextComponent,
                            context
                        );
                } else {
                    errors = this.invalidCharacterIfNotEmpty(
                        text,
                        context
                    );
                }
            } else {
                errors = this.invalidCharacter(
                    text,
                    context
                );
            }
        }

        return errors;
    }

    abstract boolean isMatch(final char c);
}
