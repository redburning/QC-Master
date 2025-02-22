package redburning.vo;

import java.util.List;

import lombok.Data;

@Data
public class ProjectDataVo {

	private String projectId;
	private String projectName;
    private String monitorPath;
    private List<String> monitorSamples;
    
    private List<BasePeakCurrentVo> basePeakCurrentList;
	private List<TotalIonCurrentVo> totalIonCurrentList;
	private List<ExtractIonCurrentVo> extractIonCurrentList;
	private List<LandmarkDataVo> landmarkDataList;
}
