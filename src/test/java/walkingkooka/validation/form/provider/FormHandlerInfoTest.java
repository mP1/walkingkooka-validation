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
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.net.Url;
import walkingkooka.plugin.PluginInfoLikeTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

public final class FormHandlerInfoTest implements PluginInfoLikeTesting<FormHandlerInfo, FormHandlerName> {

    @Test
    public void testSetNameWithDifferent() {
        final AbsoluteUrl url = Url.parseAbsolute("https://example/formHandler123");
        final FormHandlerName different = FormHandlerName.with("different");

        this.setNameAndCheck(
            FormHandlerInfo.with(
                url,
                FormHandlerName.with("original-formHandler-name")
            ),
            different,
            FormHandlerInfo.with(
                url,
                different
            )
        );
    }

    // ClassTesting.....................................................................................................

    @Override
    public Class<FormHandlerInfo> type() {
        return FormHandlerInfo.class;
    }

    // PluginInfoLikeTesting..............................................................................

    @Override
    public FormHandlerName createName(final String value) {
        return FormHandlerName.with(value);
    }

    @Override
    public FormHandlerInfo createPluginInfoLike(final AbsoluteUrl url,
                                                                                 final FormHandlerName name) {
        return FormHandlerInfo.with(
            url,
            name
        );
    }

    // json.............................................................................................................

    @Override
    public FormHandlerInfo unmarshall(final JsonNode json,
                                                                       final JsonNodeUnmarshallContext context) {
        return FormHandlerInfo.unmarshall(
            json,
            context
        );
    }

    // parse.............................................................................................................

    @Override
    public FormHandlerInfo parseString(final String text) {
        return FormHandlerInfo.parse(text);
    }
}
