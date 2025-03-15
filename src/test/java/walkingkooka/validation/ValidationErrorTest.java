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
import walkingkooka.text.printer.TreePrintableTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallingTesting;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ValidationErrorTest implements HashCodeEqualsDefinedTesting2<ValidationError<TestValidationReference>>,
    ToStringTesting<ValidationError<TestValidationReference>>,
    ClassTesting<ValidationError<TestValidationReference>>,
    TreePrintableTesting,
    JsonNodeMarshallingTesting<ValidationError<TestValidationReference>> {

    private final static TestValidationReference REFERENCE = new TestValidationReference("Hello");

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
        final ValidationError<TestValidationReference> error = ValidationError.with(
            REFERENCE,
            MESSAGE,
            VALUE
        );

        this.referenceAndCheck(error);
        this.messageAndCheck(error);
        this.valueAndCheck(error);
    }

    // setValue.........................................................................................................

    @Test
    public void testSetValueWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createObject().setValue(null)
        );
    }

    @Test
    public void testSetValueSame() {
        final ValidationError<TestValidationReference> error = this.createObject();

        assertSame(
            error,
            error.setValue(VALUE)
        );
    }

    @Test
    public void testSetValueWithDifferent() {
        final ValidationError<TestValidationReference> error = this.createObject();

        final Optional<Object> differentValue = Optional.of("Different");
        final ValidationError<TestValidationReference> different = error.setValue(differentValue);

        assertNotSame(
            error,
            different
        );

        this.referenceAndCheck(error);
        this.referenceAndCheck(different);

        this.messageAndCheck(error);
        this.messageAndCheck(different);

        this.valueAndCheck(error);
        this.valueAndCheck(
            different,
            differentValue
        );
    }

    // clearValue.......................................................................................................

    @Test
    public void testClearValueWhenNotEmpty() {
        final ValidationError<TestValidationReference> error = this.createObject();

        final ValidationError<TestValidationReference> different = error.clearValue();

        assertNotSame(
            error,
            different
        );

        this.referenceAndCheck(error);
        this.referenceAndCheck(different);

        this.messageAndCheck(error);
        this.messageAndCheck(different);

        this.valueAndCheck(error);
        this.valueAndCheck(
            different,
            Optional.empty()
        );

        assertSame(
            different,
            different.clearValue()
        );
    }

    // helper...........................................................................................................

    private void referenceAndCheck(final ValidationError<TestValidationReference> error) {
        this.referenceAndCheck(
            error,
            REFERENCE
        );
    }
    
    private void referenceAndCheck(final ValidationError<TestValidationReference> error,
                                   final TestValidationReference expected) {
        this.checkEquals(
            expected,
            error.reference(),
            error::toString
        );
    }

    private void messageAndCheck(final ValidationError<TestValidationReference> error) {
        this.messageAndCheck(
            error,
            MESSAGE
        );
    }

    private void messageAndCheck(final ValidationError<TestValidationReference> error,
                                 final String expected) {
        this.checkEquals(
            expected,
            error.message(),
            error::toString
        );
    }

    private void valueAndCheck(final ValidationError<TestValidationReference> error) {
        this.valueAndCheck(
            error,
            VALUE
        );
    }

    private void valueAndCheck(final ValidationError<TestValidationReference> error,
                               final Optional<Object> expected) {
        this.checkEquals(
            expected,
            error.value(),
            error::toString
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
    public ValidationError<TestValidationReference> createObject() {
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
    public ValidationError<TestValidationReference> unmarshall(final JsonNode json,
                                                               final JsonNodeUnmarshallContext context) {
        return ValidationError.unmarshall(
            json,
            context
        );
    }

    @Override
    public ValidationError<TestValidationReference> createJsonNodeMarshallingValue() {
        return this.createObject();
    }

    // class............................................................................................................

    @Override
    public Class<ValidationError<TestValidationReference>> type() {
        return Cast.to(ValidationError.class);
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
