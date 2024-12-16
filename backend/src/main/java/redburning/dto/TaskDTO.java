package redburning.dto;

import java.util.List;

import lombok.Data;

@Data
public class TaskDTO {

	private String rawDataDir;
	private String outputFormat = "mzXML";
	private List<Landmark> landmarks;
	
}
