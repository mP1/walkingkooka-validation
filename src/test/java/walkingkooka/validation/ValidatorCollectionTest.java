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
import walkingkooka.validation.ValidatorCollectionTest.TestValidationReference;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ValidatorCollectionTest implements ValidatorTesting2<ValidatorCollection<TestValidationReference>, TestValidationReference> {

    private final static Object VALUE = "Value111";

    static final class TestValidationReference implements ValidationReference {

        TestValidationReference(final String field) {
            this.field = field;
        }

        @Override
        public String text() {
            return this.field;
        }

        private final String field;

        @Override
        public String toString() {
            return this.field;
        }
    }

    private final static TestValidationReference REFERENCE1 = new TestValidationReference("Field1");

    private final static TestValidationReference REFERENCE2 = new TestValidationReference("Field2");

    private static ValidationError<TestValidationReference> error(final TestValidationReference reference,
                                                                  final int messageNumber,
                                                                  final Object value) {
        return ValidationError.with(
            reference,
            message(reference, messageNumber),
            Optional.ofNullable(value)
        );
    }

    private static String message(final TestValidationReference reference,
                                  final int messageNumber) {
        return reference + ": Message " + messageNumber;
    }

    private final static Validator<TestValidationReference> VALIDATOR1 = new Validator<>() {

        @Override
        public List<ValidationError<TestValidationReference>> validate(final Object value,
                                                                       final ValidatorContext<TestValidationReference> context) {
            return List.of(
                error(
                    context.validationReference(),
                    1,
                    value
                ),
                error(
                    context.validationReference(),
                    2,
                    value
                )
            );
        }

        @Override
        public String toString() {
            return "Validator1";
        }
    };

    private final static Validator<TestValidationReference> VALIDATOR2 = new Validator<>() {

        @Override
        public List<ValidationError<TestValidationReference>> validate(final Object value,
                                                                       final ValidatorContext<TestValidationReference> context) {
            return List.of(
                ValidationError.with(
                    context.validationReference(),
                    message(
                        context.validationReference(),
                        3
                    ),
                    Optional.ofNullable(value)
                )
            );
        }

        @Override
        public String toString() {
            return "Validator2";
        }
    };

    private final static Validator<TestValidationReference> VALIDATOR3 = new Validator<>() {

        @Override
        public List<ValidationError<TestValidationReference>> validate(final Object value,
                                              final ValidatorContext<TestValidationReference> context) {
            return List.of(
                ValidationError.with(
                    context.validationReference(),
                    message(
                        context.validationReference(),
                        4
                    ),
                    Optional.ofNullable(value)
                )
            );
        }

        @Override
        public String toString() {
            return "Validator3";
        }
    };

    private final static List<Validator<TestValidationReference>> VALIDATORS = Lists.of(
        VALIDATOR1,
        VALIDATOR2,
        VALIDATOR3
    );

    // with.............................................................................................................

    @Test
    public void testWithInvalidMaxErrorsFails() {
        final IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> ValidatorCollection.with(
                -1,
                VALIDATORS
            )
        );

        this.checkEquals(
            "Invalid maxErrors -1 <= 0",
            thrown.getMessage()
        );
    }

    @Test
    public void testWithInvalidMaxErrorsFails2() {
        final IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> ValidatorCollection.with(
                0,
                VALIDATORS
            )
        );

        this.checkEquals(
            "Invalid maxErrors 0 <= 0",
            thrown.getMessage()
        );
    }

    @Test
    public void testWithNullValidatorsFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValidatorCollection.with(
                1,
                null
            )
        );
    }

    @Test
    public void testWithEmptyErrorsFails() {
        final IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> ValidatorCollection.with(
                0,
                VALIDATORS
            )
        );

        this.checkEquals(
            "Invalid maxErrors 0 <= 0",
            thrown.getMessage()
        );
    }

    // validate.........................................................................................................

    @Test
    public void testValidate() {
        this.validateAndCheck(
            ValidatorCollection.with(
                10,
                VALIDATORS
            ),
            VALUE,
            this.createContext(REFERENCE1),
            error(REFERENCE1, 1, VALUE),
            error(REFERENCE1, 2, VALUE),
            error(REFERENCE1, 3, VALUE),
            error(REFERENCE1, 4, VALUE)
        );
    }

    @Test
    public void testValidateMax() {
        this.validateAndCheck(
            ValidatorCollection.with(
                2,
                VALIDATORS
            ),
            VALUE,
            this.createContext(REFERENCE2),
            error(REFERENCE2, 1, VALUE),
            error(REFERENCE2, 2, VALUE)
        );
    }

    @Test
    public void testValidateWithoutErrors() {
        this.validateAndCheck(
            ValidatorCollection.with(
                10,
                Lists.of(
                    new Validator<TestValidationReference>() {

                        @Override
                        public List<ValidationError<TestValidationReference>> validate(final Object value,
                                                              final ValidatorContext<TestValidationReference> context) {
                            return Lists.empty();
                        }

                        @Override
                        public String toString() {
                            return "No errors";
                        }
                    }
                )
            ),
            VALUE,
            ValidatorContexts.fake()
        );
    }

    // helpers..........................................................................................................

    @Override
    public ValidatorCollection<TestValidationReference> createValidator() {
        return this.createValidator(3);
    }

    private ValidatorCollection<TestValidationReference> createValidator(final int maxErrors) {
        return ValidatorCollection.with(
            maxErrors,
            VALIDATORS
        );
    }

    @Override
    public ValidatorContext<TestValidationReference> createContext() {
        return new FakeValidatorContext<>();
    }

    private ValidatorContext<TestValidationReference> createContext(final TestValidationReference reference) {
        return new FakeValidatorContext<>() {
            @Override
            public TestValidationReference validationReference() {
                return reference;
            }
        };
    }
}
