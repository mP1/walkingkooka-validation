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

final public class ValueTypeNameTest implements PluginNameTesting<ValueTypeName> {

    // fromClass........................................................................................................

    @Test
    public void testFromClassWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValueTypeName.fromClass(null)
        );
    }

    @Test
    public void testFromClassWithBooleanType() {
        this.fromClassAndCheck(
            Boolean.TYPE,
            ValueTypeName.BOOLEAN
        );
    }

    @Test
    public void testFromClassWithBooleanClass() {
        this.fromClassAndCheck(
            Boolean.class,
            ValueTypeName.BOOLEAN
        );
    }

    @Test
    public void testFromClassWithLocalDate() {
        this.fromClassAndCheck(
            LocalDate.class,
            ValueTypeName.DATE
        );
    }

    @Test
    public void testFromClassWithLocalDateTime() {
        this.fromClassAndCheck(
            LocalDateTime.class,
            ValueTypeName.DATE_TIME
        );
    }

    @Test
    public void testFromClassWithLocalTime() {
        this.fromClassAndCheck(
            LocalTime.class,
            ValueTypeName.TIME
        );
    }

    @Test
    public void testFromClassWithByteType() {
        this.fromClassAndCheck(
            Byte.TYPE,
            ValueTypeName.with("byte")
        );
    }

    @Test
    public void testFromClassWithByteClass() {
        this.fromClassAndCheck(
            Byte.class,
            ValueTypeName.with("number(Byte)")
        );
    }

    @Test
    public void testFromClassWithShortType() {
        this.fromClassAndCheck(
            Short.TYPE,
            ValueTypeName.with("short")
        );
    }

    @Test
    public void testFromClassWithShortClass() {
        this.fromClassAndCheck(
            Short.class,
            ValueTypeName.with("number(Short)")
        );
    }

    @Test
    public void testFromClassWithIntegerType() {
        this.fromClassAndCheck(
            Integer.TYPE,
            ValueTypeName.with("int")
        );
    }

    @Test
    public void testFromClassWithIntegerClass() {
        this.fromClassAndCheck(
            Integer.class,
            ValueTypeName.with("number(Integer)")
        );
    }

    @Test
    public void testFromClassWithLongType() {
        this.fromClassAndCheck(
            Long.TYPE,
            ValueTypeName.with("long")
        );
    }

    @Test
    public void testFromClassWithLongClass() {
        this.fromClassAndCheck(
            Long.class,
            ValueTypeName.with("number(Long)")
        );
    }

    @Test
    public void testFromClassWithFloatType() {
        this.fromClassAndCheck(
            Float.TYPE,
            ValueTypeName.with("float")
        );
    }

    @Test
    public void testFromClassWithFloatClass() {
        this.fromClassAndCheck(
            Float.class,
            ValueTypeName.with("number(Float)")
        );
    }

    @Test
    public void testFromClassWithDoubleType() {
        this.fromClassAndCheck(
            Double.TYPE,
            ValueTypeName.with("double")
        );
    }

    @Test
    public void testFromClassWithDoubleClass() {
        this.fromClassAndCheck(
            Double.class,
            ValueTypeName.with("number(Double)")
        );
    }

    @Test
    public void testFromClassWithBigDecimal() {
        this.fromClassAndCheck(
            BigDecimal.class,
            ValueTypeName.with("number(BigDecimal)")
        );
    }

    @Test
    public void testFromClassWithBigInteger() {
        this.fromClassAndCheck(
            BigInteger.class,
            ValueTypeName.with("number(BigInteger)")
        );
    }

    @Test
    public void testFromClassWithString() {
        this.fromClassAndCheck(
            String.class,
            ValueTypeName.TEXT
        );
    }

    @Test
    public void testFromClassWithStringBuffer() {
        this.fromClassAndCheck(
            StringBuffer.class,
            ValueTypeName.with("text(StringBuffer)")
        );
    }

    @Test
    public void testFromClassWithStringBuilder() {
        this.fromClassAndCheck(
            StringBuilder.class,
            ValueTypeName.with("text(StringBuilder)")
        );
    }

    @Test
    public void testFromClassWithEmailAddress() {
        this.fromClassAndCheck(
            EmailAddress.class,
            ValueTypeName.EMAIL
        );
    }

    @Test
    public void testFromClassWithAbsoluteUrl() {
        this.fromClassAndCheck(
            AbsoluteUrl.class,
            ValueTypeName.URL
        );
    }

    @Test
    public void testFromClassWithVoid() {
        this.fromClassAndCheck(
            Void.class,
            ValueTypeName.with("java.lang.Void")
        );
    }

    @Test
    public void testFromClassWithObject() {
        this.fromClassAndCheck(
            Object.class,
            ValueTypeName.ANY
        );
    }

    private void fromClassAndCheck(final Class<?> classs,
                                   final ValueTypeName expected) {
        this.checkEquals(
            expected,
            ValueTypeName.fromClass(classs)
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
    public ValueTypeName createName(final String name) {
        return ValueTypeName.with(name);
    }

    // isAny............................................................................................................

    @Test
    public void testIsAnyWithAny() {
        this.isAnyAndCheck(
            ValueTypeName.ANY,
            true
        );
    }

    @Test
    public void testIsAnyWithNumber() {
        this.isAnyAndCheck(
            ValueTypeName.NUMBER,
            false
        );
    }

    @Test
    public void testIsAnyWithText() {
        this.isAnyAndCheck(
            ValueTypeName.TEXT,
            false
        );
    }

    private void isAnyAndCheck(final ValueTypeName name,
                               final boolean expected) {
        this.checkEquals(
            expected,
            name.isAny(),
            name::toString
        );
    }

    // isDate...........................................................................................................

    @Test
    public void testIsDateWithAny() {
        this.isDateAndCheck(
            ValueTypeName.ANY,
            false
        );
    }

    @Test
    public void testIsDateWithDate() {
        this.isDateAndCheck(
            ValueTypeName.DATE,
            true
        );
    }

    @Test
    public void testIsDateWithDateTime() {
        this.isDateAndCheck(
            ValueTypeName.DATE_TIME,
            false
        );
    }

    @Test
    public void testIsDateWithNumber() {
        this.isDateAndCheck(
            ValueTypeName.NUMBER,
            false
        );
    }

    @Test
    public void testIsDateWithText() {
        this.isDateAndCheck(
            ValueTypeName.TEXT,
            false
        );
    }

    private void isDateAndCheck(final ValueTypeName name,
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
            ValueTypeName.ANY,
            false
        );
    }

    @Test
    public void testIsDateTimeWithDate() {
        this.isDateTimeAndCheck(
            ValueTypeName.DATE,
            false
        );
    }

    @Test
    public void testIsDateTimeWithDateTime() {
        this.isDateTimeAndCheck(
            ValueTypeName.DATE_TIME,
            true
        );
    }

    @Test
    public void testIsDateTimeWithNumber() {
        this.isDateTimeAndCheck(
            ValueTypeName.NUMBER,
            false
        );
    }

    @Test
    public void testIsDateTimeWithText() {
        this.isDateTimeAndCheck(
            ValueTypeName.TEXT,
            false
        );
    }

    private void isDateTimeAndCheck(final ValueTypeName name,
                                    final boolean expected) {
        this.checkEquals(
            expected,
            name.isDateTime(),
            name::toString
        );
    }

    // isNumber...........................................................................................................

    @Test
    public void testIsNumberWithNumber() {
        this.isNumberAndCheck(
            ValueTypeName.NUMBER,
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
            ValueTypeName.TEXT,
            false
        );
    }

    @Test
    public void testIsNumberWithTextStringBuilder() {
        this.isNumberAndCheck(
            ValueTypeName.fromClass(StringBuilder.class),
            false
        );
    }

    @Test
    public void testIsNumberWithTextDash() {
        this.isNumberAndCheck(
            ValueTypeName.with("text-etc"),
            false
        );
    }

    private void isNumberAndCheck(final Class<?> type,
                                  final boolean expected) {
        this.isNumberAndCheck(
            ValueTypeName.fromClass(type),
            expected
        );
    }

    private void isNumberAndCheck(final ValueTypeName name,
                                  final boolean expected) {
        this.checkEquals(
            expected,
            name.isNumber(),
            name::toString
        );
    }
    
    // isText...........................................................................................................

    @Test
    public void testIsTextWithNumber() {
        this.isTextAndCheck(
            ValueTypeName.NUMBER,
            false
        );
    }

    @Test
    public void testIsTextWithText() {
        this.isTextAndCheck(
            ValueTypeName.TEXT,
            true
        );
    }

    @Test
    public void testIsTextWithTextStringBuilder() {
        this.isTextAndCheck(
            ValueTypeName.fromClass(StringBuilder.class),
            true
        );
    }

    private void isTextAndCheck(final ValueTypeName name,
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
            ValueTypeName.ANY,
            false
        );
    }

    @Test
    public void testIsTimeWithDate() {
        this.isTimeAndCheck(
            ValueTypeName.DATE,
            false
        );
    }

    @Test
    public void testIsTimeWithDateTime() {
        this.isTimeAndCheck(
            ValueTypeName.DATE_TIME,
            false
        );
    }

    @Test
    public void testIsTimeWithNumber() {
        this.isTimeAndCheck(
            ValueTypeName.NUMBER,
            false
        );
    }

    @Test
    public void testIsTimeWithText() {
        this.isTimeAndCheck(
            ValueTypeName.TEXT,
            false
        );
    }

    @Test
    public void testIsTimeWithTime() {
        this.isTimeAndCheck(
            ValueTypeName.TIME,
            true
        );
    }

    private void isTimeAndCheck(final ValueTypeName name,
                                final boolean expected) {
        this.checkEquals(
            expected,
            name.isTime(),
            name::toString
        );
    }
    
    // json.............................................................................................................

    @Test
    public void testUnmarshallAny() {
        this.unmarshallAndCheck2(
            ValueTypeName.ANY_STRING,
            ValueTypeName.ANY
        );
    }

    @Test
    public void testUnmarshallBoolean() {
        this.unmarshallAndCheck2(
            ValueTypeName.BOOLEAN_STRING,
            ValueTypeName.BOOLEAN
        );
    }

    @Test
    public void testUnmarshallDate() {
        this.unmarshallAndCheck2(
            ValueTypeName.DATE_STRING,
            ValueTypeName.DATE
        );
    }

    @Test
    public void testUnmarshallDateTime() {
        this.unmarshallAndCheck2(
            ValueTypeName.DATE_TIME_STRING,
            ValueTypeName.DATE_TIME
        );
    }

    @Test
    public void testUnmarshallNumber() {
        this.unmarshallAndCheck2(
            ValueTypeName.NUMBER_STRING,
            ValueTypeName.NUMBER
        );
    }

    @Test
    public void testUnmarshallText() {
        this.unmarshallAndCheck2(
            ValueTypeName.TEXT_STRING,
            ValueTypeName.TEXT
        );
    }

    @Test
    public void testUnmarshallTime() {
        this.unmarshallAndCheck2(
            ValueTypeName.TIME_STRING,
            ValueTypeName.TIME
        );
    }

    @Test
    public void testUnmarshallWholeNumber() {
        this.unmarshallAndCheck2(
            ValueTypeName.WHOLE_NUMBER_STRING,
            ValueTypeName.WHOLE_NUMBER
        );
    }

    private void unmarshallAndCheck2(final String string,
                                     final ValueTypeName expected) {
        assertSame(
            expected,
            ValueTypeName.unmarshall(
                JsonNode.string(string),
                JsonNodeUnmarshallContexts.fake()
            )
        );
    }

    @Override
    public ValueTypeName unmarshall(final JsonNode from,
                                    final JsonNodeUnmarshallContext context) {
        return ValueTypeName.unmarshall(
            from,
            context
        );
    }

    // class............................................................................................................

    @Override
    public Class<ValueTypeName> type() {
        return ValueTypeName.class;
    }
}
