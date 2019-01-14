package com.eixampledb.api;

import com.eixampledb.api.monitoring.PerformanceMonitoringService;
import org.junit.Before;
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
public class MonitoringControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PerformanceMonitoringService pm;

    @Before
    public void init() {
        pm.resetCounters();
    }

    @Test
    public void getUsedMemory() throws Exception {
        mockMvc.perform(get("/monitor/used_memory"))
                .andExpect(status().isOk());
    }

    @Test
    public void getUsedCPU() throws Exception {
        mockMvc.perform(get("/monitor/used_cpu"))
                .andExpect(status().isOk());
    }

    @Test
    public void getFreeDiskSpace() throws Exception {
        mockMvc.perform(get("/monitor/disk_free_space"))
                .andExpect(status().isOk());
    }

    @Test
    public void getLatency() throws Exception {
        mockMvc.perform(get("/monitor/latency"))
                .andExpect(status().isOk());
    }

    @Test
    public void getOPM() throws Exception {
        mockMvc.perform(get("/monitor/opm"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));

        mockMvc.perform(post("/actualkey").content("testvalue"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/monitor/opm"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void getHitRate() throws Exception {
        mockMvc.perform(get("/monitor/hit_rate"))
                .andExpect(status().isOk())
                .andExpect(content().string("0.0"));

        mockMvc.perform(post("/actualkey").content("testvalue"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/actualkey"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/monitor/hit_rate"))
                .andExpect(status().isOk())
                .andExpect(content().string("1.0"));

        mockMvc.perform(get("/actualkey2"));

        mockMvc.perform(get("/monitor/hit_rate"))
                .andExpect(status().isOk())
                .andExpect(content().string("0.5"));
    }
}