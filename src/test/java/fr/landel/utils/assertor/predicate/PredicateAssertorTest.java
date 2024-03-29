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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import fr.landel.utils.assertor.AbstractTest;
import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.enums.EnumType;
import fr.landel.utils.commons.DateUtils;
import fr.landel.utils.commons.MapUtils2;

public class PredicateAssertorTest extends AbstractTest {

    // private static final Logger LOGGER =
    // LoggerFactory.getLogger(PredicateAssertorTest.class);

    /**
     * Test for method {@link PredicateStep#that(Object)}
     */
    @Test
    public void testMatches() {
        final PredicateStepNumber<Integer> predicate = Assertor.<Integer> ofNumber().isGT(13).or().isZero();

        Assertor.ofThrowable().hasCauseNotNull().that(new IOException(new IllegalArgumentException())).orElseThrow(JUNIT_THROWABLE);

        assertTrue(predicate.that(158).isOK());
        assertFalse(predicate.that(12).isOK());
        assertTrue(predicate.that(158).isOK());

        Assertor.<Integer> ofNumber().isGT(13).and(Assertor.<Integer> ofNumber().isGT(10)).and().isEqual(11).that(15).isOK();
        Assertor.<Integer> ofNumber().isGT(13).and(Assertor.<Integer> ofNumber().isGT(18)).and().isEqual(11).that(15).isOK();

        Assertor.ofBoolean().isFalse().that(false).orElseThrow(JUNIT_THROWABLE);
        Assertor.<String> ofCharSequence().startsWith("t").that("test").orElseThrow(JUNIT_THROWABLE);
        Assertor.ofThrowable().hasCauseAssignableFrom(IllegalArgumentException.class, false)
                .that(new IOException(new IllegalArgumentException())).orElseThrow(JUNIT_THROWABLE);
        Assertor.<String> ofArray().hasLength(1).that(new String[] {""}).orElseThrow(JUNIT_THROWABLE);

        final PredicateStepIterable<List<String>, String> predicateIterable = Assertor.<List<String>, String> ofIterable().contains("");
        predicateIterable.that(Arrays.asList("")).orElseThrow(JUNIT_THROWABLE);

        Assertor.ofCalendar().isAfter(new GregorianCalendar(2000, 0, 1)).that(Calendar.getInstance()).orElseThrow(JUNIT_THROWABLE);
        Assertor.ofDate().isAfter(new GregorianCalendar(2000, 0, 1).getTime()).that(new Date()).orElseThrow(JUNIT_THROWABLE);

        final Date date1 = new Date(1464475553640L);
        final Date date2 = new Date(1464475553641L);
        final LocalDateTime localDateTime1 = DateUtils.getLocalDateTime(date1);
        final LocalDateTime localDateTime2 = DateUtils.getLocalDateTime(date2);
        Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAfter(localDateTime1).that(localDateTime2).orElseThrow(JUNIT_THROWABLE);

        Assertor.<String, Integer> ofMap().contains("test").that(MapUtils2.newHashMap("test", 12)).orElseThrow(JUNIT_THROWABLE);

        Assertor.<EnumType> ofEnum().hasName("UNKNOWN").that(EnumType.UNKNOWN).orElseThrow(JUNIT_THROWABLE);

        Assertor.<IOException> ofClass().isAssignableFrom(Exception.class).that(IOException.class).orElseThrow(JUNIT_THROWABLE);

        final PredicateStep<?, Object> predicateColor = Assertor.ofObject().isNotNull();
        predicateColor.that(Color.BLACK).orElseThrow(JUNIT_THROWABLE);

        final PredicateStepArray<Integer> predicateIntegers = Assertor.<Integer> ofArray().contains(1);
        predicateIntegers.that(new Integer[] {1}).orElseThrow(JUNIT_THROWABLE);

        final PredicateStepCharSequence<StringBuilder> predicateStringBuilder = Assertor.<StringBuilder> ofCharSequence().contains('a');
        predicateStringBuilder.that(new StringBuilder("alibaba")).orElseThrow(JUNIT_THROWABLE);

        final Predicate<StringBuilder> p = Assertor.<StringBuilder> ofCharSequence().contains('a').asPredicate();

        assertTrue(p.test(new StringBuilder("alibaba")));
    }
}
