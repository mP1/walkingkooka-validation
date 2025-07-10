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
import walkingkooka.collect.list.Lists;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.TryingShortCircuitingConverter;
import walkingkooka.validation.ValidationError;
import walkingkooka.validation.ValidationErrorList;

import java.util.List;

/**
 * A converter that handles converting the given value to a {@link ValidationErrorList}.
 * <ul>
 *     <li>null becomes {@link ValidationErrorList#empty()}</li>
 *     <li>non list such as {@link String} if it can be converted into a {@link ValidationError}</li>
 *     <li>A single {@link ValidationError}</li>
 *     <li>A list of elements that can each be converted into a {@link ValidationError}, with nulls skipped</li>
 *     <li>A {@link ValidationErrorList}</li>
 * </ul>
 */
final class ValidationErrorListConverter<C extends ConverterContext> implements TryingShortCircuitingConverter<C> {

    /**
     * Type safe getter
     */
    static <C extends ConverterContext> ValidationErrorListConverter<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ValidationErrorListConverter<?> INSTANCE = new ValidationErrorListConverter<>();

    /**
     * Private ctor
     */
    private ValidationErrorListConverter() {
        super();
    }

    @Override
    public boolean canConvert(final Object value,
                              final Class<?> type,
                              final C context) {
        return type == ValidationErrorList.class &&
            (
                null == value ||
                    value instanceof ValidationErrorList ||
                    value instanceof ValidationError ||
                    context.canConvert(
                        value,
                        ValidationError.class
                    ) ||
                    value instanceof List &&
                        this.canConvertList(
                            (List<?>) value,
                            context
                        )
            );
    }

    // nulls are skipped
    private boolean canConvertList(final List<?> list,
                                   final C context) {
        return list.stream()
            .allMatch(e ->
                null == e ||
                    context.canConvert(
                        e,
                        ValidationError.class
                    )
            );
    }

    @Override
    public Object tryConvertOrFail(final Object value,
                                   final Class<?> type,
                                   final C context) {
        ValidationErrorList<?> validationErrorList;

        if (null == value) {
            validationErrorList = ValidationErrorList.empty();
        } else {
            if (value instanceof ValidationErrorList) {
                validationErrorList = (ValidationErrorList<?>) value;
            } else {
                if (value instanceof List) {
                    final List<ValidationError<?>> list = Lists.array();

                    // skip nulls, try and convert all other elements to ValidationError
                    for (final Object element : (List<?>) value) {
                        if (null != element) {
                            list.add(
                                context.convertOrFail(
                                    element,
                                    ValidationError.class
                                )
                            );
                        }
                    }

                    validationErrorList = ValidationErrorList.with(
                        Cast.to(list)
                    );
                } else {
                    validationErrorList = ValidationErrorList.empty()
                        .concat(
                            context.convertOrFail(
                                value,
                                ValidationError.class
                            )
                        );
                }
            }
        }

        return validationErrorList;
    }

    // Object...........................................................................................................

    @Override
    public String toString() {
        return "* to " + this.getClass().getSimpleName();
    }
}
