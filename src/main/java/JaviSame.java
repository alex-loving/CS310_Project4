import java.util.ArrayList;

public class JaviSame {
    public static boolean same(ArrayList<Integer> lizt1, ArrayList<Integer> lizt2) {

        if (lizt1.size() != lizt2.size()) {
            return false;
        }
        for (int i = 0; i < lizt1.size(); i++) {
            int item1 = lizt1.get(i);
            int item2 = lizt2.get(i);

            if (item1 != item2) {
                return false;
            }
        }
        return true;
    }
}
