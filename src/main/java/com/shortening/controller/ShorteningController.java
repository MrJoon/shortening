package com.shortening.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shortening.entity.Shortening;
import com.shortening.service.ShorteningService;

@Controller
public class ShorteningController {

	@Autowired
	private ShorteningService shorteningService;

	@GetMapping("/")
	public String main() {
		return "index";
	}

	@PostMapping("/")
	public String save(Model model, @RequestParam String url) {
		Shortening shortening = shorteningService.save(url);
		String result = "success";
		
		if(shortening == null) {	 // url값이 빈값, null, 중간에 공백 시
			result = "error";
		}else if(shortening.getShorturl().equals("")) {	// shorturl 중복체크 제한 횟수 초과 시 
			result = "empty";
		}
		
		model.addAttribute("url", shortening);
		model.addAttribute("result", result);
		return "index";
	}

	@GetMapping(value = "/{shorturl}")
	public String shorturl(Model model, @PathVariable("shorturl") String shorturl) {
		model.addAttribute("shortening", shorteningService.findByShorturl(shorturl));
		return "redirect";
	}

	@GetMapping(value = "/**/**")
	public String error() {
		return "redirect";
	}
}
