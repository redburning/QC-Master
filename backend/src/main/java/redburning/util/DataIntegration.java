package redburning.util;

import java.util.List;

public class DataIntegration {

	public static double doIntegrate(List<Double> xVal, List<Double> yVal) {
		
		DataUtil.sortListAccordingtoWeight(yVal, xVal);
		
		double integration = 0.0;
		for (int k = 1; k < xVal.size(); k++) {
			double area = (yVal.get(k) + yVal.get(k - 1)) * (xVal.get(k) - xVal.get(k - 1)) / 2;
			integration += area;
		}
		return integration;
	}
	
}
