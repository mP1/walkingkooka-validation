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
import walkingkooka.collect.set.Sets;
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

public final class MergedMappedFormHandlerProviderTest implements FormHandlerProviderTesting<MergedMappedFormHandlerProvider>,
    ToStringTesting<MergedMappedFormHandlerProvider> {

    private final static AbsoluteUrl RENAMED_URL = Url.parseAbsolute("https://example.com/renamed-form-handler-111");

    private final static FormHandlerName RENAMED_RENAME_NAME = FormHandlerName.with("renamed-rename-form-handler-111");

    private final static FormHandlerName RENAMED_PROVIDER_NAME = FormHandlerName.with("renamed-provider-only-form-handler-111");

    private final static FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> RENAME_FORM_HANDLER = FormHandlers.fake();

    private final static AbsoluteUrl PROVIDER_ONLY_URL = Url.parseAbsolute("https://example.com/provider-only-form-handler-222");

    private final static FormHandlerName PROVIDER_ONLY_NAME = FormHandlerName.with("provider-only-form-handler-222");

    private final static FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> PROVIDER_ONLY_FORM_HANDLER = FormHandlers.fake();

    private final static ProviderContext CONTEXT = ProviderContexts.fake();

    @Test
    public void testWithNullInfosFails() {
        assertThrows(
            NullPointerException.class,
            () -> MergedMappedFormHandlerProvider.with(
                null,
                FormHandlerProviders.fake()
            )
        );
    }

    @Test
    public void testWithNullProviderFails() {
        assertThrows(
            NullPointerException.class,
            () -> MergedMappedFormHandlerProvider.with(
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
    public void testFormHandlerSelectorWithRename() {
        this.formHandlerAndCheck(
            FormHandlerSelector.parse("" + RENAMED_RENAME_NAME),
            CONTEXT,
            RENAME_FORM_HANDLER
        );
    }

    @Test
    public void testFormHandlerSelectorWithProviderOnly() {
        this.formHandlerAndCheck(
            FormHandlerSelector.parse("" + PROVIDER_ONLY_NAME),
            CONTEXT,
            PROVIDER_ONLY_FORM_HANDLER
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
    public void testFormHandlerNameWithRename() {
        this.formHandlerAndCheck(
            RENAMED_RENAME_NAME,
            Lists.empty(),
            CONTEXT,
            RENAME_FORM_HANDLER
        );
    }

    @Test
    public void testFormHandlerNameWithProviderOnly() {
        this.formHandlerAndCheck(
            PROVIDER_ONLY_NAME,
            Lists.empty(),
            CONTEXT,
            PROVIDER_ONLY_FORM_HANDLER
        );
    }

    @Test
    public void testInfos() {
        this.formHandlerInfosAndCheck(
            FormHandlerInfo.with(
                RENAMED_URL,
                RENAMED_RENAME_NAME
            ),
            FormHandlerInfo.with(
                PROVIDER_ONLY_URL,
                PROVIDER_ONLY_NAME
            )
        );
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createFormHandlerProvider(),
            "https://example.com/provider-only-form-handler-222 provider-only-form-handler-222,https://example.com/renamed-form-handler-111 renamed-rename-form-handler-111"
        );
    }

    @Override
    public MergedMappedFormHandlerProvider createFormHandlerProvider() {
        return MergedMappedFormHandlerProvider.with(
            FormHandlerInfoSet.with(
                Sets.of(
                    FormHandlerInfo.with(
                        RENAMED_URL,
                        RENAMED_RENAME_NAME
                    )
                )
            ),
            new FakeFormHandlerProvider() {

                @Override
                public <R extends ValidationReference, S, C extends FormHandlerContext<R, S>> FormHandler<R, S, C> formHandler(final FormHandlerName name,
                                                                                                                               final List<?> values,
                                                                                                                               final ProviderContext context) {
                    Objects.requireNonNull(name, "name");
                    Objects.requireNonNull(values, "values");
                    Objects.requireNonNull(context, "context");

                    if (name.equals(RENAMED_PROVIDER_NAME)) {
                        return Cast.to(RENAME_FORM_HANDLER);
                    }
                    if (name.equals(PROVIDER_ONLY_NAME)) {
                        return Cast.to(PROVIDER_ONLY_FORM_HANDLER);
                    }
                    throw new IllegalArgumentException("Unknown FormHandler " + name);
                }

                @Override
                public FormHandlerInfoSet formHandlerInfos() {
                    return FormHandlerInfoSet.with(
                        Sets.of(
                            FormHandlerInfo.with(
                                RENAMED_URL,
                                RENAMED_PROVIDER_NAME
                            ),
                            FormHandlerInfo.with(
                                PROVIDER_ONLY_URL,
                                PROVIDER_ONLY_NAME
                            )
                        )
                    );
                }
            }
        );
    }

    // Class............................................................................................................

    @Override
    public Class<MergedMappedFormHandlerProvider> type() {
        return MergedMappedFormHandlerProvider.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
