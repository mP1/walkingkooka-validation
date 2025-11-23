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
import walkingkooka.datetime.DateTimeSymbols;
import walkingkooka.naming.Name;
import walkingkooka.net.http.server.hateos.HateosResourceName;
import walkingkooka.plugin.PluginName;
import walkingkooka.plugin.PluginNameLike;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.List;
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

    public final static String ADD_EXPRESSION_STRING = "expression(add)";

    public final static ValueType ADD_EXPRESSION = new ValueType(ADD_EXPRESSION_STRING);

    public final static String AND_EXPRESSION_STRING = "expression(and)";

    public final static ValueType AND_EXPRESSION = new ValueType(AND_EXPRESSION_STRING);

    public final static String ANY_STRING = "*";

    public final static ValueType ANY = new ValueType(ANY_STRING);

    public final static String BOOLEAN_STRING = "boolean";

    public final static ValueType BOOLEAN = new ValueType(BOOLEAN_STRING);

    public final static String BOOLEAN_LIST_STRING = "list(boolean)";

    public final static ValueType BOOLEAN_LIST = new ValueType(BOOLEAN_LIST_STRING);

    public final static String CALL_EXPRESSION_STRING = "expression(call)";

    public final static ValueType CALL_EXPRESSION = new ValueType(CALL_EXPRESSION_STRING);

    public final static String CHOICE_LIST_STRING = "list(choice)";

    public final static ValueType CHOICE_LIST = new ValueType(CHOICE_LIST_STRING);

    public final static String CSV_LIST_STRING = "list(csv)";

    public final static ValueType CSV_LIST = new ValueType(CSV_LIST_STRING);
    
    public final static String DATA_URL_STRING = "url(data)";

    public final static ValueType DATA_URL = new ValueType(DATA_URL_STRING);

    public final static String DATE_STRING = "date";

    public final static ValueType DATE = new ValueType(DATE_STRING);
    
    public final static String DATE_LIST_STRING = "list(date)";

    public final static ValueType DATE_LIST = new ValueType(DATE_LIST_STRING);
    
    public final static String DATE_TIME_STRING = "date-time";

    public final static ValueType DATE_TIME = new ValueType(DATE_TIME_STRING);

    public final static String DATE_TIME_LIST_STRING = "list(date-time)";

    public final static ValueType DATE_TIME_LIST = new ValueType(DATE_TIME_LIST_STRING);

    public final static String DATE_TIME_SYMBOLS_STRING = "date-time-symbols";

    public final static ValueType DATE_TIME_SYMBOLS = new ValueType(DATE_TIME_SYMBOLS_STRING);

    public final static String DECIMAL_NUMBER_SYMBOLS_STRING = "decimal-number-symbols";

    public final static ValueType DECIMAL_NUMBER_SYMBOLS = new ValueType(DECIMAL_NUMBER_SYMBOLS_STRING);

    public final static String DIVIDE_EXPRESSION_STRING = "expression(divide)";

    public final static ValueType DIVIDE_EXPRESSION = new ValueType(DIVIDE_EXPRESSION_STRING);
    
    public final static String EMAIL_STRING = "email";

    public final static ValueType EMAIL = new ValueType(EMAIL_STRING);

    public final static String EQUALS_EXPRESSION_STRING = "expression(equals)";

    public final static ValueType EQUALS_EXPRESSION = new ValueType(EQUALS_EXPRESSION_STRING);

    public final static String ERROR_STRING = "error";

    public final static ValueType ERROR = new ValueType(ERROR_STRING);

    public final static String ERROR_LIST_STRING = "list(error)";

    public final static ValueType ERROR_LIST = new ValueType(ERROR_LIST_STRING);

    public final static String EXPRESSION_STRING = "expression";

    public final static ValueType EXPRESSION = new ValueType(EXPRESSION_STRING);

    public final static String GREATER_THAN_EXPRESSION_STRING = "expression(greater-than)";

    public final static ValueType GREATER_THAN_EXPRESSION = new ValueType(GREATER_THAN_EXPRESSION_STRING);

    public final static String GREATER_THAN_EQUALS_EXPRESSION_STRING = "expression(greater-than-equals)";

    public final static ValueType GREATER_THAN_EQUALS_EXPRESSION = new ValueType(GREATER_THAN_EQUALS_EXPRESSION_STRING);

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

    public final static String LAMBDA_FUNCTION_EXPRESSION_STRING = "expression(lambda-function)";

    public final static ValueType LAMBDA_FUNCTION_EXPRESSION = new ValueType(LAMBDA_FUNCTION_EXPRESSION_STRING);

    public final static String LESS_THAN_EXPRESSION_STRING = "expression(less-than)";

    public final static ValueType LESS_THAN_EXPRESSION = new ValueType(LESS_THAN_EXPRESSION_STRING);

    public final static String LESS_THAN_EQUALS_EXPRESSION_STRING = "expression(less-than-equals)";

    public final static ValueType LESS_THAN_EQUALS_EXPRESSION = new ValueType(LESS_THAN_EQUALS_EXPRESSION_STRING);

    public final static String LIST_STRING = "list";

    public final static ValueType LIST = new ValueType(LIST_STRING);

    public final static String LIST_EXPRESSION_STRING = "expression(list)";

    public final static ValueType LIST_EXPRESSION = new ValueType(LIST_EXPRESSION_STRING);
    
    public final static String LOCALE_STRING = "locale";

    public final static ValueType LOCALE = new ValueType(LOCALE_STRING);

    public final static String MAIL_TO_URL_STRING = "url(mail-to)";

    public final static ValueType MAIL_TO_URL = new ValueType(MAIL_TO_URL_STRING);

    public final static String MODULO_EXPRESSION_STRING = "expression(modulo)";

    public final static ValueType MODULO_EXPRESSION = new ValueType(MODULO_EXPRESSION_STRING);
    
    public final static String MULTIPLY_EXPRESSION_STRING = "expression(multiply)";

    public final static ValueType MULTIPLY_EXPRESSION = new ValueType(MULTIPLY_EXPRESSION_STRING);

    public final static String NAMED_FUNCTION_EXPRESSION_STRING = "expression(named-function)";

    public final static ValueType NAMED_FUNCTION_EXPRESSION = new ValueType(NAMED_FUNCTION_EXPRESSION_STRING);

    public final static String NEGATIVE_EXPRESSION_STRING = "expression(negative)";

    public final static ValueType NEGATIVE_EXPRESSION = new ValueType(NEGATIVE_EXPRESSION_STRING);
    
    public final static String NOT_EQUALS_EXPRESSION_STRING = "expression(not-equals)";

    public final static ValueType NOT_EQUALS_EXPRESSION = new ValueType(NOT_EQUALS_EXPRESSION_STRING);

    public final static String NOT_EXPRESSION_STRING = "expression(not)";

    public final static ValueType NOT_EXPRESSION = new ValueType(NOT_EXPRESSION_STRING);

    public final static String NUMBER_STRING = "number";

    public final static ValueType NUMBER = new ValueType(NUMBER_STRING);

    public final static String NUMBER_LIST_STRING = "list(number)";

    public final static ValueType NUMBER_LIST = new ValueType(NUMBER_LIST_STRING);

    public final static String OR_EXPRESSION_STRING = "expression(or)";

    public final static ValueType OR_EXPRESSION = new ValueType(OR_EXPRESSION_STRING);

    public final static String POWER_EXPRESSION_STRING = "expression(power)";

    public final static ValueType POWER_EXPRESSION = new ValueType(POWER_EXPRESSION_STRING);

    public final static String REFERENCE_EXPRESSION_STRING = "expression(reference)";

    public final static ValueType REFERENCE_EXPRESSION = new ValueType(REFERENCE_EXPRESSION_STRING);
    
    public final static String RELATIVE_URL_STRING = "url(relative)";

    public final static ValueType RELATIVE_URL = new ValueType(RELATIVE_URL_STRING);

    public final static String STRING_LIST_STRING = "list(text)";

    public final static ValueType STRING_LIST = new ValueType(STRING_LIST_STRING);

    public final static String SUBTRACT_EXPRESSION_STRING = "expression(subtract)";

    public final static ValueType SUBTRACT_EXPRESSION = new ValueType(SUBTRACT_EXPRESSION_STRING);

    public final static String TEXT_STRING = "text";

    public final static ValueType TEXT = new ValueType(TEXT_STRING);

    public final static String TIME_STRING = "time";

    public final static ValueType TIME = new ValueType(TIME_STRING);

    public final static String TIME_LIST_STRING = "list(time)";

    public final static ValueType TIME_LIST = new ValueType(TIME_LIST_STRING);

    public final static String URL_STRING = "url";

    public final static ValueType URL = new ValueType(URL_STRING);

    public final static String VALUE_EXPRESSION_STRING = "expression(value)";

    public final static ValueType VALUE_EXPRESSION = new ValueType(VALUE_EXPRESSION_STRING);
    
    public final static String WHOLE_NUMBER_STRING = "whole-number";

    public final static ValueType WHOLE_NUMBER = new ValueType(WHOLE_NUMBER_STRING);

    public final static String XOR_EXPRESSION_STRING = "expression(xor)";

    public final static ValueType XOR_EXPRESSION = new ValueType(XOR_EXPRESSION_STRING);

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
            case "walkingkooka.datetime.DateTimeSymbols":
                valueType = DATE_TIME_SYMBOLS;
                break;
            case "walkingkooka.math.DecimalNumberSymbols":
                valueType = DECIMAL_NUMBER_SYMBOLS;
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
            case "java.util.List":
                valueType = LIST;
                break;
            case "walkingkooka.collect.list.BooleanList":
                valueType = BOOLEAN_LIST;
                break;
            case "walkingkooka.validation.ValidationChoiceList":
                valueType = CHOICE_LIST;
                break;
            case "walkingkooka.collect.list.CsvStringList":
                valueType = CSV_LIST;
                break;
            case "walkingkooka.datetime.LocalDateList":
                valueType = DATE_LIST;
                break;
            case "walkingkooka.datetime.LocalDateTimeList":
                valueType = DATE_TIME_LIST;
                break;
            case "walkingkooka.validation.ValidationErrorList":
                valueType = ERROR_LIST;
                break;
            case "walkingkooka.datetime.LocalTimeList":
                valueType = TIME_LIST;
                break;
            case "walkingkooka.math.NumberList":
                valueType = NUMBER_LIST;
                break;
            case "walkingkooka.collect.list.StringList":
                valueType = STRING_LIST;
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
            case "walkingkooka.tree.expression.AddExpression":
                valueType = ADD_EXPRESSION;
                break;
            case "walkingkooka.tree.expression.AndExpression":
                valueType = AND_EXPRESSION;
                break;
            case "walkingkooka.tree.expression.CallExpression":
                valueType = CALL_EXPRESSION;
                break;
            case "walkingkooka.tree.expression.DivideExpression":
                valueType = DIVIDE_EXPRESSION;
                break;
            case "walkingkooka.tree.expression.EqualsExpression":
                valueType = EQUALS_EXPRESSION;
                break;
            case "walkingkooka.tree.expression.Expression":
                valueType = EXPRESSION;
                break;
            case "walkingkooka.tree.expression.GreaterThanExpression":
                valueType = GREATER_THAN_EXPRESSION;
                break;
            case "walkingkooka.tree.expression.GreaterThanEqualsExpression":
                valueType = GREATER_THAN_EQUALS_EXPRESSION;
                break;
            case "walkingkooka.tree.expression.LambdaFunctionExpression":
                valueType = LAMBDA_FUNCTION_EXPRESSION;
                break;
            case "walkingkooka.tree.expression.LessThanExpression":
                valueType = LESS_THAN_EXPRESSION;
                break;
            case "walkingkooka.tree.expression.LessThanEqualsExpression":
                valueType = LESS_THAN_EQUALS_EXPRESSION;
                break;
            case "walkingkooka.tree.expression.ListExpression":
                valueType = LIST_EXPRESSION;
                break;
            case "walkingkooka.tree.expression.ModuloExpression":
                valueType = MODULO_EXPRESSION;
                break;
            case "walkingkooka.tree.expression.MultiplyExpression":
                valueType = MULTIPLY_EXPRESSION;
                break;
            case "walkingkooka.tree.expression.NamedFunctionExpression":
                valueType = NAMED_FUNCTION_EXPRESSION;
                break;
            case "walkingkooka.tree.expression.NegativeExpression":
                valueType = NEGATIVE_EXPRESSION;
                break;
            case "walkingkooka.tree.expression.NotEqualsExpression":
                valueType = NOT_EQUALS_EXPRESSION;
                break;
            case "walkingkooka.tree.expression.NotExpression":
                valueType = NOT_EXPRESSION;
                break;
            case "walkingkooka.tree.expression.OrExpression":
                valueType = OR_EXPRESSION;
                break;
            case "walkingkooka.tree.expression.PowerExpression":
                valueType = POWER_EXPRESSION;
                break;
            case "walkingkooka.tree.expression.ReferenceExpression":
                valueType = REFERENCE_EXPRESSION;
                break;
            case "walkingkooka.tree.expression.SubtractExpression":
                valueType = SUBTRACT_EXPRESSION;
                break;
            case "walkingkooka.tree.expression.ValueExpression":
                valueType = VALUE_EXPRESSION;
                break;
            case "walkingkooka.tree.expression.XorExpression":
                valueType = XOR_EXPRESSION;
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
            case ADD_EXPRESSION_STRING:
                valueType = ADD_EXPRESSION;
                break;
            case AND_EXPRESSION_STRING:
                valueType = AND_EXPRESSION;
                break;
            case ANY_STRING:
                valueType = ANY;
                break;
            case BOOLEAN_STRING:
                valueType = BOOLEAN;
                break;
            case BOOLEAN_LIST_STRING:
                valueType = BOOLEAN_LIST;
                break;
            case CALL_EXPRESSION_STRING:
                valueType = CALL_EXPRESSION;
                break;
            case CHOICE_LIST_STRING:
                valueType = CHOICE_LIST;
                break;
            case CSV_LIST_STRING:
                valueType = CSV_LIST;
                break;
            case DATE_STRING:
                valueType = DATE;
                break;
            case DATE_LIST_STRING:
                valueType = DATE_LIST;
                break;
            case DATE_TIME_STRING:
                valueType = DATE_TIME;
                break;
            case DATE_TIME_LIST_STRING:
                valueType = DATE_TIME_LIST;
                break;
            case DATE_TIME_SYMBOLS_STRING:
                valueType = DATE_TIME_SYMBOLS;
                break;
            case DECIMAL_NUMBER_SYMBOLS_STRING:
                valueType = DECIMAL_NUMBER_SYMBOLS;
                break;
            case DIVIDE_EXPRESSION_STRING:
                valueType = DIVIDE_EXPRESSION;
                break;
            case EMAIL_STRING:
                valueType = EMAIL;
                break;
            case EQUALS_EXPRESSION_STRING:
                valueType = EQUALS_EXPRESSION;
                break;
            case ERROR_STRING:
                valueType = ERROR;
                break;
            case ERROR_LIST_STRING:
                valueType = ERROR_LIST;
                break;
            case EXPRESSION_STRING:
                valueType = EXPRESSION;
                break;
            case GREATER_THAN_EXPRESSION_STRING:
                valueType = GREATER_THAN_EXPRESSION;
                break;
            case GREATER_THAN_EQUALS_EXPRESSION_STRING:
                valueType = GREATER_THAN_EQUALS_EXPRESSION;
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
            case LAMBDA_FUNCTION_EXPRESSION_STRING:
                valueType = LAMBDA_FUNCTION_EXPRESSION;
                break;
            case LESS_THAN_EXPRESSION_STRING:
                valueType = LESS_THAN_EXPRESSION;
                break;
            case LESS_THAN_EQUALS_EXPRESSION_STRING:
                valueType = LESS_THAN_EQUALS_EXPRESSION;
                break;
            case LIST_STRING:
                valueType = LIST;
                break;
            case LIST_EXPRESSION_STRING:
                valueType = LIST_EXPRESSION;
                break;
            case LOCALE_STRING:
                valueType = LOCALE;
                break;
            case MODULO_EXPRESSION_STRING:
                valueType = MODULO_EXPRESSION;
                break;
            case MULTIPLY_EXPRESSION_STRING:
                valueType = MULTIPLY_EXPRESSION;
                break;
            case NEGATIVE_EXPRESSION_STRING:
                valueType = NEGATIVE_EXPRESSION;
                break;
            case NOT_EQUALS_EXPRESSION_STRING:
                valueType = NOT_EQUALS_EXPRESSION;
                break;
            case NOT_EXPRESSION_STRING:
                valueType = NOT_EXPRESSION;
                break;
            case NUMBER_STRING:
                valueType = NUMBER;
                break;
            case NUMBER_LIST_STRING:
                valueType = NUMBER_LIST;
                break;
            case OR_EXPRESSION_STRING:
                valueType = OR_EXPRESSION;
                break;
            case POWER_EXPRESSION_STRING:
                valueType = POWER_EXPRESSION;
                break;
            case REFERENCE_EXPRESSION_STRING:
                valueType = REFERENCE_EXPRESSION;
                break;
            case SUBTRACT_EXPRESSION_STRING:
                valueType = SUBTRACT_EXPRESSION;
                break;
            case TEXT_STRING:
                valueType = TEXT;
                break;
            case STRING_LIST_STRING:
                valueType = STRING_LIST;
                break;
            case TIME_STRING:
                valueType = TIME;
                break;
            case TIME_LIST_STRING:
                valueType = TIME_LIST;
                break;
            case URL_STRING:
                valueType = URL;
                break;
            case VALUE_EXPRESSION_STRING:
                valueType = VALUE_EXPRESSION;
                break;
            case WHOLE_NUMBER_STRING:
                valueType = WHOLE_NUMBER;
                break;
            case XOR_EXPRESSION_STRING:
                valueType = XOR_EXPRESSION;
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
     * Returns true if this {@link ValueType} is {@link DateTimeSymbols}
     */
    public boolean isDateTimeSymbols() {
        return DATE_TIME_SYMBOLS_STRING.equals(this.prefix());
    }

    /**
     * Returns true if this {@link ValueType} is {@link walkingkooka.math.DecimalNumberSymbols}
     */
    public boolean isDecimalNumberSymbols() {
        return DECIMAL_NUMBER_SYMBOLS_STRING.equals(this.prefix());
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
    public boolean isExpression() {
        return EXPRESSION_STRING.equals(this.prefix());
    }

    /**
     * Returns true if this {@link ValueType}.
     */
    public boolean isJson() {
        return JSON_NODE_STRING.equals(this.prefix());
    }

    /**
     * Returns true if this {@link ValueType} is a {@link List}.
     */
    public boolean isList() {
        return LIST_STRING.equals(this.prefix());
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

    /**
     * Extracts the prefix for the value type, aka the text before any left-parens.
     */
    public String prefix() {
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
