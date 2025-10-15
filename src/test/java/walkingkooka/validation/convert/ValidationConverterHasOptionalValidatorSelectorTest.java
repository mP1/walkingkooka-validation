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
import walkingkooka.validation.provider.HasOptionalValidatorSelector;
import walkingkooka.validation.provider.ValidatorSelector;

import java.util.Optional;

public final class ValidationConverterHasOptionalValidatorSelectorTest extends ValidationConverterTestCase<ValidationConverterHasOptionalValidatorSelector<ConverterContext>, ConverterContext> {

    @Test
    public void testConvertNotHasOptionalValidatorSelector() {
        this.convertFails(
            this,
            ValidatorSelector.class
        );
    }

    @Test
    public void testConvertHasOptionalValidatorSelector() {
        final ValidatorSelector selector = ValidatorSelector.parse("hello");

        this.convertAndCheck(
            new HasOptionalValidatorSelector() {
                @Override
                public Optional<ValidatorSelector> validatorSelector() {
                    return Optional.of(selector);
                }
            },
            ValidatorSelector.class,
            selector
        );
    }

    @Test
    public void testConvertHasOptionalValidatorSelectorEmpty() {
        this.convertAndCheck(
            new HasOptionalValidatorSelector() {
                @Override
                public Optional<ValidatorSelector> validatorSelector() {
                    return Optional.empty();
                }
            },
            ValidatorSelector.class,
            null
        );
    }

    @Override
    public ValidationConverterHasOptionalValidatorSelector<ConverterContext> createConverter() {
        return ValidationConverterHasOptionalValidatorSelector.instance();
    }

    @Override
    public ConverterContext createContext() {
        return ConverterContexts.fake();
    }

    @Override
    public Class<ValidationConverterHasOptionalValidatorSelector<ConverterContext>> type() {
        return Cast.to(ValidationConverterHasOptionalValidatorSelector.class);
    }
}
