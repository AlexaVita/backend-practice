package com.practice.backend.model;

public class Example {
    private Integer id;
    private Integer number;

    public Example() {
    }

    public Example(Integer id, Integer number) {
        this.id = id;
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
