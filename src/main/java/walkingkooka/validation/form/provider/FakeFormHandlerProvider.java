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

public class FakeFormHandlerProvider implements FormHandlerProvider {

    @Override
    public <R extends ValidationReference, S, C extends FormHandlerContext<R, S>> FormHandler<R, S, C> formHandler(final FormHandlerSelector selector,
                                                                                                                   final ProviderContext context) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <R extends ValidationReference, S, C extends FormHandlerContext<R, S>> FormHandler<R, S, C> formHandler(final FormHandlerName name,
                                                                                                                   final List<?> values,
                                                                                                                   final ProviderContext context) {
        throw new UnsupportedOperationException();
    }

    @Override
    public FormHandlerInfoSet formHandlerInfos() {
        throw new UnsupportedOperationException();
    }
}
