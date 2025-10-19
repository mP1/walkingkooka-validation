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
import walkingkooka.collect.list.CsvStringList;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.TryingShortCircuitingConverter;
import walkingkooka.validation.ValidationCheckbox;

import java.util.List;
import java.util.Optional;

/**
 * A converter that handles converting a value into a {@link ValidationCheckbox}.
 * <pre>
 * "value1, "value2" -> ValidationCheckbox("value1", "value2")
 * list(value1, value2) -> ValidationCheckbox(list.get(0), list.get(1))
 * </pre>
 */
final class ValidationConverterValidationCheckbox<C extends ConverterContext> implements TryingShortCircuitingConverter<C> {

    /**
     * Type safe getter
     */
    static <C extends ConverterContext> ValidationConverterValidationCheckbox<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ValidationConverterValidationCheckbox INSTANCE = new ValidationConverterValidationCheckbox<>();

    /**
     * Private ctor
     */
    private ValidationConverterValidationCheckbox() {
        super();
    }

    @Override
    public boolean canConvert(final Object value,
                              final Class<?> type,
                              final C context) {
        return type == ValidationCheckbox.class &&
            (
                value instanceof ValidationCheckbox ||
                    context.canConvert(
                        value,
                        CsvStringList.class
                    ) ||
                    value instanceof List
            );
    }

    @Override
    public Object tryConvertOrFail(final Object value,
                                   final Class<?> type,
                                   final C context) {
        ValidationCheckbox validationCheckbox;

        if (value instanceof ValidationCheckbox) {
            validationCheckbox = (ValidationCheckbox) value;
        } else {
            if (value instanceof CharSequence) {
                validationCheckbox = checkbox(
                    context.convertOrFail(
                        value,
                        CsvStringList.class
                    )
                );
            } else {
                validationCheckbox = checkbox(
                    (List<?>) value
                );
            }
        }

        return validationCheckbox;
    }

    private static ValidationCheckbox checkbox(final List<?> values) {
        Optional<Object> trueValue;
        Optional<Object> falseValue;

        final int count = values.size();
        switch (count) {
            case 0:
                trueValue = Optional.of(true);
                falseValue = Optional.of(false);
                break;
            case 1:
                trueValue = Optional.of(values.get(0));
                falseValue = Optional.empty();
                break;
            case 2:
                trueValue = Optional.of(
                    values.get(0)
                );
                falseValue = Optional.of(
                    values.get(1)
                );
                break;
            default:
                throw new IllegalArgumentException("Invalid number of values " + count + " expected 0, 1, 2");
        }

        return ValidationCheckbox.with(
            trueValue,
            falseValue
        );
    }

    // Object...........................................................................................................

    @Override
    public String toString() {
        return "* to " + ValidationCheckbox.class.getSimpleName();
    }
}
