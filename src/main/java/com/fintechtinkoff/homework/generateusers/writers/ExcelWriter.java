package com.fintechtinkoff.homework.generateusers.writers;

import com.fintechtinkoff.homework.generateusers.human.Human;
import com.fintechtinkoff.homework.generateusers.utils.FileHelper;
import com.fintechtinkoff.homework.generateusers.utils.LoggerHelper;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static com.fintechtinkoff.homework.generateusers.utils.FileHelper.*;
import static com.fintechtinkoff.homework.generateusers.writers.DefaultTableName.getNameOfColumns;


public final class ExcelWriter implements IWriteHumansToFormat {
    private final AtomicInteger numberUser =
            new AtomicInteger(1);
    private final AtomicInteger atomicInteger =
            new AtomicInteger();

    @Override
    public void writeHumans(final List<Human> humans) {
        Logger.getLogger(this.getClass().getName());
        final var workbook = getSheet();
        prepareSheet(workbook)
                .fillRows(humans, workbook);
        String fileName = getFullPath("path.excelHuman");
        try (var fileOut = new FileOutputStream(fileName)) {
            workbook.getWorkbook().write(fileOut);
            LoggerHelper.info("Эксель файл с данными людей был создан "
                    + "по пути = " + fileName);
        } catch (Exception e) {
            LoggerHelper.error("Не удалось записать Excel файл \n\n"
                    + e.getMessage());
        }
    }

    private ExcelWriter fillRow(final Human user, final HSSFSheet sheets) {
        var row = sheets
                .createRow(numberUser.get());
        row.createCell(0)
                .setCellValue(numberUser.getAndIncrement());
        user.getFieldsValueAsList().forEach(x ->
                createAndFillCell(row, atomicInteger.incrementAndGet(), x));
        atomicInteger.set(0);
        return this;
    }

    private ExcelWriter fillRows(final List<Human> humans,
                                 final HSSFSheet sheet) {
        for (Human human:humans) {
            fillRow(human, sheet);
        }
        return this;
    }

    private ExcelWriter createAndFillCell(final HSSFRow row,
                                          final int numberColumn,
                                          final Object value) {
        row.createCell(numberColumn)
                .setCellValue(value.toString());
        return this;
    }

    private HSSFSheet getSheet() {
        var workBook = new HSSFWorkbook();
        return workBook
                .createSheet("Humans");
    }

    private ExcelWriter prepareSheet(final HSSFSheet sheet) {
        var row = sheet.createRow(0);
        getNameOfColumns().forEach(x ->
                createAndFillCell(row, atomicInteger.getAndIncrement(), x));
        atomicInteger.set(0);
        return this;
    }
}
