package com.TinkoffFintech;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main  {
    private static String pathName = System.getProperty("user.dir") + File.separator + "resources" + File.separator;
    private static Random rnd = new Random();
    private static List<String> namesM,namesF,surnamesF,surnamesM,patronymicsM,patronymicsF,countries,regions,cities,streets;

    public static void main(String[] args){
        try {
            if (!new File(pathName).exists()) {
                System.out.println("Нет папки с ресурсами для работы программы");
                return;
            }
            readResourceFiles();
            int randomUsers = rnd.nextInt(29) + 1;
            ExcelCreator excelCreator = new ExcelCreator(pathName);
            PdfCreator pdfCreator = new PdfCreator(pathName);
            PdfPTable pdfPTable = pdfCreator.prepareTable();
            HSSFWorkbook workbook = excelCreator.prepareSheet();
            HSSFSheet sheet = workbook.getSheet("Humans");
            for (int countUser = 1; countUser <= randomUsers; countUser++) {
                Human user = generateUser();
                excelCreator.fillRow(user, countUser, sheet);
                pdfCreator.fillTable(pdfPTable, user, countUser);
            }
            excelCreator.createXlsFile(workbook);
            pdfCreator.createPDFfile(pdfPTable);
        } catch (IOException fileException){
            System.out.println("Вызвано исключение при работе с файлами = " + fileException.getMessage());
        } catch (DocumentException docException){
            System.out.println("Вызвано исключение при работе с документами = " + docException.getMessage());
        } catch (Exception unknownException){
            System.out.println("Вызвано неизвестное исключение = " + unknownException.getMessage());
        }
    }
    public static Human generateUser() {
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
        int index = rnd.nextInt(100000) + 100000;
        return new Human(name,surName,patronymic,gender,dateBirth,index,country,region,city,street,house,apartment);
    }
    private static void readResourceFiles() throws IOException{
        namesM = Files.lines(Paths.get(pathName + "Names.txt")).filter(x -> x.contains("Man")).map(x -> x.replace("Man|","")).collect(Collectors.toList());
        namesF = Files.lines(Paths.get(pathName + "Names.txt")).filter(x -> x.contains("Women")).map(x -> x.replace("Women|","")).collect(Collectors.toList());
        surnamesF = Files.lines(Paths.get(pathName + "SurNames.txt")).filter(x -> x.contains("Women")).map(x -> x.replace("Women|","")).collect(Collectors.toList());
        surnamesM = Files.lines(Paths.get(pathName + "SurNames.txt")).filter(x -> x.contains("Man")).map(x -> x.replace("Man|","")).collect(Collectors.toList());
        patronymicsM = Files.lines(Paths.get(pathName + "Patronymics.txt")).filter(x -> x.contains("Man")).map(x -> x.replace("Man|","")).collect(Collectors.toList());
        patronymicsF = Files.lines(Paths.get(pathName + "Patronymics.txt")).filter(x -> x.contains("Women")).map(x -> x.replace("Women|","")).collect(Collectors.toList());
        countries = Files.lines(Paths.get(pathName + "Countries.txt")).collect(Collectors.toList());
        regions = Files.lines(Paths.get(pathName + "Regions.txt")).collect(Collectors.toList());
        cities = Files.lines(Paths.get(pathName + "Cities.txt")).collect(Collectors.toList());
        streets = Files.lines(Paths.get(pathName + "Streets.txt")).collect(Collectors.toList());
    }
}
