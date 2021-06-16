package com.number.assigner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.number.assigner.dto.Response;
import com.number.assigner.error.ApplicationError;
import com.number.assigner.error.RequestError;
import com.number.assigner.exception.GeneratorFailException;
import com.number.assigner.service.NumberAssignService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.removeHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NumberAssignController.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NumberAssignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private NumberAssignService numberAssignService;

    @Test
    void testAssignNumber() throws Exception {
        when(numberAssignService.getOrGenerateNumber(any()))
                .thenAnswer(invocation -> new Response(invocation.getArgument(0), 1));

        MvcResult mvcResult = mockMvc
                .perform(
                        RestDocumentationRequestBuilders.post("/")
                                .with(httpBasic("user", "pass"))
                                .param("s", "S1")
                )
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "assignNumber",
                                preprocessRequest(removeHeaders("Content-Length", "Host"), prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("Basic authentication header")
                                ),
                                requestParameters(
                                        parameterWithName("s").description("Number pair identificator")
                                ),
                                responseFields(
                                        fieldWithPath("s").type(JsonFieldType.STRING).description("Number identificator"),
                                        fieldWithPath("value").type(JsonFieldType.NUMBER).description("Number value")
                                )
                        )
                )
                .andDo(print())
                .andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();
        Response response = mapper.readValue(responseString, Response.class);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.getsIdentifier().isEmpty());
        Assertions.assertTrue(response.getValue() > 0);
    }

    @Test
    void testAssignNumberGeneratorFailException() throws Exception {
        when(numberAssignService.getOrGenerateNumber(any()))
                .thenThrow(new GeneratorFailException());

        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/")
                                .with(httpBasic("user", "pass"))
                                .param("s", "S1")
                )
                .andExpect(status().isInternalServerError())
                .andReturn();


        String response = mvcResult.getResponse().getContentAsString();

        RequestError error = mapper.readValue(response, RequestError.class);

        Assertions.assertEquals(ApplicationError.NUMBER_ASSIGN_ERROR.getErrorName(), error.getErrorName());
        Assertions.assertEquals(ApplicationError.NUMBER_ASSIGN_ERROR.getHttpStatus().toString(), error.getHttpStatus());
    }

}
