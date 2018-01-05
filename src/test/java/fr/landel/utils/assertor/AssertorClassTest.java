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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.io.IOException;
import java.util.Locale;

import org.junit.Test;

import fr.landel.utils.assertor.utils.AssertorClass;

/**
 * Check {@link AssertorClass}
 *
 * @since Jul 18, 2016
 * @author Gilles
 *
 */
public class AssertorClassTest extends AbstractTest {

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
        assertTrue(Assertor.that(IOException.class).isAssignableFrom(Exception.class).isOK());
        assertTrue(Assertor.that(IOException.class).isAssignableFrom(Exception.class, "test").isOK());
        assertTrue(Assertor.that(IOException.class).isAssignableFrom(Exception.class, Locale.US, "test %2d", 12).isOK());

        assertTrue(Assertor.that(IOException.class).not().isAssignableFrom(Color.class).isOK());
        assertTrue(Assertor.that(IOException.class).not().isNull().isOK());

        assertTrue(Assertor.that(IOException.class).isNotNull().and().not().isAssignableFrom(Color.class).isOK());
        assertTrue(Assertor.that(IOException.class).isNotNull().or().isAssignableFrom(Color.class).isOK());
        assertTrue(Assertor.that(IOException.class).isNotNull().xor().isAssignableFrom(Color.class).isOK());

        assertTrue(Assertor.that(IOException.class).isNotNull().and(Assertor.that(true).isTrue()).and().not().isAssignableFrom(Color.class)
                .isOK());

