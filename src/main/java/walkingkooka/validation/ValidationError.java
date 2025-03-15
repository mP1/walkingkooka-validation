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
import walkingkooka.ToStringBuilder;
import walkingkooka.UsesToStringBuilder;
import walkingkooka.Value;
import walkingkooka.text.Whitespace;

import java.util.Objects;
import java.util.Optional;

/**
 * Reports a single error for the identified field or component.
 */
public final class ValidationError implements Value<Optional<Object>>,
    UsesToStringBuilder {

    public static ValidationError with(final ValidationReference reference,
                                       final String message,
                                       final Optional<Object> value) {
        return new ValidationError(
            Objects.requireNonNull(reference, "reference"),
            Whitespace.failIfNullOrEmptyOrWhitespace(message, "message"),
            Objects.requireNonNull(value, "value")
        );
    }

    private ValidationError(final ValidationReference reference,
                            final String message,
                            final Optional<Object> value) {
        this.reference = reference;
        this.message = message;
        this.value = value;
    }

    public ValidationReference reference() {
        return this.reference;
    }

    private final ValidationReference reference;

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

    private boolean equals0(final ValidationError error) {
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
}
