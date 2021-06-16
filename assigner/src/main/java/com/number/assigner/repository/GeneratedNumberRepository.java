package com.number.assigner.repository;

import com.number.assigner.entity.GeneratedNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GeneratedNumberRepository extends JpaRepository<GeneratedNumber, Long> {

    Optional<GeneratedNumber> findBysIdentifier(String sIdentifier);

}