        assertException(() -> {
            Assertor.that(Exception.class).isAssignableFrom(IOException.class).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(Exception.class).isAssignableFrom(IOException.class).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.that(Exception.class).isAssignableFrom(null).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.that((Class<?>) null).isAssignableFrom(null).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.that((Class<?>) null).isAssignableFrom(Exception.class).orElseThrow("msg");
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

        assertTrue(Assertor.that(IOException.class).hasName(name).isOK());
        assertFalse(Assertor.that(IOException.class).hasName("rr").isOK());

        assertTrue(Assertor.that(IOException.class).isNotNull().and().hasName(name).isOK());
        assertTrue(Assertor.that(IOException.class).isNull().or().hasName(name).isOK());
        assertTrue(Assertor.that(IOException.class).isNull().xor().hasName(name).isOK());
        assertTrue(Assertor.that(IOException.class).isNull().or().not().hasName("ee").isOK());
        assertFalse(Assertor.that(IOException.class).isNull().nand().not().hasName("ee").isOK());
        assertTrue(Assertor.that(IOException.class).isNull().nor().not().hasName("ee").isOK());

        assertTrue(Assertor.that(IOException.class).hasName(name).and("ere").contains('e').isOK());
        assertTrue(Assertor.that(IOException.class).hasName(name).or("ere").contains('e').isOK());
        assertTrue(Assertor.that(IOException.class).hasName(name).xor("ara").contains('e').isOK());
        assertFalse(Assertor.that(IOException.class).hasName(name).nand("ara").contains('e').isOK());
        assertTrue(Assertor.that(IOException.class).hasName(name).nor("ara").contains('e').isOK());

        assertException(() -> {
            Assertor.that(Exception.class).hasName("re").orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(Exception.class).hasName("re").orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the class 'Exception' should have the name 're'");

        assertException(() -> {
            Assertor.that((Class<?>) null).hasName("re").orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the name cannot be null or empty");

        assertException(() -> {
            Assertor.that(Exception.class).hasName("").orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the name cannot be null or empty");

        assertException(() -> {
            Assertor.that(Exception.class).hasName(null).orElseThrow();
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

        assertTrue(Assertor.that(IOException.class).hasSimpleName(name).isOK());
        assertFalse(Assertor.that(IOException.class).hasSimpleName("rr").isOK());

        assertTrue(Assertor.that(IOException.class).isNotNull().and().hasSimpleName(name).isOK());
        assertTrue(Assertor.that(IOException.class).isNull().or().hasSimpleName(name).isOK());
        assertTrue(Assertor.that(IOException.class).isNull().xor().hasSimpleName(name).isOK());
        assertTrue(Assertor.that(IOException.class).isNull().or().not().hasSimpleName("ee").isOK());

        assertTrue(Assertor.that(IOException.class).hasSimpleName(name).and("ere").contains('e').isOK());
        assertTrue(Assertor.that(IOException.class).hasSimpleName(name).or("ere").contains('e').isOK());
        assertTrue(Assertor.that(IOException.class).hasSimpleName(name).xor("ara").contains('e').isOK());

        assertException(() -> {
            Assertor.that(Exception.class).hasSimpleName("re").orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(Exception.class).hasSimpleName("re").orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the class 'Exception' should have the simple name 're'");

        assertException(() -> {
            Assertor.that((Class<?>) null).hasSimpleName("re").orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the simple name cannot be null or empty");

        assertException(() -> {
            Assertor.that(Exception.class).hasSimpleName("").orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the simple name cannot be null or empty");

        assertException(() -> {
            Assertor.that(Exception.class).hasSimpleName(null).orElseThrow();
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

        assertTrue(Assertor.that(IOException.class).hasCanonicalName(name).isOK());
        assertFalse(Assertor.that(IOException.class).hasCanonicalName("rr").isOK());

        assertTrue(Assertor.that(IOException.class).isNotNull().and().hasCanonicalName(name).isOK());
        assertTrue(Assertor.that(IOException.class).isNull().or().hasCanonicalName(name).isOK());
        assertTrue(Assertor.that(IOException.class).isNull().xor().hasCanonicalName(name).isOK());
        assertTrue(Assertor.that(IOException.class).isNull().or().not().hasCanonicalName("ee").isOK());

        assertTrue(Assertor.that(IOException.class).hasCanonicalName(name).and("ere").contains('e').isOK());
        assertTrue(Assertor.that(IOException.class).hasCanonicalName(name).or("ere").contains('e').isOK());
        assertTrue(Assertor.that(IOException.class).hasCanonicalName(name).xor("ara").contains('e').isOK());

        assertException(() -> {
            Assertor.that(Exception.class).hasCanonicalName("re").orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(Exception.class).hasCanonicalName("re").orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the class 'Exception' should have the canonical name 're'");

        assertException(() -> {
            Assertor.that((Class<?>) null).hasCanonicalName("re").orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the canonical name cannot be null or empty");

        assertException(() -> {
            Assertor.that(Exception.class).hasCanonicalName("").orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the canonical name cannot be null or empty");

        assertException(() -> {
            Assertor.that(Exception.class).hasCanonicalName(null).orElseThrow();
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

        assertTrue(Assertor.that(IOException.class).hasTypeName(name).isOK());
        assertTrue(Assertor.that(Long.class).hasTypeName("java.lang.Long").isOK());
        assertTrue(Assertor.that(long.class).hasTypeName("long").isOK());
        assertFalse(Assertor.that(IOException.class).hasTypeName("rr").isOK());

        assertTrue(Assertor.that(IOException.class).isNotNull().and().hasTypeName(name).isOK());
        assertTrue(Assertor.that(IOException.class).isNull().or().hasTypeName(name).isOK());
        assertTrue(Assertor.that(IOException.class).isNull().xor().hasTypeName(name).isOK());
        assertTrue(Assertor.that(IOException.class).isNull().or().not().hasTypeName("ee").isOK());

        assertTrue(Assertor.that(IOException.class).hasTypeName(name).and("ere").contains('e').isOK());
        assertTrue(Assertor.that(IOException.class).hasTypeName(name).or("ere").contains('e').isOK());
        assertTrue(Assertor.that(IOException.class).hasTypeName(name).xor("ara").contains('e').isOK());

        assertException(() -> {
            Assertor.that(Exception.class).hasTypeName("re").orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(Exception.class).hasTypeName("re").orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the class 'Exception' should have the type name 're'");

        assertException(() -> {
            Assertor.that((Class<?>) null).hasTypeName("re").orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the type name cannot be null or empty");

        assertException(() -> {
            Assertor.that(Exception.class).hasTypeName("").orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the type name cannot be null or empty");

        assertException(() -> {
            Assertor.that(Exception.class).hasTypeName(null).orElseThrow();
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

        assertTrue(Assertor.that(IOException.class).hasPackageName(name).isOK());
        assertFalse(Assertor.that(IOException.class).hasPackageName("rr").isOK());

        assertTrue(Assertor.that(IOException.class).isNotNull().and().hasPackageName(name).isOK());
        assertTrue(Assertor.that(IOException.class).isNull().or().hasPackageName(name).isOK());
        assertTrue(Assertor.that(IOException.class).isNull().xor().hasPackageName(name).isOK());
        assertTrue(Assertor.that(IOException.class).isNull().or().not().hasPackageName("ee").isOK());

        assertTrue(Assertor.that(IOException.class).hasPackageName(name).and("ere").contains('e').isOK());
        assertTrue(Assertor.that(IOException.class).hasPackageName(name).or("ere").contains('e').isOK());
        assertTrue(Assertor.that(IOException.class).hasPackageName(name).xor("ara").contains('e').isOK());

        assertException(() -> {
            Assertor.that(Exception.class).hasPackageName("re").orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(Exception.class).hasPackageName("re").orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the class 'Exception' should have the package name 're'");

        assertException(() -> {
            Assertor.that((Class<?>) null).hasPackageName("re").orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the package name cannot be null or empty");

        assertException(() -> {
            Assertor.that(Exception.class).hasPackageName("").orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the package name cannot be null or empty");

        assertException(() -> {
            Assertor.that(Exception.class).hasPackageName(null).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the classes cannot be null and the package name cannot be null or empty");
    }

}
