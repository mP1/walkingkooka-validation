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
import walkingkooka.HasValue;
import walkingkooka.InvalidTextLengthException;
import walkingkooka.collect.list.BooleanList;
import walkingkooka.collect.list.CsvStringList;
import walkingkooka.collect.list.StringList;
import walkingkooka.collect.list.TsvStringList;
import walkingkooka.collect.map.Maps;
import walkingkooka.currency.CurrencyCode;
import walkingkooka.currency.CurrencyCodeSet;
import walkingkooka.currency.CurrencyValue;
import walkingkooka.datetime.DateTimeSymbols;
import walkingkooka.datetime.LocalDateList;
import walkingkooka.datetime.LocalDateTimeList;
import walkingkooka.datetime.LocalTimeList;
import walkingkooka.environment.Environment;
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.locale.LocaleLanguageTag;
import walkingkooka.locale.LocaleLanguageTagSet;
import walkingkooka.math.DecimalNumberSymbols;
import walkingkooka.math.NumberList;
import walkingkooka.naming.Name;
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.net.DataUrl;
import walkingkooka.net.MailToUrl;
import walkingkooka.net.RelativeUrl;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.net.header.HasContentType;
import walkingkooka.net.header.MediaType;
import walkingkooka.net.http.server.hateos.HateosResourceName;
import walkingkooka.plugin.PluginName;
import walkingkooka.text.CaseSensitivity;
import walkingkooka.text.CharSequences;
import walkingkooka.text.HasCaseSensitivity;
import walkingkooka.text.HasText;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.text.printer.TreePrintable;
import walkingkooka.tree.expression.AddExpression;
import walkingkooka.tree.expression.AndExpression;
import walkingkooka.tree.expression.CallExpression;
import walkingkooka.tree.expression.DivideExpression;
import walkingkooka.tree.expression.EqualsExpression;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.ExpressionNumber;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.GreaterThanEqualsExpression;
import walkingkooka.tree.expression.GreaterThanExpression;
import walkingkooka.tree.expression.LambdaFunctionExpression;
import walkingkooka.tree.expression.LessThanEqualsExpression;
import walkingkooka.tree.expression.LessThanExpression;
import walkingkooka.tree.expression.ListExpression;
import walkingkooka.tree.expression.ModuloExpression;
import walkingkooka.tree.expression.MultiplyExpression;
import walkingkooka.tree.expression.NamedFunctionExpression;
import walkingkooka.tree.expression.NegativeExpression;
import walkingkooka.tree.expression.NotEqualsExpression;
import walkingkooka.tree.expression.NotExpression;
import walkingkooka.tree.expression.OrExpression;
import walkingkooka.tree.expression.PowerExpression;
import walkingkooka.tree.expression.ReferenceExpression;
import walkingkooka.tree.expression.SubtractExpression;
import walkingkooka.tree.expression.ValueExpression;
import walkingkooka.tree.expression.XorExpression;
import walkingkooka.tree.json.JsonArray;
import walkingkooka.tree.json.JsonBoolean;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonNull;
import walkingkooka.tree.json.JsonNumber;
import walkingkooka.tree.json.JsonObject;
import walkingkooka.tree.json.JsonString;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * The {@link Name} of a supported validation value. Note names must be lower-cased kebab-case not camel-case.
 */
