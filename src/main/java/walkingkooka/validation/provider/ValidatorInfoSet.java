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

import walkingkooka.collect.set.ImmutableSet;
import walkingkooka.collect.set.Sets;
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.plugin.PluginInfoSet;
import walkingkooka.plugin.PluginInfoSetLike;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

/**
 * A read only {@link Set} of {@link ValidatorInfo} sorted by {@link ValidatorName}.
 */
public final class ValidatorInfoSet extends AbstractSet<ValidatorInfo> implements PluginInfoSetLike<ValidatorName, ValidatorInfo, ValidatorInfoSet, ValidatorSelector, ValidatorAlias, ValidatorAliasSet> {

    public final static ValidatorInfoSet EMPTY = new ValidatorInfoSet(
        PluginInfoSet.with(
            Sets.<ValidatorInfo>empty()
        )
    );

    public static ValidatorInfoSet parse(final String text) {
        return new ValidatorInfoSet(
            PluginInfoSet.parse(
                text,
                ValidatorInfo::parse
            )
        );
    }

    public static ValidatorInfoSet with(final Set<ValidatorInfo> infos) {
        ValidatorInfoSet with;

        if (infos instanceof ValidatorInfoSet) {
            with = (ValidatorInfoSet) infos;
        } else {
            final PluginInfoSet<ValidatorName, ValidatorInfo> pluginInfoSet = PluginInfoSet.with(
                Objects.requireNonNull(infos, "infos")
            );
            with = pluginInfoSet.isEmpty() ?
                EMPTY :
                new ValidatorInfoSet(pluginInfoSet);
        }

        return with;
    }

    private ValidatorInfoSet(final PluginInfoSet<ValidatorName, ValidatorInfo> pluginInfoSet) {
        this.pluginInfoSet = pluginInfoSet;
    }

    // PluginInfoSetLike................................................................................................

    @Override
    public Set<ValidatorName> names() {
        return this.pluginInfoSet.names();
    }

    @Override
    public Set<AbsoluteUrl> url() {
        return this.pluginInfoSet.url();
    }

    @Override
    public ValidatorAliasSet aliasSet() {
        return ValidatorPluginHelper.INSTANCE.toAliasSet(this);
    }

    @Override
    public ValidatorInfoSet filter(final ValidatorInfoSet infos) {
        return this.setElements(
            this.pluginInfoSet.filter(
                infos.pluginInfoSet
            )
        );
    }

    @Override
    public ValidatorInfoSet renameIfPresent(ValidatorInfoSet renameInfos) {
        return this.setElements(
            this.pluginInfoSet.renameIfPresent(
                renameInfos.pluginInfoSet
            )
        );
    }

    @Override
    public ValidatorInfoSet concat(final ValidatorInfo info) {
        return this.setElements(
            this.pluginInfoSet.concat(info)
        );
    }

    @Override
    public ValidatorInfoSet concatAll(final Collection<ValidatorInfo> infos) {
        return this.setElements(
            this.pluginInfoSet.concatAll(infos)
        );
    }

    @Override
    public ValidatorInfoSet delete(final ValidatorInfo info) {
        return this.setElements(
            this.pluginInfoSet.delete(info)
        );
    }

    @Override
    public ValidatorInfoSet deleteAll(final Collection<ValidatorInfo> infos) {
        return this.setElements(
            this.pluginInfoSet.deleteAll(infos)
        );
    }

    @Override
    public ValidatorInfoSet deleteIf(final Predicate<? super ValidatorInfo> predicate) {
        return this.setElements(
            this.pluginInfoSet.deleteIf(predicate)
        );
    }

    @Override
    public ValidatorInfoSet replace(final ValidatorInfo oldInfo,
                                    final ValidatorInfo newInfo) {
        return this.setElements(
            this.pluginInfoSet.replace(
                oldInfo,
                newInfo
            )
        );
    }

    @Override
    public ImmutableSet<ValidatorInfo> setElementsFailIfDifferent(final Set<ValidatorInfo> infos) {
        return this.setElements(
            this.pluginInfoSet.setElementsFailIfDifferent(
                infos
            )
        );
    }

    @Override
    public ValidatorInfoSet setElements(final Set<ValidatorInfo> infos) {
        final ValidatorInfoSet after;

        if (infos instanceof ValidatorInfoSet) {
            after = (ValidatorInfoSet) infos;
        } else {
            after = new ValidatorInfoSet(
                this.pluginInfoSet.setElements(infos)
            );
            return this.pluginInfoSet.equals(infos) ?
                this :
                after;

        }

        return after;
    }

    @Override
    public Set<ValidatorInfo> toSet() {
        return this.pluginInfoSet.toSet();
    }

    // TreePrintable....................................................................................................

    @Override
    public String text() {
        return this.pluginInfoSet.text();
    }

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        printer.println(this.getClass().getSimpleName());
        printer.indent();
        {
            this.pluginInfoSet.printTree(printer);
        }
        printer.outdent();
    }

    // AbstractSet......................................................................................................

    @Override
    public Iterator<ValidatorInfo> iterator() {
        return this.pluginInfoSet.iterator();
    }

    @Override
    public int size() {
        return this.pluginInfoSet.size();
    }

    private final PluginInfoSet<ValidatorName, ValidatorInfo> pluginInfoSet;

    // json.............................................................................................................

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return context.marshallCollection(this);
    }

    // @VisibleForTesting
    static ValidatorInfoSet unmarshall(final JsonNode node,
                                       final JsonNodeUnmarshallContext context) {
        return with(
            context.unmarshallSet(
                node,
                ValidatorInfo.class
            )
        );
    }

    static {
        ValidatorInfo.register(); // force json registry

        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(ValidatorInfoSet.class),
            ValidatorInfoSet::unmarshall,
            ValidatorInfoSet::marshall,
            ValidatorInfoSet.class
        );
    }
}