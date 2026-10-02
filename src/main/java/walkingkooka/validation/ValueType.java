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
import walkingkooka.net.HasUrlFragment;
import walkingkooka.net.MailToUrl;
import walkingkooka.net.RelativeUrl;
import walkingkooka.net.Url;
import walkingkooka.net.UrlFragment;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.net.header.HasContentType;
import walkingkooka.net.header.MediaType;
import walkingkooka.plugin.PluginName;
import walkingkooka.predicate.character.CharPredicates;
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
    HasUrlFragment,
    HasValue<String>,
    Predicate<ValueType>,
    TreePrintable {

    /**
     * The minimum valid length
     */
    public final static int MIN_LENGTH = 1;

    /**
     * The maximum valid length
     */
    public final static int MAX_LENGTH = PluginName.MAX_LENGTH;

    /**
     * Fully qualified class name to {@link ValueType}.
     */
    private final static Map<String, ValueType> CLASS_NAME_TO_VALUE_TYPE = Maps.sorted();

    private final static Map<Class<?>, ValueType> CLASS_TO_VALUE_TYPE = Maps.hash();

    static {
        ANY = register("*", Object.class);

        BOOLEAN = register("boolean", Boolean.class);

        register("currency", Object.class);
        register("currency/Currency", Currency.class);
        register("currency/CurrencyCode", CurrencyCode.class);
        register("currency/CurrencyCodeSet", CurrencyCodeSet.class);
        register("currency/CurrencyValue", CurrencyValue.class);

        register("date-time", Object.class);
        DATE = register("date-time/Date", LocalDate.class);
        register("date-time/DateTimeSymbols", DateTimeSymbols.class);
        DATE_TIME = register("date-time/DateTime", LocalDateTime.class);
        TIME = register("date-time/Time", LocalTime.class);

        EMAIL = register("email", Object.class);
        register("email/EmailAddress", EmailAddress.class);

        register("environment", Object.class);
        register("environment/Environment", Environment.class);
        register("environment/EnvironmentValueName", EnvironmentValueName.class);

        register("expression", Expression.class);
        register("expression/AddExpression", AddExpression.class);
        register("expression/AndExpression", AndExpression.class);
        register("expression/CallExpression", CallExpression.class);
        register("expression/DivideExpression", DivideExpression.class);
        register("expression/EqualsExpression", EqualsExpression.class);
        register("expression/GreaterThanExpression", GreaterThanExpression.class);
        register("expression/GreaterThanEqualsExpression", GreaterThanEqualsExpression.class);
        register("expression/LambdaFunctionExpression", LambdaFunctionExpression.class);
        register("expression/LessThanExpression", LessThanExpression.class);
        register("expression/LessThanEqualsExpression", LessThanEqualsExpression.class);
        register("expression/ListExpression", ListExpression.class);
        register("expression/ModuloExpression", ModuloExpression.class);
        register("expression/MultiplyExpression", MultiplyExpression.class);
        register("expression/NamedFunctionExpression", NamedFunctionExpression.class);
        register("expression/NegativeExpression", NegativeExpression.class);
        register("expression/NotExpression", NotExpression.class);
        register("expression/NotEqualsExpression", NotEqualsExpression.class);
        register("expression/OrExpression", OrExpression.class);
        register("expression/PowerExpression", PowerExpression.class);
        register("expression/ReferenceExpression", ReferenceExpression.class);
        register("expression/SubtractExpression", SubtractExpression.class);
        register("expression/ValueExpression", ValueExpression.class);
        register("expression/XorExpression", XorExpression.class);

        register("error", Object.class);
        ERROR = register("error/Error", ValidationError.class);

        register("json", JsonNode.class);
        register("json/JsonArray", JsonArray.class);
        register("json/JsonBoolean", JsonBoolean.class);
        register("json/JsonNull", JsonNull.class);
        register("json/JsonNumber", JsonNumber.class);
        register("json/JsonObject", JsonObject.class);
        register("json/JsonString", JsonString.class);

        register("list", List.class);
        register("list/BooleanList", BooleanList.class);
        register("list/DateList", LocalDateList.class);
        register("list/DateTimeList", LocalDateTimeList.class);
        register("list/ChoiceList", ValidationChoiceList.class);
        register("list/Csv", CsvStringList.class);
        ERROR_LIST = register("list/ErrorList", ValidationErrorList.class);
        register("list/NumberList", NumberList.class);
        register("list/StringList", StringList.class);
        register("list/TimeList", LocalTimeList.class);
        register("list/Tsv", TsvStringList.class);

        LOCALE = register("locale", Object.class);
        register("locale/Locale", Locale.class);
        register("locale/LocaleLanguageTag", LocaleLanguageTag.class);
        register("locale/LocaleLanguageTagSet", LocaleLanguageTagSet.class);

        NUMBER = register("number", Object.class);

        final ValueType expressionNumber = new ValueType(
            "number/Number",
            ExpressionNumber.class
        );
        ;
        CLASS_TO_VALUE_TYPE.put(
            ExpressionNumber.class,
            expressionNumber
        );
        CLASS_NAME_TO_VALUE_TYPE.put(
            ExpressionNumber.class.getName(),
            expressionNumber
        );
        CLASS_NAME_TO_VALUE_TYPE.put(
            expressionNumber.value(),
            expressionNumber
        );
        CLASS_TO_VALUE_TYPE.put(
            ExpressionNumberKind.BIG_DECIMAL.zero()
                .getClass(),
            expressionNumber
        );
        CLASS_NAME_TO_VALUE_TYPE.put(
            ExpressionNumberKind.BIG_DECIMAL.zero()
                .getClass()
                .getName(),
            expressionNumber
        );
        CLASS_TO_VALUE_TYPE.put(
            ExpressionNumberKind.DOUBLE.zero()
                .getClass(),
            expressionNumber
        );
        CLASS_NAME_TO_VALUE_TYPE.put(
            ExpressionNumberKind.DOUBLE.zero()
                .getClass()
                .getName(),
            expressionNumber
        );

        WHOLE_NUMBER = new ValueType(
            "number/whole",
            Number.class
        );
        CLASS_NAME_TO_VALUE_TYPE.put(
            "number/whole",
            ValueType.WHOLE_NUMBER
        );

        register("number/DecimalNumberSymbols", DecimalNumberSymbols.class);
        register("number/whole/Byte", Byte.class);
        register("number/Double", Double.class);
        register("number/Float", Float.class);
        register("number/whole/Integer", Integer.class);
        register("number/whole/Long", Long.class);
        register("number/whole/Short", Short.class);
        register("number/BigDecimal", BigDecimal.class);
        register("number/whole/BigInteger", BigInteger.class);

        TEXT = register("text", Object.class);
        final ValueType string = register("text/Text", String.class);
        CLASS_TO_VALUE_TYPE.put(
            String.class,
            string
        );
        register("text/StringBuffer", StringBuffer.class);
        register("text/StringBuilder", StringBuilder.class);

        register("url", Url.class);
        register("url/AbsoluteUrl", AbsoluteUrl.class);
        register("url/DataUrl", DataUrl.class);
        register("url/MailToUrl", MailToUrl.class);
        register("url/relative", RelativeUrl.class);
    }

    public static ValueType register(final String label,
                                     final Class<?> type) {
        CharSequences.failIfNullOrEmpty(label, "label");
        Objects.requireNonNull(type, "type");

        if (CLASS_NAME_TO_VALUE_TYPE.containsKey(label)) {
            throw new IllegalArgumentException("Duplicate type " + label + " registration");
        }

        final ValueType valueType = new ValueType(label, type);

        final String typeName = type.getName();

        if (Object.class != type || "*".equals(label)) {
            if (CLASS_NAME_TO_VALUE_TYPE.containsKey(typeName)) {
                throw new IllegalArgumentException("Duplicate type " + typeName + " registration");
            }
            if (false == label.equals(typeName)) {
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
        }

        if (false == label.equals(typeName)) {
            CLASS_NAME_TO_VALUE_TYPE.put(
                label,
                valueType
            );
        }

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

    public static Optional<ValueType> parse(final String name) {
        Objects.requireNonNull(name, "name");

        return Optional.ofNullable(
            CLASS_NAME_TO_VALUE_TYPE.get(name)
        );
    }

    public static ValueType parseOrFail(final String name) {
        return parse(name)
            .orElseThrow(() -> new IllegalArgumentException("Unknown type " + CharSequences.quoteIfChars(name)));
    }

    // constants........................................................................................................

    public final static ValueType ANY;

    public final static ValueType BOOLEAN;

    public final static ValueType DATE;

    public final static ValueType DATE_TIME;

    public final static ValueType EMAIL;

    public final static ValueType ERROR;

    public final static ValueType ERROR_LIST;

    public final static ValueType LOCALE;

    public final static ValueType NUMBER;

    public final static ValueType TEXT;

    public final static ValueType TIME;

    public final static ValueType WHOLE_NUMBER;

    /**
     * Private constructor
     */
    private ValueType(final String value,
                      final Class<?> type) {
        super();

        InvalidTextLengthException.throwIfFail(
            "name",
            value,
            MIN_LENGTH,
            MAX_LENGTH
        );

        this.value = value;
        this.type = type;

        // text ........................................................................................................
        final String text;

        if (false == "*".equals(value)) {
            CharPredicates.failIfNullOrEmptyOrInitialAndPartFalse(
                value,
                "name",
                CharPredicates.letter(),
                CharPredicates.letterOrDigit().or(CharPredicates.any(" /-"))
            );
        }

        // parent.......................................................................................................
        final int slash = value.lastIndexOf('/');
        if (-1 == slash) {
            this.parent = Optional.empty();
            text = value;
        } else {
            this.parent = parse(
                value.substring(0, slash)
            );
            text = value.substring(
                slash + 1
            );
        }

        this.text = text;

        // urlFragment..................................................................................................
        this.urlFragment = UrlFragment.with(
            value.replace('/',
                '-'
            )
        );
    }

    // HasValue.........................................................................................................

    @Override
    public String value() {
        return this.value;
    }

    private final String value;

    // type.............................................................................................................

    public Class<?> type() {
        return this.type;
    }

    private final Class<?> type;

    // parent...........................................................................................................

    /**
     * Extracts the prefix for the value type, aka the text before the last '/'.
     */
    public Optional<ValueType> parent() {
        return this.parent;
    }

    private final Optional<ValueType> parent;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return Objects.hash(
            this.value,
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
        return this.value.equals(other.value) &&
            this.type.equals(other.type);
    }

    @Override
    public String toString() {
        return this.value;
    }

    // Json.............................................................................................................

    static ValueType unmarshall(final JsonNode node,
                                final JsonNodeUnmarshallContext context) {
        final String value = node.stringOrFail();
        return parseOrFail(value);
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
        return this.text;
    }

    private final String text;

    // HasUrlFragment...................................................................................................

    /**
     * Returns the {@link UrlFragment}, holding the {@link #text()} with slashes replaced by dash.
     */
    @Override
    public UrlFragment urlFragment() {
        return this.urlFragment;
    }

    private final UrlFragment urlFragment;

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
        printer.println(this.value);
    }

    // Comparable.......................................................................................................

    @Override
    public int compareTo(final ValueType other) {
        return CASE_SENSITIVITY.comparator()
            .compare(
                this.value,
                other.value
            );
    }
}
