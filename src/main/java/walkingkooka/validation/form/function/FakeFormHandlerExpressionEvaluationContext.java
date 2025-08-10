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
import walkingkooka.tree.expression.ExpressionReference;
import walkingkooka.tree.expression.FakeExpressionEvaluationContext;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.ValidatorContext;
import walkingkooka.validation.form.Form;
import walkingkooka.validation.form.FormField;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class FakeFormHandlerExpressionEvaluationContext<R extends ValidationReference, S> extends FakeExpressionEvaluationContext implements FormHandlerExpressionEvaluationContext<R, S> {

    public FakeFormHandlerExpressionEvaluationContext() {
        super();
    }

    @Override
    public FormHandlerExpressionEvaluationContext<R, S> enterScope(final Function<ExpressionReference, Optional<Optional<Object>>> scoped) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Form<R> form() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Comparator<R> formFieldReferenceComparator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ValidatorContext<R> validatorContext(final R reference) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Object> loadFormFieldValue(final R reference) {
        throw new UnsupportedOperationException();
    }

    @Override
    public S saveFormFieldValues(final List<FormField<R>> formFields) {
        throw new UnsupportedOperationException();
    }

    // EnvironmentContext...............................................................................................

    @Override
    public <T> Optional<T> environmentValue(final EnvironmentValueName<T> environmentValueName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<EnvironmentValueName<?>> environmentValueNames() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> FormHandlerExpressionEvaluationContext<R, S> setEnvironmentValue(final EnvironmentValueName<T> name,
                                                                                final T value) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(value, "value");
        throw new UnsupportedOperationException();
    }

    @Override
    public FormHandlerExpressionEvaluationContext<R, S> removeEnvironmentValue(final EnvironmentValueName<?> name) {
        Objects.requireNonNull(name, "name");
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<EmailAddress> user() {
        throw new UnsupportedOperationException();
    }
}
