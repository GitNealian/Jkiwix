package cn.nealian.jkiwix.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import cn.nealian.jkiwix.model.Article;

public interface ArticleRepository extends ElasticsearchCrudRepository<Article, String>{
	
}
