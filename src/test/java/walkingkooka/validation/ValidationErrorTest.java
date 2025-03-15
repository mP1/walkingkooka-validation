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
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY REFERENCE, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.validation;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.ToStringTesting;
import walkingkooka.reflect.ClassTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.text.CharSequences;
import walkingkooka.text.printer.TreePrintableTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallingTesting;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.validation.ValidationErrorTest.TestReference;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ValidationErrorTest implements HashCodeEqualsDefinedTesting2<ValidationError<TestReference>>,
    ToStringTesting<ValidationError<TestReference>>,
    ClassTesting<ValidationError<TestReference>>,
    TreePrintableTesting,
    JsonNodeMarshallingTesting<ValidationError<TestReference>> {

    static class TestReference implements ValidationReference {

        TestReference(final String field) {
            this.field = CharSequences.failIfNullOrEmpty(field, "field");
        }

        @Override
        public String text() {
            return this.field;
        }

        private final String field;

        @Override
        public int hashCode() {
            return this.field.hashCode();
        }

        @Override
        public boolean equals(final Object other) {
            return this ==other || other instanceof TestReference && this.field.equals(((TestReference) other).field);
        }

        @Override
        public String toString() {
            return this.field;
        }

        private JsonNode marshall(final JsonNodeMarshallContext context) {
            return JsonNode.string(this.field);
        }

        static TestReference unmarshall(final JsonNode node,
                                        final JsonNodeUnmarshallContext context) {
            return new TestReference(node.stringOrFail());
        }
    }

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(TestReference.class),
            TestReference::unmarshall,
            TestReference::marshall,
            TestReference.class
        );
    }

    private final static TestReference REFERENCE = new TestReference("Hello");

    private final static String MESSAGE = "Error too many xyz";

    private final Optional<Object> VALUE = Optional.of("Value999");

    // with.............................................................................................................

    @Test
    public void testWithNullReferenceFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValidationError.with(
                null,
                MESSAGE,
                VALUE
            )
        );
    }

    @Test
    public void testWithNullMessageFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValidationError.with(
                REFERENCE,
                null,
                VALUE
            )
        );
    }

    @Test
    public void testWithEmptyMessageFails() {
        assertThrows(
            IllegalArgumentException.class,
            () -> ValidationError.with(
                REFERENCE,
                "",
                VALUE
            )
        );
    }

    @Test
    public void testWithWhitespaceMessageFails() {
        assertThrows(
            IllegalArgumentException.class,
            () -> ValidationError.with(
                REFERENCE,
                " ",
                VALUE
            )
        );
    }

    @Test
    public void testWithNullValueFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValidationError.with(
                REFERENCE,
                MESSAGE,
                null
            )
        );
    }

    @Test
    public void testWith() {
        final ValidationError error = ValidationError.with(
            REFERENCE,
            MESSAGE,
            VALUE
        );

        this.checkEquals(
            REFERENCE,
            error.reference(),
            "reference"
        );
        this.checkEquals(
            MESSAGE,
            error.message(),
            "message"
        );
        this.checkEquals(
            VALUE,
            error.value(),
            "value"
        );
    }

    // class............................................................................................................

    @Test
    public void testEqualsDifferentReference() {
        this.checkNotEquals(
            ValidationError.with(
                new ValidationReference() {
                    @Override
                    public String text() {
                        return "different";
                    }
                },
                MESSAGE,
                VALUE
            )
        );
    }

    @Test
    public void testEqualsDifferentMessage() {
        this.checkNotEquals(
            ValidationError.with(
                REFERENCE,
                "different",
                VALUE
            )
        );
    }

    @Test
    public void testEqualsDifferentValue() {
        this.checkNotEquals(
            ValidationError.with(
                REFERENCE,
                MESSAGE,
                Optional.of("different")
            )
        );
    }

    @Override
    public ValidationError<TestReference> createObject() {
        return ValidationError.with(
            REFERENCE,
            MESSAGE,
            VALUE
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createObject(),
            "Hello \"Error too many xyz\" \"Value999\""
        );
    }

    // TreePrintable....................................................................................................

    @Test
    public void testTreePrintWithoutValue() {
        this.treePrintAndCheck(
            ValidationError.with(
                REFERENCE,
                MESSAGE,
                Optional.empty()
            ),
            "ValidationError\n" +
                "  HelloError too many xyz\n"
        );
    }

    @Test
    public void testTreePrintWithValue() {
        this.treePrintAndCheck(
            ValidationError.with(
                REFERENCE,
                MESSAGE,
                VALUE
            ),
            "ValidationError\n" +
                "  HelloError too many xyz\n" +
                "      Value999"
        );
    }

    // json.............................................................................................................

    @Test
    public void testUnmarshallNonObjectFails() {
        this.unmarshallFails(
            JsonNode.string("")
        );
    }

    @Test
    public void testUnmarshall() {
        this.unmarshallAndCheck(
            JsonNode.object()
                .set(ValidationError.REFERENCE_PROPERTY, this.marshallContext().marshallWithType(REFERENCE))
                .set(ValidationError.MESSAGE_PROPERTY, JsonNode.string(MESSAGE))
                .set(
                    ValidationError.VALUE_PROPERTY,
                    this.marshallContext()
                        .marshallWithType(VALUE.get())
                ),
            ValidationError.with(
                REFERENCE,
                MESSAGE,
                VALUE
            )
        );
    }

    @Test
    public void testMarshall() {
        this.marshallAndCheck(
            this.createObject(),
            JsonNode.object()
                .set(ValidationError.REFERENCE_PROPERTY, this.marshallContext().marshallWithType(REFERENCE))
                .set(ValidationError.MESSAGE_PROPERTY, JsonNode.string(MESSAGE))
                .set(
                    ValidationError.VALUE_PROPERTY,
                    this.marshallContext()
                        .marshallWithType(VALUE.get())
                )
        );
    }

    @Override
    public ValidationError<TestReference> unmarshall(final JsonNode json,
                                      final JsonNodeUnmarshallContext context) {
        return ValidationError.unmarshall(
            json,
            context
        );
    }

    @Override
    public ValidationError<TestReference> createJsonNodeMarshallingValue() {
        return this.createObject();
    }

    // class............................................................................................................

    @Override
    public Class<ValidationError<TestReference>> type() {
        return Cast.to(ValidationError.class);
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
