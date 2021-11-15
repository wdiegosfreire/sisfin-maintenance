package br.com.dfdevforge.sisfinmaintenance.services.user;

import br.com.dfdevforge.common.services.BaseService;
import br.com.dfdevforge.sisfinmaintenance.entities.UserEntity;

public abstract class UserBaseService extends BaseService {
	protected UserEntity userParam;

	public void setEntity(UserEntity userEntity) {
		this.userParam = userEntity;
	}
}