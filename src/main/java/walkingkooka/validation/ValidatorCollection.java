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

import walkingkooka.collect.list.Lists;

import java.util.List;
import java.util.Objects;

/**
 * A {@link Validator} that contains many other validators, and tries them until the error count exceeds the given {@link #maxErrors}.
 */
final class ValidatorCollection<T extends ValidationReference> implements Validator<T> {

    static <T extends ValidationReference> ValidatorCollection<T> with(final int maxErrors,
                                                                       final List<Validator<T>> validators) {
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
                                final List<Validator<T>> validators) {
        this.maxErrors = maxErrors;

        if (validators.isEmpty()) {
            throw new IllegalArgumentException("Empty validators");
        }

        this.validators = validators;
    }

    @Override
    public List<ValidationError<T>> validate(final Object value,
                                             final ValidatorContext<T> context) {
        Objects.requireNonNull(context, "context");

        final int maxErrors = this.maxErrors;
        final List<ValidationError<T>> errors = Lists.array();

        for (final Validator<T> validator : this.validators) {
            final List<ValidationError<T>> newErrors = validator.validate(
                value,
                context
            );

            for (final ValidationError<T> error : newErrors) {
                if (false == errors.contains(error)) {
                    errors.add(error);
                }
            }

            if(errors.size() >= maxErrors) {
                break;
            }
        }

        return errors;
    }

    private final int maxErrors;
    private final List<Validator<T>> validators;

    @Override
    public String toString() {
        return "" + this.maxErrors + " " + this.validators;
    }
}
