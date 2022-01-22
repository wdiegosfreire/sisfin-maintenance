package br.com.dfdevforge.sisfinmaintenance.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfinmaintenance.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	public Optional<UserEntity> findByIdentity(Long identity);
	public Optional<UserEntity> findByEmail(String email);
}