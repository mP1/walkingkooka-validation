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

package walkingkooka.validation.form.store;

import walkingkooka.store.Store;
import walkingkooka.store.Stores;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.form.Form;
import walkingkooka.validation.form.FormName;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

final class TreeFormStore<R extends ValidationReference & Comparable<R>> implements FormStore<R> {

    static <R extends ValidationReference & Comparable<R>> TreeFormStore<R> empty() {
        return new TreeFormStore<>();
    }

    private TreeFormStore() {
        this.store = Stores.treeMap(
            Comparator.naturalOrder(),
            this::idSetter
        );
    }

    private Form<R> idSetter(final FormName name,
                             final Form<R> form) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Form<R>> load(final FormName form) {
        return this.store.load(form);
    }

    @Override
    public Form<R> save(final Form<R> form) {
        Objects.requireNonNull(form, "form");

        return this.store.save(form);
    }

    @Override
    public Runnable addSaveWatcher(final Consumer<Form<R>> watcher) {
        Objects.requireNonNull(watcher, "watcher");

        return this.store.addSaveWatcher(
            (f) -> watcher.accept(f)
        );
    }

    @Override
    public void delete(final FormName form) {
        this.store.delete(form);
    }

    @Override
    public Runnable addDeleteWatcher(final Consumer<FormName> watcher) {
        return this.store.addDeleteWatcher(watcher);
    }

    @Override
    public int count() {
        return this.store.count();
    }


    @Override
    public Set<FormName> ids(final int offset,
                             final int count) {
        return this.store.ids(
            offset,
            count
        );
    }

    @Override
    public List<Form<R>> values(final int offset,
                                final int count) {
        return this.store.values(
            offset,
            count
        );
    }

    @Override
    public List<Form<R>> between(final FormName from,
                                 final FormName to) {
        return this.store.between(
            from,
            to
        );
    }

    private final Store<FormName, Form<R>> store;

    @Override
    public String toString() {
        return this.store.toString();
    }
}
