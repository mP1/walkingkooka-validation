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

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface ValidatorTesting2<V extends Validator<R, C>, R extends ValidationReference, C extends ValidatorContext<R>> extends ValidatorTesting {

    @Test
    default void testValidateWithNullContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createValidator()
                .validate(
                    "Hello",
                    null
                )
        );
    }

    default void validateAndCheck(final Object value,
                                  final C context,
                                  final ValidationError<R>... expected) {
        this.validateAndCheck(
            value,
            context,
            List.of(expected)
        );
    }

    default void validateAndCheck(final Object value,
                                  final C context,
                                  final List<ValidationError<R>> expected) {
        this.validateAndCheck(
            this.createValidator(),
            value,
            context,
            expected
        );
    }

    V createValidator();

    C createContext();
}
