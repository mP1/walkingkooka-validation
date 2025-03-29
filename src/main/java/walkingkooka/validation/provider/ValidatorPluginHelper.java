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

import walkingkooka.collect.set.Sets;
import walkingkooka.naming.Name;
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.plugin.PluginAlias;
import walkingkooka.plugin.PluginHelper;
import walkingkooka.text.cursor.TextCursor;
import walkingkooka.text.cursor.parser.ParserContext;
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.text.cursor.parser.Parsers;
import walkingkooka.text.cursor.parser.StringParserToken;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Function;

final class ValidatorPluginHelper implements PluginHelper<ValidatorName,
    ValidatorInfo,
    ValidatorInfoSet,
    ValidatorSelector,
    ValidatorAlias,
    ValidatorAliasSet> {

    final static ValidatorPluginHelper INSTANCE = new ValidatorPluginHelper();

    private ValidatorPluginHelper() {
    }

    @Override
    public ValidatorName name(final String text) {
        return ValidatorName.with(text);
    }

    @Override
    public Optional<ValidatorName> parseName(final TextCursor cursor,
                                             final ParserContext context) {
        Objects.requireNonNull(cursor, "cursor");
        Objects.requireNonNull(context, "context");

        return Parsers.initialAndPartCharPredicateString(
            c -> ValidatorName.isChar(0, c),
            c -> ValidatorName.isChar(1, c),
            ValidatorName.MIN_LENGTH, // minLength
            ValidatorName.MAX_LENGTH // maxLength
        ).parse(
            cursor,
            context
        ).map(
            (final ParserToken token) -> this.name(
                token.cast(StringParserToken.class).value()
            )
        );
    }

    @Override
    public Set<ValidatorName> names(final Set<ValidatorName> names) {
        return Sets.immutable(
            Objects.requireNonNull(names, "names")
        );
    }

    @Override
    public Function<ValidatorName, RuntimeException> unknownName() {
        return n -> new IllegalArgumentException("Unknown validator " + n);
    }

    @Override
    public Comparator<ValidatorName> nameComparator() {
        return Name.comparator(ValidatorName.CASE_SENSITIVITY);
    }

    @Override
    public ValidatorInfo info(final AbsoluteUrl url,
                              final ValidatorName name) {
        return ValidatorInfo.with(url, name);
    }

    @Override
    public ValidatorInfo parseInfo(final String text) {
        return ValidatorInfo.parse(text);
    }

    @Override
    public ValidatorInfoSet infoSet(final Set<ValidatorInfo> infos) {
        return ValidatorInfoSet.with(infos);
    }

    @Override
    public ValidatorSelector parseSelector(final String text) {
        return ValidatorSelector.parse(text);
    }

    @Override
    public ValidatorAlias alias(final ValidatorName name,
                                final Optional<ValidatorSelector> selector,
                                final Optional<AbsoluteUrl> url) {
        return ValidatorAlias.with(
            name,
            selector,
            url
        );
    }

    @Override
    public ValidatorAlias alias(final PluginAlias<ValidatorName, ValidatorSelector> pluginAlias) {
        return ValidatorAlias.with(pluginAlias);
    }

    @Override
    public ValidatorAliasSet aliasSet(final SortedSet<ValidatorAlias> aliases) {
        return ValidatorAliasSet.with(aliases);
    }

    @Override
    public String label() {
        return "validator";
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
