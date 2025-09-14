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

package walkingkooka.validation.form.provider;

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
import java.util.Objects;
import java.util.Optional;
import java.util.SortedSet;

public final class FormHandlerAliasSet extends AbstractSet<FormHandlerAlias>
    implements PluginAliasSetLike<FormHandlerName,
    FormHandlerInfo,
    FormHandlerInfoSet,
    FormHandlerSelector,
    FormHandlerAlias,
    FormHandlerAliasSet>,
    ImmutableSortedSetDefaults<FormHandlerAliasSet, FormHandlerAlias> {

    /**
     * An empty {@link FormHandlerAliasSet}.
     */
    public final static FormHandlerAliasSet EMPTY = new FormHandlerAliasSet(
        PluginAliasSet.with(
            SortedSets.empty(),
            FormHandlerPluginHelper.INSTANCE
        )
    );

    /**
     * {@see PluginAliasSet#SEPARATOR}
     */
    public final static CharacterConstant SEPARATOR = PluginAliasSet.SEPARATOR;

    /**
     * Factory that creates {@link FormHandlerAliasSet} with the given aliases.
     */
    public static FormHandlerAliasSet with(final SortedSet<FormHandlerAlias> aliases) {
        return EMPTY.setElements(aliases);
    }

    public static FormHandlerAliasSet parse(final String text) {
        return new FormHandlerAliasSet(
            PluginAliasSet.parse(
                text,
                FormHandlerPluginHelper.INSTANCE
            )
        );
    }

    private FormHandlerAliasSet(final PluginAliasSet<FormHandlerName, FormHandlerInfo, FormHandlerInfoSet, FormHandlerSelector, FormHandlerAlias, FormHandlerAliasSet> pluginAliasSet) {
        this.pluginAliasSet = pluginAliasSet;
    }

    @Override
    public FormHandlerSelector selector(final FormHandlerSelector selector) {
        return this.pluginAliasSet.selector(selector);
    }

    @Override
    public Optional<FormHandlerSelector> aliasSelector(final FormHandlerName name) {
        return this.pluginAliasSet.aliasSelector(name);
    }

    @Override
    public Optional<FormHandlerName> aliasOrName(final FormHandlerName name) {
        return this.pluginAliasSet.aliasOrName(name);
    }

    @Override
    public FormHandlerInfoSet merge(final FormHandlerInfoSet infos) {
        return this.pluginAliasSet.merge(infos);
    }

    @Override
    public boolean containsAliasOrName(final FormHandlerName aliasOrName) {
        return this.pluginAliasSet.containsAliasOrName(aliasOrName);
    }

    @Override
    public FormHandlerAliasSet concatOrReplace(final FormHandlerAlias alias) {
        return new FormHandlerAliasSet(
            this.pluginAliasSet.concatOrReplace(alias)
        );
    }

    @Override
    public FormHandlerAliasSet deleteAliasOrNameAll(final Collection<FormHandlerName> aliasOrNames) {
        return this.setElements(
            this.pluginAliasSet.deleteAliasOrNameAll(aliasOrNames)
        );
    }

    @Override
    public FormHandlerAliasSet keepAliasOrNameAll(final Collection<FormHandlerName> aliasOrNames) {
        return this.setElements(
            this.pluginAliasSet.keepAliasOrNameAll(aliasOrNames)
        );
    }

    // ImmutableSortedSet...............................................................................................

    @Override
    public Comparator<? super FormHandlerAlias> comparator() {
        return this.pluginAliasSet.comparator();
    }

    @Override
    public Iterator<FormHandlerAlias> iterator() {
        return this.pluginAliasSet.stream().iterator();
    }

    @Override
    public int size() {
        return this.pluginAliasSet.size();
    }

    @Override
    public FormHandlerAliasSet setElements(final Collection<FormHandlerAlias> aliases) {
        FormHandlerAliasSet after;

        if (aliases instanceof FormHandlerAliasSet) {
            after = (FormHandlerAliasSet) aliases;
        } else {
            after = new FormHandlerAliasSet(
                this.pluginAliasSet.setElements(aliases)
            );
            after = this.pluginAliasSet.equals(aliases) ?
                this :
                after;

        }

        return after;
    }

    @Override
    public FormHandlerAliasSet setElementsFailIfDifferent(final Collection<FormHandlerAlias> aliases) {
        return this.setElements(
            this.pluginAliasSet.setElementsFailIfDifferent(
                aliases
            )
        );
    }

    @Override
    public SortedSet<FormHandlerAlias> toSet() {
        return this.pluginAliasSet.toSet();
    }

    @Override
    public FormHandlerAliasSet subSet(final FormHandlerAlias from,
                                      final FormHandlerAlias to) {
        return this.setElements(
            this.pluginAliasSet.subSet(
                from,
                to
            )
        );
    }

    @Override
    public FormHandlerAliasSet headSet(final FormHandlerAlias alias) {
        return this.setElements(
            this.pluginAliasSet.headSet(alias)
        );
    }

    @Override
    public FormHandlerAliasSet tailSet(final FormHandlerAlias alias) {
        return this.setElements(
            this.pluginAliasSet.tailSet(alias)
        );
    }

    @Override
    public FormHandlerAliasSet concat(final FormHandlerAlias alias) {
        return this.setElements(
            this.pluginAliasSet.concat(alias)
        );
    }

    @Override
    public FormHandlerAliasSet concatAll(final Collection<FormHandlerAlias> aliases) {
        return this.setElements(
            this.pluginAliasSet.concatAll(aliases)
        );
    }

    @Override
    public FormHandlerAliasSet delete(final FormHandlerAlias alias) {
        return this.setElements(
            this.pluginAliasSet.delete(alias)
        );
    }

    @Override
    public FormHandlerAliasSet deleteAll(final Collection<FormHandlerAlias> aliases) {
        return this.setElements(
            this.pluginAliasSet.deleteAll(aliases)
        );
    }

    @Override
    public FormHandlerAliasSet replace(final FormHandlerAlias oldAlias,
                                       final FormHandlerAlias newAlias) {
        return this.setElements(
            this.pluginAliasSet.replace(
                oldAlias,
                newAlias
            )
        );
    }

    @Override
    public FormHandlerAlias first() {
        return this.pluginAliasSet.first();
    }

    @Override
    public FormHandlerAlias last() {
        return this.pluginAliasSet.last();
    }

    @Override
    public void elementCheck(final FormHandlerAlias alias) {
        Objects.requireNonNull(alias, "alias");
    }

    @Override
    public String text() {
        return this.pluginAliasSet.text();
    }

    @Override
    public void printTree(final IndentingPrinter printer) {
        this.pluginAliasSet.printTree(printer);
    }

    private final PluginAliasSet<FormHandlerName, FormHandlerInfo, FormHandlerInfoSet, FormHandlerSelector, FormHandlerAlias, FormHandlerAliasSet> pluginAliasSet;

    // Json.............................................................................................................

    static void register() {
        // helps force registry of json marshaller
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return JsonNode.string(
            this.pluginAliasSet.text()
        );
    }

    static FormHandlerAliasSet unmarshall(final JsonNode node,
                                          final JsonNodeUnmarshallContext context) {
        return parse(
            node.stringOrFail()
        );
    }

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(FormHandlerAliasSet.class),
            FormHandlerAliasSet::unmarshall,
            FormHandlerAliasSet::marshall,
            FormHandlerAliasSet.class
        );
        FormHandlerInfoSet.EMPTY.size(); // trigger static init and json marshall/unmarshall registry
    }
}