package com.number.assigner.service;

import com.number.assigner.dto.Response;
import com.number.assigner.entity.GeneratedNumber;
import com.number.assigner.exception.GeneratorFailException;
import com.number.assigner.repository.GeneratedNumberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NumberAssignServiceImpl implements NumberAssignService {

    private final GeneratedNumberRepository generatedNumberRepository;

    private final int failureProbability;

    public NumberAssignServiceImpl(
            GeneratedNumberRepository generatedNumberRepository,
            @Value("${number.generator.failure.probability:30}") int failureProbability
    ) {
        this.generatedNumberRepository = generatedNumberRepository;
        this.failureProbability = failureProbability;
    }

    @Override
    public Response getOrGenerateNumber(String sIdentificator) throws GeneratorFailException {
        Optional<GeneratedNumber> optionalGeneratedNumber =
                generatedNumberRepository.findBysIdentificator(sIdentificator);

        GeneratedNumber generatedNumber = optionalGeneratedNumber.isPresent() ?
                optionalGeneratedNumber.get() : generateAndSaveNumber(sIdentificator);

        return new Response(
                generatedNumber.getsIdentificator(),
                generatedNumber.getValue()
        );
    }

    private GeneratedNumber generateAndSaveNumber(String sIdentificator) throws GeneratorFailException {
        if (failedGenerator()) {
            throw new GeneratorFailException();
        }

        GeneratedNumber generatedNumber = new GeneratedNumber(
                sIdentificator,
                getRandomNumber()
        );

        return generatedNumberRepository.save(generatedNumber);
    }

    private long getRandomNumber() {
        return (long) (Math.random() * 1000);
    }

    private boolean failedGenerator() {
        return (int) (Math.random() * 100) < failureProbability;
    }

}
