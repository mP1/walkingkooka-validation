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

import walkingkooka.convert.CanConvert;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.ValidatorContext;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * A {@link walkingkooka.Context} that accompanies a {@link FormHandler}.
 */
public interface FormHandlerContext<R extends ValidationReference, S> extends CanConvert, EnvironmentContext {

    /**
     * Returns the selected {@link Form}.
     * A spreadsheet form should use the references to load the initial value.
     */
    Form<R> form();

    /**
     * A {@link Comparator} that may be used to sort {@link FormField#reference()}.
     * It is probably best not to return null if null is available, as this will be probably be used to construct sorted
     * collection types like {@link java.util.TreeMap}.
     */
    Comparator<R> formFieldReferenceComparator();

    /**
     * Factory that creates a {@link ValidatorContext} that may be used to validate the given {@link ValidationReference} and its value.
     */
    ValidatorContext<R> validatorContext(final R reference);

    /**
     * Loads the current value for a form field from some other source, and does not return the actual {@link FormField#value()}.
     * <br>
     * In a spreadsheet this would use the reference to return the SpreadsheetCell#inputValue assuming the cell is present.
     */
    Optional<?> loadFieldValue(final R reference);

    /**
     * Assumes that the fields have been validated, and saves any {@link FormField#value()} ignoring all other field properties.
     */
    S saveFieldValues(final List<FormField<R>> formFields);
}
