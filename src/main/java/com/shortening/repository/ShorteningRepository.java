package com.shortening.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shortening.entity.Shortening;

public interface ShorteningRepository extends JpaRepository<Shortening, Long> {
	Shortening findByUrl(String url);

	Shortening findByShorturl(String shorturl);
}
