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

package walkingkooka.validation.form.provider;

import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.set.Sets;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.plugin.ProviderContexts;
import walkingkooka.plugin.ProviderTesting;
import walkingkooka.validation.form.FormHandler;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface FormHandlerProviderTesting<T extends FormHandlerProvider> extends ProviderTesting<T> {

    // formHandler(FormHandlerSelector).....................................................................................

    @Test
    default void testFormHandlerSelectorWithNullSelectorFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createFormHandlerProvider()
                .formHandler(
                    null,
                    ProviderContexts.fake()
                )
        );
    }

    @Test
    default void testFormHandlerSelectorWithNullContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createFormHandlerProvider()
                .formHandler(
                    FormHandlerSelector.parse("formHandler"),
                    null
                )
        );
    }

    default void formHandlerFails(final String selector,
                                  final ProviderContext context) {
        this.formHandlerFails(
            FormHandlerSelector.parse(selector),
            context
        );
    }

    default void formHandlerFails(final FormHandlerSelector selector,
                                  final ProviderContext context) {
        this.formHandlerFails(
            this.createFormHandlerProvider(),
            selector,
            context
        );
    }

    default void formHandlerFails(final FormHandlerProvider provider,
                                  final FormHandlerSelector selector,
                                  final ProviderContext context) {
        assertThrows(
            IllegalArgumentException.class,
            () -> selector.evaluateValueText(
                provider,
                context
            )
        );
    }

    default void formHandlerAndCheck(final String selector,
                                     final ProviderContext context,
                                     final FormHandler<?, ?> expected) {
        this.formHandlerAndCheck(
            FormHandlerSelector.parse(selector),
            context,
            expected
        );
    }

    default void formHandlerAndCheck(final FormHandlerSelector selector,
                                     final ProviderContext context,
                                     final FormHandler<?, ?> expected) {
        this.formHandlerAndCheck(
            this.createFormHandlerProvider(),
            selector,
            context,
            expected
        );
    }

    default void formHandlerAndCheck(final FormHandlerProvider provider,
                                     final String selector,
                                     final ProviderContext context,
                                     final FormHandler<?, ?> expected) {
        this.formHandlerAndCheck(
            provider,
            FormHandlerSelector.parse(selector),
            context,
            expected
        );
    }

    default void formHandlerAndCheck(final FormHandlerProvider provider,
                                     final FormHandlerSelector selector,
                                     final ProviderContext context,
                                     final FormHandler<?, ?> expected) {
        this.checkEquals(
            expected,
            provider.formHandler(
                selector,
                context
            )
        );
    }

    // formHandler(FormHandlerName, List<?>)................................................................................

    @Test
    default void testFormHandlerNameWithNullNameFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createFormHandlerProvider()
                .formHandler(
                    null,
                    Lists.empty(),
                    ProviderContexts.fake()
                )
        );
    }

    @Test
    default void testFormHandlerNameWithNullValueFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createFormHandlerProvider()
                .formHandler(
                    FormHandlerName.with("Hello"),
                    null,
                    ProviderContexts.fake()
                )
        );
    }

    @Test
    default void testFormHandlerNameWithNullContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createFormHandlerProvider()
                .formHandler(
                    FormHandlerName.with("Hello"),
                    Lists.empty(),
                    null
                )
        );
    }

    default void formHandlerFails(final FormHandlerName name,
                                  final List<?> values,
                                  final ProviderContext context) {
        this.formHandlerFails(
            this.createFormHandlerProvider(),
            name,
            values,
            context
        );
    }

    default void formHandlerFails(final FormHandlerProvider provider,
                                  final FormHandlerName name,
                                  final List<?> values,
                                  final ProviderContext context) {
        assertThrows(
            IllegalArgumentException.class,
            () -> provider.formHandler(
                name,
                values,
                context
            )
        );
    }

    default void formHandlerAndCheck(final FormHandlerName name,
                                     final List<?> values,
                                     final ProviderContext context,
                                     final FormHandler<?, ?> expected) {
        this.formHandlerAndCheck(
            this.createFormHandlerProvider(),
            name,
            values,
            context,
            expected
        );
    }

    default void formHandlerAndCheck(final FormHandlerProvider provider,
                                     final FormHandlerName name,
                                     final List<?> values,
                                     final ProviderContext context,
                                     final FormHandler<?, ?> expected) {
        this.checkEquals(
            expected,
            provider.formHandler(
                name,
                values,
                context
            ),
            () -> provider + " " + name + " " + values
        );
    }

    // formHandlerInfos...................................................................................................

    default void formHandlerInfosAndCheck(final FormHandlerInfo... expected) {
        this.formHandlerInfosAndCheck(
            this.createFormHandlerProvider(),
            expected
        );
    }

    default void formHandlerInfosAndCheck(final FormHandlerProvider provider,
                                          final FormHandlerInfo... expected) {
        this.formHandlerInfosAndCheck(
            provider,
            FormHandlerInfoSet.with(
                Sets.of(
                    expected
                )
            )
        );
    }

    default void formHandlerInfosAndCheck(final FormHandlerInfoSet expected) {
        this.formHandlerInfosAndCheck(
            this.createFormHandlerProvider(),
            expected
        );
    }

    default void formHandlerInfosAndCheck(final FormHandlerProvider provider,
                                          final FormHandlerInfoSet expected) {
        this.checkEquals(
            expected,
            provider.formHandlerInfos(),
            provider::toString
        );
    }

    T createFormHandlerProvider();
}
