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
import walkingkooka.NeverError;
import walkingkooka.collect.list.Lists;
import walkingkooka.predicate.character.CharPredicate;
import walkingkooka.predicate.character.CharPredicates;
import walkingkooka.text.CharSequences;
import walkingkooka.text.cursor.TextCursor;

import java.util.Iterator;
import java.util.List;

final class TextMaskValidatorComponent<T extends ValidationReference> {

    static <T extends ValidationReference> List<TextMaskValidatorComponent<T>> parse(final TextCursor mask) {
        List<TextMaskValidatorComponent<T>> components = Lists.array();

        final int MODE_NORMAL = 1;
        final int MODE_BACK_SLASH_ESCAPE = 2;
        final int MODE_NOT = 3;
        final int MODE_INSIDE_QUOTES = 4;
        final int MODE_INSIDE_QUOTES_BACKSLASH_ESCAPE = 5;

        int mode = MODE_NORMAL;

        TextMaskValidatorComponent<T> component = null;

        while (mask.isNotEmpty()) {
            final char c = mask.at();

            switch (mode) {
                case MODE_NORMAL:
                case MODE_NOT:
                    switch (c) {
                        case ANY:
                            component = any();
                            break;
                        case '\\':
                            mode = MODE_BACK_SLASH_ESCAPE;
                            break;
                        case DIGIT:
                            component = digit();
                            break;
                        case LETTER:
                            component = letter();
                            break;
                        case LOWER_CASE_LETTER:
                            component = lowerCaseLetter();
                            break;
                        case NOT:
                            mode = MODE_NOT;
                            component = null;
                            break;
                        case UPPER_CASE_LETTER:
                            component = upperCaseLetter();
                            break;
                        case '\"':
                            mode = MODE_INSIDE_QUOTES;
                            component = null;
                            break;
                        default:
                            throw mask.lineInfo()
                                .invalidCharacterException()
                                .get();
                    }
                    if (mode == MODE_NOT && null != component) {
                        component = not(component);
                        mode = MODE_NORMAL;
                    }
                    if (null != component) {
                        components.add(component);
                        component = null;
                    }
                    break;
                case MODE_BACK_SLASH_ESCAPE:
                    components.add(
                        escaped(c)
                    );
                    mode = MODE_NORMAL;
                    component = null;
                    break;
                case MODE_INSIDE_QUOTES:
                    switch (c) {
                        case '\\':
                            mode = MODE_INSIDE_QUOTES_BACKSLASH_ESCAPE;
                            break;
                        case '"':
                            mode = MODE_NORMAL;
                            break;
                        default:
                            components.add(
                                character(c)
                            );
                            break;
                    }
                    break;
                case MODE_INSIDE_QUOTES_BACKSLASH_ESCAPE:
                    components.add(
                        escaped(c)
                    );
                    mode = MODE_INSIDE_QUOTES;
                    break;
                default:
                    NeverError.unhandledCase(
                        mode,
                        MODE_NORMAL,
                        MODE_BACK_SLASH_ESCAPE,
                        MODE_INSIDE_QUOTES,
                        MODE_INSIDE_QUOTES_BACKSLASH_ESCAPE
                    );
                    break;
            }

            mask.next();
        }

        switch (mode) {
            case MODE_NORMAL:
                // OK!
                break;
            case MODE_BACK_SLASH_ESCAPE:
                throw new IllegalArgumentException("Backslash escape missing character");
            case MODE_NOT:
                throw new IllegalArgumentException("Not missing following character");
            case MODE_INSIDE_QUOTES:
            case MODE_INSIDE_QUOTES_BACKSLASH_ESCAPE:
                throw new IllegalArgumentException("Unclosed double quotes");
            default:
                NeverError.unhandledCase(
                    mode,
                    MODE_NORMAL,
                    MODE_BACK_SLASH_ESCAPE,
                    MODE_INSIDE_QUOTES,
                    MODE_INSIDE_QUOTES_BACKSLASH_ESCAPE
                );
                break;
        }

        return components;
    }

    private final static char ANY = '?';

    static <T extends ValidationReference> TextMaskValidatorComponent<T> any() {
        return ANY_COMPONENT;
    }

    private final static TextMaskValidatorComponent ANY_COMPONENT = new TextMaskValidatorComponent<>(
        CharPredicates.always(),
        "character",
        ANY
    );

    /**
     * Matches the given character verbatim. This is also used to match a backslash escaped character.
     */
    static <T extends ValidationReference> TextMaskValidatorComponent<T> character(final char c) {
        final String toString = CharSequences.quoteIfChars(c)
            .toString();

        return new TextMaskValidatorComponent<>(
            CharPredicates.is(c),
            toString,
            toString
        );
    }

