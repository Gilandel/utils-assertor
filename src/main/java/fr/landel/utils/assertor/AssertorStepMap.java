/*-
 * #%L
 * utils-assertor
 * %%
 * Copyright (C) 2016 - 2017 Gilandel
 * %%
 * Authors: Gilles Landel
 * URL: https://github.com/Gilandel
 * 
 * This file is under Apache License, version 2.0 (2004).
 * #L%
 */
package fr.landel.utils.assertor;

import java.util.Locale;
import java.util.Map;

import fr.landel.utils.assertor.commons.MessageAssertor;
import fr.landel.utils.assertor.helper.HelperStep;
import fr.landel.utils.assertor.utils.AssertorMap;

/**
 * This class define methods that can be applied on the checked {@link Map}
 * object. To provide a result, it's also provide a chain builder by returning a
 * {@link StepMap}. The chain looks like:
 * 
 * <pre>
 * {@link AssertorStepMap} &gt; {@link StepMap} &gt; {@link AssertorStepMap} &gt; {@link StepMap}...
 * </pre>
 * 
 * This chain always starts with a {@link AssertorStepMap} and ends with
 * {@link StepMap}.
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 */
@FunctionalInterface
public interface AssertorStepMap<M extends Map<K, V>, K, V> extends AssertorStep<StepMap<M, K, V>, M> {

