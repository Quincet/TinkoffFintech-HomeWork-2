package com.fintechtinkoff.homework.generateusers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HumanFromSite implements AutoCloseable{
    private String gender;
    private Map<String,String> name;
    private Map<Object,Object> location;
    private Map<String,String> dob;

    public Human toHuman()throws ParseException {
        SimpleDateFormat dataFormat = new SimpleDateFormat("YYYY-MM-dd");
        Calendar calendar  = Calendar.getInstance();
        calendar.setTime(dataFormat.parse(dob.get("date")));
        return Human.createBuilder()
                .setName(name.get("first"))
                .setPatronymic("-")
                .setSurname(name.get("last"))
                .setGender(gender == "female" ? false : true)
                .setDataBirth(calendar)
                .setIndex(String.valueOf(location.get("postcode")))
                .setCountry((String)location.get("state"))
                .setCity((String)location.get("city"))
                .setStreet((String)location.get("street"))
                .setHouse((int)(Math.random() * 100 + 1))
                .setApartment((int)(Math.random() * 100 + 1))
                .build();
    }
    public void close(){
        gender = null;
        name = null;
        location = null;
        dob = null;
    }
}
