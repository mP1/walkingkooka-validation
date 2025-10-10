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
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.FakeConverterContext;
import walkingkooka.validation.ValidationChoice;

import java.util.Optional;

public final class ValidationConverterToValidationChoiceTest extends ValidationConverterTestCase<ValidationConverterToValidationChoice<ConverterContext>, ConverterContext> {

    @Test
    public void testConvertNotValidationChoice() {
        this.convertFails(
            "Text123",
            String.class
        );
    }

    @Test
    public void testConvertNullToValidationChoice() {
        this.convertAndCheck(
            null,
            ValidationChoice.class,
            ValidationChoice.with(
                "",
                Optional.empty()
            )
        );
    }

    @Test
    public void testConvertNumberToValidationChoice() {
        this.convertAndCheck(
            111,
            ValidationChoice.class,
            ValidationChoice.with(
                "Label " + 111,
                Optional.ofNullable(111)
            )
        );
    }

    @Test
    public void testConvertStringToValidationChoice() {
        this.convertAndCheck(
            "XYZ",
            ValidationChoice.class,
            ValidationChoice.with(
                "Label XYZ",
                Optional.ofNullable("XYZ")
            )
        );
    }

    @Override
    public ValidationConverterToValidationChoice<ConverterContext> createConverter() {
        return ValidationConverterToValidationChoice.instance();
    }

    @Override
    public ConverterContext createContext() {
        return new FakeConverterContext() {
            @Override
            public boolean canConvert(final Object value,
                                      final Class<?> type) {
                return String.class == type;
            }

            @Override
            public <T> Either<T, String> convert(final Object value,
                                                 final Class<T> target) {
                return target == String.class ?
                    this.successfulConversion(
                        "Label " + value,
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
            ValidationConverterToValidationChoice.instance(),
            "to ValidationChoice"
        );
    }

    // class............................................................................................................

    @Override
    public Class<ValidationConverterToValidationChoice<ConverterContext>> type() {
        return Cast.to(ValidationConverterToValidationChoice.class);
    }
}
