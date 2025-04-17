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
import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.net.Url;
import walkingkooka.text.printer.TreePrintableTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallingTesting;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.ValidationValueTypeName;
import walkingkooka.validation.provider.ValidatorSelector;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class FormFieldTest implements HashCodeEqualsDefinedTesting2<FormField<TestValidationReference>>,
    JsonNodeMarshallingTesting<FormField<TestValidationReference>>,
    TreePrintableTesting {

    private final static TestValidationReference REFERENCE = new TestValidationReference("Hello");
    private final static TestValidationReference DIFFERENT_REFERENCE = new TestValidationReference("DifferentReference");

    private final static String LABEL = "Label123";
    private final static String DIFFERENT_LABEL = "DifferentLabel456";

    private final static Optional<ValidationValueTypeName> TYPE = Optional.of(
        ValidationValueTypeName.with("Type1")
    );
    private final static Optional<ValidationValueTypeName> DIFFERENT_TYPE = Optional.of(
        ValidationValueTypeName.with("DifferentType2")
    );

    private final static Optional<Object> VALUE = Optional.of(
        Url.parse("https://example.com")
    );
    private final static Optional<Object> DIFFERENT_VALUE = Optional.of(
        Url.parse("https://example.com/different")
    );

    private final static Optional<ValidatorSelector> VALIDATOR = Optional.of(
        ValidatorSelector.parse("helloValidator")
    );
    private final static Optional<ValidatorSelector> DIFFERENT_VALIDATOR = Optional.of(
        ValidatorSelector.parse("differentValidator")
    );

    // with.............................................................................................................

    @Test
    public void testWithNullReferenceFails() {
        assertThrows(
            NullPointerException.class,
            () -> FormField.with(null)
        );
    }

    @Test
    public void testWith() {
        final FormField<TestValidationReference> field = FormField.with(REFERENCE);
        this.referenceAndCheck(
            field,
            REFERENCE
        );
        this.labelAndCheck(
            field,
            FormField.NO_LABEL
        );
        this.typeAndCheck(
            field,
            FormField.NO_TYPE
        );
        this.valueAndCheck(
            field,
            FormField.NO_VALUE
        );
        this.validatorAndCheck(
            field,
            FormField.NO_VALIDATOR
        );
    }

    // reference........................................................................................................

    @Test
    public void testSetReferenceWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createObject().setReference(null)
        );
    }

    @Test
    public void testSetReferenceWithSame() {
        final FormField<TestValidationReference> field = this.createObject();
        assertSame(
            field,
            field.setReference(REFERENCE)
        );
    }

    @Test
    public void testSetReferenceWithDifferent() {
        final FormField<TestValidationReference> field = this.createObject();
        final FormField<TestValidationReference> different = field.setReference(DIFFERENT_REFERENCE);

        this.referenceAndCheck(
            different,
            DIFFERENT_REFERENCE
        );
        this.labelAndCheck(different);
        this.typeAndCheck(different);
        this.valueAndCheck(different);
        this.validatorAndCheck(different);
    }

    private void referenceAndCheck(final FormField<TestValidationReference> field) {
        this.referenceAndCheck(
            field,
            REFERENCE
        );
    }

    private void referenceAndCheck(final FormField<TestValidationReference> field,
                                   final TestValidationReference expected) {
        this.checkEquals(
            expected,
            field.reference()
        );
    }

    // label............................................................................................................

    @Test
    public void testSetLabelWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createObject().setLabel(null)
        );
    }

    @Test
    public void testSetLabelWithSame() {
        final FormField<TestValidationReference> field = this.createObject();
        assertSame(
            field,
            field.setLabel(LABEL)
        );
    }

    @Test
    public void testSetLabelWithDifferent() {
        final FormField<TestValidationReference> field = this.createObject();
        final FormField<TestValidationReference> different = field.setLabel(DIFFERENT_LABEL);

        this.referenceAndCheck(different);
        this.labelAndCheck(
            different,
            DIFFERENT_LABEL
        );
        this.typeAndCheck(different);
        this.valueAndCheck(different);
        this.validatorAndCheck(different);
    }

    private void labelAndCheck(final FormField<TestValidationReference> field) {
        this.labelAndCheck(
            field,
            LABEL
        );
    }

    private void labelAndCheck(final FormField<TestValidationReference> field,
                               final String expected) {
        this.checkEquals(
            expected,
            field.label()
        );
    }

    // type.............................................................................................................

    @Test
    public void testSetTypeWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createObject().setType(null)
        );
    }

    @Test
    public void testSetTypeWithSame() {
        final FormField<TestValidationReference> field = this.createObject();
        assertSame(
            field,
            field.setType(TYPE)
        );
    }

    @Test
    public void testSetTypeWithDifferent() {
        final FormField<TestValidationReference> field = this.createObject();
        final FormField<TestValidationReference> different = field.setType(DIFFERENT_TYPE);

        this.referenceAndCheck(different);
        this.labelAndCheck(different);
        this.typeAndCheck(
            different,
            DIFFERENT_TYPE
        );
        this.valueAndCheck(different);
        this.validatorAndCheck(different);
    }

    private void typeAndCheck(final FormField<TestValidationReference> field) {
        this.typeAndCheck(
            field,
            TYPE
        );
    }

    private void typeAndCheck(final FormField<TestValidationReference> field,
                              final Optional<ValidationValueTypeName> expected) {
        this.checkEquals(
            expected,
            field.type()
        );
    }
    
    // value............................................................................................................

    @Test
    public void testSetValueWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createObject().setValue(null)
        );
    }

    @Test
    public void testSetValueWithSame() {
        final FormField<TestValidationReference> field = this.createObject();
        assertSame(
            field,
            field.setValue(VALUE)
        );
    }

    @Test
    public void testSetValueWithDifferent() {
        final FormField<TestValidationReference> field = this.createObject();
        final FormField<TestValidationReference> different = field.setValue(DIFFERENT_VALUE);

        this.referenceAndCheck(different);
        this.labelAndCheck(different);
        this.typeAndCheck(different);
        this.valueAndCheck(
            different,
            DIFFERENT_VALUE
        );
        this.validatorAndCheck(different);
    }

    private void valueAndCheck(final FormField<TestValidationReference> field) {
        this.valueAndCheck(
            field,
            VALUE
        );
    }

    private void valueAndCheck(final FormField<TestValidationReference> field,
                               final Optional<Object> expected) {
        this.checkEquals(
            expected,
            field.value()
        );
    }

    // validator........................................................................................................

    @Test
    public void testSetValidatorWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createObject().setValidator(null)
        );
    }

    @Test
    public void testSetValidatorWithSame() {
        final FormField<TestValidationReference> field = this.createObject();
        assertSame(
            field,
            field.setValidator(VALIDATOR)
        );
    }

    @Test
    public void testSetValidatorWithDifferent() {
        final FormField<TestValidationReference> field = this.createObject();
        final FormField<TestValidationReference> different = field.setValidator(DIFFERENT_VALIDATOR);

        this.referenceAndCheck(different);
        this.labelAndCheck(different);
        this.typeAndCheck(different);
        this.valueAndCheck(different);
        this.validatorAndCheck(
            different,
            DIFFERENT_VALIDATOR
        );
    }

    private void validatorAndCheck(final FormField<TestValidationReference> field) {
        this.validatorAndCheck(
            field,
            VALIDATOR
        );
    }

    private void validatorAndCheck(final FormField<TestValidationReference> field,
                                   final Optional<ValidatorSelector> expected) {
        this.checkEquals(
            expected,
            field.validator()
        );
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferentReference() {
        this.checkNotEquals(
            new FormField<>(
                DIFFERENT_REFERENCE,
                LABEL,
                TYPE,
                VALUE,
                VALIDATOR
            )
        );
    }

    @Test
    public void testEqualsDifferentLabel() {
        this.checkNotEquals(
            new FormField<>(
                REFERENCE,
                DIFFERENT_LABEL,
                TYPE,
                VALUE,
                VALIDATOR
            )
        );
    }

    @Test
    public void testEqualsDifferentValidationValueTypeNameType() {
        this.checkNotEquals(
            new FormField<>(
                REFERENCE,
                LABEL,
                DIFFERENT_TYPE,
                VALUE,
                VALIDATOR
            )
        );
    }

    @Test
    public void testEqualsDifferentValue() {
        this.checkNotEquals(
            new FormField<>(
                REFERENCE,
                LABEL,
                TYPE,
                DIFFERENT_VALUE,
                VALIDATOR
            )
        );
    }

    @Test
    public void testEqualsDifferentValidator() {
        this.checkNotEquals(
            new FormField<>(
                REFERENCE,
                LABEL,
                TYPE,
                VALUE,
                DIFFERENT_VALIDATOR
            )
        );
    }

    @Override
    public FormField<TestValidationReference> createObject() {
        return new FormField<>(
            REFERENCE,
            LABEL,
            TYPE,
            VALUE,
            VALIDATOR
        );
    }

    // json.............................................................................................................

    @Test
    public void testMarshallOnlyReference() {
        this.marshallAndCheck(
            FormField.with(REFERENCE),
            "{\n" +
                "  \"reference\": {\n" +
                "    \"type\": \"test-validation-reference\",\n" +
                "    \"value\": \"Hello\"\n" +
                "  }\n" +
                "}"
        );
    }

    @Test
    public void testMarshallAllProperties() {
        this.marshallAndCheck(
            this.createJsonNodeMarshallingValue(),
            "{\n" +
                "  \"reference\": {\n" +
                "    \"type\": \"test-validation-reference\",\n" +
                "    \"value\": \"Hello\"\n" +
                "  },\n" +
                "  \"label\": \"Label123\",\n" +
                "  \"type\": \"Type1\",\n" +
                "  \"value\": {\n" +
                "    \"type\": \"url\",\n" +
                "    \"value\": \"https://example.com\"\n" +
                "  },\n" +
                "  \"validator\": \"helloValidator\"\n" +
                "}"
        );
    }

    @Override
    public FormField<TestValidationReference> unmarshall(final JsonNode json,
                                                         final JsonNodeUnmarshallContext context) {
        return FormField.unmarshall(
            json,
            context
        );
    }

    @Override
    public FormField<TestValidationReference> createJsonNodeMarshallingValue() {
        return this.createObject();
    }

    // TreePrintable....................................................................................................

    @Test
    public void testTreePrintReference() {
        this.treePrintAndCheck(
            FormField.with(REFERENCE),
            "FormField\n" +
                "  Hello (walkingkooka.validation.TestValidationReference)\n"
        );
    }

    @Test
    public void testTreePrintAllProperties() {
        this.treePrintAndCheck(
            this.createObject(),
            "FormField\n" +
                "  Hello (walkingkooka.validation.TestValidationReference)\n" +
                "  label:\n" +
                "    \"Label123\"\n" +
                "  type:\n" +
                "    Type1\n" +
                "  value:\n" +
                "    https://example.com (walkingkooka.net.AbsoluteUrl)\n" +
                "  validator:\n" +
                "    helloValidator\n"
        );
    }

    // class............................................................................................................

    @Override
    public Class<FormField<TestValidationReference>> type() {
        return Cast.to(FormField.class);
    }
}
