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
final class HumanFromSite implements AutoCloseable{
    private String gender;
    private Map<String,String> name;
    private Map<Object,Object> location;
    private Map<String,String> dob;

    public Human toHuman()throws ParseException {
        SimpleDateFormat dataFormat = new SimpleDateFormat("YYYY-MM-dd");
        Calendar calendar  = Calendar.getInstance();
        calendar.setTime(dataFormat.parse(dob.get("date")));
        return Human.builder()
                .name(name.get("first"))
                .patronymic("-")
                .surname(name.get("last"))
                .gender(gender.equals("female") ? false : true)
                .dataBirth(calendar)
                .index(String.valueOf(location.get("postcode")))
                .country((String)location.get("state"))
                .city((String)location.get("city"))
                .street((String)location.get("street"))
                .house((int)(Math.random() * 100 + 1))
                .apartment((int)(Math.random() * 100 + 1))
                .build();
    }
    public void close(){
        gender = null;
        name = null;
        location = null;
        dob = null;
    }
}
