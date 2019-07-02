package com.fintechtinkoff.homework.generateusers.writers;

import com.fintechtinkoff.homework.generateusers.human.Human;
import com.fintechtinkoff.homework.generateusers.utils.Config;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public final class ExcelWriter implements IWriteHumansToFormat {
    private final SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.forLanguageTag("ru"));

    @Override
    public void writeHumans(List<Human> humans) {
        HSSFWorkbook sheets = prepareSheet();
        fillRows(humans,sheets);
        String fileName = commonDirectory + Config.getProperty("path.excelHuman");
        try(FileOutputStream fileOut = new FileOutputStream(fileName)) {
            sheets.write(fileOut);
            System.out.println("Эксель файл с данными людей был создан по пути = " + Paths.get(fileName).toAbsolutePath());
        } catch (Exception e) {
            System.out.println("Не удалось записать Excel файл \n\n");
            e.printStackTrace();
        }
    }

    private void fillRow(Human user, Integer numberUser, HSSFWorkbook sheets){
        HSSFRow row = sheets.getSheet("Humans").createRow(numberUser);
        row.createCell(0).setCellValue(numberUser);
        row.createCell(1).setCellValue(user.getName());
        row.createCell(2).setCellValue(user.getSurname());
        row.createCell(3).setCellValue(user.getPatronymic());
        row.createCell(4).setCellValue(user.getAge());
        row.createCell(5).setCellValue(user.getGender());
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
    private void fillRows(List<Human> humans, HSSFWorkbook sheets){
        for(int numberOfHuman = 0; numberOfHuman < humans.size(); numberOfHuman++){
            fillRow(humans.get(numberOfHuman),numberOfHuman,sheets);
        }
    }

    private HSSFWorkbook prepareSheet(){
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
}