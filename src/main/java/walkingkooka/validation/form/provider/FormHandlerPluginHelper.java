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

final class FormHandlerPluginHelper implements PluginHelper<FormHandlerName,
    FormHandlerInfo,
    FormHandlerInfoSet,
    FormHandlerSelector,
    FormHandlerAlias,
    FormHandlerAliasSet> {

    final static FormHandlerPluginHelper INSTANCE = new FormHandlerPluginHelper();

    private FormHandlerPluginHelper() {
    }

    @Override
    public FormHandlerName name(final String text) {
        return FormHandlerName.with(text);
    }

    @Override
    public Optional<FormHandlerName> parseName(final TextCursor cursor,
                                        final ParserContext context) {
        Objects.requireNonNull(cursor, "cursor");
        Objects.requireNonNull(context, "context");

        return Parsers.initialAndPartCharPredicateString(
            c -> FormHandlerName.isChar(0, c),
            c -> FormHandlerName.isChar(1, c),
            FormHandlerName.MIN_LENGTH, // minLength
            FormHandlerName.MAX_LENGTH // maxLength
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
    public Set<FormHandlerName> names(final Set<FormHandlerName> names) {
        return Sets.immutable(
            Objects.requireNonNull(names, "names")
        );
    }

    @Override
    public Function<FormHandlerName, RuntimeException> unknownName() {
        return n -> new IllegalArgumentException("Unknown formHandler " + n);
    }

    @Override
    public Comparator<FormHandlerName> nameComparator() {
        return Name.comparator(FormHandlerName.CASE_SENSITIVITY);
    }

    @Override
    public FormHandlerInfo info(final AbsoluteUrl url,
                                final FormHandlerName name) {
        return FormHandlerInfo.with(url, name);
    }

    @Override
    public FormHandlerInfo parseInfo(final String text) {
        return FormHandlerInfo.parse(text);
    }

    @Override
    public FormHandlerInfoSet infoSet(final Set<FormHandlerInfo> infos) {
        return FormHandlerInfoSet.with(infos);
    }

    @Override
    public FormHandlerSelector parseSelector(final String text) {
        return FormHandlerSelector.parse(text);
    }

    @Override
    public FormHandlerAlias alias(final FormHandlerName name,
                                  final Optional<FormHandlerSelector> selector,
                                  final Optional<AbsoluteUrl> url) {
        return FormHandlerAlias.with(
            name,
            selector,
            url
        );
    }

    @Override
    public FormHandlerAlias alias(final PluginAlias<FormHandlerName, FormHandlerSelector> pluginAlias) {
        return FormHandlerAlias.with(pluginAlias);
    }

    @Override
    public FormHandlerAliasSet aliasSet(final SortedSet<FormHandlerAlias> aliases) {
        return FormHandlerAliasSet.with(aliases);
    }

    @Override
    public String label() {
        return "formHandler";
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
