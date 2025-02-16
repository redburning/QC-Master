package redburning.repository.spectrum;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import redburning.entity.spectrum.LandmarkDataEntity;

public interface LandmarkDataRepository extends JpaRepository<LandmarkDataEntity, String> {

	@Query("SELECT e FROM LandmarkDataEntity e WHERE e.taskId = :taskId")
	List<LandmarkDataEntity> findByTaskId(@Param("taskId") String taskId);
	
	@Query("SELECT e FROM LandmarkDataEntity e WHERE e.taskId = :taskId AND e.landmarkName = :landmarkName")
	LandmarkDataEntity findByTaskIdAndLandmarkName(@Param("taskId") String taskId, @Param("landmarkName") String landmarkName);
	
	@Modifying
	@Query("DELETE FROM LandmarkDataEntity e WHERE e.taskId = :taskId")
	void deleteByTaskId(@Param("taskId") String taskId);
}
