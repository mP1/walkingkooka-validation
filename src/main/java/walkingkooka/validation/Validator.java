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

import java.util.List;
import java.util.Optional;

/**
 * A validator accepts a value and potentially produces {@link ValidationError}.
 */
public interface Validator<R extends ValidationReference, C extends ValidatorContext<R>> {

    /**
     * Validates the given value returning zero or more {@link ValidationError}.
     */
    List<ValidationError<R>> validate(final Object value,
                                      final C context);

    /**
     * Helper that may be used to return no errors.
     */
    default ValidationErrorList<R> noValidationErrors() {
        return ValidationErrorList.empty();
    }

    Optional<ValidationPromptValue> NO_PROMPT_VALUES = Optional.empty();

    /**
     * Some validators may return a {@link ValidationPromptValue} as part of a {@link ValidationError}.
     */
    Optional<ValidationPromptValue> promptValue(final ValidatorContext<R> context);
}