    private final static char DIGIT = '9';

    static <T extends ValidationReference> TextMaskValidatorComponent<T> digit() {
        return DIGIT_COMPONENT;
    }

    private final static TextMaskValidatorComponent DIGIT_COMPONENT = new TextMaskValidatorComponent<>(
        CharPredicates.digit(),
        "digit",
        DIGIT
    );

    /**
     * Matches the given character which was escaped in the initial mask
     */
    static <T extends ValidationReference> TextMaskValidatorComponent<T> escaped(final char c) {
        final CharSequence cc = CharSequences.escape(
            Character.toString(c)
        );

        return new TextMaskValidatorComponent<>(
            CharPredicates.is(c),
            cc.toString(),
            "\\" + cc
        );
    }

    private final static char LETTER = 'A';

    static <T extends ValidationReference> TextMaskValidatorComponent<T> letter() {
        return LETTER_COMPONENT;
    }

    private final static TextMaskValidatorComponent LETTER_COMPONENT = new TextMaskValidatorComponent<>(
        CharPredicates.letter(),
        "letter",
        LETTER
    );

    static <T extends ValidationReference> TextMaskValidatorComponent<T> lowerCaseLetter() {
        return LOWER_CASE_LETTER_COMPONENT;
    }

    private final static char LOWER_CASE_LETTER = 'L';

    private final static TextMaskValidatorComponent LOWER_CASE_LETTER_COMPONENT = new TextMaskValidatorComponent<>(
        ((c) -> Character.isLowerCase(c)),
        "lower-case letter",
        LOWER_CASE_LETTER
    );

    private final static char NOT = '~';

    /**
     * Matches any character not matching the {@link CharPredicate}.
     */
    static <T extends ValidationReference> TextMaskValidatorComponent<T> not(
        final TextMaskValidatorComponent<T> component) {
        return new TextMaskValidatorComponent<>(
            component.predicate.negate(),
            "character but not " + component.expected,
            "" + NOT + component
        );
    }

    private final static char UPPER_CASE_LETTER = 'U';

    static <T extends ValidationReference> TextMaskValidatorComponent<T> upperCaseLetter() {
        return UPPER_CASE_LETTER_COMPONENT;
    }

    final static TextMaskValidatorComponent UPPER_CASE_LETTER_COMPONENT = new TextMaskValidatorComponent<>(
        ((c) -> Character.isUpperCase(c)),
        "upper-case letter",
        UPPER_CASE_LETTER
    );

    private TextMaskValidatorComponent(final CharPredicate predicate,
                                       final String expected,
                                       final char toString) {
        this(
            predicate,
            expected,
            CharSequences.escape(
                String.valueOf(toString)
            ).toString()
        );
    }

    private TextMaskValidatorComponent(final CharPredicate predicate,
                                       final String expected,
                                       final String toString) {
        super();
        this.predicate = predicate;
        this.expected = expected;
        this.toString = toString;
    }

    ValidationErrorList<T> tryMatch(final TextCursor text,
                                    final Iterator<TextMaskValidatorComponent<T>> nextComponent,
                                    final ValidatorContext<T> context) {
        ValidationErrorList<T> errors = null;

        if (text.isEmpty()) {
            errors = ValidationErrorList.<T>empty()
                    .concat(
                        context.validationError(
                            "End of text expected " + this.expected
                        )
                    );
        } else {
            final char c = text.at();

            if (this.predicate.test(c)) {
                text.next();

                if (nextComponent.hasNext()) {
                    errors = nextComponent.next()
                        .tryMatch(
                            text,
                            nextComponent,
                            context
                        );
                } else {
                    if(text.isEmpty()) {
                        errors = ValidationErrorList.empty();
                    }
                }
            }

            if (null == errors) {
                errors = ValidationErrorList.<T>empty()
                    .concat(
                        context.validationError(
                            "Invalid character " + CharSequences.quoteIfChars(c) + " at " + text.lineInfo()
                                .textOffset() +
                                " expected " +
                                this.expected
                        )
                    );
            }
        }

        return errors;
    }

    private final CharPredicate predicate;

    /**
     * A short message when this particular predicate fails.
     */
    private final String expected;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.toString()
            .hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof TextMaskValidatorComponent &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final TextMaskValidatorComponent<?> other) {
        return this.toString.equals(other.toString());
    }

    @Override
    public String toString() {
        return this.toString;
    }

    private final String toString;
}
