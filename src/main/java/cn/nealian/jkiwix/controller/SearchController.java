package cn.nealian.jkiwix.controller;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import cn.nealian.jkiwix.model.Article;
import cn.nealian.jkiwix.repository.ArticleRepository;

@Controller
public class SearchController {

	@Autowired
	ArticleRepository articleRepository;

	@GetMapping(value = "/wiki/{bookid}/article/search")
	public String doSearch(@PathVariable("bookid") String bookid, @RequestParam("q") String q,
			@RequestParam(value = "page", defaultValue = "1") @Min(1) int page,
			@RequestParam(value = "size", defaultValue = "10") @Min(1) int size, ModelMap model) {

		Page<Article> articles = articleRepository.findByContentAndBook(q, bookid, PageRequest.of(page - 1, size));
		model.addAttribute("articles", articles);
		model.addAttribute("q", q);
		return "search";
	}

	@GetMapping(value = "/search")
	public String doSearch(@RequestParam("q") String q,
			@RequestParam(value = "page", defaultValue = "1") @Min(1) int page,
			@RequestParam(value = "size", defaultValue = "10") @Min(1) int size, ModelMap model) {
		Page<Article> articles = articleRepository.findByContent(q, PageRequest.of(page-1, size));
		model.addAttribute("articles", articles);
		model.addAttribute("q", q);
		return "search";
	}
}
