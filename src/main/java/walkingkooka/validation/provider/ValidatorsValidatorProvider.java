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
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.set.Sets;
import walkingkooka.collect.set.SortedSets;
import walkingkooka.net.UrlPath;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.Validator;
import walkingkooka.validation.ValidatorContext;
import walkingkooka.validation.Validators;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A {@link ValidatorProvider} that sources all {@link Validator} from {@link Validators}.
 */
final class ValidatorsValidatorProvider implements ValidatorProvider {

    /**
     * Singleton
     */
    final static ValidatorsValidatorProvider INSTANCE = new ValidatorsValidatorProvider();

    private ValidatorsValidatorProvider() {
        super();

        this.infos = ValidatorInfoSet.with(
            Sets.readOnly(
                ValidatorName.NAME_TO_FACTORY.keySet()
                    .stream()
                    .map(ValidatorsValidatorProvider::nameToValidatorInfo)
                    .collect(Collectors.toCollection(SortedSets::tree))
            )
        );
    }

    private static ValidatorInfo nameToValidatorInfo(final ValidatorName name) {
        return ValidatorInfo.with(
            ValidatorProviders.BASE_URL.appendPath(
                UrlPath.parse(
                    name.value()
                )
            ),
            name
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

        final Function<List<?>, Validator<?, ?>> factory = ValidatorName.NAME_TO_FACTORY.get(name);
        if (null == factory) {
            throw new IllegalArgumentException("Unknown validator " + name);
        }

        return Cast.to(
            factory.apply(
                Lists.immutable(values)
            )
        );
    }

    @Override
    public ValidatorInfoSet validatorInfos() {
        return this.infos;
    }

    private final ValidatorInfoSet infos;

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
