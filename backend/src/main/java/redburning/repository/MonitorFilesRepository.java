package redburning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import redburning.entity.MonitorFilesEntity;

public interface MonitorFilesRepository extends JpaRepository<MonitorFilesEntity, String> {

}
