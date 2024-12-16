package redburning.vo;

import lombok.Data;

@Data
public class Result {
	// 响应码
	private Integer code;
	// 信息
	private String message;
	// 返回数据
	private Object data;
	
	public Result(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public Result(Integer code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
}
