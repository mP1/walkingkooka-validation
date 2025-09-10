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
import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.ToStringTesting;
import walkingkooka.reflect.ClassTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallingTesting;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ValidationChoiceTest implements ClassTesting<ValidationChoice>,
    HashCodeEqualsDefinedTesting2<ValidationChoice>,
    JsonNodeMarshallingTesting<ValidationChoice>,
    ToStringTesting<ValidationChoice> {

    private final static String LABEL = "Label111";
    private final static Optional<Object> VALUE = Optional.of(222);

    @Test
    public void testWithNullLabelFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValidationChoice.with(
                null,
                VALUE
            )
        );
    }

    @Test
    public void testWithNullValueFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValidationChoice.with(
                LABEL,
                null
            )
        );
    }

    @Test
    public void testWith() {
        final ValidationChoice choice = ValidationChoice.with(
            LABEL,
            VALUE
        );

        this.checkEquals(
            LABEL,
            choice.label(),
            "label"
        );

        this.checkEquals(
            VALUE,
            choice.value(),
            "value"
        );
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferentLabel() {
        this.checkNotEquals(
            ValidationChoice.with(
                "Different",
                VALUE
            )
        );
    }

    @Test
    public void testEqualsDifferentValue() {
        this.checkNotEquals(
            ValidationChoice.with(
                LABEL,
                Optional.of("Different")
            )
        );
    }

    @Override
    public ValidationChoice createObject() {
        return ValidationChoice.with(
            LABEL,
            VALUE
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createObject(),
            "Label111=222"
        );
    }

    // json.............................................................................................................

    @Test
    public void testMarshall() {
        this.marshallAndCheck(
            this.createJsonNodeMarshallingValue(),
            "{\n" +
                "  \"label\": \"Label111\",\n" +
                "  \"value\": {\n" +
                "    \"type\": \"int\",\n" +
                "    \"value\": 222\n" +
                "  }\n" +
                "}"
        );
    }

    @Test
    public void testMarshallWithNoValue() {
        this.marshallAndCheck(
            ValidationChoice.with(
                LABEL,
                Optional.empty()
            ),
            "{\n" +
                "  \"label\": \"Label111\"\n" +
                "}"
        );
    }

    @Override
    public ValidationChoice unmarshall(final JsonNode json,
                                       final JsonNodeUnmarshallContext context) {
        return ValidationChoice.unmarshall(
            json,
            context
        );
    }

    @Override
    public ValidationChoice createJsonNodeMarshallingValue() {
        return this.createObject();
    }

    // class............................................................................................................

    @Override
    public Class<ValidationChoice> type() {
        return ValidationChoice.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
