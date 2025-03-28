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

import java.util.List;
import java.util.Objects;

/**
 * A {@link Validator} that contains many other validators, and tries them until the error count exceeds the given {@link #maxErrors}.
 */
final class ValidatorCollection<R extends ValidationReference, C extends ValidatorContext<R>> implements Validator<R, C> {

    static <R extends ValidationReference, C extends ValidatorContext<R>> ValidatorCollection<R, C> with(final int maxErrors,
                                                                                                         final List<Validator<R, C>> validators) {
        if (maxErrors <= 0) {
            throw new IllegalArgumentException("Invalid maxErrors " + maxErrors + " <= 0");
        }

        return new ValidatorCollection<>(
            maxErrors,
            Lists.immutable(
                Objects.requireNonNull(validators, "validators")
            )
        );
    }

    private ValidatorCollection(final int maxErrors,
                                final List<Validator<R, C>> validators) {
        this.maxErrors = maxErrors;

        if (validators.isEmpty()) {
            throw new IllegalArgumentException("Empty validators");
        }

        this.validators = validators;
    }

    @Override
    public List<ValidationError<R>> validate(final Object value,
                                             final C context) {
        Objects.requireNonNull(context, "context");

        final int maxErrors = this.maxErrors;
        final List<ValidationError<R>> errors = Lists.array();

        for (final Validator<R, C> validator : this.validators) {
            final List<ValidationError<R>> newErrors = validator.validate(
                value,
                context
            );

            for (final ValidationError<R> error : newErrors) {
                if (false == errors.contains(error)) {
                    errors.add(error);
                }
            }

            if (errors.size() >= maxErrors) {
                break;
            }
        }

        return errors;
    }

    private final int maxErrors;
    private final List<Validator<R, C>> validators;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return Objects.hash(
            this.maxErrors,
            this.validators
        );
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof ValidatorCollection &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final ValidatorCollection<?, ?> other) {
        return this.maxErrors == other.maxErrors &&
            this.validators.equals(other.validators);
    }

    @Override
    public String toString() {
        return "" + this.maxErrors + " " + this.validators;
    }
}
