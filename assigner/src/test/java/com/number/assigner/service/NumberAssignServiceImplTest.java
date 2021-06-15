package com.number.assigner.service;


import com.number.assigner.dto.Response;
import com.number.assigner.entity.GeneratedNumber;
import com.number.assigner.repository.GeneratedNumberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NumberAssignServiceImplTest {

    private NumberAssignServiceImpl numberAssignService;

    @Mock
    private GeneratedNumberRepository generatedNumberRepository;

    @BeforeEach
    public void setup() {
        numberAssignService = new NumberAssignServiceImpl(
                generatedNumberRepository,
                0
        );
    }

    @Test
    void testGetOrGenerateNumber() throws Exception {
        String parameter = "S1";
        long value = 15;

        when(generatedNumberRepository.findBysIdentificator(any()))
                .thenAnswer(invocation -> Optional.of(
                        new GeneratedNumber(invocation.getArgument(0), value)
                ));

        Response response = numberAssignService.getOrGenerateNumber(parameter);

        verify(generatedNumberRepository, times(1))
                .findBysIdentificator(any());

        verify(generatedNumberRepository, times(0))
                .save(any());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(parameter, response.getsIdentificator());
        Assertions.assertEquals(value, response.getValue());
    }

    @Test
    void testGetOrGenerateNumberNotFound() throws Exception {
        String parameter = "S1";

        when(generatedNumberRepository.findBysIdentificator(any()))
                .thenAnswer(invocation -> Optional.empty());

        when(generatedNumberRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Response response = numberAssignService.getOrGenerateNumber(parameter);

        verify(generatedNumberRepository, times(1))
                .findBysIdentificator(any());

        verify(generatedNumberRepository, times(1))
                .save(any());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(parameter, response.getsIdentificator());
        Assertions.assertTrue(response.getValue() > 0);
    }

}
