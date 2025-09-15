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

package walkingkooka.validation.form.expression.function;

import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.set.Sets;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.validation.ValidationError;
import walkingkooka.validation.ValidationErrorList;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.form.FormField;
import walkingkooka.validation.form.expression.FormHandlerExpressionEvaluationContext;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * A function which adds errors when a field is required if it has no value.
 */
final class FormHandlerExpressionFunctionRequiredFormFields<R extends ValidationReference, S, C extends FormHandlerExpressionEvaluationContext<R, S>> extends FormHandlerExpressionFunction<R, S, C, ValidationErrorList<R>> {

    static <R extends ValidationReference, S, C extends FormHandlerExpressionEvaluationContext<R, S>> FormHandlerExpressionFunctionRequiredFormFields<R, S, C> with(final Set<R> fields) {
        return new FormHandlerExpressionFunctionRequiredFormFields<>(
            Sets.immutable(
                Objects.requireNonNull(fields, "fields")
            )
        );
    }

    private FormHandlerExpressionFunctionRequiredFormFields(final Set<R> fields) {
        super("requiredFormFields");

        if (fields.isEmpty()) {
            throw new IllegalArgumentException("Empty required form fields");
        }

        this.fields = fields;
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return Lists.of(this.form);
    }

    @Override
    public Class<ValidationErrorList<R>> returnType() {
        return Cast.to(ValidationErrorList.class);
    }

    @Override
    ValidationErrorList<R> applyNonNullParameters(final List<Object> parameters,
                                                  final C context) {
        final List<FormField<R>> formFields = this.formFields.getOrFail(parameters, 0);

        final List<ValidationError<R>> errors = Lists.array();
        final Set<R> requiredFields = this.fields;

        for (final FormField<R> formField : formFields) {
            final R reference = formField.reference();

            if (requiredFields.contains(reference)) {
                final Optional<?> value = formField.value();
                if (false == value.isPresent()) {
                    // field is missing value, add an error
                    errors.add(
                        ValidationError.with(
                            reference,
                            "Required"
                        )
                    );
                }
            }
        }

        return ValidationErrorList.<R>empty()
            .setElements(errors);
    }

    private final Set<R> fields;
}
