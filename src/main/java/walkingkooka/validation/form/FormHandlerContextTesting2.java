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
import walkingkooka.convert.ConverterLikeTesting2;
import walkingkooka.environment.EnvironmentContextTesting2;
import walkingkooka.validation.ValidationReference;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface FormHandlerContextTesting2<C extends FormHandlerContext<R, S>, R extends ValidationReference, S> extends FormHandlerContextTesting,
    ConverterLikeTesting2<C>,
    EnvironmentContextTesting2<C>,
    HasFormTesting {

    // validatorContext.................................................................................................

    @Test
    default void testValidatorContextWithNullReferenceFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext()
                .validatorContext(null)
        );
    }

    // loadFieldValue...................................................................................................

    @Test
    default void testLoadFormFieldValueWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext()
                .loadFormFieldValue(null)
        );
    }

    // saveFieldValue...................................................................................................

    @Test
    default void testSaveFormFieldValuesWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext()
                .saveFormFieldValues(null)
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

    // ConverterLike....................................................................................................

    @Override
    default C createConverterLike() {
        return this.createContext();
    }

    // typeNameSuffix...................................................................................................

    @Override
    default String typeNameSuffix() {
        return FormHandlerContext.class.getSimpleName();
    }
}
