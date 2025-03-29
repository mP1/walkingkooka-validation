/*
 * Copyright 2024 Miroslav Pokorny (github.com/mP1)
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
import walkingkooka.collect.set.Sets;
import walkingkooka.net.Url;
import walkingkooka.plugin.PluginInfoSetLikeTesting;
import walkingkooka.reflect.ClassTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import static org.junit.jupiter.api.Assertions.assertSame;

public final class ValidatorInfoSetTest implements PluginInfoSetLikeTesting<ValidatorName, ValidatorInfo, ValidatorInfoSet, ValidatorSelector, ValidatorAlias, ValidatorAliasSet>,
    ClassTesting<ValidatorInfoSet> {

    @Test
    public void testImmutableSet() {
        final ValidatorInfoSet set = this.createSet();

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
    public ValidatorInfoSet parseString(final String text) {
        return ValidatorInfoSet.parse(text);
    }

    // Set..............................................................................................................

    @Override
    public ValidatorInfoSet createSet() {
        return ValidatorInfoSet.with(
            Sets.of(
                this.info()
            )
        );
    }

    @Override
    public ValidatorInfo info() {
        return ValidatorInfo.with(
            Url.parseAbsolute("https://example.com/Validator123"),
            ValidatorName.with("Validator123")
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
            ValidatorInfoSet.EMPTY,
            JsonNode.array()
        );
    }

    @Test
    public void testMarshallNotEmpty2() {
        this.marshallAndCheck(
            ValidatorInfoSet.with(
                Sets.of(
                    ValidatorInfo.with(
                        Url.parseAbsolute("https://example.com/test123"),
                        ValidatorName.with("test123")
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
    public ValidatorInfoSet unmarshall(final JsonNode node,
                                       final JsonNodeUnmarshallContext context) {
        return ValidatorInfoSet.unmarshall(
            node,
            context
        );
    }

    @Override
    public ValidatorInfoSet createJsonNodeMarshallingValue() {
        return ValidatorInfoSet.with(
            Sets.of(
                ValidatorInfo.with(
                    Url.parseAbsolute("https://example.com/test111"),
                    ValidatorName.with("test111")
                ),
                ValidatorInfo.with(
                    Url.parseAbsolute("https://example.com/test222"),
                    ValidatorName.with("test222")
                )
            )
        );
    }

    // Class............................................................................................................

    @Override
    public Class<ValidatorInfoSet> type() {
        return ValidatorInfoSet.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
