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

import walkingkooka.Cast;
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.plugin.PluginAlias;
import walkingkooka.plugin.PluginAliasLike;
import walkingkooka.text.printer.IndentingPrinter;

import java.util.Objects;
import java.util.Optional;

public final class FormHandlerAlias implements PluginAliasLike<FormHandlerName, FormHandlerSelector, FormHandlerAlias> {

    public static FormHandlerAlias parse(final String text) {
        return FormHandlerAlias.with(
            PluginAlias.parse(
                text,
                FormHandlerPluginHelper.INSTANCE
            )
        );
    }

    static FormHandlerAlias with(final FormHandlerName name,
                                 final Optional<FormHandlerSelector> selector,
                                 final Optional<AbsoluteUrl> url) {
        return with(
            PluginAlias.with(
                name,
                selector,
                url
            )
        );
    }

    public static FormHandlerAlias with(final PluginAlias<FormHandlerName, FormHandlerSelector> pluginAlias) {
        return new FormHandlerAlias(
            Objects.requireNonNull(pluginAlias, "pluginAlias")
        );
    }

    private FormHandlerAlias(final PluginAlias<FormHandlerName, FormHandlerSelector> pluginAlias) {
        this.pluginAlias = pluginAlias;
    }

    // PluginAliasLike..................................................................................................

    @Override
    public FormHandlerName name() {
        return this.pluginAlias.name();
    }

    @Override
    public Optional<FormHandlerSelector> selector() {
        return this.pluginAlias.selector();
    }

    @Override
    public Optional<AbsoluteUrl> url() {
        return this.pluginAlias.url();
    }

    @Override
    public String text() {
        return this.pluginAlias.text();
    }

    // Comparable.......................................................................................................

    @Override
    public int compareTo(final FormHandlerAlias other) {
        return this.pluginAlias.compareTo(other.pluginAlias);
    }

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        this.pluginAlias.printTree(printer);
    }

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.pluginAlias.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof FormHandlerAlias && this.equals0(Cast.to(other));
    }

    private boolean equals0(final FormHandlerAlias other) {
        return this.pluginAlias.equals(other.pluginAlias);
    }

    @Override
    public String toString() {
        return this.pluginAlias.text();
    }

    private final PluginAlias<FormHandlerName, FormHandlerSelector> pluginAlias;
}
