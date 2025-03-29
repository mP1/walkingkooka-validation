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
import walkingkooka.InvalidCharacterException;
import walkingkooka.collect.list.Lists;
import walkingkooka.plugin.PluginSelectorEvaluateValueTextProvider;
import walkingkooka.plugin.PluginSelectorLikeTesting;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.plugin.ProviderContexts;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.text.CharSequences;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.TestValidatorContext;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.Validator;
import walkingkooka.validation.ValidatorContext;
import walkingkooka.validation.Validators;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ValidatorSelectorTest implements PluginSelectorLikeTesting<ValidatorSelector, ValidatorName> {

    private final static ValidatorName NAME = ValidatorName.with("super-magic-validator123");

    private final static ValidatorName NAME2 = ValidatorName.with("validator2");

    private final static ValidatorName NAME3 = ValidatorName.with("validator3");

    private final static String TEXT = "$0.00";

    private final static ProviderContext CONTEXT = ProviderContexts.fake();

    @Override
    public ValidatorSelector createPluginSelectorLike(final ValidatorName name,
                                                      final String text) {
        return ValidatorSelector.with(
            name,
            text
        );
    }

    @Override
    public ValidatorName createName(final String value) {
        return ValidatorName.with(value);
    }

    // parse............................................................................................................

    @Override
    public ValidatorSelector parseString(final String text) {
        return ValidatorSelector.parse(text);
    }

    // EvaluateValueText................................................................................................

    @Test
    public void testEvaluateValueTextFails() {
        final String text = NAME + " text/plain";

        final InvalidCharacterException thrown = assertThrows(
            InvalidCharacterException.class,
            () -> ValidatorSelector.parse(text)
                .evaluateValueText(
                    ValidatorProviders.fake(),
                    CONTEXT
                )
        );

        this.checkEquals(
            new InvalidCharacterException(
                text,
                text.indexOf(' ')
            ).getMessage(),
            thrown.getMessage()
        );
    }

    @Test
    public void testEvaluateValueTextNoText() {
        final Validator<TestValidationReference, TestValidatorContext> expected = Validators.fake();

        this.evaluateValueTextAndCheck(
            NAME + "",
            (n, p, x) -> {
                checkName(n, NAME);
                checkParameters(p);

                return expected;
            },
            expected
        );
    }

    @Test
    public void testEvaluateValueTextSpacesText() {
        final Validator<TestValidationReference, TestValidatorContext> expected = Validators.fake();

        this.evaluateValueTextAndCheck(
            NAME + " ",
            (n, p, x) -> {
                checkName(n, NAME);
                checkParameters(p);

                return expected;
            },
            expected
        );
    }

    @Test
    public void testEvaluateValueTextSpacesText2() {
        final Validator<TestValidationReference, TestValidatorContext> expected = Validators.fake();

        this.evaluateValueTextAndCheck(
            NAME + "   ",
            (n, p, x) -> {
                checkName(n, NAME);
                checkParameters(p);

                return expected;
            },
            expected
        );
    }

    @Test
    public void testEvaluateValueTextOpenParensFail() {
        this.evaluateValueTextFails(
            NAME + "(",
            "Invalid character '(' at 24 in \"super-magic-validator123(\""
        );
    }

    @Test
    public void testEvaluateValueTextDoubleLiteral() {
        final Validator<TestValidationReference, TestValidatorContext> expected = Validators.fake();

        this.evaluateValueTextAndCheck(
            NAME + " (1)",
            (n, p, x) -> {
                checkName(n, NAME);
                checkParameters(p, 1.0);

                return expected;
            },
            expected
        );
    }

    @Test
    public void testEvaluateValueTextNegativeDoubleLiteral() {
        final Validator<TestValidationReference, TestValidatorContext> expected = Validators.fake();

        this.evaluateValueTextAndCheck(
            NAME + " (-1)",
            (n, p, x) -> {
                checkName(n, NAME);
                checkParameters(p, -1.0);

                return expected;
            },
            expected
        );
    }

    @Test
    public void testEvaluateValueTextDoubleLiteralWithDecimals() {
        final Validator<TestValidationReference, TestValidatorContext> expected = Validators.fake();

        this.evaluateValueTextAndCheck(
            NAME + " (1.25)",
            (n, p, x) -> {
                checkName(n, NAME);
                checkParameters(p, 1.25);

                return expected;
            },
            expected
        );
    }

    @Test
    public void testEvaluateValueTextDoubleMissingClosingParensFail() {
        this.checkEquals(
            25,
            NAME.textLength() + 1
        );

        this.evaluateValueTextFails(
            NAME + "(1",
            "Invalid character '1' at 25 in \"super-magic-validator123(1\""
        );
    }

    @Test
    public void testEvaluateValueTextStringUnclosedFail() {
        this.evaluateValueTextFails(
            NAME + " (\"unclosed",
            "Missing closing '\"'"
        );
    }

    @Test
    public void testEvaluateValueTextEmptyParameterList() {
        final Validator<TestValidationReference, TestValidatorContext> expected = Validators.fake();

        this.evaluateValueTextAndCheck(
            NAME + " ()",
            (n, p, x) -> {
                checkName(n, NAME);
                checkParameters(p);

                return expected;
            },
            expected
        );
    }

    @Test
    public void testEvaluateValueTextEmptyParameterListWithExtraSpaces() {
        final Validator<TestValidationReference, TestValidatorContext> expected = Validators.fake();

        this.evaluateValueTextAndCheck(
            NAME + "  ( )",
            (n, p, x) -> {
                checkName(n, NAME);
                checkParameters(p);

                return expected;
            },
            expected
        );
    }

    @Test
    public void testEvaluateValueTextEmptyParameterListWithExtraSpaces2() {
        final Validator<TestValidationReference, TestValidatorContext> expected = Validators.fake();

        this.evaluateValueTextAndCheck(
            NAME + "   (  )",
            (n, p, x) -> {
                checkName(n, NAME);
                checkParameters(p);

                return expected;
            },
            expected
        );
    }

    @Test
    public void testEvaluateValueTextStringLiteral() {
        final Validator<TestValidationReference, TestValidatorContext> expected = Validators.fake();

        this.evaluateValueTextAndCheck(
            NAME + " (\"string-literal-parameter\")",
            (n, p, x) -> {
                checkName(n, NAME);
                checkParameters(p, "string-literal-parameter");

                return expected;
            },
            expected
        );
    }

    @Test
    public void testEvaluateValueTextStringLiteralStringLiteral() {
        final Validator<TestValidationReference, TestValidatorContext> expected = Validators.fake();

        this.evaluateValueTextAndCheck(
            NAME + " (\"string-literal-parameter-1\",\"string-literal-parameter-2\")",
            (n, p, x) -> {
                checkName(n, NAME);
                checkParameters(p, "string-literal-parameter-1", "string-literal-parameter-2");

                return expected;
            },
            expected
        );
    }

    @Test
    public void testEvaluateValueTextStringLiteralStringLiteralWithExtraSpaceIgnored() {
        final Validator<TestValidationReference, TestValidatorContext> expected = Validators.fake();

        this.evaluateValueTextAndCheck(
            NAME + "  ( \"string-literal-parameter-1\" , \"string-literal-parameter-2\" )",
            (n, p, x) -> {
                checkName(n, NAME);
                checkParameters(p, "string-literal-parameter-1", "string-literal-parameter-2");

                return expected;
            },
            expected
        );
    }

    @Test
    public void testEvaluateValueTextValidator() {
        final Validator<TestValidationReference, TestValidatorContext> expected1 = Validators.fake();
        final Validator<TestValidationReference, TestValidatorContext> expected2 = Validators.fake();

        this.evaluateValueTextAndCheck(
            NAME + " (" + NAME2 + ")",
            (n, p, x) -> {
                if (n.equals(NAME)) {
                    checkParameters(p, expected2);
                    return expected1;
                }
                if (n.equals(NAME2)) {
                    checkParameters(p);
                    return expected2;
                }

                throw new IllegalArgumentException("Unknown validator " + n);
            },
            expected1
        );
    }

    @Test
    public void testEvaluateValueTextValidatorValidator() {
        final Validator<TestValidationReference, TestValidatorContext> expected1 = Validators.fake();
        final Validator<TestValidationReference, TestValidatorContext> expected2 = Validators.fake();
        final Validator<TestValidationReference, TestValidatorContext> expected3 = Validators.fake();

        this.evaluateValueTextAndCheck(
            NAME + " (" + NAME2 + "," + NAME3 + ")",
            (n, p, x) -> {
                if (n.equals(NAME)) {
                    checkParameters(p, expected2, expected3);
                    return expected1;
                }
                if (n.equals(NAME2)) {
                    checkParameters(p);
                    return expected2;
                }
                if (n.equals(NAME3)) {
                    checkParameters(p);
                    return expected3;
                }

                throw new IllegalArgumentException("Unknown validator " + n);
            },
            expected1
        );
    }

    @Test
    public void testEvaluateValueTextNestedValidator() {
        final Validator<TestValidationReference, TestValidatorContext> expected1 = Validators.fake();
        final Validator<TestValidationReference, TestValidatorContext> expected2 = Validators.fake();
        final Validator<TestValidationReference, TestValidatorContext> expected3 = Validators.fake();

        this.evaluateValueTextAndCheck(
            NAME + " (" + NAME2 + "(" + NAME3 + "))",
            (n, p, x) -> {
                if (n.equals(NAME)) {
                    checkParameters(p, expected2);
                    return expected1;
                }
                if (n.equals(NAME2)) {
                    checkParameters(p, expected3);
                    return expected2;
                }
                if (n.equals(NAME3)) {
                    checkParameters(p);
                    return expected3;
                }

                throw new IllegalArgumentException("Unknown validator " + n);
            },
            expected1
        );
    }

    private void evaluateValueTextFails(final String selector,
                                        final String expected) {
        this.evaluateValueTextFails(
            selector,
            CONTEXT,
            expected
        );
    }

    private void evaluateValueTextFails(final String selector,
                                        final ProviderContext context,
                                        final String expected) {
        this.evaluateValueTextFails(
            selector,
            ValidatorProviders.fake(),
            context,
            expected
        );
    }

    private void evaluateValueTextFails(final String selector,
                                        final ValidatorProvider provider,
                                        final ProviderContext context,
                                        final String expected) {
        final IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> ValidatorSelector.parse(selector)
                .evaluateValueText(
                    provider,
                    context
                )
        );
        this.checkEquals(
            expected,
            thrown.getMessage(),
            () -> "validator " + CharSequences.quoteAndEscape(selector)
        );
    }

    private void evaluateValueTextAndCheck(final String selector,
                                           final PluginSelectorEvaluateValueTextProvider<ValidatorName, Validator<TestValidationReference, TestValidatorContext>> factory,
                                           final Validator<TestValidationReference, TestValidatorContext> expected) {
        this.evaluateValueTextAndCheck(
            selector,
            new FakeValidatorProvider() {
                @Override
                public <R extends ValidationReference, C extends ValidatorContext<R>> Validator<R, C> validator(final ValidatorName name,
                                                                                                                final List<?> values,
                                                                                                                final ProviderContext context) {
                    return Cast.to(
                        factory.get(
                            name,
                            values,
                            context
                        )
                    );
                }
            },
            CONTEXT,
            expected
        );
    }

    private void evaluateValueTextAndCheck(final String selector,
                                           final ValidatorProvider provider,
                                           final ProviderContext context,
                                           final Validator<TestValidationReference, TestValidatorContext> expected) {
        this.checkEquals(
            expected,
            ValidatorSelector.parse(selector)
                .evaluateValueText(
                    provider,
                    context
                )
        );
    }

    private void checkName(final ValidatorName name,
                           final ValidatorName expected) {
        this.checkEquals(
            expected,
            name,
            "name"
        );
    }

    private void checkParameters(final List<?> parameters,
                                 final Object... expected) {
        this.checkParameters(
            parameters,
            Lists.of(expected)
        );
    }

    private void checkParameters(final List<?> parameters,
                                 final List<?> expected) {
        this.checkEquals(
            expected,
            parameters,
            "parameters"
        );
    }

    // ClassTesting.....................................................................................................

    @Override
    public Class<ValidatorSelector> type() {
        return ValidatorSelector.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }

    // Json.............................................................................................................

    @Test
    public void testMarshall() {
        this.marshallAndCheck(
            this.createJsonNodeMarshallingValue(),
            "\"super-magic-validator123 $0.00\""
        );
    }

    @Test
    public void testUnmarshall() {
        this.unmarshallAndCheck(
            "\"super-magic-validator123 $0.00\"",
            this.createJsonNodeMarshallingValue()
        );
    }

    @Override
    public ValidatorSelector unmarshall(final JsonNode json,
                                        final JsonNodeUnmarshallContext context) {
        return ValidatorSelector.unmarshall(
            json,
            context
        );
    }

    @Override
    public ValidatorSelector createJsonNodeMarshallingValue() {
        return ValidatorSelector.with(
            NAME,
            TEXT
        );
    }

    // type name testing................................................................................................

    @Override
    public String typeNamePrefix() {
        return Validator.class.getSimpleName();
    }
}
