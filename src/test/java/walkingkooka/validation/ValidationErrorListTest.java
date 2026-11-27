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
import walkingkooka.reflect.PublicClassTesting;
import walkingkooka.text.printer.TreePrintableTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallerTesting;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ValidationErrorListTest implements ImmutableListTesting<ValidationErrorList<TestValidationReference>, ValidationError<TestValidationReference>>,
    TreePrintableTesting,
    PublicClassTesting<ValidationErrorList<TestValidationReference>>,
    JsonNodeMarshallerTesting<ValidationErrorList<TestValidationReference>> {

    private final static ValidationError<TestValidationReference> ERROR1 = ValidationError.with(
        new TestValidationReference("Error111")
    );
    
    private final static ValidationError<TestValidationReference> ERROR2 = ValidationError.with(
        new TestValidationReference("Error222")
    );
    
    private final static ValidationError<TestValidationReference> ERROR3 = ValidationError.with(
        new TestValidationReference("Error333")
    );
    
    private final static ValidationError<TestValidationReference> ERROR4 = ValidationError.with(
        new TestValidationReference("Error444")
    );
    
    private final static ValidationError<TestValidationReference> ERROR5 = ValidationError.with(
        new TestValidationReference("Error555")
    );

    @Test
    public void testConcatErrorWithoutMessageAndWithoutValue() {
        this.getAndCheck(
            ValidationErrorList.<TestValidationReference>empty()
                .concat(ERROR1),
            0,
            ERROR1
        );
    }

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

    // subList..........................................................................................................

    private final static List<ValidationError<TestValidationReference>> ELEMENTS = Lists.of(
        ERROR1,
        ERROR2,
        ERROR3,
        ERROR4,
        ERROR5
    );

    @Test
    public void testSubList() {
        this.subListAndCheck(
            new ValidationErrorList(ELEMENTS),
            0,
            2,
            new ValidationErrorList(
                Lists.of(
                    ERROR1,
                    ERROR2
                )
            )
        );
    }

    @Test
    public void testSubList2() {
        this.subListAndCheck(
            new ValidationErrorList(ELEMENTS),
            2,
            4,
            new ValidationErrorList(
                Lists.of(
                    ERROR3,
                    ERROR4
                )
            )
        );
    }

    @Test
    public void testSubListSubList() {
        this.subListAndCheck(
            new ValidationErrorList(ELEMENTS)
                .subList(
                    1,
                    4
                ), // 2, 3, 4
            1,
            3,
            new ValidationErrorList(
                Lists.of(
                    ERROR3,
                    ERROR4
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
                "      \"type\": \"absolute-url\",\n" +
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

    // firstOrEmpty.....................................................................................................

    @Test
    public void testFirstOrEmptyWhenEmpty() {
        this.firstOrEmptyAndCheck(
            ValidationErrorList.empty()
        );
    }

    @Test
    public void testFirstOrEmptyWhenNotEmpty() {
        this.firstOrEmptyAndCheck(
            ValidationErrorList.<TestValidationReference>empty()
                .concat(ERROR1)
                .concat(ERROR2),
            ERROR1
        );
    }

    // Class............................................................................................................

    @Override
    public Class<ValidationErrorList<TestValidationReference>> type() {
        return Cast.to(ValidationErrorList.class);
    }
}
