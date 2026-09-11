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

import walkingkooka.Either;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentContextDelegator;
import walkingkooka.validation.TestValidationReference;
import walkingkooka.validation.ValidatorContext;
import walkingkooka.validation.form.FormHandlerContextTesting2Test.TestFormHandlerContext;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class FormHandlerContextTesting2Test implements FormHandlerContextTesting2<TestFormHandlerContext, TestValidationReference, Void> {

    @Override
    public void testEnvironmentContext() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testEnvironmentValueLineEndingEqualsLineEnding() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testEnvironmentValueLocaleEqualsLocale() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testEnvironmentValueNowEqualsNow() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testEnvironmentValueUserEqualsUser() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testRemoveEnvironmentValueWithNowFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetEnvironmentValueWithNowFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetCurrencyWithDifferentAndWatcher() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetIndentationWithDifferentAndWatcher() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetLineEndingWithDifferentAndWatcher() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetLocaleWithDifferent() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetLocaleWithDifferentAndWatcher() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetTimeOffsetWithDifferentAndWatcher() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetUserWithDifferentAndWatcher() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TestFormHandlerContext createContext() {
        return new TestFormHandlerContext();
    }

    @Override
    public Class<TestFormHandlerContext> type() {
        return TestFormHandlerContext.class;
    }

    @Override
    public void testTestNaming() {
        throw new UnsupportedOperationException();
    }

    static class TestFormHandlerContext implements FormHandlerContext<TestValidationReference, Void>,
        EnvironmentContextDelegator {

        @Override
        public Form<TestValidationReference> form() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Comparator<TestValidationReference> formFieldReferenceComparator() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ValidatorContext<TestValidationReference> validatorContext(final TestValidationReference reference) {
            Objects.requireNonNull(reference, "reference");

            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<Object> loadFormFieldValue(final TestValidationReference reference) {
            Objects.requireNonNull(reference, "reference");
            throw new UnsupportedOperationException();
        }

        @Override
        public Void saveFormFieldValues(final List<FormField<TestValidationReference>> formFields) {
            Objects.requireNonNull(formFields, "formFields");

            throw new UnsupportedOperationException();
        }

        @Override
        public boolean canConvert(final Object value,
                                  final Class<?> type) {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> Either<T, String> convert(final Object value,
                                             final Class<T> type) {
            throw new UnsupportedOperationException();
        }

        @Override
        public FormHandlerContext<TestValidationReference, Void> cloneEnvironment() {
            throw new UnsupportedOperationException();
        }

        @Override
        public FormHandlerContext<TestValidationReference, Void> setEnvironmentContext(final EnvironmentContext environmentContext) {
            Objects.requireNonNull(environmentContext, "environmentContext");

            return new TestFormHandlerContext();
        }

        // EnvironmentContextDelegator..................................................................................

        @Override
        public EnvironmentContext environmentContext() {
            return this.environmentContext;
        }

        private final EnvironmentContext environmentContext = ENVIRONMENT_CONTEXT.cloneEnvironment();

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }
}
