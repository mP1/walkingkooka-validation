/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
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
import walkingkooka.CanBeEmptyTesting;
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

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class OptionalValidationValueTypeNameTest implements ClassTesting<OptionalValidationValueTypeName>,
    CanBeEmptyTesting,
    HashCodeEqualsDefinedTesting2<OptionalValidationValueTypeName>,
    ToStringTesting<OptionalValidationValueTypeName>,
    JsonNodeMarshallingTesting<OptionalValidationValueTypeName>,
    TreePrintableTesting {

    // with.............................................................................................................

    @Test
    public void testWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> OptionalValidationValueTypeName.with(null)
        );
    }

    @Test
    public void testWithEmpty() {
        assertSame(
            OptionalValidationValueTypeName.EMPTY,
            OptionalValidationValueTypeName.with(
                Optional.empty()
            )
        );
    }

    @Test
    public void testWithNotEmpty() {
        final Optional<ValidationValueTypeName> value = Optional.of(
            ValidationValueTypeName.TEXT
        );

        final OptionalValidationValueTypeName optional = OptionalValidationValueTypeName.with(value);

        assertSame(
            value,
            optional.value()
        );
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferent() {
        this.checkNotEquals(
            OptionalValidationValueTypeName.with(
                Optional.of(
                    ValidationValueTypeName.BOOLEAN
                )
            )
        );
    }

    @Override
    public OptionalValidationValueTypeName createObject() {
        return this.createJsonNodeMarshallingValue();
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        final Optional<ValidationValueTypeName> value = Optional.of(
            ValidationValueTypeName.with("Hello123")
        );

        this.toStringAndCheck(
            OptionalValidationValueTypeName.with(value),
            value.toString()
        );
    }

    // CanBeEmpty.......................................................................................................

    @Test
    public void testCanBeEmptyWhenEmpty() {
        this.isEmptyAndCheck(
            OptionalValidationValueTypeName.EMPTY,
            true
        );
    }

    @Test
    public void testCanBeEmptyWhenNotEmpty() {
        this.isEmptyAndCheck(
            OptionalValidationValueTypeName.with(
                Optional.of(
                    ValidationValueTypeName.DATE
                )
            ),
            false
        );
    }

    // json..............................................................................................................

    @Test
    public void testJsonMarshallEmpty() {
        this.marshallAndCheck(
            OptionalValidationValueTypeName.EMPTY,
            JsonNode.nullNode()
        );
    }

    @Test
    public void testJsonMarshallNotEmpty() {
        this.marshallAndCheck(
            OptionalValidationValueTypeName.with(
                Optional.of(
                    ValidationValueTypeName.DATE
                )
            ),
                "\"date\""
        );
    }

    @Test
    public void testJsonRoundtripEmpty() {
        this.marshallRoundTripTwiceAndCheck(
            OptionalValidationValueTypeName.EMPTY
        );
    }

    @Test
    public void testJsonRoundtripValidationValueTypeName() {
        this.marshallRoundTripTwiceAndCheck(
            OptionalValidationValueTypeName.with(
                Optional.of(
                    ValidationValueTypeName.with("Hello123")
                )
            )
        );
    }

    @Override
    public OptionalValidationValueTypeName unmarshall(final JsonNode json,
                                                      final JsonNodeUnmarshallContext context) {
        return OptionalValidationValueTypeName.unmarshall(
            json,
            context
        );
    }

    @Override
    public OptionalValidationValueTypeName createJsonNodeMarshallingValue() {
        return OptionalValidationValueTypeName.with(
            Optional.of(
                ValidationValueTypeName.with("Hello123")
            )
        );
    }

    // TreePrintable....................................................................................................

    @Test
    public void testTreePrintableEmpty() {
        this.treePrintAndCheck(
            OptionalValidationValueTypeName.EMPTY,
            "OptionalValidationValueTypeName\n"
        );
    }

    @Test
    public void testTreePrintable() {
        this.treePrintAndCheck(
            OptionalValidationValueTypeName.with(
                Optional.of(
                    ValidationValueTypeName.with("Hello123")
                )
            ),
            "OptionalValidationValueTypeName\n" +
                "  Hello123\n"
        );
    }

    // class............................................................................................................

    @Override
    public Class<OptionalValidationValueTypeName> type() {
        return Cast.to(OptionalValidationValueTypeName.class);
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
