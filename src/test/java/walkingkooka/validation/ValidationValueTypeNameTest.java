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
import walkingkooka.net.HasUrlFragmentTesting;
import walkingkooka.net.UrlFragment;
import walkingkooka.plugin.PluginNameTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContexts;

import static org.junit.jupiter.api.Assertions.assertSame;

final public class ValidationValueTypeNameTest implements PluginNameTesting<ValidationValueTypeName>,
    HasUrlFragmentTesting {

    // json.............................................................................................................

    @Test
    public void testUnmarshallBoolean() {
        this.unmarshallAndCheck2(
            ValidationValueTypeName.BOOLEAN_STRING,
            ValidationValueTypeName.BOOLEAN
        );
    }

    @Test
    public void testUnmarshallDate() {
        this.unmarshallAndCheck2(
            ValidationValueTypeName.DATE_STRING,
            ValidationValueTypeName.DATE
        );
    }

    @Test
    public void testUnmarshallDateTime() {
        this.unmarshallAndCheck2(
            ValidationValueTypeName.DATE_TIME_STRING,
            ValidationValueTypeName.DATE_TIME
        );
    }

    @Test
    public void testUnmarshallNumber() {
        this.unmarshallAndCheck2(
            ValidationValueTypeName.NUMBER_STRING,
            ValidationValueTypeName.NUMBER
        );
    }

    @Test
    public void testUnmarshallText() {
        this.unmarshallAndCheck2(
            ValidationValueTypeName.TEXT_STRING,
            ValidationValueTypeName.TEXT
        );
    }

    @Test
    public void testUnmarshallTime() {
        this.unmarshallAndCheck2(
            ValidationValueTypeName.TIME_STRING,
            ValidationValueTypeName.TIME
        );
    }


    private void unmarshallAndCheck2(final String string,
                                     final ValidationValueTypeName expected) {
        assertSame(
            expected,
            ValidationValueTypeName.unmarshall(
                JsonNode.string(string),
                JsonNodeUnmarshallContexts.fake()
            )
        );
    }
    
    @Override
    public ValidationValueTypeName createName(final String name) {
        return ValidationValueTypeName.with(name);
    }

    @Override
    public Class<ValidationValueTypeName> type() {
        return ValidationValueTypeName.class;
    }

    @Override
    public ValidationValueTypeName unmarshall(final JsonNode from,
                                              final JsonNodeUnmarshallContext context) {
        return ValidationValueTypeName.unmarshall(
            from,
            context
        );
    }

    // HasUrlFragment...................................................................................................

    @Test
    public void testUrlFragment() {
        this.urlFragmentAndCheck(
            ValidationValueTypeName.TEXT,
            UrlFragment.with("text")
        );
    }
}
