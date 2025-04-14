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
import walkingkooka.HasId;
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.validation.ValidationReference;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A form has a name and defines one or more fields each with their own validator.
 */
public final class Form<T extends ValidationReference> implements HasId<Optional<FormName>> {

    public static <T extends ValidationReference> Form<T> with(final FormName name) {
        return new Form<>(
            Objects.requireNonNull(name, "name"),
            FormFieldList.with(Lists.empty())
        );
    }

    // @VisibleForTesting
    Form(final FormName name,
         final List<FormField<T>> fields) {
        this.name = name;
        this.fields = fields;
    }

    // HasId............................................................................................................

    @Override
    public Optional<FormName> id() {
        return Optional.of(this.name);
    }

    // name.............................................................................................................

    public FormName name() {
        return this.name;
    }

    public Form<T> setName(final FormName name) {
        return this.name.equals(name) ?
            this :
            new Form<>(
                Objects.requireNonNull(name, "name"),
                this.fields
            );
    }

    private final FormName name;

    // fields.............................................................................................................

    public List<FormField<T>> fields() {
        return this.fields;
    }

    public Form<T> setFields(final List<FormField<T>> fields) {
        final FormFieldList<T> copy = FormFieldList.with(
            Objects.requireNonNull(fields, "fields")
        );

        return this.fields.equals(copy) ?
            this :
            new Form<>(
                this.name,
                copy
            );
    }

    private final List<FormField<T>> fields;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof Form &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final Form<?> other) {
        return this.name.equals(other.name) &&
            this.fields.equals(other.fields);
    }

    @Override
    public String toString() {
        return this.name.toString();
    }

    // Json.............................................................................................................

    private final static String NAME_PROPERTY_STRING = "name";

    private final static String FIELDS_PROPERTY_STRING = "fields";

    final static JsonPropertyName NAME_PROPERTY = JsonPropertyName.with(NAME_PROPERTY_STRING);

    final static JsonPropertyName FIELDS_PROPERTY = JsonPropertyName.with(FIELDS_PROPERTY_STRING);

    static <R extends ValidationReference> Form<R> unmarshall(final JsonNode node,
                                                              final JsonNodeUnmarshallContext context) {
        FormName formName = null;
        List<FormField<R>> fields = null;

        for (JsonNode child : node.objectOrFail().children()) {
            final JsonPropertyName name = child.name();
            switch (name.value()) {
                case NAME_PROPERTY_STRING:
                    formName = context.unmarshall(
                        child,
                        FormName.class
                    );
                    break;
                case FIELDS_PROPERTY_STRING:
                    fields = context.unmarshall(
                        child,
                        FormFieldList.class
                    );
                    break;
                default:
                    JsonNodeUnmarshallContext.unknownPropertyPresent(name, node);
                    break;
            }
        }

        return Form.<R>with(formName)
            .setFields(fields);
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return JsonNode.object()
            .set(NAME_PROPERTY, context.marshall(this.name))
            .set(FIELDS_PROPERTY, context.marshall(this.fields));
    }

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(Form.class),
            Form::unmarshall,
            Form::marshall,
            Form.class
        );
    }
}
