package com.shortening.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Table(indexes = { @Index(name = "idx_shortening1", unique=true, columnList = "shorturl"),
		@Index(name = "idx_shortening2", unique=true, columnList = "url")})
public class Shortening {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String url;
	private String shorturl;
	
	public Shortening() {
		
	}
	
	@Builder
	public Shortening(String url, String shorturl) {
		this.url = url;
		this.shorturl = shorturl;
	}
	
}
