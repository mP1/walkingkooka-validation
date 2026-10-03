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

import org.junit.jupiter.api.Test;
import walkingkooka.HasValueTesting;
import walkingkooka.collect.list.BooleanList;
import walkingkooka.collect.list.CsvStringList;
import walkingkooka.collect.list.StringList;
import walkingkooka.compare.ComparableTesting2;
import walkingkooka.currency.CurrencyCode;
import walkingkooka.currency.CurrencyValue;
import walkingkooka.datetime.DateTimeSymbols;
import walkingkooka.environment.Environment;
import walkingkooka.math.DecimalNumberSymbols;
import walkingkooka.math.NumberList;
import walkingkooka.net.HasUrlFragmentTesting;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.net.header.HasContentTypeTesting;
import walkingkooka.predicate.PredicateTesting2;
import walkingkooka.reflect.PublicClassTesting;
import walkingkooka.test.ParseStringTesting;
import walkingkooka.text.HasTextTesting;
import walkingkooka.tree.expression.AddExpression;
import walkingkooka.tree.expression.CallExpression;
import walkingkooka.tree.expression.DivideExpression;
import walkingkooka.tree.expression.EqualsExpression;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.ExpressionNumber;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.GreaterThanEqualsExpression;
import walkingkooka.tree.expression.GreaterThanExpression;
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
import walkingkooka.tree.json.marshall.JsonNodeMarshallerTesting;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContexts;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

