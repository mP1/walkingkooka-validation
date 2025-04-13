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
import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.ToStringTesting;
import walkingkooka.collect.set.SortedSets;
import walkingkooka.plugin.PluginAliasSetLikeTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallingTesting;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class FormHandlerAliasSetTest implements PluginAliasSetLikeTesting<FormHandlerName,
    FormHandlerInfo,
    FormHandlerInfoSet,
    FormHandlerSelector,
    FormHandlerAlias,
    FormHandlerAliasSet>,
    HashCodeEqualsDefinedTesting2<FormHandlerAliasSet>,
    ToStringTesting<FormHandlerAliasSet>,
    JsonNodeMarshallingTesting<FormHandlerAliasSet> {

    // with.............................................................................................................

    @Test
    public void testWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> FormHandlerAliasSet.with(null)
        );
    }

    @Test
    public void testWithEmpty() {
        assertSame(
            FormHandlerAliasSet.EMPTY,
            FormHandlerAliasSet.with(SortedSets.empty())
        );
    }

    // name.............................................................................................................

    @Test
    public void testAliasOrNameWithName() {
        final FormHandlerName abc = FormHandlerName.with("abc");

        this.aliasOrNameAndCheck(
            this.createSet(),
            abc,
            abc
        );
    }

    @Test
    public void testAliasOrNameWithAlias() {
        this.aliasOrNameAndCheck(
            this.createSet(),
            FormHandlerName.with("sunshine-alias"),
            FormHandlerName.with("sunshine")
        );
    }

    @Test
    public void testAliasSelectorWithName() {
        this.aliasSelectorAndCheck(
            this.createSet(),
            FormHandlerName.with("abc")
        );
    }

    @Test
    public void testAliasSelectorWithAlias() {
        this.aliasSelectorAndCheck(
            this.createSet(),
            FormHandlerName.with("custom-alias"),
            FormHandlerSelector.parse("custom(1)")
        );
    }

    @Override
    public FormHandlerAliasSet createSet() {
        return FormHandlerAliasSet.parse("abc, moo, mars, custom-alias custom(1) https://example.com/custom , sunshine-alias sunshine");
    }

    // parse............................................................................................................

    @Override
    public FormHandlerAliasSet parseString(final String text) {
        return FormHandlerAliasSet.parse(text);
    }

    // equals...........................................................................................................

    @Test
    public void testEqualsDifferent() {
        this.checkNotEquals(
            FormHandlerAliasSet.parse("different")
        );
    }

    @Override
    public FormHandlerAliasSet createObject() {
        return FormHandlerAliasSet.parse("abc, custom-alias custom(1) https://example.com/custom");
    }

    // json.............................................................................................................

    @Override
    public FormHandlerAliasSet unmarshall(final JsonNode json,
                                          final JsonNodeUnmarshallContext context) {
        return FormHandlerAliasSet.unmarshall(
            json,
            context
        );
    }

    @Override
    public FormHandlerAliasSet createJsonNodeMarshallingValue() {
        return FormHandlerAliasSet.parse("alias1 name1, name2, alias3 name3(\"999\") https://example.com/name3");
    }

    // class............................................................................................................

    @Override
    public Class<FormHandlerAliasSet> type() {
        return FormHandlerAliasSet.class;
    }
}
