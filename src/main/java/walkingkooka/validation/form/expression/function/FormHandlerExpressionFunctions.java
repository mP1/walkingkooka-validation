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

import walkingkooka.reflect.PublicStaticHelper;
import walkingkooka.tree.expression.function.ExpressionFunction;
import walkingkooka.validation.ValidationErrorList;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.form.expression.FormHandlerExpressionEvaluationContext;

import java.util.Set;

/**
 * A collection of {@link ExpressionFunction}.
 */
public final class FormHandlerExpressionFunctions implements PublicStaticHelper {

    /**
     * {@see FormHandlerExpressionFunctionRequiredFormFields}
     */
    public static <R extends ValidationReference, S, C extends FormHandlerExpressionEvaluationContext<R, S>> ExpressionFunction<ValidationErrorList<R>, C> requiredFormFields(final Set<R> fields) {
        return FormHandlerExpressionFunctionRequiredFormFields.with(fields);
    }

    /**
     * Stop creation
     */
    private FormHandlerExpressionFunctions() {
        throw new UnsupportedOperationException();
    }
}
