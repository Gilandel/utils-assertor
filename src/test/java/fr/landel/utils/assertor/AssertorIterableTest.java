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

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import fr.landel.utils.assertor.enums.EnumAnalysisMode;
import fr.landel.utils.assertor.utils.AssertorIterable;

/**
 * Check {@link AssertorIterable}
 *
 * @since Jun 4, 2016
 * @author Gilles
 *
 */
public class AssertorIterableTest extends AbstractTest {

    private final String ERROR = "error expected";

    /**
     * Test method for {@link AssertorIterable#AssertorIterable()} .
     */
    @Test
    public void testConstructor() {
        assertNotNull(new AssertorIterable());
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

        assertTrue(Assertor.that(set).isNotEmpty().and(Assertor.that(el).isNotBlank()).isOK());

        assertTrue(Assertor.that(set).hasSize(1).isOK());
        assertFalse(Assertor.that(set).hasSize(2).isOK());
        assertFalse(Assertor.that((Set<String>) null).hasSize(1).isOK());
        assertFalse(Assertor.that(set).hasSize(-1).isOK());

        assertTrue(Assertor.that(set).hasSize(1).and().contains(el).isOK());
        assertTrue(Assertor.that(set).hasSize(1).or().contains(el).isOK());
        assertTrue(Assertor.that(set).hasSize(1).xor().not().contains(el).isOK());
        assertFalse(Assertor.that(set).hasSize(1).nand().not().contains(el).isOK());
        assertTrue(Assertor.that(set).hasSize(1).nor().not().contains(el).isOK());

        final Iterable<String> iterable = new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return set.iterator();
            }
        };

        assertTrue(Assertor.that(iterable).hasSize(1).isOK());
        assertFalse(Assertor.that(iterable).hasSize(2).isOK());
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

        assertFalse(Assertor.that(set).not().hasSize(1).isOK());
        assertTrue(Assertor.that(set).not().hasSize(2).isOK());
        assertFalse(Assertor.that((Set<String>) null).not().hasSize(1).isOK());
        assertFalse(Assertor.that(set).not().hasSize(-1).isOK());

        final Iterable<String> iterable = new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return set.iterator();
            }
        };

        assertFalse(Assertor.that(iterable).not().hasSize(1).isOK());
        assertTrue(Assertor.that(iterable).not().hasSize(2).isOK());
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

        Assertor.that(set).isEmpty().orElseThrow();

        set.add(el);

        assertException(() -> {
            Assertor.that(set).isEmpty().orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(set).isEmpty().orElseThrow("iterable is not empty");
            fail(ERROR);
        }, IllegalArgumentException.class, "iterable is not empty");

        assertException(() -> {
            Assertor.that(set).isEmpty().orElseThrow(new IOException(), true);
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

        Assertor.that(set).isNotEmpty().orElseThrow();

        assertException(() -> {
            Assertor.that(set).not().isNotEmpty().orElseThrow("iterable is not empty");
            fail(ERROR);
        }, IllegalArgumentException.class, "iterable is not empty");

        set.clear();

        assertException(() -> {
            Assertor.that(set).isNotEmpty().orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(set).isNotEmpty().orElseThrow("iterable is empty");
            fail(ERROR);
        }, IllegalArgumentException.class, "iterable is empty");

        assertException(() -> {
            Assertor.that(set).isNotEmpty().orElseThrow(new IOException(), true);
            fail(ERROR);
        }, IOException.class);

        assertException(() -> {
            Assertor.that((Iterable<String>) null).isNotEmpty().orElseThrow();
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

        Assertor.that(set).contains(el1).orElseThrow("iterable doesn't contain the element %s*");
        Assertor.that(set, EnumAnalysisMode.STREAM).contains(el1).orElseThrow("iterable doesn't contain the element %s*");
        Assertor.that(set, EnumAnalysisMode.PARALLEL).contains(el1).orElseThrow("iterable doesn't contain the element %s*");

        assertException(() -> {
            Assertor.that(set).contains(el2).orElseThrow("iterable doesn't contain the element %2$s*");
            fail(ERROR);
        }, IllegalArgumentException.class, "iterable doesn't contain the element " + el2);

        assertException(() -> {
            Assertor.that(set).contains(el2).orElseThrow(new IOException(), true);
            fail(ERROR);
        }, IOException.class);

        assertException(() -> {
            Assertor.that(set).contains((String) null).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "the iterable '[element1]' should contain the object 'null'");

        set.clear();

        assertException(() -> {
            Assertor.that(set).contains(el1).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(set).contains(el1).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "the iterable cannot be null or empty");

        assertException(() -> {
            Assertor.that((Iterable<String>) null).contains(el1).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "the iterable cannot be null or empty");

        assertException(() -> {
            Assertor.that(set).contains((String) null).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "the iterable cannot be null or empty");
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

        Assertor.that(set).containsAll(set2).orElseThrow("iterable doesn't contain the list %s*");
        Assertor.that(set).containsAny(set2).orElseThrow("iterable doesn't contain the list %s*");
        Assertor.that(set, EnumAnalysisMode.STREAM).containsAll(set2).orElseThrow("iterable doesn't contain the list %s*");
        Assertor.that(set, EnumAnalysisMode.STREAM).containsAny(set2).orElseThrow("iterable doesn't contain the list %s*");
        Assertor.that(set, EnumAnalysisMode.PARALLEL).containsAll(set2).orElseThrow("iterable doesn't contain the list %s*");
        Assertor.that(set, EnumAnalysisMode.PARALLEL).containsAny(set2).orElseThrow("iterable doesn't contain the list %s*");

        set2.add(el2);
        Assertor.that(set).containsAny(set2).orElseThrow("iterable doesn't contain the list %s*");

        assertException(() -> {
            Assertor.that(set).containsAll(set2).orElseThrow("iterable doesn't contain the list %2$s*");
            fail(ERROR);
        }, IllegalArgumentException.class, "iterable doesn't contain the list " + set2.toString());

        assertException(() -> {
            Assertor.that(set).containsAll(set2).orElseThrow(new IOException(), true);
            fail(ERROR);
        }, IOException.class);

        assertException(() -> {
            Assertor.that(set).containsAll((Iterable<String>) null).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "neither iterables can be null or empty");

        assertException(() -> {
            Assertor.that(set).containsAny((Iterable<String>) null).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "neither iterables can be null or empty");

        set.clear();

        assertException(() -> {
            Assertor.that(set).containsAll(set2).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(set).containsAll(set2).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "neither iterables can be null or empty");

        assertException(() -> {
            Assertor.that((Iterable<String>) null).contains(el1).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "the iterable cannot be null or empty");

        assertException(() -> {
            Assertor.that((Iterable<String>) null).containsAny(set2).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "neither iterables can be null or empty");

        assertException(() -> {
            Assertor.that(set).containsAll((Iterable<String>) null).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "neither iterables can be null or empty");

        set.add(null);
        Assertor.that(set).contains(null).orElseThrow();
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

        Assertor.that(set).not().contains(el2).orElseThrow("iterable contains the element %s*");
        Assertor.that(set).not().contains((String) null).orElseThrow();
        Assertor.that(set, EnumAnalysisMode.STREAM).not().contains(el2).orElseThrow("iterable contains the element %s*");
        Assertor.that(set, EnumAnalysisMode.STREAM).not().contains((String) null).orElseThrow();
        Assertor.that(set, EnumAnalysisMode.PARALLEL).not().contains(el2).orElseThrow("iterable contains the element %s*");
        Assertor.that(set, EnumAnalysisMode.PARALLEL).not().contains((String) null).orElseThrow();

        assertException(() -> {
            Assertor.that(set).not().contains(el1).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "the iterable '[element1]' should NOT contain the object 'element1'");

        assertException(() -> {
            Assertor.that(set).not().contains(el1).orElseThrow("iterable contains the element %2$s*");
            fail(ERROR);
        }, IllegalArgumentException.class, "iterable contains the element " + el1);

        assertException(() -> {
            Assertor.that(set).not().contains(el1).orElseThrow(new IOException(), false);
            fail(ERROR);
        }, IOException.class);

        set.clear();

        assertException(() -> {
            Assertor.that((Iterable<String>) null).not().contains(el1).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "the iterable cannot be null or empty");
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

        Assertor.that(set).not().containsAll(set2).orElseThrow("iterable contains the list %s*");
        Assertor.that(set, EnumAnalysisMode.STREAM).not().containsAll(set2).orElseThrow("iterable contains the list %s*");
        Assertor.that(set, EnumAnalysisMode.PARALLEL).not().containsAll(set2).orElseThrow("iterable contains the list %s*");

        set2.remove(el1);

        assertException(() -> {
            Assertor.that(set).not().containsAll(set2).orElseThrow("iterable contains the list %2$s*");
            fail(ERROR);
        }, IllegalArgumentException.class, "iterable contains the list " + set2.toString());

        assertException(() -> {
            Assertor.that(set).not().containsAll(set2).orElseThrow(new IOException(), true);
            fail(ERROR);
        }, IOException.class);

        assertException(() -> {
            Assertor.that(set).not().containsAll((Iterable<String>) null).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "neither iterables can be null or empty");

        assertFalse(Assertor.that(set).not().containsAll(set2).isOK());
        assertFalse(Assertor.that(set).not().containsAll(set).isOK());
        assertTrue(Assertor.that(set).not().containsAny(set2).isOK());
        assertFalse(Assertor.that(set).not().containsAny(set).isOK());

        set.clear();

        assertException(() -> {
            Assertor.that((Iterable<String>) null).not().containsAll(set2).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "neither iterables can be null or empty");

        assertException(() -> {
            Assertor.that(set).not().containsAll((Iterable<String>) null).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "neither iterables can be null or empty");

        assertFalse(Assertor.that(set).not().containsAll(set2).isOK());
        assertFalse(Assertor.that(set).not().containsAll(set).isOK());
        assertFalse(Assertor.that(set).not().containsAny(set2).isOK());
        assertFalse(Assertor.that(set).not().containsAny(set).isOK());
    }

    /**
     * Test method for
     * {@link AssertorIterable#isNotEmpty(java.util.Collection, String, Object...)}
     * .
     */
    @Test
    public void testIsNotEmptyOKCollectionOfQString() {
        try {
            Assertor.that(Arrays.asList("")).isNotEmpty().orElseThrow("empty collection");
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
        Assertor.that(Collections.emptyList()).isNotEmpty().orElseThrow("empty collection");
    }

    /**
     * Test method for {@link AssertorIterable#isNotEmpty(java.util.Collection)}
     * .
     */
    @Test
    public void testIsNotEmptyOKCollectionOfQ() {
        try {
            Assertor.that(Arrays.asList("")).isNotEmpty().orElseThrow();
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
        Assertor.that(Collections.emptyList()).isNotEmpty().orElseThrow();
    }

    /**
     * Check {@link AssertorIterable#containsInOrder}
     */
    @Test
    public void testContainsInOrder() {
        List<String> listTU = Arrays.asList("t", "u");
        List<String> listTUClone = Arrays.asList("t", "u");
        List<String> listUT = Arrays.asList("u", "t");
        List<String> listU = Arrays.asList("u");
        List<String> listTVTUV = Arrays.asList("t", "v", "t", "u", "v");
        List<String> listXTUTUV = Arrays.asList("x", "t", "u", "t", "u", "v");
        List<String> listTUV = Arrays.asList("t", "u", "v");
        List<String> listUV = Arrays.asList("u", "v");
        List<String> listZ = Arrays.asList("z");
        List<String> listTNull = Arrays.asList("t", null);

        for (EnumAnalysisMode mode : EnumAnalysisMode.values()) {
            AssertorStepIterable<List<String>, String> assertorTU = Assertor.that(listTU, mode);

            assertTrue(assertorTU.containsInOrder(listTUClone).isOK());
            assertFalse(assertorTU.not().containsInOrder(listTUClone).isOK());

            assertFalse(assertorTU.containsInOrder(listUT).isOK());
            assertTrue(assertorTU.not().containsInOrder(listUT).isOK());

            assertTrue(assertorTU.containsInOrder(listU).isOK());
            assertFalse(assertorTU.not().containsInOrder(listU).isOK());

            assertTrue(Assertor.that(listTVTUV, mode).containsInOrder(listTU).isOK());
            assertTrue(Assertor.that(listXTUTUV, mode).containsInOrder(listTU).isOK());
            assertTrue(Assertor.that(listTU, mode).containsInOrder(listTU).isOK());
            assertTrue(Assertor.that(listTNull, mode).containsInOrder(listTNull).isOK());
            assertTrue(Assertor.that(listTUV, mode).containsInOrder(listTU).isOK());
            assertTrue(Assertor.that(listTUV, mode).containsInOrder(listUV).isOK());
            assertFalse(Assertor.that(listTU, mode).containsInOrder(listTUV).isOK());
            assertFalse(Assertor.that(listTU, mode).containsInOrder(listUT).isOK());
            assertFalse(Assertor.that(listTU, mode).containsInOrder(listZ).isOK());

            assertFalse(Assertor.that(listTVTUV, mode).not().containsInOrder(listTU).isOK());
            assertFalse(Assertor.that(listXTUTUV, mode).not().containsInOrder(listTU).isOK());
            assertFalse(Assertor.that(listTU, mode).not().containsInOrder(listTU).isOK());
            assertFalse(Assertor.that(listTNull, mode).not().containsInOrder(listTNull).isOK());
            assertFalse(Assertor.that(listTUV, mode).not().containsInOrder(listTU).isOK());
            assertFalse(Assertor.that(listTUV, mode).not().containsInOrder(listUV).isOK());
            assertTrue(Assertor.that(listTU, mode).not().containsInOrder(listTUV).isOK());
            assertTrue(Assertor.that(listTU, mode).not().containsInOrder(listUT).isOK());
            assertTrue(Assertor.that(listTU, mode).not().containsInOrder(listZ).isOK());

            assertFalse(Assertor.that((List<String>) null, mode).not().containsInOrder(listU).isOK());
            assertFalse(assertorTU.not().containsInOrder((List<String>) null).isOK());

            assertException(() -> Assertor.that(listTU, mode).containsInOrder(listTUV).orElseThrow(), IllegalArgumentException.class,
                    "the iterable '[t, u]' should contain all values of '[t, u, v]' in the same order");

            assertException(() -> Assertor.that(listTVTUV, mode).not().containsInOrder(listTU).orElseThrow(),
                    IllegalArgumentException.class,
                    "the iterable '[t, v, t, u, v]' should NOT contain all values of '[t, u]' or should be in an other order");

            assertException(() -> Assertor.that((List<String>) null, mode).containsInOrder(listU).orElseThrow(),
                    IllegalArgumentException.class, "neither iterables can be null or empty");

            assertException(() -> Assertor.that(Collections.<String> emptyList(), mode).containsInOrder(listU).orElseThrow(),
                    IllegalArgumentException.class, "neither iterables can be null or empty");

            assertException(() -> assertorTU.containsInOrder((List<String>) null).orElseThrow(), IllegalArgumentException.class,
                    "neither iterables can be null or empty");
        }
    }
}
