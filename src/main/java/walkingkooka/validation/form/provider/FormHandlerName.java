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
import walkingkooka.naming.Name;
import walkingkooka.net.http.server.hateos.HateosResourceName;
import walkingkooka.plugin.PluginName;
import walkingkooka.plugin.PluginNameLike;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.validation.form.FormHandler;

import java.util.Objects;

/**
 * The {@link Name} of a {@link FormHandler}. Note comparator names are case-sensitive.
 */
final public class FormHandlerName implements PluginNameLike<FormHandlerName> {

    public static final String HATEOS_RESOURCE_NAME_STRING = "formHandler";

    public static final HateosResourceName HATEOS_RESOURCE_NAME = HateosResourceName.with(HATEOS_RESOURCE_NAME_STRING);

    public static boolean isChar(final int pos,
                                 final char c) {
        return PluginName.isChar(pos, c);
    }

    /**
     * The minimum valid length
     */
    public final static int MIN_LENGTH = 1;

    /**
     * The maximum valid length
     */
    public final static int MAX_LENGTH = PluginName.MAX_LENGTH;

    // constants........................................................................................................

    final static String BASIC_STRING = "basic";

    public final static FormHandlerName BASIC = registerConstant(BASIC_STRING);
    
    private static FormHandlerName registerConstant(final String name) {
        return new FormHandlerName(
            PluginName.with(name)
        );
    }

    /**
     * Factory that creates a {@link FormHandlerName}
     */
    public static FormHandlerName with(final String name) {
        Objects.requireNonNull(name, "name");

        final FormHandlerName formHandlerName;

        switch (name) {
            case BASIC_STRING:
                formHandlerName = BASIC;
                break;
            default:
                formHandlerName = new FormHandlerName(
                    PluginName.with(name)
                        .checkLength("FormHandler")
                );
                break;
        }

        return formHandlerName;
    }

    /**
     * Private constructor
     */
    private FormHandlerName(final PluginName name) {
        super();
        this.name = name;
    }

    @Override
    public String value() {
        return this.name.value();
    }

    private final PluginName name;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof FormHandlerName &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final FormHandlerName other) {
        return this.compareTo(other) == 0;
    }

    @Override
    public String toString() {
        return this.name.toString();
    }

    // Json.............................................................................................................

    static FormHandlerName unmarshall(final JsonNode node,
                                      final JsonNodeUnmarshallContext context) {
        return with(node.stringOrFail());
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return JsonNode.string(this.toString());
    }

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(FormHandlerName.class),
            FormHandlerName::unmarshall,
            FormHandlerName::marshall,
            FormHandlerName.class
        );
    }
}
