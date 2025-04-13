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

import walkingkooka.Cast;
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.net.http.server.hateos.HateosResource;
import walkingkooka.plugin.PluginInfo;
import walkingkooka.plugin.PluginInfoLike;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.validation.form.FormHandler;

/**
 * Captures a unique {@link AbsoluteUrl} and {@link FormHandlerName} for a {@link FormHandler}.
 */
public final class FormHandlerInfo implements PluginInfoLike<FormHandlerInfo, FormHandlerName>,
    HateosResource<FormHandlerName> {

    public static FormHandlerInfo parse(final String text) {
        return new FormHandlerInfo(
            PluginInfo.parse(
                text,
                FormHandlerName::with
            )
        );
    }

    public static FormHandlerInfo with(final AbsoluteUrl url,
                                       final FormHandlerName name) {
        return new FormHandlerInfo(
            PluginInfo.with(
                url,
                name
            )
        );
    }

    private FormHandlerInfo(final PluginInfo<FormHandlerName> pluginInfo) {
        this.pluginInfo = pluginInfo;
    }

    // HasAbsoluteUrl...................................................................................................

    @Override
    public AbsoluteUrl url() {
        return this.pluginInfo.url();
    }

    // HasName..........................................................................................................

    @Override
    public FormHandlerName name() {
        return this.pluginInfo.name();
    }

    @Override
    public FormHandlerInfo setName(final FormHandlerName name) {
        return this.name().equals(name) ?
            this :
            new FormHandlerInfo(
                this.pluginInfo.setName(name)
            );
    }

    private final PluginInfo<FormHandlerName> pluginInfo;

    // Comparable.......................................................................................................

    @Override
    public int compareTo(final FormHandlerInfo other) {
        return this.pluginInfo.compareTo(other.pluginInfo);
    }

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.pluginInfo.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof FormHandlerInfo &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final FormHandlerInfo other) {
        return this.pluginInfo.equals(other.pluginInfo);
    }

    @Override
    public String toString() {
        return this.pluginInfo.toString();
    }

    // Json.............................................................................................................

    static void register() {
        // helps force registry of json marshaller
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return JsonNode.string(this.toString());
    }

    static FormHandlerInfo unmarshall(final JsonNode node,
                                      final JsonNodeUnmarshallContext context) {
        return FormHandlerInfo.parse(
            node.stringOrFail()
        );
    }

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(FormHandlerInfo.class),
            FormHandlerInfo::unmarshall,
            FormHandlerInfo::marshall,
            FormHandlerInfo.class
        );
        FormHandlerName.with("hello"); // trigger static init and json marshall/unmarshall registry
    }
}
