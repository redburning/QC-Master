package redburning.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redburning.entity.GridLayoutEntity;
import redburning.repository.LayoutRepository;
import redburning.service.GridLayoutService;

@Service
public class GridLayoutServiceImpl implements GridLayoutService {

	@Autowired
	private LayoutRepository layoutRepository;
	
	@Override
	public GridLayoutEntity saveOrUpdateLayout(GridLayoutEntity layoutEntity) {
		GridLayoutEntity saved = layoutRepository.save(layoutEntity);
		return saved;
	}

	@Override
	public List<GridLayoutEntity> getAllLayout() {
		return layoutRepository.findAll();
	}

	@Override
	public void removeLayout(String id) {
		layoutRepository.deleteById(id);
	}

	@Override
	public GridLayoutEntity getLayout(String id) {
		return layoutRepository.getOne(id);
	}

}
