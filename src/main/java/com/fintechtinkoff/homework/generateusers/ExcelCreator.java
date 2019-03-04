package com.fintechtinkoff.homework.generateusers;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ExcelCreator{
    private SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.forLanguageTag("ru"));
    private String path;

    public ExcelCreator(String path){
        this.path = path;
    }

    public void fillRow(Human user, Integer numberUser, HSSFSheet sheet){
        HSSFRow row = sheet.createRow(numberUser);
        row.createCell(0).setCellValue(numberUser);
        row.createCell(1).setCellValue(user.getName());
        row.createCell(2).setCellValue(user.getSurname());
        row.createCell(3).setCellValue(user.getPatronymic());
        row.createCell(4).setCellValue(user.getAge());
        row.createCell(5).setCellValue(user.getGender() ? "Мужчина" : "Женщина");
        row.createCell(6).setCellValue(dataFormat.format(user.getDataBirth().getTime()));
        row.createCell(7).setCellValue(user.getInn());
        row.createCell(8).setCellValue(user.getIndex());
        row.createCell(9).setCellValue(user.getCountry());
        row.createCell(10).setCellValue(user.getRegion());
        row.createCell(11).setCellValue(user.getCity());
        row.createCell(12).setCellValue(user.getStreet());
        row.createCell(13).setCellValue(user.getHouse());
        row.createCell(14).setCellValue(user.getApartment());
    }
    public HSSFWorkbook prepareSheet(){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Humans");
        HSSFRow rowHead = sheet.createRow((short)0);
        rowHead.createCell(0).setCellValue("№");
        rowHead.createCell(1).setCellValue("Имя");
        rowHead.createCell(2).setCellValue("Фамилия");
        rowHead.createCell(3).setCellValue("Отчество");
        rowHead.createCell(4).setCellValue("Возраст");
        rowHead.createCell(5).setCellValue("Пол");
        rowHead.createCell(6).setCellValue("День Рождения");
        rowHead.createCell(7).setCellValue("ИНН");
        rowHead.createCell(8).setCellValue("Почтовый индекс");
        rowHead.createCell(9).setCellValue("Страна");
        rowHead.createCell(10).setCellValue("Область");
        rowHead.createCell(11).setCellValue("Город");
        rowHead.createCell(12).setCellValue("Улица");
        rowHead.createCell(13).setCellValue("Дом");
        rowHead.createCell(14).setCellValue("Квартира");
        return workbook;
    }
    public void createXlsFile(HSSFWorkbook workbook) throws IOException{
        String fileName = path + "Users.xls";
        try(FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
            System.out.println("Эксель файл с данными людей был создан по пути = " + Paths.get(fileName).toAbsolutePath());
        } catch (IOException ioExcept){
            System.out.println("При работе создания файла возникла ошибка, excel файл не был создан");
        } finally {
            workbook.close();
        }
    }
}