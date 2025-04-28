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

package walkingkooka.validation.function;

import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.ExpressionReference;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.form.HasForm;

import java.util.Optional;
import java.util.function.Function;

public interface ValidatorExpressionEvaluationContext<R extends ValidationReference, S> extends ExpressionEvaluationContext,
    HasForm<R> {

    @Override
    ValidatorExpressionEvaluationContext<R, S> enterScope(final Function<ExpressionReference, Optional<Optional<Object>>> function);
}
