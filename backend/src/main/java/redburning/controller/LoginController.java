package redburning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import redburning.dto.LoginDTO;
import redburning.service.LoginService;
import redburning.vo.Result;

@RestController
public class LoginController {

	@Autowired
	LoginService loginService;

	@PostMapping(value = "/login")
	public Result login(@RequestBody LoginDTO loginDTO) {
		return loginService.login(loginDTO);
	}

}
