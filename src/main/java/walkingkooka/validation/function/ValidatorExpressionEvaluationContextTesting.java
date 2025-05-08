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

import walkingkooka.tree.expression.ExpressionEvaluationContextTesting;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.form.HasFormTesting;

import java.util.Optional;

public interface ValidatorExpressionEvaluationContextTesting<R extends ValidationReference, C extends ValidatorExpressionEvaluationContext<R>> extends ExpressionEvaluationContextTesting<C>,
    HasFormTesting<R> {

    default void validationValueAndCheck(final ValidatorExpressionEvaluationContext<R> context) {
        this.validationValueAndCheck(
            context,
            Optional.empty()
        );
    }

    default void validationValueAndCheck(final ValidatorExpressionEvaluationContext<R> context,
                                         final Object expected) {
        this.validationValueAndCheck(
            context,
            Optional.of(expected)
        );
    }

    default void validationValueAndCheck(final ValidatorExpressionEvaluationContext<R> context,
                                         final Optional<Object> expected) {
        this.checkEquals(
            expected,
            context.validationValue()
        );
    }

    // ExpressionEvaluationContextTesting...............................................................................

    @Override
    default C createCanConvert() {
        return this.createContext();
    }

    @Override
    default String typeNameSuffix() {
        return ValidatorExpressionEvaluationContext.class.getSimpleName();
    }
}
