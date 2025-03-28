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

import walkingkooka.Cast;
import walkingkooka.plugin.ProviderCollection;
import walkingkooka.plugin.ProviderCollectionProviderGetter;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.Validator;
import walkingkooka.validation.ValidatorContext;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A {@link ValidatorProvider} view of a collection of {@link ValidatorProvider providers}.
 */
final class ValidatorProviderCollection implements ValidatorProvider {

    static <C extends ValidatorContext<R>, R extends ValidationReference> ValidatorProviderCollection with(final Set<ValidatorProvider> providers) {
        return new ValidatorProviderCollection(
            Objects.requireNonNull(providers, "providers")
        );
    }

    private ValidatorProviderCollection(final Set<ValidatorProvider> providers) {
        this.providers = ProviderCollection.with(
            new ProviderCollectionProviderGetter<>() {
                @Override
                public Validator<?, ?> get(final ValidatorProvider provider,
                                           final ValidatorName name,
                                           final List<?> values,
                                           final ProviderContext context) {
                    return Cast.to(
                        provider.validator(
                            name,
                            values,
                            context
                        )
                    );
                }

                @Override
                public Validator<?, ?> get(final ValidatorProvider provider,
                                           final ValidatorSelector selector,
                                           final ProviderContext context) {
                    throw new UnsupportedOperationException();
                }
            },
            ValidatorProvider::validatorInfos,
            Validator.class.getSimpleName(),
            providers
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
        return Cast.to(
            this.providers.get(
                name,
                values,
                context
            )
        );
    }

    @Override
    public ValidatorInfoSet validatorInfos() {
        return ValidatorInfoSet.with(
            this.providers.infos()
        );
    }

    private final ProviderCollection<ValidatorProvider, ValidatorName, ValidatorInfo, ValidatorSelector, Validator<?, ?>> providers;

    @Override
    public String toString() {
        return this.providers.toString();
    }
}
