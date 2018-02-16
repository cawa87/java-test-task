package com.accounts;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpringBootRestSystemTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user")
    public void testAccountSystem() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/accounts").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        assertEquals(result.getResponse().getStatus(), 200);

    }

    @Test
    @WithMockUser(username = "user")
    public void testFilesSystem() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/files").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        assertEquals(result.getResponse().getStatus(), 200);

    }

}
