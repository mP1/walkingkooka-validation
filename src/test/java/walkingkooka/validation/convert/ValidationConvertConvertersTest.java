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

package walkingkooka.validation.convert;

import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.Lists;
import walkingkooka.convert.Converter;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.Converters;
import walkingkooka.reflect.MethodAttributes;
import walkingkooka.reflect.PublicStaticHelperTesting;
import walkingkooka.text.printer.TreePrintable;
import walkingkooka.text.printer.TreePrintableTesting;

import java.lang.reflect.Method;
import java.util.List;

public final class ValidationConvertConvertersTest implements PublicStaticHelperTesting<ValidationConvertConverters>,
    TreePrintableTesting {

    @Test
    public void testConverterCollectionWithAllConvertersPrintTree() throws Exception {
        final List<Converter<ConverterContext>> converters = Lists.array();

        for (final Method method : ValidationConvertConverters.class.getMethods()) {
            if (false == MethodAttributes.STATIC.is(method)) {
                continue;
            }

            if (false == method.getReturnType().equals(Converter.class)) {
                continue;
            }

            if (method.getParameterCount() != 0) {
                continue;
            }

            if (method.getName().equals("fake")) {
                continue;
            }

            converters.add(
                (Converter<ConverterContext>) method.invoke(null)
            );
        }

        converters.sort(
            (Converter<?> left, Converter<?> right) -> left.toString().compareTo(right.toString())
        );

        this.treePrintAndCheck(
            (TreePrintable) Converters.collection(converters),
            "ConverterCollection\n" +
                "  TEXT to FormName (walkingkooka.validation.convert.ValidationConverterTextToFormName)\n" +
                "  TEXT to ValidatorSelector (walkingkooka.validation.convert.ValidationConverterTextToValidatorSelector)\n" +
                "  TEXT to ValueType (walkingkooka.validation.convert.ValidationConverterTextToValueType)\n" +
                "  to ValidationCheckbox (walkingkooka.validation.convert.ValidationConverterValidationCheckbox)\n" +
                "  to ValidationChoice (walkingkooka.validation.convert.ValidationConverterToValidationChoice)\n" +
                "  to ValidationChoiceList (walkingkooka.validation.convert.ValidationConverterValidationChoiceList)\n" +
                "  to ValidationErrorList (walkingkooka.validation.convert.ValidationConverterValidationErrorList)\n" +
                "  to ValidatorSelector (walkingkooka.validation.convert.ValidationConverterToValidatorSelector)\n" +
                "  to ValueType (walkingkooka.validation.convert.ValidationConverterToValueType)\n"
        );
    }

    @Override
    public Class<ValidationConvertConverters> type() {
        return ValidationConvertConverters.class;
    }

    @Override
    public boolean canHavePublicTypes(final Method method) {
        return false;
    }
}
