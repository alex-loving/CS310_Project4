import java.util.ArrayList;
import java.util.List;

public class JaviIntersect {

	public static <T> List<T> intersect(List<T> lizt1, List<T> lizt2) {
		List<T> result = new ArrayList<>();

		for (T item : lizt1) {
			if (lizt2.contains(item)) {
				result.add(item);
			}
		}

		return result;
	}
}