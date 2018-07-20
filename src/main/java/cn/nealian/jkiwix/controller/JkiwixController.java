package cn.nealian.jkiwix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JkiwixController {
	@GetMapping(value = "/")
	public String index() {
		return "index";
	}
}
