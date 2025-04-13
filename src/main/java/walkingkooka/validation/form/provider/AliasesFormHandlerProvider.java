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

import walkingkooka.plugin.ProviderContext;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.form.FormHandler;
import walkingkooka.validation.form.FormHandlerContext;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A {@link FormHandlerProvider} that uses the given aliases definition and {@link FormHandlerProvider} to present another view.
 */
final class AliasesFormHandlerProvider implements FormHandlerProvider {

    static AliasesFormHandlerProvider with(final FormHandlerAliasSet aliases,
                                           final FormHandlerProvider provider) {
        return new AliasesFormHandlerProvider(
            Objects.requireNonNull(aliases, "aliases"),
            Objects.requireNonNull(provider, "provider")
        );
    }

    private AliasesFormHandlerProvider(final FormHandlerAliasSet aliases,
                                       final FormHandlerProvider provider) {
        this.aliases = aliases;
        this.provider = provider;

        this.infos = aliases.merge(provider.formHandlerInfos());
    }

    @Override
    public <R extends ValidationReference, C extends FormHandlerContext<R>> FormHandler<R, C> formHandler(final FormHandlerSelector selector,
                                                                                                          final ProviderContext context) {
        return this.provider.formHandler(
            this.aliases.selector(selector),
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

        FormHandler<R, C> formHandler;

        final FormHandlerAliasSet aliases = this.aliases;
        final FormHandlerProvider provider = this.provider;

        final Optional<FormHandlerSelector> selector = aliases.aliasSelector(name);
        if (selector.isPresent()) {
            if (false == values.isEmpty()) {
                throw new IllegalArgumentException("Alias " + name + " should have no values");
            }
            // assumes that $provider caches selectors to formHandler
            formHandler = provider.formHandler(
                selector.get(),
                context
            );
        } else {
            formHandler = provider.formHandler(
                aliases.aliasOrName(name)
                    .orElseThrow(() -> new IllegalArgumentException("Unknown FormHandler " + name)),
                values,
                context
            );
        }

        return formHandler;
    }

    private final FormHandlerAliasSet aliases;

    private final FormHandlerProvider provider;

    @Override
    public FormHandlerInfoSet formHandlerInfos() {
        return this.infos;
    }

    private final FormHandlerInfoSet infos;

    @Override
    public String toString() {
        return this.formHandlerInfos().toString();
    }
}
