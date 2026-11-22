package walkingkooka.validation.provider;

import org.junit.jupiter.api.Test;
import walkingkooka.EmptyTextException;
import walkingkooka.collect.list.CsvStringList;
import walkingkooka.collect.list.HasCsvStringListTsvStringListTesting2;
import walkingkooka.collect.list.ImmutableListTesting;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.list.StringList;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ValidatorSelectorListTest implements ImmutableListTesting<ValidatorSelectorList, ValidatorSelector>,
    HasCsvStringListTsvStringListTesting2<ValidatorSelectorList>,
    HasOptionalValidatorSelectorTesting {

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

    // class............................................................................................................

    @Override
    public Class<ValidatorSelectorList> type() {
        return ValidatorSelectorList.class;
    }
}
