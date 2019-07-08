package com.fintechtinkoff.homework.generateusers.writers;

import com.fintechtinkoff.homework.generateusers.human.Human;
import com.fintechtinkoff.homework.generateusers.utils.LoggerHelper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.fintechtinkoff.homework.generateusers.utils.FileHelper.getFullPath;
import static com.fintechtinkoff.homework.generateusers.utils.FileHelper.getProperty;
import static com.fintechtinkoff.homework.generateusers.writers.DefaultTableName.getNameOfColumns;
import static com.itextpdf.text.pdf.PdfWriter.getInstance;

public final class PDFWriter implements IWriteHumansToFormat {
    private final Font font = new Font(BaseFont.createFont(ourDirectory
            + getProperty("path.font"), BaseFont.IDENTITY_H,
            BaseFont.NOT_EMBEDDED), Font.DEFAULTSIZE, Font.NORMAL);
    private final AtomicInteger numberUser =
            new AtomicInteger(1);

    public PDFWriter() throws IOException, DocumentException { }

    @Override
    public void writeHumans(final List<Human> humans) {
        PdfPTable pdfPTable = getPdfPTable();
        prepareTable(pdfPTable)
                .fillRows(pdfPTable, humans);
        String pathToWriteFile = getFullPath("path.pdfHuman");
        try (var fileOutputStream = new FileOutputStream(pathToWriteFile)) {
            Document document = new Document(PageSize.A1, 10,
                    10, 10, 10);
            getInstance(document, fileOutputStream);
            document.open();
            document.add(pdfPTable);
            document.close();
            LoggerHelper.info("PDF файл с данными людей был создан по пути = "
                    + pathToWriteFile);
        } catch (Exception e) {
            LoggerHelper.error("Не удалось записать PDF файл \n\n"
                    +  e.getMessage());
        }
    }

    private PDFWriter fillRow(final PdfPTable table, final Human user) {
        fillCell(table, numberUser.getAndIncrement());
        user.getFieldsValueAsList()
                .forEach(x -> fillCell(table, x));
        return this;
    }

    private PDFWriter fillRows(final PdfPTable table,
                               final List<Human> humans) {
        for (Human human : humans) {
            fillRow(table, human);
        }
        return this;
    }

    private PDFWriter fillCell(final PdfPTable table, final Object fillValue) {
        table.addCell(new Phrase(fillValue.toString(), font));
        return this;
    }

    private PDFWriter prepareTable(final PdfPTable table) {
        getNameOfColumns()
                .forEach(x -> fillCell(table, x));
        return this;
    }

    private PdfPTable getPdfPTable() {
        PdfPTable usersTable = new PdfPTable(15);
        usersTable.setSpacingBefore(10);
        usersTable.setSpacingAfter(10);
        return usersTable;
    }
}
