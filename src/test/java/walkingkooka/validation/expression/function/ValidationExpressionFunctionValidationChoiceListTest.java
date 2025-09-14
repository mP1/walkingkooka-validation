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

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.Either;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.list.StringList;
import walkingkooka.convert.Converter;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.Converters;
import walkingkooka.math.NumberList;
import walkingkooka.tree.expression.function.ExpressionFunctionTesting;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.ValidationChoice;
import walkingkooka.validation.ValidationChoiceList;
import walkingkooka.validation.expression.FakeValidatorExpressionEvaluationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ValidationExpressionFunctionValidationChoiceListTest implements ExpressionFunctionTesting<ValidationExpressionFunctionValidationChoiceList<TestValidationReference, FakeValidatorExpressionEvaluationContext<TestValidationReference>>, ValidationChoiceList, FakeValidatorExpressionEvaluationContext<TestValidationReference>> {

    // apply............................................................................................................

    @Test
    public void testApplyOnlyValues() {
        this.applyAndCheck(
            Lists.of(
                Lists.of(
                    "Value1",
                    222,
                    true
                )
            ),
            ValidationChoiceList.EMPTY.setElements(
                Lists.of(
                    ValidationChoice.with(
                        "Value1",
                        Optional.ofNullable("Value1")
                    ),
                    ValidationChoice.with(
                        "222",
                        Optional.ofNullable(222)
                    ),
                    ValidationChoice.with(
                        "true",
                        Optional.ofNullable(true)
                    )
                )
            )
        );
    }

    @Test
    public void testApplyLabelsAndValuesDifferentCount() {
        final IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> this.apply2(
                StringList.EMPTY.setElements(
                    Lists.of(
                        "Label1",
                        "Label2"
                    )
                ),
                Lists.of(
                    "Value1",
                    "Value2",
                    "Value3"
                )
            )
        );

        this.checkEquals(
            "Invalid values count 3 != labels count 2",
            thrown.getMessage()
        );
    }

    @Test
    public void testApplyLabelsAndValues() {
        this.applyAndCheck(
            Lists.of(
                StringList.EMPTY.setElements(
                    Lists.of(
                        "Label1",
                        "Label22",
                        "Label333"
                    )
                ),
                NumberList.EMPTY.setElements(
                    Lists.of(
                        111,
                        222,
                        333
                    )
                )
            ),
            ValidationChoiceList.EMPTY.setElements(
                Lists.of(
                    ValidationChoice.with(
                        "Label1",
                        Optional.ofNullable(111)
                    ),
                    ValidationChoice.with(
                        "Label22",
                        Optional.ofNullable(222)
                    ),
                    ValidationChoice.with(
                        "Label333",
                        Optional.ofNullable(333)
                    )
                )
            )
        );
    }

    @Override
    public ValidationExpressionFunctionValidationChoiceList<TestValidationReference, FakeValidatorExpressionEvaluationContext<TestValidationReference>> createBiFunction() {
        return ValidationExpressionFunctionValidationChoiceList.instance();
    }

    @Override
    public FakeValidatorExpressionEvaluationContext<TestValidationReference> createContext() {
        return new FakeValidatorExpressionEvaluationContext<>() {

            @Override
            public <T> Either<T, String> convert(final Object value,
                                                 final Class<T> target) {
                return this.converter.convert(
                    value,
                    target,
                    this
                );
            }

            private final Converter<ConverterContext> converter = Converters.collection(
                Lists.of(
                    Converters.characterOrCharSequenceOrHasTextOrStringToCharacterOrCharSequenceOrString(),
                    Converters.objectToString(),
                    Converters.textToStringList()
                )
            );
        };
    }

    @Override
    public int minimumParameterCount() {
        return 1;
    }

    // class............................................................................................................

    @Override
    public Class<ValidationExpressionFunctionValidationChoiceList<TestValidationReference, FakeValidatorExpressionEvaluationContext<TestValidationReference>>> type() {
        return Cast.to(ValidationExpressionFunctionValidationChoiceList.class);
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
