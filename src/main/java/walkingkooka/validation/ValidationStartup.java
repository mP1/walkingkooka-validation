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

import walkingkooka.reflect.PublicStaticHelper;
import walkingkooka.tree.json.marshall.JsonNodeContext;

/**
 * Used to force all values types to register their {@link JsonNodeContext#register}
 */
public final class ValidationStartup implements PublicStaticHelper {

    static {
        ValidationChoiceList.EMPTY.toString();
        ValidationErrorList.empty().toString();
        ValidationValueTypeName.ANY_STRING.toString();
    }

    public static void init() {
        // NOP
    }

    private ValidationStartup() {
        throw new UnsupportedOperationException();
    }
}
