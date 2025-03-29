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

import org.junit.jupiter.api.Test;
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.net.Url;
import walkingkooka.plugin.PluginAliasLikeTesting;
import walkingkooka.reflect.JavaVisibility;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ValidatorAliasTest implements PluginAliasLikeTesting<ValidatorName, ValidatorSelector, ValidatorAlias> {

    private final static ValidatorName NAME = ValidatorName.with("Hello");

    private final static Optional<ValidatorSelector> SELECTOR = Optional.of(
        ValidatorSelector.parse("validator123")
    );

    private final static Optional<AbsoluteUrl> URL = Optional.of(
        Url.parseAbsolute("https://example.com/validator123")
    );

    // with.............................................................................................................

    @Test
    public void testWithNullNameFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValidatorAlias.with(
                null,
                SELECTOR,
                URL
            )
        );
    }

    @Test
    public void testWithNullSelectorFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValidatorAlias.with(
                NAME,
                null,
                URL
            )
        );
    }

    @Test
    public void testWithNullUrlFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValidatorAlias.with(
                NAME,
                SELECTOR,
                null
            )
        );
    }

    // parse............................................................................................................

    @Test
    public void testParse() {
        this.parseStringAndCheck(
            "alias1 name1 https://example.com",
            ValidatorAlias.with(
                ValidatorName.with("alias1"),
                Optional.of(
                    ValidatorSelector.parse("name1")
                ),
                Optional.of(
                    Url.parseAbsolute("https://example.com")
                )
            )
        );
    }

    @Override
    public ValidatorAlias parseString(final String text) {
        return ValidatorAlias.parse(text);
    }

    // Comparable.......................................................................................................

    @Override
    public ValidatorAlias createComparable() {
        return ValidatorAlias.with(
            NAME,
            SELECTOR,
            URL
        );
    }

    // class............................................................................................................

    @Override
    public Class<ValidatorAlias> type() {
        return ValidatorAlias.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
