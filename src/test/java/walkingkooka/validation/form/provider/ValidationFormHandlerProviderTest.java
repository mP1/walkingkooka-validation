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
import walkingkooka.collect.list.Lists;
import walkingkooka.net.UrlPath;
import walkingkooka.plugin.ProviderContexts;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.reflect.MethodAttributes;
import walkingkooka.text.CaseKind;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContexts;
import walkingkooka.validation.form.FormHandler;
import walkingkooka.validation.form.FormHandlers;

import java.util.Arrays;

public final class ValidationFormHandlerProviderTest implements FormHandlerProviderTesting<ValidationFormHandlerProvider> {

    @Test
    public void testFormHandlerSelector() {
        Arrays.stream(FormHandlers.class.getMethods())
            .filter(MethodAttributes.STATIC::is)
            .filter(m -> FormHandler.class.equals(m.getReturnType()))
            .filter(m -> m.getParameterTypes().length == 0)
            .map(m -> CaseKind.CAMEL.change(
                    m.getName(),
                    CaseKind.KEBAB
                ).toString()
            ).filter(n -> false == "fake".equals(n))
            .forEach(n -> ValidationFormHandlerProvider.INSTANCE.formHandler(
                    FormHandlerSelector.parse(n),
                    ProviderContexts.fake()
                )
            );
    }

    @Test
    public void testFormHandlerName() {
        Arrays.stream(FormHandlers.class.getMethods())
            .filter(MethodAttributes.STATIC::is)
            .filter(m -> FormHandler.class.equals(m.getReturnType()))
            .filter(m -> m.getParameterTypes().length == 0)
            .map(m -> CaseKind.CAMEL.change(
                    m.getName(),
                    CaseKind.KEBAB
                ).toString()
            ).filter(n -> false == "fake".equals(n))
            .forEach(n -> ValidationFormHandlerProvider.INSTANCE.formHandler(
                    FormHandlerName.with(n),
                    Lists.empty(),
                    ProviderContexts.fake()
                )
            );
    }

    @Test
    public void testFormHandlerInfos() {
        this.formHandlerInfosAndCheck(
            ValidationFormHandlerProvider.INSTANCE,
            Arrays.stream(FormHandlers.class.getMethods())
                .filter(MethodAttributes.STATIC::is)
                .filter(m -> FormHandler.class.equals(m.getReturnType()))
                .filter(m -> m.getParameterTypes().length == 0)
                .map(m -> CaseKind.CAMEL.change(
                        m.getName(),
                        CaseKind.KEBAB
                    ).toString()
                ).filter(n -> false == "fake".equals(n))
                .map(n -> FormHandlerInfo.with(
                        FormHandlerProviders.BASE_URL.appendPath(
                            UrlPath.parse(n)
                        ),
                        FormHandlerName.with(n)
                    )
                ).toArray(FormHandlerInfo[]::new)
        );
    }

    @Test
    public void testTreePrint() {
        this.treePrintAndCheck(
            ValidationFormHandlerProvider.INSTANCE.formHandlerInfos(),
            "FormHandlerInfoSet\n" +
                "  https://github.com/mP1/walkingkooka-validation/FormHandler/basic basic\n"
        );
    }

    @Test
    public void testMarshall() {
        this.checkEquals(
            JsonNode.parse(
                "[\n" +
                    "  \"https://github.com/mP1/walkingkooka-validation/FormHandler/basic basic\"\n" +
                    "]"
            ),
            JsonNodeMarshallContexts.basic()
                .marshall(
                    ValidationFormHandlerProvider.INSTANCE.formHandlerInfos()
                )
        );
    }

    @Override
    public ValidationFormHandlerProvider createFormHandlerProvider() {
        return ValidationFormHandlerProvider.INSTANCE;
    }

    // class............................................................................................................

    @Override
    public Class<ValidationFormHandlerProvider> type() {
        return ValidationFormHandlerProvider.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
