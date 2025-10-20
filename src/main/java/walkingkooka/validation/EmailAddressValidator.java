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

import walkingkooka.net.email.EmailAddress;

import java.util.List;
import java.util.Objects;

/**
 * A {@link Validator} that verifies that {@link String} is a {@link EmailAddress}.
 */
final class EmailAddressValidator<R extends ValidationReference, C extends ValidatorContext<R>> implements Validator<R, C> {

    static <R extends ValidationReference, C extends ValidatorContext<R>> EmailAddressValidator<R, C> instance() {
        return INSTANCE;
    }

    private final static EmailAddressValidator INSTANCE = new EmailAddressValidator<>();

    private EmailAddressValidator() {
        super();
    }

    // Validator........................................................................................................

    @Override
    public List<ValidationError<R>> validate(final Object value,
                                             final C context) {
        Objects.requireNonNull(context, "context");

        ValidationErrorList<R> errors = context.validationErrorList();

        try {
            EmailAddress.parse(
                context.convertOrFail(
                    value,
                    String.class
                )
            );
        } catch (final NullPointerException cause) {
            errors = errors.concat(
                context.validationError()
                    .setMessage("Missing email address")
            );
        } catch (final UnsupportedOperationException rethrow) {
            throw rethrow;
        } catch (final RuntimeException cause) {
            errors = errors.concat(
                context.validationError()
                    .setMessage(cause.getMessage())
            );
        }

        return errors;
    }

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
