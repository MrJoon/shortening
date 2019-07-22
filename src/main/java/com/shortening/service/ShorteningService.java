package com.shortening.service;

import com.shortening.entity.Shortening;

public interface ShorteningService {
	public Shortening save(String url);

	public Shortening findByShorturl(String shorturl);
}
