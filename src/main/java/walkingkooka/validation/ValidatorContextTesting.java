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

package walkingkooka.validation;

import walkingkooka.convert.ConverterLikeTesting;
import walkingkooka.environment.EnvironmentContextTesting;
import walkingkooka.validation.provider.ValidatorSelector;

public interface ValidatorContextTesting extends ConverterLikeTesting,
    EnvironmentContextTesting {

    default <R extends ValidationReference> void validationReferenceAndCheck(final ValidatorContext<R> context,
                                                                             final R expected) {
        this.checkEquals(
            expected,
            context.validationReference(),
            context::toString);
    }

    // validator........................................................................................................

    default <R extends ValidationReference, C extends ValidatorContext<R>> void validatorAndCheck(final C context,
                                                                                                  final ValidatorSelector selector,
                                                                                                  final Validator<R, C> expected) {
        this.checkEquals(
            expected,
            context.validator(selector),
            selector::toString
        );
    }
}
