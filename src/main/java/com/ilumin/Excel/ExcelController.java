package com.ilumin.Excel;

import com.ilumin.Employee.Employee;
import com.ilumin.Employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExcelController {

    @Autowired
    EmployeeRepository employeeRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/export")
    public void download() {
        System.out.println(">> Export!!");

        int page = 0;
        int size = 10;
        Pageable pageable = new PageRequest(page, size);
        Page<Employee> employees = employeeRepository.findAll(pageable);

        System.out.println(">> Employee: " + employees);
    }

}
