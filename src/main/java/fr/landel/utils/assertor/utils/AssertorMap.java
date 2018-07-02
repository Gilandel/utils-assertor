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
package fr.landel.utils.assertor.utils;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;

import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.commons.ConstantsAssertor;
import fr.landel.utils.assertor.commons.MessageAssertor;
import fr.landel.utils.assertor.commons.ParameterAssertor;
import fr.landel.utils.assertor.enums.EnumAnalysisMode;
import fr.landel.utils.assertor.enums.EnumType;
import fr.landel.utils.assertor.helper.HelperAssertor;
import fr.landel.utils.commons.CastUtils;
import fr.landel.utils.commons.MapUtils2;

/**
 * Utility class to prepare the check of {@link Map}
 *
 * @since Aug 10, 2016
 * @author Gilles
 *
 */
public class AssertorMap extends ConstantsAssertor {

    /**
     * Prepare the next step to validate that the {@link Map} size is equal to
     * {@code size}.
     * 
     * <p>
     * precondition: {@link Map} cannot be {@code null} and size cannot be lower
     * than zero
     * </p>
     * 
     * @param step
     *            the current step
     * @param size
     *            the size to validate
     * @param message
     *            the message if invalid
     * @param <M>
     *            the {@link Map} type
     * @param <K>
     *            the {@link Map} key elements type
     * @param <V>
     *            the {@link Map} value elements type
     * @return the next step
     */
    public static <M extends Map<K, V>, K, V> StepAssertor<M> hasSize(final StepAssertor<M> step, final int size,
            final MessageAssertor message) {

        final BiPredicate<M, Boolean> checker = (map, not) -> map.size() == size;

        return checkSize(step, size, checker, MSG.MAP.SIZE, message);
    }

    /**
     * Prepare the next step to validate that the {@link Map} size is greater
     * than {@code size}.
     * 
     * <p>
     * precondition: {@link Map} cannot be {@code null} and size cannot be lower
     * than zero
     * </p>
     * 
     * @param step
     *            the current step
     * @param size
     *            the size to validate
     * @param message
     *            the message if invalid
     * @param <M>
     *            the {@link Map} type
     * @param <K>
     *            the {@link Map} key elements type
     * @param <V>
     *            the {@link Map} value elements type
     * @return the next step
     */
    public static <M extends Map<K, V>, K, V> StepAssertor<M> hasSizeGT(final StepAssertor<M> step, final int size,
            final MessageAssertor message) {

        final BiPredicate<M, Boolean> checker = (map, not) -> map.size() > size;

        return checkSize(step, size, checker, MSG.MAP.SIZE_GT, message);
    }

    /**
     * Prepare the next step to validate that the {@link Map} size is greater
     * than or equal to {@code size}.
     * 
     * <p>
     * precondition: {@link Map} cannot be {@code null} and size cannot be lower
     * than zero
     * </p>
     * 
     * @param step
     *            the current step
     * @param size
     *            the size to validate
     * @param message
     *            the message if invalid
     * @param <M>
     *            the {@link Map} type
     * @param <K>
     *            the {@link Map} key elements type
     * @param <V>
     *            the {@link Map} value elements type
     * @return the next step
     */
    public static <M extends Map<K, V>, K, V> StepAssertor<M> hasSizeGTE(final StepAssertor<M> step, final int size,
            final MessageAssertor message) {

        final BiPredicate<M, Boolean> checker = (map, not) -> map.size() >= size;

        return checkSize(step, size, checker, MSG.MAP.SIZE_GTE, message);
    }

