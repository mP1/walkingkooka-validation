/*
 * Copyright 2024 Miroslav Pokorny (github.com/mP1)
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
import walkingkooka.Cast;
import walkingkooka.ToStringTesting;
import walkingkooka.collect.list.Lists;
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.net.Url;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.plugin.ProviderContexts;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.TestValidatorContext;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.Validator;
import walkingkooka.validation.ValidatorContext;
import walkingkooka.validation.Validators;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class FilteredMappedValidatorProviderTest implements ValidatorProviderTesting<FilteredMappedValidatorProvider>,
    ToStringTesting<FilteredMappedValidatorProvider> {

    private final static AbsoluteUrl URL = Url.parseAbsolute("https://example.com/validator123");

    private final static ValidatorName NAME = ValidatorName.with("different-validator-name-123");

    private final static ValidatorName ORIGINAL_NAME = ValidatorName.with("original-validator-123");

    private final static Validator<TestValidationReference, TestValidatorContext> VALIDATOR = Validators.fake();

    private final static ProviderContext CONTEXT = ProviderContexts.fake();

    @Test
    public void testWithNullViewFails() {
        assertThrows(
            NullPointerException.class,
            () -> FilteredMappedValidatorProvider.with(
                null,
                ValidatorProviders.fake()
            )
        );
    }

    @Test
    public void testWithNullProviderFails() {
        assertThrows(
            NullPointerException.class,
            () -> FilteredMappedValidatorProvider.with(
                ValidatorInfoSet.EMPTY,
                null
            )
        );
    }

    @Test
    public void testValidatorSelectorWithUnknownFails() {
        this.validatorFails(
            ValidatorSelector.parse("unknown"),
            CONTEXT
        );
    }

    @Test
    public void testValidatorSelector() {
        this.validatorAndCheck(
            ValidatorSelector.parse("" + NAME),
            CONTEXT,
            VALIDATOR
        );
    }

    @Test
    public void testValidatorNameWithUnknownFails() {
        this.validatorFails(
            ValidatorName.with("unknown"),
            Lists.empty(),
            CONTEXT
        );
    }

    @Test
    public void testValidatorName() {
        this.validatorAndCheck(
            NAME,
            Lists.empty(),
            CONTEXT,
            VALIDATOR
        );
    }

    @Test
    public void testInfos() {
        this.validatorInfosAndCheck(
            ValidatorInfo.with(
                URL,
                NAME
            )
        );
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createValidatorProvider(),
            "https://example.com/validator123 different-validator-name-123"
        );
    }

    @Override
    public FilteredMappedValidatorProvider createValidatorProvider() {
        return FilteredMappedValidatorProvider.with(
            ValidatorInfoSet.EMPTY.concat(
                ValidatorInfo.with(
                    URL,
                    NAME
                )
            ),
            new FakeValidatorProvider() {

                @Override
                public <R extends ValidationReference, C extends ValidatorContext<R>> Validator<R, C> validator(final ValidatorName name,
                                                                                                                final List<?> values,
                                                                                                                final ProviderContext context) {
                    Objects.requireNonNull(name, "name");
                    Objects.requireNonNull(values, "values");
                    Objects.requireNonNull(context, "context");

                    if (false == name.equals(ORIGINAL_NAME)) {
                        throw new IllegalArgumentException("Unknown Validator " + name);
                    }
                    return Cast.to(VALIDATOR);
                }

                @Override
                public ValidatorInfoSet validatorInfos() {
                    return ValidatorInfoSet.EMPTY.concat(
                        ValidatorInfo.with(
                            URL,
                            ORIGINAL_NAME
                        )
                    );
                }
            }
        );
    }

    // Class............................................................................................................

    @Override
    public Class<FilteredMappedValidatorProvider> type() {
        return FilteredMappedValidatorProvider.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
