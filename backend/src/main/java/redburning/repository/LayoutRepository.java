package redburning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import redburning.entity.GridLayoutEntity;

public interface LayoutRepository extends JpaRepository<GridLayoutEntity, String> {

}
