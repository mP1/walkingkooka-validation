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
import walkingkooka.Either;
import walkingkooka.text.CharSequences;
import walkingkooka.text.cursor.TextCursors;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.text.printer.TreePrintable;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A {@link Validator} that supports a mask using the following characters.
 * <ul>
 * <li><code> </code> Match spaces</li>
 * <li><code>-</code> Match dash</li>
 * <li><code>&slash;</code> Match '/' forward slash'</li>
 * <li><code>?</code> Match any character</li>
 * <li><code>9</code> Match any digit</li>
 * <li><code>A</code> Match any letter</li>
 * <li><code>L</code> Match any lower case letter</li>
 * <li><code>~</code> Match any character that is not the following mask control letter</li>
 * <li><code>U</code> Match any upper case letter</li>
 * <li><code>+</code> Makes the preceding mask component optional</li>
 * <li><code>&star;</code> Makes the preceding mask component repeat while true</li>
 * </ul>
 */
final class TextMaskValidator<R extends ValidationReference, C extends ValidatorContext<R>> implements Validator<R, C>,
    TreePrintable {

    static <R extends ValidationReference, C extends ValidatorContext<R>> TextMaskValidator<R, C> parse(final String mask) {
        CharSequences.failIfNullOrEmpty(mask, "mask");

        return new TextMaskValidator<>(
            TextMaskValidatorComponent.parse(
                TextCursors.charSequence(mask)
            ),
            mask
        );
    }

    // @VisibleForTesting
    TextMaskValidator(final List<TextMaskValidatorComponent<R>> components,
                      final String mask) {
        this.components = components;
        this.mask = mask;
    }

    @Override
    public List<ValidationError<R>> validate(final Object value,
                                             final C context) {
        final Either<String, String> text = context.convert(
            value,
            String.class
        );

        final List<ValidationError<R>> errors;
        if (text.isLeft()) {
            errors = this.validateText(
                CharSequences.nullToEmpty(
                    text.leftValue()
                ).toString(),
                context
            );
        } else {
            errors = ValidationErrorList.<R>empty()
                .concat(
                    context.validationError("Expected text")
                );
        }

        return errors;
    }

    private List<ValidationError<R>> validateText(final String text,
                                                  final C context) {
        final Iterator<TextMaskValidatorComponent<R>> components = this.components.iterator();

        return components.next()
            .tryMatch(
                TextCursors.charSequence(text),
                false, // invert no
                components,
                context
            );
    }

    /**
     * One or more components
     */
    private final List<TextMaskValidatorComponent<R>> components;

    @Override
    public Optional<List<ValidationChoice>> choices(final ValidatorContext<R> context) {
        Objects.requireNonNull(context, "context");
        return NO_CHOICES;
    }

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.mask.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof TextMaskValidator &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final TextMaskValidator<?, ?> other) {
        return this.components.equals(other.components);
    }

    @Override
    public String toString() {
        return this.mask;
    }

    private final String mask;

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        printer.println(this.getClass().getSimpleName());
        printer.indent();
        {
            printer.println(
                CharSequences.quoteAndEscape(this.mask)
            );

            printer.indent();
            {
                for(final TextMaskValidatorComponent<R> component : this.components) {
                    component.printTree(printer);
                }
            }
            printer.outdent();
        }
        printer.println();
    }
}
