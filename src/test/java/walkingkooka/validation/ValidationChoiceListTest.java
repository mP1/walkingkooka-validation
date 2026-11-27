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
import walkingkooka.reflect.PublicClassTesting;
import walkingkooka.reflect.ThrowableTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallerTesting;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidationChoiceListTest implements ListTesting2<ValidationChoiceList, ValidationChoice>,
    PublicClassTesting<ValidationChoiceList>,
    ImmutableListTesting<ValidationChoiceList, ValidationChoice>,
    JsonNodeMarshallerTesting<ValidationChoiceList>,
    ThrowableTesting {

    private final static ValidationChoice CHOICE1 = ValidationChoice.with(
        "Label111",
        Optional.of(111)
    );

    private final static ValidationChoice CHOICE2 = ValidationChoice.with(
        "Label222",
        Optional.empty()
    );

    private final static ValidationChoice CHOICE3 = ValidationChoice.with(
        "Label333",
        Optional.of(333)
    );

    private final static ValidationChoice CHOICE4 = ValidationChoice.with(
        "Label444",
        Optional.of(444)
    );

    private final static ValidationChoice CHOICE5 = ValidationChoice.with(
        "Label555",
        Optional.of(555)
    );

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

    // setElements......................................................................................................

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
        this.getMessageAndCheck(
            thrown,
            "Includes null ValidationChoice"
        );
    }

    @Test
    public void testSetElementsDoesntDoubleWrap() {
        final ValidationChoiceList list = this.createList();
        assertSame(
            list,
            list.setElements(list)
        );
    }

    @Test
    public void testSetElementsWithEmpty() {
        assertSame(
            ValidationChoiceList.EMPTY,
            new ValidationChoiceList(
                Lists.of(
                    CHOICE1
                )
            ).setElements(Lists.empty())
        );
    }

    // subList..........................................................................................................

    private final static List<ValidationChoice> ELEMENTS = Lists.of(
        CHOICE1,
        CHOICE2,
        CHOICE3,
        CHOICE4,
        CHOICE5
    );

    @Test
    public void testSubList() {
        this.subListAndCheck(
            new ValidationChoiceList(ELEMENTS),
            0,
            2,
            new ValidationChoiceList(
                Lists.of(
                    CHOICE1,
                    CHOICE2
                )
            )
        );
    }

    @Test
    public void testSubList2() {
        this.subListAndCheck(
            new ValidationChoiceList(ELEMENTS),
            2,
            4,
            new ValidationChoiceList(
                Lists.of(
                    CHOICE3,
                    CHOICE4
                )
            )
        );
    }

    @Test
    public void testSubListSubList() {
        this.subListAndCheck(
            new ValidationChoiceList(ELEMENTS)
                .subList(
                    1,
                    4
                ), // 2, 3, 4
            1,
            3,
            new ValidationChoiceList(
                Lists.of(
                    CHOICE3,
                    CHOICE4
                )
            )
        );
    }

    @Override
    public ValidationChoiceList createList() {
        return new ValidationChoiceList(
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
                "    \"label\": \"Label111\",\n" +
                "    \"value\": {\n" +
                "      \"type\": \"int\",\n" +
                "      \"value\": 111\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"label\": \"Label222\"\n" +
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

    // firstOrEmpty.....................................................................................................

    @Test
    public void testFirstOrEmptyWhenEmpty() {
        this.firstOrEmptyAndCheck(
            ValidationChoiceList.EMPTY
        );
    }

    @Test
    public void testFirstOrEmptyWhenNotEmpty() {
        this.firstOrEmptyAndCheck(
            ValidationChoiceList.EMPTY
                .concat(
                    CHOICE1
                ).concat(
                    CHOICE2
                ),
            CHOICE1
        );
    }

    // class............................................................................................................

    @Override
    public Class<ValidationChoiceList> type() {
        return ValidationChoiceList.class;
    }
}