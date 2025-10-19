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
import walkingkooka.net.Url;
import walkingkooka.reflect.ClassTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallingTesting;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ValidationCheckboxTest implements ClassTesting<ValidationCheckbox>,
    HashCodeEqualsDefinedTesting2<ValidationCheckbox>,
    JsonNodeMarshallingTesting<ValidationCheckbox>,
    ToStringTesting<ValidationCheckbox> {

    private final static Optional<Object> TRUE = Optional.of("true111");
    private final static Optional<Object> FALSE = Optional.of("false222");

    @Test
    public void testWithNullTrueValueFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValidationCheckbox.with(
                null,
                FALSE
            )
        );
    }

    @Test
    public void testWithNullFalseValueFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValidationCheckbox.with(
                TRUE,
                null
            )
        );
    }

    @Test
    public void testWith() {
        final ValidationCheckbox checkbox = ValidationCheckbox.with(
            TRUE,
            FALSE
        );

        this.checkEquals(
            TRUE,
            checkbox.trueValue(),
            "trueValue"
        );

        this.checkEquals(
            FALSE,
            checkbox.falseValue(),
            "falseValue"
        );
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferentTrueValue() {
        this.checkNotEquals(
            ValidationCheckbox.with(
                Optional.of("Different"),
                FALSE
            )
        );
    }

    @Test
    public void testEqualsDifferentFalseValue() {
        this.checkNotEquals(
            ValidationCheckbox.with(
                TRUE,
                Optional.of("Different")
            )
        );
    }

    @Override
    public ValidationCheckbox createObject() {
        return ValidationCheckbox.with(
            TRUE,
            FALSE
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createObject(),
            "\"true111\", \"false222\""
        );
    }

    // json.............................................................................................................

    @Test
    public void testMarshall() {
        this.marshallAndCheck(
            this.createJsonNodeMarshallingValue(),
            "{\n" +
                "  \"true\": \"true111\",\n" +
                "  \"false\": \"false222\"\n" +
                "}"
        );
    }

    @Test
    public void testMarshall2() {
        this.marshallAndCheck(
            ValidationCheckbox.with(
                Optional.of(
                    LocalDate.of(
                        1999,
                        12,
                        31
                    )
                ),
                Optional.of(
                    Url.parse("https://example.com")
                )
            ),
            "{\n" +
                "  \"true\": {\n" +
                "    \"type\": \"local-date\",\n" +
                "    \"value\": \"1999-12-31\"\n" +
                "  },\n" +
                "  \"false\": {\n" +
                "    \"type\": \"url\",\n" +
                "    \"value\": \"https://example.com\"\n" +
                "  }\n" +
                "}"
        );
    }

    @Override
    public ValidationCheckbox unmarshall(final JsonNode json,
                                         final JsonNodeUnmarshallContext context) {
        return ValidationCheckbox.unmarshall(
            json,
            context
        );
    }

    @Override
    public ValidationCheckbox createJsonNodeMarshallingValue() {
        return this.createObject();
    }

    // class............................................................................................................

    @Override
    public Class<ValidationCheckbox> type() {
        return ValidationCheckbox.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
