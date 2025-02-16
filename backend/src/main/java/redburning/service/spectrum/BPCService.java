package redburning.service.spectrum;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redburning.entity.spectrum.BPCEntity;
import redburning.repository.spectrum.BPCRepository;

@Service
public class BPCService {

	@Autowired
	private BPCRepository basePeakCurrentRepository;
	
	public List<BPCEntity> getByTaskId(String taskId) {
		return basePeakCurrentRepository.findByTaskId(taskId);
	}

	public BPCEntity save(BPCEntity entity) {
		return basePeakCurrentRepository.save(entity);
	}

	public void deleteByTaskId(String taskId) {
		basePeakCurrentRepository.deleteByTaskId(taskId);
	}
	
}
