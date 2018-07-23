package cn.nealian.jkiwix.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.nealian.jkiwix.model.Article;
import cn.nealian.jkiwix.model.WikiBook;
import cn.nealian.jkiwix.repository.WikiBookRepository;
import cn.nealian.nzim.ArticleEntry;
import cn.nealian.nzim.ZimFile;

@Controller
@RequestMapping(value="wikibook")
public class WikiBookController {
	
	@Autowired
	WikiBookRepository bookRepository;
	@GetMapping(value = "/")
	public String getMethodName(@RequestParam("bookname") String name, ModelMap model) {
		Article article = new Article();
		WikiBook book = bookRepository.findByName(name);
		if(book != null) {
			try {
				ZimFile file = new ZimFile(book.path);
				ArticleEntry entry = (ArticleEntry) file.getEntry(file.getMainPage(), false);
				byte[] buff = new byte[entry.getBlobSize()];
				entry.getInputStream().read(buff);
				article.setContent(new String(buff, "utf-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		article.setTitle("an article");
		model.addAttribute("article", article);
		return "article";
	}

}
