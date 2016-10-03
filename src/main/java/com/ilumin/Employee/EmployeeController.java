package com.ilumin.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ExportService exportService;

    @RequestMapping(value = "/employee/export", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> exportEmployee(HttpServletResponse response) throws Exception {
        int page = 0;
        int itemPerPage = 10;
        Pageable pageable = new PageRequest(page, itemPerPage);

        exportService.downloadExcel(employeeRepository.findAll(pageable).getContent(), response);

        return new ResponseEntity<>("Download...", HttpStatus.OK);
    }

}
