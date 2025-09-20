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

import walkingkooka.NeverError;
import walkingkooka.collect.list.Lists;
import walkingkooka.text.CharSequences;
import walkingkooka.text.cursor.TextCursor;
import walkingkooka.text.printer.TreePrintable;

import java.util.Iterator;
import java.util.List;

abstract class TextMaskValidatorComponent<T extends ValidationReference> implements TreePrintable {

    static <T extends ValidationReference> List<TextMaskValidatorComponent<T>> parse(final TextCursor mask) {
        List<TextMaskValidatorComponent<T>> components = Lists.array();

        final int MODE_NORMAL = 1;
        final int MODE_BACK_SLASH_ESCAPE = 2;
        final int MODE_NOT = 3;
        final int MODE_INSIDE_QUOTES = 4;
        final int MODE_INSIDE_QUOTES_BACKSLASH_ESCAPE = 5;
        final int MODE_NOT_INSIDE_QUOTES = 6;
        final int MODE_NOT_INSIDE_QUOTES_BACKSLASH_ESCAPE = 7;

        int mode = MODE_NORMAL;

        TextMaskValidatorComponent<T> component = null;
        StringBuilder textLiteral = null;

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
                        case DASH:
                            component = character(DASH);
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
                        case OPTIONAL:
                            // optional PLUS must have a mask control character before
                            if(null == component) {
                                component = components.isEmpty() ?
                                    null :
                                    components.remove(components.size() - 1);
                            }

                            if(null == component) {
                                throw new IllegalArgumentException("Optional " + CharSequences.quoteIfChars(OPTIONAL) + ": Missing component before");
                            }
                            component = optional(component);
                            break;
                        case REPEATING:
                            // repeating PLUS must have a mask control character before
                            if(null == component) {
                                component = components.isEmpty() ?
                                    null :
                                    components.remove(components.size() - 1);
                            }

                            if(null == component) {
                                throw new IllegalArgumentException("Repeating " + CharSequences.quoteIfChars(REPEATING) + ": Missing component before");
                            }
                            component = repeating(component);
                            break;
                        case SLASH:
                            component = character(SLASH);
                            break;
                        case SPACE:
                            component = character(SPACE);
                            break;
                        case UPPER_CASE_LETTER:
                            component = upperCaseLetter();
                            break;
                        case '\"':
                            mode = MODE_NOT == mode ?
                                MODE_NOT_INSIDE_QUOTES :
                                MODE_INSIDE_QUOTES;
                            component = null;
                            textLiteral = new StringBuilder();
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
                            components.add(
                                textLiteral(
                                    textLiteral.toString()
                                )
                            );
                            textLiteral = null;
                            mode = MODE_NORMAL;
                            break;
                        default:
                            textLiteral.append(c);
                            break;
                    }
                    break;
                case MODE_INSIDE_QUOTES_BACKSLASH_ESCAPE:
                    textLiteral.append(c);
                    mode = MODE_INSIDE_QUOTES;
                    break;
                case MODE_NOT_INSIDE_QUOTES:
                    switch (c) {
                        case '\\':
                            mode = MODE_NOT_INSIDE_QUOTES_BACKSLASH_ESCAPE;
                            break;
                        case '"':
                            components.add(
                                not(
                                    textLiteral(
                                        textLiteral.toString()
                                    )
                                )
                            );
                            textLiteral = null;
                            mode = MODE_NORMAL;
                            break;
                        default:
                            textLiteral.append(c);
                            break;
                    }
                    break;
                case MODE_NOT_INSIDE_QUOTES_BACKSLASH_ESCAPE:
                    textLiteral.append(c);
                    mode = MODE_NOT_INSIDE_QUOTES;
                    break;
                default:
                    NeverError.unhandledCase(
                        mode,
                        MODE_NORMAL,
                        MODE_BACK_SLASH_ESCAPE,
                        MODE_INSIDE_QUOTES,
                        MODE_INSIDE_QUOTES_BACKSLASH_ESCAPE,
                        MODE_NOT_INSIDE_QUOTES,
                        MODE_NOT_INSIDE_QUOTES_BACKSLASH_ESCAPE
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
            case MODE_NOT_INSIDE_QUOTES:
            case MODE_NOT_INSIDE_QUOTES_BACKSLASH_ESCAPE:
                throw new IllegalArgumentException("Unclosed double quotes");
            default:
                NeverError.unhandledCase(
                    mode,
                    MODE_NORMAL,
                    MODE_BACK_SLASH_ESCAPE,
                    MODE_INSIDE_QUOTES,
                    MODE_INSIDE_QUOTES_BACKSLASH_ESCAPE,
                    MODE_NOT_INSIDE_QUOTES,
                    MODE_NOT_INSIDE_QUOTES_BACKSLASH_ESCAPE
                );
                break;
        }

        if (null != component) {
            components.add(component);
        }

