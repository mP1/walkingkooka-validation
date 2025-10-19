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

import walkingkooka.convert.Converter;
import walkingkooka.convert.ConverterContext;
import walkingkooka.reflect.PublicStaticHelper;

/**
 * A collection of {@link Converter}.
 */
public final class ValidationConvertConverters implements PublicStaticHelper {

    /**
     * {@see ValidationConverterHasOptionalValidatorSelector}
     */
    public static <C extends ConverterContext> Converter<C> hasOptionalValidatorSelector() {
        return ValidationConverterHasOptionalValidatorSelector.instance();
    }

    /**
     * {@see ValidationConverterTextToFormName}
     */
    public static <C extends ConverterContext> Converter<C> textToFormName() {
        return ValidationConverterTextToFormName.instance();
    }

    /**
     * {@see ValidationConverterTextToValidatorSelector}
     */
    public static <C extends ConverterContext> Converter<C> textToValidatorSelector() {
        return ValidationConverterTextToValidatorSelector.instance();
    }

    /**
     * {@see ValidationConverterTextToValueTypeName}
     */
    public static <C extends ConverterContext> Converter<C> textToValueTypeName() {
        return ValidationConverterTextToValueTypeName.instance();
    }

    /**
     * {@see ValidationConverterValidationCheckbox}
     */
    public static <C extends ConverterContext> Converter<C> toValidationCheckbox() {
        return ValidationConverterValidationCheckbox.instance();
    }

    /**
     * {@see ValidationConverterToValidationChoice}
     */
    public static <C extends ConverterContext> Converter<C> toValidationChoice() {
        return ValidationConverterToValidationChoice.instance();
    }

    /**
     * {@see ValidationConverterValidationChoiceList}
     */
    public static <C extends ConverterContext> Converter<C> toValidationChoiceList() {
        return ValidationConverterValidationChoiceList.instance();
    }

    /**
     * {@see ValidationConverterValidationErrorList}
     */
    public static <C extends ConverterContext> Converter<C> toValidationErrorList() {
        return ValidationConverterValidationErrorList.instance();
    }

    /**
     * Stop creation
     */
    private ValidationConvertConverters() {
        throw new UnsupportedOperationException();
    }
}
