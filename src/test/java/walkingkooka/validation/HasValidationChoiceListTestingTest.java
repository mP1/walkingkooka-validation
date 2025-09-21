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

import org.junit.jupiter.api.Test;

import java.util.Optional;

public final class HasValidationChoiceListTestingTest implements HasValidationChoiceListTesting {

    @Test
    public void testValidationChoiceListAndCheck() {
        this.validationChoiceListAndCheck(
            new TestHasValidationChoiceList(Optional.empty())
        );
    }

    @Test
    public void testValidationChoiceListAndCheckWithValidationChoiceList() {
        final ValidationChoiceList choices = ValidationChoiceList.EMPTY;

        this.validationChoiceListAndCheck(
            new TestHasValidationChoiceList(
                Optional.of(choices)
            ),
            choices
        );
    }

    @Test
    public void testValidationChoiceListAndCheckWithValidationChoiceList2() {
        final ValidationChoiceList choices = ValidationChoiceList.EMPTY.concat(
            ValidationChoice.with(
                "Label1",
                Optional.empty()
            )
        );

        this.validationChoiceListAndCheck(
            new TestHasValidationChoiceList(
                Optional.of(choices)
            ),
            choices
        );
    }

    private static class TestHasValidationChoiceList implements HasValidationChoiceList {

        TestHasValidationChoiceList(final Optional<ValidationChoiceList> choices) {
            this.choices = choices;
        }

        @Override
        public Optional<ValidationChoiceList> validationChoiceList() {
            return this.choices;
        }

        private final Optional<ValidationChoiceList> choices;
    }
}
