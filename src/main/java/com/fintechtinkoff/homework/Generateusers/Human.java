package com.fintechtinkoff.homework.Generateusers;

import lombok.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Value
@ToString
public class Human {
    private String name;
    private String surname;
    private String patronymic;
    private Integer age;
    private String inn;
    private String gender;
    private Calendar dataBirth;
    private String index;
    private String country;
    private String region;
    private String city;
    private String street;
    private Integer house;
    private Integer apartment;

    @Builder
    public Human(String name, String surname, String patronymic, String gender, @NonNull Calendar dataBirth,String inn, String index,
                 String country, String region, String city, String street, Integer house, Integer apartment){
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.gender = gender;
        this.inn = inn == null ? generateINN() : inn;
        this.dataBirth = dataBirth;
        this.index = index;
        this.country = country;
        this.region = region;
        this.city = city;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
        this.age = getAge(dataBirth);
    }

    private String generateINN(){
        String region = "77";
        String inspection = addZero(String.valueOf(Math.floor((Math.random() * 99) + 1)).replace(".0",""),2);
        String numba = addZero(String.valueOf(Math.floor((Math.random() * 999999) + 1)).replace(".0",""),6);
        List<Integer> rezult = Arrays.stream((region + inspection + numba).split("")).map(Integer::valueOf).collect(Collectors.toList());
        String kontr = String.valueOf(((7*rezult.get(0) + 2*rezult.get(1) + 4*rezult.get(2) + 10*rezult.get(3) + 3*rezult.get(4) + 5*rezult.get(5) + 9*rezult.get(6) + 4*rezult.get(7) + 6*rezult.get(8) + 8*rezult.get(9)) % 11) % 10);
        kontr = kontr.equals("10") ? "0" : kontr;
        rezult.add(Integer.valueOf(kontr));
        kontr = String.valueOf(((3*rezult.get(0) +  7*rezult.get(1) + 2*rezult.get(2) + 4*rezult.get(3) + 10*rezult.get(4) + 3*rezult.get(5) + 5*rezult.get(6) +  9*rezult.get(7) + 4*rezult.get(8) + 6*rezult.get(9) +  8*rezult.get(10)) % 11) % 10);
        kontr = kontr.equals("10") ? "0" : kontr;
        rezult.add(Integer.valueOf(kontr));
        return String.join("",rezult.stream().map(String::valueOf).collect(Collectors.joining("")));
    }
    private String addZero(String str,Integer lng){
        Integer lengt = str.length();
        if (lengt < lng)
            for (int i = 0;i<(lng-lengt);i++)
                str = "0" + str;
        return str;
    }
    private int getAge(Calendar dataBirth){
        Calendar timeRightNow = Calendar.getInstance();
        int Age = timeRightNow.getWeekYear() - dataBirth.getWeekYear();
        if(dataBirth.get(Calendar.MONTH) > timeRightNow.get(Calendar.MONTH))
            Age--;
        else if(timeRightNow.get(Calendar.MONTH) == dataBirth.get(Calendar.MONTH)) {
            if (dataBirth.get(Calendar.DAY_OF_MONTH) > timeRightNow.get(Calendar.DAY_OF_MONTH))
                Age--;
        }
        return Age;
    }
}
