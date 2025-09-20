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
import walkingkooka.Cast;
import walkingkooka.ToStringTesting;

public final class NonNullValidatorTest implements ValidatorTesting2<NonNullValidator<TestValidationReference, TestValidatorContext>, TestValidationReference, TestValidatorContext>,
    ToStringTesting<NonNullValidator<TestValidationReference, TestValidatorContext>> {

    private final static TestValidationReference REFERENCE = new TestValidationReference("HelloField");

    @Test
    public void testValidateNullReturnsError() {
        this.validateAndCheck(
            null,
            this.createContext(),
            ValidationError.with(REFERENCE)
                .setMessage("Missing HelloField")
        );
    }

    @Test
    public void testValidateNonNull() {
        this.validateAndCheck(
            this,
            this.createContext()
        );
    }

    @Override
    public NonNullValidator<TestValidationReference, TestValidatorContext> createValidator() {
        return NonNullValidator.instance();
    }

    @Override
    public TestValidatorContext createContext() {
        return new TestValidatorContext() {

            @Override
            public TestValidationReference validationReference() {
                return REFERENCE;
            }
        };
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            NonNullValidator.instance(),
            "NonNull"
        );
    }

    // class............................................................................................................

    @Override
    public Class<NonNullValidator<TestValidationReference, TestValidatorContext>> type() {
        return Cast.to(NonNullValidator.class);
    }
}
