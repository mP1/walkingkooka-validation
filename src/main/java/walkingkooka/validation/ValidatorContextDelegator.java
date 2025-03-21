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

public interface ValidatorContextDelegator<T extends ValidationReference> extends ValidatorContext<T>,
    ConverterContextDelegator,
    EnvironmentContextDelegator {

    // ValidatorContextDelegator.......................................................................................

    ValidatorContext<T> validationContext();

    @Override
    default T validationReference() {
        return this.validationContext()
            .validationReference();
    }

    @Override
    default LocalDateTime now() {
        return this.converterContext()
            .now();
    }

    // ConverterContextDelegator........................................................................................

    @Override
    default ConverterContext converterContext() {
        return this.validationContext();
    }

    // EnvironmentContextDelegator......................................................................................

    @Override
    default EnvironmentContext environmentContext() {
        return this.validationContext();
    }
}
