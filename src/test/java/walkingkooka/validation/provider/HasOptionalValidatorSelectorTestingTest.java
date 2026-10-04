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

package walkingkooka.validation.provider;

import org.junit.jupiter.api.Test;
import walkingkooka.reflect.PublicClassTesting;

public final class HasOptionalValidatorSelectorTestingTest implements HasOptionalValidatorSelectorTesting,
    PublicClassTesting<HasOptionalValidatorSelectorTesting> {

    @Test
    public void testConstants() {
        this.checkNotEquals(
            VALIDATOR_SELECTOR,
            DIFFERENT_VALIDATOR_SELECTOR
        );
    }

    @Test
    public void testOptionalConstants() {
        this.checkNotEquals(
            OPTIONAL_VALIDATOR_SELECTOR,
            OPTIONAL_DIFFERENT_VALIDATOR_SELECTOR
        );
    }

    // class............................................................................................................

    @Override
    public Class<HasOptionalValidatorSelectorTesting> type() {
        return HasOptionalValidatorSelectorTesting.class;
    }
}
