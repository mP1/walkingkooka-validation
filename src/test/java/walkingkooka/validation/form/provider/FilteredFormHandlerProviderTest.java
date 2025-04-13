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

import walkingkooka.reflect.JavaVisibility;

public final class FilteredFormHandlerProviderTest implements FormHandlerProviderTesting<FilteredFormHandlerProvider> {

    @Override
    public void testFormHandlerSelectorWithNullSelectorFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testFormHandlerSelectorWithNullContextFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public FilteredFormHandlerProvider createFormHandlerProvider() {
        return FilteredFormHandlerProvider.with(
            FormHandlerProviders.fake(),
            FormHandlerInfoSet.EMPTY.concat(
                FormHandlerInfo.parse("https://github.com/mP1/walkingkooka-validation-provider/formHandler/non-null non-null")
            )
        );
    }

    // class............................................................................................................

    @Override
    public Class<FilteredFormHandlerProvider> type() {
        return FilteredFormHandlerProvider.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
