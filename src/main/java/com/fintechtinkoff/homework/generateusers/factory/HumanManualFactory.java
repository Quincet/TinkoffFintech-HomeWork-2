package com.fintechtinkoff.homework.generateusers.factory;

import com.fintechtinkoff.homework.generateusers.human.Human;
import com.fintechtinkoff.homework.generateusers.utils.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.*;

public class HumanManualFactory implements IHumanFactory{
    private static List<String> namesM,namesF,surnamesF,surnamesM,patronymicsM,patronymicsF,countries,regions,cities,streets;
    private static String commonDirectory = System.getProperty("user.dir");

    public HumanManualFactory()throws IOException{
        if(namesM == null | namesF == null | surnamesF == null | surnamesM == null | patronymicsM == null |
            patronymicsF == null | countries == null | regions == null | cities == null | streets == null) {
            Stream<String> fileNames = Files.lines(Paths.get(commonDirectory + Config.getProperty("path.names")));
            Stream<String> fileSurNames = Files.lines(Paths.get(commonDirectory + Config.getProperty("path.surnames")));
            Stream<String> filePatronymics = Files.lines(Paths.get(commonDirectory + Config.getProperty("path.patronymics")));
            namesM = fileNames
                    .filter(x -> x.contains("Man"))
                    .map(x -> x.replace("Man|", ""))
                    .collect(Collectors.toList());

            namesF = fileNames
                    .filter(x -> x.contains("Women"))
                    .map(x -> x.replace("Women|", ""))
                    .collect(Collectors.toList());

            surnamesF = fileSurNames
                    .filter(x -> x.contains("Women"))
                    .map(x -> x.replace("Women|", ""))
                    .collect(Collectors.toList());

            surnamesM = fileSurNames
                    .filter(x -> x.contains("Man"))
                    .map(x -> x.replace("Man|", ""))
                    .collect(Collectors.toList());

            patronymicsM = filePatronymics
                    .filter(x -> x.contains("Man"))
                    .map(x -> x.replace("Man|", ""))
                    .collect(Collectors.toList());

            patronymicsF = filePatronymics
                    .filter(x -> x.contains("Women"))
                    .map(x -> x.replace("Women|", "")).
                            collect(Collectors.toList());

            countries = Files.lines(Paths.get(commonDirectory + Config.getProperty("path.countries")))
                    .collect(Collectors.toList());

            regions = Files.lines(Paths.get(commonDirectory + Config.getProperty("path.regions")))
                    .collect(Collectors.toList());

            cities = Files.lines(Paths.get(commonDirectory + Config.getProperty("path.cities")))
                    .collect(Collectors.toList());

            streets = Files.lines(Paths.get(commonDirectory + Config.getProperty("path.streets")))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<Human> createHumans(int countHumans){
        List<Human> humans = new ArrayList<>(countHumans);
        for(int countHuman = 0; countHuman < countHumans;countHuman++){
            humans.add((createHuman()));
        }
        return humans;
    }

    @Override
    public Human createHuman(){
        final Random rnd = new Random();
        boolean gender = rnd.nextBoolean();
        int yearBirth = rnd.nextInt(55) + 1950;
        int monthBirth = rnd.nextInt(12);
        int dayBirth = rnd.nextInt(31);
        Calendar dateBirth = new GregorianCalendar();
        dateBirth.set(yearBirth,monthBirth,dayBirth);
        String name = gender ? namesM.get(rnd.nextInt(namesM.size())): namesF.get(rnd.nextInt(namesF.size()));
        String surName = gender ? surnamesM.get(rnd.nextInt(surnamesM.size())): surnamesF.get(rnd.nextInt(surnamesF.size()));
        String patronymic = gender ? patronymicsM.get(rnd.nextInt(patronymicsM.size())): patronymicsF.get(rnd.nextInt(patronymicsF.size()));
        String country = countries.get(rnd.nextInt(countries.size()));
        String region = regions.get(rnd.nextInt(regions.size()));
        String city = cities.get(rnd.nextInt(cities.size()));
        String street = streets.get(rnd.nextInt(streets.size()));
        int house = rnd.nextInt(200);
        int apartment = rnd.nextInt(99);
        String index = String.valueOf(rnd.nextInt(100000) + 100000);
        return Human.builder()
                .name(name)
                .surname(surName)
                .patronymic(patronymic)
                .gender(gender ? "М" : "Ж")
                .dataBirth(dateBirth)
                .index(index)
                .country(country)
                .region(region)
                .city(city)
                .street(street)
                .house(house)
                .apartment(apartment)
                .build();
    }

}
