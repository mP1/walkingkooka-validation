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

package walkingkooka.validation.form.function.provider;

import walkingkooka.collect.set.Sets;
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.net.Url;
import walkingkooka.reflect.PublicStaticHelper;
import walkingkooka.text.CaseSensitivity;
import walkingkooka.tree.expression.function.ExpressionFunction;
import walkingkooka.tree.expression.function.provider.ExpressionFunctionProvider;
import walkingkooka.tree.expression.function.provider.ExpressionFunctionProviders;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.form.FormHandler;
import walkingkooka.validation.form.function.FormHandlerExpressionEvaluationContext;

/**
 * A collection of ExpressionFunction(s) that execute with a {@link walkingkooka.validation.form.function.FormHandlerExpressionEvaluationContext}.
 */
public final class FormHandlerExpressionFunctionProviders implements PublicStaticHelper {

    /**
     * This is the base {@link AbsoluteUrl} for all {@link FormHandler} in this package. The name of each
     * form handler will be appended to this base.
     */
    public final static AbsoluteUrl BASE_URL = Url.parseAbsolute(
        "https://github.com/mP1/walkingkooka-validation/" + FormHandler.class.getSimpleName() + "/" + ExpressionFunction.class.getSimpleName()
    );

    /**
     * An {@link ExpressionFunctionProvider} with all the functions in this project.
     */
    public static <R extends ValidationReference, S, C extends FormHandlerExpressionEvaluationContext<R, S>> ExpressionFunctionProvider<C> expressionFunctionProvider(final CaseSensitivity nameCaseSensitivity) {
        return ExpressionFunctionProviders.basic(
            BASE_URL,
            nameCaseSensitivity,
            Sets.empty()
        );
    }

    /**
     * Stop creation
     */
    private FormHandlerExpressionFunctionProviders() {
        throw new UnsupportedOperationException();
    }
}
