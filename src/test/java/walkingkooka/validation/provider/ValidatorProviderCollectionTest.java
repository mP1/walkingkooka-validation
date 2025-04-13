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

package walkingkooka.validation.provider;

import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.set.Sets;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.plugin.ProviderContexts;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.validation.Validators;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ValidatorProviderCollectionTest implements ValidatorProviderTesting<ValidatorProviderCollection> {

    private final static ProviderContext CONTEXT = ProviderContexts.fake();

    @Test
    public void testWithNullProvidersFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValidatorProviderCollection.with(null)
        );
    }

    @Test
    public void testValidatorName() {
        final ValidatorProvider provider = ValidatorProviders.validators();

        this.validatorAndCheck(
            ValidatorProviderCollection.with(Sets.of(provider)),
            ValidatorName.NON_NULL,
            Lists.empty(),
            CONTEXT,
            Validators.nonNull()
        );
    }

    @Test
    public void testValidatorSelector() {
        final ValidatorProvider provider = ValidatorProviders.validators();

        this.validatorAndCheck(
            ValidatorProviderCollection.with(Sets.of(provider)),
            ValidatorSelector.parse(
                "non-null"
            ),
            CONTEXT,
            Validators.nonNull()
        );
    }

    @Test
    public void testInfos() {
        final ValidatorProvider provider = ValidatorProviders.validators();

        this.validatorInfosAndCheck(
            ValidatorProviderCollection.with(Sets.of(provider)),
            provider.validatorInfos()
        );
    }

    @Override
    public ValidatorProviderCollection createValidatorProvider() {
        return ValidatorProviderCollection.with(
            Sets.of(
                ValidatorProviders.validators()
            )
        );
    }

    // ClassTesting.....................................................................................................

    @Override
    public Class<ValidatorProviderCollection> type() {
        return ValidatorProviderCollection.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
