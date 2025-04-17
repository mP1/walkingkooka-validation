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
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.form.FakeFormHandler;
import walkingkooka.validation.form.FakeFormHandlerContext;
import walkingkooka.validation.form.FormHandler;
import walkingkooka.validation.form.FormHandlerContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class FormHandlerSelectorTest implements PluginSelectorLikeTesting<FormHandlerSelector, FormHandlerName> {

    private final static FormHandlerName NAME = FormHandlerName.with("super-magic-formHandler123");

    private final static FormHandlerName NAME2 = FormHandlerName.with("formHandler2");

    private final static FormHandlerName NAME3 = FormHandlerName.with("formHandler3");

    private final static String TEXT = "$0.00";

    private final static ProviderContext CONTEXT = ProviderContexts.fake();

    @Override
    public FormHandlerSelector createPluginSelectorLike(final FormHandlerName name,
                                                        final String text) {
        return FormHandlerSelector.with(
            name,
            text
        );
    }

    @Override
    public FormHandlerName createName(final String value) {
        return FormHandlerName.with(value);
    }

    // parse............................................................................................................

    @Override
    public FormHandlerSelector parseString(final String text) {
        return FormHandlerSelector.parse(text);
    }

    // EvaluateValueText................................................................................................

    @Test
    public void testEvaluateValueTextFails() {
        final String text = NAME + " text/plain";

        final InvalidCharacterException thrown = assertThrows(
            InvalidCharacterException.class,
            () -> FormHandlerSelector.parse(text)
                .evaluateValueText(
                    FormHandlerProviders.fake(),
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
        final FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> expected = new FakeFormHandler<>();

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
        final FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> expected = new FakeFormHandler<>();

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
        final FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> expected = new FakeFormHandler<>();

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
            "Invalid character '(' at 26 in \"super-magic-formHandler123(\""
        );
    }

    @Test
    public void testEvaluateValueTextDoubleLiteral() {
        final FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> expected = new FakeFormHandler<>();

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
        final FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> expected = new FakeFormHandler<>();

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
        final FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> expected = new FakeFormHandler<>();

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
            27,
            NAME.textLength() + 1
        );

        this.evaluateValueTextFails(
            NAME + "(1",
            "Invalid character '1' at 27 in \"super-magic-formHandler123(1\""
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
        final FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> expected = new FakeFormHandler<>();

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
        final FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> expected = new FakeFormHandler<>();

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
        final FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> expected = new FakeFormHandler<>();

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
        final FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> expected = new FakeFormHandler<>();

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
        final FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> expected = new FakeFormHandler<>();

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
        final FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> expected = new FakeFormHandler<>();

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
    public void testEvaluateValueTextFormHandler() {
        final FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> expected1 = new FakeFormHandler<>();
        final FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> expected2 = new FakeFormHandler<>();

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

                throw new IllegalArgumentException("Unknown formHandler " + n);
            },
            expected1
        );
    }

    @Test
    public void testEvaluateValueTextFormHandlerFormHandler() {
        final FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> expected1 = new FakeFormHandler<>();
        final FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> expected2 = new FakeFormHandler<>();
        final FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> expected3 = new FakeFormHandler<>();

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

                throw new IllegalArgumentException("Unknown formHandler " + n);
            },
            expected1
        );
    }

    @Test
    public void testEvaluateValueTextNestedFormHandler() {
        final FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> expected1 = new FakeFormHandler<>();
        final FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> expected2 = new FakeFormHandler<>();
        final FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> expected3 = new FakeFormHandler<>();

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

                throw new IllegalArgumentException("Unknown formHandler " + n);
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
            FormHandlerProviders.fake(),
            context,
            expected
        );
    }

    private void evaluateValueTextFails(final String selector,
                                        final FormHandlerProvider provider,
                                        final ProviderContext context,
                                        final String expected) {
        final IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> FormHandlerSelector.parse(selector)
                .evaluateValueText(
                    provider,
                    context
                )
        );
        this.checkEquals(
            expected,
            thrown.getMessage(),
            () -> "formHandler " + CharSequences.quoteAndEscape(selector)
        );
    }

    private void evaluateValueTextAndCheck(final String selector,
                                           final PluginSelectorEvaluateValueTextProvider<FormHandlerName, FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>>> factory,
                                           final FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> expected) {
        this.evaluateValueTextAndCheck(
            selector,
            new FakeFormHandlerProvider() {
                @Override
                public <R extends ValidationReference, S, C extends FormHandlerContext<R, S>> FormHandler<R, S, C> formHandler(final FormHandlerName name,
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
                                           final FormHandlerProvider provider,
                                           final ProviderContext context,
                                           final FormHandler<TestValidationReference, Void, FakeFormHandlerContext<TestValidationReference, Void>> expected) {
        this.checkEquals(
            expected,
            FormHandlerSelector.parse(selector)
                .evaluateValueText(
                    provider,
                    context
                )
        );
    }

    private void checkName(final FormHandlerName name,
                           final FormHandlerName expected) {
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
    public Class<FormHandlerSelector> type() {
        return FormHandlerSelector.class;
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
            "\"super-magic-formHandler123 $0.00\""
        );
    }

    @Test
    public void testUnmarshall() {
        this.unmarshallAndCheck(
            "\"super-magic-formHandler123 $0.00\"",
            this.createJsonNodeMarshallingValue()
        );
    }

    @Override
    public FormHandlerSelector unmarshall(final JsonNode json,
                                          final JsonNodeUnmarshallContext context) {
        return FormHandlerSelector.unmarshall(
            json,
            context
        );
    }

    @Override
    public FormHandlerSelector createJsonNodeMarshallingValue() {
        return FormHandlerSelector.with(
            NAME,
            TEXT
        );
    }

    // type name testing................................................................................................

    @Override
    public String typeNamePrefix() {
        return FormHandler.class.getSimpleName();
    }
}
