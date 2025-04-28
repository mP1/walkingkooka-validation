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

package walkingkooka.validation.form.function;

import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.tree.expression.ExpressionEvaluationContextDelegator;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.ValidatorContext;
import walkingkooka.validation.form.Form;
import walkingkooka.validation.form.FormField;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FormHandlerExpressionEvaluationContextDelegator<R extends ValidationReference, S> extends FormHandlerExpressionEvaluationContext<R, S>,
    ExpressionEvaluationContextDelegator {

    // ExpressionEvaluationContextDelegator.............................................................................

    @Override
    FormHandlerExpressionEvaluationContext<R, S> expressionEvaluationContext();

    // ExpressionEvaluationContext......................................................................................

    @Override
    default <T> Optional<T> environmentValue(final EnvironmentValueName<T> name) {
        return this.expressionEvaluationContext()
            .environmentValue(name);
    }

    @Override
    default Set<EnvironmentValueName<?>> environmentValueNames() {
        return this.expressionEvaluationContext()
            .environmentValueNames();
    }

    @Override
    default Optional<EmailAddress> user() {
        return this.expressionEvaluationContext()
            .user();
    }

    // FormHandlerExpressionEvaluationContext.............................................................................

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
    default Optional<Object> loadFieldValue(final R reference) {
        return this.expressionEvaluationContext()
            .loadFieldValue(reference);
    }

    @Override
    default S saveFieldValues(final List<FormField<R>> formFields) {
        return this.expressionEvaluationContext()
            .saveFieldValues(formFields);
    }
}
