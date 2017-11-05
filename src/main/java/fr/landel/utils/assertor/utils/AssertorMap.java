/*-
 * #%L
 * utils-assertor
 * %%
 * Copyright (C) 2016 - 2017 Gilles Landel
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

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Spliterator;
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

/**
 * Utility class to prepare the check of {@link Map}
 *
 * @since Aug 10, 2016
 * @author Gilles
 *
 */
public class AssertorMap extends ConstantsAssertor {

    /**
     * Prepare the next step to validate the {@link Map} size
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

        final Predicate<M> preChecker = (map) -> size >= 0 && map != null;

        final BiPredicate<M, Boolean> checker = (map, not) -> map.size() == size;

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.MAP.SIZE, false,
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

        final Predicate<M> preChecker = (map) -> MapUtils.isNotEmpty(map);

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

        final Predicate<M> preChecker = (map) -> MapUtils.isNotEmpty(map);

        final BiPredicate<M, Boolean> checker = (map, not) -> AssertorMap.contains(map, key, value);

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.MAP.CONTAINS_KEY, false, new ParameterAssertor<>(key),
                new ParameterAssertor<>(value));
    }

    private static <M extends Map<K, V>, K, V> StepAssertor<M> contains(final StepAssertor<M> step, final Iterable<K> keys,
            final CharSequence key, final boolean all, final MessageAssertor message) {

        final Predicate<M> preChecker = (map) -> MapUtils.isNotEmpty(map) && !IterableUtils.isEmpty(keys);

        final BiPredicate<M, Boolean> checker = (map, not) -> AssertorMap.contains(map, keys, all, not, step.getAnalysisMode());

        return new StepAssertor<>(step, preChecker, checker, true, message, key, false, new ParameterAssertor<>(keys, EnumType.ITERABLE));
    }

    private static <M extends Map<K, V>, K, V> StepAssertor<M> contains(final StepAssertor<M> step, final Map<K, V> map,
            final CharSequence key, final boolean all, final MessageAssertor message) {

        final Predicate<M> preChecker = (map1) -> MapUtils.isNotEmpty(map1) && MapUtils.isNotEmpty(map);

        final BiPredicate<M, Boolean> checker = (map1, not) -> AssertorMap.contains(map1, map, all, not, step.getAnalysisMode());

        return new StepAssertor<>(step, preChecker, checker, true, message, key, false, new ParameterAssertor<>(map, EnumType.MAP));
    }

    private static <M extends Map<K, V>, K, V> boolean contains(final M map, final Iterable<K> keys, final boolean all, final boolean not,
            final EnumAnalysisMode analysisMode) {
        long found = 0;

        final Spliterator<K> spliterator = keys.spliterator();
        if (EnumAnalysisMode.STANDARD.equals(analysisMode)) {
            for (K key : keys) {
                if (map.containsKey(key)) {
                    ++found;
                }
            }
        } else {
            found = StreamSupport.stream(spliterator, EnumAnalysisMode.PARALLEL.equals(analysisMode)).filter(o -> map.containsKey(o))
                    .count();
        }

        return HelperAssertor.isValid(all, not, found, IterableUtils.size(keys));
    }

    private static <M extends Map<K, V>, K, V> boolean contains(final M map, final Map<K, V> objects, final boolean all, final boolean not,
            final EnumAnalysisMode analysisMode) {
        long found = 0;

        final Set<Entry<K, V>> entries = objects.entrySet();
        if (EnumAnalysisMode.STANDARD.equals(analysisMode)) {
            for (Entry<K, V> entry : entries) {
                if (AssertorMap.contains(map, entry.getKey(), entry.getValue())) {
                    ++found;
                }
            }
        } else {
            final Stream<Entry<K, V>> stream;
            if (EnumAnalysisMode.PARALLEL.equals(analysisMode)) {
                stream = entries.parallelStream();
            } else {
                stream = entries.stream();
            }
            found = stream.filter(e -> AssertorMap.contains(map, e.getKey(), e.getValue())).count();
        }

        return HelperAssertor.isValid(all, not, found, objects.size());
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
}
