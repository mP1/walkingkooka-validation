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
import java.util.Optional;

/**
 * An immutable list of {@link ValidationChoice} choices returned by a {@link Validator}.
 */
public final class ValidationChoiceList extends AbstractList<ValidationChoice>
    implements ImmutableListDefaults<ValidationChoiceList, ValidationChoice> {

    /**
     * An empty {@link ValidationChoiceList}.
     */
    public final static ValidationChoiceList EMPTY = new ValidationChoiceList(
        Lists.empty()
    );

    /**
     * Factory that creates a {@link ValidationChoiceList} from the list of {@link ValidationChoice}.
     */
    public static ValidationChoiceList with(final Collection<ValidationChoice> choices) {
        Objects.requireNonNull(choices, "choices");

        ValidationChoiceList DateList;

        if (choices instanceof ValidationChoiceList) {
            DateList = (ValidationChoiceList) choices;
        } else {
            final List<ValidationChoice> copy = Lists.array();
            for (final ValidationChoice name : choices) {
                copy.add(
                    Objects.requireNonNull(name, "includes null " + ValidationChoice.class.getSimpleName())
                );
            }

            switch (choices.size()) {
                case 0:
                    DateList = EMPTY;
                    break;
                default:
                    DateList = new ValidationChoiceList(copy);
                    break;
            }
        }

        return DateList;
    }

    private ValidationChoiceList(final List<ValidationChoice> choices) {
        this.choices = choices;
    }

    @Override
    public ValidationChoice get(int index) {
        return this.choices.get(index);
    }

    @Override
    public int size() {
        return this.choices.size();
    }

    private final List<ValidationChoice> choices;

    @Override
    public void elementCheck(final ValidationChoice choice) {
        Objects.requireNonNull(choice, "choice");
    }

    @Override
    public ValidationChoiceList setElements(final Collection<ValidationChoice> choices) {
        final ValidationChoiceList copy = with(choices);
        return this.equals(copy) ?
            this :
            copy;
    }

    // json.............................................................................................................

    static ValidationChoiceList unmarshall(final JsonNode node,
                                           final JsonNodeUnmarshallContext context) {
        return with(
            context.unmarshallList(
                node,
                ValidationChoice.class
            )
        );
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return context.marshallCollection(this.choices);
    }

    static {
        ValidationChoice.with(
            "Label1",
            Optional.empty()
        );
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(ValidationChoiceList.class),
            ValidationChoiceList::unmarshall,
            ValidationChoiceList::marshall,
            ValidationChoiceList.class
        );
    }
}
