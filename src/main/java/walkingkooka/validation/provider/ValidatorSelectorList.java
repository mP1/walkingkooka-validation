package walkingkooka.validation.provider;

import walkingkooka.collect.list.CsvStringList;
import walkingkooka.collect.list.HasCsvStringListTsvStringList;
import walkingkooka.collect.list.ImmutableListDefaults;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.list.StringLikeList;
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
                .map(ValidatorSelector::parse)
                .collect(Collectors.toList())
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
    public ValidatorSelectorList setElements(final Collection<ValidatorSelector> collection) {
        final ValidatorSelectorList currencyList;

        if (collection instanceof ValidatorSelectorList) {
            currencyList = (ValidatorSelectorList) collection;
        } else {
            Objects.requireNonNull(collection, "collection");

            final List<ValidatorSelector> copy = Lists.array();
            for (final ValidatorSelector validatorSelector : collection) {
                this.elementCheck(validatorSelector);

                copy.add(validatorSelector);
            }

            if (copy.equals(this)) {
                currencyList = this;
            } else {
                currencyList = copy.isEmpty() ?
                    EMPTY :
                    new ValidatorSelectorList(copy);
            }
        }

        return currencyList;
    }

    @Override
    public void elementCheck(final ValidatorSelector validatorSelector) {
        Objects.requireNonNull(validatorSelector, "validatorSelector");
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
            ValidatorSelector::text
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
