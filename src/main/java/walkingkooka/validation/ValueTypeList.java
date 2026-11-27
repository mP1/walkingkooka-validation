package walkingkooka.validation;

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
 * A {@link List} that holds nullable {@link ValueType valueTypes}.
 */
public final class ValueTypeList extends AbstractList<ValueType>
    implements ImmutableListDefaults<ValueTypeList, ValueType>,
    HasCsvStringListTsvStringList {

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
    public ValueTypeList setElements(final Collection<ValueType> valueTypes) {
        final ValueTypeList valueTypeList;

        if (valueTypes instanceof ValueTypeList) {
            valueTypeList = (ValueTypeList) valueTypes;
        } else {
            Objects.requireNonNull(valueTypes, "valueTypes");

            final List<ValueType> copy = Lists.array();
            for (final ValueType valueType : valueTypes) {
                this.elementCheck(valueType);
                copy.add(valueType);
            }

            valueTypeList = this.equals(copy) ?
                this :
                this.replaceValueTypes(copy);
        }

        return valueTypeList;
    }

    @Override
    public ValueTypeList subList(final int from,
                                 final int to) {
        return 0 == from && this.size() == to ?
            this :
            this.replaceValueTypes(
                this.list.subList(
                    from,
                    to
                )
            );
    }

    private ValueTypeList replaceValueTypes(final List<ValueType> valueTypes) {
        return valueTypes.isEmpty() ?
            EMPTY :
            new ValueTypeList(valueTypes);
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

    // json.............................................................................................................

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(ValueTypeList.class),
            ValueTypeList::unmarshall,
            ValueTypeList::marshall,
            ValueTypeList.class
        );
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return context.marshall(this.csvStringList());
    }

    static ValueTypeList unmarshall(final JsonNode node,
                                    final JsonNodeUnmarshallContext context) {
        return ValueTypeList.fromStringLikeList(
            context.unmarshall(
                node,
                CsvStringList.class
            )
        );
    }
}
