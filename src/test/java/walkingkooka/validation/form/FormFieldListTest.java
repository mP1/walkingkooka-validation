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
import walkingkooka.collect.list.ImmutableListTesting;
import walkingkooka.collect.list.Lists;
import walkingkooka.net.Url;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.reflect.ClassTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.text.printer.TreePrintableTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallingTesting;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.ValueType;
import walkingkooka.validation.provider.ValidatorSelector;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;

public final class FormFieldListTest implements ImmutableListTesting<FormFieldList<TestValidationReference>, FormField<TestValidationReference>>,
    TreePrintableTesting,
    ClassTesting<FormFieldList<TestValidationReference>>,
    JsonNodeMarshallingTesting<FormFieldList<TestValidationReference>> {

    @Test
    public void testWithFieldsSharingValidationReference() {
        final TestValidationReference duplicate = new TestValidationReference("DuplicateFieldReference");

        FormFieldList.with(
            Lists.of(
                FormField.with(duplicate)
                    .setLabel("Label1"),
                FormField.with(duplicate)
                    .setLabel("Label2"),
                FormField.with(
                    new TestValidationReference("Field3")
                ).setLabel("Label3")
            )
        );
    }

    @Test
    public void testSetElementsDoesntDoubleWrap() {
        final FormFieldList<TestValidationReference> list = this.createList();

        assertSame(
            list,
            list.setElements(list)
        );
    }

    @Override
    public FormFieldList<TestValidationReference> createList() {
        return FormFieldList.<TestValidationReference>empty()
            .concat(
                FormField.with(
                        new TestValidationReference("Hello")
                    ).setLabel("Label111")
                    .setType(
                        Optional.of(
                            ValueType.with("type222")
                        )
                    ).setValue(
                        Optional.of("Value333")
                    ).setValidator(
                        Optional.of(
                            ValidatorSelector.parse("validator-4444")
                        )
                    )
            );
    }

    // Json.............................................................................................................

    @Test
    public void testMarshall() {
        this.marshallAndCheck(
            this.createJsonNodeMarshallingValue(),
            "[\n" +
                "  {\n" +
                "    \"reference\": {\n" +
                "      \"type\": \"test-validation-reference\",\n" +
                "      \"value\": \"Hello\"\n" +
                "    },\n" +
                "    \"label\": \"Label111\",\n" +
                "    \"type\": \"type222\",\n" +
                "    \"value\": \"Value333\",\n" +
                "    \"validator\": \"validator-4444\"\n" +
                "  }\n" +
                "]"
        );
    }

    @Test
    public void testUnmarshall() {
        this.unmarshallAndCheck(
            "[\n" +
                "  {\n" +
                "    \"reference\": {\n" +
                "      \"type\": \"test-validation-reference\",\n" +
                "      \"value\": \"Hello\"\n" +
                "    },\n" +
                "    \"label\": \"Label111\",\n" +
                "    \"type\": \"type222\",\n" +
                "    \"value\": \"Value333\",\n" +
                "    \"validator\": \"validator-4444\"\n" +
                "  }\n" +
                "]",
            this.createJsonNodeMarshallingValue()
        );
    }

    @Test
    public void testMarshallMixedFormFields() {
        this.marshallAndCheck(
            FormFieldList.<TestValidationReference>empty()
                .concat(
                    FormField.with(
                        new TestValidationReference("Hello1")
                    ).setValue(
                        Optional.of(
                            EmailAddress.parse("hello1@example.com")
                        )
                    )
                ).concat(
                    FormField.with(
                        new TestValidationReference("Hello2")
                    ).setLabel("Label2"
                    ).setType(
                        Optional.of(
                            ValueType.with("type222")
                        )
                    ).setValue(
                        Optional.of(
                            Url.parse("https://example.com/2")
                        )
                    )
                ),
            "[\n" +
                "  {\n" +
                "    \"reference\": {\n" +
                "      \"type\": \"test-validation-reference\",\n" +
                "      \"value\": \"Hello1\"\n" +
                "    },\n" +
                "    \"value\": {\n" +
                "      \"type\": \"email-address\",\n" +
                "      \"value\": \"hello1@example.com\"\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"reference\": {\n" +
                "      \"type\": \"test-validation-reference\",\n" +
                "      \"value\": \"Hello2\"\n" +
                "    },\n" +
                "    \"label\": \"Label2\",\n" +
                "    \"type\": \"type222\",\n" +
                "    \"value\": {\n" +
                "      \"type\": \"absolute-url\",\n" +
                "      \"value\": \"https://example.com/2\"\n" +
                "    }\n" +
                "  }\n" +
                "]"
        );
    }

    @Override
    public FormFieldList<TestValidationReference> unmarshall(final JsonNode json,
                                                             final JsonNodeUnmarshallContext context) {
        return FormFieldList.unmarshall(
            json,
            context
        );
    }

    @Override
    public FormFieldList<TestValidationReference> createJsonNodeMarshallingValue() {
        return this.createList();
    }

    // ClassTesting.....................................................................................................

    @Override
    public Class<FormFieldList<TestValidationReference>> type() {
        return Cast.to(FormFieldList.class);
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
