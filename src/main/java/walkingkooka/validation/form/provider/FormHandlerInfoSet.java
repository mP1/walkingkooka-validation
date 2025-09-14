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
 * A read only {@link Set} of {@link FormHandlerInfo} sorted by {@link FormHandlerName}.
 */
public final class FormHandlerInfoSet extends AbstractSet<FormHandlerInfo> implements PluginInfoSetLike<FormHandlerName, FormHandlerInfo, FormHandlerInfoSet, FormHandlerSelector, FormHandlerAlias, FormHandlerAliasSet> {

    public final static FormHandlerInfoSet EMPTY = new FormHandlerInfoSet(
        PluginInfoSet.with(
            Sets.<FormHandlerInfo>empty()
        )
    );

    public static FormHandlerInfoSet parse(final String text) {
        return new FormHandlerInfoSet(
            PluginInfoSet.parse(
                text,
                FormHandlerInfo::parse
            )
        );
    }

    public static FormHandlerInfoSet with(final Set<FormHandlerInfo> infos) {
        FormHandlerInfoSet with;

        if (infos instanceof FormHandlerInfoSet) {
            with = (FormHandlerInfoSet) infos;
        } else {
            final PluginInfoSet<FormHandlerName, FormHandlerInfo> pluginInfoSet = PluginInfoSet.with(
                Objects.requireNonNull(infos, "infos")
            );
            with = pluginInfoSet.isEmpty() ?
                EMPTY :
                new FormHandlerInfoSet(pluginInfoSet);
        }

        return with;
    }

    private FormHandlerInfoSet(final PluginInfoSet<FormHandlerName, FormHandlerInfo> pluginInfoSet) {
        this.pluginInfoSet = pluginInfoSet;
    }

    // PluginInfoSetLike................................................................................................

    @Override
    public Set<FormHandlerName> names() {
        return this.pluginInfoSet.names();
    }

    @Override
    public Set<AbsoluteUrl> url() {
        return this.pluginInfoSet.url();
    }

    @Override
    public FormHandlerAliasSet aliasSet() {
        return FormHandlerPluginHelper.INSTANCE.toAliasSet(this);
    }

    @Override
    public FormHandlerInfoSet filter(final FormHandlerInfoSet infos) {
        return this.setElements(
            this.pluginInfoSet.filter(
                infos.pluginInfoSet
            )
        );
    }

    @Override
    public FormHandlerInfoSet renameIfPresent(FormHandlerInfoSet renameInfos) {
        return this.setElements(
            this.pluginInfoSet.renameIfPresent(
                renameInfos.pluginInfoSet
            )
        );
    }

    @Override
    public FormHandlerInfoSet concat(final FormHandlerInfo info) {
        return this.setElements(
            this.pluginInfoSet.concat(info)
        );
    }

    @Override
    public FormHandlerInfoSet concatAll(final Collection<FormHandlerInfo> infos) {
        return this.setElements(
            this.pluginInfoSet.concatAll(infos)
        );
    }

    @Override
    public FormHandlerInfoSet delete(final FormHandlerInfo info) {
        return this.setElements(
            this.pluginInfoSet.delete(info)
        );
    }

    @Override
    public FormHandlerInfoSet deleteAll(final Collection<FormHandlerInfo> infos) {
        return this.setElements(
            this.pluginInfoSet.deleteAll(infos)
        );
    }

    @Override
    public FormHandlerInfoSet deleteIf(final Predicate<? super FormHandlerInfo> predicate) {
        return this.setElements(
            this.pluginInfoSet.deleteIf(predicate)
        );
    }

    @Override
    public FormHandlerInfoSet replace(final FormHandlerInfo oldInfo,
                                      final FormHandlerInfo newInfo) {
        return this.setElements(
            this.pluginInfoSet.replace(
                oldInfo,
                newInfo
            )
        );
    }

    @Override
    public FormHandlerInfoSet setElementsFailIfDifferent(final Collection<FormHandlerInfo> infos) {
        return this.setElements(
            this.pluginInfoSet.setElementsFailIfDifferent(
                infos
            )
        );
    }

    @Override
    public FormHandlerInfoSet setElements(final Collection<FormHandlerInfo> infos) {
        final FormHandlerInfoSet after;

        if (infos instanceof FormHandlerInfoSet) {
            after = (FormHandlerInfoSet) infos;
        } else {
            after = new FormHandlerInfoSet(
                this.pluginInfoSet.setElements(infos)
            );
            return this.pluginInfoSet.equals(infos) ?
                this :
                after;

        }

        return after;
    }

    @Override
    public Set<FormHandlerInfo> toSet() {
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
    public Iterator<FormHandlerInfo> iterator() {
        return this.pluginInfoSet.iterator();
    }

    @Override
    public int size() {
        return this.pluginInfoSet.size();
    }

    private final PluginInfoSet<FormHandlerName, FormHandlerInfo> pluginInfoSet;

    // json.............................................................................................................

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return context.marshallCollection(this);
    }

    // @VisibleForTesting
    static FormHandlerInfoSet unmarshall(final JsonNode node,
                                         final JsonNodeUnmarshallContext context) {
        return with(
            context.unmarshallSet(
                node,
                FormHandlerInfo.class
            )
        );
    }

    static {
        FormHandlerInfo.register(); // force json registry

        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(FormHandlerInfoSet.class),
            FormHandlerInfoSet::unmarshall,
            FormHandlerInfoSet::marshall,
            FormHandlerInfoSet.class
        );
    }
}