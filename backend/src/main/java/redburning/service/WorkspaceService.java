package redburning.service;

import java.util.List;

import redburning.entity.WorkspaceEntity;

public interface WorkspaceService {

	public WorkspaceEntity saveOrUpdateWorkspace(WorkspaceEntity workspaceEntity);
	
	public WorkspaceEntity getWorkspace(String id);
	
	public List<WorkspaceEntity> getAllWorkspaces();
	
	public void deleteWorkspace(String id);
	
}
