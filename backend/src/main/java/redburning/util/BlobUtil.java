package redburning.util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class BlobUtil {

	public static List<Double> convertBytesToList(byte[] data) {
        // 将每8个字节解码为双精度浮点数
        List<Double> values = new ArrayList<>();
        for (int i = 0; i < data.length; i += 8) {
            byte[] doubleBytes = new byte[8];
            System.arraycopy(data, i, doubleBytes, 0, 8);
            Double floatValue = ByteBuffer.wrap(doubleBytes)
                                          //.order(ByteOrder.LITTLE_ENDIAN) // 小端序
                                          .getDouble();
            if (Double.isNaN(floatValue)) {
            	values.add(null);
            } else {
            	values.add(floatValue);
            }
        }
        return values;
    }
	
	public static byte[] convertListToBytes(List<Double> list) {
		// 计算所需的 byte[] 长度, 每个 double 占用 8 个字节
        int byteLength = list.size() * 8;
        ByteBuffer buffer = ByteBuffer.allocate(byteLength);
        for (Double d : list) {
        	if (d == null) {
        		buffer.putDouble(Double.NaN);
        	} else {
        		buffer.putDouble(d);
        	}
        }
        return buffer.array();
	}
	
	public static void main(String[] args) {
		List<Double> list = new ArrayList<>();
		list.add(1.23);
		list.add(4.56);
		list.add(null);
		list.add(7.89);
		byte[] bytes = BlobUtil.convertListToBytes(list);
		List<Double> list2 = BlobUtil.convertBytesToList(bytes);
		for (Double d : list2) {
			System.out.println(d);
		}
	}
	
}
