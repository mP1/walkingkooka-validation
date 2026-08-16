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

package walkingkooka.validation.form;

import walkingkooka.collect.list.Lists;
import walkingkooka.text.printer.TreePrintableTesting;
import walkingkooka.validation.ValidationError;
import walkingkooka.validation.ValidationReference;

import java.util.List;

public interface FormHandlerTesting extends TreePrintableTesting {

    default <H extends FormHandler<R, S, C>, R extends ValidationReference, S, C extends FormHandlerContext<R, S>> void prepareFormAndCheck(final H handler,
                                                                                                                                            final Form<R> form,
                                                                                                                                            final C context,
                                                                                                                                            final Form<R> expected) {
        this.checkEquals(
            expected,
            handler.prepareForm(
                form,
                context
            )
        );
    }

    // validateForm.....................................................................................................

    default <H extends FormHandler<R, S, C>, R extends ValidationReference, S, C extends FormHandlerContext<R, S>> void validateFormAndCheck(final H handler,
                                                                                                                                             final Form<R> form,
                                                                                                                                             final C context,
                                                                                                                                             final ValidationError<R>... expected) {
        this.validateFormAndCheck(
            handler,
            form,
            context,
            Lists.of(expected)
        );
    }

    default <H extends FormHandler<R, S, C>, R extends ValidationReference, S, C extends FormHandlerContext<R, S>> void validateFormAndCheck(final H handler,
                                                                                                                                             final Form<R> form,
                                                                                                                                             final C context,
                                                                                                                                             final List<ValidationError<R>> expected) {
        this.checkEquals(
            expected,
            handler.validateForm(
                form,
                context
            )
        );
    }

    // submitForm.......................................................................................................

    default <H extends FormHandler<R, S, C>, R extends ValidationReference, S, C extends FormHandlerContext<R, S>> void submitFormAndCheck(final H handler,
                                                                                                                                           final Form<R> form,
                                                                                                                                           final C context,
                                                                                                                                           final S expected) {
        this.checkEquals(
            expected,
            handler.submitForm(
                form,
                context
            )
        );
    }
}
