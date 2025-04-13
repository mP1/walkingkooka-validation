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

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.ToStringTesting;
import walkingkooka.collect.list.Lists;
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.net.Url;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.plugin.ProviderContexts;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.form.FakeFormHandlerContext;
import walkingkooka.validation.form.FormHandler;
import walkingkooka.validation.form.FormHandlerContext;
import walkingkooka.validation.form.FormHandlers;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class FilteredMappedFormHandlerProviderTest implements FormHandlerProviderTesting<FilteredMappedFormHandlerProvider>,
    ToStringTesting<FilteredMappedFormHandlerProvider> {

    private final static AbsoluteUrl URL = Url.parseAbsolute("https://example.com/formHandler123");

    private final static FormHandlerName NAME = FormHandlerName.with("different-formHandler-name-123");

    private final static FormHandlerName ORIGINAL_NAME = FormHandlerName.with("original-formHandler-123");

    private final static FormHandler<TestValidationReference, FakeFormHandlerContext<TestValidationReference>> FORM_HANDLER = FormHandlers.fake();

    private final static ProviderContext CONTEXT = ProviderContexts.fake();

    @Test
    public void testWithNullViewFails() {
        assertThrows(
            NullPointerException.class,
            () -> FilteredMappedFormHandlerProvider.with(
                null,
                FormHandlerProviders.fake()
            )
        );
    }

    @Test
    public void testWithNullProviderFails() {
        assertThrows(
            NullPointerException.class,
            () -> FilteredMappedFormHandlerProvider.with(
                FormHandlerInfoSet.EMPTY,
                null
            )
        );
    }

    @Test
    public void testFormHandlerSelectorWithUnknownFails() {
        this.formHandlerFails(
            FormHandlerSelector.parse("unknown"),
            CONTEXT
        );
    }

    @Test
    public void testFormHandlerSelector() {
        this.formHandlerAndCheck(
            FormHandlerSelector.parse("" + NAME),
            CONTEXT,
            FORM_HANDLER
        );
    }

    @Test
    public void testFormHandlerNameWithUnknownFails() {
        this.formHandlerFails(
            FormHandlerName.with("unknown"),
            Lists.empty(),
            CONTEXT
        );
    }

    @Test
    public void testFormHandlerName() {
        this.formHandlerAndCheck(
            NAME,
            Lists.empty(),
            CONTEXT,
            FORM_HANDLER
        );
    }

    @Test
    public void testInfos() {
        this.formHandlerInfosAndCheck(
            FormHandlerInfo.with(
                URL,
                NAME
            )
        );
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createFormHandlerProvider(),
            "https://example.com/formHandler123 different-formHandler-name-123"
        );
    }

    @Override
    public FilteredMappedFormHandlerProvider createFormHandlerProvider() {
        return FilteredMappedFormHandlerProvider.with(
            FormHandlerInfoSet.EMPTY.concat(
                FormHandlerInfo.with(
                    URL,
                    NAME
                )
            ),
            new FakeFormHandlerProvider() {

                @Override
                public <R extends ValidationReference, C extends FormHandlerContext<R>> FormHandler<R, C> formHandler(final FormHandlerName name,
                                                                                                                      final List<?> values,
                                                                                                                      final ProviderContext context) {
                    Objects.requireNonNull(name, "name");
                    Objects.requireNonNull(values, "values");
                    Objects.requireNonNull(context, "context");

                    if (false == name.equals(ORIGINAL_NAME)) {
                        throw new IllegalArgumentException("Unknown FormHandler " + name);
                    }
                    return Cast.to(FORM_HANDLER);
                }

                @Override
                public FormHandlerInfoSet formHandlerInfos() {
                    return FormHandlerInfoSet.EMPTY.concat(
                        FormHandlerInfo.with(
                            URL,
                            ORIGINAL_NAME
                        )
                    );
                }
            }
        );
    }

    // Class............................................................................................................

    @Override
    public Class<FilteredMappedFormHandlerProvider> type() {
        return FilteredMappedFormHandlerProvider.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
