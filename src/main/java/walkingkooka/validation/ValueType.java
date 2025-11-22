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

import java.util.Locale;
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

    public final static String ABSOLUTE_URL_STRING = "url(absolute)";

    public final static ValueType ABSOLUTE_URL = new ValueType(ABSOLUTE_URL_STRING);

    public final static String ANY_STRING = "*";

    public final static ValueType ANY = new ValueType(ANY_STRING);

    public final static String BOOLEAN_STRING = "boolean";

    public final static ValueType BOOLEAN = new ValueType(BOOLEAN_STRING);

    public final static String DATA_URL_STRING = "url(data)";

    public final static ValueType DATA_URL = new ValueType(DATA_URL_STRING);

    public final static String DATE_STRING = "date";

    public final static ValueType DATE = new ValueType(DATE_STRING);

    public final static String DATE_TIME_STRING = "date-time";

    public final static ValueType DATE_TIME = new ValueType(DATE_TIME_STRING);
    
    public final static String EMAIL_STRING = "email";

    public final static ValueType EMAIL = new ValueType(EMAIL_STRING);

    public final static String ERROR_STRING = "error";

    public final static ValueType ERROR = new ValueType(ERROR_STRING);

    public final static String JSON_NODE_STRING = "json";

    public final static ValueType JSON_NODE = new ValueType(JSON_NODE_STRING);

    public final static String JSON_ARRAY_STRING = "json(array)";

    public final static ValueType JSON_ARRAY = new ValueType(JSON_ARRAY_STRING);

    public final static String JSON_BOOLEAN_STRING = "json(boolean)";

    public final static ValueType JSON_BOOLEAN = new ValueType(JSON_BOOLEAN_STRING);
    
    public final static String JSON_NULL_STRING = "json(null)";

    public final static ValueType JSON_NULL = new ValueType(JSON_NULL_STRING);

    public final static String JSON_NUMBER_STRING = "json(number)";

    public final static ValueType JSON_NUMBER = new ValueType(JSON_NUMBER_STRING);

    public final static String JSON_OBJECT_STRING = "json(object)";

    public final static ValueType JSON_OBJECT = new ValueType(JSON_OBJECT_STRING);

    public final static String JSON_STRING_STRING = "json(string)";

    public final static ValueType JSON_STRING = new ValueType(JSON_STRING_STRING);

    public final static String LOCALE_STRING = "locale";

    public final static ValueType LOCALE = new ValueType(LOCALE_STRING);

    public final static String MAIL_TO_URL_STRING = "url(mail-to)";

    public final static ValueType MAIL_TO_URL = new ValueType(MAIL_TO_URL_STRING);

    public final static String NUMBER_STRING = "number";

    public final static ValueType NUMBER = new ValueType(NUMBER_STRING);

    public final static String RELATIVE_URL_STRING = "url(relative)";

    public final static ValueType RELATIVE_URL = new ValueType(RELATIVE_URL_STRING);

    public final static String TEXT_STRING = "text";

    public final static ValueType TEXT = new ValueType(TEXT_STRING);

    public final static String TIME_STRING = "time";

    public final static ValueType TIME = new ValueType(TIME_STRING);

    public final static String URL_STRING = "url";

    public final static ValueType URL = new ValueType(URL_STRING);

    public final static String WHOLE_NUMBER_STRING = "whole-number";

    public final static ValueType WHOLE_NUMBER = new ValueType(WHOLE_NUMBER_STRING);

    /**
     * Gets a {@link ValueType} for the given {@link Class#getName()}.
     */
    public static ValueType fromClassName(final String klass) {
        ValueType valueType;

        switch (klass) {
            case "java.lang.Boolean":
                valueType = BOOLEAN;
                break;
            case "walkingkooka.net.email.EmailAddress":
                valueType = EMAIL;
                break;
            case "walkingkooka.net.Url":
                valueType = URL;
                break;
            case "walkingkooka.net.AbsoluteUrl":
                valueType = ABSOLUTE_URL;
                break;
            case "walkingkooka.net.DataUrl":
                valueType = DATA_URL;
                break;
            case "walkingkooka.net.MailToUrl":
                valueType = MAIL_TO_URL;
                break;
            case "walkingkooka.net.RelativeUrl":
                valueType = RELATIVE_URL;
                break;
            case "walkingkooka.tree.expression.ExpressionNumber":
            case "walkingkooka.tree.expression.ExpressionNumberBigDecimal":
            case "walkingkooka.tree.expression.ExpressionNumberDouble":
                valueType = NUMBER;
                break;
            case "java.lang.Object":
                valueType = ANY;
                break;
            case "java.lang.Byte":
            case "java.lang.Short":
            case "java.lang.Integer":
            case "java.lang.Long":
            case "java.lang.Float":
            case "java.lang.Double":
            case "java.math.BigDecimal":
            case "java.math.BigInteger":
                valueType = with("number(" + classSimpleName(klass) + ")");
                break;
            case "java.util.Locale":
                valueType = LOCALE;
                break;
            case "java.lang.String":
                valueType = TEXT;
                break;
            case "java.lang.StringBuffer":
            case "java.lang.StringBuilder":
                valueType = with("text(" + classSimpleName(klass) + ")");
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
            case "walkingkooka.tree.json.JsonNode":
                valueType = JSON_NODE;
                break;
            case "walkingkooka.tree.json.JsonArray":
                valueType = JSON_ARRAY;
                break;
            case "walkingkooka.tree.json.JsonBoolean":
                valueType = JSON_BOOLEAN;
                break;
            case "walkingkooka.tree.json.JsonNull":
                valueType = JSON_NULL;
                break;
            case "walkingkooka.tree.json.JsonNumber":
                valueType = JSON_NUMBER;
                break;
            case "walkingkooka.tree.json.JsonObject":
                valueType = JSON_OBJECT;
                break;
            case "walkingkooka.tree.json.JsonString":
                valueType = JSON_STRING;
                break;
            default:
                valueType = with(klass);
                break;
        }

        return valueType;
    }

    private static String classSimpleName(final String className) {
        final int index = className.lastIndexOf('.');
        return -1 == index ?
            className :
            className.substring(index + 1);
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
            case ERROR_STRING:
                valueType = ERROR;
                break;
            case JSON_NODE_STRING:
                valueType = JSON_NODE;
                break;
            case JSON_ARRAY_STRING:
                valueType = JSON_ARRAY;
                break;
            case JSON_BOOLEAN_STRING:
                valueType = JSON_BOOLEAN;
                break;
            case JSON_NULL_STRING:
                valueType = JSON_NULL;
                break;
            case JSON_NUMBER_STRING:
                valueType = JSON_NUMBER;
                break;
            case JSON_OBJECT_STRING:
                valueType = JSON_OBJECT;
                break;
            case JSON_STRING_STRING:
                valueType = JSON_STRING;
                break;
            case LOCALE_STRING:
                valueType = LOCALE;
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
     * Returns true if this {@link ValueType} is a boolean value like {@link #BOOLEAN}
     */
    public boolean isBoolean() {
        return BOOLEAN_STRING.equals(this.prefix());
    }

    /**
     * Returns true if this {@link ValueType} is a date value such as {@link #DATE}, but not {@link #DATE_TIME}.
     */
    public boolean isDate() {
        return DATE_STRING.equals(this.prefix());
    }

    /**
     * Returns true if this {@link ValueType} is a date value such as {@link #DATE_TIME}, but not {@link #DATE}.
     */
    public boolean isDateTime() {
        return DATE_TIME_STRING.equals(this.prefix());
    }

    /**
     * Returns true if this {@link ValueType} is a {@link walkingkooka.net.email.EmailAddress}.
     */
    public boolean isEmail() {
        return EMAIL_STRING.equals(this.prefix());
    }

    /**
     * Returns true if this {@link ValueType}.
     */
    public boolean isError() {
        return ERROR_STRING.equals(this.prefix());
    }

    /**
     * Returns true if this {@link ValueType}.
     */
    public boolean isJson() {
        return JSON_NODE_STRING.equals(this.prefix());
    }

    /**
     * Returns true if this {@link ValueType} is a {@link Locale}.
     */
    public boolean isLocale() {
        return LOCALE_STRING.equals(this.prefix());
    }
    
    /**
     * Returns true if this {@link ValueType} is text such as {@link #NUMBER}.
     */
    public boolean isNumber() {
        final String prefix = this.prefix();
        return NUMBER_STRING.equals(prefix) || WHOLE_NUMBER_STRING.equals(prefix);
    }

    /**
     * Returns true if this {@link ValueType} is text such as {@link #TEXT}.
     */
    public boolean isText() {
        return TEXT_STRING.equals(this.prefix());
    }

    /**
     * Returns true if this {@link ValueType} is text such as {@link #TIME}.
     */
    public boolean isTime() {
        return TIME_STRING.equals(this.prefix());
    }

    /**
     * Returns true if this {@link ValueType} is text such as {@link #URL}.
     */
    public boolean isUrl() {
        return URL_STRING.equals(this.prefix());
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
