package redburning.repository.spectrum;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import redburning.entity.spectrum.BPCEntity;

public interface BPCRepository extends JpaRepository<BPCEntity, String> {

	@Query("SELECT e FROM BPCEntity e WHERE e.taskId = :taskId")
	List<BPCEntity> findByTaskId(@Param("taskId") String taskId);
	
	@Modifying
	@Query("DELETE FROM BPCEntity e WHERE e.taskId = :taskId")
	void deleteByTaskId(@Param("taskId") String taskId);
	
}
