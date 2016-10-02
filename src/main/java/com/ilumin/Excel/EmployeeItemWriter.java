package com.ilumin.Excel;

import com.ilumin.Employee.Employee;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

@Component("employeeItemWriter")
@Scope("step")
public class EmployeeItemWriter implements ItemWriter<Employee> {

    private static final String FILE_NAME = "/data/excel/employee";
    private static final String[] HEADERS = {
        "EmployeeID", "LastName", "FirstName", "Title", "TitleOfCourtesy",
        "BirthDate", "HireDate", "Address", "City", "Region", "PostalCode",
        "Country", "HomePhone", "Extension", "Photo", "Notes"
    };

    private String outputFilename;
    private Workbook workbook;
    private CellStyle cellStyle;
    private Integer currentRow = 0;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        System.out.println(">> BEFORE STEP");

        String datetime = DateFormatUtils.format(Calendar.getInstance(), "yyyyMMdd-HHmmss");
        outputFilename = FILE_NAME + "-" + datetime + ".xlsx";

        workbook = new SXSSFWorkbook(100);
        Sheet sheet = workbook.createSheet("Employee");
        sheet.createFreezePane(0, 3, 0, 3);
        sheet.setDefaultColumnWidth(20);

        addTitleToSheet(sheet);
        currentRow++;
        addHeaders(sheet);
        initDataStyle();
    }

    @AfterStep
    public void afterStep(StepExecution stepExecution) throws IOException {
        FileOutputStream fos = new FileOutputStream(outputFilename);
        workbook.write(fos);
        fos.close();
    }

    @Override
    public void write(List<? extends Employee> items) throws Exception {
        Sheet sheet = workbook.getSheetAt(0);
        for (Employee em: items) {
            currentRow++;
            Row row = sheet.createRow(currentRow);
            createCell(row, 0, em.getEmployeeId());
            createCell(row, 1, em.getLastName());
            createCell(row, 2, em.getFirstName());
            createCell(row, 3, em.getTitle());
            createCell(row, 4, em.getTitleOfCourtesy());
            createCell(row, 5, em.getBirthDate());
            createCell(row, 6, em.getHireDate());
            createCell(row, 7, em.getAddress());
            createCell(row, 8, em.getCity());
            createCell(row, 9, em.getRegion());
            createCell(row, 10, em.getPostalCode());
            createCell(row, 11, em.getCountry());
            createCell(row, 12, em.getHomePhone());
            createCell(row, 13, em.getExtension());
            createCell(row, 14, em.getPhoto());
            createCell(row, 15, em.getNotes());
        }
    }

    private void addTitleToSheet(Sheet sheet) {
        Workbook wb = sheet.getWorkbook();
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();

        font.setFontHeightInPoints((short) 14);
        font.setFontName("Arial");
        font.setBold(true);

        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);

        Row row = sheet.createRow(currentRow);
        row.setHeightInPoints(14);

        String currentDate = DateFormatUtils.format(Calendar.getInstance(), DateFormatUtils.ISO_DATETIME_FORMAT.getPattern());

        createCell(row, 0, "Employee data as of " + currentDate);

        CellRangeAddress rangeAddress = new CellRangeAddress(0, 0, 0, 7);
        sheet.addMergedRegion(rangeAddress);
        currentRow++;
    }

    private void addHeaders(Sheet sheet) {
        Workbook wb = sheet.getWorkbook();
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();

        font.setFontHeight((short) 10);
        font.setFontName("Arial");
        font.setBold(true);

        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);

        Row row = sheet.createRow(2);
        int col = 0;

        for (String header: HEADERS) {
            createCell(row, col, header, style);
            col++;
        }

        currentRow = currentRow + 1;
    }

    private void initDataStyle() {
        cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();

        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");

        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setFont(font);
    }

    private void createCell(Row row, int col, Object value) {
        Cell cell = row.createCell(col);
        cell.setCellValue(value.toString());
    }

    private void createCell(Row row, int col, Object value, CellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellValue(value.toString());
        cell.setCellStyle(style);
    }
}
