package redburning.repository.spectrum;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import redburning.entity.spectrum.TICEntity;

public interface TICRepository extends JpaRepository<TICEntity, String> {

	@Query("SELECT e FROM TICEntity e WHERE e.taskId = :taskId")
	List<TICEntity> findByTaskId(@Param("taskId") String taskId);
	
	@Modifying
	@Query("DELETE FROM TICEntity e WHERE e.taskId = :taskId")
	void deleteByTaskId(@Param("taskId") String taskId);
	
}
