package cn.nealian.jkiwix.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "wiki", type = "article")
public class Article {
	@Id
	private String id;
	
	@Field(index = true, store = true, type = FieldType.Keyword)
	public String book;
	
	@Field(index = true, store = true, type = FieldType.Keyword)
	private int index;
	
	@Field(index = true, store = true, type = FieldType.Text)
	private String title;
	
	@Field(index = true, store = true, type = FieldType.Text)
	private String url;
	
	@Field(index = true, store = false, type = FieldType.Text)
	private String content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public String getBook() {
		return this.book;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
