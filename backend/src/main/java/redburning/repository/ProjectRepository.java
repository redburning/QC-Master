package redburning.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import redburning.entity.ProjectEntity;

public interface ProjectRepository extends JpaRepository<ProjectEntity, String> {

	@Query("SELECT e FROM ProjectEntity e ORDER BY e.createTime DESC")
	List<ProjectEntity> findAllSortByCreateTimeDesc();
	
	/**
     * 将所有 status 为 'running' 的项目状态更新为 'stopped'
     * @return 更新的记录数
     */
    @Modifying
    @Transactional
    @Query("UPDATE ProjectEntity p SET p.status = 'stopped' WHERE p.status = 'running'")
	void updateRunningProjectsToStopped();
    
}
