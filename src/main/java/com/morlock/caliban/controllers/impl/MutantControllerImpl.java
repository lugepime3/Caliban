package com.morlock.caliban.controllers.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morlock.caliban.controllers.MutantController;
import com.morlock.caliban.entities.Mutant;
import com.morlock.caliban.services.MutantService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class MutantControllerImpl implements MutantController {
    private static final Logger logger = LoggerFactory.getLogger(com.morlock.caliban.controllers.impl.MutantControllerImpl.class);
    private static final String UNEXPECTED_ERROR = "An unexpected error occurred: ";

    @Autowired
    @Qualifier("MutantService")
    MutantService mutantService;

    @Autowired(required=false)
    ObjectMapper mapper;

    @ApiOperation(value = "Add dna Mutant", notes = " <b>Add dna Mutants</b>.")
    @PostMapping("/mutant/")
    public ResponseEntity<Mutant> addMutant(@RequestBody Mutant mutant) {
        try {
            mutant = mutantService.saveMutant(mutant);
            if (mutant.isConfirmed()) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

        } catch (Exception e) {
            logger.error(UNEXPECTED_ERROR.concat(e.getMessage()));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "/stats", notes = " <b>Stats</b>.")
    @GetMapping("/stats")
    @Override
    public @ResponseBody
    String stats() throws JsonProcessingException {
        Map<String, Object> map;
        map = mutantService.stats();
        return mapper.writeValueAsString(map);
    }

    @ApiOperation(value = "list Mutants", notes = " <b>List All Mutants</b>.")
    @GetMapping(value = "/show")
    @Override
    public List<Mutant> getMutants() {
        return mutantService.findAllMutants();
    }

}

