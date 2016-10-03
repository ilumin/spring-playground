package com.ilumin.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderExportService orderExportService;

    @GetMapping("/order/export")
    public ResponseEntity<String> download(HttpServletResponse response) throws IOException {
        orderExportService.downloadExcel(response, orderRepository.findAll());
        return new ResponseEntity<>("", HttpStatus.OK);
    }

}
