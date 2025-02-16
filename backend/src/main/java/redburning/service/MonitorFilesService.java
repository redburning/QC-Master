package redburning.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redburning.entity.MonitorFilesEntity;
import redburning.repository.MonitorFilesRepository;

@Service
public class MonitorFilesService {

	@Autowired
	private MonitorFilesRepository monitorFilesRepository;
	
	public MonitorFilesEntity saveOrUpdateEntity(MonitorFilesEntity entity) {
		MonitorFilesEntity savedEntity = monitorFilesRepository.save(entity);
		return savedEntity;
	}

	public MonitorFilesEntity getEntity(String id) {
		Optional<MonitorFilesEntity> optional = monitorFilesRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}
	
	public void deleteById(String id) {
		monitorFilesRepository.deleteById(id);
	}
}
