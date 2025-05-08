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

import walkingkooka.tree.expression.ExpressionFunctionName;
import walkingkooka.tree.expression.function.ExpressionFunction;
import walkingkooka.validation.ValidationReference;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

abstract class ValidationExpressionFunction<T, R extends ValidationReference, C extends ValidatorExpressionEvaluationContext<R>> implements ExpressionFunction<T, C> {

    ValidationExpressionFunction(final String name) {
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

    // Object...........................................................................................................

    @Override
    public final String toString() {
        return this.name.get()
            .toString();
    }
}