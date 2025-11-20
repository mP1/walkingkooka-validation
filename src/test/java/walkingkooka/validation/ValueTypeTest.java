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
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.plugin.PluginNameTesting;
import walkingkooka.tree.expression.ExpressionNumber;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContexts;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

final public class ValueTypeTest implements PluginNameTesting<ValueType> {

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
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
    public void testFromClassWithBooleanType() {
        this.fromClassAndCheck(
            Boolean.TYPE,
            ValueType.BOOLEAN
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
    public void testFromClassWithByteType() {
        this.fromClassAndCheck(
            Byte.TYPE,
            ValueType.with("byte")
        );
    }

    @Test
    public void testFromClassWithByteClass() {
        this.fromClassAndCheck(
            Byte.class,
            ValueType.with("number(Byte)")
        );
    }

    @Test
    public void testFromClassWithShortType() {
        this.fromClassAndCheck(
            Short.TYPE,
            ValueType.with("short")
        );
    }

    @Test
    public void testFromClassWithShortClass() {
        this.fromClassAndCheck(
            Short.class,
            ValueType.with("number(Short)")
        );
    }

    @Test
    public void testFromClassWithIntegerType() {
        this.fromClassAndCheck(
            Integer.TYPE,
            ValueType.with("int")
        );
    }

    @Test
    public void testFromClassWithIntegerClass() {
        this.fromClassAndCheck(
            Integer.class,
            ValueType.with("number(Integer)")
        );
    }

    @Test
    public void testFromClassWithLongType() {
        this.fromClassAndCheck(
            Long.TYPE,
            ValueType.with("long")
        );
    }

    @Test
    public void testFromClassWithLongClass() {
        this.fromClassAndCheck(
            Long.class,
            ValueType.with("number(Long)")
        );
    }

    @Test
    public void testFromClassWithFloatType() {
        this.fromClassAndCheck(
            Float.TYPE,
            ValueType.with("float")
        );
    }

    @Test
    public void testFromClassWithFloatClass() {
        this.fromClassAndCheck(
            Float.class,
            ValueType.with("number(Float)")
        );
    }

    @Test
    public void testFromClassWithDoubleType() {
        this.fromClassAndCheck(
            Double.TYPE,
            ValueType.with("double")
        );
    }

    @Test
    public void testFromClassWithDoubleClass() {
        this.fromClassAndCheck(
            Double.class,
            ValueType.with("number(Double)")
        );
    }

    @Test
    public void testFromClassWithBigDecimal() {
        this.fromClassAndCheck(
            BigDecimal.class,
            ValueType.with("number(BigDecimal)")
        );
    }

    @Test
    public void testFromClassWithBigInteger() {
        this.fromClassAndCheck(
            BigInteger.class,
            ValueType.with("number(BigInteger)")
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
            ValueType.with("text(StringBuffer)")
        );
    }

    @Test
    public void testFromClassWithStringBuilder() {
        this.fromClassAndCheck(
            StringBuilder.class,
            ValueType.with("text(StringBuilder)")
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
    public void testFromClassWithAbsoluteUrl() {
        this.fromClassAndCheck(
            AbsoluteUrl.class,
            ValueType.URL
        );
    }

    @Test
    public void testFromClassWithVoid() {
        this.fromClassAndCheck(
            Void.class,
            ValueType.with("java.lang.Void")
        );
    }

    @Test
    public void testFromClassWithObject() {
        this.fromClassAndCheck(
            Object.class,
            ValueType.ANY
        );
    }

    private void fromClassAndCheck(final Class<?> classs,
                                   final ValueType expected) {
        this.checkEquals(
            expected,
            ValueType.fromClass(classs)
        );
    }

    // with.............................................................................................................

    @Test
    public void testWithClassNameJavaLangVoid() {
        this.createNameAndCheck("java.lang.Void");
    }

    @Test
    public void testWithInnerClassDollarSign() {
        this.createNameAndCheck("java.lang.Void$InnerClass");
    }

    // name.............................................................................................................

    @Override
    public ValueType createName(final String name) {
        return ValueType.with(name);
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
    public void testIsAnyWithNumber() {
        this.isAnyAndCheck(
            ValueType.NUMBER,
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
    public void testisBooleanWithAny() {
        this.isBooleanAndCheck(
            ValueType.ANY,
            false
        );
    }

    @Test
    public void testisBooleanWithBoolean() {
        this.isBooleanAndCheck(
            ValueType.BOOLEAN,
            true
        );
    }

    @Test
    public void testisBooleanWithDate() {
        this.isBooleanAndCheck(
            ValueType.DATE,
            false
        );
    }

    @Test
    public void testisBooleanWithDateTime() {
        this.isBooleanAndCheck(
            ValueType.DATE_TIME,
            false
        );
    }

    @Test
    public void testisBooleanWithNumber() {
        this.isBooleanAndCheck(
            ValueType.NUMBER,
            false
        );
    }

    @Test
    public void testisBooleanWithText() {
        this.isBooleanAndCheck(
            ValueType.TEXT,
            false
        );
    }

    @Test
    public void testisBooleanWithTime() {
        this.isBooleanAndCheck(
            ValueType.TIME,
            false
        );
    }

    @Test
    public void testisBooleanWithUrl() {
        this.isBooleanAndCheck(
            ValueType.URL,
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

    @Test
    public void testIsDateWithUrl() {
        this.isDateAndCheck(
            ValueType.URL,
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

    @Test
    public void testIsDateTimeWithUrl() {
        this.isDateTimeAndCheck(
            ValueType.URL,
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

    // isNumber...........................................................................................................

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
            true
        );
    }

    @Test
    public void testIsNumberWithShort() {
        this.isNumberAndCheck(
            Short.class,
            true
        );
    }

    @Test
    public void testIsNumberWithInteger() {
        this.isNumberAndCheck(
            Integer.class,
            true
        );
    }

    @Test
    public void testIsNumberWithLong() {
        this.isNumberAndCheck(
            Long.class,
            true
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
            ValueType.fromClass(StringBuilder.class),
            false
        );
    }

    @Test
    public void testIsNumberWithTextDash() {
        this.isNumberAndCheck(
            ValueType.with("text-etc"),
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

    @Test
    public void testIsNumberWithUrl() {
        this.isNumberAndCheck(
            ValueType.URL,
            false
        );
    }

    private void isNumberAndCheck(final Class<?> type,
                                  final boolean expected) {
        this.isNumberAndCheck(
            ValueType.fromClass(type),
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
            ValueType.fromClass(StringBuilder.class),
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

    @Test
    public void testIsTextWithUrl() {
        this.isTextAndCheck(
            ValueType.URL,
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

    @Test
    public void testIsTimeWithUrl() {
        this.isTimeAndCheck(
            ValueType.URL,
            false
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
    public void testIsUrlWithDateTime() {
        this.isUrlAndCheck(
            ValueType.DATE_TIME,
            false
        );
    }

    @Test
    public void testIsUrlWithNumber() {
        this.isUrlAndCheck(
            ValueType.NUMBER,
            false
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

    @Test
    public void testIsUrlWithUrl() {
        this.isUrlAndCheck(
            ValueType.URL,
            true
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
    public void testUnmarshallNumber() {
        this.unmarshallAndCheck2(
            ValueType.NUMBER_STRING,
            ValueType.NUMBER
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

    @Test
    public void testUnmarshallWholeNumber() {
        this.unmarshallAndCheck2(
            ValueType.WHOLE_NUMBER_STRING,
            ValueType.WHOLE_NUMBER
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
    public ValueType unmarshall(final JsonNode from,
                                final JsonNodeUnmarshallContext context) {
        return ValueType.unmarshall(
            from,
            context
        );
    }

    // class............................................................................................................

    @Override
    public Class<ValueType> type() {
        return ValueType.class;
    }
}
