package cn.nealian.jkiwix.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import cn.nealian.jkiwix.model.WikiBook;
import cn.nealian.jkiwix.repository.WikiBookRepository;

@Controller
public class JkiwixController {
	@Autowired
	WikiBookRepository bookRepository;
	@GetMapping(value = "/")
	public String index(ModelMap model) {
		Iterator<WikiBook> books = bookRepository.findAll().iterator();
		List<WikiBook> bookList = new ArrayList<>();
		while(books.hasNext()) {
			bookList.add(books.next());
		}
		model.addAttribute("bookList", bookList);
		return "index";
	}
}
