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

package walkingkooka.validation.form.provider;

import org.junit.jupiter.api.Test;
import walkingkooka.collect.set.Sets;
import walkingkooka.net.Url;
import walkingkooka.plugin.PluginInfoSetLikeTesting;
import walkingkooka.reflect.ClassTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import static org.junit.jupiter.api.Assertions.assertSame;

public final class FormHandlerInfoSetTest implements PluginInfoSetLikeTesting<FormHandlerName, FormHandlerInfo, FormHandlerInfoSet, FormHandlerSelector, FormHandlerAlias, FormHandlerAliasSet>,
    ClassTesting<FormHandlerInfoSet> {

    @Test
    public void testImmutableSet() {
        final FormHandlerInfoSet set = this.createSet();

        assertSame(
            set,
            Sets.immutable(set)
        );
    }

    // parse............................................................................................................

    @Override
    public void testParseStringEmptyFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public FormHandlerInfoSet parseString(final String text) {
        return FormHandlerInfoSet.parse(text);
    }

    // Set..............................................................................................................

    @Override
    public FormHandlerInfoSet createSet() {
        return FormHandlerInfoSet.with(
            Sets.of(
                this.info()
            )
        );
    }

    @Override
    public FormHandlerInfo info() {
        return FormHandlerInfo.with(
            Url.parseAbsolute("https://example.com/FormHandler123"),
            FormHandlerName.with("FormHandler123")
        );
    }

    // ImmutableSetTesting..............................................................................................

    @Override
    public void testSetElementsNullFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetElementsSame() {
        throw new UnsupportedOperationException();
    }

    // json.............................................................................................................

    @Test
    public void testMarshallEmpty() {
        this.marshallAndCheck(
            FormHandlerInfoSet.EMPTY,
            JsonNode.array()
        );
    }

    @Test
    public void testMarshallNotEmpty2() {
        this.marshallAndCheck(
            FormHandlerInfoSet.with(
                Sets.of(
                    FormHandlerInfo.with(
                        Url.parseAbsolute("https://example.com/test123"),
                        FormHandlerName.with("test123")
                    )
                )
            ),
            "[\n" +
                "  \"https://example.com/test123 test123\"\n" +
                "]"
        );
    }

    // json............................................................................................................

    @Override
    public FormHandlerInfoSet unmarshall(final JsonNode node,
                                                                          final JsonNodeUnmarshallContext context) {
        return FormHandlerInfoSet.unmarshall(
            node,
            context
        );
    }

    @Override
    public FormHandlerInfoSet createJsonNodeMarshallingValue() {
        return FormHandlerInfoSet.with(
            Sets.of(
                FormHandlerInfo.with(
                    Url.parseAbsolute("https://example.com/test111"),
                    FormHandlerName.with("test111")
                ),
                FormHandlerInfo.with(
                    Url.parseAbsolute("https://example.com/test222"),
                    FormHandlerName.with("test222")
                )
            )
        );
    }

    // Class............................................................................................................

    @Override
    public Class<FormHandlerInfoSet> type() {
        return FormHandlerInfoSet.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
