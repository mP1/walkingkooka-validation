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
import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.set.Sets;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.plugin.ProviderContexts;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.validation.FakeValidator;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.TestValidatorContext;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.Validator;
import walkingkooka.validation.ValidatorContext;

import java.util.List;

public final class AliasesValidatorProviderTest implements ValidatorProviderTesting<AliasesValidatorProvider> {

    private final static String NAME1_STRING = "validator1";

    private final static ValidatorName NAME1 = ValidatorName.with(NAME1_STRING);

    private final static ValidatorInfo INFO1 = ValidatorInfo.parse("https://example.com/validator1 " + NAME1);

    private final static ValidatorName ALIAS2 = ValidatorName.with("alias2");

    private final static Validator<TestValidationReference, TestValidatorContext> VALIDATOR1 = validator(NAME1);

    private final static String NAME2_STRING = "validator2";

    private final static ValidatorName NAME2 = ValidatorName.with(NAME2_STRING);

    private final static Validator<TestValidationReference, TestValidatorContext> VALIDATOR2 = validator(NAME2);

    private final static ValidatorInfo INFO2 = ValidatorInfo.parse("https://example.com/validator2 " + NAME2);

    private final static String NAME3_STRING = "validator3";

    private final static ValidatorName NAME3 = ValidatorName.with(NAME3_STRING);

    private final static Validator<TestValidationReference, TestValidatorContext> VALIDATOR3 = validator(NAME3);

    private final static ValidatorInfo INFO3 = ValidatorInfo.parse("https://example.com/validator3 " + NAME3);

    private final static String VALUE3 = "Value3";

    private final static String NAME4_STRING = "custom4";

    private final static ValidatorName NAME4 = ValidatorName.with(NAME4_STRING);

    private final static ValidatorInfo INFO4 = ValidatorInfo.parse("https://example.com/custom4 " + NAME4);

    private static Validator<TestValidationReference, TestValidatorContext> validator(final ValidatorName name) {
        return new FakeValidator() {

            @Override
            public int hashCode() {
                return name.hashCode();
            }

            @Override
            public boolean equals(final Object other) {
                return this == other || other instanceof Validator && this.equals0((Validator<?, ?>) other);
            }

            private boolean equals0(final Validator<?, ?> other) {
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
    public void testWithUnknownValidatorName() {
        AliasesValidatorProvider.with(
            ValidatorAliasSet.parse("unknown-validator404"),
            new FakeValidatorProvider() {
                @Override
                public ValidatorInfoSet validatorInfos() {
                    return ValidatorInfoSet.parse("https://example.com/validator111 validator111");
                }
            }
        );
    }

    @Test
    public void testValidatorNameWithName() {
        this.validatorAndCheck(
            NAME1,
            Lists.empty(),
            CONTEXT,
            VALIDATOR1
        );
    }

    @Test
    public void testValidatorSelectorWithName() {
        this.validatorAndCheck(
            ValidatorSelector.parse(NAME1 + ""),
            CONTEXT,
            VALIDATOR1
        );
    }

    @Test
    public void testValidatorNameWithAlias() {
        this.validatorAndCheck(
            ALIAS2,
            Lists.empty(),
            CONTEXT,
            VALIDATOR2
        );
    }

    @Test
    public void testValidatorSelectorWithAlias() {
        this.validatorAndCheck(
            ValidatorSelector.parse(ALIAS2 + ""),
            CONTEXT,
            VALIDATOR2
        );
    }

    @Test
    public void testValidatorNameWithSelector() {
        this.validatorAndCheck(
            NAME4,
            Lists.empty(),
            CONTEXT,
            VALIDATOR3
        );
    }

    @Test
    public void testValidatorSelectorWithSelector() {
        this.validatorAndCheck(
            ValidatorSelector.parse(NAME4 + ""),
            CONTEXT,
            VALIDATOR3
        );
    }

    @Test
    public void testInfos() {
        this.validatorInfosAndCheck(
            INFO1,
            INFO2.setName(ALIAS2),
            INFO4.setName(NAME4) // from ValidatorAliasSet
        );
    }

    @Override
    public AliasesValidatorProvider createValidatorProvider() {
        final String aliases = "validator1, alias2 validator2, custom4 validator3(\"Value3\") https://example.com/custom4";

        this.checkEquals(
            NAME1 + ", " + ALIAS2 + " " + NAME2 + ", " + NAME4 + " " + NAME3 + "(\"" + VALUE3 + "\") " + INFO4.url(),
            aliases
        );

        return AliasesValidatorProvider.with(
            ValidatorAliasSet.parse(aliases),
            new FakeValidatorProvider() {
                @Override
                public <R extends ValidationReference, C extends ValidatorContext<R>> Validator<R, C> validator(final ValidatorSelector selector,
                                                                                                                final ProviderContext context) {
                    return selector.evaluateValueText(
                        this,
                        context
                    );
                }

                @Override
                public <R extends ValidationReference, C extends ValidatorContext<R>> Validator<R, C> validator(final ValidatorName name,
                                                                                                                final List<?> values,
                                                                                                                final ProviderContext context) {
                    Validator<?, ?> validator;

                    switch (name.toString()) {
                        case NAME1_STRING:
                            checkEquals(Lists.empty(), values, "values");
                            validator = VALIDATOR1;
                            break;
                        case NAME2_STRING:
                            checkEquals(Lists.empty(), values, "values");
                            validator = VALIDATOR2;
                            break;
                        case NAME3_STRING:
                            checkEquals(Lists.of(VALUE3), values, "values");
                            validator = VALIDATOR3;
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown Validator " + name);
                    }

                    return Cast.to(validator);
                }

                @Override
                public ValidatorInfoSet validatorInfos() {
                    return ValidatorInfoSet.with(
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
    public Class<AliasesValidatorProvider> type() {
        return AliasesValidatorProvider.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
