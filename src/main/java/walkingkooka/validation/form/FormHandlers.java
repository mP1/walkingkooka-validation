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

import walkingkooka.reflect.PublicStaticHelper;
import walkingkooka.validation.ValidationReference;

/**
 * A collection of factory methods to create {@link FormHandler}
 */
public final class FormHandlers implements PublicStaticHelper {

    /**
     * {@see BasicFormHandler}
     */
    public static <R extends ValidationReference, S, C extends FormHandlerContext<R, S>> FormHandler<R, S, C> instance() {
        return BasicFormHandler.instance();
    }

    /**
     * {@see FakeFormHandler}
     */
    public static <R extends ValidationReference, S, C extends FormHandlerContext<R, S>> FormHandler<R, S, C> fake() {
        return new FakeFormHandler<>();
    }

    /**
     * Stop creation
     */
    private FormHandlers() {
        throw new UnsupportedOperationException();
    }
}