final public class ValueType implements Comparable<ValueType>,
    HasCaseSensitivity,
    HasContentType,
    HasText,
    HasValue<String>,
    Predicate<ValueType>,
    TreePrintable {

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

    private final static Map<String, ValueType> CLASS_NAME_TO_VALUE_TYPE = Maps.sorted();

    private final static Map<Class<?>, ValueType> CLASS_TO_VALUE_TYPE = Maps.hash();

    static {
        register("url/absolute", AbsoluteUrl.class);
        register("expression/add", AddExpression.class);
        register("expression/and", AndExpression.class);
        ANY = register("*", Object.class);
        BOOLEAN = register("boolean", Boolean.class);
        register("list/boolean", BooleanList.class);
        register("expression/call", CallExpression.class);
        register("list/choice", ValidationChoiceList.class);
        register("list/csv", CsvStringList.class);
        register("currency", Currency.class);
        register("currency-code", CurrencyCode.class);
        register("currency-code-set", CurrencyCodeSet.class);
        register("currency-value", CurrencyValue.class);
        register("url/data", DataUrl.class);
        DATE = register("date", LocalDate.class);
        register("list/date", LocalDateList.class);
        register("date/date-time-symbols", DateTimeSymbols.class);
        register("number/decimal-number-symbols", DecimalNumberSymbols.class);
        DATE_TIME = register("date-time", LocalDateTime.class);
        register("list/date-time", LocalDateTimeList.class);
        register("expression/divide", DivideExpression.class);
        EMAIL = register("email", EmailAddress.class);
        register("environment", Environment.class);
        register("environment-value-name", EnvironmentValueName.class);
        register("expression", Expression.class);
        register("expression/equals", EqualsExpression.class);
        register("expression/greater-than", GreaterThanExpression.class);
        register("expression/greater-than-equals", GreaterThanEqualsExpression.class);
        register("json", JsonNode.class);
        register("json/array", JsonArray.class);
        register("json/boolean", JsonBoolean.class);
        register("json/null", JsonNull.class);
        register("json/number", JsonNumber.class);
        register("json/object", JsonObject.class);
        register("json/string", JsonString.class);
        register("expression/lambda", LambdaFunctionExpression.class);
        register("expression/less-than", LessThanExpression.class);
        register("expression/less-than-equals", LessThanEqualsExpression.class);
        register("list", List.class);
        register("expression/list", ListExpression.class);
        LOCALE = register("locale", Locale.class);
        register("locale-language-tag", LocaleLanguageTag.class);
        register("locale-language-tag-set", LocaleLanguageTagSet.class);
        register("url/mail-to", MailToUrl.class);
        register("expression/modulo", ModuloExpression.class);
        register("expression/multiply", MultiplyExpression.class);
        register("expression/named-function", NamedFunctionExpression.class);
        register("expression/negative", NegativeExpression.class);
        register("expression/not", NotExpression.class);
        register("expression/not-equals", NotEqualsExpression.class);

        NUMBER = register("number", ExpressionNumber.class);
        CLASS_TO_VALUE_TYPE.put(
            ExpressionNumberKind.BIG_DECIMAL.zero()
                .getClass(),
            ValueType.NUMBER
        );
        CLASS_NAME_TO_VALUE_TYPE.put(
            ExpressionNumberKind.BIG_DECIMAL.zero()
                .getClass()
                .getSimpleName(),
            ValueType.NUMBER
        );
        CLASS_TO_VALUE_TYPE.put(
            ExpressionNumberKind.DOUBLE.zero()
                .getClass(),
            ValueType.NUMBER
        );
        CLASS_NAME_TO_VALUE_TYPE.put(
            ExpressionNumberKind.DOUBLE.zero()
                .getClass()
                .getSimpleName(),
            ValueType.NUMBER
        );

        WHOLE_NUMBER = new ValueType(
            "whole-number",
            Number.class
        );
        CLASS_NAME_TO_VALUE_TYPE.put(
            "whole-number",
            ValueType.WHOLE_NUMBER
        );

        register("whole-number/byte", Byte.class);
        register("number/double", Double.class);
        register("number/float", Float.class);
        register("whole-number/integer", Integer.class);
        register("whole-number/long", Long.class);
        register("whole-number/short", Short.class);
        register("number/big-decimal", BigDecimal.class);
        register("whole-number/big-integer", BigInteger.class);
        register("list/number", NumberList.class);
        register("expression/or", OrExpression.class);
        register("expression/power", PowerExpression.class);
        register("expression/reference", ReferenceExpression.class);
        register("url(relative", RelativeUrl.class);
        register("text/StringBuffer", StringBuffer.class);
        register("text/StringBuilder", StringBuilder.class);
        register("list/string", StringList.class);
        register("expression/subtract", SubtractExpression.class);

        TEXT = register("text", String.class);
        CLASS_TO_VALUE_TYPE.put(
            String.class,
            ValueType.TEXT
        );

        TIME = register("time", LocalTime.class);
        register("list/time", LocalTimeList.class);
        register("list/tsv", TsvStringList.class);
        register("expression/value", ValueExpression.class);
        ERROR = register("error", ValidationError.class);
        ERROR_LIST = register("list/error", ValidationErrorList.class);
        register("expression/xor", XorExpression.class);
    }

    static {
        CLASS_NAME_TO_VALUE_TYPE.put(
            "number(big-decimal)",
            ValueType.NUMBER
        );
        CLASS_NAME_TO_VALUE_TYPE.put(
            "number(double)",
            ValueType.NUMBER
        );
    }

    public static ValueType register(final String label,
                                     final Class<?> type) {
        CharSequences.failIfNullOrEmpty(label, "label");
        Objects.requireNonNull(type, "type");

        if (CLASS_NAME_TO_VALUE_TYPE.containsKey(label)) {
            throw new IllegalArgumentException("Duplicate type " + label + " registration");
        }

        final ValueType valueType = new ValueType(label, type);

        final String typeName = type.getSimpleName();

        if (CLASS_NAME_TO_VALUE_TYPE.containsKey(typeName)) {
            throw new IllegalArgumentException("Duplicate type " + typeName + " registration");
        }
        if(false == label.equals(typeName)) {
            CLASS_NAME_TO_VALUE_TYPE.put(
                label,
                valueType
            );
        }
        CLASS_NAME_TO_VALUE_TYPE.put(
            typeName,
            valueType
        );
        CLASS_TO_VALUE_TYPE.put(
            type,
            valueType
        );

        return valueType;
    }

    public static Optional<ValueType> fromClass(final Class<?> klass) {
        Objects.requireNonNull(klass, "klass");

        return Optional.ofNullable(
            CLASS_TO_VALUE_TYPE.get(klass)
        );
    }

    public static ValueType fromClassOrFail(final Class<?> klass) {
        ValueType valueType = fromClass(klass)
            .orElse(null);
        if (null == valueType) {
            valueType = new ValueType(
                klass.getSimpleName(),
                klass
            );
        }
        return valueType;
    }

    public static Optional<ValueType> fromClassName(final String name) {
        Objects.requireNonNull(name, "name");

        return Optional.ofNullable(
            CLASS_NAME_TO_VALUE_TYPE.get(name)
        );
    }

    public static ValueType fromClassNameOrFail(final String name) {
        return fromClassName(name)
            .orElseThrow(() -> new IllegalArgumentException("Unknown type " + CharSequences.quoteIfChars(name)));
    }

    // constants........................................................................................................

    public final static String ANY_STRING = "*";

    public final static ValueType ANY;

    public final static String BOOLEAN_STRING = "boolean";

    public final static ValueType BOOLEAN;

    public static final String CURRENCY_STRING = "currency";

    public final static String DATE_STRING = "date";

    public final static ValueType DATE;

    public final static String DATE_TIME_STRING = "date-time";

    public final static ValueType DATE_TIME;

    public final static String EMAIL_STRING = "email";

    public final static ValueType EMAIL;

    public final static String ENVIRONMENT_STRING = "environment";

    public final static String ERROR_STRING = "error";

    public final static ValueType ERROR;

    public final static String ERROR_LIST_STRING = "list(error)";

    public final static ValueType ERROR_LIST;

    public final static String EXPRESSION_STRING = "expression";

    public final static String JSON_NODE_STRING = "json";

    public final static String LIST_STRING = "list";

    public final static String LOCALE_STRING = "locale";

    public final static ValueType LOCALE;

    public final static String NUMBER_STRING = "number";

    public final static ValueType NUMBER;

    public final static String TEXT_STRING = "text";

    public final static ValueType TEXT;

    public final static String TIME_STRING = "time";

    public final static ValueType TIME;

    public final static String URL_STRING = "url";

    public final static ValueType WHOLE_NUMBER;

    public final static String WHOLE_NUMBER_STRING = "whole-number";

    /**
     * Private constructor
     */
    private ValueType(final String name,
                      final Class<?> type) {
        super();

        InvalidTextLengthException.throwIfFail(
            "name",
            name,
            MIN_LENGTH,
            MAX_LENGTH
        );

        this.name = name;
        this.type = type;
    }

    @Override
    public String value() {
        return this.name;
    }

    private final String name;

    public Class<?> type() {
        return this.type;
    }

    private final Class<?> type;

    /**
     * Extracts the prefix for the value type, aka the text before any left-parens.
     */
    public Optional<ValueType> parent() {
        if (null == this.parent) {
            final String name = this.name;

            final int slash = name.indexOf('/');
            if (-1 == slash) {
                this.parent = Optional.empty();
            } else {
                this.parent = fromClassName(
                    name.substring(0, slash)
                );
            }
        }

        return this.parent;
    }

    private Optional<ValueType> parent;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return Objects.hash(
            this.name,
            this.type
        );
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof ValueType &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final ValueType other) {
        return this.name.equals(other.name) &&
            this.type.equals(other.type);
    }

    @Override
    public String toString() {
        return this.name;
    }

    // Json.............................................................................................................

    static ValueType unmarshall(final JsonNode node,
                                final JsonNodeUnmarshallContext context) {
        final String className = node.stringOrFail();
        return fromClassName(className)
            .orElseThrow(() -> new IllegalArgumentException("Unknown ValueType with class name " + CharSequences.quote(className)));
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

    // HasContentType...................................................................................................

    public final static MediaType CONTENT_TYPE = HasContentType.json(ValueType.class);

    @Override
    public Optional<MediaType> contentType() {
        return Optional.of(CONTENT_TYPE);
    }

    // HasCaseSensitivity...............................................................................................

    @Override
    public CaseSensitivity caseSensitivity() {
        return CASE_SENSITIVITY;
    }

    public final static CaseSensitivity CASE_SENSITIVITY = CaseSensitivity.SENSITIVE;

    // HasText..........................................................................................................

    @Override
    public String text() {
        return this.name;
    }

    // Predicate........................................................................................................

    /**
     * This may be used to test if the given {@link ValueType} is equal or has a parent equal to this.
     * <pre>
     * NUMBER.test(FLOAT)
     * true
     *
     * WHOLE_NUMBER.test(FLOAT)
     * false
     * </pre>
     */
    @Override
    public boolean test(final ValueType other) {
        boolean test = null != other;

        if (test) {
            test = this.equals(other);
            if (false == test) {
                test = this.test(
                    other.parent()
                        .orElse(null)
                );
            }
        }

        return test;
    }

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        printer.println(this.name);
    }

    // Comparable.......................................................................................................

    @Override
    public int compareTo(final ValueType other) {
        return CASE_SENSITIVITY.comparator()
            .compare(
                this.name,
                other.name
            );
    }
}
