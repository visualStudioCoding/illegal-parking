package com.teraenergy.illegalparking.admin.map;

import com.teraenergy.global.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/admin/map")
public class MapController {
	private static final String PROGRAM_ID = ".Map";
	private static final String PAGE_ID = "admin";
	private static final String DIRECTORY = "/map/Map";

	@Resource(name = "commonService")
	private CommonService commonService;

	@GetMapping("/main")
	public ModelAndView mapMain() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(PAGE_ID + DIRECTORY + "Main");
		log.info(PAGE_ID + DIRECTORY + "Main");
		return modelAndView;
	}
}
