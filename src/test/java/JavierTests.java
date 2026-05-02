import clojure.java.api.Clojure;
import clojure.lang.IFn;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JavierTests {

    static IFn intersect;
    static IFn same;

    /**
     * =====ROLES=====
     *
     * Ethan = [Member and Append -> CLOJURE AND JAVA]
     * Javier = [Intersect -> JAVA AND CLOJURE, Same -> JAVA]
     * Alex  = [Map -> JAVA AND CLOJURE, Same -> CLOJURE]
     */

    // Load Clojure namespace
    @BeforeAll
    static void requires() {
        var require = Clojure.var("clojure.core", "require");

        require.invoke(Clojure.read("Javier"));
        intersect = Clojure.var("Javier", "intersect");
        same = Clojure.var("Javier", "same");
    }

    // =========================================================
    // INTERSECT TESTING (CLOJURE)
    // =========================================================

    @Nested
    @DisplayName("Intersect - Clojure")
    class IntersectClojureTests {

        @Test
        void basicOverlap() {
            assertEquals(
                    List.of(2, 3),
                    intersect.invoke(List.of(1, 2, 3), List.of(2, 3, 4))
            );
        }

        @Test
        void noOverlap() {
            assertEquals(
                    List.of(),
                    intersect.invoke(List.of(1, 2, 3), List.of(4, 5, 6))
            );
        }

        @Test
        void identicalLists() {
            assertEquals(
                    List.of(1, 2, 3),
                    intersect.invoke(List.of(1, 2, 3), List.of(1, 2, 3))
            );
        }

        @Test
        void firstEmptyList() {
            assertEquals(
                    List.of(),
                    intersect.invoke(List.of(), List.of(1, 2, 3))
            );
        }

        @Test
        void secondEmptyList() {
            assertEquals(
                    List.of(),
                    intersect.invoke(List.of(1, 2, 3), List.of())
            );
        }

        @Test
        void bothEmptyLists() {
            assertEquals(
                    List.of(),
                    intersect.invoke(List.of(), List.of())
            );
        }

        @Test
        void duplicatesInFirstList() {
            assertEquals(
                    List.of(2, 2, 3),
                    intersect.invoke(List.of(1, 2, 2, 3), List.of(2, 3))
            );
        }
    }

    // =========================================================
    // INTERSECT TESTING (JAVA)
    // =========================================================

    @Nested
    @DisplayName("Intersect - Java")
    class IntersectJavaTests {

        @Test
        void basicOverlapJava() {
            Object[] arr1 = {1, 2, 3};
            Object[] arr2 = {2, 3, 4};

            assertArrayEquals(
                    new Object[]{2, 3},
                    Javier.intersect(arr1, arr2, 0)
            );
        }

        @Test
        void noOverlapJava() {
            Object[] arr1 = {1, 2, 3};
            Object[] arr2 = {4, 5, 6};

            assertArrayEquals(
                    new Object[]{},
                    Javier.intersect(arr1, arr2, 0)
            );
        }

        @Test
        void identicalListsJava() {
            Object[] arr1 = {1, 2, 3};
            Object[] arr2 = {1, 2, 3};

            assertArrayEquals(
                    new Object[]{1, 2, 3},
                    Javier.intersect(arr1, arr2, 0)
            );
        }

        @Test
        void firstEmptyJava() {
            Object[] arr1 = {};
            Object[] arr2 = {1, 2, 3};

            assertArrayEquals(
                    new Object[]{},
                    Javier.intersect(arr1, arr2, 0)
            );
        }

        @Test
        void secondEmptyJava() {
            Object[] arr1 = {1, 2, 3};
            Object[] arr2 = {};

            assertArrayEquals(
                    new Object[]{},
                    Javier.intersect(arr1, arr2, 0)
            );
        }

        @Test
        void bothEmptyJava() {
            Object[] arr1 = {};
            Object[] arr2 = {};

            assertArrayEquals(
                    new Object[]{},
                    Javier.intersect(arr1, arr2, 0)
            );
        }

        @Test
        void duplicatesPreservedJava() {
            Object[] arr1 = {1, 2, 2, 3};
            Object[] arr2 = {2, 3};

            assertArrayEquals(
                    new Object[]{2, 2, 3},
                    Javier.intersect(arr1, arr2, 0)
            );
        }
    }

    @Nested
    @DisplayName("Same - Clojure")
    class SameClojureTests {

        @Test
        void identicalLists() {
            assertTrue((Boolean) same.invoke(
                    List.of(1, 2, 3),
                    List.of(1, 2, 3)
            ));
        }

        @Test
        void singleElementSame() {
            assertTrue((Boolean) same.invoke(
                    List.of(1),
                    List.of(1)
            ));
        }

        @Test
        void emptyLists() {
            assertTrue((Boolean) same.invoke(
                    List.of(),
                    List.of()
            ));
        }

        @Test
        void differentValues() {
            assertFalse((Boolean) same.invoke(
                    List.of(1, 2, 3),
                    List.of(1, 2, 4)
            ));
        }

        @Test
        void firstListShorter() {
            assertFalse((Boolean) same.invoke(
                    List.of(1, 2),
                    List.of(1, 2, 3)
            ));
        }

        @Test
        void secondListShorter() {
            assertFalse((Boolean) same.invoke(
                    List.of(1, 2, 3),
                    List.of(1, 2)
            ));
        }

        @Test
        void completelyDifferentLists() {
            assertFalse((Boolean) same.invoke(
                    List.of("a", "b"),
                    List.of("x", "y")
            ));
        }

        @Test
        void oneEmptyOneNot() {
            assertFalse((Boolean) same.invoke(
                    List.of(),
                    List.of(1)
            ));
        }

        @Test
        void nestedListsEqual() {
            assertTrue((Boolean) same.invoke(
                    List.of(List.of(1, 2), 3),
                    List.of(List.of(1, 2), 3)
            ));
        }
    }
}