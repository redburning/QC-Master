package redburning.repository.spectrum;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import redburning.entity.spectrum.EICEntity;

public interface EICRepository extends JpaRepository<EICEntity, String> {

	@Query("SELECT e FROM EICEntity e WHERE e.taskId = :taskId")
	List<EICEntity> findByTaskId(@Param("taskId") String taskId);
	
	@Query("SELECT e FROM EICEntity e WHERE e.taskId = :taskId AND e.landmark = :landmark")
	EICEntity findByTaskIdAndLandmark(@Param("taskId") String taskId, @Param("landmark") String landmark);
	
	@Modifying
	@Query("DELETE FROM EICEntity e WHERE e.taskId = :taskId")
	void deleteByTaskId(@Param("taskId") String taskId);
	
}
