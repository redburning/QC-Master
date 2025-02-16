package redburning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redburning.entity.PanelOptionsEntity;
import redburning.repository.PanelOptionsRepository;

@Service
public class PanelOptionsService {

	@Autowired
	private PanelOptionsRepository panelOptionsRepository;
	
	public PanelOptionsEntity savePanelOptions(PanelOptionsEntity entity) {
		PanelOptionsEntity saved = panelOptionsRepository.save(entity);
		return saved;
	}

	public PanelOptionsEntity getPanelOptions(String id) {
		return panelOptionsRepository.getOne(id);
	}
}
