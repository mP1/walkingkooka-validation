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

import walkingkooka.tree.expression.ExpressionReference;
import walkingkooka.tree.expression.FakeExpressionEvaluationContext;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.form.Form;

import java.util.Optional;
import java.util.function.Function;

public class FakeValidatorExpressionEvaluationContext<R extends ValidationReference> extends FakeExpressionEvaluationContext implements ValidatorExpressionEvaluationContext<R> {

    public FakeValidatorExpressionEvaluationContext() {
        super();
    }

    @Override
    public Optional<Object> validationValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ValidatorExpressionEvaluationContext<R> enterScope(final Function<ExpressionReference, Optional<Optional<Object>>> scoped) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Form<R> form() {
        throw new UnsupportedOperationException();
    }
}
