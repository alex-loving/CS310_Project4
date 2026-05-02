public class Javier {
    public static Object[] intersect(Object[] lizt1, Object[] lizt2, int index) {

        if (lizt1 == null || lizt1.length == 0) {
            return new Object[]{};
        }

        Object head = lizt1[index];

        boolean found = false;

        for (Object o : lizt2) {
            if (o == head || (o != null && o.equals(head))) {
                found = true;
                break;
            }
        }

        Object[] rest = intersect(lizt1, lizt2, index + 1);

        if (found) {
            Object[] result = new Object[rest.length + 1];
            result[0] = head;

            System.arraycopy(rest, 0, result, 1, rest.length);
            return result;
        }

        return rest;
    }
}
