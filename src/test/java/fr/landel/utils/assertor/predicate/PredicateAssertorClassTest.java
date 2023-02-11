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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Color;
import java.io.IOException;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import fr.landel.utils.assertor.AbstractTest;
import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.utils.AssertorClass;

/**
 * Check {@link AssertorClass}
 *
 * @since Jul 18, 2016
 * @author Gilles
 *
 */
public class PredicateAssertorClassTest extends AbstractTest {

    /**
     * Test method for {@link AssertorClass#AssertorClass()} .
     */
    @Test
    public void testConstructor() {
        assertNotNull(new AssertorClass());
    }

    /**
     * Test method for {@link AssertorClass#isAssignableFrom} .
     * 
     * @throws IOException
     *             On errors
     */
    @Test
    public void testIsAssignableFrom() throws IOException {
        assertTrue(Assertor.<IOException> ofClass().isAssignableFrom(Exception.class).that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().isAssignableFrom(Exception.class, "test").that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().isAssignableFrom(Exception.class, Locale.US, "test %2d", 12).that(IOException.class)
                .isOK());

        assertTrue(Assertor.<IOException> ofClass().not().isAssignableFrom(Color.class).that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().not().isNull().that(IOException.class).isOK());

        assertTrue(Assertor.<IOException> ofClass().isNotNull().and().not().isAssignableFrom(Color.class).that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().isNotNull().or().isAssignableFrom(Color.class).that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().isNotNull().xor().isAssignableFrom(Color.class).that(IOException.class).isOK());

        assertTrue(Assertor.that(IOException.class).isNotNull().and(Assertor.that(true).isTrue()).and().not().isAssignableFrom(Color.class)
                .isOK());

        assertException(() -> {
            Assertor.<Exception> ofClass().isAssignableFrom(IOException.class).that(Exception.class).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<Exception> ofClass().isAssignableFrom(IOException.class).that(Exception.class).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.<Exception> ofClass().isAssignableFrom(null).that(Exception.class).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.ofClass().isAssignableFrom(null).that((Class<Object>) null).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.ofClass().isAssignableFrom(Exception.class).that((Class<Object>) null).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");
    }

    /**
     * Test method for {@link AssertorClass#hasName} .
     * 
     * @throws IOException
     *             On errors
     */
    @Test
    public void testHasName() throws IOException {
        String name = IOException.class.getName();

        assertTrue(Assertor.<IOException> ofClass().hasName(name).that(IOException.class).isOK());
        assertFalse(Assertor.<IOException> ofClass().hasName("rr").that(IOException.class).isOK());

        assertTrue(Assertor.<IOException> ofClass().isNotNull().and().hasName(name).that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().isNull().or().hasName(name).that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().isNull().xor().hasName(name).that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().isNull().or().not().hasName("ee").that(IOException.class).isOK());
        assertFalse(Assertor.<IOException> ofClass().isNull().nand().not().hasName("ee").that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().isNull().nor().not().hasName("ee").that(IOException.class).isOK());

        assertTrue(Assertor.<IOException> ofClass().hasName(name).and(Assertor.<IOException> ofClass().hasName("java.io.IOException"))
                .that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().hasName(name).or(Assertor.<IOException> ofClass().hasName("java.io.IOException"))
                .that(IOException.class).isOK());
        assertFalse(Assertor.<IOException> ofClass().hasName(name).xor(Assertor.<IOException> ofClass().hasName("java.io.IOException"))
                .that(IOException.class).isOK());
        assertFalse(Assertor.<IOException> ofClass().hasName(name).nand(Assertor.<IOException> ofClass().hasName("java.io.IOException"))
                .that(IOException.class).isOK());
        assertFalse(Assertor.<IOException> ofClass().hasName(name).nor(Assertor.<IOException> ofClass().hasName("java.io.IOException"))
                .that(IOException.class).isOK());

        assertException(() -> {
            Assertor.<Exception> ofClass().hasName("re").that(Exception.class).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<Exception> ofClass().hasName("re").that(Exception.class).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the class 'Exception' should have the name 're'");

        assertException(() -> {
            Assertor.ofClass().hasName("re").that((Class<Object>) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the name cannot be null or empty");

        assertException(() -> {
            Assertor.<Exception> ofClass().hasName("").that(Exception.class).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the name cannot be null or empty");

        assertException(() -> {
            Assertor.<Exception> ofClass().hasName(null).that(Exception.class).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the name cannot be null or empty");
    }

    /**
     * Test method for {@link AssertorClass#hasSimpleName} .
     * 
     * @throws IOException
     *             On errors
     */
    @Test
    public void testHasSimpleName() throws IOException {
        String name = IOException.class.getSimpleName();

        assertTrue(Assertor.<IOException> ofClass().hasSimpleName(name).that(IOException.class).isOK());
        assertFalse(Assertor.<IOException> ofClass().hasSimpleName("rr").that(IOException.class).isOK());

        assertTrue(Assertor.<IOException> ofClass().isNotNull().and().hasSimpleName(name).that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().isNull().or().hasSimpleName(name).that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().isNull().xor().hasSimpleName(name).that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().isNull().or().not().hasSimpleName("ee").that(IOException.class).isOK());

        assertTrue(Assertor.<IOException> ofClass().hasSimpleName(name).and(Assertor.<IOException> ofClass().hasName("java.io.IOException"))
                .that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().hasSimpleName(name).or(Assertor.<IOException> ofClass().hasName("java.io.IOException"))
                .that(IOException.class).isOK());
        assertFalse(Assertor.<IOException> ofClass().hasSimpleName(name)
                .xor(Assertor.<IOException> ofClass().hasName("java.io.IOException")).that(IOException.class).isOK());

        assertException(() -> {
            Assertor.<Exception> ofClass().hasSimpleName("re").that(Exception.class).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<Exception> ofClass().hasSimpleName("re").that(Exception.class).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the class 'Exception' should have the simple name 're'");

        assertException(() -> {
            Assertor.ofClass().hasSimpleName("re").that((Class<Object>) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the simple name cannot be null or empty");

        assertException(() -> {
            Assertor.<Exception> ofClass().hasSimpleName("").that(Exception.class).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the simple name cannot be null or empty");

        assertException(() -> {
            Assertor.<Exception> ofClass().hasSimpleName(null).that(Exception.class).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the simple name cannot be null or empty");
    }

    /**
     * Test method for {@link AssertorClass#hasCanonicalName} .
     * 
     * @throws IOException
     *             On errors
     */
    @Test
    public void testHasCanonicalName() throws IOException {
        String name = IOException.class.getCanonicalName();

        assertTrue(Assertor.<IOException> ofClass().hasCanonicalName(name).that(IOException.class).isOK());
        assertFalse(Assertor.<IOException> ofClass().hasCanonicalName("rr").that(IOException.class).isOK());

        assertTrue(Assertor.<IOException> ofClass().isNotNull().and().hasCanonicalName(name).that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().isNull().or().hasCanonicalName(name).that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().isNull().xor().hasCanonicalName(name).that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().isNull().or().not().hasCanonicalName("ee").that(IOException.class).isOK());

        assertTrue(Assertor.<IOException> ofClass().hasCanonicalName(name)
                .and(Assertor.<IOException> ofClass().hasName("java.io.IOException")).that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().hasCanonicalName(name)
                .or(Assertor.<IOException> ofClass().hasName("java.io.IOException")).that(IOException.class).isOK());
        assertFalse(Assertor.<IOException> ofClass().hasCanonicalName(name)
                .xor(Assertor.<IOException> ofClass().hasName("java.io.IOException")).that(IOException.class).isOK());

        assertException(() -> {
            Assertor.<Exception> ofClass().hasCanonicalName("re").that(Exception.class).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<Exception> ofClass().hasCanonicalName("re").that(Exception.class).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the class 'Exception' should have the canonical name 're'");

        assertException(() -> {
            Assertor.ofClass().hasCanonicalName("re").that((Class<Object>) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the canonical name cannot be null or empty");

        assertException(() -> {
            Assertor.<Exception> ofClass().hasCanonicalName("").that(Exception.class).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the canonical name cannot be null or empty");

        assertException(() -> {
            Assertor.<Exception> ofClass().hasCanonicalName(null).that(Exception.class).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the canonical name cannot be null or empty");
    }

    /**
     * Test method for {@link AssertorClass#hasTypeName} .
     * 
     * @throws IOException
     *             On errors
     */
    @Test
    public void testHasTypeName() throws IOException {
        String name = IOException.class.getTypeName();

        assertTrue(Assertor.<IOException> ofClass().hasTypeName(name).that(IOException.class).isOK());
        assertTrue(Assertor.<Long> ofClass().hasTypeName("java.lang.Long").that(Long.class).isOK());
        assertTrue(Assertor.<Long> ofClass().hasTypeName("long").that(long.class).isOK());
        assertFalse(Assertor.<IOException> ofClass().hasTypeName("rr").that(IOException.class).isOK());

        assertTrue(Assertor.<IOException> ofClass().isNotNull().and().hasTypeName(name).that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().isNull().or().hasTypeName(name).that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().isNull().xor().hasTypeName(name).that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().isNull().or().not().hasTypeName("ee").that(IOException.class).isOK());

        assertTrue(Assertor.<IOException> ofClass().hasTypeName(name).and(Assertor.<IOException> ofClass().hasName("java.io.IOException"))
                .that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().hasTypeName(name).or(Assertor.<IOException> ofClass().hasName("java.io.IOException"))
                .that(IOException.class).isOK());
        assertFalse(Assertor.<IOException> ofClass().hasTypeName(name).xor(Assertor.<IOException> ofClass().hasName("java.io.IOException"))
                .that(IOException.class).isOK());

        assertException(() -> {
            Assertor.<Exception> ofClass().hasTypeName("re").that(Exception.class).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<Exception> ofClass().hasTypeName("re").that(Exception.class).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the class 'Exception' should have the type name 're'");

        assertException(() -> {
            Assertor.ofClass().hasTypeName("re").that((Class<Object>) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the type name cannot be null or empty");

        assertException(() -> {
            Assertor.<Exception> ofClass().hasTypeName("").that(Exception.class).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the type name cannot be null or empty");

        assertException(() -> {
            Assertor.<Exception> ofClass().hasTypeName(null).that(Exception.class).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the type name cannot be null or empty");
    }

    /**
     * Test method for {@link AssertorClass#hasPackageName} .
     * 
     * @throws IOException
     *             On errors
     */
    @Test
    public void testHasPackageName() throws IOException {
        String name = IOException.class.getPackage().getName();

        assertTrue(Assertor.<IOException> ofClass().hasPackageName(name).that(IOException.class).isOK());
        assertFalse(Assertor.<IOException> ofClass().hasPackageName("rr").that(IOException.class).isOK());

        assertTrue(Assertor.<IOException> ofClass().isNotNull().and().hasPackageName(name).that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().isNull().or().hasPackageName(name).that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().isNull().xor().hasPackageName(name).that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().isNull().or().not().hasPackageName("ee").that(IOException.class).isOK());

        assertTrue(Assertor.<IOException> ofClass().hasPackageName(name)
                .and(Assertor.<IOException> ofClass().hasName("java.io.IOException")).that(IOException.class).isOK());
        assertTrue(Assertor.<IOException> ofClass().hasPackageName(name).or(Assertor.<IOException> ofClass().hasName("java.io.IOException"))
                .that(IOException.class).isOK());
        assertFalse(Assertor.<IOException> ofClass().hasPackageName(name)
                .xor(Assertor.<IOException> ofClass().hasName("java.io.IOException")).that(IOException.class).isOK());

        assertException(() -> {
            Assertor.<Exception> ofClass().hasPackageName("re").that(Exception.class).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<Exception> ofClass().hasPackageName("re").that(Exception.class).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the class 'Exception' should have the package name 're'");

        assertException(() -> {
            Assertor.ofClass().hasPackageName("re").that((Class<Object>) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the package name cannot be null or empty");

        assertException(() -> {
            Assertor.<Exception> ofClass().hasPackageName("").that(Exception.class).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the package name cannot be null or empty");

        assertException(() -> {
            Assertor.<Exception> ofClass().hasPackageName(null).that(Exception.class).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the package name cannot be null or empty");
    }

}
