package cn.nealian.jkiwix.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import cn.nealian.jkiwix.model.Article;

public interface ArticleRepository extends ElasticsearchCrudRepository<Article, String> {
	public Article findByUrlAndBook(String url, String book);

	public List<Article> findByContentAndBook(String content, String book);

	public List<Article> findByContent(String content);
}
