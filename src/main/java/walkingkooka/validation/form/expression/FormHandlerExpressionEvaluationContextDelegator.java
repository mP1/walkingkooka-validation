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

import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.text.LineEnding;
import walkingkooka.tree.expression.ExpressionEvaluationContextDelegator;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.ValidatorContext;
import walkingkooka.validation.form.Form;
import walkingkooka.validation.form.FormField;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface FormHandlerExpressionEvaluationContextDelegator<R extends ValidationReference, S> extends FormHandlerExpressionEvaluationContext<R, S>,
    ExpressionEvaluationContextDelegator {

    // ExpressionEvaluationContextDelegator.............................................................................

    @Override
    default <T> FormHandlerExpressionEvaluationContext<R, S> setEnvironmentValue(final EnvironmentValueName<T> name,
                                                                                 final T value) {
        this.expressionEvaluationContext()
            .setEnvironmentValue(
                name,
                value
            );
        return this;
    }

    @Override
    default FormHandlerExpressionEvaluationContext<R, S> removeEnvironmentValue(final EnvironmentValueName<?> name) {
        this.expressionEvaluationContext()
            .removeEnvironmentValue(name);
        return this;
    }

    @Override
    default FormHandlerExpressionEvaluationContext<R, S> setLineEnding(final LineEnding lineEnding) {
        this.environmentContext()
            .setLineEnding(lineEnding);
        return this;
    }

    @Override
    default FormHandlerExpressionEvaluationContext<R, S> setLocale(final Locale locale) {
        this.environmentContext()
            .setLocale(locale);
        return this;
    }

    @Override
    default FormHandlerExpressionEvaluationContext<R, S> setUser(final Optional<EmailAddress> user) {
        this.environmentContext()
            .setUser(user);
        return this;
    }

    @Override
    FormHandlerExpressionEvaluationContext<R, S> expressionEvaluationContext();

    // FormHandlerExpressionEvaluationContext...........................................................................

    @Override
    default Form<R> form() {
        return this.expressionEvaluationContext()
            .form();
    }

    @Override
    default Comparator<R> formFieldReferenceComparator() {
        return this.expressionEvaluationContext()
            .formFieldReferenceComparator();
    }

    @Override
    default ValidatorContext<R> validatorContext(final R reference) {
        return this.expressionEvaluationContext()
            .validatorContext(reference);
    }

    @Override
    default Optional<Object> loadFormFieldValue(final R reference) {
        return this.expressionEvaluationContext()
            .loadFormFieldValue(reference);
    }

    @Override
    default S saveFormFieldValues(final List<FormField<R>> formFields) {
        return this.expressionEvaluationContext()
            .saveFormFieldValues(formFields);
    }
}
