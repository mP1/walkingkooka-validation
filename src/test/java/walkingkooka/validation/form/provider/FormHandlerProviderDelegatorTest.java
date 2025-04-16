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
import walkingkooka.validation.form.provider.FormHandlerProviderDelegatorTest.TestFormHandlerProvider;

public final class FormHandlerProviderDelegatorTest implements FormHandlerProviderTesting<TestFormHandlerProvider> {

    @Override
    public TestFormHandlerProvider createFormHandlerProvider() {
        return new TestFormHandlerProvider();
    }

    @Override
    public void testTestNaming() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testFormHandlerNameWithNullNameFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testFormHandlerNameWithNullValueFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testFormHandlerNameWithNullContextFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testFormHandlerSelectorWithNullSelectorFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testFormHandlerSelectorWithNullContextFails() {
        throw new UnsupportedOperationException();
    }

    // class............................................................................................................

    @Override
    public Class<TestFormHandlerProvider> type() {
        return TestFormHandlerProvider.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }

    static class TestFormHandlerProvider implements FormHandlerProviderDelegator {

        @Override
        public FormHandlerProvider formHandlerProvider() {
            return FormHandlerProviders.fake();
        }
    }
}
