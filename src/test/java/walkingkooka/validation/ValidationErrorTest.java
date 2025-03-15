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
import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.ToStringTesting;
import walkingkooka.reflect.ClassTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.text.printer.TreePrintableTesting;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ValidationErrorTest implements HashCodeEqualsDefinedTesting2<ValidationError>,
    ToStringTesting<ValidationError>,
    ClassTesting<ValidationError>,
    TreePrintableTesting {

    private final static ValidationReference REFERENCE = new ValidationReference() {
        @Override
        public String text() {
            return "Hello";
        }

        @Override
        public String toString() {
            return this.text();
        }
    };

    private final static String MESSAGE = "Error too many xyz";

    private final Optional<Object> VALUE = Optional.of("Value999");

    // with.............................................................................................................

    @Test
    public void testWithNullReferenceFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValidationError.with(
                null,
                MESSAGE,
                VALUE
            )
        );
    }

    @Test
    public void testWithNullMessageFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValidationError.with(
                REFERENCE,
                null,
                VALUE
            )
        );
    }

    @Test
    public void testWithEmptyMessageFails() {
        assertThrows(
            IllegalArgumentException.class,
            () -> ValidationError.with(
                REFERENCE,
                "",
                VALUE
            )
        );
    }

    @Test
    public void testWithWhitespaceMessageFails() {
        assertThrows(
            IllegalArgumentException.class,
            () -> ValidationError.with(
                REFERENCE,
                " ",
                VALUE
            )
        );
    }

    @Test
    public void testWithNullValueFails() {
        assertThrows(
            NullPointerException.class,
            () -> ValidationError.with(
                REFERENCE,
                MESSAGE,
                null
            )
        );
    }

    @Test
    public void testWith() {
        final ValidationError error = ValidationError.with(
            REFERENCE,
            MESSAGE,
            VALUE
        );

        this.checkEquals(
            REFERENCE,
            error.reference(),
            "reference"
        );
        this.checkEquals(
            MESSAGE,
            error.message(),
            "message"
        );
        this.checkEquals(
            VALUE,
            error.value(),
            "value"
        );
    }

    // class............................................................................................................

    @Test
    public void testEqualsDifferentReference() {
        this.checkNotEquals(
            ValidationError.with(
                new ValidationReference() {
                    @Override
                    public String text() {
                        return "different";
                    }
                },
                MESSAGE,
                VALUE
            )
        );
    }

    @Test
    public void testEqualsDifferentMessage() {
        this.checkNotEquals(
            ValidationError.with(
                REFERENCE,
                "different",
                VALUE
            )
        );
    }

    @Test
    public void testEqualsDifferentValue() {
        this.checkNotEquals(
            ValidationError.with(
                REFERENCE,
                MESSAGE,
                Optional.of("different")
            )
        );
    }

    @Override
    public ValidationError createObject() {
        return ValidationError.with(
            REFERENCE,
            MESSAGE,
            VALUE
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createObject(),
            "Hello \"Error too many xyz\" \"Value999\""
        );
    }

    // TreePrintable....................................................................................................

    @Test
    public void testTreePrintWithoutValue() {
        this.treePrintAndCheck(
            ValidationError.with(
                REFERENCE,
                MESSAGE,
                Optional.empty()
            ),
            "ValidationError\n" +
                "  HelloError too many xyz\n"
        );
    }

    @Test
    public void testTreePrintWithValue() {
        this.treePrintAndCheck(
            ValidationError.with(
                REFERENCE,
                MESSAGE,
                VALUE
            ),
            "ValidationError\n" +
                "  HelloError too many xyz\n" +
                "      Value999"
        );
    }

    // class............................................................................................................

    @Override
    public Class<ValidationError> type() {
        return ValidationError.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
