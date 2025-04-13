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

import walkingkooka.plugin.MergedProviderMapper;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.Validator;
import walkingkooka.validation.ValidatorContext;

import java.util.List;
import java.util.Objects;

/**
 * A {@link ValidatorProvider} that supports renaming {@link ValidatorName} before invoking a wrapped {@link ValidatorProvider}.
 */
final class MergedMappedValidatorProvider implements ValidatorProvider {

    static MergedMappedValidatorProvider with(final ValidatorInfoSet infos,
                                              final ValidatorProvider provider) {
        Objects.requireNonNull(infos, "infos");
        Objects.requireNonNull(provider, "provider");

        return new MergedMappedValidatorProvider(
            infos,
            provider
        );
    }

    private MergedMappedValidatorProvider(final ValidatorInfoSet infos,
                                          final ValidatorProvider provider) {
        this.provider = provider;
        this.mapper = MergedProviderMapper.with(
            infos,
            provider.validatorInfos(),
            ValidatorPluginHelper.INSTANCE
        );
    }

    @Override
    public <R extends ValidationReference, C extends ValidatorContext<R>> Validator<R, C> validator(final ValidatorSelector selector,
                                                                                                    final ProviderContext context) {
        Objects.requireNonNull(selector, "selector");

        return selector.evaluateValueText(
            this,
            context
        );
    }

    @Override
    public <R extends ValidationReference, C extends ValidatorContext<R>> Validator<R, C> validator(final ValidatorName name,
                                                                                                    final List<?> values,
                                                                                                    final ProviderContext context) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(values, "values");
        Objects.requireNonNull(context, "context");

        return this.provider.validator(
            this.mapper.name(name),
            values,
            context
        );
    }

    /**
     * The original wrapped {@link ValidatorProvider}.
     */
    private final ValidatorProvider provider;

    @Override
    public ValidatorInfoSet validatorInfos() {
        return this.mapper.infos();
    }

    private final MergedProviderMapper<ValidatorName, ValidatorInfo, ValidatorInfoSet, ValidatorSelector, ValidatorAlias, ValidatorAliasSet> mapper;

    @Override
    public String toString() {
        return this.mapper.toString();
    }
}
