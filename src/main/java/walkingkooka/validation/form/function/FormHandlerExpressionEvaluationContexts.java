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

package walkingkooka.validation.form.function;

import walkingkooka.reflect.PublicStaticHelper;
import walkingkooka.tree.expression.function.ExpressionFunction;
import walkingkooka.validation.ValidationReference;

/**
 * A collection of {@link ExpressionFunction}.
 */
public final class FormHandlerExpressionEvaluationContexts implements PublicStaticHelper {

    /**
     * {@see FakeFormHandlerExpressionEvaluationContext}
     */
    public static <R extends ValidationReference, S> FormHandlerExpressionEvaluationContext<R, S> fake() {
        return new FakeFormHandlerExpressionEvaluationContext<>();
    }

    /**
     * Stop creation
     */
    private FormHandlerExpressionEvaluationContexts() {
        throw new UnsupportedOperationException();
    }
}
