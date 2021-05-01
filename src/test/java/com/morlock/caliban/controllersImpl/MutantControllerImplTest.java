package com.morlock.caliban.controllersImpl;

import com.morlock.caliban.controllers.impl.MutantControllerImpl;
import com.morlock.caliban.entities.Mutant;
import com.morlock.caliban.services.impl.MutantServiceImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MutantControllerImpl.class })
@Import(MutantControllerImplTest.Config.class)
public class MutantControllerImplTest {
	private static final Logger logger = LoggerFactory.getLogger(MutantControllerImplTest.class);

	@Autowired
	private final MutantControllerImpl mutantController;

	public MutantControllerImplTest(MutantControllerImpl mutantController) {
		this.mutantController = mutantController;
	}

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(mutantController).build();

	}

	@TestConfiguration
	protected static class Config {

		@Bean
		public MutantServiceImpl mutantService() {
			return Mockito.mock(MutantServiceImpl.class);
		}

	}

	@Test
	public void test_getMutants() throws Exception {

		// When
		final ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/show"));
		// Then
	 result.andExpect(status().isOk());
	}

	@Test
	public void test_stats() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/stats")).andExpect(MockMvcResultMatchers.status().isOk());
		} catch (Exception e) {
			logger.error(e.getMessage());
			Assert.fail();
		}
	}

	@Test
	public void test_statsContain() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/stats")).andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().string(containsString("count_mutant_dna")));

		} catch (Exception e) {
			logger.error(e.getMessage());
			Assert.fail();
		}
	}

	@Test
	public void test_addMutant400() throws Exception {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		Mutant mutant = new Mutant();
		mutant.setDna(dna);
		mutant.setConfirmed(true);

		String response = mockMvc.perform(post("/mutant/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn().getResponse().getContentAsString();
		System.out.println("Esta fue la respuesta " + response);
		System.out.println("Esta fue la respuesta " + response);
		System.out.println("Esta fue la respuesta " + response);
		System.out.println("Esta fue la respuesta " + response);

		System.out.println("Esta fue la respuesta " + response);
		System.out.println("Esta fue la respuesta " + response);
	}

}//
