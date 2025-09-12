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
 * Inverts the matching of the next {@link TextMaskValidatorComponent}.
 */
final class TextMaskValidatorComponentNot<T extends ValidationReference> extends TextMaskValidatorComponent<T> {

    static <T extends ValidationReference> TextMaskValidatorComponent<T> with(final TextMaskValidatorComponent<T> component) {
        return new TextMaskValidatorComponentNot<>(component);
    }

    private TextMaskValidatorComponentNot(final TextMaskValidatorComponent<T> component) {
        super();
        this.component = component;
    }

    @Override
    ValidationErrorList<T> tryMatch(final TextCursor text,
                                    final boolean invertNext,
                                    final Iterator<TextMaskValidatorComponent<T>> nextComponent,
                                    final ValidatorContext<T> context) {
        return this.component.tryMatch(
                text,
                false == invertNext, // invert
                nextComponent,
                context
            );
    }

    @Override //
    CharSequence expected() {
        return "not " + this.component.expected();
    }

    private final TextMaskValidatorComponent<T> component;

// Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.toString()
            .hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof TextMaskValidatorComponentNot &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final TextMaskValidatorComponentNot<?> other) {
        return this.component.equals(other.component);
    }

    @Override
    public String toString() {
        return NOT + this.component.toString();
    }

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        printer.println(this.getClass().getSimpleName() + " " + NOT);
        printer.indent();
        {
            this.component.printTree(printer);
        }
        printer.outdent();
    }
}
