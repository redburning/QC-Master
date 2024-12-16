package redburning.service;

import redburning.dto.LoginDTO;
import redburning.vo.Result;

public interface LoginService {

	public Result login(LoginDTO loginDTO);
	
}
