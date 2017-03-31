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

import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * The base class to start an assertor chain. The 'that' and 'matcher...'
 * methods are defined here for all managed types.
 * 
 * <p>
 * "Assertor.that" creates a validator for the specified value.
 * "Assertor.matcher..." creates a validator that can be used later with "on"
 * method.
 * </p>
 * 
 * <pre>
 * Assertor.that(12).isGT(10).orElseThrow();
 * 
 * PredicateStepNumber&lt;Integer&gt; predicate = Assertor.matcherNumber(Integer.class).isGT(10);
 * // ...
 * predicate.that(12).orElseThrow();
 * </pre>
 * 
 * <p>
 * Global locale can also be defined here. The locale will be used for decimal
 * conversion for example (see {@link String#format}). This locale is used if no
 * locale is defined for the error message.
 * </p>
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 */
public class Assertor {

    /**
     * default locale
     */
    private static Locale locale = Locale.getDefault();

    /**
     * First step to check an object.
     * 
     * @param t
     *            the object to check
     * @param <S>
     *            the predicate step type
     * @param <T>
     *            the type of object under checking
     * @return the predicate assertor
     */
    public static <S extends PredicateStep<S, T>, T> PredicateAssertor<S, T> that(final T t) {
        return that(t, null);
    }

    /**
     * First step to check an object.
     * 
     * @param t
     *            the object to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <S>
     *            the predicate step type
     * @param <T>
     *            the type of object under checking
     * @return the predicate assertor
     */
    public static <S extends PredicateStep<S, T>, T> PredicateAssertor<S, T> that(final T t, final EnumAnalysisMode analysisMode) {
        return () -> new StepAssertor<>(t, EnumType.getType(t), analysisMode);
    }

    /**
     * First step to check a {@link Class}.
     * 
     * @param clazz
     *            the {@link Class} to check
     * @param <T>
     *            the type of {@link Class} under checking
     * @return the predicate {@link Class} assertor
     */
    public static <T> PredicateAssertorClass<T> that(final Class<T> clazz) {
        return that(clazz, null);
    }

    /**
     * First step to check a {@link Class}.
     * 
     * @param clazz
     *            the {@link Class} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <T>
     *            the type of {@link Class} under checking
     * @return the predicate {@link Class} assertor
     */
    public static <T> PredicateAssertorClass<T> that(final Class<T> clazz, final EnumAnalysisMode analysisMode) {
        return () -> new StepAssertor<>(clazz, EnumType.CLASS, analysisMode);
    }

    /**
     * First step to check a {@link Boolean}.
     * 
     * @param bool
     *            the {@link Boolean} to check
     * @return the predicate {@link Boolean} assertor
     */
    public static PredicateAssertorBoolean that(final Boolean bool) {
        return that(bool, null);
    }

    /**
     * First step to check a {@link Boolean}.
     * 
     * @param bool
     *            the {@link Boolean} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @return the predicate {@link Boolean} assertor
     */
    public static PredicateAssertorBoolean that(final Boolean bool, final EnumAnalysisMode analysisMode) {
        return () -> new StepAssertor<>(bool, EnumType.BOOLEAN, analysisMode);
    }

    /**
     * First step to check a {@link Number}.
     * 
     * @param number
     *            the {@link Number} to check
     * @param <N>
     *            the type of {@link Number} under checking
     * @return the predicate {@link Number} assertor
     */
    public static <N extends Number & Comparable<N>> PredicateAssertorNumber<N> that(final N number) {
        return that(number, null);
    }

    /**
     * First step to check a {@link Number}.
     * 
     * @param number
     *            the {@link Number} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <N>
     *            the type of {@link Number} under checking
     * @return the predicate {@link Number} assertor
     */
    public static <N extends Number & Comparable<N>> PredicateAssertorNumber<N> that(final N number, final EnumAnalysisMode analysisMode) {
        return () -> new StepAssertor<>(number, EnumType.getType(number), analysisMode);
    }

    /**
     * First step to check a {@link CharSequence}.
     * 
     * @param charSequence
     *            the {@link CharSequence} to check
     * @param <T>
     *            the type of {@link CharSequence} under checking
     * @return the predicate {@link CharSequence} assertor
     */
    public static <T extends CharSequence> PredicateAssertorCharSequence<T> that(final T charSequence) {
        return that(charSequence, null);
    }

    /**
     * First step to check a {@link CharSequence}.
     * 
     * @param charSequence
     *            the {@link CharSequence} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <T>
     *            the type of {@link CharSequence} under checking
     * @return the predicate {@link CharSequence} assertor
     */
    public static <T extends CharSequence> PredicateAssertorCharSequence<T> that(final T charSequence,
            final EnumAnalysisMode analysisMode) {
        return () -> new StepAssertor<>(charSequence, EnumType.CHAR_SEQUENCE, analysisMode);
    }

    /**
     * First step to check an array.
     * 
     * @param array
     *            the array to check
     * @param <T>
     *            the type of array under checking
     * @return the predicate array assertor
     */
    public static <T> PredicateAssertorArray<T> that(final T[] array) {
        return that(array, null);
    }

    /**
     * First step to check an array.
     * 
     * @param array
     *            the array to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <T>
     *            the type of array under checking
     * @return the predicate array assertor
     */
    public static <T> PredicateAssertorArray<T> that(final T[] array, final EnumAnalysisMode analysisMode) {
        return () -> new StepAssertor<>(array, EnumType.ARRAY, analysisMode);
    }

    /**
     * First step to check an {@link Iterable}.
     * 
     * @param iterable
     *            the {@link Iterable} to check
     * @param <I>
     *            the {@link Iterable} type
     * @param <T>
     *            the {@link Iterable} elements type
     * @return the predicate {@link Iterable} assertor
     */
    public static <I extends Iterable<T>, T> PredicateAssertorIterable<I, T> that(final I iterable) {
        return that(iterable, null);
    }

    /**
     * First step to check an {@link Iterable}.
     * 
     * @param iterable
     *            the {@link Iterable} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <I>
     *            the {@link Iterable} type
     * @param <T>
     *            the {@link Iterable} elements type
     * @return the predicate {@link Iterable} assertor
     */
    public static <I extends Iterable<T>, T> PredicateAssertorIterable<I, T> that(final I iterable, final EnumAnalysisMode analysisMode) {
        return () -> new StepAssertor<>(iterable, EnumType.ITERABLE, analysisMode);
    }

    /**
     * First step to check a {@link Map}.
     * 
     * @param map
     *            the {@link Map} to check
     * @param <K>
     *            the type of map's key under checking
     * @param <V>
     *            the type of map's value under checking
     * @return the predicate {@link Map} assertor
     */
    public static <K, V> PredicateAssertorMap<K, V> that(final Map<K, V> map) {
        return that(map, null);
    }

    /**
     * First step to check a {@link Map}.
     * 
     * @param map
     *            the {@link Map} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <K>
     *            the type of map's key under checking
     * @param <V>
     *            the type of map's value under checking
     * @return the predicate {@link Map} assertor
     */
    public static <K, V> PredicateAssertorMap<K, V> that(final Map<K, V> map, final EnumAnalysisMode analysisMode) {
        return () -> new StepAssertor<>(map, EnumType.MAP, analysisMode);
    }

    /**
     * First step to check an {@link Enum}.
     * 
     * @param enumeration
     *            the {@link Enum} to check
     * @param <T>
     *            the type of {@link Enum} under checking
     * @return the predicate {@link Enum} assertor
     */
    public static <T extends Enum<T>> PredicateAssertorEnum<T> that(final T enumeration) {
        return that(enumeration, null);
    }

    /**
     * First step to check an {@link Enum}.
     * 
     * @param enumeration
     *            the {@link Enum} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <T>
     *            the type of {@link Enum} under checking
     * @return the predicate {@link Enum} assertor
     */
    public static <T extends Enum<T>> PredicateAssertorEnum<T> that(final T enumeration, final EnumAnalysisMode analysisMode) {
        return () -> new StepAssertor<>(enumeration, EnumType.ENUMERATION, analysisMode);
    }

    /**
     * First step to check a {@link Date}.
     * 
     * @param date
     *            the {@link Date} to check
     * @return the predicate {@link Date} assertor
     */
    public static PredicateAssertorDate that(final Date date) {
        return that(date, null);
    }

    /**
     * First step to check a {@link Date}.
     * 
     * @param date
     *            the {@link Date} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @return the predicate {@link Date} assertor
     */
    public static PredicateAssertorDate that(final Date date, final EnumAnalysisMode analysisMode) {
        return () -> new StepAssertor<>(date, EnumType.DATE, analysisMode);
    }

    /**
     * First step to check a {@link Calendar}.
     * 
     * @param calendar
     *            the {@link Calendar} to check
     * @return the predicate {@link Calendar} assertor
     */
    public static PredicateAssertorCalendar that(final Calendar calendar) {
        return that(calendar, null);
    }

    /**
     * First step to check a {@link Calendar}.
     * 
     * @param calendar
     *            the {@link Calendar} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @return the predicate {@link Calendar} assertor
     */
    public static PredicateAssertorCalendar that(final Calendar calendar, final EnumAnalysisMode analysisMode) {
        return () -> new StepAssertor<>(calendar, EnumType.CALENDAR, analysisMode);
    }

    /**
     * First step to check a comparable {@link Temporal}.
     * 
     * @param temporal
     *            the {@link Temporal} to check
     * @param <T>
     *            the type of {@link Temporal}
     * @return the predicate {@link Temporal} assertor
     */
    public static <T extends Temporal & Comparable<T>> PredicateAssertorTemporal<T> that(final T temporal) {
        return that(temporal, null);
    }

    /**
     * First step to check a comparable {@link Temporal}.
     * 
     * @param temporal
     *            the {@link Temporal} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <T>
     *            the type of {@link Temporal}
     * @return the predicate {@link Temporal} assertor
     */
    public static <T extends Temporal & Comparable<T>> PredicateAssertorTemporal<T> that(final T temporal,
            final EnumAnalysisMode analysisMode) {
        return () -> new StepAssertor<>(temporal, EnumType.TEMPORAL, analysisMode);
    }

    /**
     * First step to check a comparable {@link Throwable}.
     * 
     * @param throwable
     *            the {@link Throwable} to check
     * @param <T>
     *            the type of {@link Throwable}
     * @return the predicate {@link Throwable} assertor
     */
    public static <T extends Throwable> PredicateAssertorThrowable<T> that(final T throwable) {
        return that(throwable, null);
    }

    /**
     * First step to check a comparable {@link Throwable}.
     * 
     * @param throwable
     *            the {@link Throwable} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <T>
     *            the type of {@link Throwable}
     * @return the predicate {@link Throwable} assertor
     */
    public static <T extends Throwable> PredicateAssertorThrowable<T> that(final T throwable, final EnumAnalysisMode analysisMode) {
        return () -> new StepAssertor<>(throwable, EnumType.THROWABLE, analysisMode);
    }

    /**
     * Create a predicate Assertor for {@link Object} Matcher
     * 
     * @param type
     *            the object type
     * @param <S>
     *            the type of predicate step
     * @param <T>
     *            the type of object
     * @return the predicate assertor for {@link Object}
     */
    public static <S extends PredicateStep<S, T>, T> PredicateAssertor<S, T> matcherObject(final Class<T> type) {
        return matcherObject(type, null);
    }

    /**
     * Create a predicate Assertor for {@link Object} Matcher
     * 
     * @param type
     *            the object type
     * @param <S>
     *            the type of predicate step
     * @param <T>
     *            the type of object
     * @param analysisMode
     *            the preferred analysis mode
     * @return the predicate assertor for {@link Object}
     */
    public static <S extends PredicateStep<S, T>, T> PredicateAssertor<S, T> matcherObject(final Class<T> type,
            final EnumAnalysisMode analysisMode) {
        return () -> new StepAssertor<>(EnumType.getTypeFromClass(Objects.requireNonNull(type), null), analysisMode);
    }

    /**
     * Create a predicate Assertor for {@link Number} Matcher
     * 
     * @param type
     *            the checked object type
     * @param <N>
     *            the number type
     * @return the predicate assertor for {@link Number}
     */
    public static <N extends Number & Comparable<N>> PredicateAssertorNumber<N> matcherNumber(final Class<N> type) {
        return matcherNumber(type, null);
    }

    /**
     * Create a predicate Assertor for {@link Number} Matcher
     * 
     * @param type
     *            the checked object type
     * @param analysisMode
     *            the preferred analysis mode
     * @param <N>
     *            the number type
     * @return the predicate assertor for {@link Number}
     */
    public static <N extends Number & Comparable<N>> PredicateAssertorNumber<N> matcherNumber(final Class<N> type,
            final EnumAnalysisMode analysisMode) {
        return () -> new StepAssertor<>(EnumType.getTypeFromClass(Objects.requireNonNull(type), null), analysisMode);
    }

    /**
     * Create a predicate Assertor for {@link Class} Matcher
     * 
     * @param type
     *            the type of class
     * @param <T>
     *            the type of class
     * @return the predicate assertor for {@link Class}
     */
    public static <T> PredicateAssertorClass<T> matcherClass(final Class<T> type) {
        return matcherClass(type, null);
    }

    /**
     * Create a predicate Assertor for {@link Class} Matcher
     * 
     * @param type
     *            the type of class
     * @param analysisMode
     *            the preferred analysis mode
     * @param <T>
     *            the type of class
     * @return the predicate assertor for {@link Class}
     */
    public static <T> PredicateAssertorClass<T> matcherClass(final Class<T> type, final EnumAnalysisMode analysisMode) {
        Objects.requireNonNull(type);
        return () -> new StepAssertor<>(EnumType.CLASS, analysisMode);
    }

    /**
     * Create a predicate Assertor for {@link CharSequence} Matcher
     * 
     * @param type
     *            the type of char sequence
     * @param <T>
     *            the type of char sequence
     * @return the predicate assertor for {@link CharSequence}
     */
    public static <T extends CharSequence> PredicateAssertorCharSequence<T> matcherCharSequence(final Class<T> type) {
        return matcherCharSequence(type, null);
    }

    /**
     * Create a predicate Assertor for {@link CharSequence} Matcher
     * 
     * @param type
     *            the type of char sequence
     * @param analysisMode
     *            the preferred analysis mode
     * @param <T>
     *            the type of char sequence
     * @return the predicate assertor for {@link CharSequence}
     */
    public static <T extends CharSequence> PredicateAssertorCharSequence<T> matcherCharSequence(final Class<T> type,
            final EnumAnalysisMode analysisMode) {
        Objects.requireNonNull(type);
        return () -> new StepAssertor<>(EnumType.CHAR_SEQUENCE, analysisMode);
    }

    /**
     * Create a predicate Assertor for {@link Boolean} Matcher
     * 
     * @return the predicate assertor for {@link Boolean}
     */
    public static PredicateAssertorBoolean matcherBoolean() {
        return matcherBoolean(null);
    }

    /**
     * Create a predicate Assertor for {@link Boolean} Matcher
     * 
     * @param analysisMode
     *            the preferred analysis mode
     * @return the predicate assertor for {@link Boolean}
     */
    public static PredicateAssertorBoolean matcherBoolean(final EnumAnalysisMode analysisMode) {
        return () -> new StepAssertor<>(EnumType.BOOLEAN, analysisMode);
    }

    /**
     * Create a predicate Assertor for {@link Date} Matcher
     * 
     * @return the predicate assertor for {@link Date}
     */
    public static PredicateAssertorDate matcherDate() {
        return matcherDate(null);
    }

    /**
     * Create a predicate Assertor for {@link Date} Matcher
     * 
     * @param analysisMode
     *            the preferred analysis mode
     * @return the predicate assertor for {@link Date}
     */
    public static PredicateAssertorDate matcherDate(final EnumAnalysisMode analysisMode) {
        return () -> new StepAssertor<>(EnumType.DATE, analysisMode);
    }

    /**
     * Create a predicate Assertor for {@link Calendar} Matcher
     * 
     * @return the predicate assertor for {@link Calendar}
     */
    public static PredicateAssertorDate matcherCalendar() {
        return matcherCalendar(null);
    }

    /**
     * Create a predicate Assertor for {@link Calendar} Matcher
     * 
     * @param analysisMode
     *            the preferred analysis mode
     * @return the predicate assertor for {@link Calendar}
     */
    public static PredicateAssertorDate matcherCalendar(final EnumAnalysisMode analysisMode) {
        return () -> new StepAssertor<>(EnumType.CALENDAR, analysisMode);
    }

    /**
     * Create a predicate Assertor for {@link Enum} Matcher
     * 
     * @param type
     *            the type of enumeration
     * @param <E>
     *            the type of enumeration
     * @return the predicate assertor for {@link Enum}
     */
    public static <E extends Enum<E>> PredicateAssertorEnum<E> matcherEnum(final Class<E> type) {
        return matcherEnum(type, null);
    }

    /**
     * Create a predicate Assertor for {@link Enum} Matcher
     * 
     * @param type
     *            the type of enumeration
     * @param analysisMode
     *            the preferred analysis mode
     * @param <E>
     *            the type of enumeration
     * @return the predicate assertor for {@link Enum}
     */
    public static <E extends Enum<E>> PredicateAssertorEnum<E> matcherEnum(final Class<E> type, final EnumAnalysisMode analysisMode) {
        Objects.requireNonNull(type);
        return () -> new StepAssertor<>(EnumType.ENUMERATION, analysisMode);
    }

    /**
     * Create a predicate Assertor for {@link Iterable} Matcher
     * 
     * @param type
     *            the type of iterable
     * @param <I>
     *            the type of iterable
     * @param <T>
     *            the type of iterable elements
     * @return the predicate assertor for {@link Iterable}
     */
    public static <I extends Iterable<T>, T> PredicateAssertorIterable<I, T> matcherIterable(final Class<I> type) {
        return matcherIterable(type, null);
    }

    /**
     * Create a predicate Assertor for {@link Iterable} Matcher
     * 
     * @param type
     *            the type of iterable
     * @param analysisMode
     *            the preferred analysis mode
     * @param <I>
     *            the type of iterable
     * @param <T>
     *            the type of iterable elements
     * @return the predicate assertor for {@link Iterable}
     */
    public static <I extends Iterable<T>, T> PredicateAssertorIterable<I, T> matcherIterable(final Class<I> type,
            final EnumAnalysisMode analysisMode) {
        Objects.requireNonNull(type);
        return () -> new StepAssertor<>(EnumType.ITERABLE, analysisMode);
    }

    /**
     * Create a predicate Assertor for {@link Map} Matcher
     * 
     * @param keyType
     *            the type of keys
     * @param valueType
     *            the type of values
     * @param <K>
     *            the type of keys in map
     * @param <V>
     *            the type of values in map
     * @return the predicate assertor for {@link Map}
     */
    public static <K, V> PredicateAssertorMap<K, V> matcherMap(final Class<K> keyType, final Class<V> valueType) {
        return matcherMap(keyType, valueType, null);
    }

    /**
     * Create a predicate Assertor for {@link Map} Matcher
     * 
     * @param keyType
     *            the type of keys
     * @param valueType
     *            the type of values
     * @param analysisMode
     *            the preferred analysis mode
     * @param <K>
     *            the type of keys in map
     * @param <V>
     *            the type of values in map
     * @return the predicate assertor for {@link Map}
     */
    public static <K, V> PredicateAssertorMap<K, V> matcherMap(final Class<K> keyType, final Class<V> valueType,
            final EnumAnalysisMode analysisMode) {
        Objects.requireNonNull(keyType);
        Objects.requireNonNull(valueType);
        return () -> new StepAssertor<>(EnumType.MAP, analysisMode);
    }

    /**
     * Create a predicate Assertor for {@link Temporal} Matcher
     * 
     * @param type
     *            the type of temporal
     * @param <T>
     *            the type of temporal
     * @return the predicate assertor for {@link Temporal}
     */
    public static <T extends Temporal & Comparable<T>> PredicateAssertorTemporal<T> matcherTemporal(final Class<T> type) {
        return matcherTemporal(type, null);
    }

    /**
     * Create a predicate Assertor for {@link Temporal} Matcher
     * 
     * @param type
     *            the type of temporal
     * @param analysisMode
     *            the preferred analysis mode
     * @param <T>
     *            the type of temporal
     * @return the predicate assertor for {@link Temporal}
     */
    public static <T extends Temporal & Comparable<T>> PredicateAssertorTemporal<T> matcherTemporal(final Class<T> type,
            final EnumAnalysisMode analysisMode) {
        Objects.requireNonNull(type);
        return () -> new StepAssertor<>(EnumType.TEMPORAL, analysisMode);
    }

    /**
     * Create a predicate Assertor for array Matcher
     * 
     * @param type
     *            the type of array elements
     * @param <T>
     *            the type of array elements
     * @return the predicate assertor for array
     */
    public static <T> PredicateAssertorArray<T[]> matcherArray(final Class<T> type) {
        return matcherArray(type, null);
    }

    /**
     * Create a predicate Assertor for array Matcher
     * 
     * @param type
     *            the type of array elements
     * @param analysisMode
     *            the preferred analysis mode
     * @param <T>
     *            the type of array elements
     * @return the predicate assertor for array
     */
    public static <T> PredicateAssertorArray<T[]> matcherArray(final Class<T> type, final EnumAnalysisMode analysisMode) {
        Objects.requireNonNull(type);
        return () -> new StepAssertor<>(EnumType.ARRAY, analysisMode);
    }

    /**
     * Create a predicate Assertor for {@link Throwable} Matcher
     * 
     * @param type
     *            the type of throwable
     * @param <T>
     *            the type of throwable
     * @return the predicate assertor for {@link Throwable}
     */
    public static <T extends Throwable> PredicateAssertorThrowable<T> matcherThrowable(final Class<T> type) {
        return matcherThrowable(type, null);
    }

    /**
     * Create a predicate Assertor for {@link Throwable} Matcher
     * 
     * @param type
     *            the type of throwable
     * @param analysisMode
     *            the preferred analysis mode
     * @param <T>
     *            the type of throwable
     * @return the predicate assertor for {@link Throwable}
     */
    public static <T extends Throwable> PredicateAssertorThrowable<T> matcherThrowable(final Class<T> type,
            final EnumAnalysisMode analysisMode) {
        Objects.requireNonNull(type);
        return () -> new StepAssertor<>(EnumType.THROWABLE, analysisMode);
    }

    /**
     * Get the global {@link Locale} used to generate messages of exceptions.
     * 
     * @return the {@link Locale}
     */
    public static final Locale getLocale() {
        return Assertor.locale;
    }

    /**
     * Get the global {@link Locale} used to generate messages of exceptions.
     * 
     * @param locale
     *            The {@link Locale}
     * @return the {@link Locale} if not null, otherwise the default one
     */
    public static final Locale getLocale(final Locale locale) {
        if (locale != null) {
            return locale;
        }
        return Assertor.locale;
    }

    /**
     * Define the default {@link Locale} for the assertor. Be aware in
     * multi-threading context, the {@link Locale} is static...
     * 
     * @param locale
     *            the {@link Locale} to set
     */
    public static final void setLocale(final Locale locale) {
        Assertor.locale = locale;
    }
}
