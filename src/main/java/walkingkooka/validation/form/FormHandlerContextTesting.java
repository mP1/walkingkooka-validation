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

package walkingkooka.validation.form;

import org.junit.jupiter.api.Test;
import walkingkooka.convert.CanConvertTesting;
import walkingkooka.environment.EnvironmentContextTesting2;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.ValidatorContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface FormHandlerContextTesting<C extends FormHandlerContext<R, S>, R extends ValidationReference, S> extends CanConvertTesting<C>,
    EnvironmentContextTesting2<C>,
    HasFormTesting<R> {

    // validatorContext.................................................................................................

    @Test
    default void testValidatorContextWithNullReferenceFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext()
                .validatorContext(null)
        );
    }

    default void validatorContextAndCheck(final FormHandlerContext<R, S> context,
                                          final R reference,
                                          final ValidatorContext<R> expected) {
        this.checkEquals(
            expected,
            context.validatorContext(reference),
            () -> "ValidatorContext for " + reference
        );
    }

    // loadFieldValue...................................................................................................

    @Test
    default void testLoadFieldValueWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext()
                .loadFieldValue(null)
        );
    }

    default void loadFieldValueAndCheck(final C context,
                                        final R reference) {
        this.loadFieldValueAndCheck(
            context,
            reference,
            Optional.empty()
        );
    }

    default void loadFieldValueAndCheck(final C context,
                                        final R reference,
                                        final Object expected) {
        this.loadFieldValueAndCheck(
            context,
            reference,
            Optional.of(expected)
        );
    }

    default void loadFieldValueAndCheck(final C context,
                                        final R reference,
                                        final Optional<?> expected) {
        this.checkEquals(
            expected,
            context.loadFieldValue(reference)
        );
    }

    // saveFieldValue...................................................................................................

    @Test
    default void testSaveFieldValuesWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext()
                .saveFieldValues(null)
        );
    }

    default void saveFieldValuesAndCheck(final C context,
                                         final List<FormField<R>> formFields,
                                         final S expected) {
        this.checkEquals(
            expected,
            context.saveFieldValues(formFields)
        );
    }

    // validateFormFields...............................................................................................

    @Test
    default void testValidateFormWithNullFormFieldsFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext().validatorContext(null)
        );
    }

    // CanConvert.......................................................................................................

    @Override
    default C createCanConvert() {
        return this.createContext();
    }

    // typeNameSuffix...................................................................................................

    @Override
    default String typeNameSuffix() {
        return FormHandlerContext.class.getSimpleName();
    }
}
