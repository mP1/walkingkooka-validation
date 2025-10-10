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

package walkingkooka.validation.convert;

import walkingkooka.Cast;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.TryingShortCircuitingConverter;
import walkingkooka.validation.ValidationChoice;

import java.util.Optional;

/**
 * A converter that handles converting an object into a {@link ValidationChoice}.
 */
final class ValidationConverterToValidationChoice<C extends ConverterContext> implements TryingShortCircuitingConverter<C> {

    /**
     * Type safe getter
     */
    static <C extends ConverterContext> ValidationConverterToValidationChoice<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ValidationConverterToValidationChoice INSTANCE = new ValidationConverterToValidationChoice<>();

    /**
     * Private ctor
     */
    private ValidationConverterToValidationChoice() {
        super();
    }

    @Override
    public boolean canConvert(final Object value,
                              final Class<?> type,
                              final C context) {
        return type == ValidationChoice.class;
    }

    @Override
    public Object tryConvertOrFail(final Object value,
                                   final Class<?> type,
                                   final C context) {
        final String label = null == value ?
            "" :
            context.convertOrFail(
            value,
            String.class
        );
        return ValidationChoice.with(
            label,
            Optional.ofNullable(value)
        );
    }

    // Object...........................................................................................................

    @Override
    public String toString() {
        return "to " + ValidationChoice.class.getSimpleName();
    }
}
