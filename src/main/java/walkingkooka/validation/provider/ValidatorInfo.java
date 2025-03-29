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

import walkingkooka.Cast;
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.net.http.server.hateos.HateosResource;
import walkingkooka.plugin.PluginInfo;
import walkingkooka.plugin.PluginInfoLike;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

/**
 * Captures a unique {@link AbsoluteUrl} and {@link ValidatorName} for a {@link Validator}.
 */
public final class ValidatorInfo implements PluginInfoLike<ValidatorInfo, ValidatorName>,
    HateosResource<ValidatorName> {

    public static ValidatorInfo parse(final String text) {
        return new ValidatorInfo(
            PluginInfo.parse(
                text,
                ValidatorName::with
            )
        );
    }

    public static ValidatorInfo with(final AbsoluteUrl url,
                                     final ValidatorName name) {
        return new ValidatorInfo(
            PluginInfo.with(
                url,
                name
            )
        );
    }

    private ValidatorInfo(final PluginInfo<ValidatorName> pluginInfo) {
        this.pluginInfo = pluginInfo;
    }

    // HasAbsoluteUrl...................................................................................................

    @Override
    public AbsoluteUrl url() {
        return this.pluginInfo.url();
    }

    // HasName..........................................................................................................

    @Override
    public ValidatorName name() {
        return this.pluginInfo.name();
    }

    @Override
    public ValidatorInfo setName(final ValidatorName name) {
        return this.name().equals(name) ?
            this :
            new ValidatorInfo(
                this.pluginInfo.setName(name)
            );
    }

    private final PluginInfo<ValidatorName> pluginInfo;

    // Comparable.......................................................................................................

    @Override
    public int compareTo(final ValidatorInfo other) {
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
            other instanceof ValidatorInfo &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final ValidatorInfo other) {
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

    static ValidatorInfo unmarshall(final JsonNode node,
                                    final JsonNodeUnmarshallContext context) {
        return ValidatorInfo.parse(
            node.stringOrFail()
        );
    }

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(ValidatorInfo.class),
            ValidatorInfo::unmarshall,
            ValidatorInfo::marshall,
            ValidatorInfo.class
        );
        ValidatorName.with("hello"); // trigger static init and json marshall/unmarshall registry
    }
}
