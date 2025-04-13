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

import org.junit.jupiter.api.Test;
import walkingkooka.reflect.ClassTesting;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.form.provider.FormName;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface FormHandlerTesting<H extends FormHandler<R, C>, R extends ValidationReference, C extends FormHandlerContext<R>> extends ClassTesting<H> {

    @Test
    default void testPrepareFormWithNullFormFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createFormHandler()
                .prepareForm(
                    null,
                    this.createContext()
                )
        );
    }

    @Test
    default void testPrepareFormWithNullContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createFormHandler()
                .prepareForm(
                    Form.with(
                        FormName.with("Form123")
                    ),
                    null
                )
        );
    }

    default void prepareFormAndCheck(final H handler,
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

    H createFormHandler();

    C createContext();
}
