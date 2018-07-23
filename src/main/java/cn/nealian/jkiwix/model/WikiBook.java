package cn.nealian.jkiwix.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName="wiki_book")
public class WikiBook {
	@Id
	@Field(index = true, store = true, type = FieldType.Keyword)
	public String id;
	@Field(index = true, store = true, type = FieldType.Keyword)
	public String name;
	@Field(index = true, store = true, type = FieldType.Keyword)
	public String path;
}
