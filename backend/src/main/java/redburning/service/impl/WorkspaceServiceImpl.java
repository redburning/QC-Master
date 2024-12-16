package redburning.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redburning.entity.WorkspaceEntity;
import redburning.repository.WorkspaceRepository;
import redburning.service.WorkspaceService;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

	@Autowired
	private WorkspaceRepository workspaceRepository;

	@Override
	public WorkspaceEntity saveOrUpdateWorkspace(WorkspaceEntity workspace) {
		WorkspaceEntity savedWorkspace = workspaceRepository.save(workspace);
		return savedWorkspace;
	}

	@Override
	public WorkspaceEntity getWorkspace(String id) {
		WorkspaceEntity workspace = workspaceRepository.getOne(id);
		return workspace;
	}
	
	@Override
	public List<WorkspaceEntity> getAllWorkspaces() {
		List<WorkspaceEntity> workspaceList = workspaceRepository.findAll();
		return workspaceList;
	}
	
	@Override
	public void deleteWorkspace(String id) {
		workspaceRepository.deleteById(id);
	}


}
