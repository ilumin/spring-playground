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
import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ExportService exportService;

    @RequestMapping(value = "/employee/export", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> exportEmployee(HttpServletResponse response) throws Exception {
        exportService.downloadExcel(getEmployeeAll(), response);

        return new ResponseEntity<>("Download...", HttpStatus.OK);
    }

    public List<Employee> getEmployeeFromPage() {
        int page = 0;
        int itemPerPage = 10;
        Pageable pageable = new PageRequest(page, itemPerPage);
        return employeeRepository.findAll(pageable).getContent();
    }

    public Iterable<Employee> getEmployeeAll() {
        return employeeRepository.findAll();
    }

}
