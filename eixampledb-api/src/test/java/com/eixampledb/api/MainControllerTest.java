package com.eixampledb.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    public void shouldIncrementInt() throws Exception{
        mockMvc.perform(post("/actualkey4").content("12").header("type","NUM"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/actualkey4"))
                .andExpect(status().isOk())
                .andExpect(content().string("12"));

        mockMvc.perform(put("/actualkey4").header("op","INCR"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/actualkey4"))
                .andExpect(status().isOk())
                .andExpect(content().string("13"));
    }

    @Test
    public void shouldDecrementInt() throws Exception{
        mockMvc.perform(post("/actualkey5").content("12").header("type","NUM"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/actualkey5"))
                .andExpect(status().isOk())
                .andExpect(content().string("12"));

        mockMvc.perform(put("/actualkey5").header("op","DECR"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/actualkey5"))
                .andExpect(status().isOk())
                .andExpect(content().string("11"));
    }

    @Test
    public void shouldIncrementFloat() throws Exception{
        mockMvc.perform(post("/actualkey6").content("12.25").header("type","NUM"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/actualkey6"))
                .andExpect(status().isOk())
                .andExpect(content().string("12.25"));

        mockMvc.perform(put("/actualkey6").header("op","INCR"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/actualkey6"))
                .andExpect(status().isOk())
                .andExpect(content().string("13.25"));
    }

    @Test
    public void shouldDecrementFloat() throws Exception{
        mockMvc.perform(post("/actualkey7").content("12.25").header("type","NUM"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/actualkey7"))
                .andExpect(status().isOk())
                .andExpect(content().string("12.25"));

        mockMvc.perform(put("/actualkey7").header("op","DECR"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/actualkey7"))
                .andExpect(status().isOk())
                .andExpect(content().string("11.25"));
    }

    @Test
    public void shouldDoNothing() throws Exception {
        mockMvc.perform(post("/actualkey8").content("I'm a word").header("type", "STR"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/actualkey8"))
                .andExpect(status().isOk())
                .andExpect(content().string("I'm a word"));

        mockMvc.perform(put("/actualkey8").header("op", "INCR"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/actualkey8"))
                .andExpect(status().isOk())
                .andExpect(content().string("I'm a word"));
    }

    @Test
    public void shouldFoundAndOverwriteMatchesPrep() throws Exception {

        mockMvc.perform(post("/actualkey09").content("pretestvalue1"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/actualkey09"))
                .andExpect(status().isOk())
                .andExpect(content().string("pretestvalue1"));

        mockMvc.perform(post("/actualkey010").content("pretestvalue2"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/actualkey010"))
                .andExpect(status().isOk())
                .andExpect(content().string("pretestvalue2"));

        mockMvc.perform(post("/actualkey0").content("newValue").header("search","STARTS"))
                .andExpect(status().isOk());


        mockMvc.perform(get("/actualkey09"))
                .andExpect(status().isOk())
                .andExpect(content().string("newValue"));
        mockMvc.perform(get("/actualkey010"))
                .andExpect(status().isOk())
                .andExpect(content().string("newValue"));
    }

    @Test
    public void shouldFoundAndOverwriteMatchesRegEx() throws Exception {
        mockMvc.perform(post("/actualregexkey11").content("pretestvalue1"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/actualregexkey11"))
                .andExpect(status().isOk())
                .andExpect(content().string("pretestvalue1"));

        mockMvc.perform(post("/actualregexkey12").content("pretestvalue2"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/actualregexkey12"))
                .andExpect(status().isOk())
                .andExpect(content().string("pretestvalue2"));

        mockMvc.perform(post("/.*regex.*").content("newValue").header("search","REGEX"))
                .andExpect(status().isOk());


        mockMvc.perform(get("/actualregexkey11"))
                .andExpect(status().isOk())
                .andExpect(content().string("newValue"));
        mockMvc.perform(get("/actualregexkey12"))
                .andExpect(status().isOk())
                .andExpect(content().string("newValue"));
    }

}