    /**
     * Prepare the next step to validate that the {@link Map} size is lower than
     * {@code size}.
     * 
     * <p>
     * precondition: {@link Map} cannot be {@code null} and size cannot be lower
     * than zero
     * </p>
     * 
     * @param step
     *            the current step
     * @param size
     *            the size to validate
     * @param message
     *            the message if invalid
     * @param <M>
     *            the {@link Map} type
     * @param <K>
     *            the {@link Map} key elements type
     * @param <V>
     *            the {@link Map} value elements type
     * @return the next step
     */
    public static <M extends Map<K, V>, K, V> StepAssertor<M> hasSizeLT(final StepAssertor<M> step, final int size,
            final MessageAssertor message) {

        final BiPredicate<M, Boolean> checker = (map, not) -> map.size() < size;

        return checkSize(step, size, checker, MSG.MAP.SIZE_LT, message);
    }

    /**
     * Prepare the next step to validate that the {@link Map} size is lower than
     * or equal to {@code size}.
     * 
     * <p>
     * precondition: {@link Map} cannot be {@code null} and size cannot be lower
     * than zero
     * </p>
     * 
     * @param step
     *            the current step
     * @param size
     *            the size to validate
     * @param message
     *            the message if invalid
     * @param <M>
     *            the {@link Map} type
     * @param <K>
     *            the {@link Map} key elements type
     * @param <V>
     *            the {@link Map} value elements type
     * @return the next step
     */
    public static <M extends Map<K, V>, K, V> StepAssertor<M> hasSizeLTE(final StepAssertor<M> step, final int size,
            final MessageAssertor message) {

        final BiPredicate<M, Boolean> checker = (map, not) -> map.size() <= size;

        return checkSize(step, size, checker, MSG.MAP.SIZE_LTE, message);
    }

    public static <M extends Map<K, V>, K, V> StepAssertor<M> checkSize(final StepAssertor<M> step, final int size,
            final BiPredicate<M, Boolean> checker, final String messageKey, final MessageAssertor message) {

        final Predicate<M> preChecker = (map) -> size >= 0 && map != null;

        return new StepAssertor<>(step, preChecker, checker, false, message, messageKey, false,
                new ParameterAssertor<>(size, EnumType.NUMBER_INTEGER));
    }

    /**
     * Prepare the next step to validate if the {@link Map} is {@code null} or
     * empty
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the current step
     * @param message
     *            the message if invalid
     * @param <M>
     *            the {@link Map} type
     * @param <K>
     *            the {@link Map} key elements type
     * @param <V>
     *            the {@link Map} value elements type
     * @return the next step
     */
    public static <M extends Map<K, V>, K, V> StepAssertor<M> isEmpty(final StepAssertor<M> step, final MessageAssertor message) {

        final BiPredicate<M, Boolean> checker = (map, not) -> MapUtils.isEmpty(map);

        return new StepAssertor<>(step, checker, false, message, MSG.MAP.EMPTY, false);
    }

    /**
     * Prepare the next step to validate if the {@link Map} is NOT {@code null}
     * and NOT empty
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the current step
     * @param message
     *            the message if invalid
     * @param <M>
     *            the {@link Map} type
     * @param <K>
     *            the {@link Map} key elements type
     * @param <V>
     *            the {@link Map} value elements type
     * @return the next step
     */
    public static <M extends Map<K, V>, K, V> StepAssertor<M> isNotEmpty(final StepAssertor<M> step, final MessageAssertor message) {

        final BiPredicate<M, Boolean> checker = (map, not) -> MapUtils.isNotEmpty(map);

        return new StepAssertor<>(step, checker, false, message, MSG.MAP.EMPTY, true);
    }

    /**
     * Prepare the next step to validate if all map entries match the predicate.
     * 
     * <p>
     * precondition: {@code map} cannot be {@code null} or empty and
     * {@code predicate} cannot be {@code null}
     * </p>
     * 
     * @param step
     *            the current step
     * @param predicate
     *            the predicate used to check each entry
     * @param message
     *            the message on predicate failed
     * @param <M>
     *            the {@link Map} type
     * @param <K>
     *            the {@link Map} key elements type
     * @param <V>
     *            the {@link Map} value elements type
     * @return the next step
     */
    public static <M extends Map<K, V>, K, V> StepAssertor<M> allMatch(final StepAssertor<M> step, final Predicate<Entry<K, V>> predicate,
            final MessageAssertor message) {

        return match(step, predicate, true, MSG.MAP.MATCH_ALL, message);
    }

