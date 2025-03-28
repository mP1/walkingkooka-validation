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

import walkingkooka.reflect.PublicStaticHelper;

import java.util.List;

/**
 * A collection of {@link Validator} factory methods.
 */
public final class Validators implements PublicStaticHelper {

    /**
     * {@link ValidatorCollection}
     */
    public static <R extends ValidationReference, C extends ValidatorContext<R>> Validator<R, C> collection(final int maxErrors,
                                                                                                            final List<Validator<R, C>> validators) {
        return ValidatorCollection.with(
            maxErrors,
            validators
        );
    }

    /**
     * {@see FakeValidator}
     */
    public static <R extends ValidationReference, C extends ValidatorContext<R>> Validator<R, C> fake() {
        return new FakeValidator<>();
    }

    /**
     * Private constructor to stop creation
     */
    private Validators() {
        throw new UnsupportedOperationException();
    }
}
