package redburning.service.spectrum;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redburning.entity.spectrum.TICEntity;
import redburning.repository.spectrum.TICRepository;

@Service
public class TICService {

	@Autowired
	private TICRepository totalIonCurrentRepository;

	public List<TICEntity> getByTaskId(String taskId) {
		return totalIonCurrentRepository.findByTaskId(taskId);
	}

	public TICEntity save(TICEntity entity) {
		return totalIonCurrentRepository.save(entity);
	}

	public void deleteByTaskId(String taskId) {
		totalIonCurrentRepository.deleteByTaskId(taskId);
	}
	
}
