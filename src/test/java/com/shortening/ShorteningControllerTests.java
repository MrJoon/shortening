package com.shortening;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.shortening.controller.ShorteningController;
import com.shortening.service.ShorteningService;
import com.shortening.utils.Base62;

@RunWith(SpringRunner.class)
@WebMvcTest(ShorteningController.class)
public class ShorteningControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ShorteningService shorteningService;

	@Test
	public void mainTest() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("index"))
				.andExpect(content().string(containsString("URL")))
				.andDo(print());
	}

	@Test
	public void shorturlTest() throws Exception {
		mockMvc.perform(get("/{shorturl}", Base62.encode()))
				.andExpect(status().isOk())
				.andExpect(view().name("redirect"))
				.andExpect(content().string(containsString("URL")))
				.andDo(print());
	}

}