    /**
     * Prepare the next step to validate if any map entry matches the predicate.
     * 
     * <p>
     * precondition: {@code map} cannot be {@code null} or empty and
     * {@code predicate} cannot be {@code null}
     * </p>
     * 
     * @param step
     *            the current step
     * @param predicate
     *            the predicate used to check each entry
     * @param message
     *            the message on predicate failed
     * @param <M>
     *            the {@link Map} type
     * @param <K>
     *            the {@link Map} key elements type
     * @param <V>
     *            the {@link Map} value elements type
     * @return the next step
     */
    public static <M extends Map<K, V>, K, V> StepAssertor<M> anyMatch(final StepAssertor<M> step, final Predicate<Entry<K, V>> predicate,
            final MessageAssertor message) {

        return match(step, predicate, false, MSG.MAP.MATCH_ANY, message);
    }

    private static <M extends Map<K, V>, K, V> StepAssertor<M> match(final StepAssertor<M> step, final Predicate<Entry<K, V>> predicate,
            final boolean all, final String messageKey, final MessageAssertor message) {

        final Predicate<M> preChecker = (map) -> MapUtils.isNotEmpty(map) && predicate != null;

        final BiPredicate<M, Boolean> checker = (object, not) -> AssertorMap.match(object, predicate, all, step.getAnalysisMode());

        return new StepAssertor<>(step, preChecker, checker, false, message, messageKey, false,
                new ParameterAssertor<>(predicate, EnumType.UNKNOWN));
    }

    private static <K, V> boolean match(final Map<K, V> map, final Predicate<Entry<K, V>> predicate, final boolean all,
            final EnumAnalysisMode analysisMode) {

        final Set<Entry<K, V>> entries = map.entrySet();

        if (EnumAnalysisMode.STANDARD.equals(analysisMode)) {
            if (all) {
                for (final Entry<K, V> entry : entries) {
                    if (!predicate.test(entry)) {
                        return false;
                    }
                }
                return true;
            } else {
                for (final Entry<K, V> entry : entries) {
                    if (predicate.test(entry)) {
                        return true;
                    }
                }
                return false;
            }
        } else {
            final Stream<Entry<K, V>> stream;
            if (EnumAnalysisMode.PARALLEL.equals(analysisMode)) {
                stream = entries.parallelStream();
            } else {
                stream = entries.stream();
            }
            if (all) {
                return stream.allMatch(predicate);
            } else {
                return stream.anyMatch(predicate);
            }
        }
    }

    /**
     * Prepare the next step to validate if the {@link Map} contains all
     * {@code map} entries
     * 
     * <p>
     * precondition: neither {@link Map} can be {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param map
     *            the map containing entries to find
     * @param message
     *            the message if invalid
     * @param <M>
     *            the {@link Map} type
     * @param <K>
     *            the {@link Map} key elements type
     * @param <V>
     *            the {@link Map} value elements type
     * @return the next step
     */
    public static <M extends Map<K, V>, K, V> StepAssertor<M> containsAll(final StepAssertor<M> step, final Map<K, V> map,
            final MessageAssertor message) {

        return contains(step, map, MSG.MAP.CONTAINS_MAP_ALL, true, message);
    }

    /**
     * Prepare the next step to validate if the {@link Map} contains all
     * {@code keys} entries
     * 
     * <p>
     * precondition: neither {@link Map} and {@code keys} can be {@code null} or
     * empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param keys
     *            the keys to find
     * @param message
     *            the message if invalid
     * @param <M>
     *            the {@link Map} type
     * @param <K>
     *            the {@link Map} key elements type
     * @param <V>
     *            the {@link Map} value elements type
     * @return the next step
     */
    public static <M extends Map<K, V>, K, V> StepAssertor<M> containsAll(final StepAssertor<M> step, final Iterable<K> keys,
            final MessageAssertor message) {

        return contains(step, keys, MSG.MAP.CONTAINS_KEYS_ALL, true, message);
    }

