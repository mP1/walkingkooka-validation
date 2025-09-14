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
import java.util.Collection;
import java.util.Objects;

/**
 * An {@link walkingkooka.collect.list.ImmutableList} holding zero or more {@link ValidationError}.
 * This is particularly useful to marshall and unmarshall lists of validation errors to/from {@link JsonNode}.
 */
public final class ValidationErrorList<T extends ValidationReference> extends AbstractList<ValidationError<T>>
    implements ImmutableListDefaults<ValidationErrorList<T>, ValidationError<T>> {

    /**
     * Factory that creates a new {@link ValidationErrorList} after taking a defensive copy.
     */
    public static <T extends ValidationReference> ValidationErrorList<T> empty() {
        return Cast.to(EMPTY);
    }

    private static final ValidationErrorList<?> EMPTY = new ValidationErrorList<>(
        new ValidationError[0]
    );

    /**
     * Factory that takes a copy and returns a {@link ValidationErrorList}.
     */
    public static <T extends ValidationReference> ValidationErrorList<T> with(final Collection<ValidationError<T>> errors) {
        Objects.requireNonNull(errors, "errors");

        final int size = errors.size();
        final ValidationError<T>[] copy = new ValidationError[errors.size()];
        errors.toArray(copy);

        final ValidationErrorList<T> result;
        switch (size) {
            case 0:
                result = empty();
                break;
            default:
                result = new ValidationErrorList<>(copy);
                break;
        }

        return result;
    }

    private ValidationErrorList(final ValidationError<T>[] list) {
        this.list = list;
    }

    @Override
    public ValidationError<T> get(final int index) {
        return this.list[index];
    }

    @Override
    public int size() {
        return this.list.length;
    }

    @Override
    public void elementCheck(final ValidationError error) {
        Objects.requireNonNull(error, "error");
    }

    @Override
    public ValidationErrorList<T> setElements(final Collection<ValidationError<T>> errors) {
        final ValidationErrorList<T> copy = with(errors);
        return this.equals(copy) ?
            this :
            copy;
    }

    private final ValidationError<T>[] list;

    // Json.............................................................................................................

    static <T extends ValidationReference> ValidationErrorList<T> unmarshall(final JsonNode node,
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
