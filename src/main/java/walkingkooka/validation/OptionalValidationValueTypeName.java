/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
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

import walkingkooka.CanBeEmpty;
import walkingkooka.Cast;
import walkingkooka.Value;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.text.printer.TreePrintable;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.Objects;
import java.util.Optional;

/**
 * A typed Optional {@link ValidationValueTypeName}. This is especially necessary because generics are erased and it is not possible
 * to test and distinguish between empty Optionals of different values.
 */
public final class OptionalValidationValueTypeName implements Value<Optional<ValidationValueTypeName>>,
        CanBeEmpty,
        TreePrintable {

    public final static OptionalValidationValueTypeName EMPTY = new OptionalValidationValueTypeName(Optional.empty());

    private OptionalValidationValueTypeName(final Optional<ValidationValueTypeName> value) {
        this.value = value;
    }

    public static OptionalValidationValueTypeName with(final Optional<ValidationValueTypeName> value) {
        Objects.requireNonNull(value, "value");

        return value.isPresent() ?
                new OptionalValidationValueTypeName(value) :
                Cast.to(EMPTY);
    }

    // value............................................................................................................

    @Override
    public Optional<ValidationValueTypeName> value() {
        return this.value;
    }

    private final Optional<ValidationValueTypeName> value;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
                other instanceof OptionalValidationValueTypeName &&
                        this.equals0(Cast.to(other));
    }

    private boolean equals0(final OptionalValidationValueTypeName other) {
        return this.value.equals(other.value);
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    // json.............................................................................................................

    static OptionalValidationValueTypeName unmarshall(final JsonNode node,
                                                      final JsonNodeUnmarshallContext context) {
        return with(
            context.unmarshallOptional(
                node,
                ValidationValueTypeName.class
            )
        );
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return context.marshallOptional(this.value);
    }

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(OptionalValidationValueTypeName.class),
            OptionalValidationValueTypeName::unmarshall,
            OptionalValidationValueTypeName::marshall,
            OptionalValidationValueTypeName.class
        );
    }

    // CanBeEmpty.......................................................................................................

    @Override
    public boolean isEmpty() {
        return !this.value.isPresent();
    }

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        printer.println(this.getClass().getSimpleName());
        printer.indent();
        {
            final Optional<ValidationValueTypeName> value = this.value;
            if (value.isPresent()) {
                TreePrintable.printTreeOrToString(
                    value.get(),
                    printer
                );
            }
        }
        printer.outdent();
    }
}
