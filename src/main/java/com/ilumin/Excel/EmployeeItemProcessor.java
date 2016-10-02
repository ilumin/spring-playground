package com.ilumin.Excel;

import com.ilumin.Employee.Employee;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component("employeeItemProcessor")
public class EmployeeItemProcessor implements ItemProcessor<Employee, Employee> {

    @Override
    public Employee process(Employee item) throws Exception {
        System.out.printf(">> Processing result:" + item);
        return item;
    }

}
