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

package walkingkooka.validation;

import walkingkooka.Cast;
import walkingkooka.ToStringBuilder;
import walkingkooka.UsesToStringBuilder;
import walkingkooka.Value;
import walkingkooka.text.CharSequences;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.text.printer.TreePrintable;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonObject;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.Objects;
import java.util.Optional;

/**
 * A single choice within a list of choices.
 */
public final class ValidationChoice implements Value<Optional<Object>>,
    UsesToStringBuilder,
    TreePrintable{

    public static ValidationChoice with(final String label,
                                        final Optional<Object> value) {
        return new ValidationChoice(
            Objects.requireNonNull(label, "label"),
            Objects.requireNonNull(value, "value")
        );
    }

    private ValidationChoice(final String label,
                             final Optional<Object> value) {
        this.label = label;
        this.value = value;
    }

    public String label() {
        return this.label;
    }

    private final String label;

    // Value............................................................................................................

    @Override
    public Optional<Object> value() {
        return this.value;
    }

    private final Optional<Object> value;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return Objects.hash(
            this.label,
            this.value
        );
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof ValidationChoice &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final ValidationChoice error) {
        return this.label.equals(error.label) &&
            this.value.equals(error.value);
    }

    @Override
    public String toString() {
        return ToStringBuilder.buildFrom(this);
    }

    // UsesToStringBuilder..............................................................................................

    @Override
    public void buildToString(final ToStringBuilder toStringBuilder) {
        toStringBuilder.label(this.label)
            .value(this.value);
    }

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        printer.println(this.label);
        printer.indent();
        {
            final Optional<Object> value = this.value();
            if(value.isPresent()) {
                printer.println(
                    CharSequences.quoteIfChars(value.orElse(null))
                );
            }
        }
        printer.outdent();
    }

    // json.............................................................................................................

    static ValidationChoice unmarshall(final JsonNode node,
                                       final JsonNodeUnmarshallContext context) {
        String label = null;
        Object value = null;

        for (final JsonNode child : node.objectOrFail().children()) {
            final JsonPropertyName name = child.name();
            switch (name.value()) {
                case LABEL_PROPERTY_STRING:
                    label = child.stringOrFail();
                    break;
                case VALUE_PROPERTY_STRING:
                    value = context.unmarshallWithType(child);
                    break;
                default:
                    JsonNodeUnmarshallContext.unknownPropertyPresent(name, node);
                    break;
            }
        }

        if (null == label) {
            JsonNodeUnmarshallContext.missingProperty(LABEL_PROPERTY, node);
        }
        return new ValidationChoice(
            label,
            Optional.ofNullable(value)
        );
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        JsonObject json = JsonNode.object()
            .set(LABEL_PROPERTY, JsonNode.string(this.label));

        final Object value = this.value()
            .orElse(null);
        if (null != value) {
            json = json.set(
                VALUE_PROPERTY,
                context.marshallWithType(value)
            );
        }

        return json;
    }

    private final static String LABEL_PROPERTY_STRING = "label";
    private final static String VALUE_PROPERTY_STRING = "value";

    // @VisibleForTesting

    final static JsonPropertyName LABEL_PROPERTY = JsonPropertyName.with(LABEL_PROPERTY_STRING);
    final static JsonPropertyName VALUE_PROPERTY = JsonPropertyName.with(VALUE_PROPERTY_STRING);

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(ValidationChoice.class),
            ValidationChoice::unmarshall,
            ValidationChoice::marshall,
            ValidationChoice.class
        );
    }
}
