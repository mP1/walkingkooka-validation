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
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.reflect.PublicStaticHelper;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.validation.provider.ValidatorSelector;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A collection of factory methods to create {@link ValidatorContext}
 */
public final class ValidatorContexts implements PublicStaticHelper {

    // trigger registering of json marshall/unmarshallers
    static {
        ValidationChoiceList.EMPTY.toString();
        ValidationErrorList.empty().toString();
        ValueType.ANY_STRING.toString();
    }

    /**
     * {@see BasicValidatorContext}
     */
    public static <T extends ValidationReference> ValidatorContext<T> basic(final T validationReference,
                                                                            final Function<ValidatorSelector, Validator<T, ? super ValidatorContext<T>>> validatorSelectorToValidator,
                                                                            final BiFunction<Object, T, ExpressionEvaluationContext> referenceToExpressionEvaluationContext,
                                                                            final CanConvert canConvert,
                                                                            final EnvironmentContext environmentContext) {
        return BasicValidatorContext.with(
            validationReference,
            validatorSelectorToValidator,
            referenceToExpressionEvaluationContext,
            canConvert,
            environmentContext
        );
    }

    /**
     * {@see FakeValidatorContext}
     */
    public static <T extends ValidationReference> ValidatorContext<T> fake() {
        return new FakeValidatorContext<>();
    }

    /**
     * Stop creation
     */
    private ValidatorContexts() {
        throw new UnsupportedOperationException();
    }
}
