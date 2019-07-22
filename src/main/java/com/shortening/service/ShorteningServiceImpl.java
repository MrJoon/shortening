package com.shortening.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shortening.entity.Shortening;
import com.shortening.repository.ShorteningRepository;
import com.shortening.utils.Base62;

@Service
public class ShorteningServiceImpl implements ShorteningService {

	@Autowired
	ShorteningRepository shorteningRepository;

	@Value("${property.findShorturlLimit}")
	private int findShorturlLimit; // shorturl 생성 초과로 인한 무한 loop 방어코드

	@Override
	public Shortening save(String url) {
		if (url == null) {
			return null;
		}

		url = url.trim();
		// url 중간에 공백이 있거나, 공백 혹은 빈값으로 되어 있을 경우 null 리턴
		if (url.indexOf(" ") > -1 || url.equals("")) {
			return null;
		}

		// 동일 url로 맞추기 위한 suffix '/', '\' 제거
		while (url.endsWith("/") || url.endsWith("\\")) {
			url = url.substring(0, url.length()-1);
		};

		// Http, Https 프로토콜 없이 입력 했을 경우는 http로 prefix
		if (!url.startsWith("https://") && !url.startsWith("http://")) {
			url = "http://" + url;
		}

		Shortening shortening = shorteningRepository.findByUrl(url);

		// 동일한 url이 아닐 경우 shorturl 새로 생성(http, https로 프로토콜이 다를 경우 동일 url로 보지 않음)
		if (shortening == null) {
			String shorturl = "";
			int cnt = 0;
			do {
				shorturl = Base62.encode();
				cnt++;
			} while (shorteningRepository.findByShorturl(shorturl) != null && cnt < findShorturlLimit);

			if (cnt == findShorturlLimit) {
				return new Shortening("", "");
			}

			shortening = shorteningRepository.save(new Shortening(url, shorturl));
		}

		return shortening;
	}

	@Override
	public Shortening findByShorturl(String shorturl) {
		return shorteningRepository.findByShorturl(shorturl);
	}
}
