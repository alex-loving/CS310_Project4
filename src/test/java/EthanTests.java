import clojure.java.api.Clojure;
import clojure.lang.IFn;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EthanTests {

    static IFn member;
    static IFn append;

    /**
     * =====ROLES=====
     *
     * Ethan = [Member and Append -> CLOJURE AND JAVA]
     * Javier = [Intersect -> JAVA AND CLOJURE, Same -> JAVA]
     * Alex: = [Map -> JAVA AND CLOJURE, Same -> CLOJURE]
     */

    // Load Clojure namespace
    @BeforeAll
    static void requires(){
        var require = Clojure.var("clojure.core", "require");

        require.invoke(Clojure.read("Ethan"));
        member = Clojure.var("Ethan", "member");
        append = Clojure.var("Ethan", "append");
    }

    // =========================================================
    // HELPERS
    // =========================================================

    // Helper method - starts recursion at 0
    boolean callMember(Object check, Object[] arr){
        return Ethan.member(check, arr, 0);
    }

    // =========================================================
    // MEMBER TESTING (CLOJURE)
    // =========================================================

    @Nested
    @DisplayName("Member - Clojure")
    class MemberClojureTests {

        @Test
        void findElementsAtBeginning(){
            assertEquals(true, member.invoke("a", List.of("a", "b", "c")));
        }

        @Test
        void findsElementInMiddle(){
            assertEquals(true, member.invoke("b", List.of("a", "b", "c")));
        }

        @Test
        void findsElementAtEnd(){
            assertEquals(true, member.invoke("c", List.of("a", "b", "c")));
        }

        @Test
        void emptyListReturnsFalse(){
            assertEquals(false, member.invoke("a", List.of()));
        }

        @Test
        void missingElementReturnsFalse(){
            assertEquals(false, member.invoke("x", List.of("a", "b", "c")));
        }

        @Test
        void singleElementMatch(){
            assertEquals(true, member.invoke("a", List.of("a")));
        }

        @Test
        void singleElementNoMatch(){
            assertEquals(false, member.invoke("c", List.of("a")));
        }

        @Test
        void findsElementAtLaterIndex(){
            assertEquals(true, member.invoke("c", List.of("a", "b", "c"), 1));
        }

        @Test
        void isIndexPastEnd(){
            assertEquals(false, member.invoke("a", List.of("a", "b", "c"), 3));
        }
    }

    // =========================================================
    // MEMBER TESTING (JAVA)
    // =========================================================

    @Nested
    @DisplayName("Member - Java")
    class MemberJavaTests {

        @Test
        void findsAtBeginning(){
            Object[] arr = {"a", "b", "c"};
            assertTrue(callMember("a", arr));
        }

        @Test
        void findsInMiddle(){
            Object[] arr = {"a", "b", "c"};
            assertTrue(callMember("b", arr));
        }

        @Test
        void findsAtEnd(){
            Object[] arr = {"a", "b", "c"};
            assertTrue(callMember("c", arr));
        }

        @Test
        void emptyArrayReturnsFalse(){
            Object[] arr = {};
            assertFalse(callMember("a", arr));
        }

        @Test
        void singleElementMatchJava(){
            Object[] arr = {"a"};
            assertTrue(callMember("a", arr));
        }

        @Test
        void singleElementNoMatchJava(){
            Object[] arr = {"a"};
            assertFalse(callMember("b", arr));
        }

        @Test
        void elementNotFound(){
            Object[] arr = {"a", "b", "c"};
            assertFalse(callMember("z", arr));
        }

        @Test
        void referenceEquality(){
            String check = new String("a");
            String[] arr = {new String("a")};

            assertFalse(callMember(check, arr));
        }
    }

    // =========================================================
    // APPEND TESTING (CLOJURE)
    // =========================================================

    @Nested
    @DisplayName("Append - Clojure")
    class AppendClojureTests {

        @Test
        void appendsTwoNormalLists(){
            List<Integer> list1 = List.of(1, 2);
            List<Integer> list2 = List.of(3, 4);

            assertEquals(List.of(1, 2, 3, 4), append.invoke(list1, list2));
        }

        @Test
        void preservesOrderOfFirstList(){
            List<String> list1 = List.of("a", "b", "c");
            List<String> list2 = List.of("d");

            assertEquals(List.of("a", "b", "c", "d"), append.invoke(list1, list2));
        }

        @Test
        void preservesOrderOfSecondList(){
            List<String> list1 = List.of("a");
            List<String> list2 = List.of( "b", "c", "d");

            assertEquals(List.of("a", "b", "c", "d"), append.invoke(list1, list2));
        }

        @Test
        void firstListEmpty(){
            List<Integer> list1 = List.of();
            List<Integer> list2 = List.of(1, 2, 3);

            assertEquals(List.of(1, 2, 3), append.invoke(list1, list2));
        }

        @Test
        void secondListEmpty(){
            List<Integer> list1 = List.of(1, 2, 3);
            List<Integer> list2 = List.of();

            assertEquals(List.of(1, 2, 3), append.invoke(list1, list2));
        }

        @Test
        void bothListsEmpty(){
            List<Integer> list1 = List.of();
            List<Integer> list2 = List.of();

            assertEquals(List.of(), append.invoke(list1, list2));
        }

        @Test
        void singleElementEach(){
            List<Integer> list1 = List.of(1);
            List<Integer> list2 = List.of(2);

            assertEquals(List.of(1, 2), append.invoke(list1, list2));
        }

        @Test
        void appendIntoSelf(){
            List<Integer> list1 = List.of(1);

            assertEquals(List.of(1), append.invoke(list1, List.of()));
        }
    }

    // =========================================================
    // APPEND TESTING (JAVA)
    // =========================================================

    @Nested
    @DisplayName("Append - Java")
    class AppendJavaTests {

        @Test
        void appendsTwoNormalArrays(){
            Object[] arr1 = {"a", "b"};
            Object[] arr2 = {"c"};

            assertArrayEquals(
                    new Object[]{"a", "b", "c"},
                    Ethan.append(arr1, arr2)
            );
        }

        @Test
        void preservesOrder() {
            Object[] arr1 = {"a", "b"};
            Object[] arr2 = {"c"};

            assertArrayEquals(
                    new Object[]{"a", "b", "c"},
                    Ethan.append(arr1, arr2)
            );
        }

        @Test
        void firstArrayEmpty() {
            Object[] arr1 = {};
            Object[] arr2 = {1, 2, 3};

            assertArrayEquals(
                    new Object[]{1, 2, 3},
                    Ethan.append(arr1, arr2)
            );
        }

        @Test
        void secondArrayEmpty() {
            Object[] arr1 = {1, 2, 3};
            Object[] arr2 = {};

            assertArrayEquals(
                    new Object[]{1, 2, 3},
                    Ethan.append(arr1, arr2)
            );
        }

        @Test
        void bothArraysEmpty() {
            Object[] arr1 = {};
            Object[] arr2 = {};

            assertArrayEquals(
                    new Object[]{},
                    Ethan.append(arr1, arr2)
            );
        }

        @Test
        void firstArrayNullReturnsSecond() {
            Object[] arr1 = null;
            Object[] arr2 = {1, 2, 3};

            assertArrayEquals(
                    new Object[]{1, 2, 3},
                    Ethan.append(arr1, arr2)
            );
        }

        @Test
        void secondArrayNullReturnsFirst() {
            Object[] arr1 = {1, 2, 3};
            Object[] arr2 = null;

            assertArrayEquals(
                    new Object[]{1, 2, 3},
                    Ethan.append(arr1, arr2)
            );
        }

        @Test
        void bothArraysNull() {
            Object[] arr1 = null;
            Object[] arr2 = null;

            assertNull(Ethan.append(arr1, arr2));
        }

        @Test
        void singleElementEachForAppend() {
            Object[] arr1 = {1};
            Object[] arr2 = {2};

            assertArrayEquals(
                    new Object[]{1, 2},
                    Ethan.append(arr1, arr2)
            );
        }
    }
}