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

import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.environment.EnvironmentWatcher;
import walkingkooka.logging.LoggingLevel;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.text.Indentation;
import walkingkooka.text.LineEnding;
import walkingkooka.tree.expression.ExpressionReference;
import walkingkooka.tree.expression.FakeExpressionEvaluationContext;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.ValidatorContext;
import walkingkooka.validation.form.Form;
import walkingkooka.validation.form.FormField;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
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
    public FormHandlerExpressionEvaluationContext<R, S> cloneEnvironment() {
        throw new UnsupportedOperationException();
    }

    @Override
    public FormHandlerExpressionEvaluationContext<R, S> setEnvironmentContext(final EnvironmentContext environmentContext) {
        throw new UnsupportedOperationException();
    }

    @Override
    public EnvironmentValueName<?> parseEnvironmentValueName(final String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> Optional<T> environmentValue(final EnvironmentValueName<T> name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<EnvironmentValueName<?>> environmentValueNames() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void setEnvironmentValue(final EnvironmentValueName<T> name,
                                        final T value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeEnvironmentValue(final EnvironmentValueName<?> name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Charset charset() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCharset(final Charset charset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Currency currency() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCurrency(final Currency currency) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Indentation indentation() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setIndentation(final Indentation indentation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LineEnding lineEnding() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLineEnding(final LineEnding lineEnding) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Locale locale() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLocale(final Locale locale) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LoggingLevel loggingLevel() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLoggingLevel(final LoggingLevel loggingLevel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LocalDateTime now() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ZoneOffset timeOffset() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTimeOffset(final ZoneOffset timeOffset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<EmailAddress> user() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setUser(final Optional<EmailAddress> user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Runnable addEnvironmentWatcher(final EnvironmentWatcher watcher) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Runnable addEnvironmentWatcherOnce(final EnvironmentWatcher watcher) {
        throw new UnsupportedOperationException();
    }
}
