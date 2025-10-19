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

package walkingkooka.validation.provider;

import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.map.Maps;
import walkingkooka.naming.Name;
import walkingkooka.net.http.server.hateos.HateosResourceName;
import walkingkooka.plugin.PluginName;
import walkingkooka.plugin.PluginNameLike;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.validation.Validator;
import walkingkooka.validation.Validators;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * The {@link Name} of a {@link Validator}. Note comparator names are case-sensitive.
 */
final public class ValidatorName implements PluginNameLike<ValidatorName> {

    public static final String HATEOS_RESOURCE_NAME_STRING = "validator";

    public static final HateosResourceName HATEOS_RESOURCE_NAME = HateosResourceName.with(HATEOS_RESOURCE_NAME_STRING);

    public static boolean isChar(final int pos,
                                 final char c) {
        return PluginName.isChar(pos, c);
    }

    /**
     * The minimum valid length
     */
    public final static int MIN_LENGTH = 1;

    /**
     * The maximum valid length
     */
    public final static int MAX_LENGTH = PluginName.MAX_LENGTH;

    // constants........................................................................................................

    private static ValidatorName registerConstantName(final String name,
                                                      final BiFunction<List<?>, ProviderContext, Validator<?, ?>> factory) {
        final ValidatorName validatorName = new ValidatorName(name);
        NAME_TO_FACTORY.put(
            validatorName,
            factory
        );
        return validatorName;
    }

    /**
     * Holds all constants in a {@link Set}.
     */
    final static Map<ValidatorName, BiFunction<List<?>, ProviderContext, Validator<?, ?>>> NAME_TO_FACTORY = Maps.sorted();

    private final static String ABSOLUTE_URL_STRING = "absolute-url";

    /**
     * The name of the {@link Validator} returned by {@link Validators#absoluteUrl()}.
     */
    public final static ValidatorName ABSOLUTE_URL = registerConstantName(
        ABSOLUTE_URL_STRING,
        (p, c) -> Validators.absoluteUrl()
    );

    private final static String CHECKBOX_STRING = "checkbox";

    /**
     * The name of the {@link Validator} returned by {@link Validators#checkbox(Expression)}.
     */
    public final static ValidatorName CHECKBOX = registerConstantName(
        CHECKBOX_STRING,
        (p, c) -> Validators.checkbox(
            c.convertOrFail(
                p.get(0),
                Expression.class
            )
        )
    );
    
    private final static String CHOICE_LIST_STRING = "choice-list";

    /**
     * The name of the {@link Validator} returned by {@link Validators#choiceList(Expression, String)}.
     */
    public final static ValidatorName CHOICE_LIST = registerConstantName(
        CHOICE_LIST_STRING,
        (p, c) -> Validators.choiceList(
            c.convertOrFail(
                p.get(0),
                Expression.class
            ),
            c.convertOrFail(
                p.get(1),
                String.class
            )
        )
    );

    private final static String COLLECTION_STRING = "collection";

    /**
     * The name of the {@link Validator} returned by {@link Validators#collection(int, List)}
     */
    public final static ValidatorName COLLECTION = registerConstantName(
        COLLECTION_STRING,
        (p, c) -> {
            boolean first = true;
            int max = 0;
            final List<Validator<?, ?>> validators = Lists.array();

            for (final Object parameter : p) {
                if (first) {
                    if (false == parameter instanceof Number) {
                        throw new IllegalArgumentException("Expected number got " + parameter);
                    }
                    first = false;
                    max = ((Number) parameter).intValue();
                } else {
                    if (false == parameter instanceof Validator) {
                        throw new IllegalArgumentException("Expected only validator(s) got " + parameter);
                    }
                    validators.add((Validator<?, ?>) parameter);
                }
            }
            return Validators.collection(
                max,
                Cast.to(validators)
            );
        }
    );

    private final static String EMAIL_ADDRESS_STRING = "email-address";

    /**
     * The name of the {@link Validator} returned by {@link Validators#emailAddress()} ()}.
     */
    public final static ValidatorName EMAIL_ADDRESS = registerConstantName(
        EMAIL_ADDRESS_STRING,
        (p, c) -> Validators.emailAddress()
    );

    private final static String EXPRESSION_STRING = "expression";

    /**
     * The name of the {@link Validator} returned by {@link Validators#expression(Expression)}.
     */
    public final static ValidatorName EXPRESSION = registerConstantName(
        EXPRESSION_STRING,
        (p, c) -> Validators.expression(
            c.convertOrFail(
                p.get(0),
                Expression.class
            )
        )
    );

    private final static String NON_NULL_STRING = "non-null";

    /**
     * The name of the {@link Validator} returned by {@link Validators#nonNull()}.
     */
    public final static ValidatorName NON_NULL = registerConstantName(
        NON_NULL_STRING,
        (p, c) -> Validators.nonNull()
    );

    private final static String TEXT_LENGTH_STRING = "text-length";

    /**
     * The name of the {@link Validator} returned by {@link Validators#nonNull()}.
     */
    public final static ValidatorName TEXT_LENGTH = registerConstantName(
        TEXT_LENGTH_STRING,
        (p, c) -> Validators.textLength(
            c.convertOrFail(
                p.get(0),
                Integer.class
            ),
            c.convertOrFail(
                p.get(1),
                Integer.class
            )
        )
    );

    private final static String TEXT_MASK_STRING = "text-mask";

    /**
     * The name of the {@link Validator} returned by {@link Validators#textMask(String)}.
     */
    public final static ValidatorName TEXT_MASK = registerConstantName(
        TEXT_MASK_STRING,
        (p, c) -> Validators.textMask(
            c.convertOrFail(
                p.get(0),
                String.class
            )
        )
    );

    /**
     * Factory that creates a {@link ValidatorName}
     */
    public static ValidatorName with(final String name) {
        Objects.requireNonNull(name, "name");

        final ValidatorName validatorName;

        switch (name) {
            case ABSOLUTE_URL_STRING:
                validatorName = ABSOLUTE_URL;
                break;
            case CHECKBOX_STRING:
                validatorName = CHECKBOX;
                break;
            case CHOICE_LIST_STRING:
                validatorName = CHOICE_LIST;
                break;
            case COLLECTION_STRING:
                validatorName = COLLECTION;
                break;
            case EMAIL_ADDRESS_STRING:
                validatorName = EMAIL_ADDRESS;
                break;
            case EXPRESSION_STRING:
                validatorName = EXPRESSION;
                break;
            case NON_NULL_STRING:
                validatorName = NON_NULL;
                break;
            case TEXT_LENGTH_STRING:
                validatorName = TEXT_LENGTH;
                break;
            case TEXT_MASK_STRING:
                validatorName = TEXT_MASK;
                break;
            default:
                validatorName = new ValidatorName(name);
                break;
        }

        return validatorName;
    }

    /**
     * Private constructor
     */
    private ValidatorName(final String name) {
        super();
        this.name = PluginName.with(name);
    }

    @Override
    public String value() {
        return this.name.value();
    }

    private final PluginName name;

    // Object..................................................................................................

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof ValidatorName &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final ValidatorName other) {
        return this.compareTo(other) == 0;
    }

    @Override
    public String toString() {
        return this.name.toString();
    }

    // Json.............................................................................................................

    static ValidatorName unmarshall(final JsonNode node,
                                    final JsonNodeUnmarshallContext context) {
        return with(node.stringOrFail());
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return JsonNode.string(this.toString());
    }

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(ValidatorName.class),
            ValidatorName::unmarshall,
            ValidatorName::marshall,
            ValidatorName.class
        );
    }
}
