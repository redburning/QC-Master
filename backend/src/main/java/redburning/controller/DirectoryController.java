package redburning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import redburning.service.DirectoryService;

@RestController
public class DirectoryController {

	@Autowired
	private DirectoryService dirService;
	
	@PostMapping("/tree")
	public String getFileList(@RequestBody String requestBody) {
		return dirService.getFileList(requestBody);
	}
	
}
