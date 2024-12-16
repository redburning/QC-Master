package redburning.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redburning.dto.LoginDTO;
import redburning.entity.UserEntity;
import redburning.repository.UserRepository;
import redburning.service.LoginService;
import redburning.vo.Result;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Result login(LoginDTO loginDTO) {
		if (loginDTO.getUsername() == null || loginDTO.getUsername().isEmpty()){
            return new Result(500, "username cann't be null");
        }
        if (loginDTO.getPassword() == null || loginDTO.getPassword().isEmpty()){
            return new Result(501, "password cann't be null");
        }
        // 通过登录名查询用户
        UserEntity user = userRepository.findByUsername(loginDTO.getUsername());
        // 校验密码是否正确
        if (user != null && user.getPassword().equals(loginDTO.getPassword())){
            return new Result(200, "login success", user);
        }
        return new Result(502, "login failed");
	}

}
