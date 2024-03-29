package br.com.dfdevforge.sisfinmaintenance.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.dfdevforge.common.entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "usr_user")
@EqualsAndHashCode(callSuper = false, of = {"identity"})
public class UserEntity extends BaseEntity {
	@Id
	@Column(name = "usr_identity")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long identity;

	@Column(name = "usr_name")
	private String name;

	@Column(name = "usr_password")
	private String password;

	@Column(name = "usr_email")
	private String email;
}