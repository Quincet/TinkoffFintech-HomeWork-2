package com.fintechtinkoff.homework.generateusers;

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

public class PdfCreator {
    private String path;
    private Font font;
    private SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.forLanguageTag("ru"));

    public PdfCreator(String path){
        this.path = path;
    }

    public void createPdfFile(PdfPTable table) throws DocumentException {
        Document document = new Document(PageSize.A1, 10, 10, 10, 10);
        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(path + "Users.pdf"));
            document.open();
            document.add(table);
            document.close();
            System.out.println("PDF файл с данными людей был создан по пути = " + Paths.get(path).toAbsolutePath() + File.separator + "Users.pdf");
        } catch (FileNotFoundException fileEx){
            System.out.println("При работе создания файла возникла ошибка, pdf файл не был создан");
            document.close();
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
    public PdfPTable prepareTable() throws IOException,DocumentException{
        BaseFont baseFont = BaseFont.createFont( path + "Roboto-Black.ttf",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        font = new Font(baseFont,Font.DEFAULTSIZE,Font.NORMAL);
        PdfPTable UsersTable = new PdfPTable(15);
        UsersTable.setSpacingBefore(10);
        UsersTable.setSpacingAfter(10);
        UsersTable.addCell(new Phrase("№",font));
        UsersTable.addCell(new Phrase("Имя",font));
        UsersTable.addCell(new Phrase("Фамилия",font));
        UsersTable.addCell(new Phrase("Отчество",font));
        UsersTable.addCell(new Phrase("Возраст",font));
        UsersTable.addCell(new Phrase("Пол",font));
        UsersTable.addCell(new Phrase("День Рождения",font));
        UsersTable.addCell(new Phrase("ИНН",font));
        UsersTable.addCell(new Phrase("Индекс",font));
        UsersTable.addCell(new Phrase("Страна",font));
        UsersTable.addCell(new Phrase("Регион",font));
        UsersTable.addCell(new Phrase("Город",font));
        UsersTable.addCell(new Phrase("Улица",font));
        UsersTable.addCell(new Phrase("Дом",font));
        UsersTable.addCell(new Phrase("Квартира",font));
        return UsersTable;
    }
}
