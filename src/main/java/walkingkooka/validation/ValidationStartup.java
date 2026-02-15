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

import walkingkooka.plugin.PluginStartup;
import walkingkooka.reflect.PublicStaticHelper;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.validation.form.provider.FormHandlerAliasSet;
import walkingkooka.validation.form.provider.FormHandlerInfoSet;
import walkingkooka.validation.form.provider.FormHandlerSelector;
import walkingkooka.validation.provider.OptionalValidatorSelector;
import walkingkooka.validation.provider.ValidatorAliasSet;
import walkingkooka.validation.provider.ValidatorInfoSet;
import walkingkooka.validation.provider.ValidatorSelector;

/**
 * Used to force all values types to register their {@link JsonNodeContext#register}
 */
public final class ValidationStartup implements PublicStaticHelper {

    static {
        PluginStartup.init();

        ValidationCheckbox.TRUE_FALSE.trueValue();
        ValidationChoiceList.EMPTY.toString();

        ValidationError.NO_VALUE.isPresent();
        ValidationErrorList.empty().toString();

        ValueType.ANY_STRING.toString();

        FormHandlerAliasSet.EMPTY.size();
        FormHandlerInfoSet.EMPTY.size();
        FormHandlerSelector.parse("hello");

        ValidatorAliasSet.EMPTY.size();
        ValidatorInfoSet.EMPTY.size();
        ValidatorSelector.parse("hello");
        OptionalValidatorSelector.EMPTY.value();
    }

    public static void init() {
        // NOP
    }

    private ValidationStartup() {
        throw new UnsupportedOperationException();
    }
}
