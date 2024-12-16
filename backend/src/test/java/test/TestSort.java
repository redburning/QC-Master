package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TestSort {

	public static void main(String[] args) {
		List<String> name = new ArrayList<>();
		name.add("wangshuang");
		name.add("lifei");
		name.add("zhanglong");
		name.add("xiaohong");
		
		List<Integer> score = new ArrayList<>();
		score.add(90);
		score.add(80);
		score.add(40);
		score.add(10);
		
		Collections.sort(name, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return score.get(name.indexOf(o1)).compareTo(score.get(name.indexOf(o2)));
			}
			
		});
		
		Collections.sort(score);
		
		for (int i = 0; i < name.size(); i++) {
			System.out.println(name.get(i) + " - " + score.get(i));
		}
	}
	
}
