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
package fr.landel.utils.assertor.predicate;

import java.util.Locale;
import java.util.Map;

import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.commons.MessageAssertor;
import fr.landel.utils.assertor.helper.HelperStep;
import fr.landel.utils.assertor.utils.AssertorMap;

/**
 * This class define methods that can be applied on the checked {@link Map}
 * object. To provide a result, it's also provide a chain builder by returning a
 * {@link PredicateStepMap}. The chain looks like:
 * 
 * <pre>
 * {@link PredicateAssertorStepMap} &gt; {@link PredicateStepMap} &gt; {@link PredicateAssertorStepMap} &gt; {@link PredicateStepMap}...
 * </pre>
 * 
 * This chain always starts with a {@link PredicateAssertorStepMap} and ends
 * with {@link PredicateStepMap}.
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 */
@FunctionalInterface
public interface PredicateAssertorStepMap<K, V> extends PredicateAssertorStep<PredicateStepMap<K, V>, Map<K, V>> {

    /**
     * {@inheritDoc}
     */
    @Override
    default PredicateStepMap<K, V> get(final StepAssertor<Map<K, V>> result) {
        return () -> result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default PredicateAssertorStepMap<K, V> not() {
        return () -> HelperStep.not(getStep());
    }

    /**
     * Asserts that the given {@link Map} has the specified {@code size}.
     * 
     * <p>
     * precondition: {@link Map} cannot be {@code null} and size cannot be lower
     * than zero
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapSize = Assertor.&lt;String, Integer&gt; ofMap().hasSize(size);
     * checkMapSize.that(map).orElseThrow();
     * </pre>
     * 
     * @param size
     *            The wanted size
     * @return The operator
     * @category no_message
     */
    default PredicateStepMap<K, V> hasSize(final int size) {
        return this.hasSize(size, null);
    }

    /**
     * Asserts that the given {@link Map} has the specified {@code size}.
     * 
     * <p>
     * precondition: {@link Map} cannot be {@code null} and size cannot be lower
     * than zero
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapSize = Assertor.&lt;String, Integer&gt; ofMap().hasSize(size, "bad size");
     * checkMapSize.that(map).orElseThrow();
     * </pre>
     * 
     * @param size
     *            The wanted size
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepMap<K, V> hasSize(final int size, final CharSequence message, final Object... arguments) {
        return this.hasSize(size, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Map} has the specified {@code size}.
     * 
     * <p>
     * precondition: {@link Map} cannot be {@code null} and size cannot be lower
     * than zero
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapSize = Assertor.&lt;String, Integer&gt; ofMap().hasSize(size, Locale.US, "bad size");
     * checkMapSize.that(map).orElseThrow();
     * </pre>
     * 
     * @param size
     *            The wanted size
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepMap<K, V> hasSize(final int size, final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorMap.hasSize(this.getStep(), size, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Map} has a size greater than {@code size}.
     * 
     * <p>
     * precondition: {@link Map} cannot be {@code null} and size cannot be lower
     * than zero
     * </p>
     * 
     * <pre>
     * Assertor.that(map).hasSizeGT(size).orElseThrow();
     * </pre>
     * 
     * @param size
     *            The wanted size
     * @return The operator
     * @category localized_message
     */
    default PredicateStepMap<K, V> hasSizeGT(final int size) {
        return this.hasSizeGT(size, null);
    }

    /**
     * Asserts that the given {@link Map} has a size greater than {@code size}.
     * 
     * <p>
     * precondition: {@link Map} cannot be {@code null} and size cannot be lower
     * than zero
     * </p>
     * 
     * <pre>
     * Assertor.that(map).hasSizeGT(size, "bad size").orElseThrow();
     * </pre>
     * 
     * @param size
     *            The wanted size
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepMap<K, V> hasSizeGT(final int size, final CharSequence message, final Object... arguments) {
        return this.hasSizeGT(size, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Map} has a size greater than {@code size}.
     * 
     * <p>
     * precondition: {@link Map} cannot be {@code null} and size cannot be lower
     * than zero
     * </p>
     * 
     * <pre>
     * Assertor.that(map).hasSizeGT(size, Locale.US, "bad size").orElseThrow();
     * </pre>
     * 
     * @param size
     *            The wanted size
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepMap<K, V> hasSizeGT(final int size, final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorMap.hasSizeGT(this.getStep(), size, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Map} has a size greater than or equal to
     * {@code size}.
     * 
     * <p>
     * precondition: {@link Map} cannot be {@code null} and size cannot be lower
     * than zero
     * </p>
     * 
     * <pre>
     * Assertor.that(map).hasSizeGTE(size).orElseThrow();
     * </pre>
     * 
     * @param size
     *            The wanted size
     * @return The operator
     * @category no_message
     */
    default PredicateStepMap<K, V> hasSizeGTE(final int size) {
        return this.hasSizeGTE(size, null);
    }

    /**
     * Asserts that the given {@link Map} has a size greater than or equal to
     * {@code size}.
     * 
     * <p>
     * precondition: {@link Map} cannot be {@code null} and size cannot be lower
     * than zero
     * </p>
     * 
     * <pre>
     * Assertor.that(map).hasSizeGTE(size, "bad size").orElseThrow();
     * </pre>
     * 
     * @param size
     *            The wanted size
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepMap<K, V> hasSizeGTE(final int size, final CharSequence message, final Object... arguments) {
        return this.hasSizeGTE(size, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Map} has a size greater than or equal to
     * {@code size}.
     * 
     * <p>
     * precondition: {@link Map} cannot be {@code null} and size cannot be lower
     * than zero
     * </p>
     * 
     * <pre>
     * Assertor.that(map).hasSizeGTE(size, Locale.US, "bad size").orElseThrow();
     * </pre>
     * 
     * @param size
     *            The wanted size
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepMap<K, V> hasSizeGTE(final int size, final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorMap.hasSizeGTE(this.getStep(), size, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Map} has a size lower than {@code size}.
     * 
     * <p>
     * precondition: {@link Map} cannot be {@code null} and size cannot be lower
     * than zero
     * </p>
     * 
     * <pre>
     * Assertor.that(map).hasSizeLT(size).orElseThrow();
     * </pre>
     * 
     * @param size
     *            The wanted size
     * @return The operator
     * @category no_message
     */
    default PredicateStepMap<K, V> hasSizeLT(final int size) {
        return this.hasSizeLT(size, null);
    }

    /**
     * Asserts that the given {@link Map} has a size lower than {@code size}.
     * 
     * <p>
     * precondition: {@link Map} cannot be {@code null} and size cannot be lower
     * than zero
     * </p>
     * 
     * <pre>
     * Assertor.that(map).hasSizeLT(size, "bad size").orElseThrow();
     * </pre>
     * 
     * @param size
     *            The wanted size
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepMap<K, V> hasSizeLT(final int size, final CharSequence message, final Object... arguments) {
        return this.hasSizeLT(size, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Map} has a size lower than {@code size}.
     * 
     * <p>
     * precondition: {@link Map} cannot be {@code null} and size cannot be lower
     * than zero
     * </p>
     * 
     * <pre>
     * Assertor.that(map).hasSizeLT(size, Locale.US, "bad size").orElseThrow();
     * </pre>
     * 
     * @param size
     *            The wanted size
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepMap<K, V> hasSizeLT(final int size, final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorMap.hasSizeLT(this.getStep(), size, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Map} has a size lower than or equal to
     * {@code size}.
     * 
     * <p>
     * precondition: {@link Map} cannot be {@code null} and size cannot be lower
     * than zero
     * </p>
     * 
     * <pre>
     * Assertor.that(map).hasSizeLTE(size).orElseThrow();
     * </pre>
     * 
     * @param size
     *            The wanted size
     * @return The operator
     * @category no_message
     */
    default PredicateStepMap<K, V> hasSizeLTE(final int size) {
        return this.hasSizeLTE(size, null);
    }

    /**
     * Asserts that the given {@link Map} has a size lower than or equal to
     * {@code size}.
     * 
     * <p>
     * precondition: {@link Map} cannot be {@code null} and size cannot be lower
     * than zero
     * </p>
     * 
     * <pre>
     * Assertor.that(map).hasSizeLTE(size, "bad size").orElseThrow();
     * </pre>
     * 
     * @param size
     *            The wanted size
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepMap<K, V> hasSizeLTE(final int size, final CharSequence message, final Object... arguments) {
        return this.hasSizeLTE(size, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Map} has a size lower than or equal to
     * {@code size}.
     * 
     * <p>
     * precondition: {@link Map} cannot be {@code null} and size cannot be lower
     * than zero
     * </p>
     * 
     * <pre>
     * Assertor.that(map).hasSizeLTE(size, Locale.US, "bad size").orElseThrow();
     * </pre>
     * 
     * @param size
     *            The wanted size
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepMap<K, V> hasSizeLTE(final int size, final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorMap.hasSizeLTE(this.getStep(), size, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Map} is {@code null} or empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapIsEmpty = Assertor.&lt;String, Integer&gt; ofMap().isEmpty();
     * checkMapIsEmpty.that(map).orElseThrow();
     * </pre>
     * 
     * @return The operator
     * @category no_message
     */
    default PredicateStepMap<K, V> isEmpty() {
        return this.isEmpty(null);
    }

    /**
     * Asserts that the given {@link Map} is {@code null} or empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapIsEmpty = Assertor.&lt;String, Integer&gt; ofMap().isEmpty("the map must be null or empty");
     * checkMapIsEmpty.that(map).orElseThrow();
     * </pre>
     * 
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepMap<K, V> isEmpty(final CharSequence message, final Object... arguments) {
        return this.isEmpty(null, message, arguments);
    }

    /**
     * Asserts that the given {@link Map} is {@code null} or empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapIsEmpty = Assertor.&lt;String, Integer&gt; ofMap().isEmpty(Locale.US,
     *         "the map must be null or empty");
     * checkMapIsEmpty.that(map).orElseThrow();
     * </pre>
     * 
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepMap<K, V> isEmpty(final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorMap.isEmpty(this.getStep(), MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Map} is not {@code null} and not empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapIsNotEmpty = Assertor.&lt;String, Integer&gt; ofMap().isNotEmpty();
     * checkMapIsNotEmpty.that(map).orElseThrow();
     * </pre>
     * 
     * @return The operator
     * @category no_message
     */
    default PredicateStepMap<K, V> isNotEmpty() {
        return this.isNotEmpty(null);
    }

    /**
     * Asserts that the given {@link Map} is not {@code null} and not empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapIsNotEmpty = Assertor.&lt;String, Integer&gt; ofMap().isNotEmpty("the map cannot be null or empty");
     * checkMapIsNotEmpty.that(map).orElseThrow();
     * </pre>
     * 
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepMap<K, V> isNotEmpty(final CharSequence message, final Object... arguments) {
        return this.isNotEmpty(null, message, arguments);
    }

    /**
     * Asserts that the given {@link Map} is not {@code null} and not empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapIsNotEmpty = Assertor.&lt;String, Integer&gt; ofMap().isNotEmpty(Locale.US,
     *         "the map cannot be null or empty");
     * checkMapIsNotEmpty.that(map).orElseThrow();
     * </pre>
     * 
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepMap<K, V> isNotEmpty(final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorMap.isNotEmpty(this.getStep(), MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Map} contains the specified {@code key}.
     * 
     * <p>
     * precondition: the {@link Map} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapContains = Assertor.&lt;String, Integer&gt; ofMap().contains(key);
     * checkMapContains.that(map).orElseThrow();
     * </pre>
     * 
     * @param key
     *            The {@link Map} key
     * @return The operator
     * @category no_message
     */
    default PredicateStepMap<K, V> contains(final K key) {
        return this.contains(key, (CharSequence) null);
    }

    /**
     * Asserts that the given {@link Map} contains the specified {@code key}.
     * 
     * <p>
     * precondition: the {@link Map} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapContains = Assertor.&lt;String, Integer&gt; ofMap().contains(key,
     *         "the key was not found in the map");
     * checkMapContains.that(map).orElseThrow();
     * </pre>
     * 
     * @param key
     *            The {@link Map} key
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepMap<K, V> contains(final K key, final CharSequence message, final Object... arguments) {
        return this.contains(key, (Locale) null, message, arguments);
    }

    /**
     * Asserts that the given {@link Map} contains the specified {@code key}.
     * 
     * <p>
     * precondition: the {@link Map} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapContains = Assertor.&lt;String, Integer&gt; ofMap().contains(key, Locale.US,
     *         "the key was not found in the map");
     * checkMapContains.that(map).orElseThrow();
     * </pre>
     * 
     * @param key
     *            The {@link Map} key
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepMap<K, V> contains(final K key, final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorMap.contains(this.getStep(), key, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Map} contains the specified pair
     * {@code key}/{@code value}.
     * 
     * <p>
     * precondition: the {@link Map} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapContains = Assertor.&lt;String, Integer&gt; ofMap().contains(key, value);
     * checkMapContains.that(map).orElseThrow();
     * </pre>
     * 
     * @param key
     *            The {@link Map} key
     * @param value
     *            The {@link Map} value
     * @return The operator
     * @category no_message
     */
    default PredicateStepMap<K, V> contains(final K key, final V value) {
        return this.contains(key, value, null);
    }

    /**
     * Asserts that the given {@link Map} contains the specified pair
     * {@code key}/{@code value}.
     * 
     * <p>
     * precondition: the {@link Map} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapContains = Assertor.&lt;String, Integer&gt; ofMap().contains(key, value,
     *         "the pair key/value was not found in the map");
     * checkMapContains.that(map).orElseThrow();
     * </pre>
     * 
     * @param key
     *            The {@link Map} key
     * @param value
     *            The {@link Map} value
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepMap<K, V> contains(final K key, final V value, final CharSequence message, final Object... arguments) {
        return this.contains(key, value, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Map} contains the specified pair
     * {@code key}/{@code value}.
     * 
     * <p>
     * precondition: the {@link Map} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapContains = Assertor.&lt;String, Integer&gt; ofMap().contains(key, value, Locale.US,
     *         "the pair key/value was not found in the map");
     * checkMapContains.that(map).orElseThrow();
     * </pre>
     * 
     * @param key
     *            The {@link Map} key
     * @param value
     *            The {@link Map} value
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepMap<K, V> contains(final K key, final V value, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorMap.contains(this.getStep(), key, value, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Map} contains the specified {@code value}.
     * 
     * <p>
     * precondition: the {@link Map} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapContains = Assertor.&lt;String, Integer&gt; ofMap().contains(value);
     * checkMapContains.that(map).orElseThrow();
     * </pre>
     * 
     * @param value
     *            The {@link Map} value
     * @return The operator
     * @category no_message
     */
    default PredicateStepMap<K, V> containsValue(final V value) {
        return this.containsValue(value, null);
    }

    /**
     * Asserts that the given {@link Map} contains the specified {@code value}.
     * 
     * <p>
     * precondition: the {@link Map} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapContains = Assertor.&lt;String, Integer&gt; ofMap().contains(value,
     *         "the value was not found in the map");
     * checkMapContains.that(map).orElseThrow();
     * </pre>
     * 
     * @param value
     *            The {@link Map} value
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepMap<K, V> containsValue(final V value, final CharSequence message, final Object... arguments) {
        return this.containsValue(value, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Map} contains the specified {@code value}.
     * 
     * <p>
     * precondition: the {@link Map} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapContains = Assertor.&lt;String, Integer&gt; ofMap().contains(value, Locale.US,
     *         "the value was not found in the map");
     * checkMapContains.that(map).orElseThrow();
     * </pre>
     * 
     * @param value
     *            The {@link Map} value
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepMap<K, V> containsValue(final V value, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorMap.containsValue(this.getStep(), value, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Map} contains ALL the specified
     * {@code keys}.
     * 
     * <p>
     * precondition: neither {@link Map} and {@code keys} can be {@code null} or
     * empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapContainsAllKeys = Assertor.&lt;String, Integer&gt; ofMap().containsAll(keys);
     * checkMapContainsAllKeys.that(map).orElseThrow();
     * </pre>
     * 
     * @param keys
     *            The {@link Iterable} keys
     * @return The operator
     * @category no_message
     */
    default PredicateStepMap<K, V> containsAll(final Iterable<K> keys) {
        return this.containsAll(keys, null);
    }

    /**
     * Asserts that the given {@link Map} contains ALL the specified
     * {@code keys}.
     * 
     * <p>
     * precondition: neither {@link Map} and {@code keys} can be {@code null} or
     * empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapContainsAllKeys = Assertor.&lt;String, Integer&gt; ofMap().containsAll(keys,
     *         "not all keys can be found in the map");
     * checkMapContainsAllKeys.that(map).orElseThrow();
     * </pre>
     * 
     * @param keys
     *            The {@link Iterable} keys
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepMap<K, V> containsAll(final Iterable<K> keys, final CharSequence message, final Object... arguments) {
        return this.containsAll(keys, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Map} contains ALL the specified
     * {@code keys}.
     * 
     * <p>
     * precondition: neither {@link Map} and {@code keys} can be {@code null} or
     * empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapContainsAllKeys = Assertor.&lt;String, Integer&gt; ofMap().containsAll(keys, Locale.US,
     *         "not all keys can be found in the map");
     * checkMapContainsAllKeys.that(map).orElseThrow();
     * </pre>
     * 
     * @param keys
     *            The {@link Iterable} keys
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepMap<K, V> containsAll(final Iterable<K> keys, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorMap.containsAll(this.getStep(), keys, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Map} contains ALL entries from the
     * specified {@code map}.
     * 
     * <p>
     * precondition: neither {@link Map} can be {@code null} or empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapContainsAll = Assertor.&lt;String, Integer&gt; ofMap().containsAll(entries);
     * checkMapContainsAll.that(map).orElseThrow();
     * </pre>
     * 
     * @param map
     *            The {@link Map}
     * @return The operator
     * @category no_message
     */
    default PredicateStepMap<K, V> containsAll(final Map<K, V> map) {
        return this.containsAll(map, null);
    }

    /**
     * Asserts that the given {@link Map} contains ALL entries from the
     * specified {@code map}.
     * 
     * <p>
     * precondition: neither {@link Map} can be {@code null} or empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapContainsAll = Assertor.&lt;String, Integer&gt; ofMap().containsAll(entries,
     *         "not all pairs key/value can be found in the map");
     * checkMapContainsAll.that(map).orElseThrow();
     * </pre>
     * 
     * @param map
     *            The {@link Map}
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepMap<K, V> containsAll(final Map<K, V> map, final CharSequence message, final Object... arguments) {
        return this.containsAll(map, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Map} contains ALL entries from the
     * specified {@code map}.
     * 
     * <p>
     * precondition: neither {@link Map} can be {@code null} or empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapContainsAll = Assertor.&lt;String, Integer&gt; ofMap().containsAll(entries, Locale.US,
     *         "not all pairs key/value can be found in the map");
     * checkMapContainsAll.that(map).orElseThrow();
     * </pre>
     * 
     * @param map
     *            The {@link Map}
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepMap<K, V> containsAll(final Map<K, V> map, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorMap.containsAll(this.getStep(), map, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Map} contains ALL the specified
     * {@code values}.
     * 
     * <p>
     * precondition: neither {@link Map} and {@code values} can be {@code null}
     * or empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapContainsValues = Assertor.&lt;String, Integer&gt; ofMap().containsAllValues(values);
     * checkMapContainsValues.that(map).orElseThrow();
     * </pre>
     * 
     * @param values
     *            The {@link Iterable} values
     * @return The operator
     * @category no_message
     */
    default PredicateStepMap<K, V> containsAllValues(final Iterable<V> values) {
        return this.containsAllValues(values, null);
    }

    /**
     * Asserts that the given {@link Map} contains ALL the specified
     * {@code values}.
     * 
     * <p>
     * precondition: neither {@link Map} and {@code values} can be {@code null}
     * or empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapContainsValues = Assertor.&lt;String, Integer&gt; ofMap().containsAllValues(values,
     *         "not all values can be found in the map");
     * checkMapContainsValues.that(map).orElseThrow();
     * </pre>
     * 
     * @param values
     *            The {@link Iterable} values
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepMap<K, V> containsAllValues(final Iterable<V> values, final CharSequence message, final Object... arguments) {
        return this.containsAllValues(values, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Map} contains ALL the specified
     * {@code values}.
     * 
     * <p>
     * precondition: neither {@link Map} and {@code values} can be {@code null}
     * or empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapContainsValues = Assertor.&lt;String, Integer&gt; ofMap().containsAllValues(values, Locale.US,
     *         "not all values can be found in the map");
     * checkMapContainsValues.that(map).orElseThrow();
     * </pre>
     * 
     * @param values
     *            The {@link Iterable} values
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepMap<K, V> containsAllValues(final Iterable<V> values, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorMap.containsAllValues(this.getStep(), values, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Map} contains ANY key from the specified
     * {@link Iterable}.
     * 
     * <p>
     * precondition: neither {@link Map} and {@code keys} can be {@code null} or
     * empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapContainsAnyKeys = Assertor.&lt;String, Integer&gt; ofMap().containsAny(keys);
     * checkMapContainsAnyKeys.that(map).orElseThrow();
     * </pre>
     * 
     * @param keys
     *            The {@link Iterable} of keys
     * @return The operator
     * @category no_message
     */
    default PredicateStepMap<K, V> containsAny(final Iterable<K> keys) {
        return this.containsAny(keys, null);
    }

    /**
     * Asserts that the given {@link Map} contains ANY key from the specified
     * {@link Iterable}.
     * 
     * <p>
     * precondition: neither {@link Map} and {@code keys} can be {@code null} or
     * empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapContainsAnyKeys = Assertor.&lt;String, Integer&gt; ofMap().containsAny(keys,
     *         "no pair key can be found in the map");
     * checkMapContainsAnyKeys.that(map).orElseThrow();
     * </pre>
     * 
     * @param keys
     *            The {@link Iterable} of keys
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepMap<K, V> containsAny(final Iterable<K> keys, final CharSequence message, final Object... arguments) {
        return this.containsAny(keys, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Map} contains ANY key from the specified
     * {@link Iterable}.
     * 
     * <p>
     * precondition: neither {@link Map} and {@code keys} can be {@code null} or
     * empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapContainsAnyKeys = Assertor.&lt;String, Integer&gt; ofMap().containsAny(keys, Locale.US,
     *         "no pair key can be found in the map");
     * checkMapContainsAnyKeys.that(map).orElseThrow();
     * </pre>
     * 
     * @param keys
     *            The {@link Iterable} of keys
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepMap<K, V> containsAny(final Iterable<K> keys, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorMap.containsAny(this.getStep(), keys, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Map} contains ANY entries from the
     * specified {@code map}.
     * 
     * <p>
     * precondition: neither {@link Map} can be {@code null} or empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapContainsAny = Assertor.&lt;String, Integer&gt; ofMap().containsAny(entries);
     * checkMapContainsAny.that(map).orElseThrow();
     * </pre>
     * 
     * @param map
     *            The {@link Map}
     * @return The operator
     * @category no_message
     */
    default PredicateStepMap<K, V> containsAny(final Map<K, V> map) {
        return this.containsAny(map, null);
    }

    /**
     * Asserts that the given {@link Map} contains ANY entries from the
     * specified {@code map}.
     * 
     * <p>
     * precondition: neither {@link Map} can be {@code null} or empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapContainsAny = Assertor.&lt;String, Integer&gt; ofMap().containsAny(entries,
     *         "no pair key/value can be found in the map");
     * checkMapContainsAny.that(map).orElseThrow();
     * </pre>
     * 
     * @param map
     *            The {@link Map}
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepMap<K, V> containsAny(final Map<K, V> map, final CharSequence message, final Object... arguments) {
        return this.containsAny(map, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Map} contains ANY entries from the
     * specified {@code map}.
     * 
     * <p>
     * precondition: neither {@link Map} can be {@code null} or empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapContainsAny = Assertor.&lt;String, Integer&gt; ofMap().containsAny(entries, Locale.US,
     *         "no pair key/value can be found in the map");
     * checkMapContainsAny.that(map).orElseThrow();
     * </pre>
     * 
     * @param map
     *            The {@link Map}
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepMap<K, V> containsAny(final Map<K, V> map, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorMap.containsAny(this.getStep(), map, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Map} contains ANY specified {@code values}.
     * 
     * <p>
     * precondition: neither {@link Map} and {@code values} can be {@code null}
     * or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(map).containsAnyValues(values).orElseThrow();
     * </pre>
     * 
     * @param values
     *            The {@link Iterable} values
     * @return The operator
     * @category no_message
     */
    default PredicateStepMap<K, V> containsAnyValues(final Iterable<V> values) {
        return this.containsAnyValues(values, null);
    }

    /**
     * Asserts that the given {@link Map} contains ANY specified {@code values}.
     * 
     * <p>
     * precondition: neither {@link Map} and {@code values} can be {@code null}
     * or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(map).containsAnyValues(values, "not all values can be found in the map").orElseThrow();
     * </pre>
     * 
     * @param values
     *            The {@link Iterable} values
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepMap<K, V> containsAnyValues(final Iterable<V> values, final CharSequence message, final Object... arguments) {
        return this.containsAnyValues(values, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Map} contains ANY specified {@code values}.
     * 
     * <p>
     * precondition: neither {@link Map} and {@code values} can be {@code null}
     * or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(map).containsAnyValues(values, Locale.US, "not all values can be found in the map").orElseThrow();
     * </pre>
     * 
     * @param values
     *            The {@link Iterable} values
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepMap<K, V> containsAnyValues(final Iterable<V> values, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorMap.containsAnyValues(this.getStep(), values, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Map} contains all entries from the
     * specified {@code map} in the same order. Each maps must be sorted or
     * linked otherwise result is unpredictable.
     * 
     * <p>
     * precondition: neither {@link Map} can be {@code null} or empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapOrder = Assertor.&lt;String, Integer&gt; ofMap().containsInOrder(entries);
     * checkMapOrder.that(map).orElseThrow();
     * </pre>
     * 
     * @param map
     *            The {@link Map}
     * @return The operator
     * @category message
     */
    default PredicateStepMap<K, V> containsInOrder(final Map<K, V> map) {
        return this.containsInOrder(map, null);
    }

    /**
     * Asserts that the given {@link Map} contains all entries from the
     * specified {@code map} in the same order. Each maps must be sorted or
     * linked otherwise result is unpredictable.
     * 
     * <p>
     * precondition: neither {@link Map} can be {@code null} or empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapOrder = Assertor.&lt;String, Integer&gt; ofMap().containsInOrder(entries,
     *         "the maps are not in the same order");
     * checkMapOrder.that(map).orElseThrow();
     * </pre>
     * 
     * @param map
     *            The {@link Map}
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepMap<K, V> containsInOrder(final Map<K, V> map, final CharSequence message, final Object... arguments) {
        return this.containsInOrder(map, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Map} contains all entries from the
     * specified {@code map} in the same order. Each maps must be sorted or
     * linked otherwise result is unpredictable.
     * 
     * <p>
     * precondition: neither {@link Map} can be {@code null} or empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapOrder = Assertor.&lt;String, Integer&gt; ofMap().containsInOrder(entries, Locale.US,
     *         "the maps are not in the same order");
     * checkMapOrder.that(map).orElseThrow();
     * </pre>
     * 
     * @param map
     *            The {@link Map}
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepMap<K, V> containsInOrder(final Map<K, V> map, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorMap.containsInOrder(this.getStep(), map, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Map} contains all keys in the same order.
     * The map and the keys' iterable must be sorted or linked otherwise result
     * is unpredictable.
     * 
     * <p>
     * precondition: neither {@link Map} nor iterable can be {@code null} or
     * empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapOrder = Assertor.&lt;String, Integer&gt; ofMap().containsInOrder(keys);
     * checkMapOrder.that(map).orElseThrow();
     * </pre>
     * 
     * @param keys
     *            The keys' {@link Iterable}
     * @return The operator
     * @category no_message
     */
    default PredicateStepMap<K, V> containsInOrder(final Iterable<K> keys) {
        return this.containsInOrder(keys, null);
    }

    /**
     * Asserts that the given {@link Map} contains all keys in the same order.
     * The map and the keys' iterable must be sorted or linked otherwise result
     * is unpredictable.
     * 
     * <p>
     * precondition: neither {@link Map} nor iterable can be {@code null} or
     * empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapOrder = Assertor.&lt;String, Integer&gt; ofMap().containsInOrder(keys,
     *         "the keys are not in the same order");
     * checkMapOrder.that(map).orElseThrow();
     * </pre>
     * 
     * @param keys
     *            The keys' {@link Iterable}
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepMap<K, V> containsInOrder(final Iterable<K> keys, final CharSequence message, final Object... arguments) {
        return this.containsInOrder(keys, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Map} contains all keys in the same order.
     * The map and the keys' iterable must be sorted or linked otherwise result
     * is unpredictable.
     * 
     * <p>
     * precondition: neither {@link Map} nor iterable can be {@code null} or
     * empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapOrder = Assertor.&lt;String, Integer&gt; ofMap().containsInOrder(keys, Locale.US,
     *         "the keys are not in the same order");
     * checkMapOrder.that(map).orElseThrow();
     * </pre>
     * 
     * @param keys
     *            The keys' {@link Iterable}
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepMap<K, V> containsInOrder(final Iterable<K> keys, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorMap.containsKeysInOrder(this.getStep(), keys, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Map} contains all values in the same order.
     * The map and the values' iterable must be sorted or linked otherwise
     * result is unpredictable.
     * 
     * <p>
     * precondition: neither {@link Map} nor iterable can be {@code null} or
     * empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapOrder = Assertor.&lt;String, Integer&gt; ofMap().containsInOrder(values);
     * checkMapOrder.that(map).orElseThrow();
     * </pre>
     * 
     * @param values
     *            The values' {@link Iterable}
     * @return The operator
     * @category no_message
     */
    default PredicateStepMap<K, V> containsValuesInOrder(final Iterable<V> values) {
        return this.containsValuesInOrder(values, null);
    }

    /**
     * Asserts that the given {@link Map} contains all values in the same order.
     * The map and the values' iterable must be sorted or linked otherwise
     * result is unpredictable.
     * 
     * <p>
     * precondition: neither {@link Map} nor iterable can be {@code null} or
     * empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapOrder = Assertor.&lt;String, Integer&gt; ofMap().containsInOrder(values,
     *         "the values are not in the same order");
     * checkMapOrder.that(map).orElseThrow();
     * </pre>
     * 
     * @param values
     *            The values' {@link Iterable}
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepMap<K, V> containsValuesInOrder(final Iterable<V> values, final CharSequence message, final Object... arguments) {
        return this.containsValuesInOrder(values, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Map} contains all values in the same order.
     * The map and the values' iterable must be sorted or linked otherwise
     * result is unpredictable.
     * 
     * <p>
     * precondition: neither {@link Map} nor iterable can be {@code null} or
     * empty
     * </p>
     * 
     * <pre>
     * PredicateStepMap&lt;String, Integer&gt; checkMapOrder = Assertor.&lt;String, Integer&gt; ofMap().containsInOrder(values, Locale.US,
     *         "the values are not in the same order");
     * checkMapOrder.that(map).orElseThrow();
     * </pre>
     * 
     * @param values
     *            The values' {@link Iterable}
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepMap<K, V> containsValuesInOrder(final Iterable<V> values, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorMap.containsValuesInOrder(this.getStep(), values, MessageAssertor.of(locale, message, arguments));
    }
}
