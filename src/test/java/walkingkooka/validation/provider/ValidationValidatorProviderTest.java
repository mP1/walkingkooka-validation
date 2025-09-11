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

package walkingkooka.validation.provider;

import org.junit.jupiter.api.Test;
import walkingkooka.Either;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.set.Sets;
import walkingkooka.collect.set.SortedSets;
import walkingkooka.plugin.FakeProviderContext;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.reflect.MethodAttributes;
import walkingkooka.text.CaseKind;
import walkingkooka.tree.expression.Expression;
import walkingkooka.validation.Validator;
import walkingkooka.validation.Validators;

import java.lang.reflect.Method;
import java.util.Set;

public final class ValidationValidatorProviderTest implements ValidatorProviderTesting<ValidationValidatorProvider> {

    private final static Expression EXPRESSION = Expression.add(
        Expression.value(1),
        Expression.value(2)
    );

    private final static ProviderContext CONTEXT = new FakeProviderContext() {

        @Override
        public <T> Either<T, String> convert(final Object value,
                                             final Class<T> type) {
            return Expression.class == type ?
                this.successfulConversion(
                    EXPRESSION,
                    type
                ) :
                Integer.class == type ?
                    this.successfulConversion(
                        Number.class.cast(value).intValue(),
                        type
                    ) :
                    String.class == type ?
                        this.successfulConversion(
                            String.class.cast(value),
                            type
                        ) :
                        this.failConversion(
                            value,
                            type
                        );
        }
    };

    @Test
    public void testValidatorSelectorWithAbsoluteUrlValidator() {
        this.validatorAndCheck(
            ValidatorSelector.with(
                ValidatorName.ABSOLUTE_URL,
                ""
            ),
            CONTEXT,
            Validators.absoluteUrl()
        );
    }

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
    public void testValidatorSelectorExpression() {
        this.validatorAndCheck(
            ValidatorSelector.with(
                ValidatorName.EXPRESSION,
                "(\"=1+2\")"
            ),
            CONTEXT,
            Validators.expression(EXPRESSION)
        );
    }

    @Test
    public void testValidatorSelectorWithTextLength() {
        this.validatorAndCheck(
            ValidatorSelector.with(
                ValidatorName.TEXT_LENGTH,
                "(1, 2)"
            ),
            CONTEXT,
            Validators.textLength(
                1,
                2
            )
        );
    }

    @Test
    public void testValidatorSelectorWithTextMask() {
        this.validatorAndCheck(
            ValidatorSelector.with(
                ValidatorName.TEXT_MASK,
                "(\"???\")"
            ),
            CONTEXT,
            Validators.textMask("???")
        );
    }

    @Test
    public void testValidatorFactoryMethodWithoutParameters() {
        final Set<ValidatorName> missing = SortedSets.tree();
        final ValidationValidatorProvider provider = this.createValidatorProvider();
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
    public ValidationValidatorProvider createValidatorProvider() {
        return ValidationValidatorProvider.INSTANCE;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }

    @Override
    public Class<ValidationValidatorProvider> type() {
        return ValidationValidatorProvider.class;
    }
}
