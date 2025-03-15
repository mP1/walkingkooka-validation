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

import walkingkooka.convert.ConverterContext;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.reflect.PublicStaticHelper;

import java.util.Objects;

/**
 * A collection of factory methods to create {@link ValidationContext}
 */
public final class ValidationContexts implements PublicStaticHelper {

    /**
     * {@see BasicValidationContext}
     */
    public static ValidationContext basic(final ConverterContext converterContext,
                                          final EnvironmentContext environmentContext) {
        return BasicValidationContext.with(
            converterContext,
            environmentContext
        );
    }

    /**
     * {@see FakeValidationContext}
     */
    public static ValidationContext fake() {
        return new FakeValidationContext();
    }

    /**
     * Stop creation
     */
    private ValidationContexts() {
        throw new UnsupportedOperationException();
    }
}
