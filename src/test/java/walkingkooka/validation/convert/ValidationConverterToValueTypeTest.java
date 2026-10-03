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
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.ConverterContexts;
import walkingkooka.validation.HasOptionalValueType;
import walkingkooka.validation.HasOptionalValueTypeTesting;
import walkingkooka.validation.HasValueType;
import walkingkooka.validation.ValueType;

import java.util.Optional;

public final class ValidationConverterToValueTypeTest extends ValidationConverterTestCase<ValidationConverterToValueType<ConverterContext>, ConverterContext>
    implements HasOptionalValueTypeTesting {

    @Test
    public void testConvertNotHasOptionalValueType() {
        this.convertFails(
            this,
            ValueType.class
        );
    }

    @Test
    public void testConvertValueType() {
        this.convertAndCheck(
            VALUE_TYPE,
            ValueType.class,
            VALUE_TYPE
        );
    }

    @Test
    public void testConvertHasValueType() {
        this.convertAndCheck(
            new HasValueType() {
                @Override
                public ValueType valueType() {
                    return VALUE_TYPE;
                }
            },
            ValueType.class,
            VALUE_TYPE
        );
    }

    @Test
    public void testConvertHasOptionalValueType() {
        this.convertAndCheck(
            new HasOptionalValueType() {
                @Override
                public Optional<ValueType> valueType() {
                    return OPTIONAL_VALUE_TYPE;
                }
            },
            ValueType.class,
            VALUE_TYPE
        );
    }

    @Test
    public void testConvertHasOptionalValueTypeEmpty() {
        this.convertAndCheck(
            new HasOptionalValueType() {
                @Override
                public Optional<ValueType> valueType() {
                    return NO_VALUE_TYPE;
                }
            },
            ValueType.class,
            null
        );
    }

    @Override
    public ValidationConverterToValueType<ConverterContext> createConverter() {
        return ValidationConverterToValueType.instance();
    }

    @Override
    public ConverterContext createContext() {
        return ConverterContexts.fake();
    }

    @Override
    public Class<ValidationConverterToValueType<ConverterContext>> type() {
        return Cast.to(ValidationConverterToValueType.class);
    }
}
