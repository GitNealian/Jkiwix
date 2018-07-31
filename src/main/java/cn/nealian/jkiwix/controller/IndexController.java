package cn.nealian.jkiwix.controller;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.nealian.jkiwix.model.Article;
import cn.nealian.jkiwix.model.WikiBook;
import cn.nealian.jkiwix.repository.ArticleRepository;
import cn.nealian.jkiwix.repository.WikiBookRepository;
import cn.nealian.nzim.ArticleEntry;
import cn.nealian.nzim.DirectoryEntry;
import cn.nealian.nzim.ZimFile;

@RestController
@RequestMapping("index")
public class IndexController {

	@Autowired
	WikiBookRepository bookRepository;
	@Autowired
	ArticleRepository articleRepository;

	@GetMapping("/")
	public String createIndex(@RequestParam("bookid") String bookId) {
		WikiBook book = bookRepository.findById(bookId).get();
		if (book.status != 0) {
			return "{'status':'error'}";
		}
		book.setStatus(1); // indexing
		bookRepository.save(book);
		SimpleAsyncTaskExecutor sate = new SimpleAsyncTaskExecutor();
		sate.createThread(new Runnable() {
			@Override
			public void run() {
				try {
					ZimFile file = new ZimFile(book.path);
					Stream.iterate(0, item -> item + 1).limit(file.getArticleCount()).parallel().forEach(i -> {
						Article a = new Article();
						a.setId(UUID.randomUUID().toString());
						a.setBook(bookId);
						try {
							DirectoryEntry entry = file.getEntry(i, false);
							a.setIndex(i);
							a.setUrl(entry.getUrl());
							a.setTitle(entry.getTitle());
							if (entry instanceof ArticleEntry && entry.getMimeType().contains("text")) {
								ArticleEntry aentry = (ArticleEntry) entry;
								byte[] buff = new byte[((ArticleEntry) entry).getBlobSize()];
								aentry.getInputStream().read(buff);
								aentry.close();
								a.setContent(new String(buff));
								articleRepository.save(a);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
					book.setStatus(2);
					bookRepository.save(book);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		return "{'status':'success'}";
	}
}
