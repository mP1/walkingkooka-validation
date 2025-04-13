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

import walkingkooka.plugin.ProviderContext;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.Validator;
import walkingkooka.validation.ValidatorContext;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A {@link ValidatorProvider} that uses the given aliases definition and {@link ValidatorProvider} to present another view.
 */
final class AliasesValidatorProvider implements ValidatorProvider {

    static AliasesValidatorProvider with(final ValidatorAliasSet aliases,
                                         final ValidatorProvider provider) {
        return new AliasesValidatorProvider(
            Objects.requireNonNull(aliases, "aliases"),
            Objects.requireNonNull(provider, "provider")
        );
    }

    private AliasesValidatorProvider(final ValidatorAliasSet aliases,
                                     final ValidatorProvider provider) {
        this.aliases = aliases;
        this.provider = provider;

        this.infos = aliases.merge(provider.validatorInfos());
    }

    @Override
    public <R extends ValidationReference, C extends ValidatorContext<R>> Validator<R, C> validator(final ValidatorSelector selector,
                                                                                                    final ProviderContext context) {
        return this.provider.validator(
            this.aliases.selector(selector),
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

        Validator<R, C> validator;

        final ValidatorAliasSet aliases = this.aliases;
        final ValidatorProvider provider = this.provider;

        final Optional<ValidatorSelector> selector = aliases.aliasSelector(name);
        if (selector.isPresent()) {
            if (false == values.isEmpty()) {
                throw new IllegalArgumentException("Alias " + name + " should have no values");
            }
            // assumes that $provider caches selectors to validator
            validator = provider.validator(
                selector.get(),
                context
            );
        } else {
            validator = provider.validator(
                aliases.aliasOrName(name)
                    .orElseThrow(() -> new IllegalArgumentException("Unknown validator " + name)),
                values,
                context
            );
        }

        return validator;
    }

    private final ValidatorAliasSet aliases;

    private final ValidatorProvider provider;

    @Override
    public ValidatorInfoSet validatorInfos() {
        return this.infos;
    }

    private final ValidatorInfoSet infos;

    @Override
    public String toString() {
        return this.validatorInfos().toString();
    }
}
