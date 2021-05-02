package com.morlock.caliban.controllersImpl;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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

import com.morlock.caliban.controllers.impl.MutantControllerImpl;
import com.morlock.caliban.entities.Mutant;
import com.morlock.caliban.services.impl.MutantServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = { MutantControllerImpl.class })
@Import(MutantControllerImplTest.Config.class)
public class MutantControllerImplTest {
	private static final Logger logger = LoggerFactory.getLogger(MutantControllerImplTest.class);

	@Autowired
	private MutantControllerImpl mutantController;

	public MutantControllerImplTest() {

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
	@DisplayName("Show all records (mutant and human)")
	public void test_getMutants() throws Exception {
		// When
		final ResultActions result;
		result = mockMvc.perform(MockMvcRequestBuilders.get("/show").contentType(MediaType.APPLICATION_JSON));
		// Then
		result.andExpect(status().isOk());
		result.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

	}

	@Test
	@DisplayName("Discriminate between humans and mutants")
	public void test_stats() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/stats")).andExpect(MockMvcResultMatchers.status().isOk());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@Test
	@DisplayName("Discriminate between humans and mutants")
	public void test_statsContain() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/stats")).andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().string(containsString("count_mutant_dna")));

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@Test
	@DisplayName("Validate the save response")
	public void test_addMutant400() throws Exception {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		Mutant mutant = new Mutant();
		mutant.setDna(dna);
		mutant.setConfirmed(true);

		String response = mockMvc.perform(post("/mutant/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn().getResponse().getContentAsString();

	}

}//
