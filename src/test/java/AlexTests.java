import clojure.java.api.Clojure;
import clojure.lang.IFn;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlexTests {

    static IFn mapFn;
    static IFn sameFn;

    @BeforeAll
    static void requires() {
        Clojure.var("clojure.core", "require")
                .invoke(Clojure.read("Alex"));

        mapFn = Clojure.var("Alex", "map");
        sameFn = Clojure.var("Alex", "same");
    }

    // =========================================================
    // HELPERS
    // =========================================================

    IFn timesTwo = new clojure.lang.AFn() {
        @Override
        public Object invoke(Object x) {
            return ((Number) x).intValue() * 2;
        }
    };

    IFn upper = new clojure.lang.AFn() {
        @Override
        public Object invoke(Object x) {
            return ((String) x).toUpperCase();
        }
    };

    private List<Object> toJavaList(Object result) {
        List<Object> javaList = new java.util.ArrayList<>();

        clojure.lang.ISeq seq = (clojure.lang.ISeq) result;

        while (seq != null) {
            javaList.add(seq.first());
            seq = seq.next();
        }

        return javaList;
    }

    // =========================================================
    // MAP TESTS (CLOJURE)
    // =========================================================

    @Nested
    @DisplayName("MAP - Clojure")
    class MapClojureTests {

        @Test
        void identityFunctionReturnsSameList() {
            List<Integer> input = List.of(1, 2, 3);

            assertEquals(
                    List.of(1, 2, 3),
                    mapFn.invoke(Clojure.var("clojure.core", "identity"), input)
            );
        }

        @Test
        void doublesEachElement() {
            List<Integer> input = List.of(2, 4, 6);

            assertEquals(
                    List.of(4, 8, 12),
                    mapFn.invoke(timesTwo, input)
            );
        }

        @Test
        void emptyListReturnsEmptyList() {
            List<Integer> input = List.of();

            assertEquals(
                    List.of(),
                    mapFn.invoke(Clojure.var("clojure.core", "inc"), input)
            );
        }

        @Test
        void preservesListLength() {
            List<Integer> input = List.of(1, 2, 3, 4);

            Object result = mapFn.invoke(timesTwo, input);

            assertEquals(4, ((List<?>) result).size());
        }

        @Test
        void preservesOrder() {
            List<Integer> input = List.of(1, 2, 3);

            assertEquals(
                    List.of(2, 4, 6),
                    mapFn.invoke(timesTwo, input)
            );
        }

        @Test
        void stringTransformation() {
            List<String> input = List.of("a", "b", "c");

            assertEquals(
                    List.of("A", "B", "C"),
                    mapFn.invoke(upper, input)
            );
        }
    }

    // =========================================================
    // MAP TESTS (JAVA)
    // =========================================================

    @Nested
    @DisplayName("MAP - Java")
    class MapJavaTests {

        @Test
        void identityFunctionReturnsSameListJava() {
            List<Integer> input = List.of(1, 2, 3);

            List<Integer> result = Alex.map(input, x -> x);

            assertEquals(List.of(1, 2, 3), result);
        }

        @Test
        void incrementsEachElementJava() {
            List<Integer> input = List.of(1, 2, 3);

            List<Integer> result = Alex.map(input, x -> x + 1);

            assertEquals(List.of(2, 3, 4), result);
        }

        @Test
        void doublesEachElementJava() {
            List<Integer> input = List.of(2, 4, 6);

            List<Integer> result = Alex.map(input, x -> x * 2);

            assertEquals(List.of(4, 8, 12), result);
        }

        @Test
        void preservesOrderJava() {
            List<Integer> input = List.of(5, 3, 1);

            List<Integer> result = Alex.map(input, x -> x * 2);

            assertEquals(List.of(10, 6, 2), result);
        }

        @Test
        void stringToUppercaseJava() {
            List<String> input = List.of("a", "b", "c");

            List<String> result = Alex.map(input, String::toUpperCase);

            assertEquals(List.of("A", "B", "C"), result);
        }
    }

    // =========================================================
    // SAME TESTS (CLOJURE)
    // =========================================================

    @Nested
    @DisplayName("SAME - Clojure")
    class SameTests {

        @Test
        void identicalLists() {
            assertTrue((Boolean) sameFn.invoke(List.of(1, 2, 3), List.of(1, 2, 3)));
        }

        @Test
        void singleElementSame() {
            assertTrue((Boolean) sameFn.invoke(List.of(1), List.of(1)));
        }

        @Test
        void emptyLists() {
            assertTrue((Boolean) sameFn.invoke(List.of(), List.of()));
        }

        @Test
        void differentValues() {
            assertFalse((Boolean) sameFn.invoke(List.of(1, 2, 3), List.of(1, 2, 4)));
        }

        @Test
        void differentLengths() {
            assertFalse((Boolean) sameFn.invoke(List.of(1, 2), List.of(1, 2, 3)));
        }
    }
}