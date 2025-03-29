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

import walkingkooka.plugin.ProviderContext;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.Validator;
import walkingkooka.validation.ValidatorContext;

import java.util.List;

public interface ValidatorProviderDelegator extends ValidatorProvider {

    @Override
    default <R extends ValidationReference, C extends ValidatorContext<R>> Validator<R, C> validator(final ValidatorSelector selector,
                                                                                                     final ProviderContext context) {
        return this.validatorProvider()
            .validator(
                selector,
                context
            );
    }

    @Override
    default <R extends ValidationReference, C extends ValidatorContext<R>> Validator<R, C> validator(final ValidatorName name,
                                                                                                     final List<?> values,
                                                                                                     final ProviderContext context) {
        return this.validatorProvider()
            .validator(
                name,
                values,
                context
            );
    }

    @Override
    default ValidatorInfoSet validatorInfos() {
        return this.validatorProvider()
            .validatorInfos();
    }

    ValidatorProvider validatorProvider();
}
