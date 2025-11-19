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
import walkingkooka.math.Maths;
import walkingkooka.naming.Name;
import walkingkooka.net.http.server.hateos.HateosResourceName;
import walkingkooka.plugin.PluginName;
import walkingkooka.plugin.PluginNameLike;
import walkingkooka.tree.expression.ExpressionNumber;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.Objects;

/**
 * The {@link Name} of a supported validation value. Note names must be lower-cased kebab-case not camel-case.
 */
final public class ValueTypeName implements PluginNameLike<ValueTypeName> {

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

    public final static ValueTypeName ANY = new ValueTypeName(ANY_STRING);

    public final static String BOOLEAN_STRING = "boolean";

    public final static ValueTypeName BOOLEAN = new ValueTypeName(BOOLEAN_STRING);

    public final static String DATE_STRING = "date";

    public final static ValueTypeName DATE = new ValueTypeName(DATE_STRING);

    public final static String DATE_TIME_STRING = "date-time";

    public final static ValueTypeName DATE_TIME = new ValueTypeName(DATE_TIME_STRING);

    public final static String EMAIL_STRING = "email";

    public final static ValueTypeName EMAIL = new ValueTypeName(EMAIL_STRING);

    public final static String NUMBER_STRING = "number";

    public final static ValueTypeName NUMBER = new ValueTypeName(NUMBER_STRING);

    public final static String TEXT_STRING = "text";

    public final static ValueTypeName TEXT = new ValueTypeName(TEXT_STRING);

    public final static String TIME_STRING = "time";

    public final static ValueTypeName TIME = new ValueTypeName(TIME_STRING);

    public final static String URL_STRING = "url";

    public final static ValueTypeName URL = new ValueTypeName(URL_STRING);

    public final static String WHOLE_NUMBER_STRING = "whole-number";

    public final static ValueTypeName WHOLE_NUMBER = new ValueTypeName(WHOLE_NUMBER_STRING);

    public static ValueTypeName fromClass(final Class<?> klass) {
        Objects.requireNonNull(klass, "class");

        ValueTypeName valueTypeName;

        switch (klass.getName()) {
            case "java.lang.Boolean":
                valueTypeName = BOOLEAN;
                break;
            case "walkingkooka.net.AbsoluteUrl":
                valueTypeName = URL;
                break;
            case "walkingkooka.net.email.EmailAddress":
                valueTypeName = EMAIL;
                break;
            case "java.lang.String":
                valueTypeName = TEXT;
                break;
            case "java.time.LocalDate":
                valueTypeName = DATE;
                break;
            case "java.time.LocalDateTime":
                valueTypeName = DATE_TIME;
                break;
            case "java.time.LocalTime":
                valueTypeName = TIME;
                break;
            default:
                if (klass == StringBuilder.class || klass == StringBuffer.class) {
                    valueTypeName = with("text(" + klass.getSimpleName() + ")");
                } else {
                    if (Maths.isNumberClass(klass)) {
                        valueTypeName = with("number(" + klass.getSimpleName() + ")");
                    } else {
                        if (ExpressionNumber.isClass(klass)) {
                            valueTypeName = NUMBER;
                            break;
                        } else {
                            if (Object.class == klass) {
                                valueTypeName = ANY;
                            } else {
                                valueTypeName = with(
                                    klass.getName()
                                );
                            }
                        }
                        break;
                    }
                }
        }

        return valueTypeName;
    }

    /**
     * Factory that creates a {@link ValueTypeName}
     */
    public static ValueTypeName with(final String name) {
        Objects.requireNonNull(name, "name");

        ValueTypeName valueTypeName;

        switch (name) {
            case ANY_STRING:
                valueTypeName = ANY;
                break;
            case BOOLEAN_STRING:
                valueTypeName = BOOLEAN;
                break;
            case DATE_STRING:
                valueTypeName = DATE;
                break;
            case DATE_TIME_STRING:
                valueTypeName = DATE_TIME;
                break;
            case EMAIL_STRING:
                valueTypeName = EMAIL;
                break;
            case NUMBER_STRING:
                valueTypeName = NUMBER;
                break;
            case TEXT_STRING:
                valueTypeName = TEXT;
                break;
            case TIME_STRING:
                valueTypeName = TIME;
                break;
            case URL_STRING:
                valueTypeName = URL;
                break;
            case WHOLE_NUMBER_STRING:
                valueTypeName = WHOLE_NUMBER;
                break;
            default:
                // FIXME weakness doesnt allow all possible class names, eg those with non ascii letters will fail
                PluginName.with(
                    name.replace('.', '-')
                        .replace('$', '-')
                        .replace('(', '-')
                        .replace(')', '-')
                        .toLowerCase()
                );
                valueTypeName = new ValueTypeName(name);
                break;
        }

        return valueTypeName;
    }

    /**
     * Private constructor
     */
    private ValueTypeName(final String name) {
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
            other instanceof ValueTypeName &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final ValueTypeName other) {
        return this.compareTo(other) == 0;
    }

    @Override
    public String toString() {
        return this.name;
    }

    // Json.............................................................................................................

    static ValueTypeName unmarshall(final JsonNode node,
                                    final JsonNodeUnmarshallContext context) {
        return with(node.stringOrFail());
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return JsonNode.string(this.toString());
    }

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(ValueTypeName.class),
            ValueTypeName::unmarshall,
            ValueTypeName::marshall,
            ValueTypeName.class
        );
    }
}
