package redburning.service;

import java.util.List;

import redburning.entity.GridLayoutEntity;

public interface GridLayoutService {

	public GridLayoutEntity saveOrUpdateLayout(GridLayoutEntity layoutEntity);
	
	public List<GridLayoutEntity> getAllLayout();
	
	public GridLayoutEntity getLayout(String id);
	
	public void removeLayout(String id);
	
}
