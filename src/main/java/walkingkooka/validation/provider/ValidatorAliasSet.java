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

package walkingkooka.validation.provider;

import walkingkooka.collect.set.ImmutableSortedSetDefaults;
import walkingkooka.collect.set.SortedSets;
import walkingkooka.plugin.PluginAliasSet;
import walkingkooka.plugin.PluginAliasSetLike;
import walkingkooka.text.CharacterConstant;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.SortedSet;

public final class ValidatorAliasSet extends AbstractSet<ValidatorAlias>
    implements PluginAliasSetLike<ValidatorName,
    ValidatorInfo,
    ValidatorInfoSet,
    ValidatorSelector,
    ValidatorAlias,
    ValidatorAliasSet>,
    ImmutableSortedSetDefaults<ValidatorAliasSet, ValidatorAlias> {

    /**
     * An empty {@link ValidatorAliasSet}.
     */
    public final static ValidatorAliasSet EMPTY = new ValidatorAliasSet(
        PluginAliasSet.with(
            SortedSets.empty(),
            ValidatorPluginHelper.INSTANCE
        )
    );

    /**
     * {@see PluginAliasSet#SEPARATOR}
     */
    public final static CharacterConstant SEPARATOR = PluginAliasSet.SEPARATOR;

    /**
     * Factory that creates {@link ValidatorAliasSet} with the given aliases.
     */
    public static ValidatorAliasSet with(final SortedSet<ValidatorAlias> aliases) {
        return EMPTY.setElements(aliases);
    }

    public static ValidatorAliasSet parse(final String text) {
        return new ValidatorAliasSet(
            PluginAliasSet.parse(
                text,
                ValidatorPluginHelper.INSTANCE
            )
        );
    }

    private ValidatorAliasSet(final PluginAliasSet<ValidatorName, ValidatorInfo, ValidatorInfoSet, ValidatorSelector, ValidatorAlias, ValidatorAliasSet> pluginAliasSet) {
        this.pluginAliasSet = pluginAliasSet;
    }

    @Override
    public ValidatorSelector selector(final ValidatorSelector selector) {
        return this.pluginAliasSet.selector(selector);
    }

    @Override
    public Optional<ValidatorSelector> aliasSelector(final ValidatorName name) {
        return this.pluginAliasSet.aliasSelector(name);
    }

    @Override
    public Optional<ValidatorName> aliasOrName(final ValidatorName name) {
        return this.pluginAliasSet.aliasOrName(name);
    }

    @Override
    public ValidatorInfoSet merge(final ValidatorInfoSet infos) {
        return this.pluginAliasSet.merge(infos);
    }

    @Override
    public boolean containsAliasOrName(final ValidatorName aliasOrName) {
        return this.pluginAliasSet.containsAliasOrName(aliasOrName);
    }

    @Override
    public ValidatorAliasSet concatOrReplace(final ValidatorAlias alias) {
        return new ValidatorAliasSet(
            this.pluginAliasSet.concatOrReplace(alias)
        );
    }

    @Override
    public ValidatorAliasSet deleteAliasOrNameAll(final Collection<ValidatorName> aliasOrNames) {
        return this.setElements(
            this.pluginAliasSet.deleteAliasOrNameAll(aliasOrNames)
        );
    }

    @Override
    public ValidatorAliasSet keepAliasOrNameAll(final Collection<ValidatorName> aliasOrNames) {
        return this.setElements(
            this.pluginAliasSet.keepAliasOrNameAll(aliasOrNames)
        );
    }

    // ImmutableSortedSet...............................................................................................

    @Override
    public Comparator<? super ValidatorAlias> comparator() {
        return this.pluginAliasSet.comparator();
    }

    @Override
    public Iterator<ValidatorAlias> iterator() {
        return this.pluginAliasSet.stream().iterator();
    }

    @Override
    public int size() {
        return this.pluginAliasSet.size();
    }

    @Override
    public ValidatorAliasSet setElements(final SortedSet<ValidatorAlias> aliases) {
        final ValidatorAliasSet after = new ValidatorAliasSet(
            this.pluginAliasSet.setElements(aliases)
        );
        return this.pluginAliasSet.equals(aliases) ?
            this :
            after;
    }

    @Override
    public ValidatorAliasSet setElementsFailIfDifferent(SortedSet<ValidatorAlias> sortedSet) {
        return null;
    }

    @Override
    public SortedSet<ValidatorAlias> toSet() {
        return this.pluginAliasSet.toSet();
    }

    @Override
    public ValidatorAliasSet subSet(final ValidatorAlias from,
                                    final ValidatorAlias to) {
        return this.setElements(
            this.pluginAliasSet.subSet(
                from,
                to
            )
        );
    }

    @Override
    public ValidatorAliasSet headSet(final ValidatorAlias alias) {
        return this.setElements(
            this.pluginAliasSet.headSet(alias)
        );
    }

    @Override
    public ValidatorAliasSet tailSet(final ValidatorAlias alias) {
        return this.setElements(
            this.pluginAliasSet.tailSet(alias)
        );
    }

    @Override
    public ValidatorAliasSet concat(final ValidatorAlias alias) {
        return this.setElements(
            this.pluginAliasSet.concat(alias)
        );
    }

    @Override
    public ValidatorAliasSet concatAll(final Collection<ValidatorAlias> aliases) {
        return this.setElements(
            this.pluginAliasSet.concatAll(aliases)
        );
    }

    @Override
    public ValidatorAliasSet delete(final ValidatorAlias alias) {
        return this.setElements(
            this.pluginAliasSet.delete(alias)
        );
    }

    @Override
    public ValidatorAliasSet deleteAll(final Collection<ValidatorAlias> aliases) {
        return this.setElements(
            this.pluginAliasSet.deleteAll(aliases)
        );
    }

    @Override
    public ValidatorAliasSet replace(final ValidatorAlias oldAlias,
                                     final ValidatorAlias newAlias) {
        return this.setElements(
            this.pluginAliasSet.replace(
                oldAlias,
                newAlias
            )
        );
    }

    @Override
    public ValidatorAlias first() {
        return this.pluginAliasSet.first();
    }

    @Override
    public ValidatorAlias last() {
        return this.pluginAliasSet.last();
    }

    @Override
    public String text() {
        return this.pluginAliasSet.text();
    }

    @Override
    public void printTree(final IndentingPrinter printer) {
        this.pluginAliasSet.printTree(printer);
    }

    private final PluginAliasSet<ValidatorName, ValidatorInfo, ValidatorInfoSet, ValidatorSelector, ValidatorAlias, ValidatorAliasSet> pluginAliasSet;

    // Json.............................................................................................................

    static void register() {
        // helps force registry of json marshaller
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return JsonNode.string(
            this.pluginAliasSet.text()
        );
    }

    static ValidatorAliasSet unmarshall(final JsonNode node,
                                        final JsonNodeUnmarshallContext context) {
        return parse(
            node.stringOrFail()
        );
    }

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(ValidatorAliasSet.class),
            ValidatorAliasSet::unmarshall,
            ValidatorAliasSet::marshall,
            ValidatorAliasSet.class
        );
        ValidatorInfoSet.EMPTY.size(); // trigger static init and json marshall/unmarshall registry
    }
}