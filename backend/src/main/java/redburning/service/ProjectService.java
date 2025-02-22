package redburning.service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redburning.entity.MonitorFilesEntity;
import redburning.entity.ProjectEntity;
import redburning.entity.spectrum.BPCEntity;
import redburning.entity.spectrum.EICEntity;
import redburning.entity.spectrum.LandmarkDataEntity;
import redburning.entity.spectrum.TICEntity;
import redburning.repository.ProjectRepository;
import redburning.service.spectrum.BPCService;
import redburning.service.spectrum.EICService;
import redburning.service.spectrum.LandmarkDataService;
import redburning.service.spectrum.TICService;
import redburning.vo.BasePeakCurrentVo;
import redburning.vo.ExtractIonCurrentVo;
import redburning.vo.LandmarkDataVo;
import redburning.vo.ProjectDataVo;
import redburning.vo.TotalIonCurrentVo;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private BPCService bpcService;
	
	@Autowired
	private TICService ticService;
	
	@Autowired
	private EICService eicService;
	
	@Autowired
	private LandmarkDataService landmarkDataService;
	
	@Autowired
	private MonitorFilesService monitorFilesService;
	
	public ProjectEntity saveOrUpdateProject(ProjectEntity entity) {
		ProjectEntity savedEntity = projectRepository.save(entity);
		return savedEntity;
	}

	public ProjectEntity getProject(String id) {
		Optional<ProjectEntity> optional = projectRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}
	
	public ProjectDataVo loadProjectData(String id) {
		ProjectDataVo projectDataVo = new ProjectDataVo();
		projectDataVo.setProjectId(id);
		
		// ProjectEntity
		ProjectEntity projectEntity = getProject(id);
		projectDataVo.setProjectName(projectEntity.getName());
		projectDataVo.setMonitorPath(projectEntity.getMonitorDir());
		
		// MonitorFilesEntity
		MonitorFilesEntity monitorFilesEntity = monitorFilesService.getEntity(id);
		List<String> filePaths = monitorFilesEntity.getFiles();
		List<String> samples = filePaths.stream()
				.map(e -> e.substring(e.lastIndexOf(File.separator) + 1, e.length() - 4))
				.collect(Collectors.toList());
		projectDataVo.setMonitorSamples(samples);
		
		// BPCEntity
		List<BPCEntity> bpcEntityList = bpcService.getByTaskId(id);
		List<BasePeakCurrentVo> basePeakCurrentVoList = bpcEntityList.stream()
				.map(e -> new BasePeakCurrentVo(e))
				.collect(Collectors.toList());
		projectDataVo.setBasePeakCurrentList(basePeakCurrentVoList);
		
		// TICEntity
		List<TICEntity> ticEntityList = ticService.getByTaskId(id);
		List<TotalIonCurrentVo> ticVoList = ticEntityList.stream()
				.map(e -> new TotalIonCurrentVo(e))
				.collect(Collectors.toList());
		projectDataVo.setTotalIonCurrentList(ticVoList);
		
		// EICEntity
		List<EICEntity> eicEntityList = eicService.getByTaskId(id);
		List<ExtractIonCurrentVo> ticEntityVoList = eicEntityList.stream()
				.map(e -> new ExtractIonCurrentVo(e))
				.collect(Collectors.toList());
		projectDataVo.setExtractIonCurrentList(ticEntityVoList);
		
		List<LandmarkDataEntity> landmarkDataEntityList = landmarkDataService.getByTaskId(id);
		List<LandmarkDataVo> landmarkDataVoList = landmarkDataEntityList.stream()
				.map(e -> new LandmarkDataVo(e))
				.collect(Collectors.toList());
		projectDataVo.setLandmarkDataList(landmarkDataVoList);
		
		return projectDataVo;
	}
	
	public List<ProjectEntity> getAllProjects() {
		List<ProjectEntity> projectList = projectRepository.findAllSortByCreateTimeDesc();
		return projectList;
	}
	
	
	@Transactional
	public void deleteProject(String id) {
		bpcService.deleteByTaskId(id);
        ticService.deleteByTaskId(id);
        eicService.deleteByTaskId(id);
        landmarkDataService.deleteByTaskId(id);
        monitorFilesService.deleteById(id);
		projectRepository.deleteById(id);
	}

	public void updateRunningProjectsToStopped() {
		projectRepository.updateRunningProjectsToStopped();
	}
	
}
