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

import walkingkooka.plugin.FilteredProviderGuard;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.Validator;
import walkingkooka.validation.ValidatorContext;

import java.util.List;
import java.util.Objects;

/**
 * A {@link ValidatorProvider} that provides {@link Validator} from one provider but lists more {@link ValidatorInfo}.
 */
final class FilteredValidatorProvider implements ValidatorProvider {

    static FilteredValidatorProvider with(final ValidatorProvider provider,
                                          final ValidatorInfoSet infos) {
        return new FilteredValidatorProvider(
            Objects.requireNonNull(provider, "provider"),
            Objects.requireNonNull(infos, "infos")
        );
    }

    private FilteredValidatorProvider(final ValidatorProvider provider,
                                      final ValidatorInfoSet infos) {
        this.guard = FilteredProviderGuard.with(
            infos.names(),
            ValidatorPluginHelper.INSTANCE
        );
        this.provider = provider;
        this.infos = infos;
    }

    @Override
    public <R extends ValidationReference, C extends ValidatorContext<R>> Validator<R, C> validator(final ValidatorSelector selector,
                                                                                                    final ProviderContext context) {
        return this.provider.validator(
            selector,
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
            this.guard.name(name),
            values,
            context
        );
    }

    private final FilteredProviderGuard<ValidatorName, ValidatorSelector> guard;

    private final ValidatorProvider provider;

    @Override
    public ValidatorInfoSet validatorInfos() {
        return this.infos;
    }

    private final ValidatorInfoSet infos;

    @Override
    public String toString() {
        return this.provider.toString();
    }
}
