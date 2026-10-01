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
import walkingkooka.net.email.EmailAddress;
import walkingkooka.net.header.HasContentTypeTesting;
import walkingkooka.predicate.PredicateTesting2;
import walkingkooka.reflect.PublicClassTesting;
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
    HasValueTesting,
    HasContentTypeTesting,
    ComparableTesting2<ValueType>,
    JsonNodeMarshallerTesting<ValueType>,
    PredicateTesting2<ValueType, ValueType> {

    // HasText..........................................................................................................

    @Test
    public void testText() {
        this.textAndCheck(
            ValueType.TEXT,
            "text"
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
            "number/big-decimal"
        );
    }

    @Test
    public void testFromClassWithBigInteger() {
        this.fromClassAndCheck(
            BigInteger.class,
            "number/whole/big-integer"
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
            "list/boolean"
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
            "number/whole/byte"
        );
    }

    @Test
    public void testFromClassWithCallExpression() {
        this.fromClassAndCheck(
            CallExpression.class,
            "expression/call"
        );
    }

    @Test
    public void testFromClassWithCsvStringList() {
        this.fromClassAndCheck(
            CsvStringList.class,
            "list/csv"
        );
    }

    @Test
    public void testFromClassWithCurrency() {
        this.fromClassAndCheck(
            Currency.class,
            "currency"
        );
    }

    @Test
    public void testFromClassWithCurrencyCode() {
        this.fromClassAndCheck(
            CurrencyCode.class,
            "currency-code"
        );
    }

    @Test
    public void testFromClassWithCurrencyValue() {
        this.fromClassAndCheck(
            CurrencyValue.class,
            "currency-value"
        );
    }

    @Test
    public void testFromClassWithDateTimeSymbols() {
        this.fromClassAndCheck(
            DateTimeSymbols.class,
            "date/date-time-symbols"
        );
    }

    @Test
    public void testFromClassWithDecimalNumberSymbols() {
        this.fromClassAndCheck(
            DecimalNumberSymbols.class,
            "number/decimal-number-symbols"
        );
    }

    @Test
    public void testFromClassWithDivideExpression() {
        this.fromClassAndCheck(
            DivideExpression.class,
            "expression/divide"
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
            "number/double"
        );
    }

    @Test
    public void testFromClassWithEmail() {
        this.fromClassAndCheck(
            EmailAddress.class,
            ValueType.EMAIL
        );
    }

    @Test
    public void testFromClassWithEmailAddress() {
        this.fromClassAndCheck(
            EmailAddress.class,
            ValueType.EMAIL
        );
    }

    @Test
    public void testFromClassWithEnvironment() {
        this.fromClassAndCheck(
            Environment.class,
            "environment"
        );
    }

    @Test
    public void testFromClassWithEqualsExpression() {
        this.fromClassAndCheck(
            EqualsExpression.class,
            "expression/equals"
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
            "number/float"
        );
    }

    @Test
    public void testFromClassWithGreaterThanExpression() {
        this.fromClassAndCheck(
            GreaterThanExpression.class,
            "expression/greater-than"
        );
    }

    @Test
    public void testFromClassWithGreaterThanEqualsExpression() {
        this.fromClassAndCheck(
            GreaterThanEqualsExpression.class,
            "expression/greater-than-equals"
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
            "number/whole/integer"
        );
    }

    @Test
    public void testFromClassWithJsonArray() {
        this.fromClassAndCheck(
            JsonArray.class,
            "json/array"
        );
    }

    @Test
    public void testFromClassWithJsonBoolean() {
        this.fromClassAndCheck(
            JsonBoolean.class,
            "json/boolean"
        );
    }

    @Test
    public void testFromClassWithJsonNull() {
        this.fromClassAndCheck(
            JsonNull.class,
            "json/null"
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
            "json/number"
        );
    }

    @Test
    public void testFromClassWithJsonObject() {
        this.fromClassAndCheck(
            JsonObject.class,
            "json/object"
        );
    }

    @Test
    public void testFromClassWithJsonString() {
        this.fromClassAndCheck(
            JsonString.class,
            "json/string"
        );
    }

    @Test
    public void testFromClassWithLessThanExpression() {
        this.fromClassAndCheck(
            LessThanExpression.class,
            "expression/less-than"
        );
    }

    @Test
    public void testFromClassWithLessThanEqualsExpression() {
        this.fromClassAndCheck(
            LessThanEqualsExpression.class,
            "expression/less-than-equals"
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
            "expression/list"
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
            "number/whole/long"
        );
    }

    @Test
    public void testFromClassWithModuloExpression() {
        this.fromClassAndCheck(
            ModuloExpression.class,
            "expression/modulo"
        );
    }

    @Test
    public void testFromClassWithMultiplyExpression() {
        this.fromClassAndCheck(
            MultiplyExpression.class,
            "expression/multiply"
        );
    }

    @Test
    public void testFromClassWithNamedFunctionExpression() {
        this.fromClassAndCheck(
            NamedFunctionExpression.class,
            "expression/named-function"
        );
    }

    @Test
    public void testFromClassWithNegativeExpression() {
        this.fromClassAndCheck(
            NegativeExpression.class,
            "expression/negative"
        );
    }

    @Test
    public void testFromClassWithNotEqualsExpression() {
        this.fromClassAndCheck(
            NotEqualsExpression.class,
            "expression/not-equals"
        );
    }

    @Test
    public void testFromClassWithNotExpression() {
        this.fromClassAndCheck(
            NotExpression.class,
            "expression/not"
        );
    }

    @Test
    public void testFromClassWithNumberList() {
        this.fromClassAndCheck(
            NumberList.class,
            "list/number"
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
            "expression/or"
        );
    }

    @Test
    public void testFromClassWithPowerExpression() {
        this.fromClassAndCheck(
            PowerExpression.class,
            "expression/power"
        );
    }

    @Test
    public void testFromClassWithReferenceExpression() {
        this.fromClassAndCheck(
            ReferenceExpression.class,
            "expression/reference"
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
            "number/whole/short"
        );
    }

    @Test
    public void testFromClassWithString() {
        this.fromClassAndCheck(
            String.class,
            ValueType.TEXT
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
            "list/string"
        );
    }

    @Test
    public void testFromClassWithSubtractExpression() {
        this.fromClassAndCheck(
            SubtractExpression.class,
            "expression/subtract"
        );
    }

    @Test
    public void testFromClassWithValidationChoiceList() {
        this.fromClassAndCheck(
            ValidationChoiceList.class,
            "list/choice"
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
            "expression/value"
        );
    }

    @Test
    public void testFromClassWithXorExpression() {
        this.fromClassAndCheck(
            XorExpression.class,
            "expression/xor"
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

    // fromClassName....................................................................................................

    @Test
    public void testFromClassNameWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValueType.fromClassName(null)
        );
    }

    @Test
    public void testFromClassNameWithBigDecimal() {
        this.fromClassNameAndCheck(
            BigDecimal.class,
            "number/big-decimal"
        );
    }

    @Test
    public void testFromClassNameWithBigInteger() {
        this.fromClassNameAndCheck(
            BigInteger.class,
            "number/whole/big-integer"
        );
    }

    @Test
    public void testFromClassNameWithBooleanType() {
        this.fromClassNameAndCheck(
            Boolean.TYPE,
            ValueType.BOOLEAN
        );
    }

    @Test
    public void testFromClassNameWithBooleanClass() {
        this.fromClassNameAndCheck(
            Boolean.class,
            ValueType.BOOLEAN
        );
    }

    @Test
    public void testFromClassNameWithBooleanList() {
        this.fromClassNameAndCheck(
            BooleanList.class,
            "list/boolean"
        );
    }

    @Test
    public void testFromClassNameWithByteType() {
        this.fromClassNameAndCheck(
            Byte.TYPE
        );
    }

    @Test
    public void testFromClassNameWithByteClass() {
        this.fromClassNameAndCheck(
            Byte.class,
            "number/whole/byte"
        );
    }

    @Test
    public void testFromClassNameWithCallExpression() {
        this.fromClassNameAndCheck(
            CallExpression.class,
            "expression/call"
        );
    }

    @Test
    public void testFromClassNameWithCsvStringList() {
        this.fromClassNameAndCheck(
            CsvStringList.class,
            "list/csv"
        );
    }

    @Test
    public void testFromClassNameWithCurrency() {
        this.fromClassNameAndCheck(
            Currency.class,
            "currency"
        );
    }

    @Test
    public void testFromClassNameWithCurrencyCode() {
        this.fromClassNameAndCheck(
            CurrencyCode.class,
            "currency-code"
        );
    }

    @Test
    public void testFromClassNameWithCurrencyValue() {
        this.fromClassNameAndCheck(
            CurrencyValue.class,
            "currency-value"
        );
    }

    @Test
    public void testFromClassNameWithDateTimeSymbols() {
        this.fromClassNameAndCheck(
            DateTimeSymbols.class,
            "date/date-time-symbols"
        );
    }

    @Test
    public void testFromClassNameWithDecimalNumberSymbols() {
        this.fromClassNameAndCheck(
            DecimalNumberSymbols.class,
            "number/decimal-number-symbols"
        );
    }

    @Test
    public void testFromClassNameWithDivideExpression() {
        this.fromClassNameAndCheck(
            DivideExpression.class,
            "expression/divide"
        );
    }

    @Test
    public void testFromClassNameWithDoubleType() {
        this.fromClassNameAndCheck(
            Double.TYPE
        );
    }

    @Test
    public void testFromClassNameWithDoubleClass() {
        this.fromClassNameAndCheck(
            Double.class,
            "number/double"
        );
    }

    @Test
    public void testFromClassNameWithEmail() {
        this.fromClassNameAndCheck(
            EmailAddress.class,
            ValueType.EMAIL
        );
    }

    @Test
    public void testFromClassNameWithEmailAddress() {
        this.fromClassNameAndCheck(
            EmailAddress.class,
            ValueType.EMAIL
        );
    }

    @Test
    public void testFromClassNameWithEnvironment() {
        this.fromClassNameAndCheck(
            Environment.class,
            "environment"
        );
    }

    @Test
    public void testFromClassNameWithEqualsExpression() {
        this.fromClassNameAndCheck(
            EqualsExpression.class,
            "expression/equals"
        );
    }

    @Test
    public void testFromClassNameWithExpression() {
        this.fromClassNameAndCheck(
            Expression.class,
            "expression"
        );
    }

    @Test
    public void testFromClassNameWithExpressionNumber() {
        this.fromClassNameAndCheck(
            ExpressionNumber.class,
            "number"
        );
    }

    @Test
    public void testFromClassNameWithExpressionNumberBigDecimal() {
        this.fromClassNameAndCheck(
            ExpressionNumberKind.BIG_DECIMAL.zero()
                .getClass()
                .getSimpleName(),
            "number"
        );
    }

    @Test
    public void testFromClassNameWithExpressionNumberDouble() {
        this.fromClassNameAndCheck(
            ExpressionNumberKind.DOUBLE.zero()
                .getClass()
                .getSimpleName(),
            "number"
        );
    }

    @Test
    public void testFromClassNameWithFloatType() {
        this.fromClassNameAndCheck(
            Float.TYPE
        );
    }

    @Test
    public void testFromClassNameWithFloatClass() {
        this.fromClassNameAndCheck(
            Float.class,
            "number/float"
        );
    }

    @Test
    public void testFromClassNameWithGreaterThanExpression() {
        this.fromClassNameAndCheck(
            GreaterThanExpression.class,
            "expression/greater-than"
        );
    }

    @Test
    public void testFromClassNameWithGreaterThanEqualsExpression() {
        this.fromClassNameAndCheck(
            GreaterThanEqualsExpression.class,
            "expression/greater-than-equals"
        );
    }

    @Test
    public void testFromClassNameWithIntegerType() {
        this.fromClassNameAndCheck(
            Integer.TYPE
        );
    }

    @Test
    public void testFromClassNameWithIntegerClass() {
        this.fromClassNameAndCheck(
            Integer.class,
            "number/whole/integer"
        );
    }

    @Test
    public void testFromClassNameWithJsonArray() {
        this.fromClassNameAndCheck(
            JsonArray.class,
            "json/array"
        );
    }

    @Test
    public void testFromClassNameWithJsonBoolean() {
        this.fromClassNameAndCheck(
            JsonBoolean.class,
            "json/boolean"
        );
    }

    @Test
    public void testFromClassNameWithJsonNull() {
        this.fromClassNameAndCheck(
            JsonNull.class,
            "json/null"
        );
    }

    @Test
    public void testFromClassNameWithJsonNode() {
        this.fromClassNameAndCheck(
            JsonNode.class,
            "json"
        );
    }

    @Test
    public void testFromClassNameWithJsonNumber() {
        this.fromClassNameAndCheck(
            JsonNumber.class,
            "json/number"
        );
    }

    @Test
    public void testFromClassNameWithJsonObject() {
        this.fromClassNameAndCheck(
            JsonObject.class,
            "json/object"
        );
    }

    @Test
    public void testFromClassNameWithJsonString() {
        this.fromClassNameAndCheck(
            JsonString.class,
            "json/string"
        );
    }

    @Test
    public void testFromClassNameWithLessThanExpression() {
        this.fromClassNameAndCheck(
            LessThanExpression.class,
            "expression/less-than"
        );
    }

    @Test
    public void testFromClassNameWithLessThanEqualsExpression() {
        this.fromClassNameAndCheck(
            LessThanEqualsExpression.class,
            "expression/less-than-equals"
        );
    }

    @Test
    public void testFromClassNameWithList() {
        this.fromClassNameAndCheck(
            List.class,
            "list"
        );
    }

    @Test
    public void testFromClassNameWithListExpression() {
        this.fromClassNameAndCheck(
            ListExpression.class,
            "expression/list"
        );
    }

    @Test
    public void testFromClassNameWithLocalDate() {
        this.fromClassNameAndCheck(
            LocalDate.class,
            ValueType.DATE
        );
    }

    @Test
    public void testFromClassNameWithLocalDateTime() {
        this.fromClassNameAndCheck(
            LocalDateTime.class,
            ValueType.DATE_TIME
        );
    }

    @Test
    public void testFromClassNameWithLocalTime() {
        this.fromClassNameAndCheck(
            LocalTime.class,
            ValueType.TIME
        );
    }

    @Test
    public void testFromClassNameWithLongType() {
        this.fromClassNameAndCheck(
            Long.TYPE
        );
    }

    @Test
    public void testFromClassNameWithLongClass() {
        this.fromClassNameAndCheck(
            Long.class,
            "number/whole/long"
        );
    }

    @Test
    public void testFromClassNameWithModuloExpression() {
        this.fromClassNameAndCheck(
            ModuloExpression.class,
            "expression/modulo"
        );
    }

    @Test
    public void testFromClassNameWithMultiplyExpression() {
        this.fromClassNameAndCheck(
            MultiplyExpression.class,
            "expression/multiply"
        );
    }

    @Test
    public void testFromClassNameWithNamedFunctionExpression() {
        this.fromClassNameAndCheck(
            NamedFunctionExpression.class,
            "expression/named-function"
        );
    }

    @Test
    public void testFromClassNameWithNegativeExpression() {
        this.fromClassNameAndCheck(
            NegativeExpression.class,
            "expression/negative"
        );
    }

    @Test
    public void testFromClassNameWithNotEqualsExpression() {
        this.fromClassNameAndCheck(
            NotEqualsExpression.class,
            "expression/not-equals"
        );
    }

    @Test
    public void testFromClassNameWithNotExpression() {
        this.fromClassNameAndCheck(
            NotExpression.class,
            "expression/not"
        );
    }

    @Test
    public void testFromClassNameWithNumberList() {
        this.fromClassNameAndCheck(
            NumberList.class,
            "list/number"
        );
    }

    @Test
    public void testFromClassNameWithObject() {
        this.fromClassNameAndCheck(
            Object.class,
            ValueType.ANY
        );
    }

    @Test
    public void testFromClassNameWithOrExpression() {
        this.fromClassNameAndCheck(
            OrExpression.class,
            "expression/or"
        );
    }

    @Test
    public void testFromClassNameWithPowerExpression() {
        this.fromClassNameAndCheck(
            PowerExpression.class,
            "expression/power"
        );
    }

    @Test
    public void testFromClassNameWithReferenceExpression() {
        this.fromClassNameAndCheck(
            ReferenceExpression.class,
            "expression/reference"
        );
    }

    @Test
    public void testFromClassNameWithShortType() {
        this.fromClassNameAndCheck(
            Short.TYPE
        );
    }

    @Test
    public void testFromClassNameWithShortClass() {
        this.fromClassNameAndCheck(
            Short.class,
            "number/whole/short"
        );
    }

    @Test
    public void testFromClassNameWithString() {
        this.fromClassNameAndCheck(
            String.class,
            ValueType.TEXT
        );
    }

    @Test
    public void testFromClassNameWithStringBuffer() {
        this.fromClassNameAndCheck(
            StringBuffer.class,
            "text/StringBuffer"
        );
    }

    @Test
    public void testFromClassNameWithStringBuilder() {
        this.fromClassNameAndCheck(
            StringBuilder.class,
            "text/StringBuilder"
        );
    }

    @Test
    public void testFromClassNameWithStringList() {
        this.fromClassNameAndCheck(
            StringList.class,
            "list/string"
        );
    }

    @Test
    public void testFromClassNameWithSubtractExpression() {
        this.fromClassNameAndCheck(
            SubtractExpression.class,
            "expression/subtract"
        );
    }

    @Test
    public void testFromClassNameWithValidationChoiceList() {
        this.fromClassNameAndCheck(
            ValidationChoiceList.class,
            "list/choice"
        );
    }

    @Test
    public void testFromClassNameWithValidationError() {
        this.fromClassNameAndCheck(
            ValidationError.class,
            ValueType.ERROR
        );
    }

    @Test
    public void testFromClassNameWithValidationErrorList() {
        this.fromClassNameAndCheck(
            ValidationErrorList.class,
            ValueType.ERROR_LIST
        );
    }

    @Test
    public void testFromClassNameWithValueExpression() {
        this.fromClassNameAndCheck(
            ValueExpression.class,
            "expression/value"
        );
    }

    @Test
    public void testFromClassNameWithXorExpression() {
        this.fromClassNameAndCheck(
            XorExpression.class,
            "expression/xor"
        );
    }

    private void fromClassNameAndCheck(final Class<?> klass) {
        this.checkEquals(
            Optional.empty(),
            ValueType.fromClassName(klass.getSimpleName())
        );
    }

    private void fromClassNameAndCheck(final String name,
                                       final String expected) {
        final ValueType valueType = ValueType.fromClassName(name)
            .orElseThrow(() -> new AssertionError("Missing " + name));

        this.valueAndCheck(
            valueType,
            expected
        );
    }

    private void fromClassNameAndCheck(final Class<?> klass,
                                       final String expected) {
        final ValueType valueType = ValueType.fromClassName(klass.getSimpleName())
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

    private void fromClassNameAndCheck(final Class<?> klass,
                                       final ValueType expected) {
        this.fromClassNameAndCheck(
            klass,
            Optional.of(expected)
        );
    }

    private void fromClassNameAndCheck(final Class<?> klass,
                                       final Optional<ValueType> expected) {
        this.checkEquals(
            expected,
            ValueType.fromClassName(klass.getSimpleName())
        );
    }

    // parent...........................................................................................................

    @Test
    public void testParentWithWholeNumber() {
        this.parentAndCheck(
            ValueType.WHOLE_NUMBER,
            ValueType.NUMBER
        );
    }

    @Test
    public void testParentWithByte() {
        this.parentAndCheck(
            Byte.class,
            ValueType.WHOLE_NUMBER
        );
    }

    @Test
    public void testParentWithShort() {
        this.parentAndCheck(
            Short.class,
            ValueType.WHOLE_NUMBER
        );
    }

    @Test
    public void testParentWithInteger() {
        this.parentAndCheck(
            Integer.class,
            ValueType.WHOLE_NUMBER
        );
    }

    @Test
    public void testParentWithLong() {
        this.parentAndCheck(
            Long.class,
            ValueType.WHOLE_NUMBER
        );
    }

    @Test
    public void testParentWithBigInteger() {
        this.parentAndCheck(
            BigInteger.class,
            ValueType.WHOLE_NUMBER
        );
    }

    @Test
    public void testParentWithNumber() {
        this.parentAndCheck(
            ValueType.NUMBER
        );
    }

    @Test
    public void testParentWithFloat() {
        this.parentAndCheck(
            Float.class,
            ValueType.NUMBER
        );
    }

    @Test
    public void testParentWithDouble() {
        this.parentAndCheck(
            Double.class,
            ValueType.NUMBER
        );
    }

    @Test
    public void testParentWithBigDecimal() {
        this.parentAndCheck(
            BigDecimal.class,
            ValueType.NUMBER
        );
    }

    @Test
    public void testParentWithExpressionNumberBigDecimal() {
        this.parentAndCheck(
            ExpressionNumberKind.BIG_DECIMAL.zero()
                .getClass()
        );
    }

    @Test
    public void testParentWithExpressionNumberDouble() {
        this.parentAndCheck(
            ExpressionNumberKind.DOUBLE.zero()
                .getClass()
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
            ValueType.ANY_STRING,
            ValueType.ANY
        );
    }

    @Test
    public void testUnmarshallBoolean() {
        this.unmarshallAndCheck2(
            ValueType.BOOLEAN_STRING,
            ValueType.BOOLEAN
        );
    }

    @Test
    public void testUnmarshallDate() {
        this.unmarshallAndCheck2(
            ValueType.DATE_STRING,
            ValueType.DATE
        );
    }

    @Test
    public void testUnmarshallDateTime() {
        this.unmarshallAndCheck2(
            ValueType.DATE_TIME_STRING,
            ValueType.DATE_TIME
        );
    }

    @Test
    public void testUnmarshallText() {
        this.unmarshallAndCheck2(
            ValueType.TEXT_STRING,
            ValueType.TEXT
        );
    }

    @Test
    public void testUnmarshallTime() {
        this.unmarshallAndCheck2(
            ValueType.TIME_STRING,
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
    public void testTestNumberWithNumber() {
        this.testAndCheck(
            ValueType.NUMBER,
            ValueType.NUMBER,
            true
        );
    }

    @Test
    public void testTestNumberWithByte() {
        this.testAndCheck(
            ValueType.NUMBER,
            ValueType.fromClassOrFail(Byte.class),
            true
        );
    }

    @Test
    public void testTestNumberWithFloat() {
        this.testAndCheck(
            ValueType.NUMBER,
            ValueType.fromClassOrFail(Float.class),
            true
        );
    }

    @Test
    public void testTestWholeNumberWithByte() {
        this.testAndCheck(
            ValueType.WHOLE_NUMBER,
            ValueType.fromClassOrFail(Byte.class),
            true
        );
    }

    @Test
    public void testTestWholeNumberWithFloat() {
        this.testAndCheck(
            ValueType.WHOLE_NUMBER,
            ValueType.fromClassOrFail(Float.class),
            false
        );
    }

    @Override
    public ValueType createPredicate() {
        return this.createComparable();
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
