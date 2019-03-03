package com.fintechtinkoff.homework.generateusers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.IOException;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class HumanGenerator {
    private String pathName = System.getProperty("user.dir") + File.separator + "resources" + File.separator;
    private Random rnd = new Random();
    private List<String> namesM,namesF,surnamesF,surnamesM,patronymicsM,patronymicsF,countries,regions,cities,streets;
    private int randomUsers = rnd.nextInt(300) + 30;

    public void createUsersManually(){
        try {
            if (!new File(pathName).exists()) {
                System.out.println("Нет папки с ресурсами для работы программы");
                return;
            }
            readResourceFiles();
            List<Human> listHumans = new ArrayList<>();
            for (int countUser = 0; countUser <= randomUsers; countUser++)
                listHumans.add(generateUser());
            writeExcelPdf(listHumans);
        } catch (IOException fileException){
            System.out.println("Вызвано исключение при работе с файлами = " + fileException.getMessage());
        } catch (DocumentException docException){
            System.out.println("Вызвано исключение при работе с документами = " + docException.getMessage());
        } catch (Exception unknownException){
            System.out.println("Вызвано неизвестное исключение = " + unknownException.getMessage());
        }
    }
    private void writeExcelPdf(List<Human> listHumans) throws IOException,DocumentException{
        ExcelCreator excelCreator = new ExcelCreator(pathName);
        PdfCreator pdfCreator = new PdfCreator(pathName);
        PdfPTable pdfPTable = pdfCreator.prepareTable();
        HSSFWorkbook workbook = excelCreator.prepareSheet();
        HSSFSheet sheet = workbook.getSheet("Humans");
        for (int countUser = 0; countUser < randomUsers; countUser++) {
            excelCreator.fillRow(listHumans.get(countUser), countUser+1, sheet);
            pdfCreator.fillTable(pdfPTable, listHumans.get(countUser), countUser+1);
        }
        excelCreator.createXlsFile(workbook);
        pdfCreator.createPdfFile(pdfPTable);
    }
    public void createUsersApi(){
        try {
            URIBuilder uriBuilder = new URIBuilder("https://randomuser.me/api/");
            uriBuilder.addParameter("results", String.valueOf(randomUsers)).addParameter("noinfo", "").addParameter("inc", "gender,name,location,dob");
            HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(30 * 1000).build()).build();
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            HttpResponse httpResponse = httpClient.execute(httpGet);
            String response = EntityUtils.toString(httpResponse.getEntity());
            List<Human> listHumans = mappingUsers(response);
            writeExcelPdf(listHumans);
        } catch (URISyntaxException uriSyntaxExcept){
            System.out.println("Вызвано исключение при работе с составлением параметров к запросу до сайта = " + uriSyntaxExcept.getMessage());
        } catch (IOException ioExcept){
            System.out.println("Была вызвана ошибка при работе с HTTP запросом, отсутствует интернет соединение");
            System.out.println("Будут созданы файлы пользователя не через API");
            createUsersManually();
        } catch (ParseException parseExcept){
            System.out.println("Вызвано исключение при работе с парсингом юзеров = " + parseExcept.getMessage());
        } catch (DocumentException documentExcept){
            System.out.println("Вызвано исключение при работе с документами = " + documentExcept.getMessage());
        }
    }
    private List<Human> mappingUsers(String jsonHuman) throws IOException,ParseException{
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonHuman).findValue("results");
        List<Human> randomUsers = new ArrayList<>();
        for(int indexArray = 0; indexArray < rootNode.size();indexArray++) {
            try(HumanFromSite humanRandomUser = mapper.readValue(rootNode.get(indexArray).toString(), HumanFromSite.class)){
                randomUsers.add(humanRandomUser.toHuman());
            }
        }
        return randomUsers;
    }
    private Human generateUser() {
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
        return Human.createBuilder()
                .setName(name)
                .setSurname(surName)
                .setPatronymic(patronymic)
                .setGender(gender)
                .setDataBirth(dateBirth)
                .setIndex(index)
                .setCountry(country)
                .setRegion(region)
                .setCity(city)
                .setStreet(street)
                .setHouse(house)
                .setApartment(apartment)
                .build();
    }
    private void readResourceFiles() throws IOException{
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
