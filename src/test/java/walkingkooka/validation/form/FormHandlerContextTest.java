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

package walkingkooka.validation.form;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.validation.FakeValidator;
import walkingkooka.validation.FakeValidatorContext;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.ValidationError;
import walkingkooka.validation.Validator;
import walkingkooka.validation.ValidatorContext;
import walkingkooka.validation.provider.ValidatorSelector;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class FormHandlerContextTest implements ClassTesting2<FormHandlerContext<TestValidationReference, Void>> {

    // validateFormFields...............................................................................................

    @Test
    public void testValidateFormFieldsWithUnknownFields() {
        final FormField<TestValidationReference> field1 = FormField.with(
            new TestValidationReference("Field1")
        );
        final FormField<TestValidationReference> field2 = FormField.with(
            new TestValidationReference("Field2")
        );
        final FormField<TestValidationReference> field3 = FormField.with(
            new TestValidationReference("Field3")
        );
        final FormField<TestValidationReference> field4 = FormField.with(
            new TestValidationReference("Field4")
        );
        final FormField<TestValidationReference> field5 = FormField.with(
            new TestValidationReference("Field5")
        );

        final IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> new TestFormHandlerContext(
                field2,
                field3,
                field4
            ).validateFormFields(
                Lists.of(
                    field1,
                    field3,
                    field4,
                    field5
                )
            )
        );

        this.checkEquals(
            "Form contains unknown fields: Field1,Field5",
            thrown.getMessage(),
            thrown::toString
        );
    }

    @Test
    public void testValidateFormFieldsWithEmptyValueNoValidator() {
        final FormField<TestValidationReference> field1 = FormField.with(
            new TestValidationReference("Field1")
        );

        this.validateFormFieldsAndCheck(
            new TestFormHandlerContext(field1) {

                @Override
                public ValidatorContext<TestValidationReference> validatorContext(final TestValidationReference reference) {
                    return new FakeValidatorContext<>() {
                        @Override
                        public TestValidationReference validationReference() {
                            return reference;
                        }
                    };
                }
            },
            Lists.of(
                field1
            )
        );
    }

    @Test
    public void testValidateFormFieldsWithValidatorAndNoErrors() {
        final ValidatorSelector validator1 = ValidatorSelector.parse("Validator1");

        final FormField<TestValidationReference> field1 = FormField.with(
            new TestValidationReference("Field1")
        ).setValidator(
            Optional.of(validator1)
        );

        final Optional<Object> value1 = Optional.of("value1");

        this.validateFormFieldsAndCheck(
            new TestFormHandlerContext(field1) {

                @Override
                public ValidatorContext<TestValidationReference> validatorContext(final TestValidationReference reference) {
                    return new FakeValidatorContext<>() {
                        @Override
                        public TestValidationReference validationReference() {
                            return reference;
                        }

                        @Override
                        public Validator<TestValidationReference, ? super ValidatorContext<TestValidationReference>> validator(final ValidatorSelector selector) {
                            if (selector.equals(validator1)) {
                                return new FakeValidator<>() {
                                    @Override
                                    public List<ValidationError<TestValidationReference>> validate(final Object value,
                                                                                                   final ValidatorContext<TestValidationReference> context) {
                                        return this.noValidationErrors();
                                    }
                                };
                            }
                            throw new UnsupportedOperationException("Unknown validator " + selector);
                        }
                    };
                }
            },
            Lists.of(
                field1.setValue(value1)
            )
        );
    }

    @Test
    public void testValidateFormFieldsWithUnknownValidator() {
        final ValidatorSelector validator1 = ValidatorSelector.parse("Validator1");

        final FormField<TestValidationReference> field1 = FormField.with(
            new TestValidationReference("Field1")
        ).setValidator(
            Optional.of(validator1)
        );

        final Optional<Object> value1 = Optional.of("value1");

        this.validateFormFieldsAndCheck(
            new TestFormHandlerContext(field1) {

                @Override
                public ValidatorContext<TestValidationReference> validatorContext(final TestValidationReference reference) {
                    return new FakeValidatorContext<>() {
                        @Override
                        public TestValidationReference validationReference() {
                            return reference;
                        }

                        @Override
                        public Validator<TestValidationReference, ? super ValidatorContext<TestValidationReference>> validator(final ValidatorSelector selector) {
                            throw new UnsupportedOperationException("Unknown validator " + selector);
                        }
                    };
                }
            },
            Lists.of(
                field1.setValue(value1)
            ),
            ValidationError.with(
                field1.reference(),
                "Unknown validator Validator1"
            )
        );
    }

    @Test
    public void testValidateFormFieldsWithSomeWithValidationErrors() {
        final ValidatorSelector validator1 = ValidatorSelector.parse("Validator1");
        final ValidatorSelector validator2 = ValidatorSelector.parse("Validator2");

        final Optional<Object> value1 = Optional.of("value1");
        final Optional<Object> value2 = Optional.of("value2");
        final Optional<Object> value3 = Optional.of("value3");

        final FormField<TestValidationReference> field1 = FormField.with(
            new TestValidationReference("Field1")
        ).setValidator(
            Optional.of(validator1)
        ).setValue(value1);

        final FormField<TestValidationReference> field2 = FormField.with(
            new TestValidationReference("Field2")
        ).setValidator(
            Optional.of(validator2)
        ).setValue(value2);

        final FormField<TestValidationReference> field3 = FormField.with(
            new TestValidationReference("Field3")
        ).setValue(value3);

        this.validateFormFieldsAndCheck(
            new TestFormHandlerContext(
                field1,
                field2,
                field3
            ) {
                @Override
                public ValidatorContext<TestValidationReference> validatorContext(final TestValidationReference reference) {
                    return new FakeValidatorContext<>() {
                        @Override
                        public TestValidationReference validationReference() {
                            return reference;
                        }

                        @Override
                        public Validator<TestValidationReference, ? super ValidatorContext<TestValidationReference>> validator(final ValidatorSelector selector) {
                            if (selector.equals(validator1)) {
                                return new FakeValidator<>() {
                                    @Override
                                    public List<ValidationError<TestValidationReference>> validate(final Object value,
                                                                                                   final ValidatorContext<TestValidationReference> context) {
                                        return Lists.of(
                                            context.validationError("Error1")
                                                .setValue(Optional.of(value))
                                        );
                                    }
                                };
                            }
                            if (selector.equals(validator2)) {
                                return new FakeValidator<>() {
                                    @Override
                                    public List<ValidationError<TestValidationReference>> validate(final Object value,
                                                                                                   final ValidatorContext<TestValidationReference> context) {
                                        return this.noValidationErrors();
                                    }
                                };
                            }
                            throw new UnsupportedOperationException("Unknown validator " + selector);
                        }
                    };
                }
            },
            Lists.of(
                field1.setValue(value1),
                field2.setValue(value2),
                field3.setValue(value3)
            ),
            ValidationError.with(
                field1.reference(),
                "Error1"
            ).setValue(value1)
        );
    }

    @Test
    public void testValidateFormFieldsWithNullValueAndValidationError() {
        final ValidatorSelector validator1 = ValidatorSelector.parse("Validator1");

        final Optional<Object> value1 = Optional.empty();

        final FormField<TestValidationReference> field1 = FormField.with(
            new TestValidationReference("Field1")
        ).setValidator(
            Optional.of(validator1)
        ).setValue(value1);

        this.validateFormFieldsAndCheck(
            new TestFormHandlerContext(
                field1
            ) {
                @Override
                public ValidatorContext<TestValidationReference> validatorContext(final TestValidationReference reference) {
                    return new FakeValidatorContext<>() {
                        @Override
                        public TestValidationReference validationReference() {
                            return reference;
                        }

                        @Override
                        public Validator<TestValidationReference, ? super ValidatorContext<TestValidationReference>> validator(final ValidatorSelector selector) {
                            if (selector.equals(validator1)) {
                                return new FakeValidator<>() {
                                    @Override
                                    public List<ValidationError<TestValidationReference>> validate(final Object value,
                                                                                                   final ValidatorContext<TestValidationReference> context) {
                                        return Lists.of(
                                            context.validationError("Error1")
                                                .setValue(Optional.ofNullable(value)) // value is null
                                        );
                                    }
                                };
                            }
                            throw new UnsupportedOperationException("Unknown validator " + selector);
                        }
                    };
                }
            },
            Lists.of(
                field1.setValue(value1)
            ),
            ValidationError.with(
                field1.reference(),
                "Error1"
            ).setValue(value1)
        );
    }

    @Test
    public void testValidateFormFieldsWithNonNullValueAndValidationError() {
        final ValidatorSelector validator1 = ValidatorSelector.parse("Validator1");

        final Optional<Object> value1 = Optional.of("NonNullValue1");

        final FormField<TestValidationReference> field1 = FormField.with(
            new TestValidationReference("Field1")
        ).setValidator(
            Optional.of(validator1)
        ).setValue(value1);

        this.validateFormFieldsAndCheck(
            new TestFormHandlerContext(
                field1
            ) {
                @Override
                public ValidatorContext<TestValidationReference> validatorContext(final TestValidationReference reference) {
                    return new FakeValidatorContext<>() {
                        @Override
                        public TestValidationReference validationReference() {
                            return reference;
                        }

                        @Override
                        public Validator<TestValidationReference, ? super ValidatorContext<TestValidationReference>> validator(final ValidatorSelector selector) {
                            if (selector.equals(validator1)) {
                                return new FakeValidator<>() {
                                    @Override
                                    public List<ValidationError<TestValidationReference>> validate(final Object value,
                                                                                                   final ValidatorContext<TestValidationReference> context) {
                                        return Lists.of(
                                            context.validationError("Error1")
                                                .setValue(Optional.of(value))
                                        );
                                    }
                                };
                            }
                            throw new UnsupportedOperationException("Unknown validator " + selector);
                        }
                    };
                }
            },
            Lists.of(
                field1.setValue(value1)
            ),
            ValidationError.with(
                field1.reference(),
                "Error1"
            ).setValue(value1)
        );
    }

    @Test
    public void testValidateFormFieldsWithValidationErrorsFromMultipleFields() {
        final ValidatorSelector validator1 = ValidatorSelector.parse("Validator1");
        final ValidatorSelector validator2 = ValidatorSelector.parse("Validator2");

        final Optional<Object> value1 = Optional.of("value1");
        final Optional<Object> value2 = Optional.of("value2");
        final Optional<Object> value3 = Optional.of("value3");

        final FormField<TestValidationReference> field1 = FormField.with(
            new TestValidationReference("Field1")
        ).setValidator(
            Optional.of(validator1)
        ).setValue(value1);

        final FormField<TestValidationReference> field2 = FormField.with(
            new TestValidationReference("Field2")
        ).setValidator(
            Optional.of(validator2)
        ).setValue(value2);

        final FormField<TestValidationReference> field3 = FormField.with(
            new TestValidationReference("Field3")
        ).setValue(value3);

        this.validateFormFieldsAndCheck(
            new TestFormHandlerContext(
                field1,
                field2,
                field3
            ) {

                @Override
                public ValidatorContext<TestValidationReference> validatorContext(final TestValidationReference reference) {
                    return new FakeValidatorContext<>() {

                        @Override
                        public TestValidationReference validationReference() {
                            return reference;
                        }

                        @Override
                        public Validator<TestValidationReference, ? super ValidatorContext<TestValidationReference>> validator(final ValidatorSelector selector) {
                            if (selector.equals(validator1)) {
                                return new FakeValidator<>() {
                                    @Override
                                    public List<ValidationError<TestValidationReference>> validate(final Object value,
                                                                                                   final ValidatorContext<TestValidationReference> context) {
                                        return Lists.of(
                                            context.validationError("Error1")
                                                .setValue(Optional.of(value))
                                        );
                                    }
                                };
                            }
                            if (selector.equals(validator2)) {
                                return new FakeValidator<>() {
                                    @Override
                                    public List<ValidationError<TestValidationReference>> validate(final Object value,
                                                                                                   final ValidatorContext<TestValidationReference> context) {
                                        return Lists.of(
                                            context.validationError("Error2a")
                                                .setValue(Optional.of(value)),
                                            context.validationError("Error2b")
                                                .setValue(Optional.of(value))
                                        );
                                    }
                                };
                            }
                            throw new UnsupportedOperationException("Unknown validator " + selector);
                        }
                    };
                }
            },
            Lists.of(
                field1.setValue(value1),
                field2.setValue(value2),
                field3.setValue(value3)
            ),
            ValidationError.with(
                field1.reference(),
                "Error1"
            ).setValue(value1),
            ValidationError.with(
                field2.reference(),
                "Error2a"
            ).setValue(value2),
            ValidationError.with(
                field2.reference(),
                "Error2b"
            ).setValue(value2)
        );
    }

    private void validateFormFieldsAndCheck(final TestFormHandlerContext context,
                                            final List<FormField<TestValidationReference>> fields,
                                            final ValidationError<TestValidationReference>... expected) {
        this.validateFormFieldsAndCheck(
            context,
            fields,
            Lists.of(expected)
        );
    }

    private void validateFormFieldsAndCheck(final TestFormHandlerContext context,
                                            final List<FormField<TestValidationReference>> fields,
                                            final List<ValidationError<TestValidationReference>> expected) {
        this.checkEquals(
            expected,
            context.validateFormFields(fields),
            fields::toString
        );
    }

    static class TestFormHandlerContext extends FakeFormHandlerContext<TestValidationReference, Void> {

        TestFormHandlerContext(final FormField<TestValidationReference>... fields) {
            this.form = Form.<TestValidationReference>with(FormName.with("FormNameIsNotImportant"))
                .setFields(
                    Lists.of(fields)
                );
        }

        @Override
        public Form<TestValidationReference> form() {
            return this.form;
        }

        private final Form<TestValidationReference> form;

        @Override
        public final Comparator<TestValidationReference> formFieldReferenceComparator() {
            return TestValidationReference.COMPARATOR;
        }
    }

    // class............................................................................................................

    @Override
    public Class<FormHandlerContext<TestValidationReference, Void>> type() {
        return Cast.to(FormHandlerContext.class);
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