    /**
     * Prepare the next step to validate if the {@link Map} contains all
     * {@code values} entries
     * 
     * <p>
     * precondition: neither {@link Map} and {@code values} can be {@code null}
     * or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param values
     *            the values to find
     * @param message
     *            the message if invalid
     * @param <M>
     *            the {@link Map} type
     * @param <K>
     *            the {@link Map} key elements type
     * @param <V>
     *            the {@link Map} value elements type
     * @return the next step
     */
    public static <M extends Map<K, V>, K, V> StepAssertor<M> containsAllValues(final StepAssertor<M> step, final Iterable<V> values,
            final MessageAssertor message) {

        return containsValues(step, values, MSG.MAP.CONTAINS_VALUES_ALL, true, message);
    }

    /**
     * Prepare the next step to validate if the {@link Map} contains any
     * {@code map} entries
     * 
     * <p>
     * precondition: neither {@link Map} can be {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param map
     *            the map containing entries to find
     * @param message
     *            the message if invalid
     * @param <M>
     *            the {@link Map} type
     * @param <K>
     *            the {@link Map} key elements type
     * @param <V>
     *            the {@link Map} value elements type
     * @return the next step
     */
    public static <M extends Map<K, V>, K, V> StepAssertor<M> containsAny(final StepAssertor<M> step, final Map<K, V> map,
            final MessageAssertor message) {

        return contains(step, map, MSG.MAP.CONTAINS_MAP_ANY, false, message);
    }

    /**
     * Prepare the next step to validate if the {@link Map} contains any
     * {@code keys} entries
     * 
     * <p>
     * precondition: neither {@link Map} and {@code keys} can be {@code null} or
     * empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param keys
     *            the keys to find
     * @param message
     *            the message if invalid
     * @param <M>
     *            the {@link Map} type
     * @param <K>
     *            the {@link Map} key elements type
     * @param <V>
     *            the {@link Map} value elements type
     * @return the next step
     */
    public static <M extends Map<K, V>, K, V> StepAssertor<M> containsAny(final StepAssertor<M> step, final Iterable<K> keys,
            final MessageAssertor message) {

        return contains(step, keys, MSG.MAP.CONTAINS_KEYS_ANY, false, message);
    }

    /**
     * Prepare the next step to validate if the {@link Map} contains any
     * {@code values} entries
     * 
     * <p>
     * precondition: neither {@link Map} and {@code values} can be {@code null}
     * or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param values
     *            the values to find
     * @param message
     *            the message if invalid
     * @param <M>
     *            the {@link Map} type
     * @param <K>
     *            the {@link Map} key elements type
     * @param <V>
     *            the {@link Map} value elements type
     * @return the next step
     */
    public static <M extends Map<K, V>, K, V> StepAssertor<M> containsAnyValues(final StepAssertor<M> step, final Iterable<V> values,
            final MessageAssertor message) {

        return containsValues(step, values, MSG.MAP.CONTAINS_VALUES_ANY, false, message);
    }

