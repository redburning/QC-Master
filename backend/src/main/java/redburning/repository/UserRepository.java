package redburning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import redburning.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {

	@Query("select e from UserEntity e where e.username = ?1")
	public UserEntity findByUsername(String username);
	
}
