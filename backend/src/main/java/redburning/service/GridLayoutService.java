package redburning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redburning.entity.GridLayoutEntity;
import redburning.repository.LayoutRepository;

@Service
public class GridLayoutService {

	@Autowired
	private LayoutRepository layoutRepository;
	
	public GridLayoutEntity saveOrUpdateLayout(GridLayoutEntity layoutEntity) {
		GridLayoutEntity saved = layoutRepository.save(layoutEntity);
		return saved;
	}

	public List<GridLayoutEntity> getAllLayout() {
		return layoutRepository.findAll();
	}

	public void removeLayout(String id) {
		layoutRepository.deleteById(id);
	}

	public GridLayoutEntity getLayout(String id) {
		return layoutRepository.getOne(id);
	}

}
