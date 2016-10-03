package com.ilumin.Order;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Optional;

import static org.apache.poi.ss.util.CellUtil.createCell;

@Service
public class OrderExportService {
    private static final String FILE_NAME = "order";
    private static final String[] HEADERS = {
            // order information
            "OrderID",
            "CustomerID",
            "EmployeeID",
            "OrderDate",
            "RequiredDate",

            // product information
            "ProductID",
            "ProductName",

            // order detail information
            "OrderDetailID",
            "UnitPrice",
            "Quantity",
            "Discount",
            "SubTotal",

            // order shipment information
            "ShippedDate",
            "ShipVia",
            "Freight",
            "ShipName",
            "ShipAddress",
            "ShipCity",
            "ShipRegion",
            "ShipPostalCode",
            "ShipCountry"
    };

    private String outputFileName;
    private Workbook workbook;
    private Integer currentRow = 0;

    public void downloadExcel(HttpServletResponse response, Iterable<Order> data) throws IOException {
        DateTime start, end;
        start = new DateTime();

        currentRow = 0;

        // defined filename
        String datetime = DateFormatUtils.format(Calendar.getInstance(), "yyyyMMdd-HHmmss");
        outputFileName = FILE_NAME + "-" + datetime + ".xlsx";
        System.out.println(">> START generate excel on " + start);

        // create workbook
        workbook = new XSSFWorkbook();

        // create sheet
        Sheet sheet = workbook.createSheet("Employee");
        sheet.createFreezePane(0, 2, 0, 2);

        // render excel data
        createSheetTitle(sheet);
        createSheetHeader(sheet);
        createSheetData(sheet, data);

        // process download
        processDownload(response);

        end = new DateTime();
        System.out.println(">> FINISHED generate excel on " + start);
        System.out.println(">> Total row = " + currentRow);
        System.out.println(">> Process duration = " + (new Duration(start, end)));
    }

    private void processDownload(HttpServletResponse response) throws IOException {
        response.addHeader("Content-disposition", "attachment;filename=" + outputFileName);
        response.setContentType("application/vnd.ms-excel");
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
    }

    private void createSheetData(Sheet sheet, Iterable<Order> data) {
        // order
        for (Order en: data) {
            // order detail
            for (OrderDetail item: en.getOrderDetails()) {
                Row row = sheet.createRow(currentRow++);

                // order information
                createCell(row, 0, en.getOrderID().toString());
                createCell(row, 1, en.getCustomerID());
                createCell(row, 2, en.getEmployeeID().toString());
                createCell(row, 3, en.getOrderDate().getTime().toString());
                createCell(row, 4, en.getRequiredDate().getTime().toString());

                // product information
                createCell(row, 5, item.getProduct().getProductID().toString());
                createCell(row, 6, item.getProduct().getProductName());

                // order detail information
                createCell(row, 7, item.getId().toString());
                createCell(row, 8, item.getUnitPrice().toString());
                createCell(row, 9, item.getQuantity().toString());
                createCell(row, 10, item.getDiscount().toString());
                createCell(row, 11, String.valueOf((item.getUnitPrice() * item.getQuantity() - item.getDiscount())));

                // order shipment information
                createCell(row, 12,
                        Optional.ofNullable(en.getShippedDate()).isPresent()
                                ? en.getShippedDate().getTime().toString()
                                : ""
                );
                createCell(row, 13, en.getShipVia().toString());
                createCell(row, 14, en.getFreight().toString());
                createCell(row, 15, en.getShipName());
                createCell(row, 16, en.getShipAddress());
                createCell(row, 17, en.getShipCity());
                createCell(row, 18, en.getShipRegion());
                createCell(row, 19, en.getShipPostalCode());
                createCell(row, 20, en.getShipCountry());
            }
        }
    }

    private void createSheetHeader(Sheet sheet) {
        Row row = sheet.createRow(currentRow++);
        int col = 0;
        for (String header: HEADERS) {
            createCell(row, col++, header);
        }
    }

    private void createSheetTitle(Sheet sheet) {
        Row row = sheet.createRow(currentRow++);
        row.setHeightInPoints(14);
        String currentDate = DateFormatUtils.format(Calendar.getInstance(), DateFormatUtils.ISO_DATETIME_FORMAT.getPattern());
        createCell(row, 0, "Order data exported on " + currentDate);
    }

}
