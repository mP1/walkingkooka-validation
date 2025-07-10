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

import walkingkooka.Cast;
import walkingkooka.naming.Name;
import walkingkooka.net.http.server.hateos.HateosResourceName;
import walkingkooka.plugin.PluginName;
import walkingkooka.plugin.PluginNameLike;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.Objects;

/**
 * The {@link Name} of a supported validation value.
 */
final public class ValidationValueTypeName implements PluginNameLike<ValidationValueTypeName> {

    public static final String HATEOS_RESOURCE_NAME_STRING = "type";

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

    public final static String ANY_STRING = "*";

    public final static ValidationValueTypeName ANY = new ValidationValueTypeName(ANY_STRING);

    public final static String BOOLEAN_STRING = "boolean";

    public final static ValidationValueTypeName BOOLEAN = new ValidationValueTypeName(BOOLEAN_STRING);

    public final static String DATE_STRING = "date";

    public final static ValidationValueTypeName DATE = new ValidationValueTypeName(DATE_STRING);

    public final static String DATE_TIME_STRING = "dateTime";

    public final static ValidationValueTypeName DATE_TIME = new ValidationValueTypeName(DATE_TIME_STRING);
    
    public final static String NUMBER_STRING = "number";

    public final static ValidationValueTypeName NUMBER = new ValidationValueTypeName(NUMBER_STRING);

    public final static String TEXT_STRING = "text";
    
    public final static ValidationValueTypeName TEXT = new ValidationValueTypeName(TEXT_STRING);

    public final static String TIME_STRING = "time";

    public final static ValidationValueTypeName TIME = new ValidationValueTypeName(TIME_STRING);

    /**
     * Factory that creates a {@link ValidationValueTypeName}
     */
    public static ValidationValueTypeName with(final String name) {
        Objects.requireNonNull(name, "name");

        ValidationValueTypeName validationValueTypeName;

        switch (name) {
            case ANY_STRING:
                validationValueTypeName = ANY;
                break;
            case BOOLEAN_STRING:
                validationValueTypeName = BOOLEAN;
                break;
            case DATE_STRING:
                validationValueTypeName = DATE;
                break;
            case DATE_TIME_STRING:
                validationValueTypeName = DATE_TIME;
                break;
            case NUMBER_STRING:
                validationValueTypeName = NUMBER;
                break;
            case TEXT_STRING:
                validationValueTypeName = TEXT;
                break;
            case TIME_STRING:
                validationValueTypeName = TIME;
                break;
            default:
                PluginName.with(name);
                validationValueTypeName = new ValidationValueTypeName(name);
                break;
        }

        return validationValueTypeName;
    }

    /**
     * Private constructor
     */
    private ValidationValueTypeName(final String name) {
        super();
        this.name = name;
    }

    @Override
    public String value() {
        return this.name;
    }

    private final String name;

    /**
     * Only returns true if this is ANY.
     */
    public boolean isAny() {
        return ANY == this;
    }

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof ValidationValueTypeName &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final ValidationValueTypeName other) {
        return this.compareTo(other) == 0;
    }

    @Override
    public String toString() {
        return this.name.toString();
    }

    // Json.............................................................................................................

    static ValidationValueTypeName unmarshall(final JsonNode node,
                                              final JsonNodeUnmarshallContext context) {
        return with(node.stringOrFail());
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return JsonNode.string(this.toString());
    }

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(ValidationValueTypeName.class),
            ValidationValueTypeName::unmarshall,
            ValidationValueTypeName::marshall,
            ValidationValueTypeName.class
        );
    }
}
