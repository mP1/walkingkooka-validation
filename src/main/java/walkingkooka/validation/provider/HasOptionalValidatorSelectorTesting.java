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

import walkingkooka.text.printer.TreePrintableTesting;

import java.util.Objects;
import java.util.Optional;

public interface HasOptionalValidatorSelectorTesting extends TreePrintableTesting {

    ValidatorSelector VALIDATOR_SELECTOR = ValidatorSelector.parse(
        ValidatorName.ABSOLUTE_URL.toString()
    );

    ValidatorSelector DIFFERENT_VALIDATOR_SELECTOR = ValidatorSelector.parse(
        ValidatorName.EMAIL_ADDRESS.toString()
    );

    Optional<ValidatorSelector> OPTIONAL_VALIDATOR_SELECTOR = Optional.of(VALIDATOR_SELECTOR);

    Optional<ValidatorSelector> OPTIONAL_DIFFERENT_VALIDATOR_SELECTOR = Optional.of(DIFFERENT_VALIDATOR_SELECTOR);

    default void validatorSelectorAndCheck(final HasOptionalValidatorSelector has) {
        this.validatorSelectorAndCheck(
            has,
            Optional.empty()
        );
    }

    default void validatorSelectorAndCheck(final HasOptionalValidatorSelector has,
                                           final ValidatorSelector expected) {
        this.validatorSelectorAndCheck(
            has,
            Optional.of(expected)
        );
    }

    default void validatorSelectorAndCheck(final HasOptionalValidatorSelector has,
                                           final Optional<ValidatorSelector> expected) {
        final Optional<ValidatorSelector> selector = has.validatorSelector();
        Objects.requireNonNull(has);
        this.checkEquals(
            expected,
            selector,
            has::toString
        );
    }
}
