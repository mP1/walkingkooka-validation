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
import walkingkooka.convert.ConverterContextDelegator;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentContextDelegator;

import java.time.LocalDateTime;
import java.util.Objects;

final class BasicValidationContext implements ValidationContext,
    ConverterContextDelegator,
    EnvironmentContextDelegator {

    static BasicValidationContext with(final ConverterContext converterContext,
                                       final EnvironmentContext environmentContext) {
        return new BasicValidationContext(
            Objects.requireNonNull(converterContext, "converterContext"),
            Objects.requireNonNull(environmentContext, "environmentContext")
        );
    }

    private BasicValidationContext(final ConverterContext converterContext,
                                   final EnvironmentContext environmentContext) {
        this.converterContext = converterContext;
        this.environmentContext = environmentContext;
    }

    @Override
    public LocalDateTime now() {
        return this.converterContext.now();
    }

    // ConverterContextDelegator........................................................................................

    @Override
    public ConverterContext converterContext() {
        return this.converterContext;
    }

    private final ConverterContext converterContext;

    // EnvironmentContextDelegator......................................................................................

    @Override
    public EnvironmentContext environmentContext() {
        return this.environmentContext;
    }

    private final EnvironmentContext environmentContext;

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.converterContext + " " + this.environmentContext;
    }
}
