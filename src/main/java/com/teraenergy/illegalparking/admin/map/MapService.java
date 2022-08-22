package com.teraenergy.illegalparking.admin.map;

import com.teraenergy.global.mapper.CommonMapper;
import com.teraenergy.global.service.CommonService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class MapService {

	@Resource(name="commonMapper")
	private CommonMapper commonMapper;

	public List<Map<String, Object>> polygonList(Map<String, Object> params, String pageId, String programId) throws Exception {
		List<Map<String, Object>> polygonList = (List<Map<String, Object>>) commonMapper.selectList(null, pageId + programId + ".selectPolygonList");
		return polygonList;
	}
}
