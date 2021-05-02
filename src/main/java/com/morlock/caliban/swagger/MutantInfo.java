package com.morlock.caliban.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "DNA details to analyze ")
public class MutantInfo {
    @ApiModelProperty(notes="Values with DNA sequence",example ="{\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"}" )
    private String[] dna;

    public MutantInfo() {
    }

    public String[] getDna() {
        return dna;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }
}
