package redburning.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class Landmark implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7132728167638927460L;
	
	private String name;
	private double rtVal;
	private double rtMin;
	private double rtMax;
	private double mzVal;
	private double mzMin;
	private double mzMax;
	
}
