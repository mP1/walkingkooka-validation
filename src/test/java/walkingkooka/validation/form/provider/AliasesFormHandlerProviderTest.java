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
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.set.Sets;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.plugin.ProviderContexts;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.form.FakeFormHandler;
import walkingkooka.validation.form.FakeFormHandlerContext;
import walkingkooka.validation.form.FormHandler;
import walkingkooka.validation.form.FormHandlerContext;

import java.util.List;

public final class AliasesFormHandlerProviderTest implements FormHandlerProviderTesting<AliasesFormHandlerProvider> {

    private final static String NAME1_STRING = "form-handler-1";

    private final static FormHandlerName NAME1 = FormHandlerName.with(NAME1_STRING);

    private final static FormHandlerInfo INFO1 = FormHandlerInfo.parse("https://example.com/form-handler-1 " + NAME1);

    private final static FormHandlerName ALIAS2 = FormHandlerName.with("alias2");

    private final static FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> FORM_HANDLER1 = formHandler(NAME1);

    private final static String NAME2_STRING = "form-handler-2";

    private final static FormHandlerName NAME2 = FormHandlerName.with(NAME2_STRING);

    private final static FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> FORM_HANDLER2 = formHandler(NAME2);

    private final static FormHandlerInfo INFO2 = FormHandlerInfo.parse("https://example.com/form-handler-2 " + NAME2);

    private final static String NAME3_STRING = "form-handler-3";

    private final static FormHandlerName NAME3 = FormHandlerName.with(NAME3_STRING);

    private final static FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> FORM_HANDLER3 = formHandler(NAME3);

    private final static FormHandlerInfo INFO3 = FormHandlerInfo.parse("https://example.com/form-handler-3 " + NAME3);

    private final static String VALUE3 = "Value3";

    private final static String NAME4_STRING = "custom4";

    private final static FormHandlerName NAME4 = FormHandlerName.with(NAME4_STRING);

    private final static FormHandlerInfo INFO4 = FormHandlerInfo.parse("https://example.com/custom4 " + NAME4);

    private static FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> formHandler(final FormHandlerName name) {
        return new FakeFormHandler<>() {

            @Override
            public int hashCode() {
                return name.hashCode();
            }

            @Override
            public boolean equals(final Object other) {
                return this == other || other instanceof FormHandler && this.equals0((FormHandler<?, ?, ?>) other);
            }

            private boolean equals0(final FormHandler<?, ?, ?> other) {
                return this.toString().equals(other.toString());
            }

            @Override
            public String toString() {
                return name.toString();
            }
        };
    }

    private final static ProviderContext CONTEXT = ProviderContexts.fake();

    @Test
    public void testWithUnknownFormHandlerName() {
        AliasesFormHandlerProvider.with(
            FormHandlerAliasSet.parse("unknown-form-handler-404"),
            new FakeFormHandlerProvider() {

                @Override
                public FormHandlerInfoSet formHandlerInfos() {
                    return FormHandlerInfoSet.parse("https://example.com/form-handler-111 form-handler-111");
                }
            }
        );
    }

    @Test
    public void testFormHandlerNameWithName() {
        this.formHandlerAndCheck(
            NAME1,
            Lists.empty(),
            CONTEXT,
            FORM_HANDLER1
        );
    }

    @Test
    public void testFormHandlerSelectorWithName() {
        this.formHandlerAndCheck(
            FormHandlerSelector.parse(NAME1 + ""),
            CONTEXT,
            FORM_HANDLER1
        );
    }

    @Test
    public void testFormHandlerNameWithAlias() {
        this.formHandlerAndCheck(
            ALIAS2,
            Lists.empty(),
            CONTEXT,
            FORM_HANDLER2
        );
    }

    @Test
    public void testFormHandlerSelectorWithAlias() {
        this.formHandlerAndCheck(
            FormHandlerSelector.parse(ALIAS2 + ""),
            CONTEXT,
            FORM_HANDLER2
        );
    }

    @Test
    public void testFormHandlerNameWithSelector() {
        this.formHandlerAndCheck(
            NAME4,
            Lists.empty(),
            CONTEXT,
            FORM_HANDLER3
        );
    }

    @Test
    public void testFormHandlerSelectorWithSelector() {
        this.formHandlerAndCheck(
            FormHandlerSelector.parse(NAME4 + ""),
            CONTEXT,
            FORM_HANDLER3
        );
    }

    @Test
    public void testInfos() {
        this.formHandlerInfosAndCheck(
            INFO1,
            INFO2.setName(ALIAS2),
            INFO4.setName(NAME4) // from FormHandlerAliasSet
        );
    }

    @Override
    public AliasesFormHandlerProvider createFormHandlerProvider() {
        final String aliases = "form-handler-1, alias2 form-handler-2, custom4 form-handler-3(\"Value3\") https://example.com/custom4";

        this.checkEquals(
            NAME1 + ", " + ALIAS2 + " " + NAME2 + ", " + NAME4 + " " + NAME3 + "(\"" + VALUE3 + "\") " + INFO4.url(),
            aliases
        );

        return AliasesFormHandlerProvider.with(
            FormHandlerAliasSet.parse(aliases),
            new FakeFormHandlerProvider() {
                @Override
                public <R extends ValidationReference, S, C extends FormHandlerContext<R, S>> FormHandler<R, S, C> formHandler(final FormHandlerSelector selector,
                                                                                                                               final ProviderContext context) {
                    return selector.evaluateValueText(
                        this,
                        context
                    );
                }

                @Override
                public <R extends ValidationReference, S, C extends FormHandlerContext<R, S>> FormHandler<R, S, C> formHandler(final FormHandlerName name,
                                                                                                                               final List<?> values,
                                                                                                                               final ProviderContext context) {
                    FormHandler<?, ?, ?> formHandler;

                    switch (name.toString()) {
                        case NAME1_STRING:
                            checkEquals(Lists.empty(), values, "values");
                            formHandler = FORM_HANDLER1;
                            break;
                        case NAME2_STRING:
                            checkEquals(Lists.empty(), values, "values");
                            formHandler = FORM_HANDLER2;
                            break;
                        case NAME3_STRING:
                            checkEquals(Lists.of(VALUE3), values, "values");
                            formHandler = FORM_HANDLER3;
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown FormHandler " + name);
                    }

                    return Cast.to(formHandler);
                }

                @Override
                public FormHandlerInfoSet formHandlerInfos() {
                    return FormHandlerInfoSet.with(
                        Sets.of(
                            INFO1,
                            INFO2,
                            INFO3
                        )
                    );
                }
            }
        );
    }

    // class............................................................................................................

    @Override
    public Class<AliasesFormHandlerProvider> type() {
        return AliasesFormHandlerProvider.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
