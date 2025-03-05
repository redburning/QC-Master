package redburning.util;

import java.util.ArrayList;
import java.util.List;

public class DataSampling {

	
	/**
	 * LTTB（Largest-Triangle-Three-Buckets）是一种用于时间序列数据降采样的算法，
	 * 它通过构建三角形来选择数据点，从而在减少数据量的同时尽可能保留数据的形状。
	 * 
	 * @param xValue
	 * @param yValue
	 * @param targetSize 期望降采样后的样本点数
	 */
	public static void lttbSampling(List<Double> xValue, List<Double> yValue, int targetSize) {
        int dataSize = xValue.size();
        if (dataSize <= targetSize || targetSize < 3) {
            return; // 无需处理或目标大小过小
        }

        List<Double> sampledX = new ArrayList<>(targetSize);
        List<Double> sampledY = new ArrayList<>(targetSize);

        // 保留第一个数据点
        sampledX.add(xValue.get(0));
        sampledY.add(yValue.get(0));

        int bucketSize = (dataSize - 2) / (targetSize - 2); // 中间桶的基准大小
        int remainder = (dataSize - 2) % (targetSize - 2);  // 余数分配

        int currentIndex = 1;
        for (int i = 0; i < targetSize - 2; i++) {
            // 计算当前桶的实际大小（前remainder个桶各+1）
            int actualBucketSize = bucketSize + (i < remainder ? 1 : 0);
            
            // 计算下一个桶的起始位置和大小
            int nextBucketStart = currentIndex + actualBucketSize;
            int nextBucketSize = bucketSize + ((i + 1) < remainder ? 1 : 0);
            
            // 处理最后一个中间桶的特殊情况
            if (i == targetSize - 3) {
                nextBucketStart = dataSize - 1;
                nextBucketSize = 1;
            }

            // 计算下一个桶的平均点作为参考点
            double nextAvgX = 0, nextAvgY = 0;
            for (int j = nextBucketStart; j < nextBucketStart + nextBucketSize; j++) {
                nextAvgX += xValue.get(j);
                nextAvgY += yValue.get(j);
            }
            nextAvgX /= nextBucketSize;
            nextAvgY /= nextBucketSize;

            // 在当前桶中寻找最佳点
            double maxArea = -1;
            int bestPointIndex = currentIndex;
            double prevX = sampledX.get(sampledX.size() - 1);
            double prevY = sampledY.get(sampledY.size() - 1);

            for (int j = 0; j < actualBucketSize; j++) {
                int currentPointIndex = currentIndex + j;
                double currentX = xValue.get(currentPointIndex);
                double currentY = yValue.get(currentPointIndex);

                // 计算向量叉积的绝对值作为面积度量
                double area = Math.abs(
                    (nextAvgX - prevX) * (currentY - prevY) - 
                    (currentX - prevX) * (nextAvgY - prevY)
                );

                if (area > maxArea) {
                    maxArea = area;
                    bestPointIndex = currentPointIndex;
                }
            }

            // 添加选中的点
            sampledX.add(xValue.get(bestPointIndex));
            sampledY.add(yValue.get(bestPointIndex));
            currentIndex += actualBucketSize;
        }

        // 保留最后一个数据点
        sampledX.add(xValue.get(dataSize - 1));
        sampledY.add(yValue.get(dataSize - 1));

        // 更新原始列表
        xValue.clear();
        yValue.clear();
        xValue.addAll(sampledX);
        yValue.addAll(sampledY);
    }
	
}
