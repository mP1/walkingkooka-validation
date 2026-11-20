package walkingkooka.validation;

import walkingkooka.collect.list.CsvStringList;
import walkingkooka.collect.list.HasCsvStringList;
import walkingkooka.collect.list.HasTsvStringList;
import walkingkooka.collect.list.ImmutableListDefaults;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.list.StringLikeList;
import walkingkooka.collect.list.TsvStringList;
import walkingkooka.text.CharSequences;

import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A {@link List} that holds nullable {@link ValueType valueTypes}.
 */
public final class ValueTypeList extends AbstractList<ValueType>
    implements ImmutableListDefaults<ValueTypeList, ValueType>,
    HasCsvStringList,
    HasTsvStringList {

    public final static ValueTypeList EMPTY = new ValueTypeList(Lists.empty());

    /**
     * Accepts a {@link StringLikeList} converting non null and non empty into a {@link ValueType}. Null or empty strings
     * will become null {@link ValueType}.
     */
    public static ValueTypeList fromStringLikeList(final StringLikeList strings) {
        Objects.requireNonNull(strings, "strings");

        return EMPTY.setElements(
            strings.stream()
                .map(
                    (final String string) -> CharSequences.isNullOrEmpty(string) ?
                        null :
                        ValueType.parseOrFail(string)
                ).collect(Collectors.toList())
        );
    }

    // @VisibleForTesting
    ValueTypeList(final List<ValueType> list) {
        super();
        this.list = list;
    }

    @Override
    public ValueType get(final int index) {
        return this.list.get(index);
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public ValueTypeList setElements(final Collection<ValueType> collection) {
        ValueTypeList valueTypeList;

        if (collection instanceof ValueTypeList) {
            valueTypeList = (ValueTypeList) collection;
        } else {
            final List<ValueType> copy = Lists.array();
            copy.addAll(collection);

            if (copy.equals(this)) {
                valueTypeList = this;
            } else {
                valueTypeList = copy.isEmpty() ?
                    EMPTY :
                    new ValueTypeList(copy);
            }
        }

        return valueTypeList;
    }

    @Override
    public void elementCheck(final ValueType valueType) {
        // null is allowed
    }

    private final List<ValueType> list;

    // HasCsvStringList.................................................................................................

    @Override
    public CsvStringList csvStringList() {
        return CsvStringList.makeCsvStringList(
            this.list,
            (final ValueType valueType) -> null == valueType ?
                "" :
                valueType.toString()
        );
    }

    // HasTsvStringList.................................................................................................

    @Override
    public TsvStringList tsvStringList() {
        return TsvStringList.EMPTY.setElements(
            this.csvStringList()
        );
    }
}
