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
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.reflect.PublicStaticHelperTesting;
import walkingkooka.text.CaseSensitivity;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ValidatorExpressionFunctionProvidersTest implements PublicStaticHelperTesting<ValidatorExpressionFunctionProviders> {

    // expressionFunctionProvider.......................................................................................

    @Test
    public void testExpressionFunctionInfos() {
        final IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> ValidatorExpressionFunctionProviders.expressionFunctionProvider(CaseSensitivity.INSENSITIVE)
                .expressionFunctionInfos()
        );

        this.checkEquals(
            "Functions cannot be empty",
            thrown.getMessage()
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
