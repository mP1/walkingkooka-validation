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

package walkingkooka.validation.form;

import walkingkooka.collect.set.Sets;
import walkingkooka.validation.ValidationReference;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * A {@link IllegalArgumentException} that may be used to report duplicate {@link ValidationReference}.
 */
public class DuplicateFormFieldReferencesException extends IllegalArgumentException {

    private static final long serialVersionUID = 1L;

    public DuplicateFormFieldReferencesException(final Set<? extends ValidationReference> references) {
        this.references = Sets.immutable(references);
    }

    @Override
    public String getMessage() {
        return "Form contains duplicate field references: " + this.references.stream()
            .map(ValidationReference::toString)
            .collect(Collectors.joining(", "));
    }

    /**
     * Getter that returns duplicated {@link ValidationReference}.
     */
    public Set<? extends ValidationReference> references() {
        return this.references;
    }

    private final Set<? extends ValidationReference> references;
}
