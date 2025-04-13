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

package walkingkooka.validation.provider;

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

public final class ValidatorAliasSetTest implements PluginAliasSetLikeTesting<ValidatorName,
    ValidatorInfo,
    ValidatorInfoSet,
    ValidatorSelector,
    ValidatorAlias,
    ValidatorAliasSet>,
    HashCodeEqualsDefinedTesting2<ValidatorAliasSet>,
    ToStringTesting<ValidatorAliasSet>,
    JsonNodeMarshallingTesting<ValidatorAliasSet> {

    // with.............................................................................................................

    @Test
    public void testWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValidatorAliasSet.with(null)
        );
    }

    @Test
    public void testWithEmpty() {
        assertSame(
            ValidatorAliasSet.EMPTY,
            ValidatorAliasSet.with(SortedSets.empty())
        );
    }

    // name.............................................................................................................

    @Test
    public void testAliasOrNameWithName() {
        final ValidatorName abc = ValidatorName.with("abc");

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
            ValidatorName.with("sunshine-alias"),
            ValidatorName.with("sunshine")
        );
    }

    @Test
    public void testAliasSelectorWithName() {
        this.aliasSelectorAndCheck(
            this.createSet(),
            ValidatorName.with("abc")
        );
    }

    @Test
    public void testAliasSelectorWithAlias() {
        this.aliasSelectorAndCheck(
            this.createSet(),
            ValidatorName.with("custom-alias"),
            ValidatorSelector.parse("custom(1)")
        );
    }

    @Override
    public ValidatorAliasSet createSet() {
        return ValidatorAliasSet.parse("abc, moo, mars, custom-alias custom(1) https://example.com/custom , sunshine-alias sunshine");
    }

    // parse............................................................................................................

    @Override
    public ValidatorAliasSet parseString(final String text) {
        return ValidatorAliasSet.parse(text);
    }

    // equals...........................................................................................................

    @Test
    public void testEqualsDifferent() {
        this.checkNotEquals(
            ValidatorAliasSet.parse("different")
        );
    }

    @Override
    public ValidatorAliasSet createObject() {
        return ValidatorAliasSet.parse("abc, custom-alias custom(1) https://example.com/custom");
    }

    // json.............................................................................................................

    @Override
    public ValidatorAliasSet unmarshall(final JsonNode json,
                                        final JsonNodeUnmarshallContext context) {
        return ValidatorAliasSet.unmarshall(
            json,
            context
        );
    }

    @Override
    public ValidatorAliasSet createJsonNodeMarshallingValue() {
        return ValidatorAliasSet.parse("alias1 name1, name2, alias3 name3(\"999\") https://example.com/name3");
    }

    // class............................................................................................................

    @Override
    public Class<ValidatorAliasSet> type() {
        return ValidatorAliasSet.class;
    }
}
