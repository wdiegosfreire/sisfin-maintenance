package br.com.dfdevforge.sisfinmaintenance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfinmaintenance.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	public UserEntity findByIdentity(Long identity);
	public UserEntity findByEmail(String email);
}