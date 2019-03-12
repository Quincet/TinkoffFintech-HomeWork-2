package com.fintechtinkoff.homework.Generateusers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Locale;

final class PdfCreator {
    private final String path;
    private final Font font;
    private final SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.forLanguageTag("ru"));

    public PdfCreator(String path)throws IOException,DocumentException{
        this.path = path;
        font = new Font(BaseFont.createFont( path + "Roboto-Black.ttf",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED),Font.DEFAULTSIZE,Font.NORMAL);
    }

    public void createPdfFile(PdfPTable table) throws DocumentException,IOException {
        Document document = new Document(PageSize.A1, 10, 10, 10, 10);
        try(FileOutputStream fileOutputStream = new FileOutputStream(path + "Users.pdf")) {
            PdfWriter.getInstance(document, fileOutputStream);
            document.open();
            document.add(table);
            document.close();
            System.out.println("PDF файл с данными людей был создан по пути = " + Paths.get(path).toAbsolutePath() + File.separator + "Users.pdf");
        } catch (FileNotFoundException fileEx){
            System.out.println("При работе создания файла возникла ошибка, pdf файл не был создан");
        }

    }
    public void fillTable(PdfPTable table,Human user, Integer numberUser){
        table.addCell(new Phrase(numberUser.toString(),font));
        table.addCell(new Phrase(user.getName(),font));
        table.addCell(new Phrase(user.getSurname(),font));
        table.addCell(new Phrase(user.getPatronymic(),font));
        table.addCell(new Phrase(user.getAge().toString(),font));
        table.addCell(new Phrase(user.getGender() ? "Мужчина" : "Женщина",font));
        table.addCell(new Phrase(dataFormat.format(user.getDataBirth().getTime()),font));
        table.addCell(new Phrase(user.getInn(),font));
        table.addCell(new Phrase(user.getIndex(),font));
        table.addCell(new Phrase(user.getCountry(),font));
        table.addCell(new Phrase(user.getRegion(),font));
        table.addCell(new Phrase(user.getCity(),font));
        table.addCell(new Phrase(user.getStreet(),font));
        table.addCell(new Phrase(user.getHouse().toString(),font));
        table.addCell(new Phrase(user.getApartment().toString(),font));
    }
    public PdfPTable prepareTable(){
        PdfPTable usersTable = new PdfPTable(15);
        usersTable.setSpacingBefore(10);
        usersTable.setSpacingAfter(10);
        usersTable.addCell(new Phrase("№",font));
        usersTable.addCell(new Phrase("Имя",font));
        usersTable.addCell(new Phrase("Фамилия",font));
        usersTable.addCell(new Phrase("Отчество",font));
        usersTable.addCell(new Phrase("Возраст",font));
        usersTable.addCell(new Phrase("Пол",font));
        usersTable.addCell(new Phrase("День Рождения",font));
        usersTable.addCell(new Phrase("ИНН",font));
        usersTable.addCell(new Phrase("Индекс",font));
        usersTable.addCell(new Phrase("Страна",font));
        usersTable.addCell(new Phrase("Регион",font));
        usersTable.addCell(new Phrase("Город",font));
        usersTable.addCell(new Phrase("Улица",font));
        usersTable.addCell(new Phrase("Дом",font));
        usersTable.addCell(new Phrase("Квартира",font));
        return usersTable;
    }
}
