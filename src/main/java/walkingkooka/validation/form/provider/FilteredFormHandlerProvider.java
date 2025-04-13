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

package walkingkooka.validation.form.provider;

import walkingkooka.plugin.FilteredProviderGuard;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.form.FormHandler;
import walkingkooka.validation.form.FormHandlerContext;

import java.util.List;
import java.util.Objects;

/**
 * A {@link FormHandlerProvider} that provides {@link FormHandler} from one provider but lists more {@link FormHandlerInfo}.
 */
final class FilteredFormHandlerProvider implements FormHandlerProvider {

    static FilteredFormHandlerProvider with(final FormHandlerProvider provider,
                                            final FormHandlerInfoSet infos) {
        return new FilteredFormHandlerProvider(
            Objects.requireNonNull(provider, "provider"),
            Objects.requireNonNull(infos, "infos")
        );
    }

    private FilteredFormHandlerProvider(final FormHandlerProvider provider,
                                        final FormHandlerInfoSet infos) {
        this.guard = FilteredProviderGuard.with(
            infos.names(),
            FormHandlerPluginHelper.INSTANCE
        );
        this.provider = provider;
        this.infos = infos;
    }

    @Override
    public <R extends ValidationReference, C extends FormHandlerContext<R>> FormHandler<R, C> formHandler(final FormHandlerSelector selector,
                                                                                                          final ProviderContext context) {
        return this.provider.formHandler(
            selector,
            context
        );
    }

    @Override
    public <R extends ValidationReference, C extends FormHandlerContext<R>> FormHandler<R, C> formHandler(final FormHandlerName name,
                                                                                                          final List<?> values,
                                                                                                          final ProviderContext context) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(values, "values");
        Objects.requireNonNull(context, "context");

        return this.provider.formHandler(
            this.guard.name(name),
            values,
            context
        );
    }

    private final FilteredProviderGuard<FormHandlerName, FormHandlerSelector> guard;

    private final FormHandlerProvider provider;

    @Override
    public FormHandlerInfoSet formHandlerInfos() {
        return this.infos;
    }

    private final FormHandlerInfoSet infos;

    @Override
    public String toString() {
        return this.provider.toString();
    }
}
