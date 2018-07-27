package cn.nealian.jkiwix.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.nealian.jkiwix.model.WikiBook;
import cn.nealian.jkiwix.repository.WikiBookRepository;
import cn.nealian.nzim.ArticleEntry;
import cn.nealian.nzim.ZimFile;

@Controller
@RequestMapping(value="wikibook")
public class WikiBookController {
	
	@Autowired
	WikiBookRepository bookRepository;
	
	@GetMapping(value="/add")
	@ResponseBody
	public String addWikiBook(@RequestParam("path")String path) {
		File  f = new File(path);
		if(f.exists()) {
			WikiBook book = new WikiBook();
			book.setId(UUID.randomUUID().toString());
			book.setPath(path);
			book.setSize(f.length()/1024/1024 + "MB");
			try {
				ZimFile file = new ZimFile(path);
				book.setTitle(readMetaData("Title", file));
				book.setAuthor(readMetaData("Creator", file));
				book.setDate(readMetaData("Date", file));
				book.setDescription(readMetaData("Description", file));
				book.setPublisher(readMetaData("Publisher", file));
				book.setLanguage(readMetaData("Language", file));
			} catch (IOException e) {
				e.printStackTrace();
			}
			bookRepository.save(book);
			return "{'status': 'success'}";
		}
		return "{'status': 'error'}";
	}
	
	private String readMetaData(String property, ZimFile file) throws IOException {
		System.out.println(property);
		ArticleEntry entry = (ArticleEntry) file.getEntry("M/"+property, false);
		System.out.println(entry);
		if(entry instanceof ArticleEntry) {
			byte[] buff = new byte[entry.getBlobSize()];
			entry.getInputStream().read(buff);
			return new String(buff, "utf-8");
		}
		return null;
	}
}
