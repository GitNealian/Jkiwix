package cn.nealian.jkiwix.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.nealian.jkiwix.model.WikiBook;
import cn.nealian.jkiwix.repository.ArticleRepository;
import cn.nealian.jkiwix.repository.WikiBookRepository;
import cn.nealian.nzim.ArticleEntry;
import cn.nealian.nzim.ZimFile;

@Controller
@RequestMapping(value = "/wikibook")
public class WikiBookController {

	@Autowired
	WikiBookRepository bookRepository;
	@Autowired
	ArticleRepository articleRepository;

	@PostMapping(value = "/add")
	@ResponseBody
	public String addWikiBook(@RequestParam("path") String path) {
		File f = new File(path);
		if (f.exists()) {
			WikiBook book = new WikiBook();
			book.setId(UUID.randomUUID().toString());
			book.setPath(path);
			book.setSize(f.length() / 1024 / 1024 + "MB");
			try {
				ZimFile file = new ZimFile(path);
				book.setTitle(readMetaData("Title", file));
				book.setAuthor(readMetaData("Creator", file));
				book.setDate(readMetaData("Date", file));
				book.setDescription(readMetaData("Description", file));
				book.setPublisher(readMetaData("Publisher", file));
				book.setLanguage(readMetaData("Language", file));
				book.setStatus(0);// not indexed yet;
			} catch (IOException e) {
				return "{'status': 'error', 'msg':'" + e.getMessage() + "'}";
			}
			bookRepository.save(book);
			return "{'status': 'success'}";
		}
		return "{'status': 'error','msg':'文件不存在'}";
	}

	@GetMapping(value = "/delete")
	@ResponseBody
	public String deleteWikiBook(@RequestParam("bookid") String bookid) {
		bookRepository.deleteById(bookid);
		// TODO： 删除索引
		return "{'status': 'success'}";
	}

	private String readMetaData(String property, ZimFile file) throws IOException {
		ArticleEntry entry = (ArticleEntry) file.getEntry("M/" + property, false);
		if (entry instanceof ArticleEntry) {
			byte[] buff = new byte[entry.getBlobSize()];
			entry.getInputStream().read(buff);
			return new String(buff, "utf-8");
		}
		return null;
	}
}
