package walkingkooka.validation.provider;

import walkingkooka.Cast;
import walkingkooka.Value;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.Objects;
import java.util.Optional;

/**
 * A typed {@link Optional} necessary because generic types are lost in java.
 */
public final class OptionalValidatorSelector implements Value<Optional<ValidatorSelector>> {

    public final static OptionalValidatorSelector EMPTY = new OptionalValidatorSelector(Optional.empty());

    public static OptionalValidatorSelector with(final Optional<ValidatorSelector> value) {
        Objects.requireNonNull(value, "value");

        return value.isPresent() ?
            new OptionalValidatorSelector(value) :
            EMPTY;
    }

    private OptionalValidatorSelector(final Optional<ValidatorSelector> value) {
        this.value = value;
    }

    @Override
    public Optional<ValidatorSelector> value() {
        return this.value;
    }

    private final Optional<ValidatorSelector> value;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof OptionalValidatorSelector &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final OptionalValidatorSelector other) {
        return this.value.equals(other.value);
    }

    @Override
    public String toString() {
        return this.value.map(ValidatorSelector::toString)
            .orElse("");
    }

    // json.............................................................................................................

    static OptionalValidatorSelector unmarshall(final JsonNode node,
                                                final JsonNodeUnmarshallContext context) {
        return with(
            context.unmarshallOptional(
                node,
                ValidatorSelector.class
            )
        );
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return context.marshallOptional(this.value);
    }

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(OptionalValidatorSelector.class),
            OptionalValidatorSelector::unmarshall,
            OptionalValidatorSelector::marshall,
            OptionalValidatorSelector.class
        );
    }
}
