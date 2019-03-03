package com.fintechtinkoff.homework.generateusers;

import java.util.Calendar;

public class HumanBuilder {
    private String newName;
    private String newSurname;
    private String newPatronymic;
    private Boolean newGender;
    private Calendar newDataBirth;
    private String newIndex;
    private String newCountry;
    private String newRegion;
    private String newCity;
    private String newStreet;
    private Integer newHouse;
    private Integer newApartment;


    public HumanBuilder setName(String name) {
        this.newName = name;
        return this;
    }

    public HumanBuilder setSurname(String surname) {
        this.newSurname = surname;
        return this;
    }

    public HumanBuilder setPatronymic(String patronymic) {
        this.newPatronymic = patronymic;
        return this;
    }

    public HumanBuilder setGender(Boolean gender) {
        this.newGender = gender;
        return this;
    }

    public HumanBuilder setDataBirth(Calendar dataBirth) {
        this.newDataBirth = dataBirth;
        return this;
    }

    public HumanBuilder setIndex(String index) {
        this.newIndex = index;
        return this;
    }

    public HumanBuilder setCountry(String country) {
        this.newCountry = country;
        return this;
    }

    public HumanBuilder setRegion(String region) {
        this.newRegion = region;
        return this;
    }

    public HumanBuilder setCity(String city) {
        this.newCity = city;
        return this;
    }

    public HumanBuilder setStreet(String street) {
        this.newStreet = street;
        return this;
    }

    public HumanBuilder setHouse(Integer house) {
        this.newHouse = house;
        return this;
    }

    public HumanBuilder setApartment(Integer apartment) {
        this.newApartment = apartment;
        return this;
    }
    public Human build(){
        return new Human(newName,newSurname,newPatronymic,newGender,newDataBirth,newIndex,newCountry,newRegion,newCity,newStreet,newHouse,newApartment);
    }
}
