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

import walkingkooka.text.printer.TreePrintableTesting;

import java.util.Optional;

public interface HasOptionalValueTypeTesting extends TreePrintableTesting {

    ValueType VALUE_TYPE = ValueType.TEXT;

    ValueType DIFFERENT_VALUE_TYPE = ValueType.NUMBER;

    Optional<ValueType> OPTIONAL_VALUE_TYPE = Optional.of(VALUE_TYPE);

    Optional<ValueType> OPTIONAL_DIFFERENT_VALUE_TYPE = Optional.of(DIFFERENT_VALUE_TYPE);

    default void valueTypeAndCheck(final HasOptionalValueType has) {
        this.valueTypeAndCheck(
            has,
            Optional.empty()
        );
    }

    default void valueTypeAndCheck(final HasOptionalValueType has,
                                   final ValueType expected) {
        this.valueTypeAndCheck(
            has,
            Optional.of(expected)
        );
    }

    default void valueTypeAndCheck(final HasOptionalValueType has,
                                   final Optional<ValueType> expected) {
        this.checkEquals(
            expected,
            has.valueType(),
            has::toString
        );
    }
}
