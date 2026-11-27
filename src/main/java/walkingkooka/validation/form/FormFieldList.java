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
import walkingkooka.collect.list.ImmutableListDefaults;
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.validation.ValidationReference;

import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * An {@link walkingkooka.collect.list.ImmutableList} holding zero or more {@link FormField}.
 * This is particularly useful to marshall and unmarshall lists of fields to/from {@link JsonNode}.
 * Nothing happens if {@link FormField} have duplicate {@link ValidationReference}.
 */
public final class FormFieldList<T extends ValidationReference> extends AbstractList<FormField<T>>
    implements ImmutableListDefaults<FormFieldList<T>, FormField<T>> {

    /**
     * Factory that creates a new {@link FormFieldList} after taking a defensive copy.
     */
    public static <T extends ValidationReference> FormFieldList<T> empty() {
        return Cast.to(EMPTY);
    }

    private static final FormFieldList<?> EMPTY = new FormFieldList<>(Lists.empty());

    FormFieldList(final List<FormField<T>> list) {
        this.list = list;
    }

    @Override
    public FormField<T> get(final int index) {
        return this.list.get(index);
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public void elementCheck(final FormField<T> field) {
        Objects.requireNonNull(field, "FormFieldList includes null field");
    }

    @Override
    public FormFieldList<T> setElements(final Collection<FormField<T>> formFields) {
        final FormFieldList<T> formFieldList;

        if (formFields instanceof FormFieldList) {
            formFieldList = (FormFieldList<T>) formFields;
        } else {
            Objects.requireNonNull(formFields, "formFields");

            final List<FormField<T>> copy = Lists.array();
            for (final FormField<T> formField : formFields) {
                this.elementCheck(formField);
                copy.add(formField);
            }

            formFieldList = this.equals(copy) ?
                this :
                this.replaceFormFields(copy);
        }

        return formFieldList;
    }

    @Override
    public FormFieldList<T> subList(final int from,
                                    final int to) {
        return 0 == from && this.size() == to ?
            this :
            this.replaceFormFields(
                this.list.subList(
                    from,
                    to
                )
            );
    }

    private FormFieldList<T> replaceFormFields(final List<FormField<T>> formFields) {
        return formFields.isEmpty() ?
            empty() :
            new FormFieldList<>(formFields);
    }

    private final List<FormField<T>> list;

    // Json.............................................................................................................

    static <T extends ValidationReference> FormFieldList<T> unmarshall(final JsonNode node,
                                                                       final JsonNodeUnmarshallContext context) {
        return FormFieldList.<T>empty()
            .setElements(
                Cast.to(
                    context.unmarshallList(
                        node,
                        FormField.class
                    )
                )
            );
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return context.marshallCollection(this);
    }

    static {
        FormField.forceJsonRegistry();

        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(FormFieldList.class),
            FormFieldList::unmarshall,
            FormFieldList::marshall,
            FormFieldList.class
        );
    }
}
