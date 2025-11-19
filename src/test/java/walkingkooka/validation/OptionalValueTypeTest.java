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

public final class OptionalValueTypeTest implements ClassTesting<OptionalValueType>,
    CanBeEmptyTesting,
    HashCodeEqualsDefinedTesting2<OptionalValueType>,
    ToStringTesting<OptionalValueType>,
    JsonNodeMarshallingTesting<OptionalValueType>,
    TreePrintableTesting {

    // with.............................................................................................................

    @Test
    public void testWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> OptionalValueType.with(null)
        );
    }

    @Test
    public void testWithEmpty() {
        assertSame(
            OptionalValueType.EMPTY,
            OptionalValueType.with(
                Optional.empty()
            )
        );
    }

    @Test
    public void testWithNotEmpty() {
        final Optional<ValueType> value = Optional.of(
            ValueType.TEXT
        );

        final OptionalValueType optional = OptionalValueType.with(value);

        assertSame(
            value,
            optional.value()
        );
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferent() {
        this.checkNotEquals(
            OptionalValueType.with(
                Optional.of(
                    ValueType.BOOLEAN
                )
            )
        );
    }

    @Override
    public OptionalValueType createObject() {
        return this.createJsonNodeMarshallingValue();
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        final Optional<ValueType> value = Optional.of(
            ValueType.with("hello-123")
        );

        this.toStringAndCheck(
            OptionalValueType.with(value),
            value.toString()
        );
    }

    // CanBeEmpty.......................................................................................................

    @Test
    public void testCanBeEmptyWhenEmpty() {
        this.isEmptyAndCheck(
            OptionalValueType.EMPTY,
            true
        );
    }

    @Test
    public void testCanBeEmptyWhenNotEmpty() {
        this.isEmptyAndCheck(
            OptionalValueType.with(
                Optional.of(
                    ValueType.DATE
                )
            ),
            false
        );
    }

    // json..............................................................................................................

    @Test
    public void testJsonMarshallEmpty() {
        this.marshallAndCheck(
            OptionalValueType.EMPTY,
            JsonNode.nullNode()
        );
    }

    @Test
    public void testJsonMarshallNotEmpty() {
        this.marshallAndCheck(
            OptionalValueType.with(
                Optional.of(
                    ValueType.DATE
                )
            ),
                "\"date\""
        );
    }

    @Test
    public void testJsonRoundtripEmpty() {
        this.marshallRoundTripTwiceAndCheck(
            OptionalValueType.EMPTY
        );
    }

    @Test
    public void testJsonRoundtripValueTypeName() {
        this.marshallRoundTripTwiceAndCheck(
            OptionalValueType.with(
                Optional.of(
                    ValueType.with("hello-123")
                )
            )
        );
    }

    @Override
    public OptionalValueType unmarshall(final JsonNode json,
                                        final JsonNodeUnmarshallContext context) {
        return OptionalValueType.unmarshall(
            json,
            context
        );
    }

    @Override
    public OptionalValueType createJsonNodeMarshallingValue() {
        return OptionalValueType.with(
            Optional.of(
                ValueType.with("hello-123")
            )
        );
    }

    // TreePrintable....................................................................................................

    @Test
    public void testTreePrintableEmpty() {
        this.treePrintAndCheck(
            OptionalValueType.EMPTY,
            "OptionalValueType\n"
        );
    }

    @Test
    public void testTreePrintable() {
        this.treePrintAndCheck(
            OptionalValueType.with(
                Optional.of(
                    ValueType.with("hello-123")
                )
            ),
            "OptionalValueType\n" +
                "  hello-123\n"
        );
    }

    // class............................................................................................................

    @Override
    public Class<OptionalValueType> type() {
        return Cast.to(OptionalValueType.class);
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
