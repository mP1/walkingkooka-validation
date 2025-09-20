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
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
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

    // @VisibleForTesting
    ValidationErrorList(final ValidationError<T>[] list) {
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
        Objects.requireNonNull(errors, "errors");

        ValidationErrorList<T> validationErrorList;

        if (errors instanceof ValidationErrorList) {
            validationErrorList = (ValidationErrorList<T>) errors;
        } else {
            final List<ValidationError<T>> copy = Lists.array();
            for (final ValidationError<T> error : errors) {
                copy.add(
                    Objects.requireNonNull(error, "includes null " + ValidationError.class.getSimpleName())
                );
            }

            final int size = copy.size();
            switch (size) {
                case 0:
                    validationErrorList = empty();
                    break;
                default:
                    validationErrorList = new ValidationErrorList<>(
                        copy.toArray(
                            new ValidationError[size]
                        )
                    );
                    break;
            }
        }

        return this.equals(validationErrorList) ?
            this :
            validationErrorList;
    }

    private final ValidationError<T>[] list;

    // Json.............................................................................................................

    static <T extends ValidationReference> ValidationErrorList<T> unmarshall(final JsonNode node,
                                                                             final JsonNodeUnmarshallContext context) {
        return ValidationErrorList.<T>empty()
            .setElements(
                context.unmarshallList(
                    node,
                    Cast.to(ValidationError.class)
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
