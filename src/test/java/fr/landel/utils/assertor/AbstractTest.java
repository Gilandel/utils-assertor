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
package fr.landel.utils.assertor;

import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.ComparisonFailure;

import fr.landel.utils.assertor.commons.ConstantsAssertor;
import fr.landel.utils.assertor.commons.ParameterAssertor;
import fr.landel.utils.assertor.commons.ResultAssertor;
import fr.landel.utils.assertor.helper.HelperAssertor;
import fr.landel.utils.commons.CastUtils;
import fr.landel.utils.commons.expect.Expect;
import fr.landel.utils.commons.function.ThrowableSupplier;
import fr.landel.utils.commons.function.TriFunction;

/**
 * Abstract for tests
 *
 * @since Jul 31, 2016
 * @author Gilles
 *
 */
public abstract class AbstractTest extends ConstantsAssertor {

    /**
     * Map an {@link IllegalArgumentException} into and {@link AssertionError}
     */
    public static final BiFunction<String, List<ParameterAssertor<?>>, AssertionError> JUNIT_THROWABLE = (message,
            parameters) -> new AssertionError(message);

    /**
     * Function to manage the creation of Junit exception
     */
    private static final TriFunction<Boolean, String, String, AssertionError> JUNIT_ERROR = (catched, expected, actual) -> {
        if (catched) {
            return new ComparisonFailure("The exception message don't match.", expected, actual);
        } else {
            return new AssertionError("The expected exception never comes up.");
        }
    };

    /**
     * Override a final field value
     * 
     * @param clazz
     *            the class containing the field
     * @param fieldName
     *            the field name, used by reflection to identify it
     * @param object
     *            the object instance
     * @param value
     *            the value to apply
     */
    public static final void setFinalField(final Class<?> clazz, final String fieldName, final Object object, final Object value) {
        try {
            final Field stepType = clazz.getDeclaredField(fieldName);
            // set public
            stepType.setAccessible(true);

            // remove final
            final Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(stepType, stepType.getModifiers() & ~Modifier.FINAL);

            // set the value
            stepType.set(object, value);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Check that the consumed code throws the specified exception.
     * 
     * <pre>
     * assertException(() -&gt; {
     *     // throw new IllegalArgumentException("parameter cannot be null");
     *     getMyType(null);
     * }, IllegalArgumentException.class);
     * </pre>
     * 
     * @param consumer
     *            The consumer (required, not null)
     * @param expectedException
     *            The expected exception type (required, not null)
     * @param <T>
     *            The generic expected exception type
     */
    public static <T extends Throwable> void assertException(final ThrowableSupplier<Throwable> consumer,
            final Class<T> expectedException) {

        Expect.exception(consumer, expectedException, JUNIT_ERROR);
    }

    /**
     * Check that the consumed code raise the specified exception, also check
     * the message.
     * 
     * <pre>
     * assertException(() -&gt; {
     *     // throw new IllegalArgumentException("parameter cannot be null");
     *     getMyType(null);
     * }, IllegalArgumentException.class, "parameter cannot be null");
     * </pre>
     * 
     * @param consumer
     *            The consumer (required, not null)
     * @param expectedException
     *            The expected exception type (required, not null)
     * @param expectedMessage
     *            The expected exception message
     * @param <T>
     *            The generic expected exception type
     */
    public static <T extends Throwable> void assertException(final ThrowableSupplier<Throwable> consumer, final Class<T> expectedException,
            final String expectedMessage) {

        Expect.exception(consumer, expectedException, expectedMessage, JUNIT_ERROR);
    }

    /**
     * Check that the consumed code raise the specified exception, also check
     * the message with the specified pattern.
     * 
     * <pre>
     * assertException(() -&gt; {
     *     // throw new IllegalArgumentException("parameter cannot be null");
     *     getMyType(null);
     * }, IllegalArgumentException.class, Pattern.compile("^parameter");
     * </pre>
     * 
     * @param consumer
     *            The consumer (required, not null)
     * @param expectedException
     *            The expected exception type (required, not null)
     * @param messagePattern
     *            The message pattern
     * @param <T>
     *            The generic expected exception type
     */
    public static <T extends Throwable> void assertException(final ThrowableSupplier<Throwable> consumer, final Class<T> expectedException,
            final Pattern messagePattern) {

        Expect.exception(consumer, expectedException, messagePattern, JUNIT_ERROR);
    }

    public static class AssertorMatcher<T> extends BaseMatcher<T> {

        private final StepAssertor<T> step;
        private ResultAssertor result;

        public <S extends Step<S, T>> AssertorMatcher(final Step<S, T> predicate) {
            super();
            this.step = predicate.getStep();
        }

        @Override
        public boolean matches(final Object item) {
            this.result = HelperAssertor.combine(this.step, CastUtils.cast(item), true, true);
            return this.result.isPrecondition() && this.result.isValid();
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText(HelperAssertor.getMessage(this.result));
        }
    }
}
