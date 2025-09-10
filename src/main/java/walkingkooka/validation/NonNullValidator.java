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
import java.util.Optional;

/**
 * A {@link Validator} that adds an error if the value is null.
 */
final class NonNullValidator<R extends ValidationReference, C extends ValidatorContext<R>> implements Validator<R, C> {

    /**
     * Type safe getter
     */
    static <R extends ValidationReference, C extends ValidatorContext<R>> NonNullValidator<R, C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static NonNullValidator<?, ?> INSTANCE = new NonNullValidator<>();

    private NonNullValidator() {
        super();
    }

    @Override
    public List<ValidationError<R>> validate(final Object value,
                                             final C context) {
        Objects.requireNonNull(context, "context");

        return null == value ?
            Lists.of(
                context.validationError(
                    "Missing " + context.validationReference()
                )
            ) :
            Lists.empty();
    }

    // choices..........................................................................................................

    @Override
    public Optional<List<ValidationChoice>> choices(final ValidatorContext<R> context) {
        Objects.requireNonNull(context, "context");
        return NO_CHOICES;
    }

    // Object...........................................................................................................

    @Override
    public String toString() {
        return "NonNull";
    }
}