final public class ValueTypeTest implements PublicClassTesting<ValueType>,
    HasTextTesting,
    HasUrlFragmentTesting,
    HasValueTesting,
    HasValueTypeTesting,
    HasContentTypeTesting,
    ComparableTesting2<ValueType>,
    JsonNodeMarshallerTesting<ValueType>,
    ParseStringTesting<Optional<ValueType>>,
    PredicateTesting2<ValueType, ValueType> {

    // HasText..........................................................................................................

    @Test
    public void testText() {
        this.textAndCheck(
            ValueType.TEXT,
            "Text"
        );
    }

    @Test
    public void testTextWithDateTimeSymbols() {
        this.textAndCheck(
            ValueType.fromClassOrFail(DateTimeSymbols.class),
            "DateTimeSymbols"
        );
    }

    @Test
    public void testTextWithExpressionNumber() {
        this.textAndCheck(
            ValueType.fromClassOrFail(
                ExpressionNumber.class
            ),
            "Number"
        );
    }

    @Test
    public void testTextWithExpressionNumberBigDecimal() {
        this.textAndCheck(
            ValueType.fromClassOrFail(
                ExpressionNumberKind.BIG_DECIMAL.zero()
                    .getClass()
            ),
            "Number"
        );
    }

    @Test
    public void testTextWithExpressionNumberDouble() {
        this.textAndCheck(
            ValueType.fromClassOrFail(
                ExpressionNumberKind.DOUBLE.zero()
                    .getClass()
            ),
            "Number"
        );
    }

    @Test
    public void testTextWithString() {
        this.textAndCheck(
            ValueType.fromClassOrFail(String.class),
            "Text"
        );
    }

    // fromClass........................................................................................................

    @Test
    public void testFromClassWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValueType.fromClass(null)
        );
    }

    @Test
    public void testFromClassWithBigDecimal() {
        this.fromClassAndCheck(
            BigDecimal.class,
            "number/BigDecimal"
        );
    }

    @Test
    public void testFromClassWithBigInteger() {
        this.fromClassAndCheck(
            BigInteger.class,
            "number/wholeNumber/BigInteger"
        );
    }

    @Test
    public void testFromClassWithBooleanClass() {
        this.fromClassAndCheck(
            Boolean.class,
            ValueType.BOOLEAN
        );
    }

    @Test
    public void testFromClassWithBooleanList() {
        this.fromClassAndCheck(
            BooleanList.class,
            "list/BooleanList"
        );
    }

    @Test
    public void testFromClassWithByteType() {
        this.fromClassAndCheck(
            Byte.TYPE
        );
    }

    @Test
    public void testFromClassWithByteClass() {
        this.fromClassAndCheck(
            Byte.class,
            "number/wholeNumber/Byte"
        );
    }

    @Test
    public void testFromClassWithCallExpression() {
        this.fromClassAndCheck(
            CallExpression.class,
            "expression/CallExpression"
        );
    }

    @Test
    public void testFromClassWithCsvStringList() {
        this.fromClassAndCheck(
            CsvStringList.class,
            "list/Csv"
        );
    }

    @Test
    public void testFromClassWithCurrency() {
        this.fromClassAndCheck(
            Currency.class,
            "currency/Currency"
        );
    }

    @Test
    public void testFromClassWithCurrencyCode() {
        this.fromClassAndCheck(
            CurrencyCode.class,
            "currency/CurrencyCode"
        );
    }

    @Test
    public void testFromClassWithCurrencyValue() {
        this.fromClassAndCheck(
            CurrencyValue.class,
            "currency/CurrencyValue"
        );
    }

    @Test
    public void testFromClassWithDateTimeSymbols() {
        this.fromClassAndCheck(
            DateTimeSymbols.class,
            "date-time/DateTimeSymbols"
        );
    }

    @Test
    public void testFromClassWithDecimalNumberSymbols() {
        this.fromClassAndCheck(
            DecimalNumberSymbols.class,
            "number/DecimalNumberSymbols"
        );
    }

    @Test
    public void testFromClassWithDivideExpression() {
        this.fromClassAndCheck(
            DivideExpression.class,
            "expression/DivideExpression"
        );
    }

    @Test
    public void testFromClassWithDoubleType() {
        this.fromClassAndCheck(
            Double.TYPE
        );
    }

    @Test
    public void testFromClassWithDoubleClass() {
        this.fromClassAndCheck(
            Double.class,
            "number/Double"
        );
    }

    @Test
    public void testFromClassWithEmailAddress() {
        this.fromClassAndCheck(
            EmailAddress.class,
            "email/Email"
        );
    }

    @Test
    public void testFromClassWithEnvironment() {
        this.fromClassAndCheck(
            Environment.class,
            "environment/Environment"
        );
    }

    @Test
    public void testFromClassWithEqualsExpression() {
        this.fromClassAndCheck(
            EqualsExpression.class,
            "expression/EqualsExpression"
        );
    }

    @Test
    public void testFromClassWithExpression() {
        this.fromClassAndCheck(
            Expression.class,
            "expression"
        );
    }

    @Test
    public void testFromClassWithFloatType() {
        this.fromClassAndCheck(
            Float.TYPE
        );
    }

    @Test
    public void testFromClassWithFloatClass() {
        this.fromClassAndCheck(
            Float.class,
            "number/Float"
        );
    }

    @Test
    public void testFromClassWithGreaterThanExpression() {
        this.fromClassAndCheck(
            GreaterThanExpression.class,
            "expression/GreaterThanExpression"
        );
    }

    @Test
    public void testFromClassWithGreaterThanEqualsExpression() {
        this.fromClassAndCheck(
            GreaterThanEqualsExpression.class,
            "expression/GreaterThanEqualsExpression"
        );
    }

    @Test
    public void testFromClassWithIntegerType() {
        this.fromClassAndCheck(
            Integer.TYPE
        );
    }

    @Test
    public void testFromClassWithIntegerClass() {
        this.fromClassAndCheck(
            Integer.class,
            "number/wholeNumber/Integer"
        );
    }

    @Test
    public void testFromClassWithJsonArray() {
        this.fromClassAndCheck(
            JsonArray.class,
            "json/JsonArray"
        );
    }

    @Test
    public void testFromClassWithJsonBoolean() {
        this.fromClassAndCheck(
            JsonBoolean.class,
            "json/JsonBoolean"
        );
    }

    @Test
    public void testFromClassWithJsonNull() {
        this.fromClassAndCheck(
            JsonNull.class,
            "json/JsonNull"
        );
    }

    @Test
    public void testFromClassWithJsonNode() {
        this.fromClassAndCheck(
            JsonNode.class,
            "json"
        );
    }

    @Test
    public void testFromClassWithJsonNumber() {
        this.fromClassAndCheck(
            JsonNumber.class,
            "json/JsonNumber"
        );
    }

    @Test
    public void testFromClassWithJsonObject() {
        this.fromClassAndCheck(
            JsonObject.class,
            "json/JsonObject"
        );
    }

    @Test
    public void testFromClassWithJsonString() {
        this.fromClassAndCheck(
            JsonString.class,
            "json/JsonString"
        );
    }

    @Test
    public void testFromClassWithLessThanExpression() {
        this.fromClassAndCheck(
            LessThanExpression.class,
            "expression/LessThanExpression"
        );
    }

    @Test
    public void testFromClassWithLessThanEqualsExpression() {
        this.fromClassAndCheck(
            LessThanEqualsExpression.class,
            "expression/LessThanEqualsExpression"
        );
    }

    @Test
    public void testFromClassWithList() {
        this.fromClassAndCheck(
            List.class,
            "list"
        );
    }

    @Test
    public void testFromClassWithListExpression() {
        this.fromClassAndCheck(
            ListExpression.class,
            "expression/ListExpression"
        );
    }

    @Test
    public void testFromClassWithLocalDate() {
        this.fromClassAndCheck(
            LocalDate.class,
            ValueType.DATE
        );
    }

    @Test
    public void testFromClassWithLocalDateTime() {
        this.fromClassAndCheck(
            LocalDateTime.class,
            ValueType.DATE_TIME
        );
    }

    @Test
    public void testFromClassWithLocalTime() {
        this.fromClassAndCheck(
            LocalTime.class,
            ValueType.TIME
        );
    }

    @Test
    public void testFromClassWithLongType() {
        this.fromClassAndCheck(
            Long.TYPE
        );
    }

    @Test
    public void testFromClassWithLongClass() {
        this.fromClassAndCheck(
            Long.class,
            "number/wholeNumber/Long"
        );
    }

    @Test
    public void testFromClassWithModuloExpression() {
        this.fromClassAndCheck(
            ModuloExpression.class,
            "expression/ModuloExpression"
        );
    }

    @Test
    public void testFromClassWithMultiplyExpression() {
        this.fromClassAndCheck(
            MultiplyExpression.class,
            "expression/MultiplyExpression"
        );
    }

    @Test
    public void testFromClassWithNamedFunctionExpression() {
        this.fromClassAndCheck(
            NamedFunctionExpression.class,
            "expression/NamedFunctionExpression"
        );
    }

    @Test
    public void testFromClassWithNegativeExpression() {
        this.fromClassAndCheck(
            NegativeExpression.class,
            "expression/NegativeExpression"
        );
    }

    @Test
    public void testFromClassWithNotEqualsExpression() {
        this.fromClassAndCheck(
            NotEqualsExpression.class,
            "expression/NotEqualsExpression"
        );
    }

    @Test
    public void testFromClassWithNotExpression() {
        this.fromClassAndCheck(
            NotExpression.class,
            "expression/NotExpression"
        );
    }

    @Test
    public void testFromClassWithNumberList() {
        this.fromClassAndCheck(
            NumberList.class,
            "list/NumberList"
        );
    }

    @Test
    public void testFromClassWithObject() {
        this.fromClassAndCheck(
            Object.class,
            ValueType.ANY
        );
    }

    @Test
    public void testFromClassWithOrExpression() {
        this.fromClassAndCheck(
            OrExpression.class,
            "expression/OrExpression"
        );
    }

    @Test
    public void testFromClassWithPowerExpression() {
        this.fromClassAndCheck(
            PowerExpression.class,
            "expression/PowerExpression"
        );
    }

    @Test
    public void testFromClassWithReferenceExpression() {
        this.fromClassAndCheck(
            ReferenceExpression.class,
            "expression/ReferenceExpression"
        );
    }

    @Test
    public void testFromClassWithShortType() {
        this.fromClassAndCheck(
            Short.TYPE
        );
    }

    @Test
    public void testFromClassWithShortClass() {
        this.fromClassAndCheck(
            Short.class,
            "number/wholeNumber/Short"
        );
    }

    @Test
    public void testFromClassWithString() {
        this.fromClassAndCheck(
            String.class,
            ValueType.parse("text/Text")
        );
    }

    @Test
    public void testFromClassWithStringBuffer() {
        this.fromClassAndCheck(
            StringBuffer.class,
            "text/StringBuffer"
        );
    }

    @Test
    public void testFromClassWithStringBuilder() {
        this.fromClassAndCheck(
            StringBuilder.class,
            "text/StringBuilder"
        );
    }

    @Test
    public void testFromClassWithStringList() {
        this.fromClassAndCheck(
            StringList.class,
            "list/StringList"
        );
    }

    @Test
    public void testFromClassWithSubtractExpression() {
        this.fromClassAndCheck(
            SubtractExpression.class,
            "expression/SubtractExpression"
        );
    }

    @Test
    public void testFromClassWithValidationChoiceList() {
        this.fromClassAndCheck(
            ValidationChoiceList.class,
            "list/ChoiceList"
        );
    }

    @Test
    public void testFromClassWithValidationError() {
        this.fromClassAndCheck(
            ValidationError.class,
            ValueType.ERROR
        );
    }

    @Test
    public void testFromClassWithValidationErrorList() {
        this.fromClassAndCheck(
            ValidationErrorList.class,
            ValueType.ERROR_LIST
        );
    }

    @Test
    public void testFromClassWithValueExpression() {
        this.fromClassAndCheck(
            ValueExpression.class,
            "expression/ValueExpression"
        );
    }

    @Test
    public void testFromClassWithXorExpression() {
        this.fromClassAndCheck(
            XorExpression.class,
            "expression/XorExpression"
        );
    }

    private void fromClassAndCheck(final Class<?> klass) {
        this.checkEquals(
            Optional.empty(),
            ValueType.fromClass(klass)
        );
    }

    private void fromClassAndCheck(final Class<?> klass,
                                   final String expected) {
        final ValueType valueType = ValueType.fromClass(klass)
            .orElseThrow(() -> new AssertionError("Missing " + klass));

        this.valueAndCheck(
            valueType,
            expected
        );

        this.checkEquals(
            klass,
            valueType.type(),
            "type"
        );
    }

    private void fromClassAndCheck(final Class<?> klass,
                                   final ValueType expected) {
        this.fromClassAndCheck(
            klass,
            Optional.of(expected)
        );
    }

    private void fromClassAndCheck(final Class<?> klass,
                                   final Optional<ValueType> expected) {
        this.checkEquals(
            expected,
            ValueType.fromClass(klass)
        );
    }

    // parse............................................................................................................

    @Override
    public void testParseStringEmptyFails() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void testParseWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValueType.parse(null)
        );
    }

    @Test
    public void testParseWithBigDecimal() {
        this.parseStringAndCheck(
            BigDecimal.class,
            "number/BigDecimal"
        );
    }

    @Test
    public void testParseWithBigInteger() {
        this.parseStringAndCheck(
            BigInteger.class,
            "number/wholeNumber/BigInteger"
        );
    }

    @Test
    public void testParseWithBooleanType() {
        this.parseStringAndCheck(
            Boolean.TYPE
        );
    }

    @Test
    public void testFromParseWithBooleanClass() {
        this.parseStringAndCheck(
            Boolean.class,
            ValueType.BOOLEAN
        );
    }

    @Test
    public void testParseWithBooleanList() {
        this.parseStringAndCheck(
            BooleanList.class,
            "list/BooleanList"
        );
    }

    @Test
    public void testParseWithByteType() {
        this.parseStringAndCheck(
            Byte.TYPE
        );
    }

    @Test
    public void testFromParseWithByteClass() {
        this.parseStringAndCheck(
            Byte.class,
            "number/wholeNumber/Byte"
        );
    }

    @Test
    public void testParseWithCallExpression() {
        this.parseStringAndCheck(
            CallExpression.class,
            "expression/CallExpression"
        );
    }

    @Test
    public void testParseWithCsvStringList() {
        this.parseStringAndCheck(
            CsvStringList.class,
            "list/Csv"
        );
    }

    @Test
    public void testParseWithCurrency() {
        this.parseStringAndCheck(
            Currency.class,
            "currency/Currency"
        );
    }

    @Test
    public void testParseWithCurrencyCode() {
        this.parseStringAndCheck(
            CurrencyCode.class,
            "currency/CurrencyCode"
        );
    }

    @Test
    public void testParseWithCurrencyValue() {
        this.parseStringAndCheck(
            CurrencyValue.class,
            "currency/CurrencyValue"
        );
    }

    @Test
    public void testParseWithDateTimeSymbols() {
        this.parseStringAndCheck(
            DateTimeSymbols.class,
            "date-time/DateTimeSymbols"
        );
    }

    @Test
    public void testParseWithDecimalNumberSymbols() {
        this.parseStringAndCheck(
            DecimalNumberSymbols.class,
            "number/DecimalNumberSymbols"
        );
    }

    @Test
    public void testParseWithDivideExpression() {
        this.parseStringAndCheck(
            DivideExpression.class,
            "expression/DivideExpression"
        );
    }

    @Test
    public void testParseWithDoubleType() {
        this.parseStringAndCheck(
            Double.TYPE
        );
    }

    @Test
    public void testFromParseWithDoubleClass() {
        this.parseStringAndCheck(
            Double.class,
            "number/Double"
        );
    }

    @Test
    public void testParseWithEmailAddress() {
        this.parseStringAndCheck(
            EmailAddress.class,
            "email/Email"
        );
    }

    @Test
    public void testParseWithEmailEmail() {
        this.parseStringAndCheck(
            EmailAddress.class,
            "email/Email"
        );
    }

    @Test
    public void testParseWithEnvironment() {
        this.parseStringAndCheck(
            Environment.class,
            "environment/Environment"
        );
    }

    @Test
    public void testParseWithEqualsExpression() {
        this.parseStringAndCheck(
            EqualsExpression.class,
            "expression/EqualsExpression"
        );
    }

    @Test
    public void testParseWithExpression() {
        this.parseStringAndCheck(
            Expression.class,
            "expression"
        );
    }

    @Test
    public void testParseWithExpressionNumber() {
        this.parseStringAndCheck(
            ExpressionNumber.class,
            "number/Number"
        );
    }

    @Test
    public void testParseWithExpressionNumberBigDecimal() {
        this.parseStringAndCheck(
            ExpressionNumberKind.BIG_DECIMAL.zero()
                .getClass()
                .getName(),
            "number/Number"
        );
    }

    @Test
    public void testParseWithExpressionNumberDouble() {
        this.parseStringAndCheck(
            ExpressionNumberKind.DOUBLE.zero()
                .getClass()
                .getName(),
            "number/Number"
        );
    }

    @Test
    public void testParseWithFloatType() {
        this.parseStringAndCheck(
            Float.TYPE
        );
    }

    @Test
    public void testFromParseWithFloatClass() {
        this.parseStringAndCheck(
            Float.class,
            "number/Float"
        );
    }

    @Test
    public void testParseWithGreaterThanExpression() {
        this.parseStringAndCheck(
            GreaterThanExpression.class,
            "expression/GreaterThanExpression"
        );
    }

    @Test
    public void testParseWithGreaterThanEqualsExpression() {
        this.parseStringAndCheck(
            GreaterThanEqualsExpression.class,
            "expression/GreaterThanEqualsExpression"
        );
    }

    @Test
    public void testParseWithIntegerType() {
        this.parseStringAndCheck(
            Integer.TYPE
        );
    }

    @Test
    public void testFromParseWithIntegerClass() {
        this.parseStringAndCheck(
            Integer.class,
            "number/wholeNumber/Integer"
        );
    }

    @Test
    public void testParseWithJsonArray() {
        this.parseStringAndCheck(
            JsonArray.class,
            "json/JsonArray"
        );
    }

    @Test
    public void testParseWithJsonBoolean() {
        this.parseStringAndCheck(
            JsonBoolean.class,
            "json/JsonBoolean"
        );
    }

    @Test
    public void testParseWithJsonNull() {
        this.parseStringAndCheck(
            JsonNull.class,
            "json/JsonNull"
        );
    }

    @Test
    public void testParseWithJsonNode() {
        this.parseStringAndCheck(
            JsonNode.class,
            "json"
        );
    }

    @Test
    public void testParseWithJsonNumber() {
        this.parseStringAndCheck(
            JsonNumber.class,
            "json/JsonNumber"
        );
    }

    @Test
    public void testParseWithJsonObject() {
        this.parseStringAndCheck(
            JsonObject.class,
            "json/JsonObject"
        );
    }

    @Test
    public void testParseWithJsonString() {
        this.parseStringAndCheck(
            JsonString.class,
            "json/JsonString"
        );
    }

    @Test
    public void testParseWithLessThanExpression() {
        this.parseStringAndCheck(
            LessThanExpression.class,
            "expression/LessThanExpression"
        );
    }

    @Test
    public void testParseWithLessThanEqualsExpression() {
        this.parseStringAndCheck(
            LessThanEqualsExpression.class,
            "expression/LessThanEqualsExpression"
        );
    }

    @Test
    public void testParseWithList() {
        this.parseStringAndCheck(
            List.class,
            "list"
        );
    }

    @Test
    public void testParseWithListExpression() {
        this.parseStringAndCheck(
            ListExpression.class,
            "expression/ListExpression"
        );
    }

    @Test
    public void testParseWithLocalDate() {
        this.parseStringAndCheck(
            LocalDate.class,
            ValueType.DATE
        );
    }

    @Test
    public void testParseWithLocalDateTime() {
        this.parseStringAndCheck(
            LocalDateTime.class,
            ValueType.DATE_TIME
        );
    }

    @Test
    public void testParseWithLocalTime() {
        this.parseStringAndCheck(
            LocalTime.class,
            ValueType.TIME
        );
    }

    @Test
    public void testParseWithLongType() {
        this.parseStringAndCheck(
            Long.TYPE
        );
    }

    @Test
    public void testFromParseWithLongClass() {
        this.parseStringAndCheck(
            Long.class,
            "number/wholeNumber/Long"
        );
    }

    @Test
    public void testParseWithModuloExpression() {
        this.parseStringAndCheck(
            ModuloExpression.class,
            "expression/ModuloExpression"
        );
    }

    @Test
    public void testParseWithMultiplyExpression() {
        this.parseStringAndCheck(
            MultiplyExpression.class,
            "expression/MultiplyExpression"
        );
    }

    @Test
    public void testParseWithNamedFunctionExpression() {
        this.parseStringAndCheck(
            NamedFunctionExpression.class,
            "expression/NamedFunctionExpression"
        );
    }

    @Test
    public void testParseWithNegativeExpression() {
        this.parseStringAndCheck(
            NegativeExpression.class,
            "expression/NegativeExpression"
        );
    }

    @Test
    public void testParseWithNotEqualsExpression() {
        this.parseStringAndCheck(
            NotEqualsExpression.class,
            "expression/NotEqualsExpression"
        );
    }

    @Test
    public void testParseWithNotExpression() {
        this.parseStringAndCheck(
            NotExpression.class,
            "expression/NotExpression"
        );
    }

    @Test
    public void testParseWithNumberList() {
        this.parseStringAndCheck(
            NumberList.class,
            "list/NumberList"
        );
    }

    @Test
    public void testParseWithObject() {
        this.parseStringAndCheck(
            Object.class,
            ValueType.ANY
        );
    }

    @Test
    public void testParseWithOrExpression() {
        this.parseStringAndCheck(
            OrExpression.class,
            "expression/OrExpression"
        );
    }

    @Test
    public void testParseWithPowerExpression() {
        this.parseStringAndCheck(
            PowerExpression.class,
            "expression/PowerExpression"
        );
    }

    @Test
    public void testParseWithReferenceExpression() {
        this.parseStringAndCheck(
            ReferenceExpression.class,
            "expression/ReferenceExpression"
        );
    }

    @Test
    public void testParseWithShortType() {
        this.parseStringAndCheck(
            Short.TYPE
        );
    }

    @Test
    public void testFromParseWithShortClass() {
        this.parseStringAndCheck(
            Short.class,
            "number/wholeNumber/Short"
        );
    }

    @Test
    public void testParseWithString() {
        this.parseStringAndCheck(
            String.class,
            "text/Text"
        );
    }

    @Test
    public void testParseWithStringBuffer() {
        this.parseStringAndCheck(
            StringBuffer.class,
            "text/StringBuffer"
        );
    }

    @Test
    public void testParseWithStringBuilder() {
        this.parseStringAndCheck(
            StringBuilder.class,
            "text/StringBuilder"
        );
    }

    @Test
    public void testParseWithStringList() {
        this.parseStringAndCheck(
            StringList.class,
            "list/StringList"
        );
    }

    @Test
    public void testParseWithSubtractExpression() {
        this.parseStringAndCheck(
            SubtractExpression.class,
            "expression/SubtractExpression"
        );
    }

    @Test
    public void testParseWithValidationChoiceList() {
        this.parseStringAndCheck(
            ValidationChoiceList.class,
            "list/ChoiceList"
        );
    }

    @Test
    public void testParseWithValidationError() {
        this.parseStringAndCheck(
            ValidationError.class,
            ValueType.ERROR
        );
    }

    @Test
    public void testParseWithValidationErrorList() {
        this.parseStringAndCheck(
            ValidationErrorList.class,
            ValueType.ERROR_LIST
        );
    }

    @Test
    public void testParseWithValueExpression() {
        this.parseStringAndCheck(
            ValueExpression.class,
            "expression/ValueExpression"
        );
    }

    @Test
    public void testParseWithXorExpression() {
        this.parseStringAndCheck(
            XorExpression.class,
            "expression/XorExpression"
        );
    }

    @Test
    public void testParseWithTextNumberParent() {
        this.parseStringAndCheck(
            "number",
            ValueType.NUMBER_PARENT
        );
    }

    @Test
    public void testParseWithTextNumber() {
        this.parseStringAndCheck(
            "Number",
            ValueType.NUMBER
        );
    }

    @Test
    public void testParseWithTextText() {
        this.parseStringAndCheck(
            "Text",
            ValueType.TEXT
        );
    }

    private void parseStringAndCheck(final Class<?> klass) {
        this.parseStringAndCheck(
            klass.getName(),
            Optional.empty()
        );
    }

    private void parseStringAndCheck(final String name,
                                     final String expected) {
        final ValueType valueType = this.parseString(name)
            .orElseThrow(() -> new AssertionError("Missing " + name));

        this.valueAndCheck(
            valueType,
            expected
        );
    }

    private void parseStringAndCheck(final Class<?> klass,
                                     final String expected) {
        final ValueType valueType = this.parseString(klass.getName())
            .orElseThrow(() -> new AssertionError("Missing " + klass));

        this.parseStringAndCheck(
            valueType.value(),
            Optional.of(valueType)
        );

        this.valueAndCheck(
            valueType,
            expected
        );

        this.checkEquals(
            klass,
            valueType.type(),
            "type"
        );
    }

    private void parseStringAndCheck(final Class<?> klass,
                                     final ValueType expected) {
        this.parseStringAndCheck(
            klass,
            Optional.of(expected)
        );
    }

    private void parseStringAndCheck(final Class<?> klass,
                                     final Optional<ValueType> expected) {
        this.checkEquals(
            expected,
            ValueType.parse(klass.getName())
        );
    }

    private void parseStringAndCheck(final String text,
                                     final ValueType expected) {
        this.parseStringAndCheck(
            text,
            Optional.of(expected)
        );
    }

    @Override
    public Optional<ValueType> parseString(final String text) {
        return ValueType.parse(text);
    }

    @Override
    public Class<? extends RuntimeException> parseStringFailedExpected(final Class<? extends RuntimeException> thrown) {
        return thrown;
    }

    @Override
    public RuntimeException parseStringFailedExpected(final RuntimeException thrown) {
        return thrown;
    }

    // parent...........................................................................................................

    @Test
    public void testParentWithWholeNumber() {
        this.parentAndCheck(
            ValueType.WHOLE_NUMBER_PARENT,
            ValueType.NUMBER_PARENT
        );
    }

    @Test
    public void testParentWithByte() {
        this.parentAndCheck(
            Byte.class,
            ValueType.WHOLE_NUMBER_PARENT
        );
    }

    @Test
    public void testParentWithShort() {
        this.parentAndCheck(
            Short.class,
            ValueType.WHOLE_NUMBER_PARENT
        );
    }

    @Test
    public void testParentWithInteger() {
        this.parentAndCheck(
            Integer.class,
            ValueType.WHOLE_NUMBER_PARENT
        );
    }

    @Test
    public void testParentWithLong() {
        this.parentAndCheck(
            Long.class,
            ValueType.WHOLE_NUMBER_PARENT
        );
    }

    @Test
    public void testParentWithBigInteger() {
        this.parentAndCheck(
            BigInteger.class,
            ValueType.WHOLE_NUMBER_PARENT
        );
    }

    @Test
    public void testParentWithNumber() {
        this.parentAndCheck(
            ValueType.NUMBER_PARENT
        );
    }

    @Test
    public void testParentWithFloat() {
        this.parentAndCheck(
            Float.class,
            ValueType.NUMBER_PARENT
        );
    }

    @Test
    public void testParentWithDouble() {
        this.parentAndCheck(
            Double.class,
            ValueType.NUMBER_PARENT
        );
    }

    @Test
    public void testParentWithBigDecimal() {
        this.parentAndCheck(
            BigDecimal.class,
            ValueType.NUMBER_PARENT
        );
    }

    @Test
    public void testParentWithEmailAddress() {
        this.parentAndCheck(
            EmailAddress.class,
            ValueType.EMAIL_PARENT
        );
    }

    @Test
    public void testParentWithExpressionNumberBigDecimal() {
        this.parentAndCheck(
            ExpressionNumberKind.BIG_DECIMAL.zero()
                .getClass(),
            ValueType.NUMBER_PARENT
        );
    }

    @Test
    public void testParentWithExpressionNumberDouble() {
        this.parentAndCheck(
            ExpressionNumberKind.DOUBLE.zero()
                .getClass(),
            ValueType.NUMBER_PARENT
        );
    }

    @Test
    public void testParentWithExpressionAdd() {
        this.parentAndCheck(
            AddExpression.class,
            Expression.class
        );
    }

    private void parentAndCheck(final Class<?> type) {
        this.parentAndCheck(
            ValueType.fromClassOrFail(type)
        );
    }

    private void parentAndCheck(final ValueType valueType) {
        this.parentAndCheck(
            valueType,
            Optional.empty()
        );
    }

    private void parentAndCheck(final Class<?> type,
                                final Class<?> expected) {
        this.parentAndCheck(
            ValueType.fromClassOrFail(type),
            ValueType.fromClass(expected)
        );
    }

    private void parentAndCheck(final Class<?> type,
                                final ValueType expected) {
        this.parentAndCheck(
            ValueType.fromClassOrFail(type),
            expected
        );
    }

    private void parentAndCheck(final ValueType valueType,
                                final ValueType expected) {
        this.parentAndCheck(
            valueType,
            Optional.of(expected)
        );
    }

    private void parentAndCheck(final ValueType valueType,
                                final Optional<ValueType> expected) {
        this.checkEquals(
            expected,
            valueType.parent(),
            valueType::toString
        );
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferentValue() {
        this.checkNotEquals(ValueType.EMAIL);
    }

    @Override
    public ValueType createComparable() {
        return ValueType.TEXT;
    }

    // json.............................................................................................................

    @Test
    public void testUnmarshallAny() {
        this.unmarshallAndCheck2(
            "*",
            ValueType.ANY
        );
    }

    @Test
    public void testUnmarshallBoolean() {
        this.unmarshallAndCheck2(
            "Boolean",
            ValueType.BOOLEAN
        );
    }

    @Test
    public void testUnmarshallDate() {
        this.unmarshallAndCheck2(
            "date-time/Date",
            ValueType.DATE
        );
    }

    @Test
    public void testUnmarshallDateTime() {
        this.unmarshallAndCheck2(
            "date-time/DateTime",
            ValueType.DATE_TIME
        );
    }

    @Test
    public void testUnmarshallEmail() {
        this.unmarshallAndCheck2(
            "email/Email",
            ValueType.EMAIL
        );
    }

    @Test
    public void testUnmarshallTextFull() {
        this.unmarshallAndCheck2(
            "text/Text",
            ValueType.TEXT
        );
    }

    @Test
    public void testUnmarshallTextWithText() {
        this.unmarshallAndCheck2(
            "Text",
            ValueType.TEXT
        );
    }

    @Test
    public void testUnmarshallTime() {
        this.unmarshallAndCheck2(
            "Time",
            ValueType.TIME
        );
    }

    private void unmarshallAndCheck2(final String string,
                                     final ValueType expected) {
        assertSame(
            expected,
            ValueType.unmarshall(
                JsonNode.string(string),
                JsonNodeUnmarshallContexts.fake()
            )
        );
    }

    @Override
    public ValueType createJsonNodeMarshallingValue() {
        return ValueType.TEXT;
    }

    @Override
    public ValueType unmarshall(final JsonNode from,
                                final JsonNodeUnmarshallContext context) {
        return ValueType.unmarshall(
            from,
            context
        );
    }

    // HasContentType...................................................................................................

    @Test
    public void testContentType() {
        this.contentTypeAndCheck(
            this.createObject(),
            "application/json+walkingkooka.validation.ValueType"
        );
    }

    // HasUrlFragment...................................................................................................

    @Test
    public void testUrlFragmentWithNumberParent() {
        this.urlFragmentAndCheck(
            ValueType.NUMBER_PARENT,
            "number"
        );
    }

    @Test
    public void testUrlFragmentWithNumber() {
        this.urlFragmentAndCheck(
            ValueType.NUMBER,
            "Number"
        );
    }

    @Test
    public void testUrlFragmentWithWholeNumber() {
        this.urlFragmentAndCheck(
            ValueType.WHOLE_NUMBER_PARENT,
            "wholeNumber"
        );
    }

    @Test
    public void testUrlFragmentWithInteger() {
        this.urlFragmentAndCheck(
            ValueType.fromClassOrFail(Integer.class),
            "Integer"
        );
    }

    // Predicate........................................................................................................

    @Test
    public void testTestTextWithString() {
        this.testAndCheck(
            ValueType.TEXT,
            ValueType.fromClassOrFail(String.class),
            true
        );
    }

    @Test
    public void testTestNumberParentWithNumber() {
        this.testAndCheck(
            ValueType.NUMBER_PARENT,
            ValueType.NUMBER,
            true
        );
    }

    @Test
    public void testTestNumberParentWithNumberParent() {
        this.testAndCheck(
            ValueType.NUMBER_PARENT,
            ValueType.NUMBER_PARENT,
            true
        );
    }

    @Test
    public void testTestNumberWithByte() {
        this.testAndCheck(
            ValueType.NUMBER_PARENT,
            ValueType.fromClassOrFail(Byte.class),
            true
        );
    }

    @Test
    public void testTestNumberWithFloat() {
        this.testAndCheck(
            ValueType.NUMBER_PARENT,
            ValueType.fromClassOrFail(Float.class),
            true
        );
    }

    @Test
    public void testTestWholeNumberWithByte() {
        this.testAndCheck(
            ValueType.WHOLE_NUMBER_PARENT,
            ValueType.fromClassOrFail(Byte.class),
            true
        );
    }

    @Test
    public void testTestWholeNumberWithFloat() {
        this.testAndCheck(
            ValueType.WHOLE_NUMBER_PARENT,
            ValueType.fromClassOrFail(Float.class),
            false
        );
    }

    @Override
    public ValueType createPredicate() {
        return this.createComparable();
    }

    // HasValueType.....................................................................................................

    @Test
    public void testValueType() {
        this.valueTypeAndCheck(
            ValueType.TEXT,
            ValueType.TEXT
        );
    }

    // class............................................................................................................

    @Override
    public Class<ValueType> type() {
        return ValueType.class;
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
