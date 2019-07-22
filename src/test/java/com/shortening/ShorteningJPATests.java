package com.shortening;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.shortening.entity.Shortening;
import com.shortening.repository.ShorteningRepository;
import com.shortening.utils.Base62;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ShorteningJPATests {

	@Autowired
	private TestEntityManager testEntityManager;

	@Autowired
	private ShorteningRepository shorteningRepository;

	@Test
	public void shorteningSaveTest() {
		Shortening shortening = Shortening.builder()
				.url("https://www.google.co.kr")
				.shorturl("AbCdEf12")
				.build();
		testEntityManager.persist(shortening);
		
		assertThat(shorteningRepository.getOne(shortening.getId()), is(shortening));
	}

	@Test
	public void shorteningSaveAndFindByShorturlTest() throws Exception {
		Shortening shortening1 = Shortening.builder()
				.url("google.co.kr")
				.shorturl(Base62.encode())
				.build();
		testEntityManager.persist(shortening1);
		
		Shortening shortening2 = Shortening.builder()
				.url("https://aws.amazon.com")
				.shorturl(Base62.encode())
				.build();
		testEntityManager.persist(shortening2);
		
		List<Shortening> shorteningList = shorteningRepository.findAll();
		assertThat(shorteningList, hasSize(2));
		assertThat(shorteningList, contains(shortening1, shortening2));
	}

	@Test
	public void shorteningSaveAndFindByUrlTest() throws Exception {
		Shortening shortening = Shortening.builder()
				.url("https://github.com")
				.shorturl(Base62.encode())
				.build();
		testEntityManager.persist(shortening);

		assertThat(shorteningRepository.findByUrl(shortening.getUrl()).getId(), is(notNullValue()));
	}

}
