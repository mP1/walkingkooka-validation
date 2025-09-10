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
import walkingkooka.convert.Converter;
import walkingkooka.convert.Converters;
import walkingkooka.convert.FakeConverterContext;
import walkingkooka.validation.form.FormName;

public final class ValidationConverterTextToFormNameTest extends ValidationConverterTestCase<ValidationConverterTextToFormName<FakeConverterContext>, FakeConverterContext> {

    @Test
    public void testConvertNumberToFormName() {
        this.convertFails(
            123,
            FormName.class
        );
    }

    @Test
    public void testConvertStringToFormName() {
        final String text = "HelloForm123";

        this.convertAndCheck(
            text,
            FormName.with(text)
        );
    }

    @Override
    public ValidationConverterTextToFormName<FakeConverterContext> createConverter() {
        return ValidationConverterTextToFormName.instance();
    }

    @Override
    public FakeConverterContext createContext() {
        return new FakeConverterContext() {
            @Override
            public boolean canConvert(final Object value,
                                      final Class<?> type) {
                return this.textTo.canConvert(
                    value,
                    type,
                    this
                );
            }

            @Override
            public <T> Either<T, String> convert(final Object value,
                                                 final Class<T> target) {
                return this.textTo.convert(
                    value,
                    target,
                    this
                );
            }

            private final Converter<FakeConverterContext> textTo = Converters.characterOrCharSequenceOrHasTextOrStringToCharacterOrCharSequenceOrString();
        };
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(
            ValidationConverterTextToFormName.instance(),
            "Text to FormName"
        );
    }

    @Override
    public Class<ValidationConverterTextToFormName<FakeConverterContext>> type() {
        return Cast.to(ValidationConverterTextToFormName.class);
    }
}
