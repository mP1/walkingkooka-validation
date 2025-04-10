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

package walkingkooka.validation.form;

import walkingkooka.Cast;
import walkingkooka.ToStringBuilder;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonObject;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.provider.ValidatorSelector;

import java.util.Objects;
import java.util.Optional;

/**
 * A FormField has a few properties that are a mixture of forms and validation along with visual clues such as a human
 * friendly label.
 */
public final class FormField<R extends ValidationReference> {

    public static <R extends ValidationReference> FormField<R> with(final R reference) {
        Objects.requireNonNull(reference, "reference");

        return new FormField<>(
            reference,
            NO_LABEL,
            NO_VALUE,
            NO_VALIDATOR
        );
    }

    // @VisibleForTesting
    FormField(final R reference,
              final String label,
              final Optional<Object> value,
              final Optional<ValidatorSelector> validator) {
        this.reference = reference;
        this.label = label;
        this.value = value;
        this.validator = validator;
    }

    // reference........................................................................................................

    public R reference() {
        return this.reference;
    }

    public FormField<R> setReference(final R reference) {
        return this.reference.equals(reference) ?
            this :
            new FormField<>(
                Objects.requireNonNull(reference, "reference"),
                this.label,
                this.value,
                this.validator
            );
    }

    private final R reference;

    // label............................................................................................................

    public final static String NO_LABEL = "";

    public String label() {
        return this.label;
    }

    public FormField<R> setLabel(final String label) {
        return this.label.equals(label) ?
            this :
            new FormField<>(
                this.reference,
                Objects.requireNonNull(label, "label"),
                this.value,
                this.validator
            );
    }

    /**
     * The label text that will appear when the form is rendered and displayed to the user.
     */
    private final String label;

    // value............................................................................................................

    public final static Optional<Object> NO_VALUE = Optional.empty();

    public Optional<Object> value() {
        return this.value;
    }

    public FormField<R> setValue(final Optional<Object> value) {
        return this.value.equals(value) ?
            this :
            new FormField<>(
                this.reference,
                this.label,
                Objects.requireNonNull(value, "value"),
                this.validator
            );
    }

    /**
     * A default or initial value that will appear in the form field when it first appears.
     */
    private final Optional<Object> value;

    // validator........................................................................................................

    public final static Optional<ValidatorSelector> NO_VALIDATOR = Optional.empty();

    public Optional<ValidatorSelector> validator() {
        return this.validator;
    }

    public FormField<R> setValidator(final Optional<ValidatorSelector> validator) {
        return this.validator.equals(validator) ?
            this :
            new FormField<>(
                this.reference,
                this.label,
                this.value,
                Objects.requireNonNull(validator, "validator")
            );
    }

    private final Optional<ValidatorSelector> validator;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return Objects.hash(
            this.reference,
            this.label,
            this.value,
            this.validator
        );
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof FormField &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final FormField<?> other) {
        return this.reference.equals(other.reference) &&
            this.label.equals(other.label) &&
            this.value.equals(other.value) &&
            this.validator.equals(other.validator);
    }

    @Override
    public String toString() {
        return ToStringBuilder.empty()
            .value(this.reference)
            .value(this.label)
            .value(this.value)
            .value(this.validator)
            .build();
    }

    // Json.............................................................................................................

    private final static String REFERENCE_PROPERTY_STRING = "reference";

    private final static String LABEL_PROPERTY_STRING = "label";

    private final static String VALUE_PROPERTY_STRING = "value";

    private final static String VALIDATOR_PROPERTY_STRING = "validator";

    final static JsonPropertyName REFERENCE_PROPERTY = JsonPropertyName.with(REFERENCE_PROPERTY_STRING);

    final static JsonPropertyName LABEL_PROPERTY = JsonPropertyName.with(LABEL_PROPERTY_STRING);

    final static JsonPropertyName VALUE_PROPERTY = JsonPropertyName.with(VALUE_PROPERTY_STRING);

    final static JsonPropertyName VALIDATOR_PROPERTY = JsonPropertyName.with(VALIDATOR_PROPERTY_STRING);

    static <R extends ValidationReference> FormField<R> unmarshall(final JsonNode node,
                                                                   final JsonNodeUnmarshallContext context) {
        R reference = null;
        String label = NO_LABEL;
        Object value = null;
        ValidatorSelector validator = null;

        for (JsonNode child : node.objectOrFail().children()) {
            final JsonPropertyName name = child.name();
            switch (name.value()) {
                case REFERENCE_PROPERTY_STRING:
                    reference = context.unmarshallWithType(child);
                    break;
                case LABEL_PROPERTY_STRING:
                    label = context.unmarshall(
                        child,
                        String.class
                    );
                    break;
                case VALUE_PROPERTY_STRING:
                    value = context.unmarshallWithType(child);
                    break;
                case VALIDATOR_PROPERTY_STRING:
                    validator = context.unmarshall(
                        child,
                        ValidatorSelector.class
                    );
                    break;
                default:
                    JsonNodeUnmarshallContext.unknownPropertyPresent(name, node);
                    break;
            }
        }

        return with(reference)
            .setLabel(label)
            .setValue(Optional.ofNullable(value))
            .setValidator(Optional.ofNullable(validator));
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        JsonObject object = JsonNode.object()
            .set(REFERENCE_PROPERTY, context.marshallWithType(this.reference));

        {
            final String label = this.label;
            if (false == NO_LABEL.equals(label)) {
                object = object.set(
                    LABEL_PROPERTY,
                    context.marshall(label)
                );
            }
        }

        {
            final Object value = this.value.orElse(null);
            if (null != value) {
                object = object.set(
                    VALUE_PROPERTY,
                    context.marshallWithType(value)
                );
            }
        }

        {
            final ValidatorSelector validator = this.validator.orElse(null);
            if (null != validator) {
                object = object.set(
                    VALIDATOR_PROPERTY,
                    context.marshall(validator)
                );
            }
        }

        return object;
    }

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(FormField.class),
            FormField::unmarshall,
            FormField::marshall,
            FormField.class
        );
    }
}
