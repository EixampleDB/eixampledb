package com.eixampledb.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldNotFound() throws Exception {
        mockMvc.perform(get("/notfoundkey")).andExpect(status().isNotFound());
    }

    @Test
    public void shouldFound() throws Exception {
        mockMvc.perform(post("/actualkey").content("testvalue"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/actualkey"))
                .andExpect(status().isOk())
                .andExpect(content().string("testvalue"));
    }

    @Test
    public void shouldFoundAndOverwrite() throws Exception {
        mockMvc.perform(post("/actualkey2").content("testvalue"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/actualkey2"))
                .andExpect(status().isOk())
                .andExpect(content().string("testvalue"));

        mockMvc.perform(post("/actualkey2").content("newValue"))
                .andExpect(status().isOk());


        mockMvc.perform(get("/actualkey2"))
                .andExpect(status().isOk())
                .andExpect(content().string("newValue"));
    }

    @Test
    public void shouldFoundAndDelete() throws Exception {
        mockMvc.perform(post("/actualkey3").content("newValue"))
                .andExpect(status().isOk());


        mockMvc.perform(get("/actualkey3"))
                .andExpect(status().isOk())
                .andExpect(content().string("newValue"));

        mockMvc.perform(delete("/actualkey3"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/actualkey3")).andExpect(status().isNotFound());

    }
}
