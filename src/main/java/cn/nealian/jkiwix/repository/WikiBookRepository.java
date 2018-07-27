package cn.nealian.jkiwix.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import cn.nealian.jkiwix.model.WikiBook;

public interface WikiBookRepository extends ElasticsearchCrudRepository<WikiBook, String>{
}