    /**
     * {@inheritDoc}
     */
    @Override
    default StepMap<M, K, V> get(final StepAssertor<M> result) {
        return () -> result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default AssertorStepMap<M, K, V> not() {
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
     * Assertor.that(map).hasSize(size).orElseThrow();
     * </pre>
     * 
     * @param size
     *            The wanted size
     * @return The operator
     * @category no_message
     */
    default StepMap<M, K, V> hasSize(final int size) {
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
     * Assertor.that(map).hasSize(size, "bad size").orElseThrow();
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
    default StepMap<M, K, V> hasSize(final int size, final CharSequence message, final Object... arguments) {
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
     * Assertor.that(map).hasSize(size, Locale.US, "bad size").orElseThrow();
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
    default StepMap<M, K, V> hasSize(final int size, final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorMap.hasSize(this.getStep(), size, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Map} is {@code null} or empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(map).isEmpty().orElseThrow();
     * </pre>
     * 
     * @return The operator
     * @category no_message
     */
    default StepMap<M, K, V> isEmpty() {
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
     * Assertor.that(map).isEmpty("the map must be null or empty").orElseThrow();
     * </pre>
     * 
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default StepMap<M, K, V> isEmpty(final CharSequence message, final Object... arguments) {
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
     * Assertor.that(map).isEmpty(Locale.US, "the map must be null or empty").orElseThrow();
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
    default StepMap<M, K, V> isEmpty(final Locale locale, final CharSequence message, final Object... arguments) {
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
     * Assertor.that(map).isNotEmpty().orElseThrow();
     * </pre>
     * 
     * @return The operator
     * @category no_message
     */
    default StepMap<M, K, V> isNotEmpty() {
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
     * Assertor.that(map).isNotEmpty("the map cannot be null or empty").orElseThrow();
     * </pre>
     * 
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default StepMap<M, K, V> isNotEmpty(final CharSequence message, final Object... arguments) {
        return this.isNotEmpty(null, message, arguments);
    }

    /**
     * Asserts that the given {@link Map} contains the specified {@code key}.
     * 
     * <p>
     * precondition: the {@link Map} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(map).contains(key).orElseThrow();
     * </pre>
     * 
     * @param key
     *            The {@link Map} key
     * @return The operator
     * @category no_message
     */
    default StepMap<M, K, V> contains(final K key) {
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
     * Assertor.that(map).contains(key, "the key was not found in the map").orElseThrow();
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
    default StepMap<M, K, V> contains(final K key, final CharSequence message, final Object... arguments) {
        return this.contains(key, (Locale) null, message, arguments);
    }

    /**
     * Asserts that the given {@link Map} is not {@code null} and not empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(map).isNotEmpty(Locale.US, "the map cannot be null or empty").orElseThrow();
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
    default StepMap<M, K, V> isNotEmpty(final Locale locale, final CharSequence message, final Object... arguments) {
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
     * Assertor.that(map).contains(key, Locale.US, "the key was not found in the map").orElseThrow();
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
    default StepMap<M, K, V> contains(final K key, final Locale locale, final CharSequence message, final Object... arguments) {
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
     * Assertor.that(map).contains(key, value).orElseThrow();
     * </pre>
     * 
     * @param key
     *            The {@link Map} key
     * @param value
     *            The {@link Map} value
     * @return The operator
     * @category no_message
     */
    default StepMap<M, K, V> contains(final K key, final V value) {
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
     * Assertor.that(map).contains(key, value, "the pair key/value was not found in the map").orElseThrow();
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
    default StepMap<M, K, V> contains(final K key, final V value, final CharSequence message, final Object... arguments) {
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
     * Assertor.that(map).contains(key, value, Locale.US, "the pair key/value was not found in the map").orElseThrow();
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
    default StepMap<M, K, V> contains(final K key, final V value, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorMap.contains(this.getStep(), key, value, MessageAssertor.of(locale, message, arguments));
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
     * Assertor.that(map).containsAll(keys).orElseThrow();
     * </pre>
     * 
     * @param keys
     *            The {@link Iterable} keys
     * @return The operator
     * @category no_message
     */
    default StepMap<M, K, V> containsAll(final Iterable<K> keys) {
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
     * Assertor.that(map).containsAll(keys, "not all keys can be found in the map").orElseThrow();
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
    default StepMap<M, K, V> containsAll(final Iterable<K> keys, final CharSequence message, final Object... arguments) {
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
     * Assertor.that(map).containsAll(keys, Locale.US, "not all keys can be found in the map").orElseThrow();
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
    default StepMap<M, K, V> containsAll(final Iterable<K> keys, final Locale locale, final CharSequence message,
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
     * Assertor.that(map).containsAll(entries).orElseThrow();
     * </pre>
     * 
     * @param map
     *            The {@link Map}
     * @return The operator
     * @category no_message
     */
    default StepMap<M, K, V> containsAll(final Map<K, V> map) {
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
     * Assertor.that(map).containsAll(entries, "not all pairs key/value can be found in the map").orElseThrow();
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
    default StepMap<M, K, V> containsAll(final Map<K, V> map, final CharSequence message, final Object... arguments) {
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
     * Assertor.that(map).containsAll(entries, Locale.US, "not all pairs key/value can be found in the map").orElseThrow();
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
    default StepMap<M, K, V> containsAll(final Map<K, V> map, final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorMap.containsAll(this.getStep(), map, MessageAssertor.of(locale, message, arguments));
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
     * Assertor.that(map).containsAny(keys).orElseThrow();
     * </pre>
     * 
     * @param keys
     *            The {@link Iterable} of keys
     * @return The operator
     * @category no_message
     */
    default StepMap<M, K, V> containsAny(final Iterable<K> keys) {
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
     * Assertor.that(map).containsAny(keys, "no pair key can be found in the map").orElseThrow();
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
    default StepMap<M, K, V> containsAny(final Iterable<K> keys, final CharSequence message, final Object... arguments) {
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
     * Assertor.that(map).containsAny(keys, Locale.US, "no pair key can be found in the map").orElseThrow();
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
    default StepMap<M, K, V> containsAny(final Iterable<K> keys, final Locale locale, final CharSequence message,
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
     * Assertor.that(map).containsAny(entries).orElseThrow();
     * </pre>
     * 
     * @param map
     *            The {@link Map}
     * @return The operator
     * @category no_message
     */
    default StepMap<M, K, V> containsAny(final Map<K, V> map) {
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
     * Assertor.that(map).containsAny(entries, "no pair key/value can be found in the map").orElseThrow();
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
    default StepMap<M, K, V> containsAny(final Map<K, V> map, final CharSequence message, final Object... arguments) {
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
     * Assertor.that(map).containsAny(entries, Locale.US, "no pair key/value can be found in the map").orElseThrow();
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
    default StepMap<M, K, V> containsAny(final Map<K, V> map, final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorMap.containsAny(this.getStep(), map, MessageAssertor.of(locale, message, arguments));
    }
}
