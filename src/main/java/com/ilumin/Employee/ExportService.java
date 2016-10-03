package com.ilumin.Employee;

import lombok.Getter;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.List;

@Service
public class ExportService {

    private static final String FILE_NAME = "employee";
    private static final String[] HEADERS = {
            "EmployeeID", "LastName", "FirstName", "Title", "TitleOfCourtesy",
            "BirthDate", "HireDate", "Address", "City", "Region", "PostalCode",
            "Country", "HomePhone", "Extension", "Photo", "Notes"
    };

    @Getter
    private String outputFileName;

    private String outputFilePath;
    private Workbook workbook;
    private CellStyle cellStyle;
    private Integer currentRow = 0;

    public void writeExcel(List<Employee> data) throws Exception {
        beforeStep();
        write(data);
        saveFile();
    }

    public void downloadExcel(List<Employee> data, HttpServletResponse response) throws Exception {
        beforeStep();
        write(data);
        downloadFile(response);
    }

    public void beforeStep() {
        String datetime = DateFormatUtils.format(Calendar.getInstance(), "yyyyMMdd-HHmmss");
        outputFileName = FILE_NAME + "-" + datetime + ".xlsx";
        outputFilePath = "/data/excel/" + outputFileName;

        workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employee");
        sheet.createFreezePane(0, 3, 0, 3);
        sheet.setDefaultColumnWidth(20);

        addTitleToSheet(sheet);
        currentRow++;
        addHeaders(sheet);
        initDataStyle();
    }

    public void saveFile() throws IOException, URISyntaxException {
        FileOutputStream fos = new FileOutputStream(outputFilePath);
        workbook.write(fos);
        fos.close();
    }

    public void downloadFile(HttpServletResponse response) throws IOException {
        response.addHeader("Content-disposition", "attachment;filename=" + getOutputFileName());
        response.setContentType("application/vnd.ms-excel");

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
    }

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
            createCell(row, 5, em.getBirthDate().getTime());
            createCell(row, 6, em.getHireDate().getTime());
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

        font.setFontHeightInPoints((short) 14);
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
