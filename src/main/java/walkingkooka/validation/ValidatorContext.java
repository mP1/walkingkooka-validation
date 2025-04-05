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

import walkingkooka.convert.CanConvert;
import walkingkooka.environment.EnvironmentContext;

/**
 * {@link walkingkooka.Context} that accompanies a {@link Validator} during validation.
 */
public interface ValidatorContext<T extends ValidationReference> extends CanConvert,
    EnvironmentContext {

    /**
     * The current {@link ValidationReference}.
     * Useful when creating a {@link ValidationError} to report an error.
     */
    T validationReference();

    /**
     * May be used to return a {@link ValidatorContext} with a new or different {@link ValidationReference}.
     */
    ValidatorContext<T> setValidationReference(final T reference);

    /**
     * Factory that creates a {@link ValidationError} using the current {@link ValidationReference}.
     */
    default ValidationError<T> validationError(final String message) {
        return ValidationError.with(
            this.validationReference(),
            message
        );
    }
}
