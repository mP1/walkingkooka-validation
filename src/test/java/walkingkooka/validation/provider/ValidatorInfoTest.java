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
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.net.Url;
import walkingkooka.plugin.PluginInfoLikeTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

public final class ValidatorInfoTest implements PluginInfoLikeTesting<ValidatorInfo, ValidatorName> {

    @Test
    public void testSetNameWithDifferent() {
        final AbsoluteUrl url = Url.parseAbsolute("https://example/validator123");
        final ValidatorName different = ValidatorName.with("different");

        this.setNameAndCheck(
            ValidatorInfo.with(
                url,
                ValidatorName.with("original-validator-name")
            ),
            different,
            ValidatorInfo.with(
                url,
                different
            )
        );
    }

    // ClassTesting.....................................................................................................

    @Override
    public Class<ValidatorInfo> type() {
        return ValidatorInfo.class;
    }

    // PluginInfoLikeTesting..............................................................................

    @Override
    public ValidatorName createName(final String value) {
        return ValidatorName.with(value);
    }

    @Override
    public ValidatorInfo createPluginInfoLike(final AbsoluteUrl url,
                                              final ValidatorName name) {
        return ValidatorInfo.with(
            url,
            name
        );
    }

    // json.............................................................................................................

    @Override
    public ValidatorInfo unmarshall(final JsonNode json,
                                    final JsonNodeUnmarshallContext context) {
        return ValidatorInfo.unmarshall(
            json,
            context
        );
    }

    // parse.............................................................................................................

    @Override
    public ValidatorInfo parseString(final String text) {
        return ValidatorInfo.parse(text);
    }
}
