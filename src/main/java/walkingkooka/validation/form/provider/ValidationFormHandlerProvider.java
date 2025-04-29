package walkingkooka.validation.form.provider;

import walkingkooka.collect.list.Lists;
import walkingkooka.collect.set.Sets;
import walkingkooka.net.UrlPath;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.validation.ValidationReference;
import walkingkooka.validation.form.FormHandler;
import walkingkooka.validation.form.FormHandlerContext;
import walkingkooka.validation.form.FormHandlers;

import java.util.List;
import java.util.Objects;

/**
 * A {@link FormHandlerProvider} that uses the {@link FormHandlerProvider} defined in {@link FormHandlers}.
 */
final class ValidationFormHandlerProvider implements FormHandlerProvider {

    /**
     * Singleton
     */
    final static ValidationFormHandlerProvider INSTANCE = new ValidationFormHandlerProvider();

    /**
     * Private ctor use singleton instance
     */
    private ValidationFormHandlerProvider() {
        super();
    }

    // FormHandler......................................................................................................

    @Override
    public <R extends ValidationReference, S, C extends FormHandlerContext<R, S>> FormHandler<R, S, C> formHandler(final FormHandlerSelector selector,
                                                                                                                   final ProviderContext context) {
        Objects.requireNonNull(selector, "selector");
        Objects.requireNonNull(context, "context");

        return selector.evaluateValueText(
            this,
            context
        );
    }

    @Override
    public <R extends ValidationReference, S, C extends FormHandlerContext<R, S>> FormHandler<R, S, C> formHandler(final FormHandlerName name,
                                                   final List<?> values,
                                                   final ProviderContext context) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(values, "values");
        Objects.requireNonNull(context, "context");

        return this.formHandler0(
            name,
            Lists.immutable(values),
            context
        );
    }

    private <R extends ValidationReference, S, C extends FormHandlerContext<R, S>> FormHandler<R, S, C> formHandler0(final FormHandlerName name,
                                                     final List<?> values,
                                                     final ProviderContext context) {
        final int count = values.size();

        final FormHandler<R, S, C> formHandler;

        switch (name.value()) {
            case FormHandlerName.BASIC_STRING:
                if (0 != count) {
                    throw new IllegalArgumentException("Got " + count + " expected 0 values");
                }
                formHandler = FormHandlers.basic();
                break;
            default:
                throw new IllegalArgumentException("Unknown formHandler " + name);
        }

        return formHandler;
    }
    
    @Override
    public FormHandlerInfoSet formHandlerInfos() {
        return INFOS;
    }

    private final static FormHandlerInfoSet INFOS = FormHandlerInfoSet.with(
        Sets.of(
            formHandlerInfo(FormHandlerName.BASIC)
        )
    );

    private static FormHandlerInfo formHandlerInfo(final FormHandlerName name) {
        return FormHandlerInfo.with(
            FormHandlerProviders.BASE_URL.appendPath(UrlPath.parse(name.value())),
            name
        );
    }

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
