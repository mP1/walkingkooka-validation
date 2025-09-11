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

import walkingkooka.net.AbsoluteUrl;
import walkingkooka.net.Url;
import walkingkooka.reflect.PublicStaticHelper;
import walkingkooka.validation.Validator;

import java.util.Set;

/**
 * A collection of ValidatorProvider(s).
 */
public final class ValidatorProviders implements PublicStaticHelper {

    /**
     * This is the base {@link AbsoluteUrl} for all {@link Validator} in this package. The name of each
     * validator will be appended to this base.
     */
    public final static AbsoluteUrl BASE_URL = Url.parseAbsolute(
        "https://github.com/mP1/walkingkooka-validation/" + Validator.class.getSimpleName()
    );

    /**
     * {@see AliasesValidatorProvider}
     */
    public static ValidatorProvider aliases(final ValidatorAliasSet aliases,
                                            final ValidatorProvider provider) {
        return AliasesValidatorProvider.with(
            aliases,
            provider
        );
    }

    /**
     * {@see ValidatorProviderCollection}
     */
    public static ValidatorProvider collection(final Set<ValidatorProvider> providers) {
        return ValidatorProviderCollection.with(providers);
    }

    /**
     * {@see ValidationValidatorProvider}
     */
    public static ValidatorProvider validators() {
        return ValidationValidatorProvider.INSTANCE;
    }

    /**
     * {@see EmptyValidatorProvider}
     */
    public static ValidatorProvider empty() {
        return EmptyValidatorProvider.INSTANCE;
    }

    /**
     * {@see FakeValidatorProvider}
     */
    public static ValidatorProvider fake() {
        return new FakeValidatorProvider();
    }

    /**
     * {@see FilteredValidatorProvider}
     */
    public static ValidatorProvider filtered(final ValidatorProvider provider,
                                             final ValidatorInfoSet infos) {
        return FilteredValidatorProvider.with(
            provider,
            infos
        );
    }

    /**
     * {@see FilteredMappedValidatorProvider}
     */
    public static ValidatorProvider filteredMapped(final ValidatorInfoSet infos,
                                                   final ValidatorProvider provider) {
        return FilteredMappedValidatorProvider.with(
            infos,
            provider
        );
    }

    /**
     * {@see MergedMappedValidatorProvider}
     */
    public static ValidatorProvider mergedMapped(final ValidatorInfoSet infos,
                                                 final ValidatorProvider provider) {
        return MergedMappedValidatorProvider.with(
            infos,
            provider
        );
    }

    /**
     * Stop creation
     */
    private ValidatorProviders() {
        throw new UnsupportedOperationException();
    }
}
