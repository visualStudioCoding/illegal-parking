package com.teraenergy.illegalparking.admin.map;

import com.teraenergy.global.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/admin/map")
public class MapController {
	private static final String PROGRAM_ID = ".Map";
	private static final String PAGE_ID = "admin";
	private static final String DIRECTORY = "/map/Map";

	@Resource(name = "commonService")
	private CommonService commonService;
	@Resource(name = "mapService")
	private MapService mapService;

	@GetMapping("/main")
	public ModelAndView mapMain() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		List<Map<String, Object>> polygonList = mapService.polygonList(null, PAGE_ID, PROGRAM_ID);

		modelAndView.setViewName(PAGE_ID + DIRECTORY + "Main");
		modelAndView.addObject("polygonList", polygonList);
		log.info(PAGE_ID + DIRECTORY + "Main");
		return modelAndView;
	}

	@PostMapping("/polygonInsert")
	public Map<String, Object> polygonInsert(@RequestBody List<Map<String, Object>> paramList) throws Exception {
		for (Map<String, Object> dataMap : paramList) {
			System.out.println(dataMap);
			System.out.println(dataMap.get("points"));

			List<Object> test = (List<Object>) dataMap.get("points");
			System.out.println(test.get(0));
			test.add(test.get(0));

			dataMap.put("points", test);
			commonService.insertContents(dataMap, PAGE_ID + PROGRAM_ID + ".polygonInsert");
		}
		Map<String, Object> testMap = new HashMap<>();
		testMap.put("test", "test");
		return testMap;
	}
}
