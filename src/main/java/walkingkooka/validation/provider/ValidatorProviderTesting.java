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
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.set.Sets;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.plugin.ProviderContexts;
import walkingkooka.plugin.ProviderTesting;
import walkingkooka.validation.Validator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface ValidatorProviderTesting<T extends ValidatorProvider> extends ProviderTesting<T> {

    // validator(ValidatorSelector).....................................................................................

    @Test
    default void testValidatorSelectorWithNullSelectorFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createValidatorProvider()
                .validator(
                    null,
                    ProviderContexts.fake()
                )
        );
    }

    @Test
    default void testValidatorSelectorWithNullContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createValidatorProvider()
                .validator(
                    ValidatorSelector.parse("validator"),
                    null
                )
        );
    }

    default void validatorFails(final String selector,
                                final ProviderContext context) {
        this.validatorFails(
            ValidatorSelector.parse(selector),
            context
        );
    }

    default void validatorFails(final ValidatorSelector selector,
                                final ProviderContext context) {
        this.validatorFails(
            this.createValidatorProvider(),
            selector,
            context
        );
    }

    default void validatorFails(final ValidatorProvider provider,
                                final ValidatorSelector selector,
                                final ProviderContext context) {
        assertThrows(
            IllegalArgumentException.class,
            () -> selector.evaluateValueText(
                provider,
                context
            )
        );
    }

    default void validatorAndCheck(final String selector,
                                   final ProviderContext context,
                                   final Validator<?, ?> expected) {
        this.validatorAndCheck(
            ValidatorSelector.parse(selector),
            context,
            expected
        );
    }

    default void validatorAndCheck(final ValidatorSelector selector,
                                   final ProviderContext context,
                                   final Validator<?, ?> expected) {
        this.validatorAndCheck(
            this.createValidatorProvider(),
            selector,
            context,
            expected
        );
    }

    default void validatorAndCheck(final ValidatorProvider provider,
                                   final String selector,
                                   final ProviderContext context,
                                   final Validator<?, ?> expected) {
        this.validatorAndCheck(
            provider,
            ValidatorSelector.parse(selector),
            context,
            expected
        );
    }

    default void validatorAndCheck(final ValidatorProvider provider,
                                   final ValidatorSelector selector,
                                   final ProviderContext context,
                                   final Validator<?, ?> expected) {
        this.checkEquals(
            expected,
            provider.validator(
                selector,
                context
            )
        );
    }

    // validator(ValidatorName, List<?>)................................................................................

    @Test
    default void testValidatorNameWithNullNameFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createValidatorProvider()
                .validator(
                    null,
                    Lists.empty(),
                    ProviderContexts.fake()
                )
        );
    }

    @Test
    default void testValidatorNameWithNullValueFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createValidatorProvider()
                .validator(
                    ValidatorName.COLLECTION,
                    null,
                    ProviderContexts.fake()
                )
        );
    }

    @Test
    default void testValidatorNameWithNullContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createValidatorProvider()
                .validator(
                    ValidatorName.COLLECTION,
                    Lists.empty(),
                    null
                )
        );
    }

    default void validatorFails(final ValidatorName name,
                                final List<?> values,
                                final ProviderContext context) {
        this.validatorFails(
            this.createValidatorProvider(),
            name,
            values,
            context
        );
    }

    default void validatorFails(final ValidatorProvider provider,
                                final ValidatorName name,
                                final List<?> values,
                                final ProviderContext context) {
        assertThrows(
            IllegalArgumentException.class,
            () -> provider.validator(
                name,
                values,
                context
            )
        );
    }

    default void validatorAndCheck(final ValidatorName name,
                                   final List<?> values,
                                   final ProviderContext context,
                                   final Validator<?, ?> expected) {
        this.validatorAndCheck(
            this.createValidatorProvider(),
            name,
            values,
            context,
            expected
        );
    }

    default void validatorAndCheck(final ValidatorProvider provider,
                                   final ValidatorName name,
                                   final List<?> values,
                                   final ProviderContext context,
                                   final Validator<?, ?> expected) {
        this.checkEquals(
            expected,
            provider.validator(
                name,
                values,
                context
            ),
            () -> provider + " " + name + " " + values
        );
    }

    // validatorInfos...................................................................................................

    default void validatorInfosAndCheck(final ValidatorInfo... expected) {
        this.validatorInfosAndCheck(
            this.createValidatorProvider(),
            expected
        );
    }

    default void validatorInfosAndCheck(final ValidatorProvider provider,
                                        final ValidatorInfo... expected) {
        this.validatorInfosAndCheck(
            provider,
            ValidatorInfoSet.with(
                Sets.of(
                    expected
                )
            )
        );
    }

    default void validatorInfosAndCheck(final ValidatorInfoSet expected) {
        this.validatorInfosAndCheck(
            this.createValidatorProvider(),
            expected
        );
    }

    default void validatorInfosAndCheck(final ValidatorProvider provider,
                                        final ValidatorInfoSet expected) {
        this.checkEquals(
            expected,
            provider.validatorInfos(),
            provider::toString
        );
    }

    T createValidatorProvider();
}
