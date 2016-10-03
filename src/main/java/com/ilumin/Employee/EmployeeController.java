package com.ilumin.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ExportService exportService;

    @RequestMapping(value = "/employee/export", method = RequestMethod.GET)
    public String exportEmployee() throws Exception {
        int page = 0;
        int itemPerPage = 10;
        Pageable pageable = new PageRequest(page, itemPerPage);
        exportService.writeExcel(employeeRepository.findAll(pageable).getContent());
        return ">>> Yikes";
    }

}
