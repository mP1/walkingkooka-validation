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
import walkingkooka.validation.ValidatorTestingTest.TestValidator;

import java.util.List;
import java.util.Optional;

public class ValidatorTestingTest implements ValidatorTesting2<TestValidator> {

    @Test
    public void testValidateAndCheck() {
        final ValidationError error1 = ValidationError.with(
            new ValidationReference() {
                @Override
                public String text() {
                    return "A1";
                }
            },
            "Message1",
            Optional.empty()
        );

        final ValidationError error2 = ValidationError.with(
            new ValidationReference() {
                @Override
                public String text() {
                    return "B2";
                }
            },
            "Message2",
            Optional.empty()
        );

        this.validateAndCheck(
            new TestValidator(error1, error2),
            "Value",
            ValidatorContexts.fake(),
            error1,
            error2
        );
    }

    @Override
    public TestValidator createValidator() {
        return new TestValidator();
    }

    @Override
    public ValidatorContext createContext() {
        return ValidatorContexts.fake();
    }

    static class TestValidator implements Validator {

        TestValidator(final ValidationError... errors) {
            this.errors = Lists.of(errors);
        }

        @Override
        public List<ValidationError> validate(final Object value,
                                              final ValidatorContext<?> context) {
            return this.errors;
        }

        private List<ValidationError> errors;
    }
}
