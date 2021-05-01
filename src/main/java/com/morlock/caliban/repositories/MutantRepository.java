package com.morlock.caliban.repositories;

import com.morlock.caliban.entities.Mutant;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Qualifier("MutantRepository")
public interface MutantRepository extends JpaRepository<Mutant, Long> {
	void save(Optional<Mutant> mutantNew);

}
