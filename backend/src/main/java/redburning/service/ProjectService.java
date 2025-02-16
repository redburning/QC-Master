package redburning.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redburning.entity.ProjectEntity;
import redburning.repository.ProjectRepository;
import redburning.service.spectrum.BPCService;
import redburning.service.spectrum.EICService;
import redburning.service.spectrum.LandmarkDataService;
import redburning.service.spectrum.TICService;

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
