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

import walkingkooka.plugin.MergedProviderMapper;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.form.FormHandler;
import walkingkooka.validation.form.FormHandlerContext;

import java.util.List;
import java.util.Objects;

/**
 * A {@link FormHandlerProvider} that supports renaming {@link FormHandlerName} before invoking a wrapped {@link FormHandlerProvider}.
 */
final class MergedMappedFormHandlerProvider implements FormHandlerProvider {

    static MergedMappedFormHandlerProvider with(final FormHandlerInfoSet infos,
                                                final FormHandlerProvider provider) {
        Objects.requireNonNull(infos, "infos");
        Objects.requireNonNull(provider, "provider");

        return new MergedMappedFormHandlerProvider(
            infos,
            provider
        );
    }

    private MergedMappedFormHandlerProvider(final FormHandlerInfoSet infos,
                                            final FormHandlerProvider provider) {
        this.provider = provider;
        this.mapper = MergedProviderMapper.with(
            infos,
            provider.formHandlerInfos(),
            FormHandlerPluginHelper.INSTANCE
        );
    }

    @Override
    public <R extends ValidationReference, S, C extends FormHandlerContext<R, S>> FormHandler<R, S, C> formHandler(final FormHandlerSelector selector,
                                                                                                                   final ProviderContext context) {
        Objects.requireNonNull(selector, "selector");

        return selector.evaluateValueText(
            this,
            context
        );
    }

    @Override
    public <R extends ValidationReference, S, C extends FormHandlerContext<R, S>> FormHandler<R, S, C> formHandler(final FormHandlerName name,
                                                                                                                   final List<?> values,
                                                                                                                   final ProviderContext context) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(values, "values");
        Objects.requireNonNull(context, "context");

        return this.provider.formHandler(
            this.mapper.name(name),
            values,
            context
        );
    }

    /**
     * The original wrapped {@link FormHandlerProvider}.
     */
    private final FormHandlerProvider provider;

    @Override
    public FormHandlerInfoSet formHandlerInfos() {
        return this.mapper.infos();
    }

    private final MergedProviderMapper<FormHandlerName, FormHandlerInfo, FormHandlerInfoSet, FormHandlerSelector, FormHandlerAlias, FormHandlerAliasSet> mapper;

    @Override
    public String toString() {
        return this.mapper.toString();
    }
}
