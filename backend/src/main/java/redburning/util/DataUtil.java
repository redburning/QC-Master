package redburning.util;

import java.util.List;

public class DataUtil {

	public static <E extends Object, W extends Comparable<W>> void sortListAccordingtoWeight(List<E> list,
			List<W> weight) {
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.size() - i - 1; j++) {
				if (weight.get(j).compareTo(weight.get(j + 1)) > 0) {
					W w = weight.get(j);
					weight.set(j, weight.get(j + 1));
					weight.set(j + 1, w);
					E o = list.get(j);
					list.set(j, list.get(j + 1));
					list.set(j + 1, o);
				}
			}
		}
	}
	
}
