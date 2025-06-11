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
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.set.SortedSets;
import walkingkooka.convert.CanConvert;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.text.CharSequences;
import walkingkooka.text.CharacterConstant;
import walkingkooka.validation.ValidationError;
import walkingkooka.validation.ValidationErrorList;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.Validator;
import walkingkooka.validation.ValidatorContext;
import walkingkooka.validation.provider.ValidatorSelector;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * A {@link walkingkooka.Context} that accompanies a {@link FormHandler}.
 */
public interface FormHandlerContext<R extends ValidationReference, S> extends CanConvert,
    HasForm<R>,
    EnvironmentContext {

    /**
     * A {@link Comparator} that may be used to sort {@link FormField#reference()}.
     * It is probably best not to return null if null is available, as this will be probably be used to construct sorted
     * collection types like {@link java.util.TreeMap}.
     */
    Comparator<R> formFieldReferenceComparator();

    /**
     * Loads the current value for a form field from some other source, and does not return the actual {@link FormField#value()}.
     * <br>
     * In a spreadsheet this would use the reference to return the SpreadsheetCell#inputValue assuming the cell is present.
     */
    Optional<Object> loadFormFieldValue(final R reference);

    /**
     * Factory that creates a {@link ValidatorContext} that may be used to validate the given {@link ValidationReference} and its value.
     */
    ValidatorContext<R> validatorContext(final R reference);

    /**
     * A default validate of the given form fields, only using the reference and value from the given {@link FormField},
     * fetching the validator from the given {@link #form()}.
     * <br>
     * Unknown fields will throw an exception and different given {@link FormField} definitions are ignored.
     */
    default List<ValidationError<R>> validateFormFields(final List<FormField<R>> fields) {
        Objects.requireNonNull(fields, "fields");

        final Form<R> form = this.form();

        final Comparator<R> formFieldReferenceComparator = this.formFieldReferenceComparator();
        final Map<R, FormField<R>> referenceToField = form.referenceAndFormFieldMap(formFieldReferenceComparator);

        // complain if given Form has extra fields.
        final Set<R> unknownFields = SortedSets.tree(formFieldReferenceComparator);

        final List<ValidationError<R>> errors = Lists.array();

        // validate each field one by one, use the Validator from the source form not the given form.
        for (final FormField<R> field : fields) {
            final R reference = field.reference();

            final FormField<R> sourceFormField = referenceToField.get(reference);
            if (null == sourceFormField) {
                unknownFields.add(reference);
                continue;
            }

            // found 1 unknown field skip validating others.
            if (false == unknownFields.isEmpty()) {
                continue;
            }

            final ValidatorSelector validatorSelector = sourceFormField.validator()
                .orElse(null);

            // if there is no ValidatorSelector skip validating field.
            if (null != validatorSelector) {
                final ValidatorContext<R> validatorContext = this.validatorContext(reference);

                Validator<R, ValidatorContext<R>> validator = null;
                try {
                    validator = Cast.to(
                        validatorContext.validator(validatorSelector)
                    );
                } catch (final RuntimeException missing) {
                    final String message = missing.getMessage();

                    errors.add(
                        validatorContext.validationError(
                            CharSequences.isNullOrEmpty(message) ?
                                "Validator error: " + validatorSelector :
                                message
                        )
                    );
                }

                if (null != validator) {
                    errors.addAll(
                        validator.validate(
                            field.value()
                                .orElse(null),
                            validatorContext
                        )
                    );
                }
            }
        }

        if (false == unknownFields.isEmpty()) {
            throw new IllegalArgumentException(
                "Form contains unknown fields: " + CharacterConstant.COMMA.toSeparatedString(
                    unknownFields,
                    R::text
                )
            );
        }

        return ValidationErrorList.with(errors);
    }

    /**
     * Assumes that the fields have been validated, and saves any {@link FormField#value()} ignoring all other field properties.
     */
    S saveFormFieldValues(final List<FormField<R>> formFields);
}
