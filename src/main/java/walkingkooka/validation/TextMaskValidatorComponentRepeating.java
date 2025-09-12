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
import walkingkooka.collect.list.Lists;
import walkingkooka.text.cursor.TextCursor;
import walkingkooka.text.cursor.TextCursorSavePoint;
import walkingkooka.text.printer.IndentingPrinter;

import java.util.Iterator;
import java.util.Objects;

/**
 * Makes the wrapped {@link TextMaskValidatorComponent} repeat zero or more times.
 */
final class TextMaskValidatorComponentRepeating<T extends ValidationReference> extends TextMaskValidatorComponent<T> {

    static <T extends ValidationReference> TextMaskValidatorComponent<T> with(final TextMaskValidatorComponent<T> component) {
        return new TextMaskValidatorComponentRepeating<>(
            Objects.requireNonNull(component, "component")
        );
    }

    private TextMaskValidatorComponentRepeating(final TextMaskValidatorComponent<T> component) {
        super();
        this.component = component;
    }

    @Override
    ValidationErrorList<T> tryMatch(final TextCursor text,
                                    final boolean invertNext,
                                    final Iterator<TextMaskValidatorComponent<T>> nextComponent,
                                    final ValidatorContext<T> context) {
        final TextMaskValidatorComponent<T> component = this.component;

        while(text.isNotEmpty()) {
            final TextCursorSavePoint save = text.save();

            final ValidationErrorList<T> errors = component.tryMatch(
                text,
                false, // invert
                Lists.of(TextMaskValidatorComponentRepeatingNext.<T>instance()) // required otherwise nextComponent might consume text and fail.
                    .iterator(),
                context
            );

            if (errors.isNotEmpty()) {
                save.restore();
                break;
            }

            // try again!
        }

        return nextComponent.hasNext() ?
            nextComponent.next()
                .tryMatch(
                    text,
                    false, // invertNext
                    nextComponent,
                    context
                ) :
            this.invalidCharacterIfNotEmpty(
                text,
                context
            );
    }

    @Override //
    CharSequence expected() {
        return "many " + this.component.expected();
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
            other instanceof TextMaskValidatorComponentRepeating &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final TextMaskValidatorComponentRepeating<?> other) {
        return this.component.equals(other.component);
    }

    @Override
    public String toString() {
        return this.component.toString() + REPEATING;
    }

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        printer.println(this.getClass().getSimpleName() + " " + REPEATING);
        printer.indent();
        {
            this.component.printTree(printer);
        }
        printer.outdent();
    }
}
