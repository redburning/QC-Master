package redburning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import redburning.service.TreeService;

@RestController
public class TreeController {

	@Autowired
	private TreeService treeService;
	
	@PostMapping("/tree")
	public String getFileList(@RequestBody String requestBody) {
		return treeService.getFileList(requestBody);
	}
	
}
