package redburning.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataScaler {

	/**
     * 对列表中的数据进行0-1标准化
     *
     * @param data 输入的List<Double>数据
     * @return 标准化后的List<Double>数据
     */
    public static List<Double> minMaxScale(List<Double> data) {
        if (data == null || data.size() < 2) {
            throw new IllegalArgumentException("输入数据必须包含至少两个元素");
        }

        // 找到列表中的最小值和最大值
        double min = Collections.min(data);
        double max = Collections.max(data);

        // 如果最大值和最小值相等，所有元素都相同，直接返回所有元素为0的列表
        if (min == max) {
            List<Double> result = new ArrayList<>(data.size());
            for (int i = 0; i < data.size(); i++) {
                result.add(0.0);
            }
            return result;
        }

        // 创建一个新列表来存储标准化后的数据
        List<Double> result = new ArrayList<>(data.size());
        for (double value : data) {
            double scaledValue = (value - min) / (max - min);
            result.add(scaledValue);
        }

        return result;
    }
	
}
