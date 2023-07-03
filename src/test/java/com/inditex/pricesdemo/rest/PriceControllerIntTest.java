package com.inditex.pricesdemo.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Sql
@Transactional
@SpringBootTest
class PriceControllerIntTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getByParams1() throws Exception {
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/prices")
                .param("date", "2020-06-14T10:00:00")
                .param("productId", "35455")
                .param("brandId", "1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.priceList", is(1)));
    }

    @Test
    void getByParams2() throws Exception {
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/prices")
                .param("date", "2020-06-14T16:00:00")
                .param("productId", "35455")
                .param("brandId", "1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.priceList", is(2)));
    }

    @Test
    void getByParams3() throws Exception {
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/prices")
                .param("date", "2020-06-14T21:00:00")
                .param("productId", "35455")
                .param("brandId", "1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.priceList", is(1)));
    }

    @Test
    void getByParams4() throws Exception {
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/prices")
                .param("date", "2020-06-15T10:00:00")
                .param("productId", "35455")
                .param("brandId", "1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.priceList", is(3)));
    }

    @Test
    void getByParams5() throws Exception {
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/prices")
                .param("date", "2020-06-16T21:00:00")
                .param("productId", "35455")
                .param("brandId", "1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.priceList", is(4)));
    }
}
