package com.fintechtinkoff.homework.generateusers.human;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public final class HumanApi{
    private String gender;
    private Map<String,String> name;
    private Map<Object,Object> location;
    private Map<String,String> dob;

    private HumanApi(){}

    public Human toHuman()
            throws ParseException {
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar  = Calendar.getInstance();
        calendar.setTime(dataFormat.parse(dob.get("date")));
        return Human.builder()
                .name(name.get("first"))
                .patronymic("-")
                .region("-")
                .surname(name.get("last"))
                .gender(gender.toUpperCase())
                .dataBirth(calendar)
                .index(String.valueOf(location.get("postcode")))
                .country((String)location.get("state"))
                .city((String)location.get("city"))
                .street((String)location.get("street"))
                .house((int)(Math.random() * 100 + 1))
                .apartment((int)(Math.random() * 100 + 1))
                .build();
    }
}
