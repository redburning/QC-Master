package redburning.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataSampling {

	public static void layerSampling(List<Double> xValue, List<Double> yValue) {
		List<Double> yValueZScore = DataScaler.minMaxScale(yValue);
		
		List<Double> xValueSampling = new ArrayList<>();
		List<Double> yValueSampling = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < yValueZScore.size(); i++) {
			if (yValueZScore.get(i) >= 0 && yValueZScore.get(i) < 0.01) {
				if (random.nextDouble() < 0.1) {
					xValueSampling.add(xValue.get(i));
					yValueSampling.add(yValue.get(i));
				}
			} else if (yValueZScore.get(i) >= 0.01 && yValueZScore.get(i) < 0.02) {
				if (random.nextDouble() < 0.2) {
					xValueSampling.add(xValue.get(i));
					yValueSampling.add(yValue.get(i));
				}
			} else if (yValueZScore.get(i) >= 0.02 && yValueZScore.get(i) < 0.03) {
				if (random.nextDouble() < 0.3) {
					xValueSampling.add(xValue.get(i));
					yValueSampling.add(yValue.get(i));
				}
			} else {
				xValueSampling.add(xValue.get(i));
				yValueSampling.add(yValue.get(i));
			}
		}
        xValue.clear();
        xValue.addAll(xValueSampling);
        yValue.clear();
        yValue.addAll(yValueSampling);
    }
	
}
