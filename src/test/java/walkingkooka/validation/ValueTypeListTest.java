package walkingkooka.validation;

import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.HasCsvStringListTsvStringListTesting2;
import walkingkooka.collect.list.ImmutableListTesting;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.list.StringLikeList;
import walkingkooka.collect.list.StringList;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ValueTypeListTest implements ImmutableListTesting<ValueTypeList, ValueType>,
    HasValueTypeTesting,
    HasCsvStringListTsvStringListTesting2<ValueTypeList> {

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

    @Override
    public ValueTypeList createList() {
        return ValueTypeList.EMPTY;
    }

    @Override
    public ValueTypeList createHasCsvStringListTsvStringList() {
        return this.createList();
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

    // class............................................................................................................

    @Override
    public Class<ValueTypeList> type() {
        return ValueTypeList.class;
    }
}
