/*
 * Copyright 2024 Miroslav Pokorny (github.com/mP1)
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

import walkingkooka.plugin.PluginHelperTesting;
import walkingkooka.reflect.JavaVisibility;

public final class ValidatorPluginHelperTest implements PluginHelperTesting<ValidatorPluginHelper,
    ValidatorName,
    ValidatorInfo,
    ValidatorInfoSet,
    ValidatorSelector,
    ValidatorAlias,
    ValidatorAliasSet> {

    @Override
    public ValidatorPluginHelper createPluginHelper() {
        return ValidatorPluginHelper.INSTANCE;
    }

    @Override
    public ValidatorName createName() {
        return ValidatorName.COLLECTION;
    }

    // class............................................................................................................

    @Override
    public Class<ValidatorPluginHelper> type() {
        return ValidatorPluginHelper.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
