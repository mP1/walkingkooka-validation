/*
 * Copyright 2024 Miroslav Pokorny (github.com/mP1)
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

package walkingkooka.validation.provider;

import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.set.Sets;
import walkingkooka.collect.set.SortedSets;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.plugin.ProviderContexts;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.reflect.MethodAttributes;
import walkingkooka.text.CaseKind;
import walkingkooka.validation.Validator;
import walkingkooka.validation.Validators;

import java.lang.reflect.Method;
import java.util.Set;

public final class ValidatorsValidatorProviderTest implements ValidatorProviderTesting<ValidatorsValidatorProvider> {

    private final static ProviderContext CONTEXT = ProviderContexts.fake();

    @Test
    public void testValidatorSelectorCollection() {
        this.validatorAndCheck(
            ValidatorSelector.with(
                ValidatorName.COLLECTION,
                "(2,non-null)"
            ),
            CONTEXT,
            Validators.collection(
                2,
                Lists.of(Validators.nonNull())
            )
        );
    }

    @Test
    public void testValidatorSelectorNonNull() {
        this.validatorAndCheck(
            ValidatorSelector.with(
                ValidatorName.NON_NULL,
                ""
            ),
            CONTEXT,
            Validators.nonNull()
        );
    }

    @Test
    public void testValidatorFactoryMethodWithoutParameters() {
        final Set<ValidatorName> missing = SortedSets.tree();
        final ValidatorsValidatorProvider provider = this.createValidatorProvider();
        int i = 0;

        for (final Method method : Validators.class.getMethods()) {
            if (JavaVisibility.PUBLIC != JavaVisibility.of(method)) {
                continue;
            }

            if (false == MethodAttributes.STATIC.is(method)) {
                continue;
            }

            final String methodName = method.getName();
            if ("fake".equals(methodName)) {
                continue;
            }

            if (method.getReturnType() != Validator.class) {
                continue;
            }

            if (method.getParameters().length > 0) {
                continue;
            }

            final String name = CaseKind.CAMEL.change(
                methodName,
                CaseKind.KEBAB
            );

            System.out.println(method + " " + name);

            final ValidatorName validatorName = ValidatorName.with(name);

            try {
                ValidatorSelector.with(
                    validatorName,
                    ""
                ).evaluateValueText(
                    provider,
                    CONTEXT
                );
            } catch (final Exception fail) {
                missing.add(validatorName);
            }

            i++;
        }

        this.checkNotEquals(
            0,
            i
        );

        this.checkEquals(
            Sets.empty(),
            missing
        );
    }

    @Test
    public void testValidatorCollection() {
        this.validatorAndCheck(
            ValidatorSelector.parse("collection (3, non-null)"),
            CONTEXT,
            Validators.collection(
                3,
                Lists.of(
                    Validators.nonNull()
                )
            )
        );
    }

    @Override
    public ValidatorsValidatorProvider createValidatorProvider() {
        return ValidatorsValidatorProvider.INSTANCE;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }

    @Override
    public Class<ValidatorsValidatorProvider> type() {
        return ValidatorsValidatorProvider.class;
    }
}
