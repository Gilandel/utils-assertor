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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Test;

import fr.landel.utils.assertor.expect.Expect;
import fr.landel.utils.commons.DateUtils;

/**
 * Check {@link HelperMessage}
 *
 * @since Aug 9, 2016
 * @author Gilles
 *
 */
public class HelperMessageTest extends AbstractTest {

    /**
     * Test method for {@link HelperMessage#PreFormatter()} .
     */
    @Test
    public void testConstructor() {
        assertNotNull(new HelperMessage());
    }

    /**
     * Test method for {@link HelperMessage#getMessage} .
     */
    @Test
    public void testGetMessage1() {
        // TEST GET MESSAGE

        assertEquals("default", HelperMessage.getMessage("default", null, null, null, null));
        assertEquals("message", HelperMessage.getMessage("default", null, "message", null, null));
        assertEquals("message test", HelperMessage.getMessage("default", null, "message %s", null, new Object[] {"test"}));
        assertEquals("message 23.26", HelperMessage.getMessage("default", Locale.US, "message %.2f", null, new Object[] {23.256f}));
        assertEquals("message 23,26", HelperMessage.getMessage("default", Locale.FRANCE, "message %.2f", null, new Object[] {23.256f}));

        Assertor.setLocale(Locale.FRANCE);
        Expect.exception(() -> {
            Assertor.that(23.6f).isEqual(25.6f).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the number '23,600' should be equal to '25,600'", JUNIT_ERROR);
        Assertor.setLocale(Locale.US);

        Expect.exception(() -> {
            Assertor.that("texte11").not().isEqual("texte11").orElseThrow("texte '%2$s*' is not equal to '%1$s*', %s", "args");
            fail();
        }, IllegalArgumentException.class, "texte 'texte11' is not equal to 'texte11', args");

        Expect.exception(() -> {
            Assertor.that("texte11").isEqual(true).orElseThrow("texte '%2$s*' is not equal to '%1$s*', %s", "args");
            fail();
        }, IllegalArgumentException.class, "texte 'true' is not equal to 'texte11', args");
    }

    /**
     * Test method for {@link HelperMessage#getMessage} .
     */
    @Test
    public void testGetMessage2() {
        try {
            Assertor.that("texte11").isNotEqual("texte11").orElseThrow("texte '%2$s*' is not equal to '%1$s*', %s", "args");
            fail("Expect an exception");
        } catch (IllegalArgumentException e) {
            assertEquals("texte 'texte11' is not equal to 'texte11', args", e.getMessage());
        }

        try {
            Assertor.that("texte11").isEqual("texte12").orElseThrow("texte '%2$s*' is not equal to '%1$s*' or '%s*' != '%s*'...");
            fail("Expect an exception");
        } catch (IllegalArgumentException e) {
            assertEquals("texte 'texte12' is not equal to 'texte11' or 'texte11' != 'texte12'...", e.getMessage());
        }

        Expect.exception(() -> {
            Assertor.that("texte11").isBlank().or().isNotEqual("texte11").orElseThrow();
            fail("Expect an exception");
        }, IllegalArgumentException.class,
                "the char sequence 'texte11' should be null, empty or blank OR the char sequence 'texte11' should NOT be equal to 'texte11'",
                JUNIT_ERROR);

        try {
            Assertor.that("texte11").isNotBlank().and().isNotEqual("texte11").orElseThrow();
            fail("Expect an exception");
        } catch (IllegalArgumentException e) {
            assertEquals("the char sequence 'texte11' should NOT be equal to 'texte11'", e.getMessage());
        }

        try {
            Assertor.that("texte11").isBlank().or().not().isEqual("texte11").orElseThrow();
            fail("Expect an exception");
        } catch (IllegalArgumentException e) {
            assertEquals(
                    "the char sequence 'texte11' should be null, empty or blank OR the char sequence 'texte11' should NOT be equal to 'texte11'",
                    e.getMessage());
        }

        try {
            Assertor.that("texte11").isBlank().or("texte12").isEqual("texte13").orElseThrow();
            fail("Expect an exception");
        } catch (IllegalArgumentException e) {
            assertEquals(
                    "the char sequence 'texte11' should be null, empty or blank OR the char sequence 'texte12' should be equal to 'texte13'",
                    e.getMessage());
        }
    }

    /**
     * Test method for {@link HelperMessage#getMessage} .
     */
    @Test
    public void testGetMessage3() {
        Expect.exception(() -> {
            Assertor.that("texte11").isBlank().or("texte12").not().startsWith("text").or().isBlank().orElseThrow();
            fail("Expect an exception");
        }, IllegalArgumentException.class,
                "the char sequence 'texte11' should be null, empty or blank"
                        + " OR the char sequence 'texte12' should NOT start with 'text'"
                        + " OR the char sequence 'texte12' should be null, empty or blank",
                JUNIT_ERROR);

        // prerequisites == false
        Expect.exception(() -> {
            Assertor.that("texte11").isBlank().or("texte12").not().startsWith("text").or().isBlank().orElseThrow();
            fail("Expect an exception");
        }, IllegalArgumentException.class,
                "the char sequence 'texte11' should be null, empty or blank"
                        + " OR the char sequence 'texte12' should NOT start with 'text'"
                        + " OR the char sequence 'texte12' should be null, empty or blank",
                JUNIT_ERROR);

        Expect.exception(() -> {
            Assertor.that("texte11").isBlank().or("texte12").not().startsWith("text").or().isBlank()
                    .orElseThrow((errors, parameters) -> new IllegalArgumentException(errors));
            fail("Expect an exception");
        }, IllegalArgumentException.class,
                "the char sequence 'texte11' should be null, empty or blank"
                        + " OR the char sequence 'texte12' should NOT start with 'text'"
                        + " OR the char sequence 'texte12' should be null, empty or blank",
                JUNIT_ERROR);
        // previous assertion is invalid (prerequisites == false), only first
        // prerequisite error set as message
        assertEquals("the char sequence cannot be null and the searched substring cannot be null or empty",
                Assertor.that("text1").contains((CharSequence) null).and("text2").isBlank().getErrors().get());
    }

    /**
     * Test method for {@link HelperMessage#getMessage} .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetMessageNullObject() {
        Assertor.that("texte11").isNotEqual("texte11").orElseThrow("texte '%2$s*' is not equal to '%1$s*', %s", (Object[]) null);
    }

    /**
     * Test method for
     * {@link HelperMessage#prepare(java.lang.CharSequence, java.lang.Object[], java.lang.Object[])}.
     */
    @Test
    public void testPrepare() {
        assertEquals("e = %2$+10.4f %3$11d %1$2d", HelperMessage.prepare("e = %+10.4f %11d %2d*", 1, 2).toString());

        assertEquals("e = %2$+10.4f %3$11d%1$2d %4$d", HelperMessage.prepare("e = %+10.4f %11d%2d* %$d", 1, 3).toString());

        assertEquals("e = %1$+10.4f   1$s", HelperMessage.prepare("e = %+10.4f %s %s* %s1$s", 0, 1).toString());

        assertEquals("e = %2$+10.4f %2$s %1$s %3$s1$s", HelperMessage.prepare("e = %1$+10.4f %s %s* %s1$s", 1, 2).toString());

        assertEquals("e = %1$+10.4f  ", HelperMessage.prepare("e = %+10.4f %11222d %2d*", 0, 1).toString());

        assertEquals("e = %1$+10.4f  ", HelperMessage.prepare("e = %+10.4f %2$11d %1$2d*", 0, 1).toString());

        assertEquals("e = %1$+10.4f  %1$* ", HelperMessage.prepare("e = %+10.4f %2$11d %1$* ", 0, 1).toString());

        assertEquals("Duke's Birthday: %2$tb %2$te, %2$tY", HelperMessage.prepare("Duke's Birthday: %1$tb %1$te, %1$tY", 1, 3).toString());
        assertEquals("Duke's Birthday: %2$tm %2$<te,%3$<TY", HelperMessage.prepare("Duke's Birthday: %1$tm %<te,%<TY", 1, 3).toString());

        byte[] authorized = new byte[] {32, 35};

        for (int i = 0; i < 256; i++) {
            String ch = String.valueOf((char) i);
            String format = HelperMessage.prepare("%" + ch, 0, 1).toString();
            if ((i > 64 && i < 91) || (i > 96 && i < 123) || i == '%') {
                assertEquals("%1$" + ch, format);
            } else if (Arrays.binarySearch(authorized, (byte) i) == -1) {
                assertNotEquals("%1$" + ch, format);
            }
        }
    }

    /**
     * Test method for {@link HelperMessage#convertParams(java.util.List)}.
     */
    @Test
    public void testConvertParams() {
        final List<ParameterAssertor<?>> parameters = new ArrayList<>();

        final Date date1 = new Date(1464475553640L);
        final Calendar calendar1 = DateUtils.getCalendar(date1);
        Integer[] integers = new Integer[] {12, 10};
        final List<String> texts = Arrays.asList("text1", "text2");
        final Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        parameters.add(new ParameterAssertor<>(true, EnumType.BOOLEAN));
        parameters.add(new ParameterAssertor<>(integers, EnumType.ARRAY));
        parameters.add(new ParameterAssertor<>(Calendar.YEAR, EnumType.CALENDAR_FIELD));
        parameters.add(new ParameterAssertor<>(Calendar.ZONE_OFFSET, EnumType.CALENDAR_FIELD));
        parameters.add(new ParameterAssertor<>("text", EnumType.CHAR_SEQUENCE));
        parameters.add(new ParameterAssertor<>(HelperMessage.class, EnumType.CLASS));
        parameters.add(new ParameterAssertor<>(date1, EnumType.DATE));
        parameters.add(new ParameterAssertor<>(calendar1, EnumType.DATE));
        parameters.add(new ParameterAssertor<>(EnumOperator.AND, EnumType.ENUMERATION));
        parameters.add(new ParameterAssertor<>(texts, EnumType.ITERABLE));
        parameters.add(new ParameterAssertor<>(map, EnumType.MAP));
        parameters.add(new ParameterAssertor<>(3.25f, EnumType.NUMBER_DECIMAL));
        parameters.add(new ParameterAssertor<>(12, EnumType.NUMBER_INTEGER));
        parameters.add(new ParameterAssertor<>(Color.BLACK, EnumType.UNKNOWN));

        Object[] convertedParams = HelperMessage.convertParams(parameters);

        assertEquals(parameters.size(), convertedParams.length);

        int i = 0;
        assertEquals(true, convertedParams[i]);
        i++;
        assertEquals("[12, 10]", convertedParams[i].toString());
        i++;
        assertEquals("YEAR", convertedParams[i]);
        i++;
        assertEquals(Calendar.ZONE_OFFSET, convertedParams[i]);
        i++;
        assertEquals("text", convertedParams[i]);
        i++;
        assertEquals(HelperMessage.class.getSimpleName(), convertedParams[i]);
        i++;
        assertEquals(date1, convertedParams[i]);
        i++;
        assertEquals(calendar1.getTime(), convertedParams[i]);
        i++;
        assertEquals(EnumOperator.AND, convertedParams[i]);
        i++;
        assertEquals("[text1, text2]", convertedParams[i].toString());
        i++;
        assertEquals("[key1=value1, key2=value2]", convertedParams[i].toString());
        i++;
        assertEquals(3.25f, convertedParams[i]);
        i++;
        assertEquals(12, convertedParams[i]);
        i++;
        assertEquals(Color.BLACK, convertedParams[i]);
    }

    /**
     * Test method for {@link ParameterAssertor#toString()}.
     */
    @Test
    public void testParameterToString() {
        assertEquals("{fr.landel.utils.assertor.ParameterAssertor: {object: true, type: BOOLEAN, checked: false}}",
                new ParameterAssertor<>(true, EnumType.BOOLEAN).toString());
    }
}
