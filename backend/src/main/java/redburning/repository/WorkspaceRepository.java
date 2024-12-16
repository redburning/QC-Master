package redburning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import redburning.entity.WorkspaceEntity;

public interface WorkspaceRepository extends JpaRepository<WorkspaceEntity, String> {

}
