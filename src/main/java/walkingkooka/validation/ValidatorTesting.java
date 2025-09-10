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

import walkingkooka.test.Testing;

import java.util.List;
import java.util.Optional;

public interface ValidatorTesting extends Testing {

    default <R extends ValidationReference, C extends ValidatorContext<R>> void validateAndCheck(final Validator<R, C> validator,
                                                                                                 final Object value,
                                                                                                 final C context,
                                                                                                 final ValidationError<R>... expected) {
        this.validateAndCheck(
            validator,
            value,
            context,
            List.of(expected)
        );
    }

    default <R extends ValidationReference, C extends ValidatorContext<R>> void validateAndCheck(final Validator<R, C> validator,
                                                                                                 final Object value,
                                                                                                 final C context,
                                                                                                 final List<ValidationError<R>> expected) {
        this.checkEquals(
            expected,
            validator.validate(
                value,
                context
            )
        );
    }

    default <R extends ValidationReference, C extends ValidatorContext<R>> void choicesAndCheck(final Validator<R, C> validator,
                                                                                                final C context) {
        this.choicesAndCheck(
            validator,
            context,
            Optional.empty()
        );
    }

    default <R extends ValidationReference, C extends ValidatorContext<R>> void choicesAndCheck(final Validator<R, C> validator,
                                                                                                final C context,
                                                                                                final ValidationChoice... expected) {
        this.choicesAndCheck(
            validator,
            context,
            List.of(expected)
        );
    }

    default <R extends ValidationReference, C extends ValidatorContext<R>> void choicesAndCheck(final Validator<R, C> validator,
                                                                                                final C context,
                                                                                                final List<ValidationChoice> expected) {
        this.choicesAndCheck(
            validator,
            context,
            Optional.of(expected)
        );
    }

    default <R extends ValidationReference, C extends ValidatorContext<R>> void choicesAndCheck(final Validator<R, C> validator,
                                                                                                final C context,
                                                                                                final Optional<List<ValidationChoice>> expected) {
        this.checkEquals(
            expected,
            validator.choices(context)
        );
    }
}
