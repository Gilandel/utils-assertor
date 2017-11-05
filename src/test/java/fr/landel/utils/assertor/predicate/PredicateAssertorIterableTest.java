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
package fr.landel.utils.assertor.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import org.junit.Test;

import fr.landel.utils.assertor.AbstractTest;
import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.enums.EnumAnalysisMode;
import fr.landel.utils.assertor.utils.AssertorIterable;

/**
 * Check {@link AssertorIterable}
 *
 * @since Jun 4, 2016
 * @author Gilles
 *
 */
public class PredicateAssertorIterableTest extends AbstractTest {

    private final String ERROR = "error expected";

    /**
     * Test method for {@link AssertorIterable} .
     */
    @Test
    public void testPredicateGet() {
        final String el = "element";

        final Set<String> set = new HashSet<>();
        set.add(el);

        assertFalse(Assertor.<Set<String>, String> ofIterable().hasHashCode(0).that(set).isOK());
        assertTrue(Assertor.<Set<String>, String> ofIterable().hasHashCode(Objects.hashCode(set)).that(set).isOK());
    }

    /**
     * Test method for {@link AssertorIterable#hasSize(int)}.
     * 
     * @throws IOException
     *             On not empty iterable
     */
    @Test
    public void testHasSize() throws IOException {
        final String el = "element";

        final Set<String> set = new HashSet<>();
        set.add(el);

        assertTrue(Assertor.<Set<String>, String> ofIterable().isNotEmpty().and(Assertor.<Set<String>, String> ofIterable().contains(el))
                .that(set).isOK());

        assertTrue(Assertor.<Set<String>, String> ofIterable().hasSize(1).that(set).isOK());
        assertFalse(Assertor.<Set<String>, String> ofIterable().hasSize(2).that(set).isOK());
        assertFalse(Assertor.<Set<String>, String> ofIterable().hasSize(1).that((Set<String>) null).isOK());
        assertFalse(Assertor.<Set<String>, String> ofIterable().hasSize(-1).that(set).isOK());

        assertTrue(Assertor.<Set<String>, String> ofIterable().hasSize(1).and().contains(el).that(set).isOK());
        assertTrue(Assertor.<Set<String>, String> ofIterable().hasSize(1).or().contains(el).that(set).isOK());
        assertTrue(Assertor.<Set<String>, String> ofIterable().hasSize(1).xor().not().contains(el).that(set).isOK());
        assertFalse(Assertor.<Set<String>, String> ofIterable().hasSize(1).nand().not().contains(el).that(set).isOK());
        assertTrue(Assertor.<Set<String>, String> ofIterable().hasSize(1).nor().not().contains(el).that(set).isOK());

        final Iterable<String> iterable = new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return set.iterator();
            }
        };

        assertTrue(Assertor.<Iterable<String>, String> ofIterable().hasSize(1).that(iterable).isOK());
        assertFalse(Assertor.<Iterable<String>, String> ofIterable().hasSize(2).that(iterable).isOK());
    }

    /**
     * Test method for {@link AssertorIterable#hasSize(int)}.
     * 
     * @throws IOException
     *             On not empty iterable
     */
    @Test
    public void testHasNotSize() throws IOException {
        final String el = "element";

        final Set<String> set = new HashSet<>();
        set.add(el);

        assertFalse(Assertor.<Set<String>, String> ofIterable().not().hasSize(1).that(set).isOK());
        assertTrue(Assertor.<Set<String>, String> ofIterable().not().hasSize(2).that(set).isOK());
        assertFalse(Assertor.<Set<String>, String> ofIterable().not().hasSize(1).that((Set<String>) null).isOK());
        assertFalse(Assertor.<Set<String>, String> ofIterable().not().hasSize(-1).that(set).isOK());

        final Iterable<String> iterable = new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return set.iterator();
            }
        };

        assertFalse(Assertor.<Iterable<String>, String> ofIterable().not().hasSize(1).that(iterable).isOK());
        assertTrue(Assertor.<Iterable<String>, String> ofIterable().not().hasSize(2).that(iterable).isOK());
    }

    /**
     * Test method for {@link AssertorIterable#isEmpty}.
     * 
     * @throws IOException
     *             On not empty iterable
     */
    @Test
    public void testIsEmpty() throws IOException {
        final String el = "element";

        final Set<String> set = new HashSet<>();

        Assertor.<Set<String>, String> ofIterable().isEmpty().that(set).orElseThrow();

        set.add(el);

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().isEmpty().that(set).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().isEmpty().that(set).orElseThrow("iterable is not empty");
            fail(ERROR);
        }, IllegalArgumentException.class, "iterable is not empty");

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().isEmpty().that(set).orElseThrow(new IOException(), true);
            fail(ERROR);
        }, IOException.class);
    }

    /**
     * Test method for {@link AssertorIterable#isNotEmpty}.
     * 
     * @throws IOException
     *             On empty iterable
     */
    @Test
    public void testIsNotEmpty() throws IOException {
        final String el = "element";

        final Set<String> set = new HashSet<>();
        set.add(el);

        Assertor.<Set<String>, String> ofIterable().isNotEmpty().that(set).orElseThrow();

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().not().isNotEmpty().that(set).orElseThrow("iterable is not empty");
            fail(ERROR);
        }, IllegalArgumentException.class, "iterable is not empty");

        set.clear();

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().isNotEmpty().that(set).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().isNotEmpty().that(set).orElseThrow("iterable is empty");
            fail(ERROR);
        }, IllegalArgumentException.class, "iterable is empty");

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().isNotEmpty().that(set).orElseThrow(new IOException(), true);
            fail(ERROR);
        }, IOException.class);

        assertException(() -> {
            Assertor.<Iterable<String>, String> ofIterable().isNotEmpty().that((Iterable<String>) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the iterable 'null' should be NOT empty and NOT null");
    }

    /**
     * Test method for {@link AssertorIterable#contains}.
     * 
     * @throws IOException
     *             On not contain
     */
    @Test
    public void testContains() throws IOException {
        final String el1 = "element1";
        final String el2 = "element2";

        final Set<String> set = new HashSet<>();
        set.add(el1);

        Assertor.<Set<String>, String> ofIterable().contains(el1).that(set).orElseThrow("iterable doesn't contain the element %s*");
        Assertor.<Set<String>, String> ofIterable(EnumAnalysisMode.STREAM).contains(el1).that(set)
                .orElseThrow("iterable doesn't contain the element %s*");
        Assertor.<Set<String>, String> ofIterable(EnumAnalysisMode.PARALLEL).contains(el1).that(set)
                .orElseThrow("iterable doesn't contain the element %s*");

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().contains(el2).that(set).orElseThrow("iterable doesn't contain the element %2$s*");
            fail(ERROR);
        }, IllegalArgumentException.class, "iterable doesn't contain the element " + el2);

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().contains(el2).that(set).orElseThrow(new IOException(), true);
            fail(ERROR);
        }, IOException.class);

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().contains((String) null).that(set).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "the iterable '[element1]' should contain the object 'null'");

        set.clear();

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().contains(el1).that(set).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().contains(el1).that(set).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "the iterable cannot be null");

        assertException(() -> {
            Assertor.<Iterable<String>, String> ofIterable().contains(el1).that((Iterable<String>) null).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "the iterable cannot be null");

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().contains((String) null).that(set).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "the iterable cannot be null");
    }

    /**
     * Test method for {@link AssertorIterable#contains}.
     * 
     * @throws IOException
     *             On not contain
     */
    @Test
    public void testContainsIterable() throws IOException {
        final String el1 = "element1";
        final String el2 = "element2";

        final Set<String> set = new HashSet<>();
        final Set<String> set2 = new HashSet<>();
        set.add(el1);
        set2.add(el1);

        Assertor.<Set<String>, String> ofIterable().containsAll(set2).that(set).orElseThrow("iterable doesn't contain the list %s*");
        Assertor.<Set<String>, String> ofIterable().containsAny(set2).that(set).orElseThrow("iterable doesn't contain the list %s*");
        Assertor.<Set<String>, String> ofIterable(EnumAnalysisMode.STREAM).containsAll(set2).that(set)
                .orElseThrow("iterable doesn't contain the list %s*");
        Assertor.<Set<String>, String> ofIterable(EnumAnalysisMode.STREAM).containsAny(set2).that(set)
                .orElseThrow("iterable doesn't contain the list %s*");
        Assertor.<Set<String>, String> ofIterable(EnumAnalysisMode.PARALLEL).containsAll(set2).that(set)
                .orElseThrow("iterable doesn't contain the list %s*");
        Assertor.<Set<String>, String> ofIterable(EnumAnalysisMode.PARALLEL).containsAny(set2).that(set)
                .orElseThrow("iterable doesn't contain the list %s*");

        set2.add(el2);
        Assertor.<Set<String>, String> ofIterable().containsAny(set2).that(set).orElseThrow("iterable doesn't contain the list %s*");

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().containsAll(set2).that(set).orElseThrow("iterable doesn't contain the list %2$s*");
            fail(ERROR);
        }, IllegalArgumentException.class, "iterable doesn't contain the list " + set2.toString());

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().containsAll(set2).that(set).orElseThrow(new IOException(), true);
            fail(ERROR);
        }, IOException.class);

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().containsAll((Iterable<String>) null).that(set).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "neither iterables can be null");

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().containsAny((Iterable<String>) null).that(set).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "neither iterables can be null");

        set.clear();

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().containsAll(set2).that(set).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().containsAll(set2).that(set).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "neither iterables can be null");

        assertException(() -> {
            Assertor.<Iterable<String>, String> ofIterable().contains(el1).that((Iterable<String>) null).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "the iterable cannot be null");

        assertException(() -> {
            Assertor.<Iterable<String>, String> ofIterable().containsAny(set2).that((Iterable<String>) null).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "neither iterables can be null");

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().containsAll((Iterable<String>) null).that(set).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "neither iterables can be null");

        set.add(null);
        Assertor.<Set<String>, String> ofIterable().contains(null).that(set).orElseThrow();
    }

    /**
     * Test method for {@link AssertorIterable#contains}.
     * 
     * @throws IOException
     *             On contain
     */
    @Test
    public void testDoesNotContain() throws IOException {
        final String el1 = "element1";
        final String el2 = "element2";

        final Set<String> set = new HashSet<>();
        set.add(el1);

        Assertor.<Set<String>, String> ofIterable().not().contains(el2).that(set).orElseThrow("iterable contains the element %s*");
        Assertor.<Set<String>, String> ofIterable().not().contains((String) null).that(set).orElseThrow();
        Assertor.<Set<String>, String> ofIterable(EnumAnalysisMode.STREAM).not().contains(el2).that(set)
                .orElseThrow("iterable contains the element %s*");
        Assertor.<Set<String>, String> ofIterable(EnumAnalysisMode.STREAM).not().contains((String) null).that(set).orElseThrow();
        Assertor.<Set<String>, String> ofIterable(EnumAnalysisMode.PARALLEL).not().contains(el2).that(set)
                .orElseThrow("iterable contains the element %s*");
        Assertor.<Set<String>, String> ofIterable(EnumAnalysisMode.PARALLEL).not().contains((String) null).that(set).orElseThrow();

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().not().contains(el1).that(set).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "the iterable '[element1]' should NOT contain the object 'element1'");

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().not().contains(el1).that(set).orElseThrow("iterable contains the element %2$s*");
            fail(ERROR);
        }, IllegalArgumentException.class, "iterable contains the element " + el1);

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().not().contains(el1).that(set).orElseThrow(new IOException(), false);
            fail(ERROR);
        }, IOException.class);

        set.clear();

        assertException(() -> {
            Assertor.<Iterable<String>, String> ofIterable().not().contains(el1).that((Iterable<String>) null).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "the iterable cannot be null");
    }

    /**
     * Test method for {@link AssertorIterable#contains}.
     * 
     * @throws IOException
     *             On contain
     */
    @Test
    public void testDoesNotContainIterable() throws IOException {
        final String el1 = "element1";
        final String el2 = "element2";

        final Set<String> set = new HashSet<>();
        final Set<String> set2 = new HashSet<>();
        set.add(el1);
        set2.add(el1);
        set2.add(el2);

        Assertor.<Set<String>, String> ofIterable().not().containsAll(set2).that(set).orElseThrow("iterable contains the list %s*");
        Assertor.<Set<String>, String> ofIterable(EnumAnalysisMode.STREAM).not().containsAll(set2).that(set)
                .orElseThrow("iterable contains the list %s*");
        Assertor.<Set<String>, String> ofIterable(EnumAnalysisMode.PARALLEL).not().containsAll(set2).that(set)
                .orElseThrow("iterable contains the list %s*");

        set2.remove(el1);

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().not().containsAll(set2).that(set).orElseThrow("iterable contains the list %2$s*");
            fail(ERROR);
        }, IllegalArgumentException.class, "iterable contains the list " + set2.toString());

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().not().containsAll(set2).that(set).orElseThrow(new IOException(), true);
            fail(ERROR);
        }, IOException.class);

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().not().containsAll((Iterable<String>) null).that(set).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "neither iterables can be null");

        assertFalse(Assertor.<Set<String>, String> ofIterable().not().containsAll(set2).that(set).isOK());
        assertFalse(Assertor.<Set<String>, String> ofIterable().not().containsAll(set).that(set).isOK());
        assertTrue(Assertor.<Set<String>, String> ofIterable().not().containsAny(set2).that(set).isOK());
        assertFalse(Assertor.<Set<String>, String> ofIterable().not().containsAny(set).that(set).isOK());

        set.clear();

        assertException(() -> {
            Assertor.<Iterable<String>, String> ofIterable().not().containsAll(set2).that((Iterable<String>) null).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "neither iterables can be null");

        assertException(() -> {
            Assertor.<Set<String>, String> ofIterable().not().containsAll((Iterable<String>) null).that(set).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "neither iterables can be null");

        assertFalse(Assertor.<Set<String>, String> ofIterable().not().containsAll(set2).that(set).isOK());
        assertFalse(Assertor.<Set<String>, String> ofIterable().not().containsAll(set).that(set).isOK());
        assertFalse(Assertor.<Set<String>, String> ofIterable().not().containsAny(set2).that(set).isOK());
        assertFalse(Assertor.<Set<String>, String> ofIterable().not().containsAny(set).that(set).isOK());
    }

    /**
     * Test method for
     * {@link AssertorIterable#isNotEmpty(java.util.Collection, String, Object...)}
     * .
     */
    @Test
    public void testIsNotEmptyOKCollectionOfQString() {
        try {
            Assertor.ofIterable().isNotEmpty().that(Arrays.asList("")).orElseThrow("empty collection");
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for
     * {@link AssertorIterable#isNotEmpty(java.util.Collection, String, Object...)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsNotEmptyKOCollectionOfQString() {
        Assertor.ofIterable().isNotEmpty().that(Collections.emptyList()).orElseThrow("empty collection");
    }

    /**
     * Test method for {@link AssertorIterable#isNotEmpty(java.util.Collection)}
     * .
     */
    @Test
    public void testIsNotEmptyOKCollectionOfQ() {
        try {
            Assertor.ofIterable().isNotEmpty().that(Arrays.asList("")).orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for {@link AssertorIterable#isNotEmpty(java.util.Collection)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsNotEmptyKOCollectionOfQ() {
        Assertor.ofIterable().isNotEmpty().that(Collections.emptyList()).orElseThrow();
    }
}
