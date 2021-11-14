package br.com.dfdevforge.sisfinmaintenance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dfdevforge.sisfinmaintenance.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	public UserEntity findByIdentity(Long identity);
	public UserEntity findByEmail(String email);
}