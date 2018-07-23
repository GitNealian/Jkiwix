package cn.nealian.jkiwix.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;

import cn.nealian.jkiwix.model.Article;
import cn.nealian.jkiwix.model.WikiBook;
import cn.nealian.jkiwix.repository.ArticleRepository;
import cn.nealian.jkiwix.repository.WikiBookRepository;
import cn.nealian.nzim.ArticleEntry;
import cn.nealian.nzim.ZimFile;

@Controller
public class ArticleController {
	@Autowired
	WikiBookRepository bookRepository;

	@Autowired
	ArticleRepository articlerRepository;

	@GetMapping(value = "{bookname}/article/{url}")
	public String getArticle(@PathVariable("bookname") String bookname, @PathVariable("url") String url,
			ModelMap model) {
		WikiBook book = bookRepository.findByName(bookname);

		if (book != null) {
			try {
				ZimFile file = new ZimFile(book.path);
				ArticleEntry entry = (ArticleEntry) file.getEntry(url, true);
				byte[] buff = new byte[entry.getBlobSize()];
				entry.getInputStream().read(buff);
				Article article = new Article();
				article.setTitle(entry.getTitle());
				article.setContent(new String(buff));
				model.addAttribute("article", article);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "article";
	}

	@GetMapping(value = "{bookname}/-/**")
	@ResponseBody
	public String getResource(@PathVariable("bookname") String bookname, HttpServletRequest request) {
		WikiBook book = bookRepository.findByName(bookname);

		System.out.println(request.getRequestURI().replace("/abook/", ""));
		if (book != null) {
			try {
				ZimFile file = new ZimFile(book.path);
				ArticleEntry entry = (ArticleEntry) file.getEntry(request.getRequestURI().replace("/abook/", ""), true);
				byte[] buff = new byte[entry.getBlobSize()];
				entry.getInputStream().read(buff);
				return new String(buff);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "article";
	}
}
