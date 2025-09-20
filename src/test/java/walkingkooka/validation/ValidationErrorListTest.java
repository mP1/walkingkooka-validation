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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ValidationErrorListTest implements ImmutableListTesting<ValidationErrorList<TestValidationReference>, ValidationError<TestValidationReference>>,
    TreePrintableTesting,
    ClassTesting<ValidationErrorList<TestValidationReference>>,
    JsonNodeMarshallingTesting<ValidationErrorList<TestValidationReference>> {

    @Test
    public void testSetElementsDoesntDoubleWrap() {
        final ValidationErrorList<TestValidationReference> list = this.createList();

        assertSame(
            list,
            list.setElements(list)
        );
    }

    @Test
    public void testSetElementsIncludesNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createList()
                .setElements(
                    Lists.of(
                        null
                    )
                )
        );
    }

    @Test
    public void testSetElementsIncludesNonValidationErrorElementFails() {
        assertThrows(
            RuntimeException.class,
            () -> this.createList()
                .setElements(
                    Cast.to(
                        Lists.of(
                            "Not a ValidationError!"
                        )
                    )
                )
        );
    }

    @Override
    public ValidationErrorList<TestValidationReference> createList() {
        return ValidationErrorList.<TestValidationReference>empty()
            .concat(
                ValidationError.with(
                    new TestValidationReference("Hello")
                ).setMessage("Something went wrong 123")
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
                "    \"message\": \"Something went wrong 123\"\n" +
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
                "    \"message\": \"Something went wrong 123\"\n" +
                "  }\n" +
                "]",
            this.createJsonNodeMarshallingValue()
        );
    }

    @Test
    public void testMarshallWithValueComplexType() {
        this.marshallAndCheck(
            ValidationErrorList.<TestValidationReference>empty()
                .concat(
                    ValidationError.with(
                            new TestValidationReference("Hello")
                        ).setMessage("Something went wrong 123")
                        .setValue(
                            Optional.of(
                                EmailAddress.parse("hello@example.com")
                            )
                        )
                ).concat(
                    ValidationError.with(
                            new TestValidationReference("Hello")
                        ).setMessage("Something went wrong 123")
                        .setValue(
                            Optional.of(
                                Url.parse("https://example.com")
                            )
                        )
                ),
            "[\n" +
                "  {\n" +
                "    \"reference\": {\n" +
                "      \"type\": \"test-validation-reference\",\n" +
                "      \"value\": \"Hello\"\n" +
                "    },\n" +
                "    \"message\": \"Something went wrong 123\",\n" +
                "    \"value\": {\n" +
                "      \"type\": \"email-address\",\n" +
                "      \"value\": \"hello@example.com\"\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"reference\": {\n" +
                "      \"type\": \"test-validation-reference\",\n" +
                "      \"value\": \"Hello\"\n" +
                "    },\n" +
                "    \"message\": \"Something went wrong 123\",\n" +
                "    \"value\": {\n" +
                "      \"type\": \"url\",\n" +
                "      \"value\": \"https://example.com\"\n" +
                "    }\n" +
                "  }\n" +
                "]"
        );
    }

    @Override
    public ValidationErrorList<TestValidationReference> unmarshall(final JsonNode json,
                                                                   final JsonNodeUnmarshallContext context) {
        return ValidationErrorList.unmarshall(
            json,
            context
        );
    }

    @Override
    public ValidationErrorList<TestValidationReference> createJsonNodeMarshallingValue() {
        return this.createList();
    }

    // ClassTesting.....................................................................................................

    @Override
    public Class<ValidationErrorList<TestValidationReference>> type() {
        return Cast.to(ValidationErrorList.class);
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
