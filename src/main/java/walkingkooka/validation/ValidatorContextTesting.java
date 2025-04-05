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

package walkingkooka.validation;

import org.junit.jupiter.api.Test;
import walkingkooka.convert.CanConvertTesting;
import walkingkooka.environment.EnvironmentContextTesting2;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public interface ValidatorContextTesting<C extends ValidatorContext<R>, R extends ValidationReference> extends CanConvertTesting<C>,
    EnvironmentContextTesting2<C> {

    @Test
    default void testSetValidationReferenceWithNullFails() {
        final C context = this.createContext();

        assertThrows(
            NullPointerException.class,
            () -> context.setValidationReference(null)
        );
    }

    @Test
    default void testSetValidationReferenceSame() {
        final C context = this.createContext();

        assertSame(
            context,
            context.setValidationReference(
                context.validationReference()
            )
        );
    }

    default void validationReferenceAndCheck(final C context,
                                             final R expected) {
        this.checkEquals(
            expected,
            context.validationReference(),
            context::toString);
    }

    @Override
    default C createCanConvert() {
        return this.createContext();
    }

    // class............................................................................................................

    @Override
    default String typeNameSuffix() {
        return ValidatorContext.class.getSimpleName();
    }
}
