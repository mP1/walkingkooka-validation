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
import walkingkooka.text.cursor.TextCursor;
import walkingkooka.text.printer.IndentingPrinter;

import java.util.Iterator;

/**
 * Matches a text literal.
 */
final class TextMaskValidatorComponentTextLiteral<T extends ValidationReference> extends TextMaskValidatorComponent<T> {

    static <T extends ValidationReference> TextMaskValidatorComponent<T> with(final String text) {
        return new TextMaskValidatorComponentTextLiteral<>(text);
    }

    private TextMaskValidatorComponentTextLiteral(final String text) {
        super();
        this.text = text;
    }

    @Override
    ValidationErrorList<T> tryMatch(final TextCursor text,
                                    final boolean invertNext,
                                    final Iterator<TextMaskValidatorComponent<T>> nextComponent,
                                    final ValidatorContext<T> context) {
        return text.isEmpty() ?
            this.endOfText(context) :
            this.tryMatchNotEmpty(
                text,
                invertNext,
                nextComponent,
                context
            );
    }

    private ValidationErrorList<T> tryMatchNotEmpty(final TextCursor text,
                                                    final boolean invertNext,
                                                    final Iterator<TextMaskValidatorComponent<T>> nextComponent,
                                                    final ValidatorContext<T> context) {
        final String textLiteral = this.text;
        final int textLiteralLength = textLiteral.length();

        int i = 0;
        while (text.isNotEmpty() && i < textLiteralLength) {
            if (invertNext == (text.at() == textLiteral.charAt(i))) {
                break;
            }
            i++;
            text.next();
        }

        return i < textLiteralLength ?
            this.invalidCharacter(
                text,
                i,
                context
            ) :
            nextComponent.hasNext() ?
                nextComponent.next()
                    .tryMatch(
                        text,
                        false, // invertNext NO!
                        nextComponent,
                        context
                    ) :
                text.isEmpty() ?
                    ValidationErrorList.empty() :
                    this.invalidCharacter(
                        text,
                        i,
                        context
                    );
    }

    private ValidationErrorList<T> invalidCharacter(final TextCursor text,
                                                    final int i,
                                                    final ValidatorContext<T> context) {
        return ValidationErrorList.<T>empty()
            .concat(
                context.validationError(
                    "Invalid character " + CharSequences.quoteIfChars(text.at()) + " at " + text.lineInfo()
                        .textOffset() +
                        " expected " +
                        CharSequences.quoteIfChars(this.text.charAt(i))
                )
            );
    }


    @Override //
    CharSequence expected() {
        return CharSequences.quoteAndEscape(this.text);
    }

    /**
     * The text too match.
     */
    private final String text;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.toString()
            .hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof TextMaskValidatorComponentTextLiteral &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final TextMaskValidatorComponentTextLiteral<?> other) {
        return this.text.equals(other.text);
    }

    @Override
    public String toString() {
        return this.expected()
            .toString();
    }

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        printer.println(this.getClass().getSimpleName() + " " + this);
    }
}
