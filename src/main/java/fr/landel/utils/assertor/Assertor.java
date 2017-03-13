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

/**
 * The base class to start an assertor chain. The 'that' method is defined here
 * for all managed types.
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
        return () -> new StepAssertor<>(t, EnumType.getType(t));
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
        return () -> new StepAssertor<>(clazz, EnumType.CLASS);
    }

    /**
     * First step to check a {@link Boolean}.
     * 
     * @param bool
     *            the {@link Boolean} to check
     * @return the predicate {@link Boolean} assertor
     */
    public static PredicateAssertorBoolean that(final Boolean bool) {
        return () -> new StepAssertor<>(bool, EnumType.BOOLEAN);
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
        return () -> new StepAssertor<>(number, EnumType.getType(number));
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
        return () -> new StepAssertor<>(charSequence, EnumType.CHAR_SEQUENCE);
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
        return () -> new StepAssertor<>(array, EnumType.ARRAY);
    }

    /**
     * First step to check an {@link Iterable}.
     * 
     * @param iterable
     *            the {@link Iterable} to check
     * @param <T>
     *            the type of {@link Iterable} under checking
     * @return the predicate {@link Iterable} assertor
     */
    public static <T> PredicateAssertorIterable<T> that(final Iterable<T> iterable) {
        return () -> new StepAssertor<>(iterable, EnumType.ITERABLE);
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
        return () -> new StepAssertor<>(map, EnumType.MAP);
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
        return () -> new StepAssertor<>(enumeration, EnumType.ENUMERATION);
    }

    /**
     * First step to check a {@link Date}.
     * 
     * @param date
     *            the {@link Date} to check
     * @return the predicate {@link Date} assertor
     */
    public static PredicateAssertorDate that(final Date date) {
        return () -> new StepAssertor<>(date, EnumType.DATE);
    }

    /**
     * First step to check a {@link Calendar}.
     * 
     * @param calendar
     *            the {@link Calendar} to check
     * @return the predicate {@link Calendar} assertor
     */
    public static PredicateAssertorCalendar that(final Calendar calendar) {
        return () -> new StepAssertor<>(calendar, EnumType.CALENDAR);
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
        return () -> new StepAssertor<>(temporal, EnumType.TEMPORAL);
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
