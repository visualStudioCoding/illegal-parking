package com.teraenergy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class IndexController {

    @GetMapping(value = {"/", "/index", "/admin"})
    public String indexPage() {
        return "redirect:/admin/map/main";
    }
}
