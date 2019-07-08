package com.fintechtinkoff.homework.generateusers.human;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Value
@ToString
@Entity
@Table(name = "persons")
@SecondaryTable(name = "address", pkJoinColumns =
        {@PrimaryKeyJoinColumn(name = "id")})
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Human {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", table = "persons")
    private int id = 0;

    @Column(name = "name", table = "persons")
    private String name;

    @Column(name = "surname", table = "persons")
    private String surname;

    @Column(name = "middlename", table = "persons")
    private String patronymic;

    @Column(name = "age", table = "persons")
    private Integer age;

    @Column(name = "inn", table = "persons")
    private String inn;

    @Column(name = "gender", table = "persons")
    private String gender;

    @Column(name = "birthday", table = "persons")
    private Calendar dataBirth;

    @Column(name = "postcode", table = "address")
    private String index;

    @Column(name = "country", table = "address")
    private String country;

    @Column(name = "region", table = "address")
    private String region;

    @Column(name = "city", table = "address")
    private String city;

    @Column(name = "street", table = "address")
    private String street;

    @Column(name = "house", table = "address")
    private Integer house;

    @Column(name = "flat", table = "address")
    private Integer apartment;

    @Builder
    public Human(String name, String surname, String patronymic, String gender,
                 @NonNull Calendar dataBirth, String inn, String index,
                 String country, String region, String city, String street,
                 Integer house, Integer apartment) {
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
    private Human() {
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

    public List<String> getFieldsValueAsList() {
        var dataFormat =
                new SimpleDateFormat("dd-MM-yyyy", Locale.forLanguageTag("ru"));
        var classProp = new ArrayList<String>();
        classProp.add(name);
        classProp.add(surname);
        classProp.add(patronymic);
        classProp.add(age.toString());
        classProp.add(gender);
        classProp.add(dataFormat.format(dataBirth.getTime()));
        classProp.add(inn);
        classProp.add(index);
        classProp.add(country);
        classProp.add(region);
        classProp.add(city);
        classProp.add(street);
        classProp.add(house.toString());
        classProp.add(apartment.toString());
        return classProp;
    }
    private String generateINN() {
        final Random rnd = new Random();
        String region = "77";
        Integer inspection = rnd.nextInt(89) + 10;
        Integer numba = rnd.nextInt(899999) + 100000;
        List<Integer> rezult = Arrays.stream((region
                + inspection.toString()
                + numba.toString())
                .split(""))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        int controlNumber = ((7 * rezult.get(0) + 2 * rezult.get(1)
                + 4 * rezult.get(2) + 10 * rezult.get(3)
                + 3 * rezult.get(4) + 5 * rezult.get(5)
                + 9 * rezult.get(6) + 4 * rezult.get(7)
                + 6 * rezult.get(8) + 8 * rezult.get(9))
                % 11) % 10;
        controlNumber = controlNumber == 10
                ? 0 : controlNumber;
        rezult.add(controlNumber);
        controlNumber = ((3 * rezult.get(0) +  7 * rezult.get(1)
                + 2 * rezult.get(2) + 4 * rezult.get(3)
                + 10 * rezult.get(4) + 3 * rezult.get(5)
                + 5 * rezult.get(6) +  9 * rezult.get(7)
                + 4 * rezult.get(8) + 6 * rezult.get(9)
                + 8 * rezult.get(10))
                % 11) % 10;
        controlNumber = controlNumber == 10
                ? 0 : controlNumber;
        rezult.add(controlNumber);
        var stringBuilder = new StringBuilder();
        rezult.forEach(x -> stringBuilder.append(x.toString()));
        return stringBuilder.toString();
    }

    private int getAge(final Calendar dataBirth) {
        Calendar timeRightNow = Calendar.getInstance();
        int prepareAge = timeRightNow.getWeekYear() - dataBirth.getWeekYear();
        if (dataBirth.get(Calendar.MONTH)
                > timeRightNow.get(Calendar.MONTH)) {
            prepareAge--;
        } else if (timeRightNow.get(Calendar.MONTH)
                == dataBirth.get(Calendar.MONTH)
                && dataBirth.get(Calendar.DAY_OF_MONTH)
                        > timeRightNow.get(Calendar.DAY_OF_MONTH)) {
                prepareAge--;
        }
        return prepareAge;
    }
}