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
    private String sIdentifier;

    private long value;

    public GeneratedNumber() {
        //empty for auto init
    }

    public GeneratedNumber(String sIdentifier, long value) {
        this.sIdentifier = sIdentifier;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getsIdentifier() {
        return sIdentifier;
    }

    public void setsIdentifier(String sIdentifier) {
        this.sIdentifier = sIdentifier;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

}
