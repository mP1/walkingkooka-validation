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
import walkingkooka.collect.set.Sets;
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

public final class MergedMappedValidatorProviderTest implements ValidatorProviderTesting<MergedMappedValidatorProvider>,
    ToStringTesting<MergedMappedValidatorProvider> {

    private final static AbsoluteUrl RENAMED_URL = Url.parseAbsolute("https://example.com/renamed-validator-111");

    private final static ValidatorName RENAMED_RENAME_NAME = ValidatorName.with("renamed-rename-validator-111");

    private final static ValidatorName RENAMED_PROVIDER_NAME = ValidatorName.with("renamed-provider-only-validator-111");

    private final static Validator<TestValidationReference, TestValidatorContext> RENAME_VALIDATOR = Validators.fake();

    private final static AbsoluteUrl PROVIDER_ONLY_URL = Url.parseAbsolute("https://example.com/provider-only-validator-222");

    private final static ValidatorName PROVIDER_ONLY_NAME = ValidatorName.with("provider-only-validator-222");

    private final static Validator<TestValidationReference, TestValidatorContext> PROVIDER_ONLY_VALIDATOR = Validators.fake();

    private final static ProviderContext CONTEXT = ProviderContexts.fake();

    @Test
    public void testWithNullInfosFails() {
        assertThrows(
            NullPointerException.class,
            () -> MergedMappedValidatorProvider.with(
                null,
                ValidatorProviders.fake()
            )
        );
    }

    @Test
    public void testWithNullProviderFails() {
        assertThrows(
            NullPointerException.class,
            () -> MergedMappedValidatorProvider.with(
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
    public void testValidatorSelectorWithRename() {
        this.validatorAndCheck(
            ValidatorSelector.parse("" + RENAMED_RENAME_NAME),
            CONTEXT,
            RENAME_VALIDATOR
        );
    }

    @Test
    public void testValidatorSelectorWithProviderOnly() {
        this.validatorAndCheck(
            ValidatorSelector.parse("" + PROVIDER_ONLY_NAME),
            CONTEXT,
            PROVIDER_ONLY_VALIDATOR
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
    public void testValidatorNameWithRename() {
        this.validatorAndCheck(
            RENAMED_RENAME_NAME,
            Lists.empty(),
            CONTEXT,
            RENAME_VALIDATOR
        );
    }

    @Test
    public void testValidatorNameWithProviderOnly() {
        this.validatorAndCheck(
            PROVIDER_ONLY_NAME,
            Lists.empty(),
            CONTEXT,
            PROVIDER_ONLY_VALIDATOR
        );
    }

    @Test
    public void testInfos() {
        this.validatorInfosAndCheck(
            ValidatorInfo.with(
                RENAMED_URL,
                RENAMED_RENAME_NAME
            ),
            ValidatorInfo.with(
                PROVIDER_ONLY_URL,
                PROVIDER_ONLY_NAME
            )
        );
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createValidatorProvider(),
            "https://example.com/provider-only-validator-222 provider-only-validator-222,https://example.com/renamed-validator-111 renamed-rename-validator-111"
        );
    }

    @Override
    public MergedMappedValidatorProvider createValidatorProvider() {
        return MergedMappedValidatorProvider.with(
            ValidatorInfoSet.with(
                Sets.of(
                    ValidatorInfo.with(
                        RENAMED_URL,
                        RENAMED_RENAME_NAME
                    )
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

                    if (name.equals(RENAMED_PROVIDER_NAME)) {
                        return Cast.to(RENAME_VALIDATOR);
                    }
                    if (name.equals(PROVIDER_ONLY_NAME)) {
                        return Cast.to(PROVIDER_ONLY_VALIDATOR);
                    }
                    throw new IllegalArgumentException("Unknown Validator " + name);
                }

                @Override
                public ValidatorInfoSet validatorInfos() {
                    return ValidatorInfoSet.with(
                        Sets.of(
                            ValidatorInfo.with(
                                RENAMED_URL,
                                RENAMED_PROVIDER_NAME
                            ),
                            ValidatorInfo.with(
                                PROVIDER_ONLY_URL,
                                PROVIDER_ONLY_NAME
                            )
                        )
                    );
                }
            }
        );
    }

    // Class............................................................................................................

    @Override
    public Class<MergedMappedValidatorProvider> type() {
        return MergedMappedValidatorProvider.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
