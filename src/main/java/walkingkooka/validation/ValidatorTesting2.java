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

public interface ValidatorTesting2<T extends Validator> extends ValidatorTesting {

    @Test
    default void testValidateWithNullContextFails() {
        this.validateAndCheck(
            "Hello",
            null
        );
    }

    default void validateAndCheck(final Object value,
                                  final ValidatorContext context,
                                  ValidationError... expected) {
        this.validateAndCheck(
            value,
            context,
            List.of(expected)
        );
    }

    default void validateAndCheck(final Object value,
                                  final ValidatorContext context,
                                  final List<ValidationError> expected) {
        this.validateAndCheck(
            this.createValidator(),
            value,
            context,
            expected
        );
    }

    T createValidator();

    ValidatorContext createContext();
}
