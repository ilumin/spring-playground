package com.ilumin.Employee;

import org.springframework.batch.item.ItemProcessor;

public class EmployeeBatchProcessor implements ItemProcessor<Employee, Employee> {

    @Override
    public Employee process(Employee employee) throws Exception {
        System.out.println(">> RUN PROCESS[" + employee.getEmployeeId() + "]");
        employee.setLastName("TRANSFORM: " + employee.getLastName().toUpperCase());
        employee.setFirstName("TRANSFORM: " + employee.getFirstName().toUpperCase());
        return employee;
    }

}
