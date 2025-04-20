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
import walkingkooka.ToStringTesting;
import walkingkooka.collect.iterator.IteratorTesting;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.set.SortedSets;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.text.printer.TreePrintableTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallingTesting;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.ValidationError;
import walkingkooka.validation.ValidationErrorList;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class FormTest implements ClassTesting2<Form<TestValidationReference>>,
    HashCodeEqualsDefinedTesting2<Form<TestValidationReference>>,
    ToStringTesting<Form<TestValidationReference>>,
    JsonNodeMarshallingTesting<Form<TestValidationReference>>,
    TreePrintableTesting,
    IteratorTesting {

    private final static FormName NAME = FormName.with("name123");
    private final static FormName DIFFERENT_NAME = FormName.with("differentName234");

    private final static FormFieldList<TestValidationReference> FIELDS = FormFieldList.with(
        Lists.of(
            FormField.with(
                new TestValidationReference("Field111")
            )
        )
    );
    private final static FormFieldList<TestValidationReference> DIFFERENT_FIELDS = FormFieldList.with(
        Lists.of(
            FormField.with(
                new TestValidationReference("DifferentField222")
            )
        )
    );

    private final static ValidationErrorList<TestValidationReference> ERRORS = ValidationErrorList.with(
        Lists.of(
            ValidationError.with(
                new TestValidationReference("Field111"),
                "Error in Field111"
            )
        )
    );

    private final static ValidationErrorList<TestValidationReference> DIFFERENT_ERRORS = ValidationErrorList.with(
        Lists.of(
            ValidationError.with(
                new TestValidationReference("Field111"),
                "Different Error in Field111"
            )
        )
    );

    // with.............................................................................................................

    @Override
    public void testAllConstructorsVisibility() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testIfClassIsFinalIfAllConstructorsArePrivate() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void testWithNullNameFails() {
        assertThrows(
            NullPointerException.class,
            () -> Form.with(null)
        );
    }

    // setName..........................................................................................................

    @Test
    public void testSetNameWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createObject().setName(null)
        );
    }

    @Test
    public void testSetNameSame() {
        final Form<TestValidationReference> form = this.createObject()
            .setName(NAME);

        assertSame(
            form,
            form.setName(NAME)
        );
    }

    @Test
    public void testSetNameWithDifferent() {
        final Form<TestValidationReference> form = this.createObject();

        final Form<TestValidationReference> different = form.setName(DIFFERENT_NAME);

        assertNotSame(
            form,
            different
        );

        this.nameAndCheck(form);
        this.nameAndCheck(different, DIFFERENT_NAME);

        this.fieldsAndCheck(form);
        this.fieldsAndCheck(different);

        this.errorsAndCheck(form);
        this.errorsAndCheck(different);
    }

    private void nameAndCheck(final Form<TestValidationReference> form) {
        this.nameAndCheck(
            form,
            NAME
        );
    }

    private void nameAndCheck(final Form<TestValidationReference> form,
                              final FormName name) {
        this.checkEquals(
            name,
            form.name()
        );
    }

    // setFields........................................................................................................

    @Test
    public void testSetFieldsWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createObject().setFields(null)
        );
    }

    @Test
    public void testSetFieldsSame() {
        final Form<TestValidationReference> form = this.createObject();

        assertSame(
            form,
            form.setFields(FIELDS)
        );
    }

    @Test
    public void testSetFieldsWithDifferent() {
        final Form<TestValidationReference> form = this.createObject();

        final Form<TestValidationReference> different = form.setFields(DIFFERENT_FIELDS);

        assertNotSame(
            form,
            different
        );

        this.nameAndCheck(form);
        this.nameAndCheck(different);

        this.fieldsAndCheck(form);
        this.fieldsAndCheck(different, DIFFERENT_FIELDS);

        this.errorsAndCheck(form);
        this.errorsAndCheck(different);
    }

    private void fieldsAndCheck(final Form<TestValidationReference> form) {
        this.fieldsAndCheck(
            form,
            FIELDS
        );
    }

    private void fieldsAndCheck(final Form<TestValidationReference> form,
                                final List<FormField<TestValidationReference>> expected) {
        this.checkEquals(
            expected,
            form.fields()
        );
    }

    // setErrors........................................................................................................

    @Test
    public void testSetErrorsWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createObject().setErrors(null)
        );
    }

    @Test
    public void testSetErrorsSame() {
        final Form<TestValidationReference> form = this.createObject();

        assertSame(
            form,
            form.setErrors(ERRORS)
        );
    }

    @Test
    public void testSetErrorsWithDifferent() {
        final Form<TestValidationReference> form = this.createObject();

        final Form<TestValidationReference> different = form.setErrors(DIFFERENT_ERRORS);

        assertNotSame(
            form,
            different
        );

        this.nameAndCheck(form);
        this.nameAndCheck(different);

        this.fieldsAndCheck(form);
        this.fieldsAndCheck(different);

        this.errorsAndCheck(form);
        this.errorsAndCheck(different, DIFFERENT_ERRORS);
    }

    @Test
    public void testClearErrorsWhenErrorsPresent() {
        final Form<TestValidationReference> form = this.createObject();

        final Form<TestValidationReference> different = form.clearErrors();

        assertNotSame(
            form,
            different
        );

        this.nameAndCheck(form);
        this.nameAndCheck(different);

        this.fieldsAndCheck(form);
        this.fieldsAndCheck(different);

        this.errorsAndCheck(form);
        this.errorsAndCheck(
            different,
            ValidationErrorList.empty()
        );
    }

    private void errorsAndCheck(final Form<TestValidationReference> form) {
        this.errorsAndCheck(
            form,
            ERRORS
        );
    }

    private void errorsAndCheck(final Form<TestValidationReference> form,
                                final List<ValidationError<TestValidationReference>> expected) {
        this.checkEquals(
            expected,
            form.errors()
        );
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferentName() {
        this.checkNotEquals(
            this.createObject()
                .setName(DIFFERENT_NAME)
        );
    }

    @Test
    public void testEqualsDifferentFields() {
        this.checkNotEquals(
            this.createObject()
                .setFields(DIFFERENT_FIELDS)
        );
    }

    @Test
    public void testEqualsDifferentErrors() {
        this.checkNotEquals(
            this.createObject()
                .setErrors(DIFFERENT_ERRORS)
        );
    }

    @Override
    public Form<TestValidationReference> createObject() {
        return new Form<>(
            NAME,
            FIELDS,
            ERRORS
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToStringName() {
        this.toStringAndCheck(
            Form.with(NAME),
            "name123"
        );
    }

    @Test
    public void testToStringNameFieldsErrors() {
        this.toStringAndCheck(
            Form.<TestValidationReference>with(NAME)
                .setFields(FIELDS)
                .setErrors(ERRORS),
            "name123 fields=Field111 errors=Field111 \"Error in Field111\""
        );
    }

    // json.............................................................................................................

    @Test
    public void testMarshallEmptyForm() {
        this.marshallAndCheck(
            Form.with(NAME),
            "{\"name\": \"name123\"}"
        );
    }

    @Test
    public void testUnmarshallEmptyForm() {
        this.unmarshallAndCheck(
            "{\"name\": \"name123\"}",
            Form.with(NAME)
        );
    }

    @Override
    public Form<TestValidationReference> unmarshall(final JsonNode json,
                                                    final JsonNodeUnmarshallContext context) {
        return Form.unmarshall(json, context);
    }

    @Override
    public Form<TestValidationReference> createJsonNodeMarshallingValue() {
        return this.createObject();
    }

    // TreePrintable....................................................................................................

    @Test
    public void testTreePrintWithName() {
        this.treePrintAndCheck(
            Form.with(NAME),
            "Form\n" +
                "  name123\n"
        );
    }

    @Test
    public void testTreePrintAllProperties() {
        this.treePrintAndCheck(
            this.createObject(),
            "Form\n" +
                "  name123\n" +
                "  fields:\n" +
                "    FormField\n" +
                "      Field111 (walkingkooka.validation.TestValidationReference)\n" +
                "  errors:\n" +
                "    ValidationError\n" +
                "      Field111 (walkingkooka.validation.TestValidationReference)\n" +
                "        Error in Field111\n"
        );
    }

    // nameComparator...................................................................................................

    @Test
    public void testComparator() {
        final Form<TestValidationReference> form1 = Form.with(FormName.with("Form111"));
        final Form<TestValidationReference> form2 = Form.with(FormName.with("Form222"));
        final Form<TestValidationReference> form3 = Form.with(FormName.with("Form333"));

        final Set<Form<TestValidationReference>> forms = SortedSets.tree(
            Form.nameComparator()
        );
        forms.add(form1);
        forms.add(form3);
        forms.add(form2);

        this.iterateAndCheck(
            forms.iterator(),
            form1,
            form2,
            form3
        );
    }

    // class............................................................................................................

    @Override
    public Class<Form<TestValidationReference>> type() {
        return Cast.to(Form.class);
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
