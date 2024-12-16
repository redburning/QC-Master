package redburning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import redburning.entity.LandmarkEntity;

public interface LandmarkRepository extends JpaRepository<LandmarkEntity, String> {

}
