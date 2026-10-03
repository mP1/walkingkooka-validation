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
import walkingkooka.validation.HasOptionalValueType;
import walkingkooka.validation.HasValueType;
import walkingkooka.validation.ValueType;

/**
 * A {@link walkingkooka.convert.Converter} that may be used to get a {@link ValueType}.
 */
final class ValidationConverterToValueType<C extends ConverterContext> implements ShortCircuitingConverter<C> {

    /**
     * Type safe getter
     */
    static <C extends ConverterContext> ValidationConverterToValueType<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ValidationConverterToValueType<?> INSTANCE = new ValidationConverterToValueType<>();

    private ValidationConverterToValueType() {
        super();
    }

    @Override
    public boolean canConvert(final Object value,
                              final Class<?> type,
                              final C context) {
        return (value instanceof HasValueType ||
            value instanceof HasOptionalValueType) &&
            ValueType.class == type;
    }

    @Override
    public <T> Either<T, String> doConvert(final Object value,
                                           final Class<T> type,
                                           final C context) {
        return this.successfulConversion(
            value instanceof HasValueType ?
                ((HasValueType) value).valueType() :
                value instanceof HasOptionalValueType ?
                    ((HasOptionalValueType) value).valueType()
                        .orElse(null) :
                    null,
            type
        );
    }

    // Object...........................................................................................................

    @Override
    public String toString() {
        return "to " + ValueType.class.getSimpleName();
    }
}
