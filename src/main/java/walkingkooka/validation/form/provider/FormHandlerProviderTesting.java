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

import walkingkooka.collect.set.Sets;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.text.printer.TreePrintableTesting;
import walkingkooka.validation.form.FormHandler;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface FormHandlerProviderTesting extends TreePrintableTesting {

    default void formHandlerAndCheck(final FormHandlerProvider provider,
                                     final String selector,
                                     final ProviderContext context,
                                     final FormHandler<?, ?, ?> expected) {
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
                                     final FormHandler<?, ?, ?> expected) {
        this.checkEquals(
            expected,
            provider.formHandler(
                selector,
                context
            )
        );
    }

    // formHandler(FormHandlerName, List<?>)................................................................................

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

    default void formHandlerAndCheck(final FormHandlerProvider provider,
                                     final FormHandlerName name,
                                     final List<?> values,
                                     final ProviderContext context,
                                     final FormHandler<?, ?, ?> expected) {
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

    default void formHandlerInfosAndCheck(final FormHandlerProvider provider,
                                          final FormHandlerInfoSet expected) {
        this.checkEquals(
            expected,
            provider.formHandlerInfos(),
            provider::toString
        );
    }
}
