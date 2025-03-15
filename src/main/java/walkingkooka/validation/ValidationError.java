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
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY REFERENCE, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.validation;

import walkingkooka.Cast;
import walkingkooka.ToStringBuilder;
import walkingkooka.UsesToStringBuilder;
import walkingkooka.Value;
import walkingkooka.text.Whitespace;
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
 * Reports a single error for the identified field or component.
 */
public final class ValidationError<T extends ValidationReference> implements Value<Optional<Object>>,
    UsesToStringBuilder,
    TreePrintable {

    public static <T extends ValidationReference> ValidationError<T> with(final T reference,
                                                                          final String message,
                                                                          final Optional<Object> value) {
        return new ValidationError<>(
            Objects.requireNonNull(reference, "reference"),
            Whitespace.failIfNullOrEmptyOrWhitespace(message, "message"),
            Objects.requireNonNull(value, "value")
        );
    }

    private ValidationError(final T reference,
                            final String message,
                            final Optional<Object> value) {
        this.reference = reference;
        this.message = message;
        this.value = value;
    }

    public T reference() {
        return this.reference;
    }

    private final T reference;

    public String message() {
        return this.message;
    }

    private final String message;

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
            this.reference,
            this.message,
            this.value
        );
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof ValidationError &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final ValidationError<?> error) {
        return this.reference.equals(error.reference) &&
            this.message.equals(error.message) &&
            this.value.equals(error.value);
    }

    @Override
    public String toString() {
        return ToStringBuilder.buildFrom(this);
    }

    // UsesToStringBuilder..............................................................................................

    @Override
    public void buildToString(final ToStringBuilder builder) {
        builder.value(this.reference)
            .value(this.message)
            .value(this.value);
    }

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        printer.println(this.getClass().getSimpleName());

        printer.indent();
        {
            TreePrintable.printTreeOrToString(
                this.reference,
                printer
            );

            printer.indent();
            {
                printer.println(this.message);

                final Object valueOrNull = this.value.orElse(null);
                if (null != valueOrNull) {

                    printer.indent();
                    {
                        TreePrintable.printTreeOrToString(
                            valueOrNull,
                            printer
                        );
                    }
                    printer.outdent();
                }
            }
            printer.outdent();
        }
        printer.outdent();
    }

    // json.............................................................................................................

    static <T extends ValidationReference> ValidationError<T> unmarshall(final JsonNode node,
                                                                         final JsonNodeUnmarshallContext context) {
        T kind = null;
        String message = null;
        Object value = null;

        for (final JsonNode child : node.objectOrFail().children()) {
            final JsonPropertyName name = child.name();
            switch (name.value()) {
                case REFERENCE_PROPERTY_STRING:
                    kind = context.unmarshallWithType(child);
                    break;
                case MESSAGE_PROPERTY_STRING:
                    message = child.stringOrFail();
                    break;
                case VALUE_PROPERTY_STRING:
                    value = context.unmarshallWithType(child);
                    break;
                default:
                    JsonNodeUnmarshallContext.unknownPropertyPresent(name, node);
                    break;
            }
        }

        if (null == kind) {
            JsonNodeUnmarshallContext.missingProperty(REFERENCE_PROPERTY, node);
        }
        if (null == message) {
            JsonNodeUnmarshallContext.missingProperty(MESSAGE_PROPERTY, node);
        }

        return new ValidationError(
            kind,
            message,
            Optional.ofNullable(value)
        );
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        JsonObject json = JsonNode.object()
            .set(REFERENCE_PROPERTY, context.marshallWithType(this.reference))
            .set(MESSAGE_PROPERTY, JsonNode.string(this.message));

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

    private final static String REFERENCE_PROPERTY_STRING = "reference";
    private final static String MESSAGE_PROPERTY_STRING = "message";
    private final static String VALUE_PROPERTY_STRING = "value";

    // @VisibleForTesting

    final static JsonPropertyName REFERENCE_PROPERTY = JsonPropertyName.with(REFERENCE_PROPERTY_STRING);
    final static JsonPropertyName MESSAGE_PROPERTY = JsonPropertyName.with(MESSAGE_PROPERTY_STRING);
    final static JsonPropertyName VALUE_PROPERTY = JsonPropertyName.with(VALUE_PROPERTY_STRING);

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(ValidationError.class),
            ValidationError::unmarshall,
            ValidationError::marshall,
            ValidationError.class
        );
    }
}
