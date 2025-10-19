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
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.text.printer.TreePrintable;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.Objects;
import java.util.Optional;

/**
 * A checkbox represents a value that can be checked or not checked, each with a different values.
 */
public final class ValidationCheckbox implements ValidationPromptValue,
    UsesToStringBuilder,
    TreePrintable {

    public final static ValidationCheckbox TRUE_FALSE = new ValidationCheckbox(
        Optional.of(true),
        Optional.of(false)
    );

    public static ValidationCheckbox with(final Optional<Object> trueValue,
                                          final Optional<Object> falseValue) {
        Objects.requireNonNull(trueValue, "trueValue");
        Objects.requireNonNull(falseValue, "falseValue");

        return TRUE_FALSE.trueValue.equals(trueValue) &&
            TRUE_FALSE.falseValue.equals(falseValue) ?
            TRUE_FALSE :
            new ValidationCheckbox(
                trueValue,
                falseValue
            );
    }

    private ValidationCheckbox(final Optional<Object> trueValue,
                               final Optional<Object> falseValue) {
        this.trueValue = trueValue;
        this.falseValue = falseValue;
    }

    public Optional<Object> trueValue() {
        return this.trueValue;
    }

    private final Optional<Object> trueValue;

    public Optional<Object> falseValue() {
        return this.falseValue;
    }

    private final Optional<Object> falseValue;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return Objects.hash(
            this.trueValue,
            this.falseValue
        );
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof ValidationCheckbox &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final ValidationCheckbox other) {
        return this.trueValue.equals(other.trueValue) &&
            this.falseValue.equals(other.falseValue);
    }

    @Override
    public String toString() {
        return ToStringBuilder.buildFrom(this);
    }

    // UsesToStringBuilder..............................................................................................

    @Override
    public void buildToString(final ToStringBuilder toStringBuilder) {
        toStringBuilder.separator(", ")
            .value(this.trueValue)
            .value(this.falseValue);
    }

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        printer.println("true");
        printer.indent();
        {
            TreePrintable.printTreeOrToString(
                this.trueValue.orElse(null),
                printer
            );
        }
        printer.outdent();

        printer.println("false");
        printer.indent();
        {
            TreePrintable.printTreeOrToString(
                this.falseValue.orElse(null),
                printer
            );
        }
        printer.outdent();
    }

    // json.............................................................................................................

    static ValidationCheckbox unmarshall(final JsonNode node,
                                         final JsonNodeUnmarshallContext context) {
        Object trueValue = null;
        Object falseValue = null;

        for (final JsonNode child : node.objectOrFail().children()) {
            final JsonPropertyName name = child.name();
            switch (name.value()) {
                case TRUE_PROPERTY_STRING:
                    trueValue = context.unmarshallWithType(child);
                    break;
                case FALSE_PROPERTY_STRING:
                    falseValue = context.unmarshallWithType(child);
                    break;
                default:
                    JsonNodeUnmarshallContext.unknownPropertyPresent(name, node);
                    break;
            }
        }

        return new ValidationCheckbox(
            Optional.ofNullable(trueValue),
            Optional.ofNullable(falseValue)
        );
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return JsonNode.object()
            .set(
                TRUE_PROPERTY,
                context.marshallWithType(
                    this.trueValue.orElse(null)
                )
            ).set(
                FALSE_PROPERTY,
                context.marshallWithType(
                    this.falseValue.orElse(null)
                )
            );
    }

    private final static String TRUE_PROPERTY_STRING = "true";
    private final static String FALSE_PROPERTY_STRING = "false";

    // @VisibleForTesting

    final static JsonPropertyName TRUE_PROPERTY = JsonPropertyName.with(TRUE_PROPERTY_STRING);
    final static JsonPropertyName FALSE_PROPERTY = JsonPropertyName.with(FALSE_PROPERTY_STRING);

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(ValidationCheckbox.class),
            ValidationCheckbox::unmarshall,
            ValidationCheckbox::marshall,
            ValidationCheckbox.class
        );
    }
}
