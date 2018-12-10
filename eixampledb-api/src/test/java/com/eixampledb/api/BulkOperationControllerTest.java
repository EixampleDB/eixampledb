package com.eixampledb.api;

import com.eixampledb.core.api.request.BulkRequest;
import com.eixampledb.core.enums.TipoOperacion;
import com.eixampledb.core.model.OperationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BulkOperationControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    public void sendBulkOperationPetitionOK() throws Exception {
        List<OperationDTO> operationDTOList = Arrays.asList(
                OperationDTO.builder()
                        .key("KEY1")
                        .tipo(TipoOperacion.SET)
                        .parameters("VALUE!!!")
                        .build()
        );
        BulkRequest bulkRequest = BulkRequest.builder().operatioons(operationDTOList).build();

        MvcResult mvcResult = mockMvc.perform(post("/bulk/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bulkRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String idOperation = mvcResult.getResponse().getContentAsString();

        MvcResult mvcGetResult = mockMvc.perform(get("/bulk/"+idOperation))
                .andExpect(status().isOk())
                .andReturn();

        String informe = mvcGetResult.getResponse().getContentAsString();
        assertThat(informe,equalTo("SET, KEY:KEY1, VALUE: OK, NEW\n"));
        return;
    }

    @Test
    public void checkIdNotFound() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/bulk/111"))
                .andExpect(status().isNotFound()).andReturn();

    }
}
