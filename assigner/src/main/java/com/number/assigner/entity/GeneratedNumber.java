package com.number.assigner.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
public class GeneratedNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "s")
    private String sIdentificator;

    private long value;

    public GeneratedNumber() {
        //empty for auto init
    }

    public GeneratedNumber(String sIdentificator, long value) {
        this.sIdentificator = sIdentificator;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getsIdentificator() {
        return sIdentificator;
    }

    public void setsIdentificator(String sIdentificator) {
        this.sIdentificator = sIdentificator;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

}
