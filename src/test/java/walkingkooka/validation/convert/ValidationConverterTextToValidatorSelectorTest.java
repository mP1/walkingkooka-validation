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
import walkingkooka.validation.provider.ValidatorSelector;

public final class ValidationConverterTextToValidatorSelectorTest extends ValidationConverterTestCase<ValidationConverterTextToValidatorSelector<FakeConverterContext>, FakeConverterContext> {

    @Test
    public void testConvertNumberToValidatorSelector() {
        this.convertFails(
            123,
            ValidatorSelector.class
        );
    }

    @Test
    public void testConvertStringToValidatorSelector() {
        final String text = "hello-validator";

        this.convertAndCheck(
            text,
            ValidatorSelector.parse(text)
        );
    }

    @Override
    public ValidationConverterTextToValidatorSelector<FakeConverterContext> createConverter() {
        return ValidationConverterTextToValidatorSelector.instance();
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
            ValidationConverterTextToValidatorSelector.instance(),
            "Text to ValidatorSelector"
        );
    }

    @Override
    public Class<ValidationConverterTextToValidatorSelector<FakeConverterContext>> type() {
        return Cast.to(ValidationConverterTextToValidatorSelector.class);
    }
}
