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
import walkingkooka.plugin.PluginNameTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContexts;

import static org.junit.jupiter.api.Assertions.assertSame;

final public class ValueTypeNameTest implements PluginNameTesting<ValueTypeName> {

    // name.............................................................................................................

    @Override
    public ValueTypeName createName(final String name) {
        return ValueTypeName.with(name);
    }

    // json.............................................................................................................

    @Test
    public void testUnmarshallAny() {
        this.unmarshallAndCheck2(
            ValueTypeName.ANY_STRING,
            ValueTypeName.ANY
        );
    }

    @Test
    public void testUnmarshallBoolean() {
        this.unmarshallAndCheck2(
            ValueTypeName.BOOLEAN_STRING,
            ValueTypeName.BOOLEAN
        );
    }

    @Test
    public void testUnmarshallDate() {
        this.unmarshallAndCheck2(
            ValueTypeName.DATE_STRING,
            ValueTypeName.DATE
        );
    }

    @Test
    public void testUnmarshallDateTime() {
        this.unmarshallAndCheck2(
            ValueTypeName.DATE_TIME_STRING,
            ValueTypeName.DATE_TIME
        );
    }

    @Test
    public void testUnmarshallNumber() {
        this.unmarshallAndCheck2(
            ValueTypeName.NUMBER_STRING,
            ValueTypeName.NUMBER
        );
    }

    @Test
    public void testUnmarshallText() {
        this.unmarshallAndCheck2(
            ValueTypeName.TEXT_STRING,
            ValueTypeName.TEXT
        );
    }

    @Test
    public void testUnmarshallTime() {
        this.unmarshallAndCheck2(
            ValueTypeName.TIME_STRING,
            ValueTypeName.TIME
        );
    }

    @Test
    public void testUnmarshallWholeNumber() {
        this.unmarshallAndCheck2(
            ValueTypeName.WHOLE_NUMBER_STRING,
            ValueTypeName.WHOLE_NUMBER
        );
    }

    private void unmarshallAndCheck2(final String string,
                                     final ValueTypeName expected) {
        assertSame(
            expected,
            ValueTypeName.unmarshall(
                JsonNode.string(string),
                JsonNodeUnmarshallContexts.fake()
            )
        );
    }

    @Override
    public ValueTypeName unmarshall(final JsonNode from,
                                    final JsonNodeUnmarshallContext context) {
        return ValueTypeName.unmarshall(
            from,
            context
        );
    }

    // class............................................................................................................

    @Override
    public Class<ValueTypeName> type() {
        return ValueTypeName.class;
    }
}
