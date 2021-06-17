package com.number.assigner.service;

import com.number.assigner.mapper.ResponseMapper;
import com.number.assigner.dto.GeneratedNumberResponse;
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
    public GeneratedNumberResponse generateNumber(String identifier) throws GeneratorFailException {
        Optional<GeneratedNumber> optionalGeneratedNumber =
                generatedNumberRepository.findByIdentifier(identifier);

        if (optionalGeneratedNumber.isPresent()) {
            return ResponseMapper.toResponse(
                    optionalGeneratedNumber.get().getIdentifier(),
                    optionalGeneratedNumber.get().getValue()
            );
        }

        GeneratedNumber generatedNumber = new GeneratedNumber(
                identifier,
                getGeneratedNumber()
        );

        generatedNumberRepository.save(generatedNumber);

        return new GeneratedNumberResponse(
                generatedNumber.getIdentifier(),
                generatedNumber.getValue()
        );
    }

    private long getGeneratedNumber() throws GeneratorFailException {
        if (failedGenerator()) {
            throw new GeneratorFailException();
        }

        return getRandomNumber();
    }

    private long getRandomNumber() {
        return (long) (Math.random() * 1000);
    }

    private boolean failedGenerator() {
        return (int) (Math.random() * 100) < failureProbability;
    }

}
