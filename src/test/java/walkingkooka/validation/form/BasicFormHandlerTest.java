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
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.validation.FakeValidator;
import walkingkooka.validation.FakeValidatorContext;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.ValidationError;
import walkingkooka.validation.ValidationErrorList;
import walkingkooka.validation.Validator;
import walkingkooka.validation.ValidatorContext;
import walkingkooka.validation.provider.ValidatorSelector;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public final class BasicFormHandlerTest implements FormHandlerTesting<
    BasicFormHandler<TestValidationReference, BasicFormHandlerTest, FakeFormHandlerContext<TestValidationReference, BasicFormHandlerTest>>,
    TestValidationReference,
    BasicFormHandlerTest,
    FakeFormHandlerContext<TestValidationReference, BasicFormHandlerTest>
    > {

    @Test
    public void testPrepareForm() {
        final FormField<TestValidationReference> field1 = FormField.with(
            new TestValidationReference("Field1")
        );
        final FormField<TestValidationReference> field2 = FormField.with(
            new TestValidationReference("Field2")
        ).setValue(
            Optional.of("OldValue2")
        );
        final FormField<TestValidationReference> field3 = FormField.with(
            new TestValidationReference("Field3")
        ).setValue(
            Optional.of("OldValue3")
        );

        final Form<TestValidationReference> form = Form.<TestValidationReference>with(
            FormName.with("Form123")
        ).setFields(
            Lists.of(
                field1,
                field2,
                field3
            )
        );

        final Optional<Object> value1 = Optional.empty();
        final Optional<Object> value2 = Optional.empty();
        final Optional<Object> value3 = Optional.of("NewValue3");

        this.prepareFormAndCheck(
            this.createFormHandler(),
            form,
            new FakeFormHandlerContext<>() {
                @Override
                public Optional<Object> loadFormFieldValue(final TestValidationReference reference) {
                    if (field1.reference().equals(reference)) {
                        return value1;
                    }
                    if (field2.reference().equals(reference)) {
                        return value2;
                    }
                    if (field3.reference().equals(reference)) {
                        return value3;
                    }
                    throw new UnsupportedOperationException("Unknown reference " + reference);
                }
            },
            Form.<TestValidationReference>with(
                FormName.with("Form123")
            ).setFields(
                Lists.of(
                    field1,
                    field2.setValue(value2),
                    field3.setValue(value3)
                )
            )
        );
    }

    @Test
    public void testValidateForm() {
        final Validator<TestValidationReference, ValidatorContext<TestValidationReference>> validator1 = new FakeValidator<>() {
            @Override
            public List<ValidationError<TestValidationReference>> validate(final Object value,
                                                                           final ValidatorContext<TestValidationReference> context) {
                return Lists.of(
                    context.validationError("Error1")
                        .setValue(
                            Optional.ofNullable(value)
                        )
                );
            }
        };
        final ValidatorSelector validatorSelector1 = ValidatorSelector.parse("Validator1");

        final FormField<TestValidationReference> field1 = FormField.with(
            new TestValidationReference("Field1")
        ).setValidator(
            Optional.of(validatorSelector1)
        ).setValue(
            Optional.of("Value1")
        );
        final FormField<TestValidationReference> field2 = FormField.with(
            new TestValidationReference("Field2")
        ).setValue(
            Optional.of("Value2")
        );
        final Form<TestValidationReference> form = Form.<TestValidationReference>with(
            FormName.with("Form123")
        ).setFields(
            Lists.of(
                field1,
                field2
            )
        );

        this.validateFormAndCheck(
            this.createFormHandler(),
            form,
            new FakeFormHandlerContext<TestValidationReference, BasicFormHandlerTest>() {
                @Override
                public Form<TestValidationReference> form() {
                    return Form.<TestValidationReference>with(
                        FormName.with("Form123")
                    ).setFields(
                        Lists.of(
                            field1.setValidator(
                                Optional.of(validatorSelector1)
                            ),
                            field2.setLabel("Label2")
                        )
                    );
                }

                @Override
                public Comparator<TestValidationReference> formFieldReferenceComparator() {
                    return TestValidationReference.COMPARATOR;
                }

                @Override
                public ValidatorContext<TestValidationReference> validatorContext(final TestValidationReference reference) {
                    return new FakeValidatorContext<>() {

                        @Override
                        public TestValidationReference validationReference() {
                            return reference;
                        }

                        @Override
                        public Validator<TestValidationReference, ? super ValidatorContext<TestValidationReference>> validator(final ValidatorSelector selector) {
                            if (selector.equals(validatorSelector1)) {
                                return validator1;
                            }
                            throw new UnsupportedOperationException("Unknown selector " + selector);
                        }
                    };
                }
            },
            ValidationErrorList.empty().concat(
                    ValidationError.with(
                        field1.reference(),
                        "Error1"
                    ).setValue(
                        field1.value()
                    )
                )
        );
    }

    @Test
    public void testSubmitForm() {
        final FormField<TestValidationReference> field1 = FormField.with(
            new TestValidationReference("Field1")
        );
        final FormField<TestValidationReference> field2 = FormField.with(
            new TestValidationReference("Field2")
        ).setValue(
            Optional.of("OldValue2")
        );
        final FormField<TestValidationReference> field3 = FormField.with(
            new TestValidationReference("Field3")
        ).setValue(
            Optional.of("OldValue3")
        );

        final Form<TestValidationReference> form = Form.<TestValidationReference>with(
            FormName.with("Form123")
        ).setFields(
            Lists.of(
                field1,
                field2,
                field3
            )
        );

        final Optional<Object> value1 = Optional.empty();
        final Optional<Object> value2 = Optional.empty();
        final Optional<Object> value3 = Optional.of("NewValue3");

        this.prepareFormAndCheck(
            this.createFormHandler(),
            form,
            new FakeFormHandlerContext<>() {
                @Override
                public Optional<Object> loadFormFieldValue(final TestValidationReference reference) {
                    if (field1.reference().equals(reference)) {
                        return Optional.of(value1);
                    }
                    if (field2.reference().equals(reference)) {
                        return Optional.of(value2);
                    }
                    if (field3.reference().equals(reference)) {
                        return Optional.of(value3);
                    }
                    throw new UnsupportedOperationException("Unknown reference " + reference);
                }
            },
            Form.<TestValidationReference>with(
                FormName.with("Form123")
            ).setFields(
                Lists.of(
                    field1,
                    field2.setValue(value2),
                    field3.setValue(value3)
                )
            )
        );
    }

    @Override
    public BasicFormHandler<TestValidationReference, BasicFormHandlerTest, FakeFormHandlerContext<TestValidationReference, BasicFormHandlerTest>> createFormHandler() {
        return BasicFormHandler.instance();
    }

    @Override
    public FakeFormHandlerContext<TestValidationReference, BasicFormHandlerTest> createContext() {
        return new FakeFormHandlerContext<>();
    }

    // class............................................................................................................

    @Override
    public Class<BasicFormHandler<TestValidationReference, BasicFormHandlerTest, FakeFormHandlerContext<TestValidationReference, BasicFormHandlerTest>>> type() {
        return Cast.to(BasicFormHandler.class);
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
