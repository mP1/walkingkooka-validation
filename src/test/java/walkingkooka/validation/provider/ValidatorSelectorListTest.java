package walkingkooka.validation.provider;

import org.junit.jupiter.api.Test;
import walkingkooka.EmptyTextException;
import walkingkooka.collect.list.CsvStringList;
import walkingkooka.collect.list.HasCsvStringListTsvStringListTesting2;
import walkingkooka.collect.list.ImmutableListTesting;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.list.StringList;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallerTesting;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ValidatorSelectorListTest implements ImmutableListTesting<ValidatorSelectorList, ValidatorSelector>,
    HasCsvStringListTsvStringListTesting2<ValidatorSelectorList>,
    HasOptionalValidatorSelectorTesting,
    JsonNodeMarshallerTesting<ValidatorSelectorList> {

    // fromStringLikeList................................................................................................

    @Test
    public void testFromStringLikeListWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValidatorSelectorList.fromStringLikeList(null)
        );
    }


    @Test
    public void testFromStringLikeListWithNullElementFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValidatorSelectorList.fromStringLikeList(
                StringList.EMPTY.concat(VALIDATOR_SELECTOR.text())
                    .concat(null)
                    .concat(DIFFERENT_VALIDATOR_SELECTOR.text()
                    )
            )
        );
    }

    @Test
    public void testFromStringLikeListWithEmptyStringFails() {
        assertThrows(
            EmptyTextException.class,
            () -> ValidatorSelectorList.fromStringLikeList(
                StringList.EMPTY.concat(VALIDATOR_SELECTOR.text())
                    .concat("")
            )
        );
    }

    @Test
    public void testFromStringLikeListWithEmpty() {
        this.fromStringLikeListAndCheck(
            CsvStringList.EMPTY,
            ValidatorSelectorList.EMPTY
        );
    }

    @Test
    public void testFromStringLikeListWithNotEmpty() {
        this.fromStringLikeListAndCheck(
            CsvStringList.EMPTY.concat(VALIDATOR_SELECTOR.text())
                .concat(DIFFERENT_VALIDATOR_SELECTOR.text()),
            ValidatorSelectorList.EMPTY.concat(VALIDATOR_SELECTOR)
                .concat(DIFFERENT_VALIDATOR_SELECTOR)
        );
    }

    private void fromStringLikeListAndCheck(final CsvStringList csvStringList,
                                            final ValidatorSelectorList expected) {
        this.checkEquals(
            expected,
            ValidatorSelectorList.fromStringLikeList(csvStringList)
        );
    }

    // concat...........................................................................................................

    @Test
    public void testConcatWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValidatorSelectorList.EMPTY.concat(null)
        );
    }

    // setElements......................................................................................................

    @Test
    public void testSetElementsWithNullElementFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValidatorSelectorList.EMPTY.setElements(
                Arrays.asList((ValidatorSelector) null)
            )
        );
    }

    @Test
    public void testSetElementsWithDifferent() {
        this.setElementsAndCheck(
            new ValidatorSelectorList(
                Lists.of(VALIDATOR_SELECTOR)
            ),
            Lists.of(DIFFERENT_VALIDATOR_SELECTOR),
            new ValidatorSelectorList(
                Lists.of(DIFFERENT_VALIDATOR_SELECTOR)
            )
        );
    }

    @Test
    public void testSetElementsWithDifferentEmpty() {
        this.setElementsAndCheck(
            new ValidatorSelectorList(
                Lists.of(VALIDATOR_SELECTOR)
            ),
            Lists.empty(),
            ValidatorSelectorList.EMPTY
        );
    }

    // subList..........................................................................................................

    private final static ValidatorSelector VALIDATOR_SELECTOR1 = VALIDATOR_SELECTOR;
    private final static ValidatorSelector VALIDATOR_SELECTOR2 = DIFFERENT_VALIDATOR_SELECTOR;
    private final static ValidatorSelector VALIDATOR_SELECTOR3 = ValidatorSelector.parse("test-validator-333");
    private final static ValidatorSelector VALIDATOR_SELECTOR4 = ValidatorSelector.parse("test-validator-444");
    private final static ValidatorSelector VALIDATOR_SELECTOR5 = ValidatorSelector.parse("test-validator-555");

    private final static List<ValidatorSelector> ELEMENTS = Lists.of(
        VALIDATOR_SELECTOR1,
        VALIDATOR_SELECTOR2,
        VALIDATOR_SELECTOR3,
        VALIDATOR_SELECTOR4,
        VALIDATOR_SELECTOR5
    );

    @Test
    public void testSubList() {
        this.subListAndCheck(
            new ValidatorSelectorList(ELEMENTS),
            0,
            2,
            new ValidatorSelectorList(
                Lists.of(
                    VALIDATOR_SELECTOR1,
                    VALIDATOR_SELECTOR2
                )
            )
        );
    }

    @Test
    public void testSubList2() {
        this.subListAndCheck(
            new ValidatorSelectorList(ELEMENTS),
            2,
            4,
            new ValidatorSelectorList(
                Lists.of(
                    VALIDATOR_SELECTOR3,
                    VALIDATOR_SELECTOR4
                )
            )
        );
    }

    @Test
    public void testSubListSubList() {
        this.subListAndCheck(
            new ValidatorSelectorList(ELEMENTS)
                .subList(
                    1,
                    4
                ), // 2, 3, 4
            1,
            3,
            new ValidatorSelectorList(
                Lists.of(
                    VALIDATOR_SELECTOR3,
                    VALIDATOR_SELECTOR4
                )
            )
        );
    }

    @Override
    public ValidatorSelectorList createList() {
        return ValidatorSelectorList.EMPTY;
    }

    // HasCsvStringList.................................................................................................

    @Test
    public void testCsvStringListWithEmpty() {
        this.csvStringListAndCheck(
            ValidatorSelectorList.EMPTY,
            ""
        );
    }

    @Test
    public void testCsvStringListWithNotEmpty() {
        this.csvStringListAndCheck(
            ValidatorSelectorList.EMPTY.concat(VALIDATOR_SELECTOR)
                .concat(DIFFERENT_VALIDATOR_SELECTOR),
            "\"absolute-url\"\n" +
                "\"email-address\""
        );
    }

    // HasTsvStringList.................................................................................................

    @Test
    public void testTsvStringListWithEmpty() {
        this.tsvStringListAndCheck(
            ValidatorSelectorList.EMPTY,
            ""
        );
    }

    @Test
    public void testTsvStringListWithNotEmpty() {
        this.tsvStringListAndCheck(
            ValidatorSelectorList.EMPTY.concat(VALIDATOR_SELECTOR)
                .concat(DIFFERENT_VALIDATOR_SELECTOR),
            "\"absolute-url\"\n" +
                "\"email-address\""
        );
    }

    @Override
    public ValidatorSelectorList createHasCsvStringListTsvStringList() {
        return this.createList();
    }

    // json.............................................................................................................

    @Test
    public void testMarshall() {
        this.marshallAndCheck(
            ValidatorSelectorList.EMPTY.concat(VALIDATOR_SELECTOR)
                .concat(DIFFERENT_VALIDATOR_SELECTOR),
            "\"absolute-url,email-address\""
        );
    }

    @Override
    public ValidatorSelectorList unmarshall(final JsonNode jsonNode,
                                            final JsonNodeUnmarshallContext context) {
        return ValidatorSelectorList.unmarshall(
            jsonNode,
            context
        );
    }

    @Override
    public ValidatorSelectorList createJsonNodeMarshallingValue() {
        return this.createList();
    }

    // class............................................................................................................

    @Override
    public Class<ValidatorSelectorList> type() {
        return ValidatorSelectorList.class;
    }
}
