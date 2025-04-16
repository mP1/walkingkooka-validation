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

package walkingkooka.validation;

import walkingkooka.convert.CanConvert;
import walkingkooka.convert.CanConvertDelegator;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentContextDelegator;
import walkingkooka.tree.expression.ExpressionEvaluationContext;

import java.util.Objects;
import java.util.function.BiFunction;

final class BasicValidatorContext<T extends ValidationReference> implements ValidatorContext<T>,
    CanConvertDelegator,
    EnvironmentContextDelegator {

    static <T extends ValidationReference> BasicValidatorContext<T> with(final T validationReference,
                                                                         final BiFunction<Object, T, ExpressionEvaluationContext> referenceToExpressionEvaluationContext,
                                                                         final CanConvert canConvert,
                                                                         final EnvironmentContext environmentContext) {
        return new BasicValidatorContext<>(
            Objects.requireNonNull(validationReference, "validationReference"),
            Objects.requireNonNull(referenceToExpressionEvaluationContext, "referenceToExpressionEvaluationContext"),
            Objects.requireNonNull(canConvert, "canConvert"),
            Objects.requireNonNull(environmentContext, "environmentContext")
        );
    }

    private BasicValidatorContext(final T validationReference,
                                  final BiFunction<Object, T, ExpressionEvaluationContext> referenceToExpressionEvaluationContext,
                                  final CanConvert canConvert,
                                  final EnvironmentContext environmentContext) {
        this.validationReference = validationReference;
        this.referenceToExpressionEvaluationContext = referenceToExpressionEvaluationContext;
        this.canConvert = canConvert;
        this.environmentContext = environmentContext;
    }

    @Override
    public T validationReference() {
        return this.validationReference;
    }

    private final T validationReference;

    @Override
    public ValidatorContext<T> setValidationReference(final T validationReference) {
        return this.validationReference.equals(validationReference) ?
            this :
            new BasicValidatorContext<>(
                Objects.requireNonNull(validationReference, "validationReference"),
                this.referenceToExpressionEvaluationContext,
                this.canConvert,
                this.environmentContext
            );
    }

    @Override
    public ExpressionEvaluationContext expressionEvaluationContext(final Object value) {
        return this.referenceToExpressionEvaluationContext.apply(
            value,
            this.validationReference
        );
    }

    private final BiFunction<Object, T, ExpressionEvaluationContext> referenceToExpressionEvaluationContext;

    // CanConvertDelegator..............................................................................................

    @Override
    public CanConvert canConvert() {
        return this.canConvert;
    }

    // @VisibleForTesting
    final CanConvert canConvert;

    // EnvironmentContextDelegator......................................................................................

    @Override
    public EnvironmentContext environmentContext() {
        return this.environmentContext;
    }

    // @VisibleForTesting
    final EnvironmentContext environmentContext;

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.validationReference + " " + this.referenceToExpressionEvaluationContext + " " + this.canConvert + " " + this.environmentContext;
    }
}
