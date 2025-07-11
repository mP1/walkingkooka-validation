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
import walkingkooka.collect.map.Maps;
import walkingkooka.net.http.server.hateos.HateosResource;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.text.printer.TreePrintable;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonObject;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.validation.ValidationError;
import walkingkooka.validation.ValidationErrorList;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.form.provider.FormHandlerSelector;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * A form has a name and defines one or more fields each with their own validator.
 */
public final class Form<T extends ValidationReference> implements HateosResource<FormName>,
    TreePrintable{

    /**
     * A {@link Comparator} that may be used to sort {@link Form} using only the {@link FormName}.
     */
    public static <T extends ValidationReference> Comparator<Form<T>> nameComparator() {
        return (final Form<T> left, final Form<T> right) -> left.name.compareTo(right.name);
    }

    public static <T extends ValidationReference> Form<T> with(final FormName name) {
        return new Form<>(
            Objects.requireNonNull(name, "name"),
            NO_HANDLER,
            FormFieldList.empty(),
            ValidationErrorList.empty()
        );
    }

    // @VisibleForTesting
    Form(final FormName name,
         final Optional<FormHandlerSelector> handler,
         final FormFieldList<T> fields,
         final ValidationErrorList<T> errors) {
        this.name = name;
        this.handler = handler;
        this.fields = fields;
        this.errors = errors;
    }

    // HateosResource...................................................................................................

    @Override
    public Optional<FormName> id() {
        return Optional.of(this.name);
    }

    @Override
    public String hateosLinkId() {
        return this.name.toString();
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
                this.handler,
                this.fields,
                this.errors
            );
    }

    private final FormName name;

    // handler..........................................................................................................

    /**
     * Default handler for all forms.
     */
    public final static Optional<FormHandlerSelector> NO_HANDLER = Optional.empty();

    public Optional<FormHandlerSelector> handler() {
        return this.handler;
    }

    public Form<T> setHandler(final Optional<FormHandlerSelector> handler) {
        return this.handler.equals(handler) ?
            this :
            new Form<>(
                this.name,
                Objects.requireNonNull(handler, "handler"),
                this.fields,
                this.errors
            );
    }

    private final Optional<FormHandlerSelector> handler;

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
                this.handler,
                copy,
                this.errors
            );
    }

    private final FormFieldList<T> fields;

    /**
     * Helper that returns a {@link Map} using the reference as the key.
     */
    Map<T, FormField<T>> referenceAndFormFieldMap(final Comparator<T> referenceComparator) {
        final Map<T, FormField<T>> referenceToField = Maps.sorted(referenceComparator);
        for(final FormField<T> field : this.fields) {
            referenceToField.put(
                field.reference(),
                field
            );
        }

        return referenceToField;
    }

    // errors.............................................................................................................

    public List<ValidationError<T>> errors() {
        return this.errors;
    }

    /**
     * Returns a {@link Form} with the given errors, creating a new instance and sharing the other properties.
     * No attempt is made to verify if all the {@link ValidationError#reference()} match existing {@link #fields()}.
     */
    public Form<T> setErrors(final List<ValidationError<T>> errors) {
        final ValidationErrorList<T> copy = ValidationErrorList.with(
            Objects.requireNonNull(errors, "errors")
        );

        return this.errors.equals(copy) ?
            this :
            new Form<>(
                this.name,
                this.handler,
                this.fields,
                copy
            );
    }

    /**
     * Clears an errors, but keeps the name and fields.
     */
    public Form<T> clearErrors() {
        return this.setErrors(ValidationErrorList.empty());
    }

    private final ValidationErrorList<T> errors;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return Objects.hash(
            this.name,
            this.handler,
            this.fields,
            this.errors
        );
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof Form &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final Form<?> other) {
        return this.name.equals(other.name) &&
            this.handler.equals(other.handler) &&
            this.fields.equals(other.fields) &&
            this.errors.equals(other.errors);
    }

    @Override
    public String toString() {
        return ToStringBuilder.empty()
            .value(this.name)
            .label("handler")
            .value(this.handler)
            .label("fields")
            .value(this.fields)
            .label("errors")
            .value(this.errors)
            .build();
    }

    // Json.............................................................................................................

    private final static String NAME_PROPERTY_STRING = "name";

    private final static String HANDLER_PROPERTY_STRING = "handler";

    private final static String FIELDS_PROPERTY_STRING = "fields";

    private final static String ERRORS_PROPERTY_STRING = "errors";

    final static JsonPropertyName NAME_PROPERTY = JsonPropertyName.with(NAME_PROPERTY_STRING);

    final static JsonPropertyName HANDLER_PROPERTY = JsonPropertyName.with(HANDLER_PROPERTY_STRING);

    final static JsonPropertyName FIELDS_PROPERTY = JsonPropertyName.with(FIELDS_PROPERTY_STRING);

    final static JsonPropertyName ERRORS_PROPERTY = JsonPropertyName.with(ERRORS_PROPERTY_STRING);

    static <R extends ValidationReference> Form<R> unmarshall(final JsonNode node,
                                                              final JsonNodeUnmarshallContext context) {
        FormName formName = null;
        Optional<FormHandlerSelector> handler = NO_HANDLER;
        FormFieldList<R> fields = FormFieldList.empty();
        ValidationErrorList<R> errors = ValidationErrorList.empty();

        for (JsonNode child : node.objectOrFail().children()) {
            final JsonPropertyName name = child.name();
            switch (name.value()) {
                case NAME_PROPERTY_STRING:
                    formName = context.unmarshall(
                        child,
                        FormName.class
                    );
                    break;
                case HANDLER_PROPERTY_STRING:
                    handler = context.unmarshallOptional(
                        child,
                        FormHandlerSelector.class
                    );
                    break;
                case FIELDS_PROPERTY_STRING:
                    fields = context.unmarshall(
                        child,
                        FormFieldList.class
                    );
                    break;
                case ERRORS_PROPERTY_STRING:
                    errors = context.unmarshall(
                        child,
                        ValidationErrorList.class
                    );
                    break;
                default:
                    JsonNodeUnmarshallContext.unknownPropertyPresent(name, node);
                    break;
            }
        }

        return Form.<R>with(formName)
            .setHandler(handler)
            .setFields(fields)
            .setErrors(errors);
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        JsonObject json = JsonNode.object()
            .set(NAME_PROPERTY, context.marshall(this.name));

        final FormFieldList<T> fields = this.fields;
        if (fields.isNotEmpty()) {
            json = json.set(
                FIELDS_PROPERTY,
                context.marshall(fields)
            );
        }

        final Optional<FormHandlerSelector> handler = this.handler;
        if (handler.isPresent()) {
            json = json.set(
                HANDLER_PROPERTY,
                context.marshallOptional(handler)
            );
        }

        final ValidationErrorList<T> errors = this.errors;
        if (errors.isNotEmpty()) {
            json = json.set(
                ERRORS_PROPERTY,
                context.marshall(errors)
            );
        }

        return json;
    }

    static {
        FormName.with("HelloForm");
        FormFieldList.empty();
        ValidationErrorList.empty();

        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(Form.class),
            Form::unmarshall,
            Form::marshall,
            Form.class
        );
    }

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        printer.println(this.getClass().getSimpleName());
        printer.indent();
        {
            TreePrintable.printTreeOrToString(
                this.name,
                printer
            );
            printer.lineStart();

            {
                final Optional<FormHandlerSelector> handler = this.handler;
                if (handler.isPresent()) {
                    printer.println("handler:");
                    printer.indent();
                    {
                        handler.get()
                            .printTree(printer);
                    }
                    printer.outdent();
                    printer.lineStart();
                }
            }

            printTreeLabelAndCollection(
                "fields",
                this.fields,
                printer
            );

            printTreeLabelAndCollection(
                "errors",
                this.errors,
                printer
            );
        }
        printer.outdent();
    }


    private static <T extends TreePrintable> void printTreeLabelAndCollection(final String label,
                                                                              final Collection<T> collection,
                                                                              final IndentingPrinter printer) {
        if (false == collection.isEmpty()) {
            printer.println(label + ":");
            printer.indent();
            {
                for (final T element : collection) {
                    element.printTree(printer);
                }
            }
            printer.outdent();
        }
    }
}
