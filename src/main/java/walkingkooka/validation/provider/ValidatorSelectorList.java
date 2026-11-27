package walkingkooka.validation.provider;

import walkingkooka.collect.list.CsvStringList;
import walkingkooka.collect.list.HasCsvStringListTsvStringList;
import walkingkooka.collect.list.ImmutableListDefaults;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.list.StringLikeList;
import walkingkooka.text.CharSequences;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A {@link List} that holds non-null {@link ValidatorSelector validatorSelector}.
 */
public final class ValidatorSelectorList extends AbstractList<ValidatorSelector>
    implements ImmutableListDefaults<ValidatorSelectorList, ValidatorSelector>,
    HasCsvStringListTsvStringList {

    public static ValidatorSelectorList fromStringLikeList(final StringLikeList stringLikeList) {
        Objects.requireNonNull(stringLikeList, "stringLikeList");

        return EMPTY.setElements(
            stringLikeList.stream()
                .map(
                    (final String string) -> CharSequences.isNullOrEmpty(string) ?
                        null :
                        ValidatorSelector.parse(string)
                ).collect(Collectors.toList())
        );
    }

    public final static ValidatorSelectorList EMPTY = new ValidatorSelectorList(Lists.empty());

    // @VisibleForTesting
    ValidatorSelectorList(final List<ValidatorSelector> list) {
        super();
        this.list = list;
    }

    @Override
    public ValidatorSelector get(final int index) {
        return this.list.get(index);
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public ValidatorSelectorList setElements(final Collection<ValidatorSelector> elements) {
        return (ValidatorSelectorList)
            this.setElementsMaybeCreate(
                elements,
                (final Collection<ValidatorSelector> collection) -> collection instanceof ValidatorSelectorList,
                this::elementCheck,
                this::replaceValidatorSelectors
            );
    }

    @Override
    public ValidatorSelectorList subList(final int from,
                                         final int to) {
        return 0 == from && this.size() == to ?
            this :
            this.replaceValidatorSelectors(
                this.list.subList(
                    from,
                    to
                )
            );
    }

    private ValidatorSelectorList replaceValidatorSelectors(final List<ValidatorSelector> validatorSelectors) {
        return validatorSelectors.isEmpty() ?
            EMPTY :
            new ValidatorSelectorList(validatorSelectors);
    }

    @Override
    public void elementCheck(final ValidatorSelector validatorSelector) {
        // allow null
    }

    private final List<ValidatorSelector> list;

    // HasCsvString.....................................................................................................

    /**
     * Returns a {@link CsvStringList}, with each value a single {@link ValidatorSelector} after converting to CSV.
     */
    @Override
    public CsvStringList csvStringList() {
        return CsvStringList.makeCsvStringList(
            this.list,
            (final ValidatorSelector validatorSelector) -> null != validatorSelector ?
                validatorSelector.text() :
                ""
        );
    }

    // json.............................................................................................................

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(ValidatorSelectorList.class),
            ValidatorSelectorList::unmarshall,
            ValidatorSelectorList::marshall,
            ValidatorSelectorList.class
        );
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return context.marshall(this.csvStringList());
    }

    static ValidatorSelectorList unmarshall(final JsonNode node,
                                            final JsonNodeUnmarshallContext context) {
        return ValidatorSelectorList.fromStringLikeList(
            context.unmarshall(
                node,
                CsvStringList.class
            )
        );
    }
}
