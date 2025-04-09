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

import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.ConverterContextDelegator;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentContextDelegator;
import walkingkooka.tree.expression.ExpressionEvaluationContext;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Function;

final class BasicValidatorContext<T extends ValidationReference> implements ValidatorContext<T>,
    ConverterContextDelegator,
    EnvironmentContextDelegator {

    static <T extends ValidationReference> BasicValidatorContext<T> with(final T validationReference,
                                                                         final Function<T, ExpressionEvaluationContext> referenceToExpressionEvaluationContext,
                                                                         final ConverterContext converterContext,
                                                                         final EnvironmentContext environmentContext) {
        return new BasicValidatorContext<>(
            Objects.requireNonNull(validationReference, "validationReference"),
            Objects.requireNonNull(referenceToExpressionEvaluationContext, "referenceToExpressionEvaluationContext"),
            Objects.requireNonNull(converterContext, "converterContext"),
            Objects.requireNonNull(environmentContext, "environmentContext")
        );
    }

    private BasicValidatorContext(final T validationReference,
                                  final Function<T, ExpressionEvaluationContext> referenceToExpressionEvaluationContext,
                                  final ConverterContext converterContext,
                                  final EnvironmentContext environmentContext) {
        this.validationReference = validationReference;
        this.referenceToExpressionEvaluationContext = referenceToExpressionEvaluationContext;
        this.converterContext = converterContext;
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
                this.converterContext,
                this.environmentContext
            );
    }

    @Override
    public ExpressionEvaluationContext expressionEvaluationContext() {
        return this.referenceToExpressionEvaluationContext.apply(this.validationReference);
    }

    private final Function<T, ExpressionEvaluationContext> referenceToExpressionEvaluationContext;

    @Override
    public LocalDateTime now() {
        return this.converterContext.now();
    }

    // ConverterContextDelegator........................................................................................

    @Override
    public ConverterContext converterContext() {
        return this.converterContext;
    }

    // @VisibleForTesting
    final ConverterContext converterContext;

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
        return this.validationReference + " " + this.referenceToExpressionEvaluationContext + " " + this.converterContext + " " + this.environmentContext;
    }
}
