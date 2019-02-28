package com.TinkoffFintech;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ExcelCreator {
    private SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.forLanguageTag("ru"));
    private String path;

    public ExcelCreator(String path){
        this.path = path;
    }

    public HSSFSheet fillRow(Human user, Integer numberUser, HSSFSheet sheet){
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
        return sheet;
    }
    public HSSFWorkbook prepareSheet(){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Humans");
        HSSFRow rowhead = sheet.createRow((short)0);
        rowhead.createCell(0).setCellValue("№");
        rowhead.createCell(1).setCellValue("Имя");
        rowhead.createCell(2).setCellValue("Фамилия");
        rowhead.createCell(3).setCellValue("Отчество");
        rowhead.createCell(4).setCellValue("Возраст");
        rowhead.createCell(5).setCellValue("Пол");
        rowhead.createCell(6).setCellValue("День Рождения");
        rowhead.createCell(7).setCellValue("ИНН");
        rowhead.createCell(8).setCellValue("Почтовый индекс");
        rowhead.createCell(9).setCellValue("Страна");
        rowhead.createCell(10).setCellValue("Область");
        rowhead.createCell(11).setCellValue("Город");
        rowhead.createCell(12).setCellValue("Улица");
        rowhead.createCell(13).setCellValue("Дом");
        rowhead.createCell(14).setCellValue("Квартира");
        return workbook;
    }
    public void createXlsFile(HSSFWorkbook workbook) throws IOException {
        String fileName = path + "Users.xls";
        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
        System.out.println("Эксель файл с данными людей был создан по пути = " + Paths.get(fileName).toAbsolutePath());
    }
}
