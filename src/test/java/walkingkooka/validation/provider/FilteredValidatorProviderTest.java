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
import walkingkooka.ToStringTesting;
import walkingkooka.collect.list.Lists;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.plugin.ProviderContexts;
import walkingkooka.reflect.JavaVisibility;

import java.util.List;

public final class FilteredValidatorProviderTest implements ValidatorProviderTesting<FilteredValidatorProvider>,
    ToStringTesting<FilteredValidatorProvider> {

    private final static ProviderContext CONTEXT = ProviderContexts.fake();

    @Test
    public void testValidatorName() {
        final ValidatorName name = ValidatorName.NON_NULL;
        final List<?> values = Lists.empty();

        this.validatorAndCheck(
            name,
            values,
            CONTEXT,
            ValidatorProviders.validators()
                .validator(
                    name,
                    values,
                    CONTEXT
                )
        );
    }

    @Test
    public void testValidatorInfos() {
        this.validatorInfosAndCheck(
            ValidatorInfoSet.EMPTY.concat(
                ValidatorInfo.parse("https://github.com/mP1/walkingkooka-validation-provider/validator/non-null non-null")
            )
        );
    }

    @Override
    public FilteredValidatorProvider createValidatorProvider() {
        return FilteredValidatorProvider.with(
            ValidatorProviders.validators(),
            ValidatorInfoSet.EMPTY.concat(
                ValidatorInfo.parse("https://github.com/mP1/walkingkooka-validation-provider/validator/non-null non-null")
            )
        );
    }

    // ToString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createValidatorProvider(),
            ValidatorProviders.validators()
                .toString()
        );
    }

    // class............................................................................................................

    @Override
    public Class<FilteredValidatorProvider> type() {
        return FilteredValidatorProvider.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
