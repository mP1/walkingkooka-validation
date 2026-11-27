package walkingkooka.validation;

import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.HasCsvStringListTsvStringListTesting2;
import walkingkooka.collect.list.ImmutableListTesting;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.list.StringLikeList;
import walkingkooka.collect.list.StringList;
import walkingkooka.text.HasTextTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallerTesting;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ValueTypeListTest implements ImmutableListTesting<ValueTypeList, ValueType>,
    HasTextTesting,
    HasValueTypeTesting,
    HasCsvStringListTsvStringListTesting2<ValueTypeList>,
    JsonNodeMarshallerTesting<ValueTypeList> {

    @Test
    public void testFromStringLikeListWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValueTypeList.fromStringLikeList(null)
        );
    }

    @Test
    public void testFromStringLikeListWithEmpty() {
        this.fromStringLikeList(
            StringList.EMPTY,
            ValueTypeList.EMPTY
        );
    }

    @Test
    public void testFromStringLikeListWithNotEmpty() {
        this.fromStringLikeList(
            StringList.EMPTY.concat(VALUE_TYPE.toString())
                .concat(DIFFERENT_VALUE_TYPE.toString()),
            ValueTypeList.EMPTY.concat(VALUE_TYPE)
                .concat(DIFFERENT_VALUE_TYPE)
        );
    }

    @Test
    public void testFromStringLikeListWithNotEmptyWithNull() {
        this.fromStringLikeList(
            StringList.EMPTY.concat(VALUE_TYPE.toString())
                .concat(null)
                .concat(DIFFERENT_VALUE_TYPE.toString()),
            ValueTypeList.EMPTY.concat(VALUE_TYPE)
                .concat(null)
                .concat(DIFFERENT_VALUE_TYPE)
        );
    }

    @Test
    public void testFromStringLikeListWithNotEmptyWithEmpty() {
        this.fromStringLikeList(
            StringList.EMPTY.concat(VALUE_TYPE.toString())
                .concat("")
                .concat(DIFFERENT_VALUE_TYPE.toString()),
            ValueTypeList.EMPTY.concat(VALUE_TYPE)
                .concat(null)
                .concat(DIFFERENT_VALUE_TYPE)
        );
    }

    private void fromStringLikeList(final StringLikeList strings,
                                    final ValueTypeList expected) {
        this.checkEquals(
            expected,
            ValueTypeList.fromStringLikeList(strings),
            strings::toString
        );
    }

    // setElements......................................................................................................

    @Test
    public void testSetElementsWithListIncludesNull() {
        this.setElementsAndCheck(
            new ValueTypeList(
                Lists.of(VALUE_TYPE)
            ),
            Lists.of((ValueType)null),
            new ValueTypeList(
                Lists.of((ValueType)null)
            )
        );
    }

    @Test
    public void testSetElementsWithDifferent() {
        this.setElementsAndCheck(
            new ValueTypeList(
                Lists.of(VALUE_TYPE)
            ),
            Lists.of(DIFFERENT_VALUE_TYPE),
            new ValueTypeList(
                Lists.of(DIFFERENT_VALUE_TYPE)
            )
        );
    }

    @Test
    public void testSetElementsWithDifferentEmpty() {
        this.setElementsAndCheck(
            new ValueTypeList(
                Lists.of(VALUE_TYPE)
            ),
            Lists.empty(),
            ValueTypeList.EMPTY
        );
    }

    // subList..........................................................................................................

    private final static ValueType VALUE_TYPE1 = ValueType.CURRENCY_VALUE;
    private final static ValueType VALUE_TYPE2 = ValueType.DATA_URL;
    private final static ValueType VALUE_TYPE3 = ValueType.ENVIRONMENT_VALUE_NAME;
    private final static ValueType VALUE_TYPE4 = ValueType.FLOAT;
    private final static ValueType VALUE_TYPE5 = ValueType.JSON_STRING;

    private final static List<ValueType> ELEMENTS = Lists.of(
        VALUE_TYPE1,
        VALUE_TYPE2,
        VALUE_TYPE3,
        VALUE_TYPE4,
        VALUE_TYPE5
    );

    @Test
    public void testSubList() {
        this.subListAndCheck(
            new ValueTypeList(ELEMENTS),
            0,
            2,
            new ValueTypeList(
                Lists.of(
                    VALUE_TYPE1,
                    VALUE_TYPE2
                )
            )
        );
    }

    @Test
    public void testSubList2() {
        this.subListAndCheck(
            new ValueTypeList(ELEMENTS),
            2,
            4,
            new ValueTypeList(
                Lists.of(
                    VALUE_TYPE3,
                    VALUE_TYPE4
                )
            )
        );
    }

    @Test
    public void testSubListSubList() {
        this.subListAndCheck(
            new ValueTypeList(ELEMENTS)
                .subList(
                    1,
                    4
                ), // 2, 3, 4
            1,
            3,
            new ValueTypeList(
                Lists.of(
                    VALUE_TYPE3,
                    VALUE_TYPE4
                )
            )
        );
    }
    
    @Override
    public ValueTypeList createList() {
        return ValueTypeList.EMPTY;
    }

    @Override
    public ValueTypeList createHasCsvStringListTsvStringList() {
        return this.createList();
    }

    // HasText..........................................................................................................

    @Test
    public void testText() {
        this.csvStringListAndCheck(
            ValueTypeList.EMPTY.concat(VALUE_TYPE)
                .concat(DIFFERENT_VALUE_TYPE),
            "\"text/Text\"\n" +
                "\"number/Number\""
        );
    }

    @Test
    public void testTextIncludesNull() {
        this.csvStringListAndCheck(
            ValueTypeList.EMPTY.concat(VALUE_TYPE)
                .concat(DIFFERENT_VALUE_TYPE)
                .concat(null),
            "\"text/Text\"\n" +
                "\"number/Number\"\n" +
                "\"\""
        );
    }

    // HasCsvStringList.................................................................................................

    @Test
    public void testCsvStringList() {
        this.csvStringListAndCheck(
            ValueTypeList.EMPTY.concat(VALUE_TYPE)
                .concat(DIFFERENT_VALUE_TYPE),
            "\"text/Text\"\n" +
                "\"number/Number\""
        );
    }

    @Test
    public void testCsvStringListWithNull() {
        this.csvStringListAndCheck(
            ValueTypeList.EMPTY.concat(VALUE_TYPE)
                .concat(null)
                .concat(DIFFERENT_VALUE_TYPE),
            "\"text/Text\"\n" +
                "\"\"\n" +
                "\"number/Number\""
        );
    }

    // HasTsvStringList.................................................................................................

    @Test
    public void testTsvStringList() {
        this.tsvStringListAndCheck(
            ValueTypeList.EMPTY.concat(VALUE_TYPE)
                .concat(null)
                .concat(DIFFERENT_VALUE_TYPE),
            "\"text/Text\"\n" +
                "\"\"\n" +
                "\"number/Number\""
        );
    }

    // json.............................................................................................................

    @Test
    public void testMarshall() {
        this.marshallAndCheck(
            ValueTypeList.EMPTY.concat(VALUE_TYPE)
                .concat(DIFFERENT_VALUE_TYPE),
            "\"text/Text,number/Number\""
        );
    }

    @Override
    public ValueTypeList unmarshall(final JsonNode jsonNode,
                                    final JsonNodeUnmarshallContext context) {
        return ValueTypeList.unmarshall(
            jsonNode,
            context
        );
    }

    @Override
    public ValueTypeList createJsonNodeMarshallingValue() {
        return this.createList();
    }

    // class............................................................................................................

    @Override
    public Class<ValueTypeList> type() {
        return ValueTypeList.class;
    }
}
