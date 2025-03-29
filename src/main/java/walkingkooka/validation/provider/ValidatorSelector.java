/*
 * Copyright 2024 Miroslav Pokorny (github.com/mP1)
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

import walkingkooka.plugin.PluginSelector;
import walkingkooka.plugin.PluginSelectorLike;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.Validator;
import walkingkooka.validation.ValidatorContext;

import java.util.List;
import java.util.Objects;

/**
 * Contains the {@link ValidatorName} and some text which may contain an expression for a {@link Validator}.
 */
public final class ValidatorSelector implements PluginSelectorLike<ValidatorName> {

    /**
     * Parses the given text into a {@link ValidatorSelector}. Note the text following the {@link ValidatorName} is not validated in any form and simply stored.
     */
    public static ValidatorSelector parse(final String text) {
        return new ValidatorSelector(
            PluginSelector.parse(
                text,
                ValidatorName::with
            )
        );
    }

    /**
     * Factory that creates a new {@link ValidatorSelector}.
     */
    public static ValidatorSelector with(final ValidatorName name,
                                         final String text) {
        return new ValidatorSelector(
            PluginSelector.with(
                name,
                text
            )
        );
    }

    private ValidatorSelector(final PluginSelector<ValidatorName> selector) {
        this.selector = selector;
    }

    // HasName..........................................................................................................

    @Override
    public ValidatorName name() {
        return this.selector.name();
    }

    /**
     * Would be setter that returns a {@link ValidatorSelector} with the given {@link ValidatorName},
     * creating a new instance if necessary.
     */
    @Override
    public ValidatorSelector setName(final ValidatorName name) {
        Objects.requireNonNull(name, "name");

        return this.name().equals(name) ?
            this :
            new ValidatorSelector(
                PluginSelector.with(
                    name,
                    this.valueText()
                )
            );
    }

    // HasText..........................................................................................................

    /**
     * If the {@link ValidatorName} identifies a {@link Validator}, this will
     * hold the pattern text itself.
     */
    @Override
    public String valueText() {
        return this.selector.valueText();
    }

    @Override
    public ValidatorSelector setValueText(final String text) {
        final PluginSelector<ValidatorName> different = this.selector.setValueText(text);
        return this.selector.equals(different) ?
            this :
            new ValidatorSelector(different);
    }

    private final PluginSelector<ValidatorName> selector;

    // setValues........................................................................................................

    @Override
    public ValidatorSelector setValues(final List<?> values) {
        final PluginSelector<ValidatorName> different = this.selector.setValues(values);
        return this.selector.equals(different) ?
            this :
            new ValidatorSelector(different);
    }

    // evaluateText.....................................................................................................

    /**
     * Parses the {@link #valueText()}  as an expression that contains an optional parameter list which may include
     * <ul>
     * <li>{@link ValidatorName}</li>
     * <li>double literals including negative or leading minus signs.</li>
     * <li>a double quoted string literal</li>
     * </ul>
     * Sample text.
     * <pre>
     * number-to-number
     * collection ( number-to-boolen, number-number, string-to-local-date "yyyy-mm-dd")
     * </pre>
     * The {@link ValidatorProvider} will be used to fetch {@link Validator} with any parameters.
     */
    public <R extends ValidationReference, C extends ValidatorContext<R>> Validator<R, C> evaluateValueText(final ValidatorProvider provider,
                                                                                                            final ProviderContext context) {
        Objects.requireNonNull(provider, "provider");
        Objects.requireNonNull(provider, "context");

        return this.selector.evaluateValueText(
            ValidatorPluginHelper.INSTANCE::parseName,
            provider::validator,
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
            other instanceof ValidatorSelector && this.equals0((ValidatorSelector) other);
    }

    private boolean equals0(final ValidatorSelector other) {
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
     * Factory that creates a {@link ValidatorSelector} from a {@link JsonNode}.
     */
    static ValidatorSelector unmarshall(final JsonNode node,
                                        final JsonNodeUnmarshallContext context) {
        return parse(node.stringOrFail());
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return this.selector.marshall(context);
    }

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(ValidatorSelector.class),
            ValidatorSelector::unmarshall,
            ValidatorSelector::marshall,
            ValidatorSelector.class
        );
    }

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        this.selector.printTree(printer);
    }
}
