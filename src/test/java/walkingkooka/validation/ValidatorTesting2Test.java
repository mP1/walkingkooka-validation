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

import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.Lists;
import walkingkooka.validation.ValidatorTesting2Test.TestValidator;

import java.util.List;
import java.util.Objects;

public class ValidatorTesting2Test implements ValidatorTesting2<TestValidator, TestValidationReference, TestValidatorContext> {

    @Test
    public void testValidateAndCheck() {
        final ValidationError<TestValidationReference> error1 = ValidationError.with(
            new TestValidationReference("A1")
        ).setMessage("Message1");

        final ValidationError<TestValidationReference> error2 = ValidationError.with(
            new TestValidationReference("B2")
        ).setMessage("Message2");

        this.validateAndCheck(
            new TestValidator(error1, error2),
            "Value",
            new TestValidatorContext(),
            error1,
            error2
        );
    }

    @Override
    public TestValidator createValidator() {
        return new TestValidator();
    }

    @Override
    public TestValidatorContext createContext() {
        return new TestValidatorContext();
    }

    static class TestValidator implements Validator<TestValidationReference, TestValidatorContext> {

        TestValidator(final ValidationError<TestValidationReference>... errors) {
            this.errors = Lists.of(errors);
        }

        @Override
        public List<ValidationError<TestValidationReference>> validate(final Object value,
                                                                       final TestValidatorContext context) {
            Objects.requireNonNull(context, "context");
            return this.errors;
        }

        private final List<ValidationError<TestValidationReference>> errors;
    }
}
