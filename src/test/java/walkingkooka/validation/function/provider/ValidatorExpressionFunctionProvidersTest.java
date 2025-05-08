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

package walkingkooka.validation.function.provider;

import org.junit.jupiter.api.Test;
import walkingkooka.plugin.ProviderContexts;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.reflect.PublicStaticHelperTesting;
import walkingkooka.text.CaseSensitivity;
import walkingkooka.tree.expression.function.provider.ExpressionFunctionInfoSet;
import walkingkooka.tree.expression.function.provider.ExpressionFunctionSelector;
import walkingkooka.validation.function.ValidatorExpressionFunctions;

import java.lang.reflect.Method;

public final class ValidatorExpressionFunctionProvidersTest implements PublicStaticHelperTesting<ValidatorExpressionFunctionProviders> {

    // expressionFunctionProvider.......................................................................................

    @Test
    public void testExpressionFunctionValidationValue() {
        final CaseSensitivity caseSensitivity = CaseSensitivity.SENSITIVE;

        this.checkEquals(
            ValidatorExpressionFunctions.validationValue(),
            ValidatorExpressionFunctionProviders.expressionFunctionProvider(caseSensitivity)
                .expressionFunction(
                    ExpressionFunctionSelector.parse(
                        "validationValue",
                        caseSensitivity
                    ),
                    ProviderContexts.fake()
                )
        );
    }

    @Test
    public void testExpressionFunctionInfos() {
        // https://github.com/mP1/walkingkooka-tree-expression-function-provider/issues/181
        // ExpressionFunctionInfoSet.parse creates functions with SENSITIVE
        this.checkEquals(
            ExpressionFunctionInfoSet.parse("https://github.com/mP1/walkingkooka-validation/Validator/ExpressionFunction/validationValue validationValue"),
            ValidatorExpressionFunctionProviders.expressionFunctionProvider(CaseSensitivity.SENSITIVE)
                .expressionFunctionInfos()
        );
    }

    // class............................................................................................................

    @Override
    public Class<ValidatorExpressionFunctionProviders> type() {
        return ValidatorExpressionFunctionProviders.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }

    @Override
    public boolean canHavePublicTypes(final Method method) {
        return false;
    }
}
