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

public final class OptionalValueTypeNameTest implements ClassTesting<OptionalValueTypeName>,
    CanBeEmptyTesting,
    HashCodeEqualsDefinedTesting2<OptionalValueTypeName>,
    ToStringTesting<OptionalValueTypeName>,
    JsonNodeMarshallingTesting<OptionalValueTypeName>,
    TreePrintableTesting {

    // with.............................................................................................................

    @Test
    public void testWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> OptionalValueTypeName.with(null)
        );
    }

    @Test
    public void testWithEmpty() {
        assertSame(
            OptionalValueTypeName.EMPTY,
            OptionalValueTypeName.with(
                Optional.empty()
            )
        );
    }

    @Test
    public void testWithNotEmpty() {
        final Optional<ValueTypeName> value = Optional.of(
            ValueTypeName.TEXT
        );

        final OptionalValueTypeName optional = OptionalValueTypeName.with(value);

        assertSame(
            value,
            optional.value()
        );
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferent() {
        this.checkNotEquals(
            OptionalValueTypeName.with(
                Optional.of(
                    ValueTypeName.BOOLEAN
                )
            )
        );
    }

    @Override
    public OptionalValueTypeName createObject() {
        return this.createJsonNodeMarshallingValue();
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        final Optional<ValueTypeName> value = Optional.of(
            ValueTypeName.with("hello-123")
        );

        this.toStringAndCheck(
            OptionalValueTypeName.with(value),
            value.toString()
        );
    }

    // CanBeEmpty.......................................................................................................

    @Test
    public void testCanBeEmptyWhenEmpty() {
        this.isEmptyAndCheck(
            OptionalValueTypeName.EMPTY,
            true
        );
    }

    @Test
    public void testCanBeEmptyWhenNotEmpty() {
        this.isEmptyAndCheck(
            OptionalValueTypeName.with(
                Optional.of(
                    ValueTypeName.DATE
                )
            ),
            false
        );
    }

    // json..............................................................................................................

    @Test
    public void testJsonMarshallEmpty() {
        this.marshallAndCheck(
            OptionalValueTypeName.EMPTY,
            JsonNode.nullNode()
        );
    }

    @Test
    public void testJsonMarshallNotEmpty() {
        this.marshallAndCheck(
            OptionalValueTypeName.with(
                Optional.of(
                    ValueTypeName.DATE
                )
            ),
                "\"date\""
        );
    }

    @Test
    public void testJsonRoundtripEmpty() {
        this.marshallRoundTripTwiceAndCheck(
            OptionalValueTypeName.EMPTY
        );
    }

    @Test
    public void testJsonRoundtripValueTypeName() {
        this.marshallRoundTripTwiceAndCheck(
            OptionalValueTypeName.with(
                Optional.of(
                    ValueTypeName.with("hello-123")
                )
            )
        );
    }

    @Override
    public OptionalValueTypeName unmarshall(final JsonNode json,
                                            final JsonNodeUnmarshallContext context) {
        return OptionalValueTypeName.unmarshall(
            json,
            context
        );
    }

    @Override
    public OptionalValueTypeName createJsonNodeMarshallingValue() {
        return OptionalValueTypeName.with(
            Optional.of(
                ValueTypeName.with("hello-123")
            )
        );
    }

    // TreePrintable....................................................................................................

    @Test
    public void testTreePrintableEmpty() {
        this.treePrintAndCheck(
            OptionalValueTypeName.EMPTY,
            "OptionalValueTypeName\n"
        );
    }

    @Test
    public void testTreePrintable() {
        this.treePrintAndCheck(
            OptionalValueTypeName.with(
                Optional.of(
                    ValueTypeName.with("hello-123")
                )
            ),
            "OptionalValueTypeName\n" +
                "  hello-123\n"
        );
    }

    // class............................................................................................................

    @Override
    public Class<OptionalValueTypeName> type() {
        return Cast.to(OptionalValueTypeName.class);
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
