package redburning.vo;

import lombok.Data;

@Data
public class Acknowledged {

	private boolean acknowledged;
	private String message;
	
	public Acknowledged(boolean acknowledged) {
		this.acknowledged = acknowledged;
	}
	
	public Acknowledged(boolean acknowledged, String message) {
		this(acknowledged);
		this.message = message;
	}
	
}
