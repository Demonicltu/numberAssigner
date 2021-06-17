package com.number.assigner.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.number.assigner.dto.GeneratedNumberResponse;
import com.number.assigner.entity.GeneratedNumber;
import com.number.assigner.error.ApplicationError;
import com.number.assigner.error.RequestError;
import com.number.assigner.repository.GeneratedNumberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NumberAssignControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GeneratedNumberRepository generatedNumberRepository;

    @Test
    void testAssignNumber() throws Exception {
        String parameter = "S1";
        long value = 15;

        when(generatedNumberRepository.findByIdentifier(any()))
                .thenAnswer(invocation -> Optional.of(
                        new GeneratedNumber(invocation.getArgument(0), value)
                ));

        MvcResult mvcResult = mockMvc
                .perform(
                        RestDocumentationRequestBuilders.post("/v1/number")
                                .with(httpBasic("user", "pass"))
                                .param("identifier", parameter)
                )
                .andExpect(status().isOk())
                .andReturn();

        verify(generatedNumberRepository, times(1))
                .findByIdentifier(any());

        verify(generatedNumberRepository, times(0))
                .save(any());

        String responseString = mvcResult.getResponse().getContentAsString();
        GeneratedNumberResponse generatedNumberResponse = mapper.readValue(responseString, GeneratedNumberResponse.class);

        Assertions.assertNotNull(generatedNumberResponse);
        Assertions.assertEquals(parameter, generatedNumberResponse.getIdentifier());
        Assertions.assertEquals(value, generatedNumberResponse.getValue());
    }

    @Test
    void testAssignNumberGeneratorFailException() throws Exception {
        when(generatedNumberRepository.findByIdentifier(any()))
                .thenAnswer(invocation -> Optional.empty());

        MvcResult mvcResult = mockMvc
                .perform(
                        RestDocumentationRequestBuilders.post("/v1/number")
                                .with(httpBasic("user", "pass"))
                                .param("identifier", "S1")
                )
                .andExpect(status().isInternalServerError())
                .andReturn();

        verify(generatedNumberRepository, times(1))
                .findByIdentifier(any());

        verify(generatedNumberRepository, times(0))
                .save(any());

        String response = mvcResult.getResponse().getContentAsString();

        RequestError error = mapper.readValue(response, RequestError.class);

        Assertions.assertEquals(ApplicationError.NUMBER_ASSIGN_ERROR.getErrorName(), error.getErrorName());
        Assertions.assertEquals(ApplicationError.NUMBER_ASSIGN_ERROR.getHttpStatus().toString(), error.getHttpStatus());
    }

}
