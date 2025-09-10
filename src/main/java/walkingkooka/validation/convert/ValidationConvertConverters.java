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
     * {@see HasOptionalValidatorSelectorConverter}
     */
    public static <C extends ConverterContext> Converter<C> hasOptionalValidatorSelector() {
        return HasOptionalValidatorSelectorConverter.instance();
    }

    /**
     * {@see TextToFormNameConverter}
     */
    public static <C extends ConverterContext> Converter<C> textToFormName() {
        return TextToFormNameConverter.instance();
    }

    /**
     * {@see TextToValidationValueTypeNameConverter}
     */
    public static <C extends ConverterContext> Converter<C> textToValidationValueTypeName() {
        return TextToValidationValueTypeNameConverter.instance();
    }

    /**
     * {@see TextToValidatorSelectorConverter}
     */
    public static <C extends ConverterContext> Converter<C> textToValidatorSelector() {
        return TextToValidatorSelectorConverter.instance();
    }

    /**
     * {@see ValidationChoiceListConverter}
     */
    public static <C extends ConverterContext> Converter<C> toValidationChoiceList() {
        return ValidationChoiceListConverter.instance();
    }

    /**
     * {@see ValidationErrorListConverter}
     */
    public static <C extends ConverterContext> Converter<C> toValidationErrorList() {
        return ValidationErrorListConverter.instance();
    }

    /**
     * Stop creation
     */
    private ValidationConvertConverters() {
        throw new UnsupportedOperationException();
    }
}
