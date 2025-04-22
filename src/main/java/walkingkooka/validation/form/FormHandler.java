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

import walkingkooka.validation.ValidationError;
import walkingkooka.validation.ValidationReference;

import java.util.List;

/**
 * A form handler is responsible for {@link Form} lifecycles.
 */
public interface FormHandler<R extends ValidationReference, S, C extends FormHandlerContext<R, S>> {

    /**
     * Prepares a {@link Form} before it is displayed for data entry.
     */
    Form<R> prepareForm(final Form<R> form,
                        final C context);

    /**
     * Validates the given form, returning an aggregation of {@link ValidationError} but does not submit the form.
     * This is useful so the UI can continuously validate one or more fields and update to show any error messages.
     */
    List<ValidationError<R>> validateForm(final Form<R> form,
                                          final C context);

    /**
     * Submits the given form so that it is saved.
     */
    S submitForm(final Form<R> form,
                 final C context);
}
