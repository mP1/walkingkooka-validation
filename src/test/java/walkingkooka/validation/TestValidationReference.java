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

import walkingkooka.text.CharSequences;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

final class TestValidationReference implements ValidationReference {

    TestValidationReference(final String field) {
        this.field = CharSequences.failIfNullOrEmpty(field, "field");
    }

    @Override
    public String text() {
        return this.field;
    }

    private final String field;

    @Override
    public int hashCode() {
        return this.field.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this ==other || other instanceof TestValidationReference && this.field.equals(((TestValidationReference) other).field);
    }

    @Override
    public String toString() {
        return this.field;
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return JsonNode.string(this.field);
    }

    static TestValidationReference unmarshall(final JsonNode node,
                                              final JsonNodeUnmarshallContext context) {
        return new TestValidationReference(node.stringOrFail());
    }

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(TestValidationReference.class),
            TestValidationReference::unmarshall,
            TestValidationReference::marshall,
            TestValidationReference.class
        );
    }
}
