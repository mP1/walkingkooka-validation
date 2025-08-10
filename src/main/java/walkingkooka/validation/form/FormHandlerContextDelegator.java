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
import walkingkooka.convert.CanConvertDelegator;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentContextDelegator;
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.ValidatorContext;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface FormHandlerContextDelegator<R extends ValidationReference, S> extends FormHandlerContext<R, S>,
    CanConvertDelegator,
    EnvironmentContextDelegator {

    @Override
    default Form<R> form() {
        return this.formHandlerContext()
            .form();
    }

    @Override
    default Comparator<R> formFieldReferenceComparator() {
        return this.formHandlerContext()
            .formFieldReferenceComparator();
    }

    @Override
    default ValidatorContext<R> validatorContext(final R reference) {
        return this.formHandlerContext()
            .validatorContext(reference);
    }

    @Override
    default Optional<Object> loadFormFieldValue(final R reference) {
        return this.formHandlerContext()
            .loadFormFieldValue(reference);
    }

    @Override
    default S saveFormFieldValues(final List<FormField<R>> formFields) {
        return this.formHandlerContext()
            .saveFormFieldValues(formFields);
    }

    FormHandlerContext<R, S> formHandlerContext();

    // CanConvertDelegator..............................................................................................

    @Override
    default CanConvert canConvert() {
        return this.formHandlerContext();
    }

    // EnvironmentContext...............................................................................................

    @Override
    default <T> FormHandlerContext<R, S> setEnvironmentValue(final EnvironmentValueName<T> name,
                                                             final T value) {
        this.environmentContext()
            .setEnvironmentValue(
                name,
                value
            );
        return this;
    }

    @Override
    default FormHandlerContext<R, S> removeEnvironmentValue(final EnvironmentValueName<?> name) {
        this.environmentContext()
            .removeEnvironmentValue(name);
        return this;
    }

    @Override
    default EnvironmentContext environmentContext() {
        return this.formHandlerContext();
    }
}
