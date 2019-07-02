<<<<<<< HEAD:src/main/java/com/fintechtinkoff/homework/generateusers/human/Human.java
package com.fintechtinkoff.homework.generateusers.human;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
=======
package com.fintechtinkoff.homework.generateusers;

import lombok.Getter;
import lombok.NoArgsConstructor;
>>>>>>> master:src/main/java/com/fintechtinkoff/homework/generateusers/Human.java

import javax.persistence.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Value
@ToString
@Entity
@Table(name="persons")
@SecondaryTable(name="address",pkJoinColumns =
        {@PrimaryKeyJoinColumn(name="id")})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Human {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",table = "persons")
    private int id = 0;

    @Column(name = "name",table = "persons")
    private String name;

    @Column(name = "surname",table = "persons")
    private String surname;

    @Column(name = "middlename",table = "persons")
    private String patronymic;

    @Column(name = "age",table = "persons")
    private Integer age;

    @Column(name = "inn",table = "persons")
    private String inn;

    @Column(name = "gender",table = "persons")
    private String gender;

    @Column(name = "birthday",table = "persons")
    private Calendar dataBirth;
<<<<<<< HEAD:src/main/java/com/fintechtinkoff/homework/generateusers/human/Human.java

    @Column(name = "postcode",table = "address")
    private String index;

    @Column(name = "country",table = "address")
=======
    private String index;
>>>>>>> master:src/main/java/com/fintechtinkoff/homework/generateusers/Human.java
    private String country;

    @Column(name = "region",table = "address")
    private String region;

    @Column(name = "city",table = "address")
    private String city;

    @Column(name = "street",table = "address")
    private String street;

    @Column(name = "house",table = "address")
    private Integer house;

    @Column(name = "flat",table = "address")
    private Integer apartment;

<<<<<<< HEAD:src/main/java/com/fintechtinkoff/homework/generateusers/human/Human.java
    @Builder
    public Human(String name, String surname, String patronymic, String gender, @NonNull Calendar dataBirth, String inn, String index,
                 String country, String region, String city, String street, Integer house, Integer apartment){
=======
    public Human(String name,String surname,String patronymic,Boolean gender,Calendar dataBirth,String index,
                 String country,String region,String city,String street,Integer house,Integer apartment){
>>>>>>> master:src/main/java/com/fintechtinkoff/homework/generateusers/Human.java
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
    private Human(){
        this.name = null;
        this.surname = null;
        this.patronymic = null;
        this.gender = null;
        this.inn = null;
        this.dataBirth = null;
        this.index = null;
        this.country = null;
        this.region = null;
        this.city = null;
        this.street = null;
        this.house = null;
        this.apartment = null;
        this.age = null;
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
        int prepareAge = timeRightNow.getWeekYear() - dataBirth.getWeekYear();
        if(dataBirth.get(Calendar.MONTH) > timeRightNow.get(Calendar.MONTH))
            prepareAge--;
        else if(timeRightNow.get(Calendar.MONTH) == dataBirth.get(Calendar.MONTH)) {
            if (dataBirth.get(Calendar.DAY_OF_MONTH) > timeRightNow.get(Calendar.DAY_OF_MONTH))
                prepareAge--;
        }
        return prepareAge;
    }
}