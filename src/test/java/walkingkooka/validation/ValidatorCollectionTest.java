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
import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.collect.list.Lists;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ValidatorCollectionTest implements ValidatorTesting2<ValidatorCollection<TestValidationReference, TestValidatorContext>, TestValidationReference, TestValidatorContext>,
    HashCodeEqualsDefinedTesting2<ValidatorCollection<TestValidationReference, TestValidatorContext>> {

    private final static Object VALUE = "Value111";

    private final static TestValidationReference REFERENCE1 = new TestValidationReference("Field1");

    private final static TestValidationReference REFERENCE2 = new TestValidationReference("Field2");

    private static ValidationError<TestValidationReference> error(final TestValidationReference reference,
                                                                  final int messageNumber,
                                                                  final Object value) {
        return ValidationError.with(
            reference,
            message(reference, messageNumber)
        ).setValue(
            Optional.ofNullable(value)
        );
    }

    private static String message(final TestValidationReference reference,
                                  final int messageNumber) {
        return reference + ": Message " + messageNumber;
    }

    private final static Validator<TestValidationReference, TestValidatorContext> VALIDATOR1 = new FakeValidator<>() {

        @Override
        public List<ValidationError<TestValidationReference>> validate(final Object value,
                                                                       final TestValidatorContext context) {
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

    private final static Validator<TestValidationReference, TestValidatorContext> VALIDATOR2 = new FakeValidator<>() {

        @Override
        public List<ValidationError<TestValidationReference>> validate(final Object value,
                                                                       final TestValidatorContext context) {
            return List.of(
                context.validationError(
                    message(
                        context.validationReference(),
                        3
                    )
                ).setValue(
                    Optional.ofNullable(value)
                )
            );
        }

        @Override
        public String toString() {
            return "Validator2";
        }
    };

    private final static Validator<TestValidationReference, TestValidatorContext> VALIDATOR3 = new FakeValidator<>() {

        @Override
        public List<ValidationError<TestValidationReference>> validate(final Object value,
                                                                       final TestValidatorContext context) {
            return List.of(
                context.validationError(
                    message(
                        context.validationReference(),
                        4
                    )
                ).setValue(
                    Optional.ofNullable(value)
                )
            );
        }

        @Override
        public String toString() {
            return "Validator3";
        }
    };

    private final static List<Validator<TestValidationReference, TestValidatorContext>> VALIDATORS = Lists.of(
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
                    new FakeValidator<>() {

                        @Override
                        public List<ValidationError<TestValidationReference>> validate(final Object value,
                                                                                       final TestValidatorContext context) {
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
            new TestValidatorContext()
        );
    }

    // helpers..........................................................................................................

    private final static int MAX_ERRORS = 3;

    @Override
    public ValidatorCollection<TestValidationReference, TestValidatorContext> createValidator() {
        return this.createValidator(MAX_ERRORS);
    }

    private ValidatorCollection<TestValidationReference, TestValidatorContext> createValidator(final int maxErrors) {
        return ValidatorCollection.with(
            maxErrors,
            VALIDATORS
        );
    }

    @Override
    public TestValidatorContext createContext() {
        return new TestValidatorContext();
    }

    private TestValidatorContext createContext(final TestValidationReference reference) {
        return new TestValidatorContext() {
            @Override
            public TestValidationReference validationReference() {
                return reference;
            }
        };
    }

    // equals...........................................................................................................

    @Test
    public void testEqualsDifferentMaxErrors() {
        this.checkNotEquals(
            ValidatorCollection.with(
                MAX_ERRORS+1,
                VALIDATORS
            )
        );
    }

    @Test
    public void testEqualsDifferentValidators() {
        this.checkNotEquals(
            ValidatorCollection.with(
                MAX_ERRORS,
                Lists.of(
                    VALIDATOR1
                )
            )
        );
    }

    @Override
    public ValidatorCollection<TestValidationReference, TestValidatorContext> createObject() {
        return ValidatorCollection.with(
            MAX_ERRORS,
            VALIDATORS
        );
    }
}
