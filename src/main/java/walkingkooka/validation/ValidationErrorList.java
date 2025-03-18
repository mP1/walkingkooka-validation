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
import walkingkooka.collect.list.ImmutableListDefaults;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.AbstractList;
import java.util.List;
import java.util.Objects;

/**
 * An {@link walkingkooka.collect.list.ImmutableList} holding zero or more {@link ValidationError}.
 * This is particularly useful to marshall and unmarshall lists of validation errors to/from {@link JsonNode}.
 */
public final class ValidationErrorList extends AbstractList<ValidationError<?>>
    implements ImmutableListDefaults<ValidationErrorList, ValidationError<?>> {

    /**
     * Factory that creates a new {@link ValidationErrorList} after taking a defensive copy.
     */
    public static final ValidationErrorList EMPTY = new ValidationErrorList(
        new ValidationError<?>[0]
    );

    /**
     * Factory that takes a copy and returns a {@link ValidationErrorList}.
     */
    public static ValidationErrorList with(final List<ValidationError<?>> list) {
        Objects.requireNonNull(list, "list");

        final int size = list.size();
        final ValidationError<?>[] copy = new ValidationError[list.size()];
        list.toArray(copy);

        final ValidationErrorList result;
        switch (size) {
            case 0:
                result = EMPTY;
                break;
            default:
                result = new ValidationErrorList(copy);
                break;
        }

        return result;
    }

    private ValidationErrorList(final ValidationError<?>[] list) {
        this.list = list;
    }

    @Override
    public ValidationError<?> get(final int index) {
        return this.list[index];
    }

    @Override
    public int size() {
        return this.list.length;
    }

    @Override
    public ValidationErrorList setElements(final List<ValidationError<?>> list) {
        final ValidationErrorList copy = with(list);
        return this.equals(copy) ?
            this :
            copy;
    }

    private final ValidationError<?>[] list;

    // Json.............................................................................................................

    static ValidationErrorList unmarshall(final JsonNode node,
                                          final JsonNodeUnmarshallContext context) {
        return with(
            Cast.to(
                context.unmarshallList(
                    node,
                    ValidationError.class
                )
            )
        );
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return context.marshallCollection(this);
    }

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(ValidationErrorList.class),
            ValidationErrorList::unmarshall,
            ValidationErrorList::marshall,
            ValidationErrorList.class
        );
    }
}
