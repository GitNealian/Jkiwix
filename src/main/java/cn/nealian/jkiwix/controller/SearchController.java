package cn.nealian.jkiwix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	@GetMapping(value="/wiki/{bookid}/article/search")
	public String doSearch(@PathVariable("bookid")String bookid, @RequestParam("q") String q, ModelMap model) {
		List<Article> articles = articleRepository.findByContentAndBook(q, bookid);
		model.addAttribute("articles", articles);
		return "search";
	}
	
	@GetMapping(value="/search")
	public String doSearch(@RequestParam("q") String q, ModelMap model) {
		List<Article> articles = articleRepository.findByContent(q);
		model.addAttribute("articles", articles);
		return "search";
	}
}
