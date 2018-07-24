package cn.nealian.jkiwix.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.nealian.jkiwix.model.Article;
import cn.nealian.jkiwix.model.WikiBook;
import cn.nealian.jkiwix.repository.ArticleRepository;
import cn.nealian.jkiwix.repository.WikiBookRepository;
import cn.nealian.nzim.ArticleEntry;
import cn.nealian.nzim.DirectoryEntry;
import cn.nealian.nzim.ZimFile;

@Controller
@RequestMapping("wiki")
public class ArticleController {
	@Autowired
	WikiBookRepository bookRepository;

	@Autowired
	ArticleRepository articlerRepository;

	@GetMapping("{bookname}/article")
	public String getMainPage(@PathVariable("bookname") String bookname, ModelMap model) {
		WikiBook book = bookRepository.findByName(bookname);
		if (book != null) {
			ZimFile file;
			try {
				file = new ZimFile(book.path);
				model.addAttribute("article", getEntryAsArticle(file.getEntry(file.getMainPage(), true)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "article";
	}

	@GetMapping(value = "{bookname}/article/{url}")
	public String getArticle(@PathVariable("bookname") String bookname, @PathVariable("url") String url,
			ModelMap model) {
		WikiBook book = bookRepository.findByName(bookname);
		if (book != null) {
			ZimFile file;
			try {
				file = new ZimFile(book.path);
				DirectoryEntry entry = file.getEntry("A/" + url, true);
				if(entry == null) {
					return "404";
				}
				model.addAttribute("article", getEntryAsArticle(entry));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "article";
	}

	@GetMapping(value = "{bookname}/*/**")
	public void getResource(@PathVariable("bookname") String bookname, HttpServletRequest request,
			HttpServletResponse response) {
		WikiBook book = bookRepository.findByName(bookname);
		if (book != null) {
			try {
				ZimFile file = new ZimFile(book.path);
				DirectoryEntry entry = file.getEntry(request.getRequestURI().replace("/wiki/abook/", ""), true);
				if (entry == null) {
					return;
				}
				byte[] buff = getEntryAsByteArray(entry);
				response.getOutputStream().write(buff);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private Article getEntryAsArticle(DirectoryEntry entry) {
		Article article = new Article();
		article.setContent(getEntryAsString(entry));
		article.setTitle(entry.getTitle());
		article.setUrl(entry.getUrl());
		return article;
	}

	private String getEntryAsString(DirectoryEntry entry) {
		return new String(getEntryAsByteArray(entry));
	}

	private byte[] getEntryAsByteArray(DirectoryEntry entry) {
		ArticleEntry aentry = (ArticleEntry) entry;
		byte[] buff = new byte[aentry.getBlobSize()];
		try {
			aentry.getInputStream().read(buff);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buff;
	}

}
