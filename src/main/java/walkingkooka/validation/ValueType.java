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
final public class ValueType implements PluginNameLike<ValueType> {

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

    public final static ValueType ANY = new ValueType(ANY_STRING);

    public final static String BOOLEAN_STRING = "boolean";

    public final static ValueType BOOLEAN = new ValueType(BOOLEAN_STRING);

    public final static String DATE_STRING = "date";

    public final static ValueType DATE = new ValueType(DATE_STRING);

    public final static String DATE_TIME_STRING = "date-time";

    public final static ValueType DATE_TIME = new ValueType(DATE_TIME_STRING);

    public final static String EMAIL_STRING = "email";

    public final static ValueType EMAIL = new ValueType(EMAIL_STRING);

    public final static String NUMBER_STRING = "number";

    public final static ValueType NUMBER = new ValueType(NUMBER_STRING);

    public final static String TEXT_STRING = "text";

    public final static ValueType TEXT = new ValueType(TEXT_STRING);

    public final static String TIME_STRING = "time";

    public final static ValueType TIME = new ValueType(TIME_STRING);

    public final static String URL_STRING = "url";

    public final static ValueType URL = new ValueType(URL_STRING);

    public final static String WHOLE_NUMBER_STRING = "whole-number";

    public final static ValueType WHOLE_NUMBER = new ValueType(WHOLE_NUMBER_STRING);

    public static ValueType fromClass(final Class<?> klass) {
        Objects.requireNonNull(klass, "class");

        ValueType valueType;

        switch (klass.getName()) {
            case "java.lang.Boolean":
                valueType = BOOLEAN;
                break;
            case "walkingkooka.net.AbsoluteUrl":
                valueType = URL;
                break;
            case "walkingkooka.net.email.EmailAddress":
                valueType = EMAIL;
                break;
            case "java.lang.String":
                valueType = TEXT;
                break;
            case "java.time.LocalDate":
                valueType = DATE;
                break;
            case "java.time.LocalDateTime":
                valueType = DATE_TIME;
                break;
            case "java.time.LocalTime":
                valueType = TIME;
                break;
            default:
                if (klass == StringBuilder.class || klass == StringBuffer.class) {
                    valueType = with("text(" + klass.getSimpleName() + ")");
                } else {
                    if (Maths.isNumberClass(klass)) {
                        valueType = with("number(" + klass.getSimpleName() + ")");
                    } else {
                        if (ExpressionNumber.isClass(klass)) {
                            valueType = NUMBER;
                            break;
                        } else {
                            if (Object.class == klass) {
                                valueType = ANY;
                            } else {
                                valueType = with(
                                    klass.getName()
                                );
                            }
                        }
                        break;
                    }
                }
        }

        return valueType;
    }

    /**
     * Factory that creates a {@link ValueType}
     */
    public static ValueType with(final String name) {
        Objects.requireNonNull(name, "name");

        ValueType valueType;

        switch (name) {
            case ANY_STRING:
                valueType = ANY;
                break;
            case BOOLEAN_STRING:
                valueType = BOOLEAN;
                break;
            case DATE_STRING:
                valueType = DATE;
                break;
            case DATE_TIME_STRING:
                valueType = DATE_TIME;
                break;
            case EMAIL_STRING:
                valueType = EMAIL;
                break;
            case NUMBER_STRING:
                valueType = NUMBER;
                break;
            case TEXT_STRING:
                valueType = TEXT;
                break;
            case TIME_STRING:
                valueType = TIME;
                break;
            case URL_STRING:
                valueType = URL;
                break;
            case WHOLE_NUMBER_STRING:
                valueType = WHOLE_NUMBER;
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
                valueType = new ValueType(name);
                break;
        }

        return valueType;
    }

    /**
     * Private constructor
     */
    private ValueType(final String name) {
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

    /**
     * Returns true if this {@link ValueType} is a date value such as {@link #DATE}, but not {@link #DATE_TIME}.
     */
    public boolean isDate() {
        return "date".equals(this.prefix());
    }

    /**
     * Returns true if this {@link ValueType} is a date value such as {@link #DATE_TIME}, but not {@link #DATE}.
     */
    public boolean isDateTime() {
        return "date-time".equals(this.prefix());
    }

    /**
     * Returns true if this {@link ValueType} is text such as {@link #NUMBER}.
     */
    public boolean isNumber() {
        return "number".equals(this.prefix());
    }

    /**
     * Returns true if this {@link ValueType} is text such as {@link #TIME}.
     */
    public boolean isTime() {
        return "time".equals(this.prefix());
    }

    /**
     * Returns true if this {@link ValueType} is text such as {@link #TEXT}.
     */
    public boolean isText() {
        return "text".equals(this.prefix());
    }

    private String prefix() {
        final String name = this.name;

        final int leftParens = name.indexOf('(');

        return -1 == leftParens ?
            name :
            name.substring(
                0,
                leftParens
            );
    }

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof ValueType &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final ValueType other) {
        return this.compareTo(other) == 0;
    }

    @Override
    public String toString() {
        return this.name;
    }

    // Json.............................................................................................................

    static ValueType unmarshall(final JsonNode node,
                                final JsonNodeUnmarshallContext context) {
        return with(node.stringOrFail());
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return JsonNode.string(this.toString());
    }

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(ValueType.class),
            ValueType::unmarshall,
            ValueType::marshall,
            ValueType.class
        );
    }
}
