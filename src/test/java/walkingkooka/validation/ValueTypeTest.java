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
import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.collect.list.BooleanList;
import walkingkooka.collect.list.CsvStringList;
import walkingkooka.collect.list.StringList;
import walkingkooka.currency.CurrencyCode;
import walkingkooka.currency.CurrencyValue;
import walkingkooka.datetime.DateTimeSymbols;
import walkingkooka.datetime.LocalDateList;
import walkingkooka.datetime.LocalDateTimeList;
import walkingkooka.datetime.LocalTimeList;
import walkingkooka.environment.Environment;
import walkingkooka.math.DecimalNumberSymbols;
import walkingkooka.math.NumberList;
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.net.DataUrl;
import walkingkooka.net.MailToUrl;
import walkingkooka.net.RelativeUrl;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.net.header.HasContentTypeTesting;
import walkingkooka.reflect.PublicClassTesting;
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
    HasValueTesting,
    HasContentTypeTesting,
    HashCodeEqualsDefinedTesting2<ValueType>,
    JsonNodeMarshallerTesting<ValueType> {

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
            "number(big-decimal)"
        );
    }

    @Test
    public void testFromClassWithBigInteger() {
        this.fromClassAndCheck(
            BigInteger.class,
            "whole-number(big-integer)"
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
            "list(boolean)"
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
            "whole-number(byte)"
        );
    }

    @Test
    public void testFromClassWithCallExpression() {
        this.fromClassAndCheck(
            CallExpression.class,
            "expression(call)"
        );
    }

    @Test
    public void testFromClassWithCsvStringList() {
        this.fromClassAndCheck(
            CsvStringList.class,
            "list(csv)"
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
            "date(date-time-symbols)"
        );
    }

    @Test
    public void testFromClassWithDecimalNumberSymbols() {
        this.fromClassAndCheck(
            DecimalNumberSymbols.class,
            "number(decimal-number-symbols)"
        );
    }

    @Test
    public void testFromClassWithDivideExpression() {
        this.fromClassAndCheck(
            DivideExpression.class,
            "expression(divide)"
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
            "number(double)"
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
            "expression(equals)"
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
            "number(float)"
        );
    }

    @Test
    public void testFromClassWithGreaterThanExpression() {
        this.fromClassAndCheck(
            GreaterThanExpression.class,
            "expression(greater-than)"
        );
    }

    @Test
    public void testFromClassWithGreaterThanEqualsExpression() {
        this.fromClassAndCheck(
            GreaterThanEqualsExpression.class,
            "expression(greater-than-equals)"
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
            "whole-number(integer)"
        );
    }

    @Test
    public void testFromClassWithJsonArray() {
        this.fromClassAndCheck(
            JsonArray.class,
            "json(array)"
        );
    }

    @Test
    public void testFromClassWithJsonBoolean() {
        this.fromClassAndCheck(
            JsonBoolean.class,
            "json(boolean)"
        );
    }

    @Test
    public void testFromClassWithJsonNull() {
        this.fromClassAndCheck(
            JsonNull.class,
            "json(null)"
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
            "json(number)"
        );
    }

    @Test
    public void testFromClassWithJsonObject() {
        this.fromClassAndCheck(
            JsonObject.class,
            "json(object)"
        );
    }

    @Test
    public void testFromClassWithJsonString() {
        this.fromClassAndCheck(
            JsonString.class,
            "json(string)"
        );
    }

    @Test
    public void testFromClassWithLessThanExpression() {
        this.fromClassAndCheck(
            LessThanExpression.class,
            "expression(less-than)"
        );
    }

    @Test
    public void testFromClassWithLessThanEqualsExpression() {
        this.fromClassAndCheck(
            LessThanEqualsExpression.class,
            "expression(less-than-equals)"
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
            "expression(list)"
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
            "whole-number(long)"
        );
    }

    @Test
    public void testFromClassWithModuloExpression() {
        this.fromClassAndCheck(
            ModuloExpression.class,
            "expression(modulo)"
        );
    }

    @Test
    public void testFromClassWithMultiplyExpression() {
        this.fromClassAndCheck(
            MultiplyExpression.class,
            "expression(multiply)"
        );
    }

    @Test
    public void testFromClassWithNamedFunctionExpression() {
        this.fromClassAndCheck(
            NamedFunctionExpression.class,
            "expression(named-function)"
        );
    }

    @Test
    public void testFromClassWithNegativeExpression() {
        this.fromClassAndCheck(
            NegativeExpression.class,
            "expression(negative)"
        );
    }

    @Test
    public void testFromClassWithNotEqualsExpression() {
        this.fromClassAndCheck(
            NotEqualsExpression.class,
            "expression(not-equals)"
        );
    }

    @Test
    public void testFromClassWithNotExpression() {
        this.fromClassAndCheck(
            NotExpression.class,
            "expression(not)"
        );
    }

    @Test
    public void testFromClassWithNumberList() {
        this.fromClassAndCheck(
            NumberList.class,
            "list(number)"
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
            "expression(or)"
        );
    }

    @Test
    public void testFromClassWithPowerExpression() {
        this.fromClassAndCheck(
            PowerExpression.class,
            "expression(power)"
        );
    }

    @Test
    public void testFromClassWithReferenceExpression() {
        this.fromClassAndCheck(
            ReferenceExpression.class,
            "expression(reference)"
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
            "whole-number(short)"
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
            "text(StringBuffer)"
        );
    }

    @Test
    public void testFromClassWithStringBuilder() {
        this.fromClassAndCheck(
            StringBuilder.class,
            "text(StringBuilder)"
        );
    }

    @Test
    public void testFromClassWithStringList() {
        this.fromClassAndCheck(
            StringList.class,
            "list(string)"
        );
    }

    @Test
    public void testFromClassWithSubtractExpression() {
        this.fromClassAndCheck(
            SubtractExpression.class,
            "expression(subtract)"
        );
    }

    @Test
    public void testFromClassWithValidationChoiceList() {
        this.fromClassAndCheck(
            ValidationChoiceList.class,
            "list(choice)"
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
            "expression(value)"
        );
    }

    @Test
    public void testFromClassWithXorExpression() {
        this.fromClassAndCheck(
            XorExpression.class,
            "expression(xor)"
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
            "number(big-decimal)"
        );
    }

    @Test
    public void testFromClassNameWithBigInteger() {
        this.fromClassNameAndCheck(
            BigInteger.class,
            "whole-number(big-integer)"
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
            "list(boolean)"
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
            "whole-number(byte)"
        );
    }

    @Test
    public void testFromClassNameWithCallExpression() {
        this.fromClassNameAndCheck(
            CallExpression.class,
            "expression(call)"
        );
    }

    @Test
    public void testFromClassNameWithCsvStringList() {
        this.fromClassNameAndCheck(
            CsvStringList.class,
            "list(csv)"
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
            "date(date-time-symbols)"
        );
    }

    @Test
    public void testFromClassNameWithDecimalNumberSymbols() {
        this.fromClassNameAndCheck(
            DecimalNumberSymbols.class,
            "number(decimal-number-symbols)"
        );
    }

    @Test
    public void testFromClassNameWithDivideExpression() {
        this.fromClassNameAndCheck(
            DivideExpression.class,
            "expression(divide)"
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
            "number(double)"
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
            "expression(equals)"
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
            "number(float)"
        );
    }

    @Test
    public void testFromClassNameWithGreaterThanExpression() {
        this.fromClassNameAndCheck(
            GreaterThanExpression.class,
            "expression(greater-than)"
        );
    }

    @Test
    public void testFromClassNameWithGreaterThanEqualsExpression() {
        this.fromClassNameAndCheck(
            GreaterThanEqualsExpression.class,
            "expression(greater-than-equals)"
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
            "whole-number(integer)"
        );
    }

    @Test
    public void testFromClassNameWithJsonArray() {
        this.fromClassNameAndCheck(
            JsonArray.class,
            "json(array)"
        );
    }

    @Test
    public void testFromClassNameWithJsonBoolean() {
        this.fromClassNameAndCheck(
            JsonBoolean.class,
            "json(boolean)"
        );
    }

    @Test
    public void testFromClassNameWithJsonNull() {
        this.fromClassNameAndCheck(
            JsonNull.class,
            "json(null)"
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
            "json(number)"
        );
    }

    @Test
    public void testFromClassNameWithJsonObject() {
        this.fromClassNameAndCheck(
            JsonObject.class,
            "json(object)"
        );
    }

    @Test
    public void testFromClassNameWithJsonString() {
        this.fromClassNameAndCheck(
            JsonString.class,
            "json(string)"
        );
    }

    @Test
    public void testFromClassNameWithLessThanExpression() {
        this.fromClassNameAndCheck(
            LessThanExpression.class,
            "expression(less-than)"
        );
    }

    @Test
    public void testFromClassNameWithLessThanEqualsExpression() {
        this.fromClassNameAndCheck(
            LessThanEqualsExpression.class,
            "expression(less-than-equals)"
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
            "expression(list)"
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
            "whole-number(long)"
        );
    }

    @Test
    public void testFromClassNameWithModuloExpression() {
        this.fromClassNameAndCheck(
            ModuloExpression.class,
            "expression(modulo)"
        );
    }

    @Test
    public void testFromClassNameWithMultiplyExpression() {
        this.fromClassNameAndCheck(
            MultiplyExpression.class,
            "expression(multiply)"
        );
    }

    @Test
    public void testFromClassNameWithNamedFunctionExpression() {
        this.fromClassNameAndCheck(
            NamedFunctionExpression.class,
            "expression(named-function)"
        );
    }

    @Test
    public void testFromClassNameWithNegativeExpression() {
        this.fromClassNameAndCheck(
            NegativeExpression.class,
            "expression(negative)"
        );
    }

    @Test
    public void testFromClassNameWithNotEqualsExpression() {
        this.fromClassNameAndCheck(
            NotEqualsExpression.class,
            "expression(not-equals)"
        );
    }

    @Test
    public void testFromClassNameWithNotExpression() {
        this.fromClassNameAndCheck(
            NotExpression.class,
            "expression(not)"
        );
    }

    @Test
    public void testFromClassNameWithNumberList() {
        this.fromClassNameAndCheck(
            NumberList.class,
            "list(number)"
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
            "expression(or)"
        );
    }

    @Test
    public void testFromClassNameWithPowerExpression() {
        this.fromClassNameAndCheck(
            PowerExpression.class,
            "expression(power)"
        );
    }

    @Test
    public void testFromClassNameWithReferenceExpression() {
        this.fromClassNameAndCheck(
            ReferenceExpression.class,
            "expression(reference)"
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
            "whole-number(short)"
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
            "text(StringBuffer)"
        );
    }

    @Test
    public void testFromClassNameWithStringBuilder() {
        this.fromClassNameAndCheck(
            StringBuilder.class,
            "text(StringBuilder)"
        );
    }

    @Test
    public void testFromClassNameWithStringList() {
        this.fromClassNameAndCheck(
            StringList.class,
            "list(string)"
        );
    }

    @Test
    public void testFromClassNameWithSubtractExpression() {
        this.fromClassNameAndCheck(
            SubtractExpression.class,
            "expression(subtract)"
        );
    }

    @Test
    public void testFromClassNameWithValidationChoiceList() {
        this.fromClassNameAndCheck(
            ValidationChoiceList.class,
            "list(choice)"
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
            "expression(value)"
        );
    }

    @Test
    public void testFromClassNameWithXorExpression() {
        this.fromClassNameAndCheck(
            XorExpression.class,
            "expression(xor)"
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

    // isAny............................................................................................................

    @Test
    public void testIsAnyWithAny() {
        this.isAnyAndCheck(
            ValueType.ANY,
            true
        );
    }

    @Test
    public void testIsAnyWithBoolean() {
        this.isAnyAndCheck(
            ValueType.BOOLEAN,
            false
        );
    }

    @Test
    public void testIsAnyWithCurrencyCode() {
        this.isAnyAndCheck(
            ValueType.fromClassOrFail(CurrencyCode.class),
            false
        );
    }

    @Test
    public void testIsAnyWithEmail() {
        this.isAnyAndCheck(
            ValueType.EMAIL,
            false
        );
    }

    @Test
    public void testIsAnyWithError() {
        this.isAnyAndCheck(
            ValueType.ERROR,
            false
        );
    }

    @Test
    public void testIsAnyWithNumber() {
        this.isAnyAndCheck(
            ValueType.fromClassOrFail(Number.class),
            false
        );
    }

    @Test
    public void testIsAnyWithText() {
        this.isAnyAndCheck(
            ValueType.TEXT,
            false
        );
    }

    @Test
    public void testIsAnyWithUrl() {
        this.isAnyAndCheck(
            ValueType.BOOLEAN,
            false
        );
    }

    private void isAnyAndCheck(final ValueType name,
                               final boolean expected) {
        this.checkEquals(
            expected,
            name.isAny(),
            name::toString
        );
    }

    // isBoolean........................................................................................................

    @Test
    public void testIsBooleanWithAny() {
        this.isBooleanAndCheck(
            ValueType.ANY,
            false
        );
    }

    @Test
    public void testIsBooleanWithBoolean() {
        this.isBooleanAndCheck(
            ValueType.BOOLEAN,
            true
        );
    }

    @Test
    public void testIsBooleanWithDate() {
        this.isBooleanAndCheck(
            ValueType.DATE,
            false
        );
    }

    @Test
    public void testIsBooleanWithDateTime() {
        this.isBooleanAndCheck(
            ValueType.DATE_TIME,
            false
        );
    }

    @Test
    public void testIsBooleanWithEmail() {
        this.isBooleanAndCheck(
            ValueType.EMAIL,
            false
        );
    }

    @Test
    public void testIsBooleanWithError() {
        this.isBooleanAndCheck(
            ValueType.ERROR,
            false
        );
    }

    @Test
    public void testIsBooleanWithNumber() {
        this.isBooleanAndCheck(
            ValueType.NUMBER,
            false
        );
    }

    @Test
    public void testIsBooleanWithText() {
        this.isBooleanAndCheck(
            ValueType.TEXT,
            false
        );
    }

    @Test
    public void testIsBooleanWithTime() {
        this.isBooleanAndCheck(
            ValueType.TIME,
            false
        );
    }

    private void isBooleanAndCheck(final ValueType name,
                                   final boolean expected) {
        this.checkEquals(
            expected,
            name.isBoolean(),
            name::toString
        );
    }

    // isDate...........................................................................................................

    @Test
    public void testIsDateWithAny() {
        this.isDateAndCheck(
            ValueType.ANY,
            false
        );
    }

    @Test
    public void testIsDateWithBoolean() {
        this.isDateAndCheck(
            ValueType.BOOLEAN,
            false
        );
    }

    @Test
    public void testIsDateWithDate() {
        this.isDateAndCheck(
            ValueType.DATE,
            true
        );
    }

    @Test
    public void testIsDateWithDateTime() {
        this.isDateAndCheck(
            ValueType.DATE_TIME,
            false
        );
    }

    @Test
    public void testIsDateWithEmail() {
        this.isDateAndCheck(
            ValueType.EMAIL,
            false
        );
    }

    @Test
    public void testIsDateWithError() {
        this.isDateAndCheck(
            ValueType.ERROR,
            false
        );
    }

    @Test
    public void testIsDateWithNumber() {
        this.isDateAndCheck(
            ValueType.NUMBER,
            false
        );
    }

    @Test
    public void testIsDateWithText() {
        this.isDateAndCheck(
            ValueType.TEXT,
            false
        );
    }

    @Test
    public void testIsDateWithTime() {
        this.isDateAndCheck(
            ValueType.TIME,
            false
        );
    }

    private void isDateAndCheck(final ValueType name,
                                final boolean expected) {
        this.checkEquals(
            expected,
            name.isDate(),
            name::toString
        );
    }

    // isDateTime.......................................................................................................

    @Test
    public void testIsDateTimeWithAny() {
        this.isDateTimeAndCheck(
            ValueType.ANY,
            false
        );
    }

    @Test
    public void testIsDateTimeWithBoolean() {
        this.isDateTimeAndCheck(
            ValueType.BOOLEAN,
            false
        );
    }

    @Test
    public void testIsDateTimeWithDate() {
        this.isDateTimeAndCheck(
            ValueType.DATE,
            false
        );
    }

    @Test
    public void testIsDateTimeWithDateTime() {
        this.isDateTimeAndCheck(
            ValueType.DATE_TIME,
            true
        );
    }

    @Test
    public void testIsDateTimeWithEmail() {
        this.isDateTimeAndCheck(
            ValueType.EMAIL,
            false
        );
    }

    @Test
    public void testIsDateTimeWithError() {
        this.isDateTimeAndCheck(
            ValueType.ERROR,
            false
        );
    }

    @Test
    public void testIsDateTimeWithNumber() {
        this.isDateTimeAndCheck(
            ValueType.NUMBER,
            false
        );
    }

    @Test
    public void testIsDateTimeWithText() {
        this.isDateTimeAndCheck(
            ValueType.TEXT,
            false
        );
    }

    @Test
    public void testIsDateTimeWithTime() {
        this.isDateTimeAndCheck(
            ValueType.TIME,
            false
        );
    }

    private void isDateTimeAndCheck(final ValueType name,
                                    final boolean expected) {
        this.checkEquals(
            expected,
            name.isDateTime(),
            name::toString
        );
    }

    // isEmail........................................................................................................

    @Test
    public void testIsEmailWithAny() {
        this.isEmailAndCheck(
            ValueType.ANY,
            false
        );
    }

    @Test
    public void testIsEmailWithBoolean() {
        this.isEmailAndCheck(
            ValueType.BOOLEAN,
            false
        );
    }

    @Test
    public void testIsEmailWithDate() {
        this.isEmailAndCheck(
            ValueType.DATE,
            false
        );
    }

    @Test
    public void testIsEmailWithDateTime() {
        this.isEmailAndCheck(
            ValueType.DATE_TIME,
            false
        );
    }

    @Test
    public void testIsEmailWithEmail() {
        this.isEmailAndCheck(
            ValueType.EMAIL,
            true
        );
    }

    @Test
    public void testIsEmailWithError() {
        this.isEmailAndCheck(
            ValueType.ERROR,
            false
        );
    }

    @Test
    public void testIsEmailWithNumber() {
        this.isEmailAndCheck(
            ValueType.NUMBER,
            false
        );
    }

    @Test
    public void testIsEmailWithText() {
        this.isEmailAndCheck(
            ValueType.TEXT,
            false
        );
    }

    private void isEmailAndCheck(final ValueType name,
                                 final boolean expected) {
        this.checkEquals(
            expected,
            name.isEmail(),
            name::toString
        );
    }

    // isError............................................................................................................

    @Test
    public void testIsErrorWithAny() {
        this.isErrorAndCheck(
            ValueType.ANY,
            false
        );
    }

    @Test
    public void testIsErrorWithBoolean() {
        this.isErrorAndCheck(
            ValueType.BOOLEAN,
            false
        );
    }

    @Test
    public void testIsErrorWithEmail() {
        this.isErrorAndCheck(
            ValueType.EMAIL,
            false
        );
    }

    @Test
    public void testIsErrorWithError() {
        this.isErrorAndCheck(
            ValueType.ERROR,
            true
        );
    }

    @Test
    public void testIsErrorWithNumber() {
        this.isErrorAndCheck(
            ValueType.NUMBER,
            false
        );
    }

    @Test
    public void testIsErrorWithText() {
        this.isErrorAndCheck(
            ValueType.TEXT,
            false
        );
    }

    @Test
    public void testIsErrorWithUrl() {
        this.isErrorAndCheck(
            ValueType.BOOLEAN,
            false
        );
    }

    private void isErrorAndCheck(final ValueType name,
                                 final boolean expected) {
        this.checkEquals(
            expected,
            name.isError(),
            name::toString
        );
    }

    // isExpression............................................................................................................

    @Test
    public void testIsExpressionWithAddExpression() {
        this.isExpressionAndCheck(
            AddExpression.class,
            true
        );
    }

    @Test
    public void testIsExpressionWithAndExpression() {
        this.isExpressionAndCheck(
            AndExpression.class,
            true
        );
    }

    @Test
    public void testIsExpressionWithAny() {
        this.isExpressionAndCheck(
            ValueType.ANY,
            false
        );
    }

    @Test
    public void testIsExpressionWithBoolean() {
        this.isExpressionAndCheck(
            ValueType.BOOLEAN,
            false
        );
    }

    @Test
    public void testIsExpressionWithCallExpression() {
        this.isExpressionAndCheck(
            CallExpression.class,
            true
        );
    }

    @Test
    public void testIsExpressionWithDivideExpression() {
        this.isExpressionAndCheck(
            DivideExpression.class,
            true
        );
    }

    @Test
    public void testIsExpressionWithEmail() {
        this.isExpressionAndCheck(
            ValueType.EMAIL,
            false
        );
    }

    @Test
    public void testIsExpressionWithError() {
        this.isExpressionAndCheck(
            ValueType.ERROR,
            false
        );
    }

    @Test
    public void testIsExpressionWithEqualsExpression() {
        this.isExpressionAndCheck(
            EqualsExpression.class,
            true
        );
    }

    @Test
    public void testIsExpressionWithExpression() {
        this.isExpressionAndCheck(
            Expression.class,
            true
        );
    }

    @Test
    public void testIsExpressionWithGreaterThanEqualsExpression() {
        this.isExpressionAndCheck(
            GreaterThanExpression.class,
            true
        );
    }

    @Test
    public void testIsExpressionWithGreaterThanExpression() {
        this.isExpressionAndCheck(
            GreaterThanExpression.class,
            true
        );
    }

    @Test
    public void testIsExpressionWithLambdaFunctionExpression() {
        this.isExpressionAndCheck(
            LambdaFunctionExpression.class,
            true
        );
    }

    @Test
    public void testIsExpressionWithLessThanEqualsExpression() {
        this.isExpressionAndCheck(
            LessThanEqualsExpression.class,
            true
        );
    }

    @Test
    public void testIsExpressionWithLessThanExpression() {
        this.isExpressionAndCheck(
            LessThanExpression.class,
            true
        );
    }

    @Test
    public void testIsExpressionWithListExpression() {
        this.isExpressionAndCheck(
            ListExpression.class,
            true
        );
    }

    @Test
    public void testIsExpressionWithModuloExpression() {
        this.isExpressionAndCheck(
            ModuloExpression.class,
            true
        );
    }

    @Test
    public void testIsExpressionWithMultiplyExpression() {
        this.isExpressionAndCheck(
            MultiplyExpression.class,
            true
        );
    }

    @Test
    public void testIsExpressionWithNamedFunctionExpression() {
        this.isExpressionAndCheck(
            NamedFunctionExpression.class,
            true
        );
    }

    @Test
    public void testIsExpressionWithNegativeExpression() {
        this.isExpressionAndCheck(
            NegativeExpression.class,
            true
        );
    }

    @Test
    public void testIsExpressionWithNotExpression() {
        this.isExpressionAndCheck(
            NotExpression.class,
            true
        );
    }

    @Test
    public void testIsExpressionWithNotEqualsExpression() {
        this.isExpressionAndCheck(
            NotEqualsExpression.class,
            true
        );
    }

    @Test
    public void testIsExpressionWithNumber() {
        this.isExpressionAndCheck(
            ValueType.NUMBER,
            false
        );
    }

    @Test
    public void testIsExpressionWithOrExpression() {
        this.isExpressionAndCheck(
            OrExpression.class,
            true
        );
    }

    @Test
    public void testIsExpressionWithPowerExpression() {
        this.isExpressionAndCheck(
            PowerExpression.class,
            true
        );
    }

    @Test
    public void testIsExpressionWithReferenceExpression() {
        this.isExpressionAndCheck(
            ReferenceExpression.class,
            true
        );
    }

    @Test
    public void testIsExpressionWithSubtractExpression() {
        this.isExpressionAndCheck(
            SubtractExpression.class,
            true
        );
    }

    @Test
    public void testIsExpressionWithText() {
        this.isExpressionAndCheck(
            ValueType.TEXT,
            false
        );
    }

    @Test
    public void testIsExpressionWithUrl() {
        this.isExpressionAndCheck(
            ValueType.BOOLEAN,
            false
        );
    }

    @Test
    public void testIsExpressionWithValueExpression() {
        this.isExpressionAndCheck(
            ValueExpression.class,
            true
        );
    }

    @Test
    public void testIsExpressionWithXorExpression() {
        this.isExpressionAndCheck(
            XorExpression.class,
            true
        );
    }

    private void isExpressionAndCheck(final Class<?> klass,
                                      final boolean expected) {
        this.isExpressionAndCheck(
            ValueType.fromClassOrFail(klass),
            expected
        );
    }

    private void isExpressionAndCheck(final ValueType name,
                                      final boolean expected) {
        this.checkEquals(
            expected,
            name.isExpression(),
            name::toString
        );
    }

    // isJson........................................................................................................

    @Test
    public void testIsJsonWithAny() {
        this.isJsonAndCheck(
            ValueType.ANY,
            false
        );
    }

    @Test
    public void testIsJsonWithBoolean() {
        this.isJsonAndCheck(
            ValueType.BOOLEAN,
            false
        );
    }

    @Test
    public void testIsJsonWithDate() {
        this.isJsonAndCheck(
            ValueType.DATE,
            false
        );
    }

    @Test
    public void testIsJsonWithDateTime() {
        this.isJsonAndCheck(
            ValueType.DATE_TIME,
            false
        );
    }

    @Test
    public void testIsJsonWithEmail() {
        this.isJsonAndCheck(
            ValueType.EMAIL,
            false
        );
    }

    @Test
    public void testIsJsonWithError() {
        this.isJsonAndCheck(
            ValueType.ERROR,
            false
        );
    }

    @Test
    public void testIsJsonWithJsonNode() {
        this.isJsonAndCheck(
            JsonNode.class,
            true
        );
    }

    @Test
    public void testIsJsonWithJsonArray() {
        this.isJsonAndCheck(
            JsonArray.class,
            true
        );
    }

    @Test
    public void testIsJsonWithJsonBoolean() {
        this.isJsonAndCheck(
            JsonBoolean.class,
            true
        );
    }

    @Test
    public void testIsJsonWithJsonNull() {
        this.isJsonAndCheck(
            JsonNull.class,
            true
        );
    }

    @Test
    public void testIsJsonWithJsonNumber() {
        this.isJsonAndCheck(
            JsonNode.class,
            true
        );
    }

    @Test
    public void testIsJsonWithJsonObject() {
        this.isJsonAndCheck(
            JsonObject.class,
            true
        );
    }

    @Test
    public void testIsJsonWithJsonString() {
        this.isJsonAndCheck(
            JsonArray.class,
            true
        );
    }

    @Test
    public void testIsJsonWithNumber() {
        this.isJsonAndCheck(
            ValueType.NUMBER,
            false
        );
    }

    @Test
    public void testIsJsonWithText() {
        this.isJsonAndCheck(
            ValueType.TEXT,
            false
        );
    }

    private void isJsonAndCheck(final Class<?> klass,
                                final boolean expected) {
        this.isJsonAndCheck(
            ValueType.fromClassOrFail(klass),
            expected
        );
    }

    private void isJsonAndCheck(final ValueType name,
                                final boolean expected) {
        this.checkEquals(
            expected,
            name.isJson(),
            name::toString
        );
    }

    // isList............................................................................................................

    @Test
    public void testIsListWithAny() {
        this.isListAndCheck(
            ValueType.ANY,
            false
        );
    }

    @Test
    public void testIsListWithBoolean() {
        this.isListAndCheck(
            ValueType.BOOLEAN,
            false
        );
    }

    @Test
    public void testIsListWithBooleanList() {
        this.isListAndCheck(
            BooleanList.class,
            true
        );
    }

    @Test
    public void testIsListWithChoiceList() {
        this.isListAndCheck(
            ValidationChoiceList.class,
            true
        );
    }

    @Test
    public void testIsListWithCsvList() {
        this.isListAndCheck(
            CsvStringList.class,
            true
        );
    }

    @Test
    public void testIsListWithDate() {
        this.isListAndCheck(
            ValueType.DATE,
            false
        );
    }

    @Test
    public void testIsListWithDateList() {
        this.isListAndCheck(
            LocalDateList.class,
            true
        );
    }

    @Test
    public void testIsListWithDateTime() {
        this.isListAndCheck(
            ValueType.DATE_TIME,
            false
        );
    }

    @Test
    public void testIsListWithDateTimeList() {
        this.isListAndCheck(
            LocalDateTimeList.class,
            true
        );
    }

    @Test
    public void testIsListWithEmail() {
        this.isListAndCheck(
            ValueType.EMAIL,
            false
        );
    }

    @Test
    public void testIsListWithError() {
        this.isListAndCheck(
            ValueType.ERROR,
            false
        );
    }

    @Test
    public void testIsListWithErrorList() {
        this.isListAndCheck(
            ValueType.ERROR_LIST,
            true
        );
    }

    @Test
    public void testIsListWithList() {
        this.isListAndCheck(
            List.class,
            true
        );
    }

    @Test
    public void testIsListWithNumber() {
        this.isListAndCheck(
            ValueType.NUMBER,
            false
        );
    }

    @Test
    public void testIsListWithNumberList() {
        this.isListAndCheck(
            NumberList.class,
            true
        );
    }

    @Test
    public void testIsListWithText() {
        this.isListAndCheck(
            ValueType.TEXT,
            false
        );
    }

    @Test
    public void testIsListWithTextList() {
        this.isListAndCheck(
            StringList.class,
            true
        );
    }

    @Test
    public void testIsListWithTimeList() {
        this.isListAndCheck(
            LocalTimeList.class,
            true
        );
    }

    @Test
    public void testIsListWithUrl() {
        this.isListAndCheck(
            ValueType.BOOLEAN,
            false
        );
    }

    private void isListAndCheck(final Class<?> klass,
                                final boolean expected) {
        this.isListAndCheck(
            ValueType.fromClassOrFail(klass),
            expected
        );
    }

    private void isListAndCheck(final ValueType name,
                                final boolean expected) {
        this.checkEquals(
            expected,
            name.isList(),
            name::toString
        );
    }

    // isLocale...........................................................................................................

    @Test
    public void testIsLocaleWithAny() {
        this.isLocaleAndCheck(
            ValueType.ANY,
            false
        );
    }

    @Test
    public void testIsLocaleWithBoolean() {
        this.isLocaleAndCheck(
            ValueType.BOOLEAN,
            false
        );
    }

    @Test
    public void testIsLocaleWithDate() {
        this.isLocaleAndCheck(
            ValueType.DATE,
            false
        );
    }

    @Test
    public void testIsLocaleWithDateTime() {
        this.isLocaleAndCheck(
            ValueType.DATE_TIME,
            false
        );
    }

    @Test
    public void testIsLocaleWithEmail() {
        this.isLocaleAndCheck(
            ValueType.EMAIL,
            false
        );
    }

    @Test
    public void testIsLocaleWithError() {
        this.isLocaleAndCheck(
            ValueType.ERROR,
            false
        );
    }

    @Test
    public void testIsLocaleWithLocale() {
        this.isLocaleAndCheck(
            ValueType.LOCALE,
            true
        );
    }

    @Test
    public void testIsLocaleWithNumber() {
        this.isLocaleAndCheck(
            Number.class,
            false
        );
    }

    @Test
    public void testIsLocaleWithText() {
        this.isLocaleAndCheck(
            ValueType.TEXT,
            false
        );
    }

    @Test
    public void testIsLocaleWithTime() {
        this.isLocaleAndCheck(
            ValueType.TIME,
            false
        );
    }

    private void isLocaleAndCheck(final Class<?> type,
                                  final boolean expected) {
        this.isLocaleAndCheck(
            ValueType.fromClassOrFail(type),
            expected
        );
    }

    private void isLocaleAndCheck(final ValueType name,
                                  final boolean expected) {
        this.checkEquals(
            expected,
            name.isLocale(),
            name::toString
        );
    }

    // isNumber.........................................................................................................

    @Test
    public void testIsNumberWithAny() {
        this.isNumberAndCheck(
            ValueType.ANY,
            false
        );
    }

    @Test
    public void testIsNumberWithBoolean() {
        this.isNumberAndCheck(
            ValueType.BOOLEAN,
            false
        );
    }

    @Test
    public void testIsNumberWithDate() {
        this.isNumberAndCheck(
            ValueType.DATE,
            false
        );
    }

    @Test
    public void testIsNumberWithDateTime() {
        this.isNumberAndCheck(
            ValueType.DATE_TIME,
            false
        );
    }

    @Test
    public void testIsNumberWithEmail() {
        this.isNumberAndCheck(
            ValueType.EMAIL,
            false
        );
    }

    @Test
    public void testIsNumberWithError() {
        this.isNumberAndCheck(
            ValueType.ERROR,
            false
        );
    }

    @Test
    public void testIsNumberWithNumber() {
        this.isNumberAndCheck(
            ValueType.NUMBER,
            true
        );
    }

    @Test
    public void testIsNumberWithExpressionNumber() {
        this.isNumberAndCheck(
            ExpressionNumber.class,
            true
        );
    }

    @Test
    public void testIsNumberWithExpressionNumberBigDecimal() {
        this.isNumberAndCheck(
            ExpressionNumberKind.BIG_DECIMAL.zero()
                .getClass(),
            true
        );
    }

    @Test
    public void testIsNumberWithByte() {
        this.isNumberAndCheck(
            Byte.class,
            false
        );
    }

    @Test
    public void testIsNumberWithShort() {
        this.isNumberAndCheck(
            Short.class,
            false
        );
    }

    @Test
    public void testIsNumberWithInteger() {
        this.isNumberAndCheck(
            Integer.class,
            false
        );
    }

    @Test
    public void testIsNumberWithLong() {
        this.isNumberAndCheck(
            Long.class,
            false
        );
    }

    @Test
    public void testIsNumberWithExpressionNumberDouble() {
        this.isNumberAndCheck(
            ExpressionNumberKind.DOUBLE.zero()
                .getClass(),
            true
        );
    }

    @Test
    public void testIsNumberWithText() {
        this.isNumberAndCheck(
            ValueType.TEXT,
            false
        );
    }

    @Test
    public void testIsNumberWithTextStringBuilder() {
        this.isNumberAndCheck(
            StringBuilder.class,
            false
        );
    }

    @Test
    public void testIsNumberWithTime() {
        this.isNumberAndCheck(
            ValueType.TIME,
            false
        );
    }

    private void isNumberAndCheck(final Class<?> type,
                                  final boolean expected) {
        this.isNumberAndCheck(
            ValueType.fromClassOrFail(type),
            expected
        );
    }

    private void isNumberAndCheck(final ValueType name,
                                  final boolean expected) {
        this.checkEquals(
            expected,
            name.isNumber(),
            name::toString
        );
    }

    // isText...........................................................................................................

    @Test
    public void testIsTextWithAny() {
        this.isTextAndCheck(
            ValueType.ANY,
            false
        );
    }

    @Test
    public void testIsTextWithBoolean() {
        this.isTextAndCheck(
            ValueType.BOOLEAN,
            false
        );
    }

    @Test
    public void testIsTextWithDate() {
        this.isTextAndCheck(
            ValueType.DATE,
            false
        );
    }

    @Test
    public void testIsTextWithDateTime() {
        this.isTextAndCheck(
            ValueType.DATE_TIME,
            false
        );
    }

    @Test
    public void testIsTextWithEmail() {
        this.isTextAndCheck(
            ValueType.EMAIL,
            false
        );
    }

    @Test
    public void testIsTextWithError() {
        this.isTextAndCheck(
            ValueType.ERROR,
            false
        );
    }

    @Test
    public void testIsTextWithNumber() {
        this.isTextAndCheck(
            ValueType.NUMBER,
            false
        );
    }

    @Test
    public void testIsTextWithText() {
        this.isTextAndCheck(
            ValueType.TEXT,
            true
        );
    }

    @Test
    public void testIsTextWithTextStringBuilder() {
        this.isTextAndCheck(
            ValueType.fromClassOrFail(StringBuilder.class),
            true
        );
    }

    @Test
    public void testIsTextWithTime() {
        this.isTextAndCheck(
            ValueType.TIME,
            false
        );
    }

    private void isTextAndCheck(final ValueType name,
                                final boolean expected) {
        this.checkEquals(
            expected,
            name.isText(),
            name::toString
        );
    }

    // isTime...........................................................................................................

    @Test
    public void testIsTimeWithAny() {
        this.isTimeAndCheck(
            ValueType.ANY,
            false
        );
    }

    @Test
    public void testIsTimeWithBoolean() {
        this.isTimeAndCheck(
            ValueType.BOOLEAN,
            false
        );
    }

    @Test
    public void testIsTimeWithDate() {
        this.isTimeAndCheck(
            ValueType.DATE,
            false
        );
    }

    @Test
    public void testIsTimeWithDateTime() {
        this.isTimeAndCheck(
            ValueType.DATE_TIME,
            false
        );
    }

    @Test
    public void testIsTimeWithEmail() {
        this.isTimeAndCheck(
            ValueType.EMAIL,
            false
        );
    }

    @Test
    public void testIsTimeWithError() {
        this.isTimeAndCheck(
            ValueType.ERROR,
            false
        );
    }

    @Test
    public void testIsTimeWithNumber() {
        this.isTimeAndCheck(
            ValueType.NUMBER,
            false
        );
    }

    @Test
    public void testIsTimeWithText() {
        this.isTimeAndCheck(
            ValueType.TEXT,
            false
        );
    }

    @Test
    public void testIsTimeWithTime() {
        this.isTimeAndCheck(
            ValueType.TIME,
            true
        );
    }

    private void isTimeAndCheck(final ValueType name,
                                final boolean expected) {
        this.checkEquals(
            expected,
            name.isTime(),
            name::toString
        );
    }

    // isUrl............................................................................................................

    @Test
    public void testIsUrlWithAbsoluteUrl() {
        this.isUrlAndCheck(
            AbsoluteUrl.class,
            true
        );
    }

    @Test
    public void testIsUrlWithAny() {
        this.isUrlAndCheck(
            ValueType.ANY,
            false
        );
    }

    @Test
    public void testIsUrlWithDate() {
        this.isUrlAndCheck(
            ValueType.DATE,
            false
        );
    }

    @Test
    public void testIsUrlWithDataUrl() {
        this.isUrlAndCheck(
            DataUrl.class,
            true
        );
    }

    @Test
    public void testIsUrlWithDateTime() {
        this.isUrlAndCheck(
            ValueType.DATE_TIME,
            false
        );
    }

    @Test
    public void testIsUrlWithMailToUrl() {
        this.isUrlAndCheck(
            MailToUrl.class,
            true
        );
    }

    @Test
    public void testIsUrlWithRelativeUrl() {
        this.isUrlAndCheck(
            RelativeUrl.class,
            true
        );
    }

    @Test
    public void testIsUrlWithText() {
        this.isUrlAndCheck(
            ValueType.TEXT,
            false
        );
    }

    @Test
    public void testIsUrlWithTime() {
        this.isUrlAndCheck(
            ValueType.TIME,
            false
        );
    }

    private void isUrlAndCheck(final Class<?> type,
                               final boolean expected) {
        this.isUrlAndCheck(
            ValueType.fromClassOrFail(type),
            expected
        );
    }

    private void isUrlAndCheck(final ValueType name,
                               final boolean expected) {
        this.checkEquals(
            expected,
            name.isUrl(),
            name::toString
        );
    }

    // isWholeNumber....................................................................................................

    @Test
    public void testIsWholeNumberWithAny() {
        this.isWholeNumberAndCheck(
            ValueType.ANY,
            false
        );
    }

    @Test
    public void testIsWholeNumberWithBoolean() {
        this.isWholeNumberAndCheck(
            ValueType.BOOLEAN,
            false
        );
    }

    @Test
    public void testIsWholeNumberWithDate() {
        this.isWholeNumberAndCheck(
            ValueType.DATE,
            false
        );
    }

    @Test
    public void testIsWholeNumberWithDateTime() {
        this.isWholeNumberAndCheck(
            ValueType.DATE_TIME,
            false
        );
    }

    @Test
    public void testIsWholeNumberWithEmail() {
        this.isWholeNumberAndCheck(
            ValueType.EMAIL,
            false
        );
    }

    @Test
    public void testIsWholeNumberWithError() {
        this.isWholeNumberAndCheck(
            ValueType.ERROR,
            false
        );
    }

    @Test
    public void testIsWholeNumberWithNumber() {
        this.isWholeNumberAndCheck(
            ValueType.NUMBER,
            false
        );
    }

    @Test
    public void testIsWholeNumberWithExpressionNumber() {
        this.isWholeNumberAndCheck(
            ExpressionNumber.class,
            false
        );
    }

    @Test
    public void testIsWholeNumberWithExpressionNumberBigDecimal() {
        this.isWholeNumberAndCheck(
            ExpressionNumberKind.BIG_DECIMAL.zero()
                .getClass(),
            false
        );
    }

    @Test
    public void testIsWholeNumberWithByte() {
        this.isWholeNumberAndCheck(
            Byte.class,
            true
        );
    }

    @Test
    public void testIsWholeNumberWithShort() {
        this.isWholeNumberAndCheck(
            Short.class,
            true
        );
    }

    @Test
    public void testIsWholeNumberWithInteger() {
        this.isWholeNumberAndCheck(
            Integer.class,
            true
        );
    }

    @Test
    public void testIsWholeNumberWithLong() {
        this.isWholeNumberAndCheck(
            Long.class,
            true
        );
    }

    @Test
    public void testIsWholeNumberWithExpressionNumberDouble() {
        this.isWholeNumberAndCheck(
            ExpressionNumberKind.DOUBLE.zero()
                .getClass(),
            false
        );
    }

    @Test
    public void testIsWholeNumberWithText() {
        this.isWholeNumberAndCheck(
            ValueType.TEXT,
            false
        );
    }

    @Test
    public void testIsWholeNumberWithTextStringBuilder() {
        this.isWholeNumberAndCheck(
            StringBuilder.class,
            false
        );
    }

    @Test
    public void testIsWholeNumberWithTime() {
        this.isWholeNumberAndCheck(
            ValueType.TIME,
            false
        );
    }

    private void isWholeNumberAndCheck(final Class<?> type,
                                  final boolean expected) {
        this.isWholeNumberAndCheck(
            ValueType.fromClassOrFail(type),
            expected
        );
    }

    private void isWholeNumberAndCheck(final ValueType name,
                                  final boolean expected) {
        this.checkEquals(
            expected,
            name.isWholeNumber(),
            name::toString
        );
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferentValue() {
        this.checkNotEquals(ValueType.EMAIL);
    }

    @Override
    public ValueType createObject() {
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

    // class............................................................................................................

    @Override
    public Class<ValueType> type() {
        return ValueType.class;
    }
}
