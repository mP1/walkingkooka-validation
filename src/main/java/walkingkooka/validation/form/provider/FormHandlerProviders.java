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

package walkingkooka.validation.form.provider;

import walkingkooka.net.AbsoluteUrl;
import walkingkooka.net.Url;
import walkingkooka.reflect.PublicStaticHelper;
import walkingkooka.validation.form.FormHandler;

/**
 * A collection of FormHandlerProvider(s).
 */
public final class FormHandlerProviders implements PublicStaticHelper {

    /**
     * This is the base {@link AbsoluteUrl} for all {@link FormHandler} in this package. The name of each
     * form handler will be appended to this base.
     */
    public final static AbsoluteUrl BASE_URL = Url.parseAbsolute(
        "https://github.com/mP1/walkingkooka-validation/" + FormHandler.class.getSimpleName()
    );

    /**
     * {@see AliasesFormHandlerProvider}
     */
    public static FormHandlerProvider aliases(final FormHandlerAliasSet aliases,
                                              final FormHandlerProvider provider) {
        return AliasesFormHandlerProvider.with(
            aliases,
            provider
        );
    }

    /**
     * {@see EmptyFormHandlerProvider}
     */
    public static FormHandlerProvider empty() {
        return EmptyFormHandlerProvider.INSTANCE;
    }

    /**
     * {@see FakeFormHandlerProvider}
     */
    public static FormHandlerProvider fake() {
        return new FakeFormHandlerProvider();
    }

    /**
     * {@see FilteredFormHandlerProvider}
     */
    public static FormHandlerProvider filtered(final FormHandlerProvider provider,
                                               final FormHandlerInfoSet infos) {
        return FilteredFormHandlerProvider.with(
            provider,
            infos
        );
    }

    /**
     * {@see FilteredMappedFormHandlerProvider}
     */
    public static FormHandlerProvider filteredMapped(final FormHandlerInfoSet infos,
                                                     final FormHandlerProvider provider) {
        return FilteredMappedFormHandlerProvider.with(
            infos,
            provider
        );
    }

    /**
     * {@see MergedMappedFormHandlerProvider}
     */
    public static FormHandlerProvider mergedMapped(final FormHandlerInfoSet infos,
                                                   final FormHandlerProvider provider) {
        return MergedMappedFormHandlerProvider.with(
            infos,
            provider
        );
    }

    /**
     * {@see ValidationFormHandlerProvider}
     */
    public static FormHandlerProvider validation() {
        return ValidationFormHandlerProvider.INSTANCE;
    }

    /**
     * Stop creation
     */
    private FormHandlerProviders() {
        throw new UnsupportedOperationException();
    }
}
