package cn.nealian.jkiwix.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import cn.nealian.jkiwix.model.Article;

public interface ArticleRepository extends ElasticsearchCrudRepository<Article, String> {
	public Article findByUrlAndBook(String url, String book);

	public Page<Article> findByContentAndBook(String content, String book, Pageable pageable);
	
	public void deleteByBook(String book);
	
	public Page<Article> findByContent(String content, Pageable pageable);
}
