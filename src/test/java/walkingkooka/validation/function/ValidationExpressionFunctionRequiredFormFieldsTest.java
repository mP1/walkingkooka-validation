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

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.set.Sets;
import walkingkooka.tree.expression.function.ExpressionFunctionTesting;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.ValidationError;
import walkingkooka.validation.ValidationErrorList;
import walkingkooka.validation.form.FormField;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ValidationExpressionFunctionRequiredFormFieldsTest implements ExpressionFunctionTesting<ValidationExpressionFunctionRequiredFormFields<TestValidationReference, Void, FakeValidatorExpressionEvaluationContext<TestValidationReference, Void>>, ValidationErrorList<TestValidationReference>, FakeValidatorExpressionEvaluationContext<TestValidationReference, Void>> {

    private final static TestValidationReference FIELD1 = new TestValidationReference("Field1");
    private final static TestValidationReference FIELD2 = new TestValidationReference("Field2");
    private final static TestValidationReference FIELD3 = new TestValidationReference("Field3");

    @Test
    public void testWithNullFieldsFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValidationExpressionFunctionRequiredFormFields.with(null)
        );
    }

    @Test
    public void testWithEmptyFieldsFails() {
        final IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> ValidationExpressionFunctionRequiredFormFields.with(
                Sets.empty()
            )
        );
        this.checkEquals(
            "Empty required form fields",
            thrown.getMessage()
        );
    }

    // apply............................................................................................................

    @Test
    public void testApplyNoMissingFieldOrMissingValues() {
        this.applyAndCheck2(
            Lists.of(
              Lists.of(
                  FormField.with(FIELD1)
                      .setValue(Optional.of("Value1")),
                  FormField.with(FIELD2)
                      .setValue(Optional.of("Value2")),
                  FormField.with(FIELD3)
                      .setValue(Optional.of("Value3"))
              )
            ),
            ValidationErrorList.empty()
        );
    }

    @Test
    public void testApplyMissingFieldAndMissingValues() {
        this.applyAndCheck2(
            Lists.of(
                Lists.of(
                    FormField.with(FIELD2),
                    FormField.with(FIELD3)
                        .setValue(Optional.of("Value3"))
                )
            ),
            ValidationErrorList.with(
                Lists.of(
                    ValidationError.with(FIELD2, "Required")
                )
            )
        );
    }

    @Override
    public ValidationExpressionFunctionRequiredFormFields<TestValidationReference, Void, FakeValidatorExpressionEvaluationContext<TestValidationReference, Void>> createBiFunction() {
        return ValidationExpressionFunctionRequiredFormFields.with(
            Sets.of(
                FIELD1,
                FIELD2
            )
        );
    }

    @Override
    public FakeValidatorExpressionEvaluationContext<TestValidationReference, Void> createContext() {
        return new FakeValidatorExpressionEvaluationContext<>();
    }

    @Override
    public int minimumParameterCount() {
        return 1;
    }

    // class............................................................................................................

    @Override
    public Class<ValidationExpressionFunctionRequiredFormFields<TestValidationReference, Void, FakeValidatorExpressionEvaluationContext<TestValidationReference, Void>>> type() {
        return Cast.to(ValidationExpressionFunctionRequiredFormFields.class);
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
