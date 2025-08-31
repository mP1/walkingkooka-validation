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
import walkingkooka.Either;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.ShortCircuitingConverter;
import walkingkooka.validation.provider.HasOptionalValidatorSelector;
import walkingkooka.validation.provider.ValidatorSelector;

/**
 * A {@link walkingkooka.convert.Converter} that may be used to get a {@link ValidatorSelector}.
 */
final class HasOptionalValidatorSelectorConverter<C extends ConverterContext> implements ShortCircuitingConverter<C> {

    /**
     * Type safe getter
     */
    static <C extends ConverterContext> HasOptionalValidatorSelectorConverter<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static HasOptionalValidatorSelectorConverter<?> INSTANCE = new HasOptionalValidatorSelectorConverter<>();

    private HasOptionalValidatorSelectorConverter() {
        super();
    }

    @Override
    public boolean canConvert(final Object value,
                              final Class<?> type,
                              final C context) {
        return value instanceof HasOptionalValidatorSelector &&
            ValidatorSelector.class == type;
    }

    @Override
    public <T> Either<T, String> doConvert(final Object value,
                                           final Class<T> type,
                                           final C context) {
        return this.successfulConversion(
            ((HasOptionalValidatorSelector) value)
                .validatorSelector()
                .orElse(null),
            type
        );
    }

    // Object...........................................................................................................

    @Override
    public String toString() {
        return HasOptionalValidatorSelector.class.getSimpleName();
    }
}
