import clojure.java.api.Clojure;
import clojure.lang.IFn;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
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

        require.invoke(Clojure.read("JaviIntersect"));
        intersect = Clojure.var("JaviIntersect", "intersect");
        same = Clojure.var("JaviSame", "same");
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
            List<Object> list1 = List.of(1, 2, 3);
            List<Object> list2 = List.of(2, 3, 4);

            assertEquals(
                    List.of(2, 3),
                    JaviIntersect.intersect(list1, list2)
            );
        }

        @Test
        void noOverlapJava() {
            List<Object> list1 = List.of(1, 2, 3);
            List<Object> list2 = List.of(4, 5, 6);

            assertEquals(
                    List.of(),
                    JaviIntersect.intersect(list1, list2)
            );
        }

        @Test
        void identicalListsJava() {

            List<Object> list1 = List.of(1, 2, 3);
            List<Object> list2 = List.of(1, 2, 3);

            assertEquals(
                    List.of(1, 2, 3),
                    JaviIntersect.intersect(list1, list2)
            );
        }

        @Test
        void firstEmptyJava() {
            List<Object> list1 = List.of();
            List<Object> list2 = List.of(1, 2, 3);

            assertEquals(
                    List.of(),
                    JaviIntersect.intersect(list1, list2)
            );
        }

        @Test
        void secondEmptyJava() {
            List<Object> list1 = List.of(1, 2, 3);
            List<Object> list2 = List.of();

            assertEquals(
                    List.of(),
                    JaviIntersect.intersect(list1, list2)
            );
        }

        @Test
        void bothEmptyJava() {
            List<Object> list1 = List.of();
            List<Object> list2 = List.of();

            assertEquals(
                    List.of(),
                    JaviIntersect.intersect(list1, list2)
            );
        }

        @Test
        void duplicatesPreservedJava() {
            List<Object> list1 = List.of(1, 2, 2, 3);
            List<Object> list2 = List.of(2, 3);

            assertEquals(
                    List.of(2, 2, 3),
                    JaviIntersect.intersect(list1, list2)
            );
        }
    }

    @Nested
    @DisplayName("Same - Java")
    class SameJavaTests {

        @Test
        void identicalLists() {
            ArrayList<Integer> list1 = new ArrayList<>(List.of(1, 2, 3));
            ArrayList<Integer> list2 = new ArrayList<>(List.of(1, 2, 3));

            assertTrue(JaviSame.same(list1, list2));
        }

        @Test
        void singleElementSame() {
            ArrayList<Integer> list1 = new ArrayList<>(List.of(1));
            ArrayList<Integer> list2 = new ArrayList<>(List.of(1));

            assertTrue(JaviSame.same(list1, list2));
        }

        @Test
        void emptyLists() {
            ArrayList<Integer> list1 = new ArrayList<>();
            ArrayList<Integer> list2 = new ArrayList<>();

            assertTrue(JaviSame.same(list1, list2));
        }

        @Test
        void differentValues() {
            ArrayList<Integer> list1 = new ArrayList<>(List.of(1, 2, 3));
            ArrayList<Integer> list2 = new ArrayList<>(List.of(1, 2, 4));

            assertFalse(JaviSame.same(list1, list2));
        }

        @Test
        void firstListShorter() {
            ArrayList<Integer> list1 = new ArrayList<>(List.of(1, 2));
            ArrayList<Integer> list2 = new ArrayList<>(List.of(1, 2, 3));

            assertFalse(JaviSame.same(list1, list2));
        }

        @Test
        void secondListShorter() {
            ArrayList<Integer> list1 = new ArrayList<>(List.of(1, 2, 3));
            ArrayList<Integer> list2 = new ArrayList<>(List.of(1, 2));

            assertFalse(JaviSame.same(list1, list2));
        }

        @Test
        void completelyDifferentLists() {
            ArrayList<Integer> list1 = new ArrayList<>(List.of(1, 2));
            ArrayList<Integer> list2 = new ArrayList<>(List.of(8, 9));

            assertFalse(JaviSame.same(list1, list2));
        }

        @Test
        void oneEmptyOneNot() {
            ArrayList<Integer> list1 = new ArrayList<>();
            ArrayList<Integer> list2 = new ArrayList<>(List.of(1));

            assertFalse(JaviSame.same(list1, list2));
        }

        @Test
        void nestedListsEqual() {
            ArrayList<Integer> list1 = new ArrayList<>(List.of(1, 2, 3));
            ArrayList<Integer> list2 = new ArrayList<>(List.of(1, 2, 3));

            assertTrue(JaviSame.same(list1, list2));
        }
    }
}