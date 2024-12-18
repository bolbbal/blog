package com.blog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blog.domain.NoticeVo;

@Controller
@RequestMapping("/thy")
public class ThymeleafController {

	@GetMapping("/ex1")
	public String basic1() {
		return "sample/thy1";
	}

	@GetMapping("/ex2")
	public String basic2(Model model) {

		List<NoticeVo> list = new ArrayList<>();
		NoticeVo notice = new NoticeVo();

		notice.setIdx(1L);
		notice.setTitle("ウさん");
		notice.setContent("死にたい？");
		notice.setWriter("キム");

		model.addAttribute("notice", notice);

		return "sample/thy2";
	}

	@GetMapping("/ex3")
	public String basic3(Model model) {

		List<NoticeVo> list = new ArrayList<>();

		for (Long i = 1L; i <= 10; i++) {
			NoticeVo notice = new NoticeVo();

			notice.setIdx(i);
			notice.setTitle("ウさん" + i);
			notice.setContent("死にたい？" + i);
			notice.setWriter("キムシヌ" + i);

			list.add(notice);
		}

		model.addAttribute("list", list);

		return "sample/thy3";
	}

	@GetMapping("/ex4")
	public String basic4(Model model) {

		List<NoticeVo> list = new ArrayList<>();

		for (Long i = 1L; i <= 10; i++) {
			NoticeVo notice = new NoticeVo();

			notice.setIdx(i);
			notice.setTitle("ウさん" + i);
			notice.setContent("死にたい？" + i);
			notice.setWriter("キムシヌ" + i);

			list.add(notice);
		}

		model.addAttribute("list", list);

		return "sample/thy4";
	}

	@GetMapping("/ex5")
	public String basic5(Model model) {
		return "sample/thy5";
	}

	@GetMapping("/ex6")
	public String basic6(@RequestParam("param1") String p1,
			@RequestParam("param2") String p2, Model model) {

		return "sample/thy5";
	}
}
