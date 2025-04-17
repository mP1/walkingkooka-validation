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

import walkingkooka.plugin.PluginSelector;
import walkingkooka.plugin.PluginSelectorLike;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.form.FormHandler;
import walkingkooka.validation.form.FormHandlerContext;

import java.util.List;
import java.util.Objects;

/**
 * Contains the {@link FormHandlerName} and some text which may contain an expression for a {@link FormHandler}.
 */
public final class FormHandlerSelector implements PluginSelectorLike<FormHandlerName> {

    /**
     * Parses the given text into a {@link FormHandlerSelector}. Note the text following the {@link FormHandlerName} is not validated in any form and simply stored.
     */
    public static FormHandlerSelector parse(final String text) {
        return new FormHandlerSelector(
            PluginSelector.parse(
                text,
                FormHandlerName::with
            )
        );
    }

    /**
     * Factory that creates a new {@link FormHandlerSelector}.
     */
    public static FormHandlerSelector with(final FormHandlerName name,
                                           final String text) {
        return new FormHandlerSelector(
            PluginSelector.with(
                name,
                text
            )
        );
    }

    private FormHandlerSelector(final PluginSelector<FormHandlerName> selector) {
        this.selector = selector;
    }

    // HasName..........................................................................................................

    @Override
    public FormHandlerName name() {
        return this.selector.name();
    }

    /**
     * Would be setter that returns a {@link FormHandlerSelector} with the given {@link FormHandlerName},
     * creating a new instance if necessary.
     */
    @Override
    public FormHandlerSelector setName(final FormHandlerName name) {
        Objects.requireNonNull(name, "name");

        return this.name().equals(name) ?
            this :
            new FormHandlerSelector(
                PluginSelector.with(
                    name,
                    this.valueText()
                )
            );
    }

    // HasText..........................................................................................................

    /**
     * If the {@link FormHandlerName} identifies a {@link FormHandler}, this will
     * hold the pattern text itself.
     */
    @Override
    public String valueText() {
        return this.selector.valueText();
    }

    @Override
    public FormHandlerSelector setValueText(final String text) {
        final PluginSelector<FormHandlerName> different = this.selector.setValueText(text);
        return this.selector.equals(different) ?
            this :
            new FormHandlerSelector(different);
    }

    private final PluginSelector<FormHandlerName> selector;

    // setValues........................................................................................................

    @Override
    public FormHandlerSelector setValues(final List<?> values) {
        final PluginSelector<FormHandlerName> different = this.selector.setValues(values);
        return this.selector.equals(different) ?
            this :
            new FormHandlerSelector(different);
    }

    // evaluateText.....................................................................................................

    /**
     * Parses the {@link #valueText()}  as an expression that contains an optional parameter list which may include
     * <ul>
     * <li>{@link FormHandlerName}</li>
     * <li>double literals including negative or leading minus signs.</li>
     * <li>a double quoted string literal</li>
     * </ul>
     * Sample text.
     * <pre>
     * number-to-number
     * collection ( number-to-boolen, number-number, string-to-local-date "yyyy-mm-dd")
     * </pre>
     * The {@link FormHandlerProvider} will be used to fetch {@link FormHandler} with any parameters.
     */
    public <R extends ValidationReference, S, C extends FormHandlerContext<R, S>> FormHandler<R, S, C> evaluateValueText(final FormHandlerProvider provider,
                                                                                                                         final ProviderContext context) {
        Objects.requireNonNull(provider, "provider");
        Objects.requireNonNull(provider, "context");

        return this.selector.evaluateValueText(
            FormHandlerPluginHelper.INSTANCE::parseName,
            provider::formHandler,
            context
        );
    }

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.selector.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof FormHandlerSelector && this.equals0((FormHandlerSelector) other);
    }

    private boolean equals0(final FormHandlerSelector other) {
        return this.selector.equals(other.selector);
    }

    /**
     * Note it is intentional that the {@link #text()} is not quoted, to ensure {@link #parse(String)} and {@link #toString()}
     * are round-trippable.
     */
    @Override
    public String toString() {
        return this.selector.toString();
    }

    // JsonNodeContext..................................................................................................

    /**
     * Factory that creates a {@link FormHandlerSelector} from a {@link JsonNode}.
     */
    static FormHandlerSelector unmarshall(final JsonNode node,
                                          final JsonNodeUnmarshallContext context) {
        return parse(node.stringOrFail());
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return this.selector.marshall(context);
    }

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(FormHandlerSelector.class),
            FormHandlerSelector::unmarshall,
            FormHandlerSelector::marshall,
            FormHandlerSelector.class
        );
    }

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        this.selector.printTree(printer);
    }
}
