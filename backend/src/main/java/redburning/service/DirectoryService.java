package redburning.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service
public class DirectoryService {

	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String PID = "pId";
	private static final String IS_PARENT = "isParent";
	
	public String getFileList(String requestBody) {
		String id = "";
		if (requestBody != null && !requestBody.isEmpty() && !requestBody.equals("{}")) {
			id = JSONObject.parseObject(requestBody).getString("id");
		}

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		if ("".equals(id)) {
			File[] roots = File.listRoots();
			for (int i = 0; i < roots.length; i++) {
				HashMap<String, Object> hm = new HashMap<String, Object>();
				hm.put(ID, roots[i]);
				hm.put(NAME, roots[i]);
				hm.put(PID, null);
				hm.put(IS_PARENT, true);
				list.add(hm);
			}
		} else { // 如果传入了id, 则根据指定的id查询信息
			File root = new File(id);
			File[] fileslist = root.listFiles();
			for (File file : fileslist) {
				HashMap<String, Object> hm = new HashMap<String, Object>();
				hm.put(ID, file.getAbsolutePath());
				hm.put(NAME, file.getName());
				hm.put(PID, id);
				if (file.isDirectory()) {
					hm.put(IS_PARENT, true);
				} else {
					// hm.put(IS_PARENT, false);
				}
				list.add(hm);
			}
		}
		return JSON.toJSONString(list);
	}

}