        return components;
    }

    final static char DASH = '-';
    final static char SLASH = '/';
    final static char SPACE = ' ';


    final static char ANY = '?';

    /**
     * {@link TextMaskValidatorComponentCharacterAny}
     */
    static <T extends ValidationReference> TextMaskValidatorComponent<T> any() {
        return TextMaskValidatorComponentCharacterAny.instance();
    }

    /**
     * {@link TextMaskValidatorComponentCharacterChar}
     */
    static <T extends ValidationReference> TextMaskValidatorComponent<T> character(final char c) {
        return TextMaskValidatorComponentCharacterChar.with(
            c,
            CharSequences.quoteIfChars(c).toString()
        );
    }

    final static char DIGIT = '9';

    /**
     * {@link TextMaskValidatorComponentCharacterDigit}
     */
    static <T extends ValidationReference> TextMaskValidatorComponent<T> digit() {
        return TextMaskValidatorComponentCharacterDigit.instance();
    }

    /**
     * Matches the given character which was escaped in the initial mask
     */
    static <T extends ValidationReference> TextMaskValidatorComponent<T> escaped(final char c) {
        return TextMaskValidatorComponentCharacterChar.with(
            c,
            "\\" + CharSequences.escape(
                Character.toString(c)
            )
        );
    }

    final static char LETTER = 'A';

    /**
     * {@link TextMaskValidatorComponentCharacterLetter}
     */
    static <T extends ValidationReference> TextMaskValidatorComponent<T> letter() {
        return TextMaskValidatorComponentCharacterLetter.instance();
    }

    final static char LOWER_CASE_LETTER = 'L';

    /**
     * {@link TextMaskValidatorComponentCharacterLowerCaseLetter}
     */
    static <T extends ValidationReference> TextMaskValidatorComponent<T> lowerCaseLetter() {
        return TextMaskValidatorComponentCharacterLowerCaseLetter.instance();
    }

    final static char NOT = '~';

    /**
     * {@link TextMaskValidatorComponentNot}
     */
    static <T extends ValidationReference> TextMaskValidatorComponent<T> not(final TextMaskValidatorComponent<T> component) {
        return TextMaskValidatorComponentNot.with(component);
    }

    final static char OPTIONAL = '+';

    /**
     * {@link TextMaskValidatorComponentOptional}
     */
    static <T extends ValidationReference> TextMaskValidatorComponent<T> optional(final TextMaskValidatorComponent<T> component) {
        return TextMaskValidatorComponentOptional.with(component);
    }

    final static char REPEATING = '*';

    /**
     * {@link TextMaskValidatorComponentRepeating}
     */
    static <T extends ValidationReference> TextMaskValidatorComponent<T> repeating(final TextMaskValidatorComponent<T> component) {
        return TextMaskValidatorComponentRepeating.with(component);
    }

   final static char UPPER_CASE_LETTER = 'U';
    
    /**
     * {@see TextMaskValidatorComponentTextLiteral}
     */
    static <T extends ValidationReference> TextMaskValidatorComponent<T> textLiteral(final String text) {
        return TextMaskValidatorComponentTextLiteral.with(text);
    }

    /**
     * {@link TextMaskValidatorComponentCharacterUpperCaseLetter}
     */
    static <T extends ValidationReference> TextMaskValidatorComponent<T> upperCaseLetter() {
        return TextMaskValidatorComponentCharacterUpperCaseLetter.instance();
    }

    TextMaskValidatorComponent() {
        super();
    }

    abstract ValidationErrorList<T> tryMatch(final TextCursor text,
                                             final boolean not,
                                             final Iterator<TextMaskValidatorComponent<T>> nextComponent,
                                             final ValidatorContext<T> context);

    final ValidationErrorList<T> endOfText(final ValidatorContext<T> context) {
        return ValidationErrorList.<T>empty()
            .concat(
                context.validationError(
                    "End of text expected " + this.expected()
                )
            );
    }

    final ValidationErrorList<T> invalidCharacterIfNotEmpty(final TextCursor text,
                                                            final ValidatorContext<T> context) {
        return text.isEmpty() ?
            context.validationErrorList() :
            context.validationErrorList()
                .concat(
                    context.validationError(
                        "Invalid character " + CharSequences.quoteIfChars(text.at()) + " at " + text.lineInfo()
                            .textOffset()
                    )
                );
    }

    final ValidationErrorList<T> invalidCharacter(final TextCursor text,
                                                  final ValidatorContext<T> context) {
        return ValidationErrorList.<T>empty()
            .concat(
                context.validationError(
                    "Invalid character " + CharSequences.quoteIfChars(text.at()) + " at " + text.lineInfo()
                        .textOffset() +
                        " expected " +
                        this.expected()
                )
            );
    }

    abstract CharSequence expected();

    @Override
    abstract public String toString();
}
