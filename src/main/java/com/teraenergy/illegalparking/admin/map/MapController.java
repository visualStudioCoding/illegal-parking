package com.teraenergy.illegalparking.admin.map;

import com.teraenergy.global.service.CommonService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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

//		Map<String, Object> searchMap = new HashMap<>();
//		searchMap.put("searchZoneType", "");
		List<Map<String, Object>> polygonList = mapService.polygonList(null, PAGE_ID, PROGRAM_ID);

		modelAndView.setViewName(PAGE_ID + DIRECTORY + "Main");
		modelAndView.addObject("polygonList", polygonList);
		log.info(PAGE_ID + DIRECTORY + "Main");
		return modelAndView;
	}
	@GetMapping("/test")
	public ModelAndView test() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		log.info(PAGE_ID + DIRECTORY + "Test");
		return modelAndView;
	}

	@PostMapping("/insertPolygon")
	public Map<String, Object> insertPolygon(@RequestBody Map<String, Object> param) throws Exception {
		List<Map<String,Object>> polygons = (List<Map<String, Object>>) param.get("polygonData");
		for (Map<String, Object> dataMap : polygons) {
			System.out.println(dataMap);
			System.out.println(dataMap.get("points"));

			List<Object> pointList = (List<Object>) dataMap.get("points");
			System.out.println(pointList.get(0));
			pointList.add(pointList.get(0));

			dataMap.put("points", pointList);
			dataMap.put("zoneType", "N");
			commonService.insertContents(dataMap, PAGE_ID + PROGRAM_ID + ".insertPolygon");
		}

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("polygonList", polygonList(param).get("polygonList"));
		return resultMap;
	}

	@DeleteMapping("/deletePolygon")
	public Map<String, Object> deletePolygon(@RequestBody Map<String, Object> paramMap) throws Exception {
		commonService.deleteContents(paramMap, PAGE_ID + PROGRAM_ID + ".deletePolygon");

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("polygonList", polygonList(paramMap).get("polygonList"));
		return resultMap;
	}

	@PutMapping("/updatePolygon")
	public Map<String, Object> updatePolygon(@RequestBody Map<String, Object> paramMap) throws Exception {
		commonService.updateContents(paramMap, PAGE_ID + PROGRAM_ID + ".updatePolygon");

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("polygonList", polygonList(paramMap).get("polygonList"));
		return resultMap;
	}

	@GetMapping("/polygonDetail")
	public Map<String, Object> polygonDetail(@RequestParam int polySeq) throws Exception {
		Map<String, Object> result = (Map<String, Object>) commonService.selectContents(polySeq, PAGE_ID + PROGRAM_ID + ".selectPolygonDetail");

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", result);
		resultMap.put("success", "ok");
		return resultMap;
	}

	@GetMapping("/polygonList")
	public Map<String, Object> polygonList(@RequestParam Map<String, Object> searchParam) throws Exception {
		List<Map<String, Object>> polygonList = mapService.polygonList(searchParam, PAGE_ID, PROGRAM_ID);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("polygonList", polygonList);
		resultMap.put("success", "ok");
		return resultMap;
	}
}
