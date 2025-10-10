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

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.Either;
import walkingkooka.collect.list.Lists;
import walkingkooka.convert.FakeConverterContext;
import walkingkooka.validation.ValidationChoice;
import walkingkooka.validation.ValidationChoiceList;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;

public final class ValidationConverterValidationChoiceListTest extends ValidationConverterTestCase<ValidationConverterValidationChoiceList<FakeConverterContext>, FakeConverterContext> {

    private final static String STRING_VALUE = "stringValue";

    private final static ValidationChoice CHOICE = ValidationChoice.with(
        STRING_VALUE,
        Optional.of(STRING_VALUE)
    );

    private final static ValidationChoice CHOICE2 = ValidationChoice.with(
        "label2",
        Optional.of(2)
    );

    @Test
    public void testConvertValidationChoiceListToStringFails() {
        this.convertFails(
            ValidationChoiceList.EMPTY,
            String.class
        );
    }

    @Test
    public void testConvertNull() {
        this.convertAndCheck(
            null,
            ValidationChoiceList.EMPTY
        );
    }

    @Test
    public void testConvertString() {
        this.convertAndCheck(
            STRING_VALUE,
            ValidationChoiceList.EMPTY
                .concat(CHOICE)
        );
    }

    @Test
    public void testConvertValidationChoice() {
        this.convertAndCheck(
            CHOICE,
            ValidationChoiceList.EMPTY
                .concat(CHOICE)
        );
    }

    @Test
    public void testConvertEmptyList() {
        this.convertAndCheck(
            Lists.empty(),
            ValidationChoiceList.EMPTY
        );
    }

    @Test
    public void testConvertListOfNull() {
        this.convertFails(
            Arrays.asList((Object) null),
            ValidationChoiceList.class
        );
    }

    @Test
    public void testConvertListString() {
        this.convertAndCheck(
            Lists.of(CHOICE),
            ValidationChoiceList.EMPTY
                .concat(CHOICE)
        );
    }

    @Test
    public void testConvertListStringToValidationChoiceList2() {
        this.convertAndCheck(
            Lists.of(
                CHOICE,
                CHOICE2
            ),
            ValidationChoiceList.EMPTY
                .concat(CHOICE)
                .concat(CHOICE2)
        );
    }

    @Test
    public void testConvertListValidationChoice() {
        this.convertAndCheck(
            Lists.of(CHOICE),
            ValidationChoiceList.EMPTY
                .concat(CHOICE)
        );
    }

    @Test
    public void testConvertListValidationChoice2() {
        this.convertAndCheck(
            Lists.of(
                CHOICE,
                CHOICE2
            ),
            ValidationChoiceList.EMPTY
                .concat(CHOICE)
                .concat(CHOICE2)
        );
    }

    @Test
    public void testConvertValidationChoiceListToValidationChoiceList() {
        final ValidationChoiceList list = ValidationChoiceList.EMPTY.setElements(
            Lists.of(
                CHOICE
            )
        );

        assertSame(
            list,
            this.convertAndCheck(
                list,
                list
            )
        );
    }

    @Override
    public ValidationConverterValidationChoiceList<FakeConverterContext> createConverter() {
        return ValidationConverterValidationChoiceList.instance();
    }

    @Override
    public FakeConverterContext createContext() {
        return new FakeConverterContext() {
            @Override
            public boolean canConvert(final Object value,
                                      final Class<?> type) {
                return (value instanceof String || value instanceof ValidationChoice) &&
                    ValidationChoice.class == type;
            }

            @Override
            public <T> Either<T, String> convert(final Object value,
                                                 final Class<T> target) {
                return value instanceof String && target == ValidationChoice.class ?
                    this.successfulConversion(
                        ValidationChoice.with(
                            (String) value,
                            Optional.of(value)
                        ),
                        target
                    ) :
                    value instanceof ValidationChoice && target == ValidationChoice.class ?
                        this.successfulConversion(
                            value,
                            target
                        ) :
                        this.failConversion(
                            value,
                            target
                        );
            }
        };
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            ValidationConverterValidationChoiceList.instance(),
            "* to ValidationChoiceList"
        );
    }

    // class............................................................................................................

    @Override
    public Class<ValidationConverterValidationChoiceList<FakeConverterContext>> type() {
        return Cast.to(ValidationConverterValidationChoiceList.class);
    }
}
