/*-
 * #%L
 * utils-assertor
 * %%
 * Copyright (C) 2016 - 2018 Gilles Landel
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package fr.landel.utils.assertor.helper;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.commons.ConstantsAssertor;
import fr.landel.utils.assertor.commons.ParameterAssertor;
import fr.landel.utils.assertor.commons.ResultAssertor;
import fr.landel.utils.commons.Result;
import fr.landel.utils.commons.StringUtils;
import fr.landel.utils.commons.function.ConsumerThrowable;

/**
 * Helper end class, to build end steps
 *
 * @since Apr 11, 2017
 * @author Gilles
 *
 */
public final class HelperEnd {

    private static final String MISSING_FUNCTION = "function";
    private static final String MISSING_SUPPLIER_EXCEPTION = "exceptionSupplier";

    public static <T> Result<T> asResult(final StepAssertor<T> step) {
        final ResultAssertor result = HelperAssertor.combine(step, false);

        if (!result.isPrecondition() || !result.isValid()) {
            return Result.empty();
        }

        return Result.ofNullable(HelperAssertor.getLastChecked(result.getParameters()));
    }

    public static <T> boolean isOK(final StepAssertor<T> step) {
        final ResultAssertor result = HelperAssertor.combine(step, false);
        return result.isPrecondition() && result.isValid();
    }

    public static <T> Optional<String> getErrors(final StepAssertor<T> step) {
        final String message = HelperAssertor.getMessage(HelperAssertor.combine(step, true));

        if (StringUtils.isNotEmpty(message)) {
            return Optional.of(message);
        }
        return Optional.empty();
    }

    public static <T> T getNullable(final StepAssertor<T> step) {
        final ResultAssertor result = HelperAssertor.combine(step, false);

        T value = null;
        if (result.isPrecondition() && result.isValid()) {
            value = HelperAssertor.getLastChecked(result.getParameters());
        }
        return value;
    }

    public static <T> T orElseThrow(final StepAssertor<T> step, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return orElseThrow(step, message == null, result -> {
            final String error;
            if (message != null) {
                error = HelperMessage.getMessage(ConstantsAssertor.DEFAULT_ASSERTION, locale, message, result.getParameters(), arguments);
            } else {
                error = HelperAssertor.getMessage(result);
            }
            throw new IllegalArgumentException(error);
        });
    }

    public static <T, E extends Throwable> T orElseThrow(final StepAssertor<T> step,
            final BiFunction<String, List<ParameterAssertor<?>>, E> function) throws E {
        Objects.requireNonNull(function, MISSING_FUNCTION);

        return orElseThrow(step, true, result -> {
            final E exception = function.apply(HelperAssertor.getMessage(result), result.getParameters());
            exception.addSuppressed(new IllegalArgumentException(HelperAssertor.getMessage(result)));

            throw exception;
        });
    }

    public static <T, E extends Throwable> T orElseThrow(final StepAssertor<T> step, final Supplier<E> exceptionSupplier) throws E {
        Objects.requireNonNull(exceptionSupplier, MISSING_SUPPLIER_EXCEPTION);

        return orElseThrow(step, false, result -> {
            throw exceptionSupplier.get();
        });
    }

    public static <T, E extends Throwable> T orElseThrow(final StepAssertor<T> step, final E exception, final boolean injectSuppressed)
            throws E {
        return orElseThrow(step, exception == null || injectSuppressed, result -> {
            if (exception != null) {
                if (injectSuppressed) {
                    exception.addSuppressed(new IllegalArgumentException(HelperAssertor.getMessage(result)));
                }
                throw exception;
            } else {
                throw new IllegalArgumentException(HelperAssertor.getMessage(result));
            }
        });
    }

    private static <T, E extends Throwable> T orElseThrow(final StepAssertor<T> step, final boolean loadMessage,
            final ConsumerThrowable<ResultAssertor, E> consumer) throws E {
        final ResultAssertor result = HelperAssertor.combine(step, loadMessage);

        if (!result.isPrecondition() || !result.isValid()) {
            consumer.acceptThrows(result);
        }

        return HelperAssertor.getLastChecked(result.getParameters());
    }
}
