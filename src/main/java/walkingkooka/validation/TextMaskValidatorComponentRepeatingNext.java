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
import walkingkooka.text.cursor.TextCursor;
import walkingkooka.text.printer.IndentingPrinter;

import java.util.Iterator;

/**
 * Always returns no errors used as the next component by {@link TextMaskValidatorComponentRepeating}.
 */
final class TextMaskValidatorComponentRepeatingNext<T extends ValidationReference> extends TextMaskValidatorComponent<T> {

    static <T extends ValidationReference> TextMaskValidatorComponent<T> instance() {
        return Cast.to(INSTANCE);
    }

    private final static TextMaskValidatorComponentRepeatingNext INSTANCE = new TextMaskValidatorComponentRepeatingNext<>();

    private TextMaskValidatorComponentRepeatingNext() {
        super();
    }

    @Override
    ValidationErrorList<T> tryMatch(final TextCursor text,
                                    final boolean invertNext,
                                    final Iterator<TextMaskValidatorComponent<T>> nextComponent,
                                    final ValidatorContext<T> context) {
        return ValidationErrorList.empty();
    }

    @Override //
    CharSequence expected() {
        return "";
    }

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        printer.println(this.getClass().getSimpleName());
    }

    // toString.........................................................................................................

    @Override
    public String toString() {
        return "";
    }
}
