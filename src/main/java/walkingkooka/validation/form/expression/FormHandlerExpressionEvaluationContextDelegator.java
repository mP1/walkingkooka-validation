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

package walkingkooka.validation.form.expression;

import walkingkooka.currency.CurrencyCode;
import walkingkooka.environment.expression.EnvironmentExpressionEvaluationContext;
import walkingkooka.environment.expression.EnvironmentExpressionEvaluationContextDelegator;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.ValidatorContext;
import walkingkooka.validation.form.Form;
import walkingkooka.validation.form.FormField;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface FormHandlerExpressionEvaluationContextDelegator<R extends ValidationReference, S> extends FormHandlerExpressionEvaluationContext<R, S>,
    EnvironmentExpressionEvaluationContextDelegator {

    // EnvironmentExpressionEvaluationContextDelegator..................................................................

    @Override
    default EnvironmentExpressionEvaluationContext environmentExpressionEvaluationContext() {
        return this.formHandlerExpressionEvaluationContext();
    }

    @Override
    default CurrencyCode currencyCode() {
        return this.environmentExpressionEvaluationContext()
            .currencyCode();
    }

    @Override
    default Locale locale() {
        return this.environmentExpressionEvaluationContext()
            .locale();
    }

    @Override
    default void setLocale(final Locale locale) {
        this.environmentExpressionEvaluationContext()
            .setLocale(locale);
    }

    FormHandlerExpressionEvaluationContext<R, S> formHandlerExpressionEvaluationContext();

    // FormHandlerExpressionEvaluationContext...........................................................................

    @Override
    default Form<R> form() {
        return this.formHandlerExpressionEvaluationContext()
            .form();
    }

    @Override
    default Comparator<R> formFieldReferenceComparator() {
        return this.formHandlerExpressionEvaluationContext()
            .formFieldReferenceComparator();
    }

    @Override
    default ValidatorContext<R> validatorContext(final R reference) {
        return this.formHandlerExpressionEvaluationContext()
            .validatorContext(reference);
    }

    @Override
    default Optional<Object> loadFormFieldValue(final R reference) {
        return this.formHandlerExpressionEvaluationContext()
            .loadFormFieldValue(reference);
    }

    @Override
    default S saveFormFieldValues(final List<FormField<R>> formFields) {
        return this.formHandlerExpressionEvaluationContext()
            .saveFormFieldValues(formFields);
    }
}
