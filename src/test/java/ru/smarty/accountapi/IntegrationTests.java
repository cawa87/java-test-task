package ru.smarty.accountapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests are here as there's just too little logic to be unit tested without just repeating controllers code.
 * And integration test here provides value of testing it all together.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class IntegrationTests {
    private final RequestPostProcessor auth = httpBasic("admin", "admin");
    @Autowired
    private MockMvc mvc;

    @Test
    public void testBasicList() throws Exception {
        mvc.perform(get("/accounts").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        mvc.perform(get("/accounts").with(auth)).andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testCreate() throws Exception {
        mvc.perform(post("/accounts").accept(MediaType.APPLICATION_JSON).with(auth).contentType(MediaType.APPLICATION_JSON)
                .content(json("" +
                        "{'login': 'new user', " +
                        "'accountDetails': 'Hacky details'," +
                        "'password': '123123'," +
                        "'personalInfo': 'Pretty good new user'}")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.login", is("new user")))
                .andExpect(jsonPath("$.personalInfo", startsWith("Pretty good")))
                .andExpect(jsonPath("$.accountDetails", not("Hacky details")));

        mvc.perform(get("/accounts").with(auth)).andExpect(jsonPath("$", hasSize(2)));

        // Checkout uniqueness.
        mvc.perform(post("/accounts").with(auth).contentType(MediaType.APPLICATION_JSON)
                .content(json("" +
                        "{'login': 'new user', " +
                        "'accountDetails': 'Hacky details'," +
                        "'password': '123123'," +
                        "'personalInfo': 'Pretty good new user'}")))
                .andExpect(status().isBadRequest());
    }

    private static String json(String singleQuotedJson) {
        return singleQuotedJson.replace('\'', '"');
    }
}
