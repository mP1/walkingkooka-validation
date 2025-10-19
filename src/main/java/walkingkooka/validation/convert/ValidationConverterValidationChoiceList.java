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
import walkingkooka.collect.list.Lists;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.TryingShortCircuitingConverter;
import walkingkooka.validation.ValidationChoice;
import walkingkooka.validation.ValidationChoiceList;

import java.util.List;

/**
 * A converter that handles converting the given value to a {@link ValidationChoiceList}.
 */
final class ValidationConverterValidationChoiceList<C extends ConverterContext> implements TryingShortCircuitingConverter<C> {

    /**
     * Type safe getter
     */
    static <C extends ConverterContext> ValidationConverterValidationChoiceList<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ValidationConverterValidationChoiceList INSTANCE = new ValidationConverterValidationChoiceList<>();

    /**
     * Private ctor
     */
    private ValidationConverterValidationChoiceList() {
        super();
    }

    @Override
    public boolean canConvert(final Object value,
                              final Class<?> type,
                              final C context) {
        return type == ValidationChoiceList.class &&
            (
                null == value ||
                    value instanceof ValidationChoiceList ||
                    value instanceof ValidationChoice ||
                    context.canConvert(
                        value,
                        ValidationChoice.class
                    ) ||
                    value instanceof List &&
                        this.canConvertList(
                            (List<?>) value,
                            context
                        ) ||
                    context.canConvert(
                            value,
                            CsvStringList.class
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
                        ValidationChoice.class
                    )
            );
    }

    @Override
    public Object tryConvertOrFail(final Object value,
                                   final Class<?> type,
                                   final C context) {
        ValidationChoiceList validationChoiceList;

        if (null == value) {
            validationChoiceList = ValidationChoiceList.EMPTY;
        } else {
            if (value instanceof ValidationChoiceList) {
                // ValidationChoiceList -> ValidationChoiceList
                validationChoiceList = (ValidationChoiceList) value;
            } else {
                if (value instanceof List) {
                    // eg list( ValidationChoice, ValidationChoice, ValidationChoice)
                    validationChoiceList = ValidationChoiceList.EMPTY.setElements(
                        convertToValidationChoice(
                            (List<?>)value,
                            context
                        )
                    );
                } else {
                    // eg "Label1, Label2, Labell3"
                    if( value instanceof CharSequence ) {
                        validationChoiceList = ValidationChoiceList.EMPTY.setElements(
                            convertToValidationChoice(
                                context.convertOrFail(
                                    value,
                                    CsvStringList.class
                                ),
                                context
                            )
                        );
                    } else {
                        validationChoiceList = ValidationChoiceList.EMPTY
                            .concat(
                                context.convertOrFail(
                                    value,
                                    ValidationChoice.class
                                )
                            );
                    }
                }
            }
        }

        return validationChoiceList;
    }

    private static List<ValidationChoice> convertToValidationChoice(final List<?> value,
                                                                    final ConverterContext context) {
        final List<ValidationChoice> list = Lists.array();

        for (final Object element : value) {
            list.add(
                context.convertOrFail(
                    element,
                    ValidationChoice.class
                )
            );
        }

        return list;
    }

    // Object...........................................................................................................

    @Override
    public String toString() {
        return "* to " + ValidationChoiceList.class.getSimpleName();
    }
}
