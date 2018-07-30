package cn.nealian.jkiwix.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "wiki_book")
public class WikiBook {
	@Id
	@Field(index = true, store = true, type = FieldType.Keyword)
	public String id;
	@Field(index = true, store = true, type = FieldType.Keyword)
	public String title;
	@Field(index = true, store = true, type = FieldType.Keyword)
	public String path;
	@Field(index = true, store = true, type = FieldType.Keyword)
	public String author;
	@Field(index = true, store = true, type = FieldType.Keyword)
	public String date;
	@Field(index = true, store = true, type = FieldType.Keyword)
	public String publisher;
	@Field(index = true, store = true, type = FieldType.Keyword)
	public String articleCount;
	@Field(index = true, store = true, type = FieldType.Keyword)
	public String size;
	@Field(index = true, store = true, type = FieldType.Keyword)
	public String mediaCount;
	@Field(index = true, store = true, type = FieldType.Keyword)
	public String language;
	@Field(index = true, store = true, type = FieldType.Keyword)
	public String description;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(String articleCount) {
		this.articleCount = articleCount;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getMediaCount() {
		return mediaCount;
	}

	public void setMediaCount(String mediaCount) {
		this.mediaCount = mediaCount;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