    /**
     * Prepare the next step to validate if the {@link Map} contains the
     * specified key
     * 
     * <p>
     * precondition: {@link Map} cannot be {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param key
     *            the key to find
     * @param message
     *            the message if invalid
     * @param <M>
     *            the {@link Map} type
     * @param <K>
     *            the {@link Map} key elements type
     * @param <V>
     *            the {@link Map} value elements type
     * @return the next step
     */
    public static <M extends Map<K, V>, K, V> StepAssertor<M> contains(final StepAssertor<M> step, final K key,
            final MessageAssertor message) {

        final Predicate<M> preChecker = MapUtils::isNotEmpty;

        final BiPredicate<M, Boolean> checker = (map, not) -> map.containsKey(key);

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.MAP.CONTAINS_KEY, false, new ParameterAssertor<>(key));
    }

    /**
     * Prepare the next step to validate if the {@link Map} contains the
     * specified pair key/value
     * 
     * <p>
     * precondition: {@link Map} cannot be {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param key
     *            the key to find
     * @param value
     *            the value to find
     * @param message
     *            the message if invalid
     * @param <M>
     *            the {@link Map} type
     * @param <K>
     *            the {@link Map} key elements type
     * @param <V>
     *            the {@link Map} value elements type
     * @return the next step
     */
    public static <M extends Map<K, V>, K, V> StepAssertor<M> contains(final StepAssertor<M> step, final K key, final V value,
            final MessageAssertor message) {

        final Predicate<M> preChecker = MapUtils::isNotEmpty;

        final BiPredicate<M, Boolean> checker = (map, not) -> AssertorMap.contains(map, key, value);

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.MAP.CONTAINS_KEY, false, new ParameterAssertor<>(key),
                new ParameterAssertor<>(value));
    }

    /**
     * Prepare the next step to validate if the {@link Map} contains the
     * specified value
     * 
     * <p>
     * precondition: {@link Map} cannot be {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param value
     *            the value to find
     * @param message
     *            the message if invalid
     * @param <M>
     *            the {@link Map} type
     * @param <K>
     *            the {@link Map} key elements type
     * @param <V>
     *            the {@link Map} value elements type
     * @return the next step
     */
    public static <M extends Map<K, V>, K, V> StepAssertor<M> containsValue(final StepAssertor<M> step, final V value,
            final MessageAssertor message) {

        final Predicate<M> preChecker = MapUtils::isNotEmpty;

        final BiPredicate<M, Boolean> checker = (map, not) -> map.containsValue(value);

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.MAP.CONTAINS_VALUE, false, new ParameterAssertor<>(value));
    }

    private static <M extends Map<K, V>, K, V> StepAssertor<M> contains(final StepAssertor<M> step, final Iterable<K> keys,
            final CharSequence key, final boolean all, final MessageAssertor message) {

        final Predicate<M> preChecker = (map) -> MapUtils.isNotEmpty(map) && !IterableUtils.isEmpty(keys);

        final BiPredicate<M, Boolean> checker = (map, not) -> AssertorMap.contains(map, keys, map::containsKey, all, not,
                step.getAnalysisMode());

        return new StepAssertor<>(step, preChecker, checker, true, message, key, false, new ParameterAssertor<>(keys, EnumType.ITERABLE));
    }

    private static <M extends Map<K, V>, K, V> StepAssertor<M> containsValues(final StepAssertor<M> step, final Iterable<V> values,
            final CharSequence key, final boolean all, final MessageAssertor message) {

        final Predicate<M> preChecker = (map) -> MapUtils.isNotEmpty(map) && !IterableUtils.isEmpty(values);

        final BiPredicate<M, Boolean> checker = (map, not) -> AssertorMap.contains(map, values, map::containsValue, all, not,
                step.getAnalysisMode());

        return new StepAssertor<>(step, preChecker, checker, true, message, key, false, new ParameterAssertor<>(values, EnumType.ITERABLE));
    }

    private static <M extends Map<K, V>, K, V> StepAssertor<M> contains(final StepAssertor<M> step, final Map<K, V> map,
            final CharSequence key, final boolean all, final MessageAssertor message) {

        final Predicate<M> preChecker = (map1) -> MapUtils.isNotEmpty(map1) && MapUtils.isNotEmpty(map);

        final BiPredicate<M, Boolean> checker = (map1, not) -> AssertorMap.contains(map1, map, all, not, step.getAnalysisMode());

        return new StepAssertor<>(step, preChecker, checker, true, message, key, false, new ParameterAssertor<>(map, EnumType.MAP));
    }

    private static <M extends Map<K, V>, K, V, T> boolean contains(final M map, final Iterable<T> objects, final Predicate<T> predicate,
            final boolean all, final boolean not, final EnumAnalysisMode analysisMode) {

        long found = 0;

        if (EnumAnalysisMode.STANDARD.equals(analysisMode)) {
            for (T object : objects) {
                if (predicate.test(object)) {
                    ++found;
                }
            }
        } else {
            found = StreamSupport.stream(objects.spliterator(), EnumAnalysisMode.PARALLEL.equals(analysisMode)).filter(predicate).count();
        }

        return HelperAssertor.isValid(all, not, found, IterableUtils.size(objects));
    }

    private static <M extends Map<K, V>, K, V> boolean contains(final M map, final Map<K, V> objects, final boolean all, final boolean not,
            final EnumAnalysisMode analysisMode) {

        final Set<Entry<K, V>> entries = objects.entrySet();
        if (EnumAnalysisMode.STANDARD.equals(analysisMode)) {
            if (all && !not) {
                for (final Entry<K, V> entry : entries) {
                    if (!AssertorMap.contains(map, entry.getKey(), entry.getValue())) {
                        return false;
                    }
                }
                return true;
            } else if (!all) { // any and not any
                for (final Entry<K, V> entry : entries) {
                    if (AssertorMap.contains(map, entry.getKey(), entry.getValue())) {
                        return !not;
                    }
                }
                return not;
            } else { // not all
                long found = 0;
                for (final Entry<K, V> entry : entries) {
                    if (AssertorMap.contains(map, entry.getKey(), entry.getValue())) {
                        ++found;
                    }
                }
                return HelperAssertor.isValid(all, not, found, entries.size());
            }
        } else {
            final Stream<Entry<K, V>> stream;
            if (EnumAnalysisMode.PARALLEL.equals(analysisMode)) {
                stream = entries.parallelStream();
            } else {
                stream = entries.stream();
            }

            return HelperAssertor.isValid(stream, e -> AssertorMap.contains(map, e.getKey(), e.getValue()), all, not, objects::size);
        }
    }

    private static <M extends Map<K, V>, K, V> boolean contains(final M map, final K key, final V value) {
        if (map.containsKey(key)) {
            V val = map.get(key);
            if (val != null) {
                return val.equals(value);
            } else {
                return value == null;
            }
        }
        return false;
    }

    /**
     * Prepare the next step to validate if the {@link Map} contains the entries
     * in a specified order. To work correctly, the map must be sorted or
     * linked.
     * 
     * <p>
     * precondition: {@link Map} and {@link Iterable} cannot be {@code null} or
     * empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param keys
     *            the keys to find
     * @param message
     *            the message if invalid
     * @param <M>
     *            the {@link Map} type
     * @param <K>
     *            the {@link Map} key elements type
     * @param <V>
     *            the {@link Map} value elements type
     * @return the next step
     */
    public static <M extends Map<K, V>, K, V> StepAssertor<M> containsKeysInOrder(final StepAssertor<M> step, final Iterable<K> keys,
            final MessageAssertor message) {

        final Predicate<M> preChecker = map1 -> MapUtils.isNotEmpty(map1) && !IterableUtils.isEmpty(keys);

        final BiPredicate<M, Boolean> checker = (map1, not) -> AssertorMap.hasInOrder(map1, keys, not, step.getAnalysisMode(),
                MapUtils2::areKeysEqual, CastUtils.cast(Object.class));

        return new StepAssertor<>(step, preChecker, checker, true, message, MSG.MAP.CONTAINS_KEYS_IN_ORDER, false,
                new ParameterAssertor<>(keys, EnumType.ITERABLE));
    }

    /**
     * Prepare the next step to validate if the {@link Map} contains the entries
     * in a specified order. To work correctly, the map must be sorted or
     * linked.
     * 
     * <p>
     * precondition: {@link Map} and {@link Iterable} cannot be {@code null} or
     * empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param values
     *            the values to find
     * @param message
     *            the message if invalid
     * @param <M>
     *            the {@link Map} type
     * @param <K>
     *            the {@link Map} key elements type
     * @param <V>
     *            the {@link Map} value elements type
     * @return the next step
     */
    public static <M extends Map<K, V>, K, V> StepAssertor<M> containsValuesInOrder(final StepAssertor<M> step, final Iterable<V> values,
            final MessageAssertor message) {

        final Predicate<M> preChecker = map1 -> MapUtils.isNotEmpty(map1) && !IterableUtils.isEmpty(values);

        final BiPredicate<M, Boolean> checker = (map1, not) -> AssertorMap.hasInOrder(map1, values, not, step.getAnalysisMode(),
                MapUtils2::areValuesEqual, CastUtils.cast(Object.class));

        return new StepAssertor<>(step, preChecker, checker, true, message, MSG.MAP.CONTAINS_VALUES_IN_ORDER, false,
                new ParameterAssertor<>(values, EnumType.ITERABLE));
    }

    /**
     * Prepare the next step to validate if the {@link Map} contains the entries
     * in a specified order. To work correctly, the map must be sorted or
     * linked.
     * 
     * <p>
     * precondition: {@link Map} cannot be {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param map
     *            the map entries to find
     * @param message
     *            the message if invalid
     * @param <M>
     *            the {@link Map} type
     * @param <K>
     *            the {@link Map} key elements type
     * @param <V>
     *            the {@link Map} value elements type
     * @return the next step
     */
    public static <M extends Map<K, V>, K, V> StepAssertor<M> containsInOrder(final StepAssertor<M> step, final Map<K, V> map,
            final MessageAssertor message) {

        final Predicate<M> preChecker = map1 -> MapUtils.isNotEmpty(map1) && MapUtils.isNotEmpty(map);

        final BiPredicate<M, Boolean> checker = (map1, not) -> AssertorMap.hasInOrder(map1, IterableUtils.toList(map.entrySet()), not,
                step.getAnalysisMode(), MapUtils2::areEntriesEqual, CastUtils.cast(Entry.class));

        return new StepAssertor<>(step, preChecker, checker, true, message, MSG.MAP.CONTAINS_MAP_IN_ORDER, false,
                new ParameterAssertor<>(map, EnumType.MAP));
    }

    private static <M extends Map<K, V>, K, V, T> boolean hasInOrder(final M map, final Iterable<T> objects, final boolean not,
            final EnumAnalysisMode analysisMode, final BiPredicate<Entry<K, V>, T> entriesEqualChecker, final Class<T> objectsClass) {

        int found = 0;

        final int size1 = map.size();
        final int size2 = IterableUtils.size(objects);

        if (size1 < size2) {
            return not;
        }

        final Set<Entry<K, V>> entries1 = map.entrySet();
        final List<T> entries2 = IterableUtils.toList(objects);

        if (EnumAnalysisMode.STANDARD.equals(analysisMode)) {
            for (Entry<K, V> entry1 : entries1) {
                if (found < size2) {
                    if (entriesEqualChecker.test(entry1, entries2.get(found))) {
                        ++found;
                    } else if (found > 0) {
                        found = 0;
                    }
                }
            }
        } else {
            final AtomicInteger count = new AtomicInteger(0);

            final Stream<Entry<K, V>> stream;
            if (EnumAnalysisMode.PARALLEL.equals(analysisMode)) {
                stream = entries1.parallelStream();
            } else {
                stream = entries1.stream();
            }

            stream.forEachOrdered(o -> {
                int inc = count.get();
                if (inc < size2) {
                    if (entriesEqualChecker.test(o, entries2.get(inc))) {
                        count.incrementAndGet();
                    } else if (inc > 0) {
                        count.set(0);
                    }
                }
            });

            found = count.get();
        }

        return not ^ (found == size2);
    }
}
