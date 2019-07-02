package com.fintechtinkoff.homework.generateusers.writers;

import com.fintechtinkoff.homework.generateusers.human.Human;
import com.fintechtinkoff.homework.generateusers.utils.Config;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public final class Pdf_Writer implements IWriteHumansToFormat {
    private final Font font = new Font(BaseFont.createFont(commonDirectory + Config.getProperty("path.font"),BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED),Font.DEFAULTSIZE,Font.NORMAL);
    private final SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.forLanguageTag("ru"));

    public Pdf_Writer() throws IOException, DocumentException { }

    @Override
    public void writeHumans(List<Human> humans) {
        PdfPTable pdfPTable = prepareTable();
        fillTableManyRows(pdfPTable, humans);
        String pathToWriteFile = commonDirectory + Config.getProperty("path.pdfHuman");
        try(FileOutputStream fileOutputStream = new FileOutputStream(pathToWriteFile)) {
            Document document = new Document(PageSize.A1, 10, 10, 10, 10);
            com.itextpdf.text.pdf.PdfWriter.getInstance(document, fileOutputStream);
            document.open();
            document.add(pdfPTable);
            document.close();
            System.out.println("PDF файл с данными людей был создан по пути = " + Paths.get(Config.getProperty("path.pdfHuman")).toAbsolutePath());
        } catch (Exception e){
            System.out.println("Не удалось записать PDF файл \n\n");
            e.printStackTrace();
        }
    }
    private void fillTableOneRow(PdfPTable table, Human user, Integer numberUser){
        table.addCell(new Phrase(numberUser.toString(),font));
        table.addCell(new Phrase(user.getName(),font));
        table.addCell(new Phrase(user.getSurname(),font));
        table.addCell(new Phrase(user.getPatronymic(),font));
        table.addCell(new Phrase(user.getAge().toString(),font));
        table.addCell(new Phrase(user.getGender(),font));
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
    private void fillTableManyRows(PdfPTable table, List<Human> humans){
        for(int countHuman = 0; countHuman < humans.size(); countHuman++){
            fillTableOneRow(table, humans.get(countHuman),countHuman);
        }
    }
    private PdfPTable prepareTable(){
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
