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
import walkingkooka.ToStringTesting;
import walkingkooka.collect.list.Lists;
import walkingkooka.convert.Converter;
import walkingkooka.convert.Converters;
import walkingkooka.convert.FakeConverterContext;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.ValidationCheckbox;

import java.util.Optional;

public final class ValidationConverterValidationCheckboxTest extends ValidationConverterTestCase<ValidationConverterValidationCheckbox<FakeConverterContext>, FakeConverterContext>
    implements ToStringTesting<ValidationConverterValidationCheckbox<FakeConverterContext>> {

    private final static TestValidationReference TEST_VALIDATION_REFERENCE = new TestValidationReference("Hello");

    private final static String MESSAGE = "Message111";

    private final static String MESSAGE2 = "Message222";

    @Test
    public void testConvertValidationCheckboxToStringFails() {
        this.convertFails(
            ValidationCheckbox.with(
                Optional.of(true),
                Optional.of(false)
            ),
            String.class
        );
    }

    @Test
    public void testConvertNullToValidationCheckboxFails() {
        this.convertFails(
            null,
            ValidationCheckbox.class
        );
    }

    @Test
    public void testConvertValidationCheckboxToValidationCheckbox() {
        this.convertAndCheck(
            ValidationCheckbox.TRUE_FALSE
        );
    }

    @Test
    public void testConvertEmptyStringToValidationCheckbox() {
        this.convertAndCheck(
            "",
            ValidationCheckbox.TRUE_FALSE
        );
    }

    @Test
    public void testConvertStringToValidationCheckbox() {
        this.convertAndCheck(
            "true111, false222",
            ValidationCheckbox.with(
                Optional.of(
                    "true111"
                ),
                Optional.of(
                    "false222"
                )
            )
        );
    }

    @Test
    public void testConvertEmptyListToValidationCheckbox() {
        this.convertAndCheck(
            Lists.empty(),
            ValidationCheckbox.TRUE_FALSE
        );
    }

    @Test
    public void testConvertListToValidationCheckbox() {
        this.convertAndCheck(
            Lists.of(
                "true111"
            ),
            ValidationCheckbox.with(
                Optional.of(
                    "true111"
                ),
                Optional.empty()
            )
        );
    }

    @Test
    public void testConvertListToValidationCheckbox2() {
        this.convertAndCheck(
            Lists.of(
                "true111",
                "false222"
            ),
            ValidationCheckbox.with(
                Optional.of(
                    "true111"
                ),
                Optional.of(
                    "false222"
                )
            )
        );
    }

    @Override
    public ValidationConverterValidationCheckbox<FakeConverterContext> createConverter() {
        return ValidationConverterValidationCheckbox.instance();
    }

    @Override
    public FakeConverterContext createContext() {
        return new FakeConverterContext() {
            @Override
            public boolean canConvert(final Object value,
                                      final Class<?> type) {
                return this.converter.canConvert(
                    value,
                    type,
                    this
                );
            }

            @Override
            public <T> Either<T, String> convert(final Object value,
                                                 final Class<T> target) {
                return this.converter.convert(
                    value,
                    target,
                    this
                );
            }

            private final Converter converter = Converters.collection(
                Lists.of(
                    Converters.characterOrCharSequenceOrHasTextOrStringToCharacterOrCharSequenceOrString(),
                    Converters.textToCsvStringList()
                )
            );

            @Override
            public char valueSeparator() {
                return ',';
            }
        };
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            ValidationConverterValidationCheckbox.instance(),
            "* to ValidationCheckbox"
        );
    }

    // class............................................................................................................

    @Override
    public Class<ValidationConverterValidationCheckbox<FakeConverterContext>> type() {
        return Cast.to(ValidationConverterValidationCheckbox.class);
    }
}
