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

package walkingkooka.validation.expression.function;

import walkingkooka.Cast;
import walkingkooka.collect.list.StringList;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.ExpressionPurityContext;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterKind;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterName;
import walkingkooka.validation.ValidationChoice;
import walkingkooka.validation.ValidationChoiceList;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.expression.ValidatorExpressionEvaluationContext;

import java.util.List;
import java.util.Optional;

/**
 * A function which returns a {@link walkingkooka.validation.ValidationChoiceList} from one list of values, or
 * two {@link List}, the first holding the labels and the second the matching values.
 * <pre>
 * validationChoiceList(
 *   list(
 *     "ACT",
 *     "NSW",
 *     "QLD",
 *     "SA",
 *     "TAS",
 *     "VIC",
 *     "WA"
 *   )
 * )
 * validationChoiceList(
 *   list(
 *     "Australian Capital Territory",
 *     "New South Wales",
 *     "Queensland",
 *     "South Australia",
 *     "Tasmania",
 *     "Victoria",
 *     "West Australia"
 *   ),
 *   list(
 *     "ACT",
 *     "NSW",
 *     "QLD",
 *     "SA",
 *     "TAS",
 *     "VIC",
 *     "WA"
 *   )
 * )
 * </pre>
 */
final class ValidationExpressionFunctionValidationChoiceList<R extends ValidationReference, C extends ValidatorExpressionEvaluationContext<R>> extends ValidationExpressionFunction<ValidationChoiceList, R, C> {

    /**
     * Type safe getter.
     */
    static <R extends ValidationReference, C extends ValidatorExpressionEvaluationContext<R>> ValidationExpressionFunctionValidationChoiceList<R, C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private static final ValidationExpressionFunctionValidationChoiceList<?, ?> INSTANCE = new ValidationExpressionFunctionValidationChoiceList<>();

    private ValidationExpressionFunctionValidationChoiceList() {
        super("validationChoiceList");
    }

    /**
     * The {@link Expression} which will be evaluated giving the labels.
     */
    private final static ExpressionFunctionParameter<StringList> LABELS = ExpressionFunctionParameterName.with("labels")
        .required(StringList.class)
        .setKinds(ExpressionFunctionParameterKind.CONVERT_EVALUATE_RESOLVE_REFERENCES);

    /**
     * The {@link Expression} which will be evaluated giving the values.
     */
    private final static ExpressionFunctionParameter<List<?>> VALUES = ExpressionFunctionParameterName.with("values")
        .required(Cast.<Class<List<?>>>to(List.class))
        .setKinds(ExpressionFunctionParameterKind.CONVERT_EVALUATE_RESOLVE_REFERENCES);

    private final static List<ExpressionFunctionParameter<?>> PARAMETERS_1 = ExpressionFunctionParameter.list(
        VALUES
    );

    private final static List<ExpressionFunctionParameter<?>> PARAMETERS_2 = ExpressionFunctionParameter.list(
        LABELS,
        VALUES
    );

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return 1 == count ?
            PARAMETERS_1 :
            PARAMETERS_2;
    }

    @Override
    public Class<ValidationChoiceList> returnType() {
        return ValidationChoiceList.class;
    }

    @Override
    public boolean isPure(final ExpressionPurityContext context) {
        return true; // is pure if parameters are pure.
    }

    @Override
    ValidationChoiceList applyNonNullParameters(final List<Object> parameters,
                                                final C context) {
        this.checkParameterCount(parameters);

        ValidationChoiceList validationChoiceList = ValidationChoiceList.EMPTY;

        final int count = parameters.size();
        switch (count) {
            case 1:
                for (final Object value : VALUES.getOrFail(
                    parameters,
                    0
                )) {
                    final String label = context.convertOrFail(
                        value,
                        String.class
                    );

                    validationChoiceList = validationChoiceList.concat(
                        ValidationChoice.with(
                            label,
                            Optional.ofNullable(value)
                        )
                    );
                }
                break;
            case 2:
                final StringList labels = LABELS.getOrFail(
                    parameters,
                    0
                );
                final List<?> values1 = VALUES.getOrFail(
                    parameters,
                    1
                );
                final int labelCount = labels.size();
                final int valueCount = values1.size();
                if (valueCount != labelCount) {
                    throw new IllegalArgumentException("Invalid values count " + valueCount + " != labels count " + labelCount);
                }

                for (int i = 0; i < labelCount; i++) {
                    validationChoiceList = validationChoiceList.concat(
                        ValidationChoice.with(
                            labels.get(i),
                            Optional.ofNullable(
                                values1.get(i)
                            )
                        )
                    );
                }
            default:
                new IllegalArgumentException("Invalid parameters count: " + count + " expected 1 (values), 2 (labels and values)");
        }

        return validationChoiceList;
    }
}