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

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface FormHandlerContextTesting<C extends FormHandlerContext<R>, R extends ValidationReference> extends CanConvertTesting<C>,
    EnvironmentContextTesting2<C> {

    // form.............................................................................................................

    default void fieldValueAndCheck(final FormHandlerContext<R> context,
                                    final Form<R> expected) {
        this.checkEquals(
            expected,
            context.form()
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
}
