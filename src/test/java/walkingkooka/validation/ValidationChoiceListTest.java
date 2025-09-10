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
import walkingkooka.collect.list.ImmutableListTesting;
import walkingkooka.collect.list.ListTesting2;
import walkingkooka.collect.list.Lists;
import walkingkooka.reflect.ClassTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallingTesting;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidationChoiceListTest implements ListTesting2<ValidationChoiceList, ValidationChoice>,
    ClassTesting<ValidationChoiceList>,
    ImmutableListTesting<ValidationChoiceList, ValidationChoice>,
    JsonNodeMarshallingTesting<ValidationChoiceList> {

    private final static ValidationChoice CHOICE1 = ValidationChoice.with(
        "Label1",
        Optional.of(1)
    );

    private final static ValidationChoice CHOICE2 = ValidationChoice.with(
        "Label2",
        Optional.empty()
    );

    @Test
    public void testWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValidationChoiceList.with(null)
        );
    }

    @Test
    public void testWithDoesntDoubleWrap() {
        final ValidationChoiceList list = this.createList();
        assertSame(
            list,
            ValidationChoiceList.with(list)
        );
    }

    @Test
    public void testWithEmpty() {
        assertSame(
            ValidationChoiceList.EMPTY,
            ValidationChoiceList.with(
                Lists.empty()
            )
        );
    }

    // list.............................................................................................................

    @Test
    public void testGet() {
        this.getAndCheck(
            this.createList(),
            0, // index
            CHOICE1 // expected
        );
    }

    @Test
    public void testGet2() {
        this.getAndCheck(
            this.createList(),
            1, // index
            CHOICE2 // expected
        );
    }

    @Test
    public void testSetFails() {
        this.setFails(
            this.createList(),
            0, // index
            CHOICE1 // expected
        );
    }

    @Test
    public void testRemoveIndexFails() {
        final ValidationChoiceList list = this.createList();

        this.removeIndexFails(
            list,
            0
        );
    }

    @Test
    public void testRemoveElementFails() {
        final ValidationChoiceList list = this.createList();

        this.removeFails(
            list,
            list.get(0)
        );
    }

    @Test
    public void testSetElementsIncludesNullFails() {
        final NullPointerException thrown = assertThrows(
            NullPointerException.class,
            () -> this.createList()
                .setElements(
                    Lists.of(
                        CHOICE1,
                        null
                    )
                )
        );
        this.checkEquals(
            "includes null ValidationChoice",
            thrown.getMessage()
        );
    }

    @Override
    public ValidationChoiceList createList() {
        return ValidationChoiceList.with(
            Lists.of(
                CHOICE1,
                CHOICE2
            )
        );
    }

    // json.............................................................................................................

    @Test
    public void testMarshall() {
        this.marshallAndCheck(
            this.createList(),
            "[\n" +
                "  {\n" +
                "    \"label\": \"Label1\",\n" +
                "    \"value\": {\n" +
                "      \"type\": \"int\",\n" +
                "      \"value\": 1\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"label\": \"Label2\"\n" +
                "  }\n" +
                "]"
        );
    }

    @Override
    public ValidationChoiceList unmarshall(final JsonNode jsonNode,
                                           final JsonNodeUnmarshallContext context) {
        return ValidationChoiceList.unmarshall(
            jsonNode,
            context
        );
    }

    @Override
    public ValidationChoiceList createJsonNodeMarshallingValue() {
        return this.createList();
    }

    // class............................................................................................................

    @Override
    public Class<ValidationChoiceList> type() {
        return ValidationChoiceList.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}