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

package walkingkooka.validation.provider;

import org.junit.jupiter.api.Test;
import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.ToStringTesting;
import walkingkooka.reflect.ClassTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallingTesting;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class OptionalValidatorSelectorTest implements ClassTesting<OptionalValidatorSelector>,
    HashCodeEqualsDefinedTesting2<OptionalValidatorSelector>,
    ToStringTesting<OptionalValidatorSelector>,
    JsonNodeMarshallingTesting<OptionalValidatorSelector> {

    private final static String SELECTOR = "test-validator-123";

    // with.............................................................................................................

    @Test
    public void testWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> OptionalValidatorSelector.with(null)
        );
    }

    @Test
    public void testWithEmpty() {
        assertSame(
            OptionalValidatorSelector.EMPTY,
            OptionalValidatorSelector.with(
                Optional.empty()
            )
        );
    }

    @Test
    public void testWithNotEmpty() {
        final Optional<ValidatorSelector> selector = Optional.of(
            ValidatorSelector.parse(SELECTOR)
        );

        final OptionalValidatorSelector optional = OptionalValidatorSelector.with(selector);

        assertSame(
            selector,
            optional.value()
        );
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferent() {
        this.checkNotEquals(
            OptionalValidatorSelector.with(
                Optional.of(
                    ValidatorSelector.parse("different")
                )
            )
        );
    }

    @Override
    public OptionalValidatorSelector createObject() {
        return this.createJsonNodeMarshallingValue();
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        final String selector = "test-selector-123";

        this.toStringAndCheck(
            OptionalValidatorSelector.with(
                Optional.of(
                    ValidatorSelector.parse(selector)
                )
            ),
            selector
        );
    }

    @Test
    public void testToStringWithEmpty() {
        this.toStringAndCheck(
            OptionalValidatorSelector.EMPTY,
            ""
        );
    }

    // json..............................................................................................................

    @Test
    public void testJsonMarshallEmpty() {
        this.marshallAndCheck(
            OptionalValidatorSelector.EMPTY,
            JsonNode.nullNode()
        );
    }

    @Test
    public void testJsonMarshallNotEmpty() {
        this.marshallAndCheck(
            this.createJsonNodeMarshallingValue(),
            JsonNode.string(SELECTOR)
        );
    }

    @Test
    public void testJsonRoundtripEmpty() {
        this.marshallRoundTripTwiceAndCheck(
            OptionalValidatorSelector.EMPTY
        );
    }


    @Test
    public void testJsonRoundtripNotEmpty() {
        this.marshallRoundTripTwiceAndCheck(
            this.createJsonNodeMarshallingValue()
        );
    }

    @Override
    public OptionalValidatorSelector unmarshall(final JsonNode json,
                                                           final JsonNodeUnmarshallContext context) {
        return OptionalValidatorSelector.unmarshall(
            json,
            context
        );
    }

    @Override
    public OptionalValidatorSelector createJsonNodeMarshallingValue() {
        return OptionalValidatorSelector.with(
            Optional.of(
                ValidatorSelector.parse(SELECTOR)
            )
        );
    }

    // class............................................................................................................

    @Override
    public Class<OptionalValidatorSelector> type() {
        return OptionalValidatorSelector.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
