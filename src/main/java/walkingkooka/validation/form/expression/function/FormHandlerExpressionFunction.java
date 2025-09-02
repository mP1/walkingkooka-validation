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
import walkingkooka.tree.expression.ExpressionFunctionName;
import walkingkooka.tree.expression.ExpressionPurityContext;
import walkingkooka.tree.expression.function.ExpressionFunction;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterKind;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterName;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.form.Form;
import walkingkooka.validation.form.FormField;
import walkingkooka.validation.form.expression.FormHandlerExpressionEvaluationContext;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

abstract class FormHandlerExpressionFunction<R extends ValidationReference, S, C extends FormHandlerExpressionEvaluationContext<R, S>, T> implements ExpressionFunction<T, C> {

    FormHandlerExpressionFunction(final String name) {
        this.name = Optional.of(
            ExpressionFunctionName.with(name)
        );
    }

    @Override
    public final Optional<ExpressionFunctionName> name() {
        return this.name;
    }

    private final Optional<ExpressionFunctionName> name;

    @Override
    public final boolean isPure(final ExpressionPurityContext context) {
        return true;
    }

    @Override
    public final T apply(final List<Object> parameters,
                         final C context) {
        Objects.requireNonNull(parameters, "parameters");
        Objects.requireNonNull(context, "context");

        this.checkParameterCount(parameters);

        return this.applyNonNullParameters(
            parameters,
            context
        );
    }

    abstract T applyNonNullParameters(final List<Object> parameters,
                                      final C context);

    /**
     * A parameter to holding form fields.
     */
    final ExpressionFunctionParameter<List<FormField<R>>> formFields = ExpressionFunctionParameterName.with("formFields")
        .required(Cast.<Class<List<FormField<R>>>>to(List.class))
        .setKinds(ExpressionFunctionParameterKind.CONVERT_EVALUATE);

    /**
     * A {@link Form} parameter.
     */
    final ExpressionFunctionParameter<Form<R>> form = ExpressionFunctionParameterName.with("form")
        .required(Cast.<Class<Form<R>>>to(Form.class))
        .setKinds(ExpressionFunctionParameterKind.CONVERT_EVALUATE);

    @Override
    public final String toString() {
        return this.name.get()
            .toString();
    }
}
