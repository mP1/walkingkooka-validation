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

import walkingkooka.text.CharSequences;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A {@link Validator} that assumes a {@link String text} value and verifies its length is between the given range.
 */
final class TextLengthValidator<R extends ValidationReference, C extends ValidatorContext<R>> implements Validator<R, C> {

    static <R extends ValidationReference, C extends ValidatorContext<R>> TextLengthValidator<R, C> with(final int minLength,
                                                                                                         final int maxLength) {
        if (minLength < 0) {
            throw new IllegalArgumentException("Invalid minLength " + minLength + " < 0");
        }
        if (maxLength < minLength) {
            throw new IllegalArgumentException("Invalid maxLength " + maxLength + " < " + minLength);
        }
        return new TextLengthValidator<>(
            minLength,
            maxLength
        );
    }

    private TextLengthValidator(final int minLength,
                                final int maxLength) {
        super();
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    // Validator........................................................................................................

    @Override
    public List<ValidationError<R>> validate(final Object value,
                                             final C context) {
        Objects.requireNonNull(context, "context");

        final int minLength = this.minLength;
        final int maxLength = this.maxLength;

        ValidationErrorList<R> errors = context.validationErrorList();

        final String text = context.convertOrFail(
            value,
            String.class
        );

        if (minLength > 0 && CharSequences.isNullOrEmpty(text)) {
            errors = errors.concat(
                context.validationError()
                    .setMessage("Missing required text")
            );
        } else {
            final int textLength = text.length();
            if (textLength < minLength) {
                errors = errors.concat(
                    context.validationError()
                        .setMessage("Text length " + textLength + " < " + minLength)
                );
            }
            if (textLength > maxLength) {
                errors = errors.concat(
                    context.validationError()
                        .setMessage("Text length " + textLength + " > " + minLength)
                );
            }
        }

        return errors;
    }

    private final int minLength;

    private final int maxLength;

    // choices..........................................................................................................

    @Override
    public Optional<List<ValidationChoice>> choices(final ValidatorContext<R> context) {
        Objects.requireNonNull(context, "context");
        return NO_CHOICES;
    }

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return Objects.hash(
            this.minLength,
            this.maxLength
        );
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof TextLengthValidator && this.equals0((TextLengthValidator<?, ?>) other);
    }

    private boolean equals0(final TextLengthValidator<?, ?> other) {
        return this.minLength == other.minLength &&
            this.maxLength == other.maxLength;
    }

    @Override
    public String toString() {
        return "TextLength minLength=" + this.minLength + " maxLength=" + this.maxLength;
    }
}
