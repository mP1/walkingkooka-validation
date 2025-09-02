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
import walkingkooka.validation.ValidationError;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.form.expression.FormHandlerExpressionEvaluationContext;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A {@link FormHandler} that delegates to the provided {@link FormHandlerContext} to prepare, validate and submit a form.
 */
final class BasicFormHandler<R extends ValidationReference, S, C extends FormHandlerContext<R, S>> implements FormHandler<R, S, C> {

    /**
     * Type safe getter
     */
    static <R extends ValidationReference, S, C extends FormHandlerContext<R, S>> BasicFormHandler<R, S, C> instance() {
        return new BasicFormHandler<>();
    }

    /**
     * Private ctor use factory.
     */
    private BasicFormHandler() {
        super();
    }

    // FormHandler......................................................................................................

    /**
     * Load and replace the value for each form. If the loaded value is missing the original {@link FormField#value()}
     * is not replaced.
     */
    @Override
    public Form<R> prepareForm(final Form<R> form,
                               final C context) {
        Objects.requireNonNull(form, "form");
        Objects.requireNonNull(context, "context");

        final List<FormField<R>> loadedFields = Lists.array();

        for (final FormField<R> field : form.fields()) {
            final Optional<Object> loadedValue = context.loadFormFieldValue(field.reference());

            loadedFields.add(
                loadedValue.isPresent() ?
                    field.setValue(loadedValue) :
                    field
            );
        }

        return form.setFields(loadedFields);
    }

    /**
     * Delegates validation of the form fields to {@link FormHandlerExpressionEvaluationContext#validateFormFields(List)}.
     */
    @Override
    public List<ValidationError<R>> validateForm(final Form<R> form,
                                                 final C context) {
        Objects.requireNonNull(form, "form");
        Objects.requireNonNull(context, "context");

        return context.validateFormFields(
            form.fields()
        );
    }

    /**
     * Saves the given {@link Form#fields()} using {@link FormHandlerExpressionEvaluationContext#saveFormFieldValues(List)}.
     */
    @Override
    public S submitForm(final Form<R> form,
                        final C context) {
        Objects.requireNonNull(form, "form");
        Objects.requireNonNull(context, "context");

        return context.saveFormFieldValues(
            form.fields()
        );
    }

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
