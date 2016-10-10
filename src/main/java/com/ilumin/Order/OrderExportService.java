package com.ilumin.Order;

import com.google.common.collect.Lists;
import com.ilumin.OrderDetail.OrderDetail;
import com.ilumin.OrderDetail.OrderDetailRepository;
import com.ilumin.Product.Product;
import com.ilumin.Product.ProductRepository;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    public OrderDetailRepository orderDetailRepository;

    @Autowired
    public ProductRepository productRepository;

    public void downloadExcel(HttpServletResponse response, Iterable<Orders> data) throws IOException {
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
        createSheetData(sheet, Lists.newArrayList(data));

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

    private void createSheetData(Sheet sheet, List<Orders> orderses) {
        System.out.println(">> Orders.length = " + orderses.size());

        // get collection of order id
        List<Long> listOfOrderId = orderses.stream().map(Orders::getOrderId).collect(Collectors.toList());

        // get collection of order detail
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderIdIn(listOfOrderId);
        System.out.println(">> OrderDetail.length = " + orderDetails.size());

        Set<Long> setOfProductId = orderDetails.stream().map(OrderDetail::getProductId).collect(Collectors.toSet());
        // and this should be map of order id with order detail
        Map<Long, List<OrderDetail>> groupOfOrderDetail = orderDetails.stream().collect(Collectors.groupingBy(OrderDetail::getOrderId));

        // get collection of product
        List<Product> products = productRepository.findByProductIdIn(setOfProductId);
        System.out.println(">> Product.length = " + products.size());

        Map<Long, String> mapOfProductName = products.stream().collect(Collectors.toMap(Product::getProductId, Product::getProductName));

        orderses.forEach(order -> {
            List<OrderDetail> listOfOrderDetail = groupOfOrderDetail.getOrDefault(order.getOrderId(), Lists.newArrayList());
            if (listOfOrderDetail.size() > 0) {
                listOfOrderDetail.forEach(orderDetail -> writeRow(sheet, mapOfProductName, order, orderDetail));
            }
            else {
                writeOrderRow(sheet, order);
            }
        });
    }

    private void writeOrderRow(Sheet sheet, Orders orders) {
        Row row = sheet.createRow(currentRow++);

        createCell(row, 0, orders.getOrderId().toString());
        createCell(row, 1, orders.getCustomerId());
        createCell(row, 2, orders.getEmployeeId().toString());
        createCell(row, 3, orders.getOrderDate().getTime().toString());
        createCell(row, 4, orders.getRequiredDate().getTime().toString());

        // orders shipment information
        createCell(row, 12,
                Optional.ofNullable(orders.getShippedDate()).isPresent()
                        ? orders.getShippedDate().getTime().toString()
                        : ""
        );
        createCell(row, 13, orders.getShipVia().toString());
        createCell(row, 14, orders.getFreight().toString());
        createCell(row, 15, orders.getShipName());
        createCell(row, 16, orders.getShipAddress());
        createCell(row, 17, orders.getShipCity());
        createCell(row, 18, orders.getShipRegion());
        createCell(row, 19, orders.getShipPostalCode());
        createCell(row, 20, orders.getShipCountry());
    }

    private void writeRow(Sheet sheet, Map<Long, String> mapOfProductName, Orders orders, OrderDetail orderDetail) {
        // variable needed to get information
        Long productId = orderDetail.getProductId();

        Row row = sheet.createRow(currentRow++);

        // orders information
        createCell(row, 0, orders.getOrderId().toString());
        createCell(row, 1, orders.getCustomerId());
        createCell(row, 2, orders.getEmployeeId().toString());
        createCell(row, 3, orders.getOrderDate().getTime().toString());
        createCell(row, 4, orders.getRequiredDate().getTime().toString());

        // product information
        createCell(row, 5, productId.toString());
        createCell(row, 6, mapOfProductName.getOrDefault(productId, "-"));

        // orders detail information
        createCell(row, 7, orderDetail.getId().toString());
        createCell(row, 8, orderDetail.getUnitPrice().toString());
        createCell(row, 9, orderDetail.getQuantity().toString());
        createCell(row, 10, orderDetail.getDiscount().toString());
        createCell(row, 11, String.valueOf(
                (orderDetail.getUnitPrice() * orderDetail.getQuantity()) - orderDetail.getDiscount()
        ));

        // orders shipment information
        createCell(row, 12,
                Optional.ofNullable(orders.getShippedDate()).isPresent()
                        ? orders.getShippedDate().getTime().toString()
                        : ""
        );
        createCell(row, 13, orders.getShipVia().toString());
        createCell(row, 14, orders.getFreight().toString());
        createCell(row, 15, orders.getShipName());
        createCell(row, 16, orders.getShipAddress());
        createCell(row, 17, orders.getShipCity());
        createCell(row, 18, orders.getShipRegion());
        createCell(row, 19, orders.getShipPostalCode());
        createCell(row, 20, orders.getShipCountry());
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
        createCell(row, 0, "Orders data exported on " + currentDate);
    }

}
